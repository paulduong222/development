package gov.nwcg.isuite.core.rules.incident.restrictincident;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckAlreadyRestrictedRules extends AbstractRestrictIncidentRule implements IRule{
	public static final String _RULE_NAME=RestrictIncidentRuleFactory.RuleEnum.CHECK_ALREADY_RESTRICTED.name();

	public CheckAlreadyRestrictedRules(ApplicationContext ctx)
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
	 */
	private int runRuleCheck(DialogueVo dialogueVo) {

		/*
		 * Check if incident is already restricted.
		 * 
		 */
		
		if(null != incidentEntity){
			if(null != incidentEntity.getRestricted() && incidentEntity.getRestricted()){
			
				CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName(_MSG_FINISHED);
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coaVo.setMessageVo(new MessageVo("text.time"
												, "error.800000"
												, new String[]{"The incident is already restricted."}
												, MessageTypeEnum.CRITICAL));
				coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setCourseOfActionVo(coaVo);
				
				return _FAIL;
			}
		}

		return _OK;
	}
	
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
