package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupDefaultAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CostGroupVo extends AbstractVo {
	private IncidentVo incidentVo;
	private String costGroupName;
	private String description;
	private Date startDate;
	private IncidentShiftVo incidentShiftVo;
	private Collection<ResourceVo> resourceVos = new ArrayList<ResourceVo>();
	private Collection<ResourceOtherVo> resourceOtherVos = new ArrayList<ResourceOtherVo>();
	private Collection<CostGroupAgencyDayShareVo> costGroupAgencyDayShareVos = new ArrayList<CostGroupAgencyDayShareVo>();
	private Collection<CostGroupDefaultAgencyDaySharePercentageVo> costGroupDefaultAgencyDaySharePercentageVos = new ArrayList<CostGroupDefaultAgencyDaySharePercentageVo>();
	
	public CostGroupVo(){
		
	}

	/**
	 * Returns a CostGroupVo from a CostGroup entity.
	 * 
	 * @param entity
	 * 			the source CostGroup entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of CostGroupVo
	 * @throws Exception
	 */
	public static CostGroupVo getInstance(CostGroup entity,boolean cascadable) throws Exception{
		CostGroupVo vo = new CostGroupVo();

		if(null == entity)
			throw new Exception("Unable to create CostGroupVo from null CostGroup entity.");

		vo.setId(entity.getId());

		if(cascadable){
			if (null != entity.getIncident()) {
				IncidentVo ivo = new IncidentVo();
				ivo.setId(entity.getIncident().getId());
				ivo.setIncidentName(entity.getIncident().getIncidentName());
				vo.setIncidentVo(ivo);
				//vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			}
			vo.setCostGroupName(entity.getCostGroupName());
			vo.setDescription(entity.getDescription());
			vo.setStartDate(entity.getStartDate());
			if (null != entity.getIncidentShift()) {
				vo.setIncidentShiftVo(IncidentShiftVo.getInstance(entity.getIncidentShift(), true));
			}
			if (null != entity.getResources()) {
				vo.setResourceVos(ResourceVo.getInstances(entity.getResources(), true));
			}
			if (null != entity.getResourceOthers()) {
				vo.setResourceOtherVos(ResourceOtherVo.getInstances(entity.getResourceOthers(), true));
			}
			if (null != entity.getCostGroupDfAgPcts()) {
				vo.setCostGroupDefaultAgencyDaySharePercentageVos(CostGroupDefaultAgencyDaySharePercentageVo.getInstances(entity.getCostGroupDfAgPcts(), true));
			}
			
			if (null != entity.getCostGroupAgencyDayShares()) {
				vo.setCostGroupAgencyDayShareVos(CostGroupAgencyDayShareVo.getInstances(entity.getCostGroupAgencyDayShares(), true));
			}
		}

		return vo;
	}

	/**
	 * Returns a collection of CostGroupVos from a collection of CostGroup entities.
	 * 
	 * @param entities
	 * 			the source collection of CostGroup entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of CostGroup vos
	 * @throws Exception
	 */
	public static Collection<CostGroupVo> getInstances(Collection<CostGroup> entities, boolean cascadable) throws Exception {
		Collection<CostGroupVo> vos = new ArrayList<CostGroupVo>();

		for(CostGroup entity : entities){
			if(!DateUtil.hasValue(entity.getDeletedDate()))
				vos.add(CostGroupVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostGroup entity from a CostGroup vo.
	 * 
	 * @param vo
	 * 			the source CostGroup vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostGroup entity
	 * @throws Exception
	 */
	public static CostGroup toEntity(CostGroup entity, CostGroupVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		
		if(null == entity) entity = new CostGroupImpl();

		entity.setId(vo.getId());

		if(cascadable){
			if (null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
			
			entity.setCostGroupName(vo.getCostGroupName());
			entity.setDescription(vo.getDescription());
			entity.setStartDate(vo.getStartDate());
			
			if (null != vo.getIncidentShiftVo()) {
				entity.setIncidentShift(IncidentShiftVo.toEntity(null, vo.getIncidentShiftVo(), false));
			}
			
			if (null != vo.getResourceVos()) {
				entity.setResources(ResourceVo.toEntityList(vo.getResourceVos(), true));
			}
			
			if (null != vo.getResourceOtherVos()) {
				entity.setResourceOthers(ResourceOtherVo.toEntityList(vo.getResourceOtherVos(), true));
			}

			
			Collection<CostGroupDefaultAgencyDaySharePercentage> removeList = 
				CostGroupDefaultAgencyDaySharePercentageVo.toEntityRemoveList(vo.getCostGroupDefaultAgencyDaySharePercentageVos(),entity.getCostGroupDfAgPcts());

			Collection<CostGroupDefaultAgencyDaySharePercentage> addList = 
				CostGroupDefaultAgencyDaySharePercentageVo.toEntityAddList(vo.getCostGroupDefaultAgencyDaySharePercentageVos(),entity.getCostGroupDfAgPcts(),entity);
			
			Collection<CostGroupDefaultAgencyDaySharePercentage> updateList = 
				CostGroupDefaultAgencyDaySharePercentageVo.toEntityUpdateList(vo.getCostGroupDefaultAgencyDaySharePercentageVos(),entity.getCostGroupDfAgPcts(),entity);

			if(CollectionUtility.hasValue(removeList)){
				for(CostGroupDefaultAgencyDaySharePercentage p : removeList){
					entity.getCostGroupDfAgPcts().remove(p);
				}
			}
			
			if(CollectionUtility.hasValue(addList))
				entity.getCostGroupDfAgPcts().addAll(addList);
			
			if(CollectionUtility.hasValue(updateList)){
				Collection<CostGroupDefaultAgencyDaySharePercentage> newList = new ArrayList<CostGroupDefaultAgencyDaySharePercentage>();
				for(CostGroupDefaultAgencyDaySharePercentage p : entity.getCostGroupDfAgPcts()){
					boolean bfound=false;
					
					for(CostGroupDefaultAgencyDaySharePercentage p2 : updateList){
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
				
				entity.getCostGroupDfAgPcts().clear();
				entity.getCostGroupDfAgPcts().addAll(newList);
			}
			
//			if (null != vo.getCostGroupAgencyDayShareVos()) {
//				entity.setCostGroupAgencyDayShares(CostGroupAgencyDayShareVo.toEntityList(vo.getCostGroupAgencyDayShareVos(), false));
//			}
			
			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of CostGroup entities from a collection of CostGroup vos.
	 * 
	 * @param vos
	 * 			the source collection of CostGroup vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of CostGroup entities
	 * @throws Exception
	 */
	public static Collection<CostGroup> toEntityList(Collection<CostGroupVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CostGroup> entities = new ArrayList<CostGroup>();

		for(CostGroupVo vo : vos){
			entities.add(CostGroupVo.toEntity(null, vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the CostGroup field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source CostGroup entity
	 * @throws ValidationException
	 */
	private static void validateEntity(CostGroup entity) throws ValidationException {
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
	 * @param costGroupName the costGroupName to set
	 */
	public void setCostGroupName(String costGroupName) {
		this.costGroupName = costGroupName;
	}

	/**
	 * @return the costGroupName
	 */
	public String getCostGroupName() {
		return costGroupName;
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
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param incidentShiftVo
	 */
	public void setIncidentShiftVo(IncidentShiftVo incidentShiftVo) {
		this.incidentShiftVo = incidentShiftVo;
	}

	/**
	 * @return the incidentShiftVo
	 */
	public IncidentShiftVo getIncidentShiftVo() {
		return incidentShiftVo;
	}

	/**
	 * @param resourceVos the resourceVos to set
	 */
	public void setResourceVos(Collection<ResourceVo> resourceVos) {
		this.resourceVos = resourceVos;
	}

	/**
	 * @return the resourceVos
	 */
	public Collection<ResourceVo> getResourceVos() {
		return resourceVos;
	}

	/**
	 * @param resourceOtherVos the resourceOtherVos to set
	 */
	public void setResourceOtherVos(Collection<ResourceOtherVo> resourceOtherVos) {
		this.resourceOtherVos = resourceOtherVos;
	}

	/**
	 * @return the resourceOtherVos
	 */
	public Collection<ResourceOtherVo> getResourceOtherVos() {
		return resourceOtherVos;
	}

	/**
	 * @param costGroupAgencyDayShareVos the costGroupAgencyDayShareVos to set
	 */
	public void setCostGroupAgencyDayShareVos(
			Collection<CostGroupAgencyDayShareVo> costGroupAgencyDayShareVos) {
		this.costGroupAgencyDayShareVos = costGroupAgencyDayShareVos;
	}

	/**
	 * @return the costGroupAgencyDayShareVos
	 */
	public Collection<CostGroupAgencyDayShareVo> getCostGroupAgencyDayShareVos() {
		return costGroupAgencyDayShareVos;
	}

	/**
	 * @return the costGroupDefaultAgencyDaySharePercentageVos
	 */
	public Collection<CostGroupDefaultAgencyDaySharePercentageVo> getCostGroupDefaultAgencyDaySharePercentageVos() {
		return costGroupDefaultAgencyDaySharePercentageVos;
	}

	/**
	 * @param costGroupDefaultAgencyDaySharePercentageVos the costGroupDefaultAgencyDaySharePercentageVos to set
	 */
	public void setCostGroupDefaultAgencyDaySharePercentageVos(
			Collection<CostGroupDefaultAgencyDaySharePercentageVo> costGroupDefaultAgencyDaySharePercentageVos) {
		this.costGroupDefaultAgencyDaySharePercentageVos = costGroupDefaultAgencyDaySharePercentageVos;
	}

}
