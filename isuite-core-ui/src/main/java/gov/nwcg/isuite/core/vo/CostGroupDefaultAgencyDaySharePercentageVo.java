package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupDefaultAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupDefaultAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CostGroupDefaultAgencyDaySharePercentageVo extends AbstractVo {
	
	private CostGroupVo costGroupVo;
	private AgencyVo agencyVo;
	private BigDecimal percentage;
	
	public CostGroupDefaultAgencyDaySharePercentageVo(){
		
	}

	/**
	 * Returns a CostGroupDefaultAgencyDaySharePercentageVo from a CostGroupDefaultAgencyDaySharePercent entity.
	 * 
	 * @param entity
	 * 			the source CostGroupDefaultAgencyDaySharePercent entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of CostGroupDefaultAgencyDaySharePercentageVo
	 * @throws Exception
	 */
	public static CostGroupDefaultAgencyDaySharePercentageVo getInstance(CostGroupDefaultAgencyDaySharePercentage entity,boolean cascadable) throws Exception{
		CostGroupDefaultAgencyDaySharePercentageVo vo = new CostGroupDefaultAgencyDaySharePercentageVo();

		if(null == entity)
			throw new Exception("Unable to create CostGroupDefaultAgencyDaySharePercentageVo from null CostGroupDefaultAgencyDaySharePercent entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if (null != entity.getCostGroup()) {
				vo.setCostGroupVo(CostGroupVo.getInstance(entity.getCostGroup(), false));
			}
			if (null != entity.getAgency()) {
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}
			vo.setPercentage(entity.getPercentage());
		}

		return vo;
	}

	/**
	 * Returns a collection of CostGroupDefaultAgencyDaySharePercentageVos from a collection of CostGroupDefaultAgencyDaySharePercent entities.
	 * 
	 * @param entities
	 * 			the source collection of CostGroupDefaultAgencyDaySharePercent entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of CostGroupDefaultAgencyDaySharePercent vos
	 * @throws Exception
	 */
	public static Collection<CostGroupDefaultAgencyDaySharePercentageVo> getInstances(Collection<CostGroupDefaultAgencyDaySharePercentage> entities, boolean cascadable) throws Exception {
		Collection<CostGroupDefaultAgencyDaySharePercentageVo> vos = new ArrayList<CostGroupDefaultAgencyDaySharePercentageVo>();

		for(CostGroupDefaultAgencyDaySharePercentage entity : entities){
			vos.add(CostGroupDefaultAgencyDaySharePercentageVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostGroupDefaultAgencyDaySharePercent entity from a CostGroupDefaultAgencyDaySharePercent vo.
	 * 
	 * @param vo
	 * 			the source CostGroupDefaultAgencyDaySharePercent vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostGroupDefaultAgencyDaySharePercent entity
	 * @throws Exception
	 */
	public static CostGroupDefaultAgencyDaySharePercentage toEntity(CostGroupDefaultAgencyDaySharePercentageVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		CostGroupDefaultAgencyDaySharePercentage entity = new CostGroupDefaultAgencyDaySharePercentageImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			CostGroup costGroup =(CostGroup)AbstractVo.getPersistableObject(persistables, CostGroupImpl.class);
			if(null != costGroup){
				entity.setCostGroup(costGroup);
			}else{
				if (null != vo.getCostGroupVo()) {
					entity.setCostGroup(CostGroupVo.toEntity(null,vo.getCostGroupVo(), false));
				}
			}
			
			if (null != vo.getAgencyVo()) {
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}
			
			entity.setPercentage(vo.getPercentage());
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of CostGroupDefaultAgencyDaySharePercent entities from a collection of CostGroupDefaultAgencyDaySharePercent vos.
	 * 
	 * @param vos
	 * 			the source collection of CostGroupDefaultAgencyDaySharePercent vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of CostGroupDefaultAgencyDaySharePercent entities
	 * @throws Exception
	 */
	public static Collection<CostGroupDefaultAgencyDaySharePercentage> toEntityList(Collection<CostGroupDefaultAgencyDaySharePercentageVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CostGroupDefaultAgencyDaySharePercentage> entities = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();

		for(CostGroupDefaultAgencyDaySharePercentageVo vo : vos){
			entities.add(CostGroupDefaultAgencyDaySharePercentageVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the CostGroupDefaultAgencyDaySharePercentage field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source CostGroupDefaultAgencyDaySharePercentage entity
	 * @throws ValidationException
	 */
	private static void validateEntity(CostGroupDefaultAgencyDaySharePercentage entity) throws ValidationException {
	}

	public static Collection<CostGroupDefaultAgencyDaySharePercentage> toEntityRemoveList(Collection<CostGroupDefaultAgencyDaySharePercentageVo> newList, Collection<CostGroupDefaultAgencyDaySharePercentage> percentages){
		Collection<CostGroupDefaultAgencyDaySharePercentage> removeList = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages))
			return removeList;

		for(CostGroupDefaultAgencyDaySharePercentage p : percentages){
			Boolean found=false;

			for(CostGroupDefaultAgencyDaySharePercentageVo pVo : newList){
				if(p.getId().compareTo(pVo.getId())==0){
					found=true;
					break;
				};
			}
			if(!found){
				removeList.add(p);
			}
		}

		return removeList;
	}
   
	public static Collection<CostGroupDefaultAgencyDaySharePercentage> toEntityAddList(Collection<CostGroupDefaultAgencyDaySharePercentageVo> vos, Collection<CostGroupDefaultAgencyDaySharePercentage> percentages, CostGroup entity) throws Exception {
		Collection<CostGroupDefaultAgencyDaySharePercentage> addList = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages)){
			/*
			 * Add all
			 */
			for(CostGroupDefaultAgencyDaySharePercentageVo pVo : vos){
				CostGroupDefaultAgencyDaySharePercentage p = new CostGroupDefaultAgencyDaySharePercentageImpl();
				p.setCostGroup(entity);
				p.setAgency(AgencyVo.toEntity(null, pVo.getAgencyVo(), false));
				p.getAgency().setAgencyCode(pVo.getAgencyVo().getAgencyCd());
				p.setPercentage(pVo.getPercentage());
				addList.add(p);
			}
			return addList;
		}

		for(CostGroupDefaultAgencyDaySharePercentageVo pVo : vos){
			Boolean found=false;

			for(CostGroupDefaultAgencyDaySharePercentage p : percentages){
				if(p.getId().compareTo(pVo.getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				CostGroupDefaultAgencyDaySharePercentage p = new CostGroupDefaultAgencyDaySharePercentageImpl();
				p.setCostGroup(entity);
				p.setAgency(AgencyVo.toEntity(null, pVo.getAgencyVo(), false));
				p.setPercentage(pVo.getPercentage());
				addList.add(p);
			}
		}

		return addList;
	}

	public static Collection<CostGroupDefaultAgencyDaySharePercentage> toEntityUpdateList(Collection<CostGroupDefaultAgencyDaySharePercentageVo> vos, Collection<CostGroupDefaultAgencyDaySharePercentage> percentages, CostGroup entity) throws Exception {
		Collection<CostGroupDefaultAgencyDaySharePercentage> updateList = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages)){
			return updateList;
		}

		for(CostGroupDefaultAgencyDaySharePercentageVo pVo : vos){
			Boolean found=false;

			for(CostGroupDefaultAgencyDaySharePercentage p : percentages){
				if(LongUtility.hasValue(p.getId()) && LongUtility.hasValue(pVo.getId())){
					if(p.getId().compareTo(pVo.getId())==0){
						p.setPercentage(pVo.getPercentage());
						updateList.add(p);
					}
				}
			}

		}

		return updateList;
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
	 * @return the agencyVo
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param agencyVo the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}


}
