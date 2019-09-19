package gov.nwcg.isuite.core.rules.incidentresourceother.save;

import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.util.RequestNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class CheckIRORequestNumberFormatRules extends AbstractSaveIRORule implements IRule {
	public static final String _RULE_NAME = SaveIRORuleFactory.RuleEnum.CHECK_REQUEST_NUMBER_FORMAT.name();

	public CheckIRORequestNumberFormatRules(ApplicationContext ctx) {
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
	 * Rule to:
	 * 1. Format the request number.
	 * 2. Validate the request number. 
	 * @param dialogueVo
	 * @return
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		String requestNumber = vo.getResourceOtherVo().getRequestNumber();
		
		if(StringUtility.hasValue(requestNumber)){
			requestNumber = RequestNumberUtil.formatRequestNumberString(requestNumber);
			String rqChar = RequestNumberUtil.parseRequestNumberChar(requestNumber);
			
			/*
			 * if rqChar has a value, verify it is A C E O or S
			 */
			if(StringUtility.hasValue(rqChar)){
				if(rqChar.length() > 1 || !rqChar.toUpperCase().matches("[ACEOS]")){
					// must be A C E O S
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.incidentResources"
												  ,"validationerror"
												  ,"error.8004"
												  ,null));	
					return _FAIL;
				}
			}
			
			String rqValue = RequestNumberUtil.parseRequestNumberValue(requestNumber);
	
			/*
			 * If rqValue has a value, verify it only contains
			 * numbers and/or .
			 */
			if(StringUtility.hasValue(rqValue)){
				if(!rqValue.matches("([0-9?\\.]*)")){
					dialogueVo.setCourseOfActionVo(
							super.buildErrorCoaVo("text.incidentResources"
												  ,"validationerror"
												  ,"error.8005"
												  ,null));	
					return _FAIL;
				}
			}
			
			/*
			 * verify we either have both or none at all
			 */
			if( (StringUtility.hasValue(rqChar) && !StringUtility.hasValue(rqValue))
					||
				(!StringUtility.hasValue(rqChar) && StringUtility.hasValue(rqValue) )){
			
				dialogueVo.setCourseOfActionVo(
						super.buildErrorCoaVo("text.incidentResources"
											  ,"validationerror"
											  ,"error.8005"
											  ,new String[]{""}));	
				return _FAIL;
			}
		}
		
		//All validation has passed; Save the formatted request number in the 
		//original holding object 
		vo.getResourceOtherVo().setRequestNumber(requestNumber);
		return _OK;
	}
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {

	}

}
