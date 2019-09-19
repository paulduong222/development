package gov.nwcg.isuite.core.rules.reports.shiftsInExcess;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import org.springframework.context.ApplicationContext;

public class StartDateBeforeEndDateRule extends AbstractShiftsInExcessGenerationRule implements IRule {

	public StartDateBeforeEndDateRule(ApplicationContext ctx, String rname)
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
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

		ErrorObject error=null;
		
		if(filter.getFirstDateToIncludeOnReport() == null){
			error = new ErrorObject("error.mustSelectItem","Start Date");
			errorObjects.add(error);
		}
		if(filter.getLastDateToIncludeOnReport() == null){
			error = new ErrorObject("error.mustSelectItem","End Date");
			errorObjects.add(error);
		}
		if(errorObjects.size() == 0) {
			if(!filter.getFirstDateToIncludeOnReport().before(filter.getLastDateToIncludeOnReport())) {
				error = new ErrorObject("error.startDateMustPrecedeEndDate","End Date");
				errorObjects.add(error);
			}
		}
		
		if(errorObjects.size() > 0) {
			CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaName(ruleName);
		    coa.setCoaType(CourseOfActionTypeEnum.ERROR);
		    coa.setErrorObjectVos(errorObjects);
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
