package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AdjustCategory;
import gov.nwcg.isuite.core.domain.impl.AdjustCategoryImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.util.Collection;

public class AdjustCategoryVo extends AbstractVo {

	private String code;
	private String description;
	private AdjustmentTypeEnum adjustmentType;
	private Collection<TimeAssignAdjustVo> timeAssignAdjustVos;
	
	public AdjustCategoryVo() {
		super();
	}
	
	public static AdjustCategoryVo getInstance(AdjustCategory entity, boolean cascadable) throws Exception {
		
		AdjustCategoryVo vo = new AdjustCategoryVo();
		vo.setId(entity.getId());
		
		if(cascadable) {
			
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			vo.setAdjustmentType(entity.getAdjustmentType());
//			TODO: set adjustment type: enum?
			//vo.setTimeAssignAdjustVos(TimeAssignAdjustVo.getInstances(entity.getTimeAssignAdjusts(), cascadable));
		}
		
		return vo;
	}
	
	public static AdjustCategory toEntity(AdjustCategory entity, AdjustCategoryVo vo
			, boolean cascadable,Persistable...persistables) throws Exception {
		
		if(null == entity) entity = new AdjustCategoryImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			//TODO: set adjustment type: enum?
			entity.setTimeAssignAdjusts(TimeAssignAdjustVo.toEntities(vo.getTimeAssignAdjustVos(), cascadable, persistables));
		}
		
		return entity;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	/**
	 * @return the adjustmentType
	 */
	public AdjustmentTypeEnum getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(AdjustmentTypeEnum adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	/**
	 * @return the timeAssignAdjustVos
	 */
	public Collection<TimeAssignAdjustVo> getTimeAssignAdjustVos() {
		return timeAssignAdjustVos;
	}

	/**
	 * @param timeAssignAdjustVos the timeAssignAdjustVos to set
	 */
	public void setTimeAssignAdjustVos(
			Collection<TimeAssignAdjustVo> timeAssignAdjustVos) {
		this.timeAssignAdjustVos = timeAssignAdjustVos;
	}
}
