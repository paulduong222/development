package gov.nwcg.isuite.core.rules.reports.customreports.sqlbuilder;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

public class ValidateDataRules extends AbstractCustomReportSQLBuilderRule implements IRule {

	private static final String EM_AT_LEAST_ONE_COLUMN = 
		"At least one column must exist in the report.";
	
	public static final String _RULE_NAME=CustomReportSQLBuilderRuleFactory.RuleEnum.VALIDATE_DATA.name();
	
	public ValidateDataRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, ruleName))
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
	private int runRuleCheck(DialogueVo dialogueVo) {
		
		// list of errors
		Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
		ErrorObject error=null;
		
		// At least one column must exist
		if(!CollectionUtility.hasValue(reportVo.getCustomReportColumnVos())) {
			
			error = new ErrorObject("info.generic", EM_AT_LEAST_ONE_COLUMN);
			errorObjects.add(error);
		}
		
		if(CollectionUtility.hasValue(errorObjects)){
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("ValidationError");
			coaVo.setCoaType(CourseOfActionTypeEnum.ERROR );
			coaVo.setIsDialogueEnding(true);

			coaVo.setErrorObjectVos(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
			return _FAIL;
		}
		
		return _OK;
	}
	
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
