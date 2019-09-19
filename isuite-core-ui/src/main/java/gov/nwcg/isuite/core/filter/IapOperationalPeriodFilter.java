package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface IapOperationalPeriodFilter extends Filter {
	
	/**
	 * @return incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return name
	 */
	public String getName();
	
	/**
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * @return startTime
	 */
	public String getStartTime();
	
	/**
	 * @param startTime
	 */
	public void setStartTime(String startTime);
	
	/**
	 * @return endTime
	 */
	public String getEndTime();
	
	/**
	 * @param endTime
	 */
	public void setEndTime(String endTime);
	
	/**
	 * @return font
	 */
	public String getFont();
	
	/**
	 * @param font
	 */
	public void setFont(String font);

}
