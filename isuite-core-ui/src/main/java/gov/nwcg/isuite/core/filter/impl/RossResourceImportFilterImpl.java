package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class RossResourceImportFilterImpl extends FilterImpl  {
	private static final long serialVersionUID = 3109805558842919394L;
	private String fromDate;
	private String toDate;

	public RossResourceImportFilterImpl(){
		
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
