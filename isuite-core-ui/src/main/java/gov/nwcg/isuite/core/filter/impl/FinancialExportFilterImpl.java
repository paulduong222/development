package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.FinancialExportFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;


public class FinancialExportFilterImpl extends FilterImpl implements FinancialExportFilter {


	private static final long serialVersionUID = -4052134118456218537L;
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;
	
	public FinancialExportFilterImpl(){
	}
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.FinancialExportFilter#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.FinancialExportFilter#setIncidentId(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.FinancialExportFilter#getIncidentGroupId()
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.FinancialExportFilter#setIncidentGroupId(java.lang.Long)
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
}
