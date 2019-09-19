package gov.nwcg.isuite.core.rules.incidentresource.save;

import gov.nwcg.isuite.core.vo.AccrualCodeVo;
import gov.nwcg.isuite.core.vo.CostDataVo;
import gov.nwcg.isuite.core.vo.IncidentVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.rules.IRule;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import org.springframework.context.ApplicationContext;

public class DefaultAccrualCodeRules extends AbstractIncidentResourceSaveRule implements IRule {
	public static final String _RULE_NAME=IncidentResourceSaveRuleFactory.RuleEnum.GENERATE_DEFAULT_ACCRUAL_CODE.name();
	
	public DefaultAccrualCodeRules(ApplicationContext ctx) {
		super(ctx, _RULE_NAME);
	}

	@Override
	public int executeRules(DialogueVo dialogueVo) throws Exception {
		try{
			// if rule check has been completed, return
			if(isCourseOfActionComplete(dialogueVo, _RULE_NAME))
				return _OK;

			// Run rule check
			if(runRuleCheck(dialogueVo)==_FAIL)
				return _FAIL;
			
			 // Rule check passed, mark as completed
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
	 * @throws ServiceException 
	 */
	private int runRuleCheck(DialogueVo dialogueVo) throws ServiceException {
		if(null == vo.getCostDataVo())
			return _OK;

		// This rule should only be run if the Accrual Code hasn't been locked by the user
		if(BooleanUtility.isFalse(vo.getCostDataVo().getAccrualLocked())) {
			
			// /////////////////////////////////////////////////////////////////////
			// Retrieve values to be used to run this rule
			IncidentVo incident = vo.getIncidentVo();
			ResourceVo resourceVo = vo.getResourceVo();
			CostDataVo costData = vo.getCostDataVo();

			String resourceAgency = (resourceVo.getAgencyVo()!=null)?resourceVo.getAgencyVo().getAgencyCd():"";
			Boolean isResourceAgencyState = (resourceVo.getAgencyVo()!=null)?resourceVo.getAgencyVo().getState():Boolean.FALSE;
			String paymentAgency = (costData.getPaymentAgencyVo()!=null)?costData.getPaymentAgencyVo().getAgencyCd():"";
			String jurisdiction = (incident.getAgencyVo()!=null)?incident.getAgencyVo().getAgencyCd():"";
			String incidentState = (incident.getCountryCodeSubdivisionVo()!=null)?incident.getCountryCodeSubdivisionVo().getCountrySubAbbreviation():"";
			String resourceUnit=resourceVo.getOrganizationVo().getUnitCode();
			String resourceUnitState=(resourceUnit!=null?resourceUnit.substring(0, 2):"");
			String requestNumber=vo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber();
			String requestCategory="";
			if(StringUtility.hasValue(requestNumber))
				requestCategory=requestNumber.substring(0, 1);
			String kindCode=vo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getCode();
			String empCode="";
			if(null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo() &&
					null != vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType()){
				empCode=vo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentTimeVo().getEmploymentType().name();
			}

			// /////////////////////////////////////////////////////////////////////
			// Determine which domain (jurisdiction or payment agency) the resource comes under.

			// Boolean to denote if Jurisdiction or Payment Agency is FS
			boolean isFSDomain = false;
			
			// Boolean to denote if Jurisdiction or Payment Agency is DOI
			String doiDomainString = "BIA BLM BOEM BOR BSEE NPS OSM FWS USGS";
			boolean isDOIDomain = false;
			
			// Boolean to denote if Jurisdiction or Payment Agency is anything other than FS and DOI
			boolean isOtherDomain = false;
			
			// Per use-case excel sheet: If payment agency is established for a resource, the 
			// payment agency rules will override the jurisdictional agency
			if(StringUtility.hasValue(paymentAgency)){
				isFSDomain = "USFS".equalsIgnoreCase(paymentAgency);
				isDOIDomain = doiDomainString.indexOf(paymentAgency) >= 0;
				isOtherDomain = !(isFSDomain || isDOIDomain);
			} else if(StringUtility.hasValue(jurisdiction)){
				isFSDomain = "USFS".equalsIgnoreCase(jurisdiction);
				isDOIDomain = doiDomainString.indexOf(jurisdiction) >= 0;
				isOtherDomain = !(isFSDomain || isDOIDomain);
			}
			
			// /////////////////////////////////////////////////////////////////////
			// Determine the default accrual code using the above values
			if(("SHW".equalsIgnoreCase(kindCode) || "CTR".equalsIgnoreCase(kindCode)) && "PVT".equalsIgnoreCase(resourceAgency)){
				return updateAccrualCode(dialogueVo, "CONT"); 
			} else if("USFS".equalsIgnoreCase(resourceAgency) && "AD".equalsIgnoreCase(empCode)) {
				return updateAccrualCode(dialogueVo, "AD");
			} else if(isFSDomain && isResourceAgencyState && incidentState.equalsIgnoreCase(resourceAgency)) {
				return updateAccrualCode(dialogueVo, resourceAgency);
			} else if(isFSDomain && isResourceAgencyState && !incidentState.equalsIgnoreCase(resourceAgency) && incidentState.equalsIgnoreCase(resourceUnitState)){
				return updateAccrualCode(dialogueVo, resourceUnitState); 
			} else if(isFSDomain && ("RUR".equalsIgnoreCase(resourceAgency) || "CNTY".equalsIgnoreCase(resourceAgency) || "CITY".equalsIgnoreCase(resourceAgency))
					&& incidentState.equalsIgnoreCase(resourceUnitState)) {
				return updateAccrualCode(dialogueVo, resourceUnitState); 
			} else if((isFSDomain || isOtherDomain) && isResourceAgencyState && !incidentState.equalsIgnoreCase(resourceAgency) && !incidentState.equalsIgnoreCase(resourceUnitState)){
				return updateAccrualCode(dialogueVo, resourceUnitState); 
			} else if((isFSDomain || isOtherDomain) && ("RUR".equalsIgnoreCase(resourceAgency) || "CNTY".equalsIgnoreCase(resourceAgency) || "CITY".equalsIgnoreCase(resourceAgency))
					&& !incidentState.equalsIgnoreCase(resourceUnitState)) {
				return updateAccrualCode(dialogueVo, resourceUnitState); 
			} else if(isFSDomain && !"A".equalsIgnoreCase(requestCategory) && "PVT".equalsIgnoreCase(resourceAgency)) {
				return updateAccrualCode(dialogueVo, "CONT"); 
			} else if(isFSDomain && "AMD".equalsIgnoreCase(resourceAgency)) {
				return updateAccrualCode(dialogueVo, "AMD"); 
			} else if(isFSDomain && "NWS".equalsIgnoreCase(resourceAgency)) {
				return updateAccrualCode(dialogueVo, "NOAA"); 
			} else {
				return updateAccrualCode(dialogueVo, "EXCL");
			}
		}
		return _OK;
	}
	
	/**
	 * Private method to update the accrual code in the cost data based on the String value of the accrual code it receives.
	 * 
	 * @param dialogueVo
	 * @param accrualCodeCd Code value of the accrual code. 
	 * @return _OK or _FAIL based on whether a matching a accrual code Vo was found for the input parameter accrualCodeCd
	 * @throws ServiceException if accrualCodeCd is a non-empty String but does not match a valid AccrualCodeVo's code
	 */
	private int updateAccrualCode(DialogueVo dialogueVo, String accrualCodeCd) throws ServiceException {
		if(accrualCodeCd == null){
			vo.getCostDataVo().setAccrualCodeVo(null);
		}else{
			for(AccrualCodeVo acvo : super.getGlobalCacheVo().getAccrualCodeVos()){
				if(acvo.getCode().equals(accrualCodeCd)){
					vo.getCostDataVo().setAccrualCodeVo(acvo);
					break;
				}
			}
			/*
			retrieveAccrualCodeVos();
			AccrualCodeVo systemSelectedAccrualCodeVo = null;
			
			for(AccrualCodeVo accrualCodeVo : accrualCodes){
				if(accrualCodeVo.getCode().equals(accrualCodeCd)){
					systemSelectedAccrualCodeVo = accrualCodeVo;
					break;
				}
			}
			
			// If a matching accrual code Vo is found in the list of Accrual Code Vos, set that as the default Accrual Code Vo;
			// else, return an error. This allows the user to manually select an accrual code, lock it, and save the record again.
			if(systemSelectedAccrualCodeVo != null) {
				iroVo.getCostDataVo().setAccrualCodeVo(systemSelectedAccrualCodeVo);
				return _OK;
			} else {
				Collection<ErrorObject> errorObjects = new ArrayList<ErrorObject>();
				ErrorObject error = new ErrorObject("info.generic","Could not determine the correct Accrual Code for this Other Resource.");
				errorObjects.add(error);
				CourseOfActionVo coaVo = buildValidationErrorCoaVo(errorObjects);
				dialogueVo.setCourseOfActionVo(coaVo);
				return _FAIL;
			}
			*/
		}
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName(_RULE_NAME+"UPDATEACCRUALCODE");
		coaVo.setCoaType(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED);
		coaVo.setStoredObject(vo.getCostDataVo());
		dialogueVo.getProcessedCourseOfActionVos().add(coaVo);
		
		return _OK;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.rules.IRule#executePostProcessActions(gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@Override
	public void executePostProcessActions(DialogueVo dialogueVo) throws Exception {
		/*
		CourseOfActionVo coaVo = dialogueVo.getCourseOfActionByName(ruleName+"UPDATEACCRUALCODE");
		if(coaVo != null && coaVo.getCoaType().equals(CourseOfActionTypeEnum.ADDITIONAL_ACTION_NEEDED)){
			if(null != coaVo.getStoredObject()){
				CostDataVo cvo = (CostDataVo)coaVo.getStoredObject();
				
				// update
				Long id=cvo.getId();
				Long accrualCodeId=cvo.getAccrualCodeVo().getId();
				
				
			}
		}
		*/
	}

}
