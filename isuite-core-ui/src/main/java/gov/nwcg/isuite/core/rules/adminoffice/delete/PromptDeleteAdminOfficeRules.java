package gov.nwcg.isuite.core.rules.adminoffice.delete;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class PromptDeleteAdminOfficeRules extends AbstractAdminOfficeDeleteRule implements IRule{
	public static final String _RULE_NAME=AdminOfficeDeleteRuleFactory.RuleEnum.PROMPT_DELETE_ADMIN_OFFICE.name();

	
	public PromptDeleteAdminOfficeRules(ApplicationContext ctx)
	{
		super(ctx,_RULE_NAME);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {

//		try{
//			if(!isCourseOfActionComplete(dialogueVo, _RULE_NAME)){
//				
//
//				if(super.isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
//
//					// add to processed
//					dialogueVo.getCourseOfActionVo().setIsComplete(Boolean.TRUE);
//					dialogueVo.getProcessedCourseOfActionVos().add(dialogueVo.getCourseOfActionVo());
//
//					 // Check Prompt Result:					
//					if(getPromptResult(dialogueVo) == PromptVo._YES) {
//						// continue;						
//					}else if(getPromptResult(dialogueVo) == PromptVo._NO){
//						// cannot continue if prompt was cancel delete
//						CourseOfActionVo coaVo = new CourseOfActionVo();
//						coaVo.setCoaName(_MSG_FINISHED);
//						coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
//						coaVo.setMessageVo(new MessageVo("text.adminOfficesForPayment", "text.abortProcess" , new String[]{"deleting admin office"}, MessageTypeEnum.INFO));
//						coaVo.setIsDialogueEnding(Boolean.TRUE);
//						
//						dialogueVo.setCourseOfActionVo(coaVo);
//						
//						return _FAIL;
//					}
//					
//					
//				}else{
//					/*
//					 * 	When a user deletes an admin office, 
//					 *  display message confirming 
//					 *  the delete action.
//					 */
//					CourseOfActionVo coaVo = new CourseOfActionVo();
//					coaVo.setCoaName(_RULE_NAME);
//					coaVo.setCoaType(CourseOfActionTypeEnum.PROMPT);
//					coaVo.setPromptVo(new PromptVo("text.adminOfficesForPayment","text.confirmDeleteAdminOffice",new String[]{vo.getOfficeName().toUpperCase()},PromptVo._YES | PromptVo._NO ));
//					dialogueVo.setCourseOfActionVo(coaVo);
//					
//					return _FAIL;
//				}
//				
//			}
//
//		}catch(Exception e){
//			throw new ServiceException(e);
//		}
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}

}
