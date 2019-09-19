package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface CostRateFilter extends Filter {

	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() ;

	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory);
	
	/**
	 * @return column filter object
	 */
	public Object getSubFilter();
	
	/**
	 * @param subFilter; the column filter object to set
	 */
	public void setSubFilter(Object subFilter);
	
	/**
	 * 
	 * @return
	 */
	public Long getIncidentId();
	
	/**
	 * 
	 * @param incidentId
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * 
	 * @return
	 */
	public Long getIncidentGroupId();
	
	/**
	 * 
	 * @param incidentId
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	/**
	 * @return the advancedSearch
	 */
	public String getAdvancedSearch();

	/**
	 * @param advancedSearch the advancedSearch to set
	 */
	public void setAdvancedSearch(String advancedSearch);

	public String getCostRateCategory() ;
	
	public void setCostRateCategory(String costRateCategory);
	
	public void setAgencyId(Long id);
	
	public Long getAgencyId();
	
}
