package gov.nwcg.isuite.core.rules.contractor.save;

import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;

public class ValidateDataRules extends AbstractContractorSaveRule implements IRule{
	public static final String _RULE_NAME=ContractorSaveRuleFactory.RuleEnum.VALIDATE_DATA.name();

	public ValidateDataRules(ApplicationContext ctx)
	{
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

		// check for incident
		//if(!CollectionUtility.hasValue(vo.getIncidentVos())){
		if(!AbstractVo.hasValue(vo.getIncidentVo())){
			error = new ErrorObject("info.generic","An incident must be associated with the Contractor.");
			if(null != error)errorObjects.add(error);
		}
		
		// check for name
		error=Validator.validateStringField2("Contractor/Cooperator Name", vo.getName(), 50, true);
		if(null != error)errorObjects.add(error);
		
		// check tin 
		if(StringUtility.hasValue(vo.getTin())){
			error=Validator.validateStringField2("TIN", vo.getTin(), 9, false);
			if(null != error)errorObjects.add(error);
			
			if(StringUtility.hasValue(vo.getVerifyTin())){
				if(!vo.getTin().equals(vo.getVerifyTin())){
					error = new ErrorObject("info.generic","TIN and Verify TIN do not match.");
					if(null != error)errorObjects.add(error);
				}
			}else{
				error = new ErrorObject("info.generic","TIN and Verify TIN do not match.");
				if(null != error)errorObjects.add(error);
			}
		
		}else {
			if(StringUtility.hasValue(vo.getVerifyTin())) {
				error = new ErrorObject("info.generic","TIN and Verify TIN do not match.");
				if(null != error)errorObjects.add(error);
			}
		}


		// check duns
		if(StringUtility.hasValue(vo.getDuns())){
			error=Validator.validateStringField2("DUNS", vo.getDuns(), 13, false);
			if(null != error)errorObjects.add(error);
		}
		
		// check phone
		if(StringUtility.hasValue(vo.getPhone())){
			vo.setPhone(StringUtility.removeNonNumeric(vo.getPhone()));
			error=Validator.validatePhoneNumberField("Phone", vo.getPhone(), false);
			if(null != error)errorObjects.add(error);
		}
		
		if(AbstractVo.hasValue(vo.getAddressVo())){
			// check addr1
			if(StringUtility.hasValue(vo.getAddressVo().getAddressLine1())){
				error=Validator.validateStringField2("Address Line 1", vo.getAddressVo().getAddressLine1(), 35, false);
				if(null != error)errorObjects.add(error);
			}
			
			// check addr2
			if(StringUtility.hasValue(vo.getAddressVo().getAddressLine2())){
				error=Validator.validateStringField2("Address Line 2", vo.getAddressVo().getAddressLine2(), 35, false);
				if(null != error)errorObjects.add(error);
			}
			
			// check city
			if(StringUtility.hasValue(vo.getAddressVo().getCity())){
				error=Validator.validateStringField2("City", vo.getAddressVo().getCity(), 30, false);
				if(null != error)errorObjects.add(error);
			}
			
			// check state
			
			// check zip
			if(StringUtility.hasValue(vo.getAddressVo().getPostalCode())){
				vo.getAddressVo().setPostalCode(StringUtility.removeNonNumeric(vo.getAddressVo().getPostalCode()));
				error=Validator.validateZipCodeField("Zip Code", vo.getAddressVo().getPostalCode(), false);
				if(null != error)errorObjects.add(error);
			}
		}
		
		// If errors exist, create Validation Error COA and set in dialogue
		if(CollectionUtility.hasValue(errorObjects)){
			CourseOfActionVo coaVo = buildValidationErrorCoaVo(errorObjects);
			dialogueVo.setCourseOfActionVo(coaVo);
			
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
