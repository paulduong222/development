package gov.nwcg.isuite.core.domain;

import java.math.BigDecimal;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ContractorRate extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the supercededBy
	 */
	public ContractorRate getSupercededBy() ;

	/**
	 * @param supercededBy the supercededBy to set
	 */
	public void setSupercededBy(ContractorRate supercededBy); 

	/**
	 * @return the supercededById
	 */
	public Long getSupercededById() ;

	/**
	 * @param supercededById the supercededById to set
	 */
	public void setSupercededById(Long supercededById) ;

	/**
	 * @return the rateType
	 */
	public String getRateType() ;

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) ;

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() ;

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) ;

	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() ;

	/**
	 * @param rateAmmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmmount) ;

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() ;

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) ;

	/**
	 * @return the description
	 */
	public String getDescription() ;

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) ;
	
}
