package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface FinancialExportFilter extends Filter {
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

}
