package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.core.domain.impl.ResourceTrainingImpl;
import gov.nwcg.isuite.core.domain.impl.RscTrainingObjectiveImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class RscTrainingObjectiveVo extends AbstractVo implements PersistableVo {
	
	private ResourceTrainingVo resourceTrainingVo;
	private String objective;
	private Integer positionNum;
	
	public RscTrainingObjectiveVo(){
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RscTrainingObjectiveVo getInstance(RscTrainingObjective entity, boolean cascadable) throws Exception {
		RscTrainingObjectiveVo vo = new RscTrainingObjectiveVo();
		
		if(null != entity){
			vo.setId(entity.getId());
		}
		
		if(cascadable){
			if(null != entity.getResourceTraining()){
//				vo.setResourceTrainingVo(ResourceTrainingVo.getInstance(entity.getResourceTraining(), true));
			}
			vo.setObjective(entity.getObjective());
			vo.setPositionNum(entity.getPositionNum());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RscTrainingObjectiveVo> getInstances(Collection<RscTrainingObjective> entities, boolean cascadable) throws Exception {
		Collection<RscTrainingObjectiveVo> vos = new ArrayList<RscTrainingObjectiveVo>();

		for(RscTrainingObjective entity : entities){
			vos.add(RscTrainingObjectiveVo.getInstance(entity, cascadable));
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
	public static RscTrainingObjective toEntity(RscTrainingObjective entity, RscTrainingObjectiveVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new RscTrainingObjectiveImpl();
		
		if(vo.getId()==0){
			entity.setId(null);
		}else{
			entity.setId(vo.getId());
		}
		
		if(cascadable){
//			if(null != vo.getResourceTrainingVo()){
//				ResourceTraining rtEntity = new ResourceTrainingImpl();
//				rtEntity.setId(vo.getResourceTrainingVo().getId());
//				entity.setResourceTraining(rtEntity);
//			}
			
			ResourceTraining rtEntity = (ResourceTraining)getPersistableObject(persistables, ResourceTraining.class);
			if(null != rtEntity){
				entity.setResourceTraining(rtEntity);
			}
			
			entity.setObjective(vo.getObjective());
			entity.setPositionNum(vo.getPositionNum());
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
	public static Collection<RscTrainingObjective> toEntityList(Collection<RscTrainingObjectiveVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<RscTrainingObjective> entities = new ArrayList<RscTrainingObjective>();
		ResourceTraining rtEntity = (ResourceTraining)getPersistableObject(persistables, ResourceTraining.class);
		RscTrainingObjective rto = null;
		
		for(RscTrainingObjectiveVo vo : vos){
			for(RscTrainingObjective rscto : rtEntity.getRscTrainingObjectives()){
				if(rscto.getId().compareTo(vo.getId())==0){
					rto = rscto;
				}
			}
			entities.add(RscTrainingObjectiveVo.toEntity(rto, vo, cascadable, persistables));	
		}
		
		return entities;
	}
	

	/**
	 * @param resourceTrainingVo the resourceTrainingVo to set
	 */
	public void setResourceTrainingVo(ResourceTrainingVo resourceTrainingVo) {
		this.resourceTrainingVo = resourceTrainingVo;
	}

	/**
	 * @return the resourceTrainingVo
	 */
	public ResourceTrainingVo getResourceTrainingVo() {
		return resourceTrainingVo;
	}

	/**
	 * @param objective the objective to set
	 */
	public void setObjective(String objective) {
		this.objective = objective;
	}

	/**
	 * @return the objective
	 */
	public String getObjective() {
		return objective;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

}
