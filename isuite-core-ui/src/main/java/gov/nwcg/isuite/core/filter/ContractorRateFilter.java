package gov.nwcg.isuite.core.filter;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.framework.core.filter.Filter;

import java.math.BigDecimal;

public interface ContractorRateFilter extends Filter {
	

	/**
	 * @return the supercededByVo
	 */
	public ContractorRateVo getSupercededByVo(); 

	/**
	 * @param supercededByVo the supercededByVo to set
	 */
	public void setSupercededByVo(ContractorRateVo supercededByVo);

	/**
	 * @return the rateType
	 */
	public String getRateType();

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType);

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure();

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure);
	
	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount();

	/**
	 * @param rateAmmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmmount);

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount();

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount);

	/**
	 * @return the description
	 */
	public String getDescription();

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description);
	
}
