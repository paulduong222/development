package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

import gov.nwcg.isuite.core.filter.CostGroupFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CostGroupFilterImpl extends FilterImpl implements CostGroupFilter {
	
	private static final long serialVersionUID = 6712322756663497973L;
	
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;
	
	private String costGroupName;
	private Date startDate;
	private String description;
	private String shift;
	private String incidentName;
	private String crypticDateFilterCodeForStartDate;
	
	public CostGroupFilterImpl() {
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getIncidentId()
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setIncidentId(java.lang.Long)
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getIncidentGroupId()
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setIncidentGroupId(java.lang.Long)
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getCostGroupName()
	 */
	public String getCostGroupName() {
		return costGroupName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setCostGroupName(java.lang.String)
	 */
	public void setCostGroupName(String costGroupName) {
		this.costGroupName = costGroupName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getStartDate()
	 */
	public Date getStartDate() {
		return startDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setStartDate(java.util.Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getDescription()
	 */
	public String getDescription() {
		return description;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getShift()
	 */
	public String getShift() {
		return shift;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setShift(java.lang.String)
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setIncidentName(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#getCrypticDateFilterCodeForStartDate()
	 */
	public String getCrypticDateFilterCodeForStartDate() {
		return this.crypticDateFilterCodeForStartDate;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupFilter#setCrypticDateFilterCodeForStartDate(java.lang.String)
	 */
	public void setCrypticDateFilterCodeForStartDate(String crypticDateFilterCodeForStartDate) {
		this.crypticDateFilterCodeForStartDate = crypticDateFilterCodeForStartDate;
	}
	
}
