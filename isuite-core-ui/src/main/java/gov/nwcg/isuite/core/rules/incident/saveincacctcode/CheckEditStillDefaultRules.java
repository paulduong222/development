package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckEditStillDefaultRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_EDIT_STILL_DEFAULT.name();

	public CheckEditStillDefaultRules(ApplicationContext ctx)
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
		if(!LongUtility.hasValue(vo.getId())) {
			//Add mode.  No need to run this edit rule.
			return _OK;
		}
		
		/*
		 * B.R. 4.0002
		 *    7.	The system must require the user to identify one default 
		 *          Accounting Code for an Incident. 
		 *          (Applies to Use Cases 4.0001 and 4.0002.)
		 * 
		 * D.R. 4.0002 - Edit Accounting Codes
		 *    2.	The system must prevent the user from deselecting 
		 *    		the Default option for an Accounting Code. 
		 *    		The system must only allow a user to change the default 
		 *    		Accounting Code by selecting the Default option for a different Accounting Code.
		 * 
		 * Development: If updating the incident account code that is the default,
		 * 				and the incident account code now has default set to false,
		 * 				then the rule check failed.
		 */
		
		IncidentAccountCode iac = dao.getById(vo.getId(), IncidentAccountCodeImpl.class);
		if(iac != null) {
			if(iac.getDefaultFlag() == true) {
				if(vo.getDefaultFlag() == false) {

					CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
					courseOfActionVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
					courseOfActionVo.setCoaName("ERROR");
					courseOfActionVo.setMessageVo(
							new MessageVo(
									"text.incidentAccountingFireCodes",
									"error.cannotDeselectDefaultAcctCode",
									null,
									MessageTypeEnum.CRITICAL));
					courseOfActionVo.setIsDialogueEnding(true);
					dialogueVo.setCourseOfActionVo(courseOfActionVo);

					return _FAIL;
				}
			}
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
