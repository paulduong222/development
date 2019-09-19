/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.DailyCostFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.Date;

public class DailyCostFilterImpl extends FilterImpl implements DailyCostFilter {

	private static final long serialVersionUID = 4433328339581949565L;
	private Long incidentResourceId;
	private Long incidentResourceOtherId;
	private Date fromDate;
	private String fromDateComparator=">";
	
	/**
	 * 
	 */
	public DailyCostFilterImpl() {}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the fromDateComparator
	 */
	public String getFromDateComparator() {
		return fromDateComparator;
	}

	/**
	 * @param fromDateComparator the fromDateComparator to set
	 */
	public void setFromDateComparator(String fromDateComparator) {
		this.fromDateComparator = fromDateComparator;
	}

	/**
	 * @return the incidentResourceOtherId
	 */
	public Long getIncidentResourceOtherId() {
		return incidentResourceOtherId;
	}

	/**
	 * @param incidentResourceOtherId the incidentResourceOtherId to set
	 */
	public void setIncidentResourceOtherId(Long incidentResourceOtherId) {
		this.incidentResourceOtherId = incidentResourceOtherId;
	}


}
