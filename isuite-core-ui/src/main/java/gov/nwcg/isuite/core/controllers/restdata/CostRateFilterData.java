package gov.nwcg.isuite.core.controllers.restdata;

import java.util.Date;

import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.filter.impl.CostRateCategoryFilterImpl;
import gov.nwcg.isuite.core.filter.impl.CostRateFilterImpl;
import gov.nwcg.isuite.core.filter.impl.CostRateGridFilterImpl;

public class CostRateFilterData extends DialogueData {

	private CostRateFilter costRateFilter = new CostRateFilterImpl();
	private CostRateCategoryFilterImpl costRateCategorySubFilter;
	private CostRateGridFilterImpl costRategGridSubFilter;
	
	public CostRateFilter getCostRateFilter() {
		return costRateFilter;
	}
	
	public void setCostRateFilter(CostRateFilter costRateFilter) {
		this.costRateFilter = costRateFilter;
	}

	public String getRequestCategory() {
		return costRateFilter.getRequestCategory();
	}

	public void setRequestCategory(String requestCategory) {
		costRateFilter.setRequestCategory(requestCategory);
	}

	public Long getWorkAreaId() {
		return costRateFilter.getWorkAreaId();
	}

	/**
	 * Note that there is NO setter for this directly (at this level)...  This gets set either by 
	 * setCostRateCategorySubFilter() or setCostRateGridSubFilter().
	 * 
	 * @return
	 */
	public Object getSubFilter() {
		return costRateFilter.getSubFilter();
	}
	
	public CostRateCategoryFilterImpl getCostRateCategorySubFilter() {
		return costRateCategorySubFilter;
	}

	/**
	 * This is the method to use to set the "subFilter" object when specifying a CostRateCategoryFilter.
	 * 
	 * @param costRateCategorySubFilter
	 */
	public void setCostRateCategorySubFilter(CostRateCategoryFilterImpl costRateCategorySubFilter) {
		this.costRateCategorySubFilter = costRateCategorySubFilter;
		this.costRateFilter.setSubFilter(costRateCategorySubFilter);
	}

	public CostRateGridFilterImpl getCostRateGridSubFilter() {
		return costRategGridSubFilter;
	}

	/**
	 * This is the method to use to set the "subFilter" object when specifying a CostRateGridFilter.
	 * 
	 * @param costRateGridSubFilter
	 */
	public void setCostRateGridSubFilter(CostRateGridFilterImpl costRateGridSubFilter) {
		this.costRategGridSubFilter = costRateGridSubFilter;
		this.costRateFilter.setSubFilter(costRateGridSubFilter);
	}

	public void setWorkAreaId(Long waId) {
		costRateFilter.setWorkAreaId(waId);
	}

	public Long getCurrentUserId() {
		return costRateFilter.getCurrentUserId();
	}

	public Long getIncidentId() {
		return costRateFilter.getIncidentId();
	}

	public void setIncidentId(Long incidentId) {
		costRateFilter.setIncidentId(incidentId);
	}

	public void setCurrentUserId(Long userId) {
		costRateFilter.setCurrentUserId(userId);
	}

	public Long getIncidentGroupId() {
		return costRateFilter.getIncidentGroupId();
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		costRateFilter.setIncidentGroupId(incidentGroupId);
	}

	public Date crypticDateConverter(String dateString) {
		return costRateFilter.crypticDateConverter(dateString);
	}

	public String getAdvancedSearch() {
		return costRateFilter.getAdvancedSearch();
	}

	public void setAdvancedSearch(String advancedSearch) {
		costRateFilter.setAdvancedSearch(advancedSearch);
	}

	public String getCostRateCategory() {
		return costRateFilter.getCostRateCategory();
	}

	public void setCostRateCategory(String costRateCategory) {
		costRateFilter.setCostRateCategory(costRateCategory);
	}

	public void setAgencyId(Long id) {
		costRateFilter.setAgencyId(id);
	}

	public Long getAgencyId() {
		return costRateFilter.getAgencyId();
	}
}
