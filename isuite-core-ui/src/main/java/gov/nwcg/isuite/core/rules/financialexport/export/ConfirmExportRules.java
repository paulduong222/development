package gov.nwcg.isuite.core.rules.financialexport.export;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.PromptVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import org.springframework.context.ApplicationContext;

public class ConfirmExportRules extends AbstractFinancialExportExportRule implements IRule{
	public static final String _RULE_NAME=FinancialExportExportRuleFactory.RuleEnum.CONFIRM_EXPORT.name();

	public ConfirmExportRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
		
	}

	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
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
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		/*A.	12.0001 - Financial Export
				Use Case ID: 12.0001	Goal: Generate a Financial Export
				Actors: Data Steward	Category: Enterprise and Site
		
				Business Requirements
		
				Financial Export export
				
				1. When the user clicks the button to create a Financial Export file, the system must warn the user that no data included in that export file can be modified. Yes and No buttons are available. [Message 0258]
					1.1. When the user selects Yes in response to message 0258, the system must create the Financial Export File.
					1.2. When the user selects No in response to message 0259, the system must not create the Financial Export File.
		*/

		if(null != financialExportVo) {
			
			PromptVo promptVo = new PromptVo();
			promptVo.setMessageProperty("action.0258");
			promptVo.setTitleProperty("text.financialExport"); 
		    promptVo.setPromptValue(PromptVo._YES | PromptVo._NO);
			
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.PROMPT);
		    coa.setPromptVo(promptVo);
		    
		    dialogueVo.setCourseOfActionVo(coa);
			
			return _FAIL;
		}
	
		
		return _OK;
	}

	
}
