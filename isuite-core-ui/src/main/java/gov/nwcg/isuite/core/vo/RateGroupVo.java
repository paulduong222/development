package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.RateGroup;
import gov.nwcg.isuite.core.domain.impl.RateGroupImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class RateGroupVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard = false;
	
	public RateGroupVo() {
		super();
	}
	
	public static RateGroupVo getInstance(RateGroup entity, boolean cascadable) throws Exception {
		RateGroupVo vo = new RateGroupVo();
		
		if(null == entity)
			throw new Exception("Unable to create RateGroupVo from null RateGroup entity.");
		
		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			vo.setStandard(entity.isStandard());
		}
		   
		return vo;
	}
	
	public static Collection<RateGroupVo> getInstances(Collection<RateGroup> entities, boolean cascadable) throws Exception {
		Collection<RateGroupVo> vos = new ArrayList<RateGroupVo>();
		
		for(RateGroup entity : entities) {
			vos.add(RateGroupVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static RateGroup toEntity(RateGroup entity, RateGroupVo vo, Boolean cascadable) throws Exception {
		if(null == entity) {
			entity = new RateGroupImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
		}
		
		return entity;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param standard the standard to set
	 */
	@JsonProperty("standard")
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the standard
	 */
	@JsonProperty("standard")
	public Boolean getStandard() {
		return standard;
	}
	
	/**
	 * @return the standard
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return this.standard;
	}

}
