package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface CostGroupFilter extends Filter {
	
	/**
	 * @return incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return incidentGroupId
	 */
	public Long getIncidentGroupId();
	
	/**
	 * @param incidentGroupId
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return costGroupName
	 */
	public String getCostGroupName();

	/**
	 * @param costGroupName
	 */
	public void setCostGroupName(String costGroupName);
	
	/**
	 * @return startDate
	 */
	public Date getStartDate() ;
	
	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate);
	
	/**
	 * @return crypticDateFilterCodeForStartDate
	 */
	public String getCrypticDateFilterCodeForStartDate();
	
	/**
	 * @param crypticDateFilterCodeForStartDate
	 */
	public void setCrypticDateFilterCodeForStartDate(String crypticDateFilterCodeForStartDate);
	
	/**
	 * @return description
	 */
	public String getDescription();
	
	/**
	 * @param description
	 */
	public void setDescription(String description);
	
	/**
	 * @return shift
	 */
	public String getShift();
	
	/**
	 * @param shift
	 */
	public void setShift(String shift);
	
	/**
	 * @return incidentName
	 */
	public String getIncidentName();

	/**
	 * @param incidentName
	 */
	public void setIncidentName(String incidentName);
	
}
