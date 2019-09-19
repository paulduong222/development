package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.SubGroupCategory;
import gov.nwcg.isuite.core.domain.impl.SubGroupCategoryImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class SubGroupCategoryVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard = false;
	private Boolean active;
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;
	
	public SubGroupCategoryVo() {
		super();
	}
	
	public static Collection<SubGroupCategoryVo> getInstances(Collection<SubGroupCategory> entities, boolean cascadable) throws Exception {
		Collection<SubGroupCategoryVo> vos = new ArrayList<SubGroupCategoryVo>();
		
		for(SubGroupCategory entity : entities) {
			vos.add(SubGroupCategoryVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static SubGroupCategoryVo getInstance(SubGroupCategory entity, boolean cascadable) throws Exception {
		SubGroupCategoryVo vo = new SubGroupCategoryVo();
		
		if(null == entity)
			   throw new Exception("Unable to create SubGroupCategoryVo from null SubGroupCategory entity.");
		
		   vo.setId(entity.getId());

		   /*
		    * Only populate fields outside of the entity Id if needed
		    */
		   if(cascadable){
			   vo.setCode(entity.getCode());
			   vo.setDescription(entity.getDescription());
			   vo.setStandard(entity.isStandard());
	           vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
	         
			   if(null != entity.getIncident()) {
				   vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
			   }
	         
			   if(null != entity.getIncidentGroup()) {
				   vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), true));
			   }

		   }
		   
		   return vo;
	}
	
	public static SubGroupCategory toEntity(SubGroupCategory entity, SubGroupCategoryVo vo, Boolean cascadable) throws Exception {
		if(null == entity) {
			entity = new SubGroupCategoryImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
	         
			if (null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
         
			if (null != vo.getIncidentGroupVo()) {
				entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
			}
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
	 * @return
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return this.standard;
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
	 * @return the active
	 */
	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the active
	 */
	@JsonIgnore
	public Boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}
	   
}
