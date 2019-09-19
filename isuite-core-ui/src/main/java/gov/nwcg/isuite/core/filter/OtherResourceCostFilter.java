package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface OtherResourceCostFilter extends Filter {
	
	public Long getIncidentId();
	public void setIncidentId(Long incidentId); 
	
	public Long getIncidentGroupId();
	public void setIncidentGroupId(Long incidentGroupId);
	
	public Long getIncidentResourceOtherId();
	public void setIncidentResourceOtherId(Long incidentResourceOtherId);
	
	public String getRequestNumber();
	public void setRequestNumber(String requestNumber);
	
	public String getCostDescription();
	public void setCostDescription(String costDescription);
	
	public String getItemName();
	public void setItemName(String itemName);
	
	public String getItemCode();
	public void setItemCode(String itemCode);
	
	public String getAgency();
	public void setAgency(String agency);
	
	public String getPaymentAgency();
	public void setPaymentAgency(String paymentAgency);
	
	public Date getAssignDate();
	public void setAssignDate(Date assignDate);
	
	public Date getActualReleaseDate();
	public void setActualReleaseDate(Date actualReleaseDate);
	
	public String getStatus();
	public void setStatus(String status);
	
	public String getRemarks();
	public void setRemarks(String remarks);
	
	public String getAccrualCode();
	public void setAccrualCode(String accrualCode);
	
	public String getIncidentName();
	public void setIncidentName(String incidentName);
	
	public String getIncidentNumber();
	public void setIncidentNumber(String incidentNumber);
	
	public String getCrypticDateFilterAssignDate();
	public void setCrypticDateFilterAssignDate(String crypticDateFilterAssignDate);
	
	public String getCrypticDateFilterActualReleaseDate();
	public void setCrypticDateFilterActualReleaseDate(String crypticDateFilterActualReleaseDate);
}
