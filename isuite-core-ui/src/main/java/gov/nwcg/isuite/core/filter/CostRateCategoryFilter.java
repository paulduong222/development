package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;

import java.math.BigDecimal;

public interface CostRateCategoryFilter {

	/**
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() ;

	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory);

	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() ;

	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory);
	
	/**
	 * @return
	 */
	public Long getAgencyId();
	
	/**
	 * @param code
	 */
	public void setAgencyId(Long id);
	
	/**
	 * 
	 * @return
	 */
	public String getItemCode();
  
	/**
	 * Set the column Item Code filter
   * 
	 * @param itemCode
	 */
  public void setItemCode(String itemCode);
  
  /**
   * 
   * @return
   */
  public String getItemDescription();
  
  /**
   * Set the column Item Description filter
   * 
   * @param itemDescription
   */
  public void setItemDescription(String itemDescription);
  
  /**
   * 
   * @return
   */
  public String getRateType();
  
  /**
   * Set the column Rate Type filter
   * 
   * @param rateType
   */
  public void setRateType(String rateType);
  
  /**
   * 
   * @return
   */
  public BigDecimal getRate();

  /**
   * Set the column Rate filter
   * 
   * @param rate
   */
  public void setRate(BigDecimal rate);
  
	/**
	 * @return the unitOfMeasureVo
	 */
	public UnitOfMeasureVo getUnitOfMeasureVo();

	/**
	 * @param unitOfMeasureVo the unitOfMeasureVo to set
	 */
	public void setUnitOfMeasureVo(UnitOfMeasureVo unitOfMeasureVo);
}
