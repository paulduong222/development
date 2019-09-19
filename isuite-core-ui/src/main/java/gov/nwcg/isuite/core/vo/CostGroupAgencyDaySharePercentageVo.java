package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class CostGroupAgencyDaySharePercentageVo extends AbstractVo {
	
	private CostGroupAgencyDayShareVo costGroupAgencyDayShareVo;
	private AgencyVo agencyVo;
	private BigDecimal percentage;
	
	public CostGroupAgencyDaySharePercentageVo(){
		
	}

	/**
	 * Returns a CostGroupAgencyDaySharePercentageVo from a CostGroupAgencyDaySharePercent entity.
	 * 
	 * @param entity
	 * 			the source CostGroupAgencyDaySharePercent entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of CostGroupAgencyDaySharePercentageVo
	 * @throws Exception
	 */
	public static CostGroupAgencyDaySharePercentageVo getInstance(CostGroupAgencyDaySharePercentage entity,boolean cascadable) throws Exception{
		CostGroupAgencyDaySharePercentageVo vo = new CostGroupAgencyDaySharePercentageVo();

		if(null == entity)
			throw new Exception("Unable to create CostGroupAgencyDaySharePercentageVo from null CostGroupAgencyDaySharePercent entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if (null != entity.getCostGroupAgencyDayShare()) {
				vo.setCostGroupAgencyDayShareVo(CostGroupAgencyDayShareVo.getInstance(entity.getCostGroupAgencyDayShare(), false));
			}
			if (null != entity.getAgency()) {
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}
			vo.setPercentage(entity.getPercentage());
		}

		return vo;
	}

	/**
	 * Returns a collection of CostGroupAgencyDaySharePercentageVos from a collection of CostGroupAgencyDaySharePercent entities.
	 * 
	 * @param entities
	 * 			the source collection of CostGroupAgencyDaySharePercent entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of CostGroupAgencyDaySharePercent vos
	 * @throws Exception
	 */
	public static Collection<CostGroupAgencyDaySharePercentageVo> getInstances(Collection<CostGroupAgencyDaySharePercentage> entities, boolean cascadable) throws Exception {
		Collection<CostGroupAgencyDaySharePercentageVo> vos = new ArrayList<CostGroupAgencyDaySharePercentageVo>();

		for(CostGroupAgencyDaySharePercentage entity : entities){
			vos.add(CostGroupAgencyDaySharePercentageVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostGroupAgencyDaySharePercent entity from a CostGroupAgencyDaySharePercent vo.
	 * 
	 * @param vo
	 * 			the source CostGroupAgencyDaySharePercent vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostGroupAgencyDaySharePercent entity
	 * @throws Exception
	 */
	public static CostGroupAgencyDaySharePercentage toEntity(CostGroupAgencyDaySharePercentageVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		CostGroupAgencyDaySharePercentage entity = new CostGroupAgencyDaySharePercentageImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			CostGroupAgencyDayShare dayShare=(CostGroupAgencyDayShare)AbstractVo.getPersistableObject(persistables, CostGroupAgencyDayShareImpl.class);
			if(null != dayShare){
				entity.setCostGroupAgencyDayShare(dayShare);
			}else{
				if (null != vo.getCostGroupAgencyDayShareVo()) {
					entity.setCostGroupAgencyDayShare(CostGroupAgencyDayShareVo.toEntity(null,vo.getCostGroupAgencyDayShareVo(), false));
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
	 * Returns a collection of CostGroupAgencyDaySharePercent entities from a collection of CostGroupAgencyDaySharePercent vos.
	 * 
	 * @param vos
	 * 			the source collection of CostGroupAgencyDaySharePercent vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of CostGroupAgencyDaySharePercent entities
	 * @throws Exception
	 */
	public static Collection<CostGroupAgencyDaySharePercentage> toEntityList(Collection<CostGroupAgencyDaySharePercentageVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CostGroupAgencyDaySharePercentage> entities = new ArrayList<CostGroupAgencyDaySharePercentage>();

		for(CostGroupAgencyDaySharePercentageVo vo : vos){
			entities.add(CostGroupAgencyDaySharePercentageVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the CostGroupAgencyDaySharePercentage field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source CostGroupAgencyDaySharePercentage entity
	 * @throws ValidationException
	 */
	private static void validateEntity(CostGroupAgencyDaySharePercentage entity) throws ValidationException {
	}

	public static Collection<CostGroupAgencyDaySharePercentage> toEntityRemoveList(Collection<CostGroupAgencyDaySharePercentageVo> newList, Collection<CostGroupAgencyDaySharePercentage> percentages){
		Collection<CostGroupAgencyDaySharePercentage> removeList = new ArrayList<CostGroupAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages))
			return removeList;

		for(CostGroupAgencyDaySharePercentage p : percentages){
			Boolean found=false;

			for(CostGroupAgencyDaySharePercentageVo pVo : newList){
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
   
	public static Collection<CostGroupAgencyDaySharePercentage> toEntityAddList(Collection<CostGroupAgencyDaySharePercentageVo> vos, Collection<CostGroupAgencyDaySharePercentage> percentages, CostGroupAgencyDayShare entity) throws Exception {
		Collection<CostGroupAgencyDaySharePercentage> addList = new ArrayList<CostGroupAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages)){
			/*
			 * Add all
			 */
			for(CostGroupAgencyDaySharePercentageVo pVo : vos){
				CostGroupAgencyDaySharePercentage p = new CostGroupAgencyDaySharePercentageImpl();
				p.setCostGroupAgencyDayShare(entity);
				p.setAgency(AgencyVo.toEntity(null, pVo.getAgencyVo(), false));
				p.getAgency().setAgencyCode(pVo.getAgencyVo().getAgencyCd());
				p.setPercentage(pVo.getPercentage());
				addList.add(p);
			}
			return addList;
		}

		for(CostGroupAgencyDaySharePercentageVo pVo : vos){
			Boolean found=false;

			for(CostGroupAgencyDaySharePercentage p : percentages){
				if(p.getId().compareTo(pVo.getId())==0){
					found=true;
					break;
				};
			}

			if(!found){
				CostGroupAgencyDaySharePercentage p = new CostGroupAgencyDaySharePercentageImpl();
				p.setCostGroupAgencyDayShare(entity);
				p.setAgency(AgencyVo.toEntity(null, pVo.getAgencyVo(), false));
				p.setPercentage(pVo.getPercentage());
				addList.add(p);
			}
		}

		return addList;
	}

	public static Collection<CostGroupAgencyDaySharePercentage> toEntityUpdateList(Collection<CostGroupAgencyDaySharePercentageVo> vos, Collection<CostGroupAgencyDaySharePercentage> percentages, CostGroupAgencyDayShare entity) throws Exception {
		Collection<CostGroupAgencyDaySharePercentage> updateList = new ArrayList<CostGroupAgencyDaySharePercentage>();

		if(!CollectionUtility.hasValue(percentages)){
			return updateList;
		}

		for(CostGroupAgencyDaySharePercentageVo pVo : vos){
			Boolean found=false;

			for(CostGroupAgencyDaySharePercentage p : percentages){
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
	 * @param costGroupAgencyDayShareVo the costGroupAgencyDayShareVo to set
	 */
	public void setCostGroupAgencyDayShareVo(CostGroupAgencyDayShareVo costGroupAgencyDayShareVo) {
		this.costGroupAgencyDayShareVo = costGroupAgencyDayShareVo;
	}

	/**
	 * @return the costGroupAgencyDayShareVo
	 */
	public CostGroupAgencyDayShareVo getCostGroupAgencyDayShareVo() {
		return costGroupAgencyDayShareVo;
	}

	/**
	 * @param agencyVo the agencyVo to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * @return the agencyVo
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	/**
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}


}
