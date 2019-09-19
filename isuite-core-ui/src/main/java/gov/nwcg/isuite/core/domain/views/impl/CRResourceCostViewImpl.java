package gov.nwcg.isuite.core.domain.views.impl;

import gov.nwcg.isuite.core.domain.views.CRResourceCostView;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iswv_cr_resource_cost")
public class CRResourceCostViewImpl implements CRResourceCostView{
	
	@Id 
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false)
	private Long incidentId;

	@Column(name = "INCIDENT_NAME", insertable = false, updatable = false)
	private String incidentName;

	@Column(name = "INCIDENT_NUMBER", insertable = false, updatable = false)
	private String incidentNumber;
	
	@Id
	@Column(name = "ACCOUNTING_CODE", insertable = false, updatable = false)
	private String accountCode;
	
	@Column(name = "ACCRUAL_CODE", insertable = false, updatable = false)
	private String accrual;
	
	@Column(name = "ACCRUAL_DESCRIPTION", insertable = false, updatable = false)
	private String accrualDescription;
	
	@Column(name="ACTIVITY_DATE", insertable = false, updatable = false)
	private Date activityDate;
	
	@Column(name = "AGENCY_CODE", insertable = false, updatable = false)
	private String agencyCode;
	
	@Column(name = "AGENCY_DESCRIPTION", insertable = false, updatable = false)
	private String agencyDescription;
	
	@Column(name = "AIRCRAFT_COST", insertable = false, updatable = false)
	private BigDecimal aircraftCost;
	
	@Column(name = "COST_GROUP", insertable = false, updatable = false)
	private String costGroup;
	
	@Column(name = "COST_GROUP_DESCRIPTION", insertable = false, updatable = false)
	private String costGroupDescription;
	
	@Column(name = "COST_UOM", insertable = false, updatable = false)
	private String costUOM;
	
	@Column(name = "IS_DIRECT", insertable = false, updatable = false)
	private String direct;

	@Column(name = "FLIGHT_HRS", insertable = false, updatable = false)
	private BigDecimal flightHours;

	@Column(name = "GRAND_TOTAL_COST", insertable = false, updatable = false)
	private BigDecimal grandTotalCost;

	///@Column(name = "GROUP_CODE", insertable = false, updatable = false)
	//private String groupCode;
	
	@Column(name = "ITEM_CODE", insertable = false, updatable = false)
	private String itemCode;

	@Column(name = "ITEM_DESCRIPTION", insertable = false, updatable = false)
	private String itemDescription;

	@Column(name = "LBS_CARGO", insertable = false, updatable = false)
	private BigDecimal lbsCargo;
	
	@Column(name = "NUM_TRIPS", insertable = false, updatable = false)
	private Integer numberOfTrips;

	@Column(name = "PARENT_ID", insertable = false, updatable = false)
	private Long parentId;
	
	@Column(name = "PASSENGER", insertable = false, updatable = false)
	private Integer passenger;
	
	@Column(name = "PAYMENT_AGENCY_CODE", insertable = false, updatable = false)
	private String paymentAgencyCode;

	@Column(name = "PAYMENT_AGENCY_DESCRIPTION", insertable = false, updatable = false)
	private String paymentAgencyDescription;

	@Column(name = "PRIMARY_COST", insertable = false, updatable = false)
	private BigDecimal primaryCost;

	@Column(name = "REQUEST_NUMBER", insertable = false, updatable = false)
	private String requestNumber;

	@Column(name = "RESOURCE_NAME", insertable = false, updatable = false)
	private String resourceName;

	@Column(name = "RETARDANT_GAL", insertable = false, updatable = false)
	private BigDecimal retardantGal;

	@Column(name = "SECTION_CODE", insertable = false, updatable = false)
	private String sectionCode;

	@Column(name = "SECTION_DESCRIPTION", insertable = false, updatable = false)
	private String sectionDescription;

	@Column(name = "SHIFT", insertable = false, updatable = false)
	private String shift;

	//@Column(name = "SUB_GROUP_CODE", insertable = false, updatable = false)
	//private String subGroupCode;

	@Column(name = "SUBORDINATE_COST", insertable = false, updatable = false)
	private BigDecimal subordinateCost;

	@Column(name = "UNIT_COST", insertable = false, updatable = false)
	private BigDecimal unitCost;

	@Column(name = "UNIT_DESCRIPTION", insertable = false, updatable = false)
	private String unitDescription;

	@Column(name = "UNIT_ID", insertable = false, updatable = false)
	private String unitId;

	@Column(name = "UNITS", insertable = false, updatable = false)
	private BigDecimal units;

	@Column(name = "WATER_GAL", insertable = false, updatable = false)
	private BigDecimal waterGal;
	
	public CRResourceCostViewImpl() {
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccrual() {
		return accrual;
	}

	public void setAccrual(String accrual) {
		this.accrual = accrual;
	}

	public String getAccrualDescription() {
		return accrualDescription;
	}

	public void setAccrualDescription(String accrualDescription) {
		this.accrualDescription = accrualDescription;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getAgencyDescription() {
		return agencyDescription;
	}

	public void setAgencyDescription(String agencyDescription) {
		this.agencyDescription = agencyDescription;
	}

	public BigDecimal getAircraftCost() {
		return aircraftCost;
	}

	public void setAircraftCost(BigDecimal aircraftCost) {
		this.aircraftCost = aircraftCost;
	}

	public String getCostGroup() {
		return costGroup;
	}

	public void setCostGroup(String costGroup) {
		this.costGroup = costGroup;
	}

	public String getCostGroupDescription() {
		return costGroupDescription;
	}

	public void setCostGroupDescription(String costGroupDescription) {
		this.costGroupDescription = costGroupDescription;
	}

	public String getCostUOM() {
		return costUOM;
	}

	public void setCostUOM(String costUOM) {
		this.costUOM = costUOM;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public BigDecimal getFlightHours() {
		return flightHours;
	}

	public void setFlightHours(BigDecimal flightHours) {
		this.flightHours = flightHours;
	}

	public BigDecimal getGrandTotalCost() {
		return grandTotalCost;
	}

	public void setGrandTotalCost(BigDecimal grandTotalCost) {
		this.grandTotalCost = grandTotalCost;
	}

//	public String getGroupCode() {
///		return groupCode;
//	}

//	public void setGroupCode(String groupCode) {
//		this.groupCode = groupCode;
//	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public BigDecimal getLbsCargo() {
		return lbsCargo;
	}

	public void setLbsCargo(BigDecimal lbsCargo) {
		this.lbsCargo = lbsCargo;
	}

	public Integer getNumberOfTrips() {
		return numberOfTrips;
	}

	public void setNumberOfTrips(Integer numberOfTrips) {
		this.numberOfTrips = numberOfTrips;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getPassenger() {
		return passenger;
	}

	public void setPassenger(Integer passenger) {
		this.passenger = passenger;
	}

	public String getPaymentAgencyCode() {
		return paymentAgencyCode;
	}

	public void setPaymentAgencyCode(String paymentAgencyCode) {
		this.paymentAgencyCode = paymentAgencyCode;
	}

	public String getPaymentAgencyDescription() {
		return paymentAgencyDescription;
	}

	public void setPaymentAgencyDescription(String paymentAgencyDescription) {
		this.paymentAgencyDescription = paymentAgencyDescription;
	}

	public BigDecimal getPrimaryCost() {
		return primaryCost;
	}

	public void setPrimaryCost(BigDecimal primaryCost) {
		this.primaryCost = primaryCost;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public BigDecimal getRetardantGal() {
		return retardantGal;
	}

	public void setRetardantGal(BigDecimal retardantGal) {
		this.retardantGal = retardantGal;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSectionDescription() {
		return sectionDescription;
	}

	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

//	public String getSubGroupCode() {
//		return subGroupCode;
//	}

//	public void setSubGroupCode(String subGroupCode) {
//		this.subGroupCode = subGroupCode;
//	}

	public BigDecimal getSubordinateCost() {
		return subordinateCost;
	}

	public void setSubordinateCost(BigDecimal subordinateCost) {
		this.subordinateCost = subordinateCost;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getWaterGal() {
		return waterGal;
	}

	public void setWaterGal(BigDecimal waterGal) {
		this.waterGal = waterGal;
	}
}
