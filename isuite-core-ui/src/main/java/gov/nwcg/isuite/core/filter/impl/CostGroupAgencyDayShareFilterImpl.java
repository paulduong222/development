package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

public class CostGroupAgencyDayShareFilterImpl extends FilterImpl implements CostGroupAgencyDayShareFilter {

	private static final long serialVersionUID = -5402539792565724239L;

	private Long costGroupId = 0L;
	
	private String agency;
	private Date agencyShareDate;
	private String crypticDateFilterCodeForAgencyShareDate;
	private String percentage;
	
	public CostGroupAgencyDayShareFilterImpl() {
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#getAgency()
	 */
	public String getAgency() {
		return agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#getAgencyShareDate()
	 */
	public Date getAgencyShareDate() {
		return agencyShareDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#getCostGroupId()
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#getCrypticDateFilterCodeForAgencyShareDate()
	 */
	public String getCrypticDateFilterCodeForAgencyShareDate() {
		return crypticDateFilterCodeForAgencyShareDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#getPercentage()
	 */
	public String getPercentage() {
		return percentage;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#setAgency(java.lang.String)
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#setAgencyShareDate(java.util.Date)
	 */
	public void setAgencyShareDate(Date agencyShareDate) {
		this.agencyShareDate = agencyShareDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#setCostGroupId(java.lang.Long)
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#setCrypticDateFilterCodeForAgencyShareDate(java.lang.String)
	 */
	public void setCrypticDateFilterCodeForAgencyShareDate(String crypticDateFilterCodeForAgencyShareDate) {
		this.crypticDateFilterCodeForAgencyShareDate = crypticDateFilterCodeForAgencyShareDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter#setPercentage(java.lang.String)
	 */
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	
}
