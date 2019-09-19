package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.DailyForm;
import gov.nwcg.isuite.core.domain.impl.DailyFormImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class DailyFormVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Boolean standard = false;
	
	public DailyFormVo() {
		super();
	}
	
	public static Collection<DailyFormVo> getInstances(Collection<DailyForm> entities, boolean cascadable) throws Exception {
		Collection<DailyFormVo> vos = new ArrayList<DailyFormVo>();
		
		for(DailyForm entity : entities) {
			vos.add(DailyFormVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static DailyFormVo getInstance(DailyForm entity, boolean cascadable) throws Exception {
		DailyFormVo vo = new DailyFormVo();
		
		if(null == entity)
			   throw new Exception("Unable to create DailyFormVo from null DailyForm entity.");
		
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
	
	public static DailyForm toEntity(DailyForm entity, DailyFormVo vo, boolean cascadable) throws Exception {
		if(null == entity) {
			entity = new DailyFormImpl();
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

}
