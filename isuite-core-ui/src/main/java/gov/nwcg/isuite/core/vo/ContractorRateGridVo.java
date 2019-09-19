package gov.nwcg.isuite.core.vo;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import java.math.BigDecimal;

public class ContractorRateGridVo extends AbstractVo {
	private ContractorRateVo supercededByVo;
	private String rateType;
	private String unitOfMeasure;
	private BigDecimal rateAmount;
	private BigDecimal guaranteeAmount;
	private String description;
	
	public ContractorRateGridVo(){
		
	}
	
	
	/**
	 * @return the supercededByVo
	 */
	public ContractorRateVo getSupercededByVo() {
		return supercededByVo;
	}

	/**
	 * @param supercededByVo the supercededByVo to set
	 */
	public void setSupercededByVo(ContractorRateVo supercededByVo) {
		this.supercededByVo = supercededByVo;
	}

	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}

	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	/**
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmmount) {
		this.rateAmount = rateAmmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	
}
