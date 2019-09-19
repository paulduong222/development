package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.RequestNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class DuplicateRequestNumberRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME="DUPLICATE_REQUEST_NUMBER";

	public DuplicateRequestNumberRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try {

			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			

			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				dialogueVo.getCourseOfActionVo().setIsComplete(true);

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
			
		} catch(Exception e){
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
		 * B.R. 5.0004
		 * 
		 *   4.	If a user enters a Request Number that is identical to a Request Number 
		 *      already assigned to a Resource for the selected Incident, the system must 
		 *      display a confirmation message. Yes and No buttons are available. 
		 *      [Message 0165] 
		 * 
		 *   4.1.	When the user selects ‘Yes’ in response to message 0165, 
		 *          the system must save the Resource and assign the duplicate 
		 *          Request Number to the Resource.
		 *          
		 *   4.2.	When the user selects No in response to message 0165, 
		 *          the system must not save the Resource data with that request number.
		 *           
		 */
		Collection<IncidentResourceGridVo> vos = null;
		
		RequestNumberUtil.formatRequestNumber(vo);
		
		if(!StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber()))
			return _OK;
		
		Boolean checkForDuplicates=false;
		
		if(LongUtility.hasValue(vo.getId())){
			String reqNum="";
			
			for(Assignment a : irEntity.getWorkPeriod().getAssignments()){
				if(!DateUtil.hasValue(a.getEndDate())){
					reqNum=a.getRequestNumber();
					break;
				}
			}
			
			if(vo.getIncidentVo().getId().compareTo(irEntity.getIncidentId()) != 0) {
				checkForDuplicates = true;
			}else{
				if(StringUtility.hasValue(reqNum) && StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber())){
					if(!reqNum.equals(vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber()))
						checkForDuplicates=true;
				}else
					checkForDuplicates=true;
			}
		}else{
			checkForDuplicates=true;
		}
		
		if(checkForDuplicates==true){
			vos = irDao.checkRequestNumber(workAreaId, vo);
			
			if(null != vos && vos.size()>0){
				
				String resourceNames="";
				
				for(IncidentResourceGridVo irgvo : vos){
					String name="";
					if(StringUtility.hasValue(irgvo.getResourceName())){
						name=irgvo.getResourceName();
					}else{
						name=irgvo.getLastName() + ", " + irgvo.getFirstName();
					}
					
					resourceNames = resourceNames + "\n" + name;
				}
				
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_RULE_NAME);
				coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
				coaVo.setPromptVo(new PromptVo("text.incidentResources","text.duplicateRequestNumber",new String[]{resourceNames},PromptVo._YES | PromptVo._NO));
				
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
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
		
		dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
		
		if(getPromptResult(dialogueVo) == PromptVo._YES) {
			// continue;
		}else{
			// cannot continue if prompt was no
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentResources", "text.abortProcess" , new String[]{"save"}, MessageTypeEnum.INFO));

			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
