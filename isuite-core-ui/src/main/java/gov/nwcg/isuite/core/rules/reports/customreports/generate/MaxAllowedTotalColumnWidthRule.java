package gov.nwcg.isuite.core.rules.reports.customreports.generate;


import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.customreports.designer.DesignModifierDelegate;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;

import org.springframework.context.ApplicationContext;

public class MaxAllowedTotalColumnWidthRule extends AbstractCustomReportGenerationRule implements IRule {
	
	private static final String ERROR_COLUMNS_TOO_WIDE = 
		"The columns selected for this report are too wide to be shown on the report page."
		+ "\nYou can make one or more of the following three changes and try again:" 
		+ "\n1. Export this report to Excel (columns can be as wide as needed), or," 
		+ "\n2. Reduce the total number of columns on the report, or,"
		+ "\n3. Reduce the width of the columns on the report"; // Dont end with period.
	
	private static final String ERROR_COLUMNS_TOO_WIDE_LANDSCAPE_OPTION =
		", or,"
		+ "\n4. Change the report orientation to landscape.";
	
	public MaxAllowedTotalColumnWidthRule(ApplicationContext ctx, String rname)
	{
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
		
		String errorString = null;
		
		if(reportVo.getExportToExcel()) {
			// This section may include other exports like HTML where page width and heights are irrelevant
		} else { // Report type is PDF
			if(reportVo.getTotalWidthOfColumnsIncludingPadding() > DesignModifierDelegate.getMaximumAllowedContentWidth(reportVo)) {
				errorString = ERROR_COLUMNS_TOO_WIDE;
				
				if(!reportVo.getLandscape()) {
					errorString += ERROR_COLUMNS_TOO_WIDE_LANDSCAPE_OPTION;
				} else {
					errorString += ".";
				}
			}
		}
		
		if(errorString != null) {
			CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coa.setMessageVo(new MessageVo("text.customReports"
					, "info.generic"
					, new String[]{errorString}
					, MessageTypeEnum.CRITICAL));
		    
		    coa.setIsDialogueEnding(true);
		    
		    dialogueVo.setCourseOfActionVo(coa);
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
