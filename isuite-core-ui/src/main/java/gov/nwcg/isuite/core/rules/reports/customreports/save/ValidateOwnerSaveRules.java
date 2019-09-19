package gov.nwcg.isuite.core.rules.reports.customreports.save;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class ValidateOwnerSaveRules extends AbstractCustomReportSaveRule implements IRule {
	
	private static final String EM_USER_NOT_PERMITTED = 
		"You must be the owner of the report to save the report.";
	
	public ValidateOwnerSaveRules(ApplicationContext ctx, String rname) {
		super(ctx, rname);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			if(isCourseOfActionComplete(dialogueVo, ruleName))
				return _OK;
					
			/*
			 * Run rule check
			 */
			if(runRuleCheck(dialogueVo) == _FAIL) {
			    return _FAIL;
			} 	
			
			/*
			 * Rule check passed, mark as completed
			 */
			dialogueVo.getProcessedCourseOfActionVos()
				.add(super.buildNoActionCoaVo(ruleName,true));
		
		}catch(Exception e){
			throw new ServiceException(e);
		}
		
		return _OK;
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {

		// Is the user the report creator?
		if(!userVo.getId().equals(customReportVo.getUserVo().getId())){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coaVo.setMessageVo(new MessageVo("text.customReports"
					, "info.generic"
					, new String[]{EM_USER_NOT_PERMITTED}
					, MessageTypeEnum.CRITICAL));
			
		    coaVo.setIsDialogueEnding(Boolean.TRUE);
		    
		    dialogueVo.setCourseOfActionVo(coaVo);
		    
			return _FAIL;
		} 
			
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
