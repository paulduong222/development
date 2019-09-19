package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public interface ScratchReportTime {//extends Persistable {

	/**
	 * @return the transactionId
	 */
	public Long getTransactionId();

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(Long transactionId);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incident to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	    * @return the incidentName
	 */
	public String getIncidentName();
	   
	/**
	    * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	
	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate();

	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the incidentGroupName
	 */
	public String getIncidentGroupName();

	/**
	 * @param incidentGroupName the incidentGroupName to set
	 */
	public void setIncidentGroupName(String incidentGroupName);
	
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber();

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber);
	
	/**
	 * @return the ResourceId
	 */
	public Long getResourceId();

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId);
	
	/**
	 * @return the resourceLastName
	 */
	public String getResourceLastName();

	/**
	 * @param resourceLastName the resourceLastName to set
	 */
	public void setResourceLastName(String resourceLastName);
	
	/**
	 * @return the resourceFirstName
	 */
	public String getResourceFirstName();

	/**
	 * @param resourceFirstName the resourceFirstName to set
	 */
	public void setResourceFirstName(String resourceFirstName);
	
	/**
	 * @return if Resource is contracted
	 */
	public Boolean getContracted();
	
	/**
	 * @param contracted the contracted value to set
	 */
	public void setContracted(Boolean contracted);
	
	/**
	 * @return the itemCode
	 */
	public String getItemCode();

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode);
	
	/**
	 * @return the sectionCode
	 */
	public String getSectionCode();

	/**
	 * @param sectionCode the sectionCode to set
	 */
	public void setSectionCode(String sectionCode);
	
	/**
	 * @return the sectionName
	 */
	public String getSectionName();

	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName);
	
	/**
	 * @return the status
	 */
	public String getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
	
	/**
	 * @return the shiftStartDate
	 */
	public Date getShiftStartDate();

	/**
	 * @param shiftStartDate the shiftStartDate to set
	 */
	public void setShiftStartDate(Date shiftStartDate);
	
	/**
	 * @return the shiftEndDate
	 */
	public Date getShiftEndDate();
	
	/**
	 * @param shiftEndDate the shiftEndDate to set
	 */
	public void setShiftEndDate(Date shiftEndDate);
	
	/**
	 * @return the hoursWork
	 */
	public BigDecimal getHoursOfWork();

	/**
	 * @param hoursWork the hoursWork to set
	 */
	public void setHoursOfWork(BigDecimal hoursOfWork);
	
	/**
	 * @return the hoursRest
	 */
	public BigDecimal getHoursOfRest();

	/**
	 * @param hoursRest the hoursRest to set
	 */
	public void setHoursOfRest(BigDecimal hoursOfRest);
	
	/**
	 * @return the mitigationNeeded
	 */
	public BigDecimal getMitigationNeeded();

	/**
	 * @param mitigationNeeded the mitigationNeeded to set
	 */
	public void setMitigationNeeded(BigDecimal mitigationNeeded);
	
	/**
	 * @return the CreatedDate
	 */
	public Date getCreatedDate();

	/**
	 * @param CreatedDate the CreatedDate to set
	 */
	public void setCreatedDate(Date createdDate);
	
	public String getIncidentNumber();

	public void setIncidentNumber(String incidentNumber);
	
}
