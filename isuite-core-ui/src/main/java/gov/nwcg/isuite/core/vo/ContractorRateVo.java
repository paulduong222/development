package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.impl.ContractorRateImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class ContractorRateVo extends AbstractVo {
	private ContractorRateVo supercededByVo;
	private String rateType;
	private String unitOfMeasure;
	private BigDecimal rateAmount;
	private BigDecimal guaranteeAmount;
	private String description;
	private String displayName;
	
	public ContractorRateVo(){
		
	}

	public static ContractorRateVo getInstance(ContractorRate entity,boolean cascadable) throws Exception {
		ContractorRateVo vo = new ContractorRateVo();
		
		if(null == entity)
			throw new Exception("Unable to create vo from null ContractorRate entity");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			
			if(null != entity.getSupercededBy())
				vo.setSupercededByVo(ContractorRateVo.getInstance(entity.getSupercededBy(), cascadable));
			
			vo.setRateType(entity.getRateType());
			vo.setUnitOfMeasure(entity.getUnitOfMeasure());
			vo.setRateAmount(entity.getRateAmount());
			vo.setGuaranteeAmount(entity.getGuaranteeAmount());
			vo.setDescription(entity.getDescription());
		
			String name=
				StringUtility.rightPad(vo.getUnitOfMeasure(), " ", 10) + " | " +
				StringUtility.rightPad(StringUtility.toCurrency(String.valueOf(vo.getRateAmount())), " ", 10) + " | " ;
			if(DecimalUtil.hasValue(vo.getGuaranteeAmount())){
				name=name+StringUtility.rightPad("$ 0.00", " ", 10) + " | ";
			}else{
				name=name+StringUtility.rightPad(StringUtility.toCurrency(String.valueOf(vo.getGuaranteeAmount())), " ", 10) + " | ";
			}
			
			vo.setDisplayName(name);
			
		}
		
		return vo;
	}

	public static Collection<ContractorRateVo> getInstances(Collection<ContractorRate> entities,boolean cascadable) throws Exception {
		Collection<ContractorRateVo> vos = new ArrayList<ContractorRateVo>();
		
		for(ContractorRate entity : entities){
			vos.add(ContractorRateVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static ContractorRate toEntity(ContractorRate entity, ContractorRateVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if(null == entity)
			entity = new ContractorRateImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(null != vo.getSupercededByVo()){
				entity.setSupercededBy(ContractorRateVo.toEntity(null, vo.getSupercededByVo(), cascadable, persistables));
			}
			entity.setRateType(vo.getRateType());
			entity.setUnitOfMeasure(vo.getUnitOfMeasure());
			entity.setRateAmount(vo.getRateAmount());
			entity.setGuaranteeAmount(vo.getGuaranteeAmount());
			entity.setDescription(vo.getDescription());
		}
		
		return entity;
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
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
	
}
