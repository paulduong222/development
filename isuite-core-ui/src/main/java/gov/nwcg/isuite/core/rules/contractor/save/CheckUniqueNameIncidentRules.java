package gov.nwcg.isuite.core.rules.contractor.save;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class CheckUniqueNameIncidentRules extends AbstractContractorSaveRule implements IRule{
	public static final String _RULE_NAME=ContractorSaveRuleFactory.RuleEnum.CHECK_UNIQUE_NAME_INCIDENT.name();

	public CheckUniqueNameIncidentRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

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
			
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 * @throws PersistenceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {	
		
		try {
			Collection<Long> incidentIds = new ArrayList<Long>();
			for (IncidentVo incidentVo : vo.getIncidentVos()) {
				incidentIds.add(incidentVo.getId());
			}

			if(!CollectionUtility.hasValue(incidentIds) && AbstractVo.hasValue(vo.getIncidentVo())){
				incidentIds.add(vo.getIncidentVo().getId());
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				
				if (null != super.dao.getByContractorNameIds(vo.getName().toUpperCase(), incidentIds, null, vo.getId())) {
					
					CourseOfActionVo coaVo = new CourseOfActionVo();
					coaVo.setCoaName("DuplicateNameError");
					coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE );
					coaVo.setIsDialogueEnding(true);
					
					coaVo.setMessageVo(new MessageVo("text.contractor",ErrorEnum._0238_DUPLICATE_CONTRACTOR_NAME.getErrorName()
										, new String[]{vo.getName().toUpperCase()} , MessageTypeEnum.CRITICAL));

					dialogueVo.setCourseOfActionVo(coaVo);
					
					return _FAIL;
				}
			}
			
		} catch(Exception e){
			throw new ServiceException(e);
		}

		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
