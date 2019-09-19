package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.PriorityProgramImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.LongUtility;

public class PriorityProgramVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	private Collection<ResourceTrainingVo> resourceTrainingVos = new ArrayList<ResourceTrainingVo>();
	private Long incidentId;
	private Long incidentGroupId;
	
	private IncidentVo incidentVo;
//	private IncidentGroupVo incidentGroupVo;
	
	
	/**
	 * Default Constructor
	 */
	public PriorityProgramVo(){
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static PriorityProgramVo getInstance(PriorityProgram entity,boolean cascadable) throws Exception {
		PriorityProgramVo vo = new PriorityProgramVo();
		
		if(null == entity)
			throw new Exception("Unable to create PriorityProgramVo from null PriorityProgram entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			
//			if(null != entity.getResourceTrainings())
//				vo.setResourceTrainingVos(ResourceTrainingVo.getInstances(entity.getResourceTrainings(), true));
			
			if(null != entity.getIncident()) {
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
				vo.setIncidentId(entity.getIncident().getId());
			}
				
			if(null != entity.getIncidentGroup())
				vo.setIncidentGroupId(entity.getIncidentGroup().getId());
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<PriorityProgramVo> getInstances(Collection<PriorityProgram> entities, boolean cascadable) throws Exception {
		Collection<PriorityProgramVo> vos = new ArrayList<PriorityProgramVo>();

		for(PriorityProgram entity : entities){
			vos.add(PriorityProgramVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static PriorityProgram toEntity(PriorityProgram entity, PriorityProgramVo vo, boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) {
			entity = new PriorityProgramImpl();
		}
		
		if(vo.getId()==0){
			entity.setId(null);
		}else{
			entity.setId(vo.getId());
		}
		
		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			
			if(null != vo.getResourceTrainingVos())
				entity.setResourceTrainings(ResourceTrainingVo.toEntityList(vo.getResourceTrainingVos(), true));
			
			if(LongUtility.hasValue(vo.getIncidentId())){
				Incident inc = new IncidentImpl();
				inc.setId(vo.getIncidentId());
				entity.setIncident(inc);
			}
			
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				IncidentGroup incGroup = new IncidentGroupImpl();
				incGroup.setId(vo.getIncidentGroupId());
				entity.setIncidentGroup(incGroup);
			}
			
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<PriorityProgram> toEntityList(Collection<PriorityProgramVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<PriorityProgram> entities = new ArrayList<PriorityProgram>();
		
		for(PriorityProgramVo vo : vos){
			entities.add(PriorityProgramVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	 * @param resourceTrainingVos the resourceTrainingVos to set
	 */
	public void setResourceTrainingVos(Collection<ResourceTrainingVo> resourceTrainingVos) {
		this.resourceTrainingVos = resourceTrainingVos;
	}

	/**
	 * @return the resourceTrainingVos
	 */
	public Collection<ResourceTrainingVo> getResourceTrainingVos() {
		return resourceTrainingVos;
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
//	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
//		this.incidentGroupVo = incidentGroupVo;
//	}

	/**
	 * @return the incidentGroupVo
	 */
//	public IncidentGroupVo getIncidentGroupVo() {
//		return incidentGroupVo;
//	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

}
