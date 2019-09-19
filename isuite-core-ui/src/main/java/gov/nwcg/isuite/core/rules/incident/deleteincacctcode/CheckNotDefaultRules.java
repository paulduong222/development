package gov.nwcg.isuite.core.rules.incident.deleteincacctcode;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckNotDefaultRules extends AbstractDeleteIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=DeleteIncAcctCodeRuleFactory.RuleEnum.CHECK_NOT_DEFAULT.name();

	public CheckNotDefaultRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try{
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
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
		 * B.R. 4.0003 Delete Accounting Codes
		 * 
		 *    2.	If an Accounting Code is the default Accounting Code for an Incident, 
		 *          the system must prevent a user from deleting the Accounting Code for 
		 *          that Incident, until the user has selected a different default 
		 *          Accounting Code for the Incident.
		 *          
		 *    3.	If there is only one Accounting Code associated with an Incident, 
		 *          the system must allow the user to delete that Accounting Code and 
		 *          have no Accounting Codes associated with that Incident.
		 *          
		 *          
		 * Development: In order to delete the default accounting code for an incident,
		 *              it has to be the only accounting code for the incident.
		 * 
		 */
		if(entity.getDefaultFlag() && incidentEntity.getIncidentAccountCodes().size()>1){
			String errorMsg="The system cannot delete the incident accounting code.  " +
							"You must first selected another accounting code as the default.";

			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentAccountingCodes"
										, "info.generic"
										, new String[]{errorMsg}
										, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
	
			dialogueVo.setCourseOfActionVo(coaVo);

			return _FAIL;
			
		}
		
		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int checkPromptResult(DialogueVo dialogueVo) {
	
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
