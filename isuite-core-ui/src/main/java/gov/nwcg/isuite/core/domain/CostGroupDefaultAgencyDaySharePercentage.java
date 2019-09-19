package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;

public interface CostGroupDefaultAgencyDaySharePercentage extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;


	/**
	 * @return the costGroup
	 */
	public CostGroup getCostGroup() ;


	/**
	 * @param costGroup the costGroup to set
	 */
	public void setCostGroup(CostGroup costGroup) ;


	/**
	 * @return the agency
	 */
	public Agency getAgency() ;


	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) ;


	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() ;


	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage);

	
}
