package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Recommendation;
import gov.nwcg.isuite.core.domain.impl.RecommendationImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class RecommendationVo extends AbstractVo implements PersistableVo {
	private String code;
	private String description;
	
	/**
	 * Default Constructor
	 */
	public RecommendationVo() {
	}
	
	public static RecommendationVo getInstance(Recommendation entity, boolean cascadable) throws Exception {
		RecommendationVo vo = new RecommendationVo();
		
		if(null == entity)
			throw new Exception("Unable to create RecommendationVo from null Recommendation entity.");
		
		vo.setId(entity.getId());
			
		if(cascadable){
			vo.setCode(entity.getCode());
			vo.setDescription(entity.getDescription());
		}
		
		return vo;
	}
	
	public static Collection<RecommendationVo> getInstances(Collection<Recommendation> entities, boolean cascadable) throws Exception {
		Collection<RecommendationVo> vos = new ArrayList<RecommendationVo>();
		
		for(Recommendation entity : entities){
			vos.add(RecommendationVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static Recommendation toEntity(Recommendation entity, RecommendationVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new RecommendationImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setCode(vo.getCode());
			entity.setDescription(vo.getDescription());
		}
		
		return entity;
	}
	
	public static Collection<Recommendation> toEntityList(Collection<RecommendationVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Recommendation> entities = new ArrayList<Recommendation>();
		
		for(RecommendationVo vo : vos){
			entities.add(RecommendationVo.toEntity(null, vo, cascadable, persistables));
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
	

}
