package gov.nwcg.isuite.core.domain;

import java.math.BigDecimal;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CostGroupAgencyDaySharePercentage extends Persistable{


	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the costGroupAgencyDayShare
	 */
	public CostGroupAgencyDayShare getCostGroupAgencyDayShare() ;

	/**
	 * @param costGroupAgencyDayShare the costGroupAgencyDayShare to set
	 */
	public void setCostGroupAgencyDayShare(
			CostGroupAgencyDayShare costGroupAgencyDayShare) ;

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
	public void setPercentage(BigDecimal percentage) ;	
}
