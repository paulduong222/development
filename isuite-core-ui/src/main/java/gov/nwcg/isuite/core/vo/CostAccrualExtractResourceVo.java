package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostAccrualExtractRsc;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CostAccrualExtractResourceVo extends AbstractVo implements PersistableVo {
	private Long costAccrualExtractId;
	private BigDecimal totalAmount; 
	private BigDecimal changeAmount; 
	
	public CostAccrualExtractResourceVo() {
	}

	public static CostAccrualExtractResourceVo getInstance(CostAccrualExtractRsc entity,boolean cascadable) throws Exception {
		CostAccrualExtractResourceVo vo = new CostAccrualExtractResourceVo();

		if(null == entity)
			throw new Exception("Unable to create CostAccrualExtractResourceVo from null CostAccrualExtractRsc entity.");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setCostAccrualExtractId(entity.getCostAccrualExtractId());
			vo.setTotalAmount(entity.getTotalAmount());
			vo.setChangeAmount(entity.getChangeAmount());
		}
		
		return vo;
	}

	public static Collection<CostAccrualExtractResourceVo> getInstances(Collection<CostAccrualExtractRsc> entities, boolean cascadable) throws Exception {
		Collection<CostAccrualExtractResourceVo> vos = new ArrayList<CostAccrualExtractResourceVo>();

		for(CostAccrualExtractRsc entity : entities){
			vos.add(CostAccrualExtractResourceVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @return the costAccrualExtractId
	 */
	public Long getCostAccrualExtractId() {
		return costAccrualExtractId;
	}

	/**
	 * @param costAccrualExtractId the costAccrualExtractId to set
	 */
	public void setCostAccrualExtractId(Long costAccrualExtractId) {
		this.costAccrualExtractId = costAccrualExtractId;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the changeAmount
	 */
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}

	/**
	 * @param changeAmount the changeAmount to set
	 */
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}

}
