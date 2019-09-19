package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.KindAltDesc;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.core.domain.impl.KindAltDescImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class KindAltDescVo extends AbstractVo implements PersistableVo {
	private KindVo kindVo;
	private String description;
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static KindAltDescVo getInstance(KindAltDesc entity, boolean cascadable) throws Exception {
		KindAltDescVo vo = new KindAltDescVo();
		
		if(null == entity)
			throw new Exception("Unable to create KindAltDescVo from null KindAltDesc entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(null != entity.getKind()) {
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
			vo.setDescription(entity.getDescription());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<KindAltDescVo> getInstances(Collection<KindAltDesc> entities, boolean cascadable) throws Exception {
		Collection<KindAltDescVo> vos = new ArrayList<KindAltDescVo>();
		
		for(KindAltDesc entity : entities) {
			vos.add(KindAltDescVo.getInstance(entity, cascadable));
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
	public static KindAltDesc toEntity(KindAltDesc entity, KindAltDescVo vo, boolean cascadable,Persistable...persistables)throws Exception {
		if(null == entity){
			entity = new KindAltDescImpl();
		}
		
		entity.setId(vo.getId());

		if(cascadable){
			if(null != vo.getKindVo()) {
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			}
			
			entity.setDescription(vo.getDescription());
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
	public static Collection<KindAltDesc> toEntityList(Collection<KindAltDescVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<KindAltDesc> entities = new ArrayList<KindAltDesc>();
		
		for(KindAltDescVo vo : vos) {
			entities.add(KindAltDescVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	
	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}
	
	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
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
