package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CostGroupAgencyDayShareVo extends AbstractVo{

	private CostGroupVo costGroupVo;
	private Date agencyShareDate;
	private Collection<CostGroupAgencyDaySharePercentageVo> costGroupAgencyDaySharePercentageVos = new ArrayList<CostGroupAgencyDaySharePercentageVo>();
	
	public CostGroupAgencyDayShareVo() {
		
	}
	
	/**
	 * Returns a CostGroupAgencyDaySharePercentageVo from a CostGroupAgencyDaySharePercent entity.
	 * 
	 * @param entity
	 * 			the source CostGroupAgencyDayShare entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of CostGroupAgencyDayShareVo
	 * @throws Exception
	 */
	public static CostGroupAgencyDayShareVo getInstance(CostGroupAgencyDayShare entity,boolean cascadable) throws Exception{
		CostGroupAgencyDayShareVo vo = new CostGroupAgencyDayShareVo();

		if(null == entity)
			throw new Exception("Unable to create CostGroupAgencyDayShareVo from null CostGroupAgencyDayShare entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if (null != entity.getCostGroup()) {
				vo.setCostGroupVo(CostGroupVo.getInstance(entity.getCostGroup(), false));
			}
			vo.setAgencyShareDate(entity.getAgencyShareDate());

			if(null != entity.getCostGroupAgencyDaySharePercentages()){
				vo.setCostGroupAgencyDaySharePercentageVos(CostGroupAgencyDaySharePercentageVo.getInstances(entity.getCostGroupAgencyDaySharePercentages(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of CostGroupAgencyDayShareVos from a collection of CostGroupAgencyDayShare entities.
	 * 
	 * @param entities
	 * 			the source collection of CostGroupAgencyDayShare entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of CostGroupAgencyDayShare vos
	 * @throws Exception
	 */
	public static Collection<CostGroupAgencyDayShareVo> getInstances(Collection<CostGroupAgencyDayShare> entities, boolean cascadable) throws Exception {
		Collection<CostGroupAgencyDayShareVo> vos = new ArrayList<CostGroupAgencyDayShareVo>();

		for(CostGroupAgencyDayShare entity : entities){
			vos.add(CostGroupAgencyDayShareVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostGroupAgencyDayShare entity from a CostGroupAgencyDayShare vo.
	 * 
	 * @param vo
	 * 			the source CostGroupAgencyDayShare vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostGroupAgencyDayShare entity
	 * @throws Exception
	 */
	public static CostGroupAgencyDayShare toEntity(CostGroupAgencyDayShare entity,CostGroupAgencyDayShareVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null==entity)
			entity = new CostGroupAgencyDayShareImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if (null != vo.getCostGroupVo()) {
				entity.setCostGroup(CostGroupVo.toEntity(null, vo.getCostGroupVo(), false));
			}
			
			entity.setAgencyShareDate(vo.getAgencyShareDate());
			
			Collection<CostGroupAgencyDaySharePercentage> removeList = 
				CostGroupAgencyDaySharePercentageVo.toEntityRemoveList(vo.getCostGroupAgencyDaySharePercentageVos(),entity.getCostGroupAgencyDaySharePercentages());

			Collection<CostGroupAgencyDaySharePercentage> addList = 
				CostGroupAgencyDaySharePercentageVo.toEntityAddList(vo.getCostGroupAgencyDaySharePercentageVos(),entity.getCostGroupAgencyDaySharePercentages(),entity);
			
			Collection<CostGroupAgencyDaySharePercentage> updateList = 
				CostGroupAgencyDaySharePercentageVo.toEntityUpdateList(vo.getCostGroupAgencyDaySharePercentageVos(),entity.getCostGroupAgencyDaySharePercentages(),entity);

			if(CollectionUtility.hasValue(removeList)){
				entity.getCostGroupAgencyDaySharePercentages().removeAll(removeList);
			}
			
			if(CollectionUtility.hasValue(addList))
				entity.getCostGroupAgencyDaySharePercentages().addAll(addList);
			
			if(CollectionUtility.hasValue(updateList)){
				Collection<CostGroupAgencyDaySharePercentage> newList = new ArrayList<CostGroupAgencyDaySharePercentage>();
				for(CostGroupAgencyDaySharePercentage p : entity.getCostGroupAgencyDaySharePercentages()){
					boolean bfound=false;
					
					for(CostGroupAgencyDaySharePercentage p2 : updateList){
						if(LongUtility.hasValue(p.getId()) && LongUtility.hasValue(p2.getId())){
							if(p.getId().compareTo(p2.getId())==0){
								p.setPercentage(p2.getPercentage());
								newList.add(p);
								bfound=true;
								break;
							}
						}
					}
					
					if(!bfound)
						newList.add(p);
				}
				
				entity.getCostGroupAgencyDaySharePercentages().clear();
				entity.getCostGroupAgencyDaySharePercentages().addAll(newList);
			}
			
			/*
			if (null != vo.getCostGroupAgencyDaySharePercentageVos()) {
				entity.setCostGroupAgencyDaySharePercentages(CostGroupAgencyDaySharePercentageVo.toEntityList(vo.getCostGroupAgencyDaySharePercentageVos(), false));
			}
			*/
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of CostGroupAgencyDayShare entities from a collection of CostGroupAgencyDayShare vos.
	 * 
	 * @param vos
	 * 			the source collection of CostGroupAgencyDayShare vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of CostGroupAgencyDayShare entities
	 * @throws Exception
	 */
	public static Collection<CostGroupAgencyDayShare> toEntityList(Collection<CostGroupAgencyDayShareVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CostGroupAgencyDayShare> entities = new ArrayList<CostGroupAgencyDayShare>();

		for(CostGroupAgencyDayShareVo vo : vos){
			entities.add(CostGroupAgencyDayShareVo.toEntity(null,vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the CostGroupAgencyDayShare field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source CostGroupAgencyDayShare entity
	 * @throws ValidationException
	 */
	private static void validateEntity(CostGroupAgencyDayShare entity) throws ValidationException {
	}
	
	/**
	 * @return the costGroupVo
	 */
	public CostGroupVo getCostGroupVo() {
		return costGroupVo;
	}

	/**
	 * @param costGroupVo the costGroupVo to set
	 */
	public void setCostGroupVo(CostGroupVo costGroupVo) {
		this.costGroupVo = costGroupVo;
	}

	/**
	 * @return the agencyShareDate
	 */
	public Date getAgencyShareDate() {
		return agencyShareDate;
	}

	/**
	 * @param agencyShareDate the agencyShareDate to set
	 */
	public void setAgencyShareDate(Date agencyShareDate) {
		this.agencyShareDate = agencyShareDate;
	}

	/**
	 * @param costGroupAgencyDaySharePercentageVos the costGroupAgencyDaySharePercentageVos to set
	 */
	public void setCostGroupAgencyDaySharePercentageVos(
			Collection<CostGroupAgencyDaySharePercentageVo> costGroupAgencyDaySharePercentageVos) {
		this.costGroupAgencyDaySharePercentageVos = costGroupAgencyDaySharePercentageVos;
	}

	/**
	 * @return the costGroupAgencyDaySharePercentageVos
	 */
	public Collection<CostGroupAgencyDaySharePercentageVo> getCostGroupAgencyDaySharePercentageVos() {
		return costGroupAgencyDaySharePercentageVos;
	}
}
