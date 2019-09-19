package gov.nwcg.isuite.core.filter.impl;

import java.math.BigDecimal;

import gov.nwcg.isuite.core.filter.CostRateCategoryFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.core.vo.UnitOfMeasureVo;

public class CostRateCategoryFilterImpl extends FilterImpl implements CostRateCategoryFilter {

	private String costRateCategory=null;
	private String requestCategory=null;
	private Long agencyId=null;
	private String itemCode=null;
	private String itemDescription=null;
	private String rateType=null;
	private UnitOfMeasureVo unitOfMeasureVo=null;
	private BigDecimal rate=null;
	
	public CostRateCategoryFilterImpl(){
		
	}

	/**
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() {
		return costRateCategory;
	}

	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
	}

	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() {
		return requestCategory;
	}

	/**
	 * @param requestCategory the requestCategory to set
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#getItemCode()
   */
  public String getItemCode() {
    return itemCode;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#setItemCode(java.lang.String)
   */
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#getItemDescription()
   */
  public String getItemDescription() {
    return itemDescription;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#setItemDescription(java.lang.String)
   */
  public void setItemDescription(String itemDescription) {
    this.itemDescription = itemDescription;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#getRateType()
   */
  public String getRateType() {
    return rateType;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#setRateType(java.lang.String)
   */
  public void setRateType(String rateType) {
    this.rateType = rateType;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#getRate()
   */
  public BigDecimal getRate() {
    return rate;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.CostRateCategoryFilter#setRate(java.lang.String)
   */
  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

	/**
	 * @return the unitOfMeasureVo
	 */
	public UnitOfMeasureVo getUnitOfMeasureVo() {
		return unitOfMeasureVo;
	}

	/**
	 * @param unitOfMeasureVo the unitOfMeasureVo to set
	 */
	public void setUnitOfMeasureVo(UnitOfMeasureVo unitOfMeasureVo) {
		this.unitOfMeasureVo = unitOfMeasureVo;
	}
}
