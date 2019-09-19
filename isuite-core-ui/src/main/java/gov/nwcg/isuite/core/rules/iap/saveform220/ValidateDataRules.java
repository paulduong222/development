package gov.nwcg.isuite.core.rules.iap.saveform220;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractSaveForm220Rule implements IRule {
	public static final String _RULE_NAME=SaveForm220RuleFactory.RuleEnum.VALIDATE_DATA.name();
	
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

		// check if sunrise is valid
		if(StringUtility.hasValue(super.iapForm220Vo.getSunrise())){
			if(BooleanUtility.isFalse(DateUtil.isMilitaryTime(super.iapForm220Vo.getSunrise()))){
				ErrorObject error2 = new ErrorObject("error.800000","Sunrise is invalid time.");
				errorObjects.add(error2);
			}
		}
		
		// check if sunset is valid
		if(StringUtility.hasValue(super.iapForm220Vo.getSunset())){
			if(BooleanUtility.isFalse(DateUtil.isMilitaryTime(super.iapForm220Vo.getSunset()))){
				ErrorObject error2 = new ErrorObject("error.800000","Sunset is invalid time.");
				errorObjects.add(error2);
			}
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
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}


