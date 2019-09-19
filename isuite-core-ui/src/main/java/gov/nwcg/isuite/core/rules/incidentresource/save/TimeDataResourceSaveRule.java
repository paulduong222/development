package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.context.ApplicationContext;

public class TimeDataResourceSaveRule extends AbstractIncidentResourceSaveRule
		implements IRule {

	public static final String _RULE_NAME="TIME_DATA_VALIDATION";
	
	public TimeDataResourceSaveRule(ApplicationContext ctx) {
		
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {

		try {
			
			/*
			 * if rule check has been completed, return
			 */
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;
			
//			if(isCurrentCourseOfAction(dialogueVo, _RULE_NAME)){
				
//				dialogueVo.getCourseOfActionVo().setIsComplete(true);
				
				return runRuleCheck(dialogueVo);
				
//			} 
			
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		
	}
	
	/**
	 * @param dialogueVo
	 * @return
	 * @throws Exception
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws Exception {
		
		ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
		
		if(isContractor()) {
			errors.addAll(getContractorValidationErrors());
		}
		
		if(isAD()) {
			errors.addAll(getAdErrors());
		}
		
		if(isNonContractedPerson()) {
			errors.addAll(getNonContractedPersonErrors());
		}
		
		if(errors.size() > 0) {
			dialogueVo.setCourseOfActionVo(buildValidationErrorCoaVo(errors));
			return _FAIL;
		} else {
			return _OK;
		}
	}
	
	private Boolean isContractor() {
		
		if(null != vo.getWorkPeriodVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo()
				&& vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.CONTRACTOR) {
			
			//if(vo.getSourcePage().equals("time"))
				return Boolean.TRUE;
			//else
			//	return Boolean.FALSE;
		} else return Boolean.FALSE;
	}
	
	private ArrayList<ErrorObject> getContractorValidationErrors() {
		
		ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
		
		ErrorObject error=null;
		
		if(super.hasRole("ROLE_TIME")){
			////if resource is contractor/cooperator, a unique name or vin is always required
			error=Validator.validateStringField2("Unique/VIN Name"
					, vo.getWorkPeriodVo().getCurrentAssignmentVo()
					.getAssignmentTimeVo().getContractorPaymentInfoVo().getVinName(), 90, true);
			if(null != error)errors.add(error);
		}
		
		//agreement number is required when contractor/cooperator is selected
		if(AbstractVo.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorVo())){
			if(!AbstractVo.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getContractorAgreementVo())){
		
				errors.add(new ErrorObject("error.0226"));
			}
		}
		
		// hired date cannot be before inc start date
		//Date hiredDate=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDate();
		Date hiredDate=null;
		String hiredTime="";
		if(DateTransferVo.hasDateString(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDateVo())){
			try{
				hiredDate=DateTransferVo.getTransferDate(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDateVo());
				hiredTime=DateTransferVo.getTime(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo().getHiredDateVo());
			}catch(Exception ee){}
		}		
		Date incStartDate=vo.getIncStartDate();
		if(DateUtil.hasValue(hiredDate) && DateUtil.hasValue(incStartDate)){
			error=DateTimeValidator.validateDate1NotBeforeDate2(
					hiredDate
					,incStartDate
					, "Hired Date", "Incident Start Date");
			if(error!=null)errors.add(error);
		}
		
		//ensure HiredTime is in correct format
		if(StringUtility.hasValue(hiredTime)){
			error = DateTimeValidator.validateTimeField("Contractor Hired Time", hiredTime);
			if(error!=null)errors.add(error);
		}
		
		return errors;
	}
	
	private Boolean isAD() {
		
		if(null != vo.getWorkPeriodVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getContractorPaymentInfoVo()
				&& vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.AD) {
			
			return Boolean.TRUE;
		} else return Boolean.FALSE;
	}
	
	private ArrayList<ErrorObject> getAdErrors() {
		
		ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();

		if(StringUtility.hasValue(vo.getSourcePage()) && vo.getSourcePage().equals("time")){
			//SSN
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
					.getAdPaymentInfoVo().getSsn())) {

				boolean ssnError=false;
				
				if(StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getSsnVerify())) {
							
					if(!vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getSsn().equals(
							vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getAdPaymentInfoVo().getSsnVerify())){
						ssnError=true;
					}
				}else{
					ssnError=true;
				}

				if(ssnError==true){
					// error ssn/verifyssn do not match
					ErrorObject error = new ErrorObject("info.generic","SSN and Verify SSN do not match.");
					errors.add(error);
					
				}
				
				// verify length is 9
				if(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
						.getAdPaymentInfoVo().getSsn().length() != 9){
					ErrorObject error = new ErrorObject("info.generic","SSN must be 9 numeric characters.");
					errors.add(error);
				}
			}

		
			//point of hire
			if(!AbstractVo.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
					.getAdPaymentInfoVo().getPointOfHireOrgVo())) {
				
				errors.add(new ErrorObject("text.requiredPointOfHire"));
			}
			
			//rate area
			/*
			if(null == vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
					.getAdPaymentInfoVo().getRateAreaVo()) {
				
				errors.add(new ErrorObject("text.requiredRateArea"));
			}
			*/
			
			//rate class rate
			if(!AbstractVo.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo()
					.getAdPaymentInfoVo().getRateClassRateVo())) {
				
				errors.add(new ErrorObject("text.requiredRateClass"));
			}
			
		}
		
		return errors;
	}
	
	private Boolean isNonContractedPerson() {
		
		if(null != vo.getWorkPeriodVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo()
				&& null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo() 
				&& (vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.AD
				|| vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.FED
				|| vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType() == EmploymentTypeEnum.OTHER)) {
			
			return Boolean.TRUE;
			
		} else return Boolean.FALSE;
		
	}
	
	private ArrayList<ErrorObject> getNonContractedPersonErrors() {
		ArrayList<ErrorObject> errors = new ArrayList<ErrorObject>();
		
		ErrorObject error=null;
		
		// check phone
		if(StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getPhone())){
			vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().setPhone(StringUtility.removeNonNumeric(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getPhone()));
			error=Validator.validatePhoneNumberField("Phone", vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getPhone(), false);
			if(null != error)errors.add(error);
		}
		
		// check zip
		if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getMailingAddressVo()) {
			
			if(StringUtility.hasValue(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getMailingAddressVo().getPostalCode())){
				vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getMailingAddressVo().setPostalCode(StringUtility.removeNonNumeric(vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getMailingAddressVo().getPostalCode()));
				error=Validator.validateZipCodeField("Zip Code", vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getMailingAddressVo().getPostalCode(), false);
				if(null != error)errors.add(error);
			}
		}
		
		return errors;
	}
	
	
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo)
			throws Exception {
		// TODO Auto-generated method stub

	}
}
