package gov.nwcg.isuite.core.rules.timepost.crews;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.framework.util.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidationAdRules extends AbstractCrewRule implements IRule {
	public static final String _RULE_NAME="VALIDATE_AD_RULES";
	
	public ValidationAdRules(ApplicationContext ctx){
		super(ctx,_RULE_NAME);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executeRules(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		
		try{

			/*
			 * if rule check has been completed, return
			 */
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
			throw e;
		}

		return _OK;
	}

	/**
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		if(super.employmentType=="AD"){
			// list of errors
			Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();

			errorObjects = super.validateCommonData();
			
			ErrorObject error=null;

			if(AbstractVo.hasValue(vo.getSpecialPayVo())){
				String code=vo.getSpecialPayVo().getCode();
				
				double hours = 0;
				
				if(null != vo.getQuantity())
					hours=vo.getQuantity().doubleValue();
					
				int intHours = TypeConverter.convertToInt(new BigDecimal(hours));
				
				if(code.equals("COP") || code.equals("GUAR") ){
					// hours must be between 0 and 8
					if( ((hours < 0.0)  || hours > 8.0 ) ){
						error = new ErrorObject("info.generic","When Special Rate is COP or GUAR, the hours must be between 0 and 8");
						errorObjects.add(error);
					}
				}else if(code.equals("DAY OFF")){
					// hours must be 0 or 8
					if(intHours != 0 && intHours != 8){
						error = new ErrorObject("info.generic","When Special Rate is DAY OFF, the hours must be 0 or 8");
						errorObjects.add(error);
					}
					
				}else if(code.equals("TVL")){
					// start time
					if(StringUtility.hasValue(vo.getPostStartTime())){
						error = DateTimeValidator.validateTimeField("Start Time", vo.getPostStartTime());
						if(error!=null)errorObjects.add(error);
					}else{
						error=Validator.validateStringField2("Start Time", vo.getPostStartTime(), 4, true);
						if(error!=null)errorObjects.add(error);
					}

					// only check stop time if postrtntravel = false
					if(BooleanUtility.isFalse(vo.getReturnTravelStartOnly())){
						// stop time
						if(StringUtility.hasValue(vo.getPostStopTime())){
							error = DateTimeValidator.validateTimeField("Stop Time", vo.getPostStopTime());
							if(error!=null)errorObjects.add(error);
						}else{
							error=Validator.validateStringField2("Stop Time", vo.getPostStopTime(), 4, true);
							if(error!=null)errorObjects.add(error);
						}
					}
				}
				
				// check for rate
				error=Validator.validateVoField2("Class", vo.getRateClassRateVo(), true);
				if(error!=null)errorObjects.add(error);
				
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
			
		}
		
		return _OK;
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		
	}
	
}
