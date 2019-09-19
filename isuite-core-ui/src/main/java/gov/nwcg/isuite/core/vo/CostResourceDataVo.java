package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.util.BigDecimalUtility;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.Date;

public class CostResourceDataVo {
	private Long incidentResourceId;
	private Long incidentResourceOtherId;
	private Long resourceId;
	private Long resourceOtherId;
	private Long assignmentTimeId;
	private Long costDataId;
	private Long costGroupId;
	private Long shiftId;
	private Long contractorPaymentInfoId;
	private Long assignmentId;
	private String status;
	private Long agencyId;
	private String agencyCode;
	private String agencyType;
	private String rateGroupType;
	private Long kindId;
	private String kindCode;
	private Long defIncidentAccountCodeId;
	private String accountCode;
	private Date assignDate;
	private Date releaseDate;
	private Date arrivalDate;
	private Boolean useActualsOnly;
	private Boolean generateCosts;
	private String generateCostsString;
	private Boolean generateCostsSys;
	private String generateCostsSysString;
	private String dailyFormCode;
	private String employmentType;
	private Long parentResourceId;
	private Integer subordinateCount;
	private Integer defaultHours;
	private String accrualCode;
	private Long accrualCodeId;
	//private String estimateRateType;
	//private BigDecimal estimateRate;
	private Integer estimateUnits;
	//private BigDecimal estimateStateCustomRate;
	//private String estimateStateCustomRateType;
	private Integer estimateStateCustomUnits;
	//private String actualRateType;
	//private BigDecimal actualRate;
	private BigDecimal actualOtherRate;
	private Integer actualUnits;
	private Integer timePostCount;
	private Integer adjustmentCount;
	private String resourceCostType;
	private Integer subordinateTimePostCount;
	private Integer subordinateTimeAdjustmentCount;
	private BigDecimal contractorRate;
	private BigDecimal fedRate;
	private BigDecimal stateRate;
	private BigDecimal stateCustomRate;
	private String contractorRateUom;
	private String fedRateUom;
	private String stateRateUom;
	private String stateCustomRateUom;
	private Date endDate;
	private Date incidentStartDate;
	
	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}
	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}
	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}
	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	/**
	 * @return the agencyCode
	 */
	public String getAgencyCode() {
		if(StringUtility.hasValue(agencyCode))
			return agencyCode;
		else
			return "";
	}
	
	/**
	 * @param agencyCode the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}
	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}
	/**
	 * @return the kindCode
	 */
	public String getKindCode() {
		return kindCode;
	}
	/**
	 * @param kindCode the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}
	/**
	 * @return the defIncidentAccountCodeId
	 */
	public Long getDefIncidentAccountCodeId() {
		return defIncidentAccountCodeId;
	}
	/**
	 * @param defIncidentAccountCodeId the defIncidentAccountCodeId to set
	 */
	public void setDefIncidentAccountCodeId(Long defIncidentAccountCodeId) {
		this.defIncidentAccountCodeId = defIncidentAccountCodeId;
	}
	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}
	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}
	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}
	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}
	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate(){
		return endDate;
	}
	/**
	 * @param endDate the incident endDate to set
	 */
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
	/**
	 * @return the useActualsOnly
	 */
	public Boolean getUseActualsOnly() {
		return useActualsOnly;
	}
	/**
	 * @param useActualsOnly the useActualsOnly to set
	 */
	public void setUseActualsOnly(Boolean useActualsOnly) {
		this.useActualsOnly = useActualsOnly;
	}
	/**
	 * @return the generateCosts
	 */
	public Boolean getGenerateCosts() {
		if(StringUtility.hasValue(this.generateCostsString) && generateCostsString.equalsIgnoreCase("true"))
			return true;
		else
			return false;
	}
	
	/**
	 * @param generateCosts the generateCosts to set
	 */
	public void setGenerateCosts(Boolean generateCosts) {
		this.generateCosts = generateCosts;
	}
	/**
	 * @return the dailyFormCode
	 */
	public String getDailyFormCode() {
		return dailyFormCode;
	}
	/**
	 * @param dailyFormCode the dailyFormCode to set
	 */
	public void setDailyFormCode(String dailyFormCode) {
		this.dailyFormCode = dailyFormCode;
	}
	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		if(StringUtility.hasValue(employmentType))
			return employmentType;
		else
			return "";
	}
	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	/**
	 * @return the parentResourceId
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}
	/**
	 * @param parentResourceId the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}
	/**
	 * @return the subordinateCount
	 */
	public Integer getSubordinateCount() {
		return subordinateCount;
	}
	/**
	 * @param subordinateCount the subordinateCount to set
	 */
	public void setSubordinateCount(Integer subordinateCount) {
		this.subordinateCount = subordinateCount;
	}
	/**
	 * @return the arrivalDate
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}
	/**
	 * @param arrivalDate the arrivalDate to set
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	/**
	 * @return the defaultHours
	 */
	public Integer getDefaultHours() {
		return defaultHours;
	}
	/**
	 * @param defaultHours the defaultHours to set
	 */
	public void setDefaultHours(Integer defaultHours) {
		this.defaultHours = defaultHours;
	}
	/**
	 * @return the accrualCode
	 */
	public String getAccrualCode() {
		return accrualCode;
	}
	/**
	 * @param accrualCode the accrualCode to set
	 */
	public void setAccrualCode(String accrualCode) {
		this.accrualCode = accrualCode;
	}
	/**
	 * @return the accrualCodeId
	 */
	public Long getAccrualCodeId() {
		return accrualCodeId;
	}
	/**
	 * @param accrualCodeId the accrualCodeId to set
	 */
	public void setAccrualCodeId(Long accrualCodeId) {
		this.accrualCodeId = accrualCodeId;
	}
	
	/**
	 * @param estimateUnits the estimateUnits to set
	 */
	public void setEstimateUnits(Integer estimateUnits) {
		this.estimateUnits = estimateUnits;
	}
	/**
	 * @return the agencyType
	 */
	public String getAgencyType() {
		return agencyType;
	}
	/**
	 * @param agencyType the agencyType to set
	 */
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	/**
	 * @return the estimateStateCustomUnits
	 */
	public Integer getEstimateStateCustomUnits() {
		return estimateStateCustomUnits;
	}
	/**
	 * @param estimateStateCustomUnits the estimateStateCustomUnits to set
	 */
	public void setEstimateStateCustomUnits(Integer estimateStateCustomUnits) {
		this.estimateStateCustomUnits = estimateStateCustomUnits;
	}
	
	/**
	 * @return the actualUnits
	 */
	public Integer getActualUnits() {
		if(StringUtility.hasValue(this.employmentType) && this.employmentType.equals("FED")){
			if(this.getRateType().equals("HOURLY"))
				return this.defaultHours;
			else
				return 1;
		}
		
		if(!IntegerUtility.hasValue(this.actualUnits))
			return actualUnits;
		else
			return this.defaultHours;
	}
	
	/**
	 * @param actualUnits the actualUnits to set
	 */
	public void setActualUnits(Integer actualUnits) {
		this.actualUnits = actualUnits;
	}
	/**
	 * @return the timePostCount
	 */
	public Integer getTimePostCount() {
		return timePostCount;
	}
	/**
	 * @param timePostCount the timePostCount to set
	 */
	public void setTimePostCount(Integer timePostCount) {
		this.timePostCount = timePostCount;
	}
	/**
	 * @return the adjustmentCount
	 */
	public Integer getAdjustmentCount() {
		return adjustmentCount;
	}
	/**
	 * @param adjustmentCount the adjustmentCount to set
	 */
	public void setAdjustmentCount(Integer adjustmentCount) {
		this.adjustmentCount = adjustmentCount;
	}
	/**
	 * @return the assignmentTimeId
	 */
	public Long getAssignmentTimeId() {
		return assignmentTimeId;
	}
	/**
	 * @param assignmentTimeId the assignmentTimeId to set
	 */
	public void setAssignmentTimeId(Long assignmentTimeId) {
		this.assignmentTimeId = assignmentTimeId;
	}
	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}
	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}
	/**
	 * @return the actualOtherRate
	 */
	public BigDecimal getActualOtherRate() {
		return actualOtherRate;
	}
	/**
	 * @param actualOtherRate the actualOtherRate to set
	 */
	public void setActualOtherRate(BigDecimal actualOtherRate) {
		this.actualOtherRate = actualOtherRate;
	}
	/**
	 * @return the resourceCostType
	 */
	public String getResourceCostType() {
		String type="RESOURCE";
		if(LongUtility.hasValue(this.incidentResourceId)){
			if(StringUtility.hasValue(this.dailyFormCode) && this.dailyFormCode.equals("A")){
				type="AIRCRAFT";
			}
		}else if(LongUtility.hasValue(this.incidentResourceOtherId)){
			type="RESOURCE_OTHER";
		}
		
		return type;
	}
	/**
	 * @param resourceCostType the resourceCostType to set
	 */
	public void setResourceCostType(String resourceCostType) {
		this.resourceCostType=resourceCostType;
	}
	/**
	 * @return the incidentResourceOtherId
	 */
	public Long getIncidentResourceOtherId() {
		return incidentResourceOtherId;
	}
	/**
	 * @param incidentResourceOtherId the incidentResourceOtherId to set
	 */
	public void setIncidentResourceOtherId(Long incidentResourceOtherId) {
		this.incidentResourceOtherId = incidentResourceOtherId;
	}
	/**
	 * @return the contractorPaymentInfoId
	 */
	public Long getContractorPaymentInfoId() {
		return contractorPaymentInfoId;
	}
	/**
	 * @param contractorPaymentInfoId the contractorPaymentInfoId to set
	 */
	public void setContractorPaymentInfoId(Long contractorPaymentInfoId) {
		this.contractorPaymentInfoId = contractorPaymentInfoId;
	}
	/**
	 * @return the subordinateTimePostCount
	 */
	public Integer getSubordinateTimePostCount() {
		return subordinateTimePostCount;
	}
	/**
	 * @param subordinateTimePostCount the subordinateTimePostCount to set
	 */
	public void setSubordinateTimePostCount(Integer subordinateTimePostCount) {
		this.subordinateTimePostCount = subordinateTimePostCount;
	}
	/**
	 * @return the subordinateTimeAdjustmentCount
	 */
	public Integer getSubordinateTimeAdjustmentCount() {
		return subordinateTimeAdjustmentCount;
	}
	/**
	 * @param subordinateTimeAdjustmentCount the subordinateTimeAdjustmentCount to set
	 */
	public void setSubordinateTimeAdjustmentCount(
			Integer subordinateTimeAdjustmentCount) {
		this.subordinateTimeAdjustmentCount = subordinateTimeAdjustmentCount;
	}
	/**
	 * @return the costDataId
	 */
	public Long getCostDataId() {
		return costDataId;
	}
	/**
	 * @param costDataId the costDataId to set
	 */
	public void setCostDataId(Long costDataId) {
		this.costDataId = costDataId;
	}
	/**
	 * @return the costGroupId
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}
	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}
	/**
	 * @return the shiftId
	 */
	public Long getShiftId() {
		return shiftId;
	}
	/**
	 * @param shiftId the shiftId to set
	 */
	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}
	/**
	 * @return the resourceOtherId
	 */
	public Long getResourceOtherId() {
		return resourceOtherId;
	}
	/**
	 * @param resourceOtherId the resourceOtherId to set
	 */
	public void setResourceOtherId(Long resourceOtherId) {
		this.resourceOtherId = resourceOtherId;
	}
	/**
	 * @return the generateCostsSys
	 */
	public Boolean getGenerateCostsSys() {
		if(StringUtility.hasValue(this.generateCostsSysString) && generateCostsSysString.equals("Y"))
			return true;
		else
			return false;
		//return generateCostsSys;
	}
	/**
	 * @param generateCostsSys the generateCostsSys to set
	 */
	public void setGenerateCostsSys(Boolean generateCostsSys) {
		this.generateCostsSys = generateCostsSys;
	}
	/**
	 * @return the generateCostsSysString
	 */
	public String getGenerateCostsSysString() {
		return generateCostsSysString;
	}
	/**
	 * @param generateCostsSysString the generateCostsSysString to set
	 */
	public void setGenerateCostsSysString(String generateCostsSysString) {
		this.generateCostsSysString = generateCostsSysString;
	}
	/**
	 * @return the generateCostsString
	 */
	public String getGenerateCostsString() {
		return generateCostsString;
	}
	/**
	 * @param generateCostsString the generateCostsString to set
	 */
	public void setGenerateCostsString(String generateCostsString) {
		this.generateCostsString = generateCostsString;
	}
	/**
	 * @return the rateGroupType
	 */
	public String getRateGroupType() {
		if(!StringUtility.hasValue(this.rateGroupType))
			return "";
		
		return rateGroupType;
	}
	/**
	 * @param rateGroupType the rateGroupType to set
	 */
	public void setRateGroupType(String rateGroupType) {
		this.rateGroupType = rateGroupType;
	}
	/**
	 * @return the contractorRate
	 */
	public BigDecimal getContractorRate() {
		if(DecimalUtil.hasValue(contractorRate))
			return contractorRate;
		else
			return BigDecimal.valueOf(0.0);
	}
	/**
	 * @param contractorRate the contractorRate to set
	 */
	public void setContractorRate(BigDecimal contractorRate) {
		this.contractorRate = contractorRate;
	}
	/**
	 * @return the fedRate
	 */
	public BigDecimal getFedRate() {
		if(DecimalUtil.hasValue(fedRate))
			return fedRate;
		else
			return BigDecimal.valueOf(0.0);
	}
	/**
	 * @param fedRate the fedRate to set
	 */
	public void setFedRate(BigDecimal fedRate) {
		this.fedRate = fedRate;
	}
	/**
	 * @return the stateRate
	 */
	public BigDecimal getStateRate() {
		if(DecimalUtil.hasValue(stateRate))
			return stateRate;
		else
			return BigDecimal.valueOf(0.0);
	}
	/**
	 * @param stateRate the stateRate to set
	 */
	public void setStateRate(BigDecimal stateRate) {
		this.stateRate = stateRate;
	}
	/**
	 * @return the stateCustomRate
	 */
	public BigDecimal getStateCustomRate() {
		if(DecimalUtil.hasValue(stateCustomRate))
			return stateCustomRate;
		else
			return BigDecimal.valueOf(0.0);
	}
	/**
	 * @param stateCustomRate the stateCustomRate to set
	 */
	public void setStateCustomRate(BigDecimal stateCustomRate) {
		this.stateCustomRate = stateCustomRate;
	}
	/**
	 * @return the contractorRateUom
	 */
	public String getContractorRateUom() {
		if(StringUtility.hasValue(contractorRateUom))
			return contractorRateUom;
		else
			return "DAILY";
	}
	/**
	 * @param contractorRateUom the contractorRateUom to set
	 */
	public void setContractorRateUom(String contractorRateUom) {
		this.contractorRateUom = contractorRateUom;
	}
	/**
	 * @return the fedRateUom
	 */
	public String getFedRateUom() {
		if(StringUtility.hasValue(fedRateUom))
			return fedRateUom;
		else
			return "DAILY";
	}
	
	/**
	 * @param fedRateUom the fedRateUom to set
	 */
	public void setFedRateUom(String fedRateUom) {
		this.fedRateUom = fedRateUom;
	}
	/**
	 * @return the stateRateUom
	 */
	public String getStateRateUom() {
		if(StringUtility.hasValue(stateRateUom))
			return stateRateUom;
		else
			return "DAILY";
	}
	/**
	 * @param stateRateUom the stateRateUom to set
	 */
	public void setStateRateUom(String stateRateUom) {
		this.stateRateUom = stateRateUom;
	}
	/**
	 * @return the stateCustomRateUom
	 */
	public String getStateCustomRateUom() {
		if(StringUtility.hasValue(stateCustomRateUom))
			return stateCustomRateUom;
		else
			return "DAILY";
	}
	/**
	 * @param stateCustomRateUom the stateCustomRateUom to set
	 */
	public void setStateCustomRateUom(String stateCustomRateUom) {
		this.stateCustomRateUom = stateCustomRateUom;
	}

	public BigDecimal getRate() {
		
		if(this.getRateGroupType().equals("FED")){
			return this.getFedRate();
		}else if(this.getRateGroupType().equals("CONT")){
			return this.getContractorRate();
		}else if(this.getRateGroupType().equals("ST")){
			//if(this.isAgencyCityRurCounty()==true){
				if(DecimalUtil.hasValue(this.getStateCustomRate())){
					return this.getStateCustomRate();
				}else
					return this.getStateRate();
			//}
		}
		
		return BigDecimal.valueOf(0.0);
	}
	
	public BigDecimal getActualRate() {

		if(this.getEmploymentType().equals("AD")){
			
		}else{
			if(this.getRateGroupType().equals("FED")){
				return this.getFedRate();
			}else if(this.getRateGroupType().equals("CONT")){
				return this.getContractorRate();
			}else if(this.getRateGroupType().equals("ST")){
				//if(this.isAgencyCityRurCounty()==true){
					if(DecimalUtil.hasValue(this.getStateCustomRate())){
						return this.getStateCustomRate();
					}else
						return this.getStateRate();
				//}
			}
		}
		
		return BigDecimal.valueOf(0.0);
	}

	public String getRateType() {
		
		if(this.getRateGroupType().equals("FED")){
			return this.getFedRateUom();
		}else if(this.getRateGroupType().equals("CONT")){
			return this.getContractorRateUom();
		}else if(this.getRateGroupType().equals("ST")){
			//if(this.isAgencyCityRurCounty()==true){
				if(DecimalUtil.hasValue(this.getStateCustomRate())){
					return this.getStateCustomRateUom();
				}else
					return this.getStateRateUom();
			//}
		}
		
		return "HOURLY";
	}

	/**
	 * @return the estimateUnits
	 */
	public Integer getUnits() {
		Integer rtnVal=Integer.valueOf(0);
		
		if(!this.getRateType().equals("HOURLY")){
			rtnVal=Integer.valueOf(1);
		}
		
		if(IntegerUtility.hasValue(estimateUnits)){
			rtnVal=estimateUnits;
		}else{
			if(StringUtility.hasValue(this.getRateType())){
				if(this.getRateType().equals("HOURLY"))
					rtnVal=this.defaultHours;
				else 
					rtnVal=Integer.valueOf(1);
			}
		}
		
		return rtnVal;
	}

	public Boolean isAgencyCityRurCounty(){
		if(this.getAgencyCode().equals("RUR") 
				|| this.getAgencyCode().equals("CITY")
				|| this.getAgencyCode().equals("CNTY")) {
			return true;
		}else
			return false;
	}

	public Boolean isDailyFormAircraft() {
		if(StringUtility.hasValue(this.dailyFormCode)){
			if(this.dailyFormCode.equals("A"))
				return true;
		}
		return false;
	}
	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}
	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}
}
