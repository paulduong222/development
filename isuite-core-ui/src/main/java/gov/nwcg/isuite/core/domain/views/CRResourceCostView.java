package gov.nwcg.isuite.core.domain.views;

import java.math.BigDecimal;
import java.util.Date;

public interface CRResourceCostView {
	public Long getIncidentId();

	public void setIncidentId(Long incidentId);

	public String getIncidentName();
 
	public void setIncidentName(String incidentName);

	public String getIncidentNumber();

	public void setIncidentNumber(String incidentNumber);

	public String getAccountCode();

	public void setAccountCode(String accountCode);

	public String getAccrual();

	public void setAccrual(String accrual);

	public String getAccrualDescription();

	public void setAccrualDescription(String accrualDescription);

	public Date getActivityDate();

	public void setActivityDate(Date activityDate);

	public String getAgencyCode();

	public void setAgencyCode(String agencyCode);

	public String getAgencyDescription();

	public void setAgencyDescription(String agencyDescription);

	public BigDecimal getAircraftCost();

	public void setAircraftCost(BigDecimal aircraftCost);

	public String getCostGroup();

	public void setCostGroup(String costGroup);

	public String getCostGroupDescription();

	public void setCostGroupDescription(String costGroupDescription);

	public String getCostUOM();

	public void setCostUOM(String costUOM);

	public String getDirect();

	public void setDirect(String direct);

	public BigDecimal getFlightHours();

	public void setFlightHours(BigDecimal flightHours);

	public BigDecimal getGrandTotalCost();

	public void setGrandTotalCost(BigDecimal grandTotalCost);

	//public String getGroupCode();

	//public void setGroupCode(String groupCode);

	public String getItemCode();

	public void setItemCode(String itemCode);

	public String getItemDescription();

	public void setItemDescription(String itemDescription);

	public BigDecimal getLbsCargo();

	public void setLbsCargo(BigDecimal lbsCargo);

	public Integer getNumberOfTrips();

	public void setNumberOfTrips(Integer numberOfTrips);

	public Long getParentId();

	public void setParentId(Long parentId);

	public Integer getPassenger();

	public void setPassenger(Integer passenger);

	public String getPaymentAgencyCode();

	public void setPaymentAgencyCode(String paymentAgencyCode);

	public String getPaymentAgencyDescription();

	public void setPaymentAgencyDescription(String paymentAgencyDescription);

	public BigDecimal getPrimaryCost();

	public void setPrimaryCost(BigDecimal primaryCost);

	public String getRequestNumber();

	public void setRequestNumber(String requestNumber);

	public String getResourceName();

	public void setResourceName(String resourceName);

	public BigDecimal getRetardantGal();

	public void setRetardantGal(BigDecimal retardantGal);

	public String getSectionCode();

	public void setSectionCode(String sectionCode);

	public String getSectionDescription();

	public void setSectionDescription(String sectionDescription);

	public String getShift();

	public void setShift(String shift);

	//public String getSubGroupCode();

	//public void setSubGroupCode(String subGroupCode);

	public BigDecimal getSubordinateCost();

	public void setSubordinateCost(BigDecimal subordinateCost);

	public BigDecimal getUnitCost();

	public void setUnitCost(BigDecimal unitCost);

	public String getUnitDescription();

	public void setUnitDescription(String unitDescription);

	public String getUnitId();

	public void setUnitId(String unitId);

	public BigDecimal getUnits();

	public void setUnits(BigDecimal units);

	public BigDecimal getWaterGal();

	public void setWaterGal(BigDecimal waterGal);
}