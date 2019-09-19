package gov.nwcg.isuite.core.reports.filter;

import java.util.ArrayList;
import java.util.Collection;

public class CheckoutReportFilter {
	private Collection<Long> resourceIds = null;
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;
	private String sortBy = "";
	private Collection<String> sorts = new ArrayList<String>();
	private Boolean markCheckOutFormPrinted=false;
	private Boolean markIncludeBox12=false;
	
	/**
	 * Sets the resourceIds
	 * 
	 * @param resourceIds
	 * 		the resourceIds to set
	 */
	public void setResourceIds(Collection<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}
	
	/**
	 * Returns the resourceIds
	 * 
	 * @return
	 * 		the resourceIds to return
	 */
	public Collection<Long> getResourceIds() {
		return resourceIds;
	}
	
	/**
	 * Sets the incidentId
	 * 
	 * @param incidentId
	 * 		the incidentId to set
	 */
	public void setIncidentId(Long id) {
		this.incidentId=id;
	}
	
	/**
	 * Returns the incidentId
	 * 
	 * @return
	 * 		the incidentId to return
	 */
	public Long getIncidentId(){
		return incidentId;
	}

	/**
	 * Sets include Strike Team Task Force Components
	 * 
	 * @param includeSTTFComponents
	 * 		the includeSTTFComponents to set
	 */
	public void setIncludeSTTFComponents(Boolean includeSTTFComponents) {
		this.includeSTTFComponents = includeSTTFComponents;
	}

	/**
	 * Returns include Strike Team Task Force Components
	 * 
	 * @return
	 * 		the includeSTTFComponent to return
	 */
	public Boolean getIncludeSTTFComponents() {
		return includeSTTFComponents;
	}

	/**
	 * @param sortBy the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * @return the sortBy
	 */
	public String getSortBy() {
		return sortBy;
	}

	public Collection<String> getSorts() {
		return sorts;
	}

	public void setSorts(Collection<String> sorts) {
		this.sorts = sorts;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Boolean getMarkCheckOutFormPrinted() {
		return markCheckOutFormPrinted;
	}

	public void setMarkCheckOutFormPrinted(Boolean markCheckOutFormPrinted) {
		this.markCheckOutFormPrinted = markCheckOutFormPrinted;
	}

	public Boolean getMarkIncludeBox12() {
		return markIncludeBox12;
	}

	public void setMarkIncludeBox12(Boolean markIncludeBox12) {
		this.markIncludeBox12 = markIncludeBox12;
	}

}
