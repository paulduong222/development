package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Complexity;
import gov.nwcg.isuite.core.domain.impl.ComplexityImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class ComplexityVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
//	private Collection<RscTrainingTrainerVo> rscTrainingTrainerVos = new ArrayList<RscTrainingTrainerVo>();
//	private Collection<IncidentVo> incidentVos = new ArrayList<IncidentVo>();
	
	/**
	* Default Constructor
	*/
	public ComplexityVo() {
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ComplexityVo getInstance(Complexity entity,boolean cascadable) throws Exception {
		ComplexityVo vo = new ComplexityVo();
		
		if(null == entity)
			throw new Exception("Unable to create ComplexityVo from null Complexity entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
			
//			if(null != entity.getRscTrainingTrainers())
//				vo.setRscTrainingTrainerVos(RscTrainingTrainerVo.getInstances(entity.getRscTrainingTrainers(), true));
			
//			if(null != entity.getIncidents())
//				vo.setIncidentVos(IncidentVo.getInstances(entity.getIncidents(), true));
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<ComplexityVo> getInstances(Collection<Complexity> entities, boolean cascadable) throws Exception {
		Collection<ComplexityVo> vos = new ArrayList<ComplexityVo>();

		for(Complexity entity : entities){
			vos.add(ComplexityVo.getInstance(entity, cascadable));
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
	public static Complexity toEntity(Complexity entity, ComplexityVo vo, boolean cascadable,Persistable...persistables) throws Exception {
		if (null == entity) {
			entity = new ComplexityImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
			
//			if(null != vo.getRscTrainingTrainerVos())
//				entity.setRscTrainingTrainers(RscTrainingTrainerVo.toEntityList(vo.getRscTrainingTrainerVos(), true));
			
//			if(null != vo.getIncidentVos())
//				entity.setIncidents(IncidentVo.toEntityList(vo.getIncidentVos(), true));
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
	public static Collection<Complexity> toEntityList(Collection<ComplexityVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Complexity> entities = new ArrayList<Complexity>();
		
		for(ComplexityVo vo : vos){
			entities.add(ComplexityVo.toEntity(null, vo, cascadable, persistables));
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
	 * @param rscTrainingTrainerVos the rscTrainingTrainerVos to set
	 */
//	public void setRscTrainingTrainerVos(Collection<RscTrainingTrainerVo> rscTrainingTrainerVos) {
//		this.rscTrainingTrainerVos = rscTrainingTrainerVos;
//	}

	/**
	 * @return the rscTrainingTrainerVos
	 */
//	public Collection<RscTrainingTrainerVo> getRscTrainingTrainerVos() {
//		return rscTrainingTrainerVos;
//	}

	/**
	 * @param incidentVos the incidentVos to set
	 */
//	public void setIncidentVos(Collection<IncidentVo> incidentVos) {
//		this.incidentVos = incidentVos;
//	}

	/**
	 * @return the incidentVos
	 */
//	public Collection<IncidentVo> getIncidentVos() {
//		return incidentVos;
//	}
}
