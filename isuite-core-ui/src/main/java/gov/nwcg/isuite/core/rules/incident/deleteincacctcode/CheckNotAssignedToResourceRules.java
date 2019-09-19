package gov.nwcg.isuite.core.rules.incident.deleteincacctcode;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckNotAssignedToResourceRules extends AbstractDeleteIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=DeleteIncAcctCodeRuleFactory.RuleEnum.CHECK_NOT_ASSIGNED_TO_RESOURCE.name();

	public CheckNotAssignedToResourceRules(ApplicationContext ctx)
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
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		/*
		 * B.R. 4.0003 Delete Accounting Codes
		 * 
		 * 	6.	If an Accounting Code is assigned to a Resource or used in any time postings, 
		 * 		the system must prevent the user from deleting the Accounting Code.
		 * 
		 * Development: Check if assigned to resource.
		 */
		
		int cnt=dao.getResourceCount(entity.getId(), entity.getIncidentId());
		
		if(cnt > 0){
			
			String msg="You cannot delete an Incident Accounting Code that has been assigned " + 
						"as the default for a Resource in the Incident.";
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentAccountingCodes"
											, "error.800000"
											, new String[]{msg}
											, MessageTypeEnum.CRITICAL));
			coaVo.setIsDialogueEnding(Boolean.TRUE);
					
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
