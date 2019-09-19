package gov.nwcg.isuite.core.rules.iap.copyform;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
import gov.nwcg.isuite.core.vo.Duplicate204BranchDivisionVo;
import gov.nwcg.isuite.core.vo.IapForm204Vo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.CustomPromptVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

public class Duplicate204BranchDivisionRules extends AbstractCopyFormRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_204_BRANCH_DIVISION";
	
	public Duplicate204BranchDivisionRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{

			/*
			 * if rule check completed,
			 * return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){

				/*
				 * Check prompt result
				 */
				if(checkPromptResult(dialogueVo)==_FAIL)
					return _FAIL;
				
			}else{

				/*
				 * Run rule check
				 */
				if(runRuleCheck(dialogueVo)==_FAIL)
					return _FAIL;
				
				/*
				 * Rule check passed, mark as completed
				 */
				dialogueVo.getProcessedCourseOfActionVos()
					.add(super.buildNoActionCoaVo(_RULE_NAME,true));
			}
				
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*
		 * If the copied form is an ICS 204 form that has the same name, 
		 * the system must allow the user to change the Branch and Division 
		 * for the ICS 204 form in order to change the form’s name.
		 */
		
		if(CollectionUtility.hasValue(iapCopyVo.getIapForm204Vos())){
			IapBranchDao iapBranchDao = (IapBranchDao)context.getBean("iapBranchDao");
			
			//IapForm204Vos in destination plan to compare to
			Collection<IapForm204Vo> iapForm204DestinationVos;
			
			iapForm204DestinationVos = IapForm204Vo.getInstances(iapBranchDao.getByPlanId(iapCopyVo.getIapPlanDestinationVo().getId()), true);
			
			if(CollectionUtility.hasValue(iapForm204DestinationVos)){
				
				//duplicate IapForm204Vos
				Collection<Duplicate204BranchDivisionVo> form204DuplicateVos = new ArrayList<Duplicate204BranchDivisionVo>();
				
				for(IapForm204Vo iapForm204Vo : iapCopyVo.getIapForm204Vos()) {
					
					if(!StringUtility.hasValue(iapForm204Vo.getBranchName()) || !StringUtility.hasValue(iapForm204Vo.getDivisionName())) {
						iapForm204Vo = IapForm204Vo.getInstance(iapBranchDao.getById(iapForm204Vo.getId(), IapBranchImpl.class), true);
					}
					
					IapBranch iapBranch = iapBranchDao.getByBranchDivision(iapForm204Vo.getBranchName(), iapForm204Vo.getDivisionName(), iapCopyVo.getIapPlanDestinationVo().getId(), null);
					
					if(null != iapBranch) {
						Duplicate204BranchDivisionVo duplicateVo = new Duplicate204BranchDivisionVo();
						duplicateVo.setId(iapForm204Vo.getId());
						duplicateVo.setNewBranchName(iapBranch.getBranchName());
						duplicateVo.setNewDivisionName(iapBranch.getDivisionName());
						duplicateVo.setBranchName(iapBranch.getBranchName());
						duplicateVo.setDivisionName(iapBranch.getDivisionName());
						form204DuplicateVos.add(duplicateVo);
					}
				}
				
				if(CollectionUtility.hasValue(form204DuplicateVos)) {
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName(_RULE_NAME);
					coaVo.setCoaType(CourseOfActionTypeEnum.CUSTOMPROMPT);
					coaVo.setCustomPromptVo(new CustomPromptVo("DUP204BRANCHDIV","text.iap" ,"", form204DuplicateVos));
					coaVo.setStoredObject(iapForm204DestinationVos);
					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
				
			}
			
		}
		
		return _OK;
	}
	
	
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int checkPromptResult(DialogueVo dialogueVo) throws Exception {
		
		dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.NOACTION);
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());

		return _OK;
	}
	
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

	

}
