package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.GroupCategory;
import gov.nwcg.isuite.core.domain.impl.GroupCategoryImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class GroupCategoryVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard = false;
	private Boolean active;
//	private IncidentVo incidentVo;
//	private IncidentGroupVo incidentGroupVo;

	
	public GroupCategoryVo() {
		super();
	}
	
	public static Collection<GroupCategoryVo> getInstances(Collection<GroupCategory> entities, boolean cascadable) throws Exception {
		Collection<GroupCategoryVo> vos = new ArrayList<GroupCategoryVo>();
		
		for(GroupCategory entity : entities) {
			vos.add(GroupCategoryVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static GroupCategoryVo getInstance(GroupCategory entity, boolean cascadable) throws Exception {
		GroupCategoryVo vo = new GroupCategoryVo();
		
		if(null == entity)
			throw new Exception("");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			vo.setStandard(entity.isStandard());
            vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
         
		}
		
		return vo;
	}
	
	public static GroupCategory toEntity(GroupCategory entity, GroupCategoryVo vo, Boolean cascadable) throws Exception {
		if(null == entity) {
			entity = new GroupCategoryImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			entity.setStandard(vo.getStandard());
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
	         
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
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the standard
	 */
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
//	public void setIncidentVo(IncidentVo incidentVo) {
//		this.incidentVo = incidentVo;
//	}
	
	/**
	 * @return the incidentVo
	 */
//	public IncidentVo getIncidentVo() {
//		return incidentVo;
//	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
//	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
//		this.incidentGroupVo = incidentGroupVo;
//	}
	
	/**
	 * @return the incidentGroupVo
	 */
//	public IncidentGroupVo getIncidentGroupVo() {
//		return incidentGroupVo;
//	}
	   
}
