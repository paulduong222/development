package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class CheckUniqueCodeRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_UNIQUE_CODE.name();

	public CheckUniqueCodeRules(ApplicationContext ctx)
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
		 * B.R. 4.0001
		 *      If the Accounting Code the user is adding to the Incident is already associated 
		 *      with that Incident, regardless of the Agency selected, the system must not save 
		 *      the Accounting Code and must display a message. 
		 *      [Message 0128] 
		 *      (Applies to Use Cases 4.0001 and 4.0002.)
		 * 
		 * Development: This rule looks to be in conflict with 
		 * 				CheckAgencyCodeUniqueRule.java and
		 * 				CheckSameCodeAgencyRule.java.
		 */
		
		IncidentAccountCode iac = dao.getIACByAccountCodeAndIncidentId(
				vo.getAccountCodeVo().getAccountCode().toUpperCase(), incidentEntity.getId(), vo.getId());
		
		if(iac != null) {
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			courseOfActionVo.setMessageVo(
					new MessageVo(
							"text.accountingFireCodes",
							"error.0128",
							null,
							MessageTypeEnum.CRITICAL));
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			
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
