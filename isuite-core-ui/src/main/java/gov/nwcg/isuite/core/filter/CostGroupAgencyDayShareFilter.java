package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface CostGroupAgencyDayShareFilter extends Filter {
	
	/**
	 * @return costId
	 */
	public Long getCostGroupId();
	
	/**
	 * @param costId
	 */
	public void setCostGroupId(Long costId);
	
	/**
	 * @return agencyShareDate
	 */
	public Date getAgencyShareDate();
	
	/**
	 * @param agencyShareDate
	 */
	public void setAgencyShareDate(Date agencyShareDate);
	
	/**
	 * @return agency
	 */
	public String getAgency();
	
	/**
	 * @param agency
	 */
	public void setAgency(String agency);
	
	/**
	 * @return percentage
	 */
	public String getPercentage();
	
	/**
	 * @param percentage
	 */
	public void setPercentage(String percentage);
	
	/**
	 * @return crypticDateFilterCodeForAgencyShareDate
	 */
	public  String getCrypticDateFilterCodeForAgencyShareDate();
	
	/**
	 * @param crypticDateFilterCodeForAgencyShareDate
	 */
	public void setCrypticDateFilterCodeForAgencyShareDate(String crypticDateFilterCodeForAgencyShareDate);

}
