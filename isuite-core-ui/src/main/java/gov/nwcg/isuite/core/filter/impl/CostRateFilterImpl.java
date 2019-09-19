package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CostRateFilterImpl extends FilterImpl implements CostRateFilter {

	private static final long serialVersionUID = -1368817533227151877L;

	private Long incidentId;
	private Long incidentGroupId;
	private String requestCategory=null;
	private Object subFilter;
	private String advancedSearch;
	private String costRateCategory;
	private Long agencyId;
	
	public CostRateFilterImpl(){

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateFilter#getRequestCategory()
	 */
	@Override
	public String getRequestCategory() {
		return requestCategory;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateFilter#setRequestCategory(java.lang.String)
	 */
	@Override
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateFilter#getSubFilter()
	 */

	@Override
	public Object getSubFilter() {
		return subFilter;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostRateFilter#setSubFilter(java.lang.Object)
	 */

	@Override
	public void setSubFilter(Object subFilter) {
		this.subFilter = subFilter;    
	}

	@Override
	public Long getIncidentId() {
		return this.incidentId;
	}

	@Override
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the advancedSearch
	 */
	public String getAdvancedSearch() {
		return advancedSearch;
	}

	/**
	 * @param advancedSearch the advancedSearch to set
	 */
	public void setAdvancedSearch(String advancedSearch) {
		this.advancedSearch = advancedSearch;
	}

	public String getCostRateCategory() {
		return costRateCategory;
	}

	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
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
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
