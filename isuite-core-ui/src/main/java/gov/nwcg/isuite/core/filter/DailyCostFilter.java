package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Date;

public interface DailyCostFilter extends Filter {

	/**
	 * @param id
	 */
	public void setIncidentResourceId(Long id);
	
	/**
	 * @return
	 */
	public Long getIncidentResourceId();

	public void setIncidentResourceOtherId(Long id);
	
	public Long getIncidentResourceOtherId();
	
	/**
	 * @param date
	 */
	public void setFromDate(Date date);
	
	/**
	 * @return
	 */
	public Date getFromDate();

	/**
	 * @return the fromDateComparator
	 */
	public String getFromDateComparator() ;
	
	/**
	 * @param fromDateComparator the fromDateComparator to set
	 */
	public void setFromDateComparator(String fromDateComparator) ;
	
}
