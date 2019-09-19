package gov.nwcg.isuite.core.rules.incident.saveincacctcode;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import org.springframework.context.ApplicationContext;

public class CheckNewDefaultRules extends AbstractSaveIncAcctCodeRule implements IRule{
	public static final String _RULE_NAME=SaveIncAcctCodeRuleFactory.RuleEnum.CHECK_NEW_DEFAULT.name();

	public CheckNewDefaultRules(ApplicationContext ctx)
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
		Boolean showPrompt=false;
		String code="";
		
		/*
		 * B.R. 4.0001
		 * 	   If the user identifies the Accounting Code as the default Accounting Code
		 *     for an Incident and that Incident already has a default Accounting Code defined,
		 *     the system must display an action message confirming that the user wants 
		 *     to change the default Accounting Code. 
		 *     Yes and No buttons are available. 
		 *     [Message 0129] (Applies to Use Cases 4.0001 and 4.0002.)
	 	 *
	 	 *		2.1	If the user selects Yes in response to action message 0129, 
	 	 *	    the system must make the new Accounting Code the default Accounting Code
	 	 *		and save the Accounting Code data. 
	 	 *		The system must remove the Default attribute from the Accounting Code 
	 	 *		that was previously defined as the default for this Incident. 
	 	 *		(Applies to Use Cases 4.0001 and 4.0002.)
		 *      
		 *      2.2	If the user selects No in response to action message 0129, 
		 *      the system must leave the existing Accounting Code as the default Accounting Code. 
		 *      The system must display a message and save the Accounting Code data. 
		 *      [Message 0130]. 
		 *      (Applies to Use Cases 4.0001 and 4.0002.)ere
		 * 
		 */
		if(vo.getDefaultFlag() && CollectionUtility.hasValue(incidentEntity.getIncidentAccountCodes())){

			if(!LongUtility.hasValue(vo.getId())) {
				for(IncidentAccountCode iac : incidentEntity.getIncidentAccountCodes()){
					if(iac.getDefaultFlag()){
						code=iac.getAccountCode().getAccountCode();
					}
				}
				showPrompt=true;
			} else{
				/*
				 * Check if this incident accounting code is already the default?
				 */
				for(IncidentAccountCode iac : incidentEntity.getIncidentAccountCodes()){
					if(iac.getDefaultFlag()){
						if(iac.getId().compareTo(vo.getId()) != 0){
							code=iac.getAccountCode().getAccountCode();
							showPrompt=true;
						}
					}
				}
			}
		}

		if(showPrompt){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_RULE_NAME);
			coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
			coaVo.setPromptVo(new PromptVo("text.incidentAccountingCodes"
											,"action.0129"
											,new String[]{code, vo.getAccountCodeVo().getAccountCode()}
											,PromptVo._YES | PromptVo._NO));
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
	
		
		if(getPromptResult(dialogueVo)==PromptVo._YES){
			
			dialogueVo.getCourseOfActionVo().setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
			dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
			dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
			
		}else{
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName(_MSG_FINISHED);
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.incidentAccountingCodes"
											, "text.abortProcess" 
											, new String[]{"save incident accounting code"}
											, MessageTypeEnum.INFO));
			dialogueVo.setCourseOfActionVo(coaVo);
					
			return _FAIL;
			
		}
		
		return _OK;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
		CourseOfActionVo coa = dialogueVo.getCourseOfActionByName(_RULE_NAME);
		
		if(null != coa && coa.getCoaType()==CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED){

			dao.updateNonDefaults(super.incidentEntity.getId(), super.entity.getId());

		}
	}

}
