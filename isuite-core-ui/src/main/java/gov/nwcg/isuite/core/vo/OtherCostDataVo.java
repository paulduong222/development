package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class OtherCostDataVo extends AbstractVo {
	private AgencyVo paymentAgencyVo;
	private Date assignDate;
	private AccrualCodeVo accrualCodeVo;
	private Boolean accrualLocked;
	private Boolean useAccrualsOnly;
	private AssignmentStatusVo assignmentStatusVo;
	private Boolean generateCosts;
	private String costRemarks;

	public OtherCostDataVo(){
		
	}

	/**
	 * Returns a CostDataVo from a CostData entity.
	 * 
	 * @param entity
	 * 			the source CostData entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of CostDataVo
	 * @throws Exception
	 */
	public static OtherCostDataVo getInstance(CostData entity,boolean cascadable) throws Exception{
		OtherCostDataVo vo = new OtherCostDataVo();

		if(null == entity)
			throw new Exception("Unable to create CostDataVo from null CostData entity.");

		vo.setId(entity.getId());

		if(cascadable){
			
			if(null != entity.getAccrualCode())
				vo.setAccrualCodeVo(AccrualCodeVo.getInstance(entity.getAccrualCode(), true));

			if(null != entity.getPaymentAgency())
				vo.setPaymentAgencyVo(AgencyVo.getInstance(entity.getPaymentAgency(), true));
			
			vo.setAccrualLocked(entity.getAccrualLocked());
			vo.setUseAccrualsOnly(entity.getUseAccrualsOnly());
			vo.setGenerateCosts(entity.getGenerateCosts());
			vo.setAssignDate(entity.getAssignDate());
			vo.setCostRemarks(entity.getCostRemarks());

			//TODO MANU: Not available yet in the CostData entity/doma. 
			//Check with Trudi if status is required. Not mentioned
			//in the use case but mentioned in the prototype UI
			//vo.setAssignmentStatusVo(enitity.getAssignmentStatusVo); 

			
		}

		return vo;
	}

	/**
	 * Returns a collection of CostDataVos from a collection of CostData entities.
	 * 
	 * @param entities
	 * 			the source collection of CostData entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of CostData vos
	 * @throws Exception
	 */
	public static Collection<OtherCostDataVo> getInstances(Collection<CostData> entities, boolean cascadable) throws Exception {
		Collection<OtherCostDataVo> vos = new ArrayList<OtherCostDataVo>();

		for(CostData entity : entities){
			vos.add(OtherCostDataVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostData entity from a OtherCostDataVo vo.
	 * 
	 * @param vo
	 * 			the source OtherCostDataVo vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostData entity
	 * @throws Exception
	 */
	public static CostData toEntity(OtherCostDataVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		CostData entity = new CostDataImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			entity.setAccrualLocked(vo.getAccrualLocked());
			entity.setAssignDate(vo.getAssignDate());
			entity.setGenerateCosts(vo.getGenerateCosts());
			entity.setUseAccrualsOnly(vo.getUseAccrualsOnly());
//			entity.setCostOther1(vo.getCostOther1());
//			entity.setCostOther2(vo.getCostOther2());
//			entity.setCostOther3(vo.getCostOther3());
			entity.setCostRemarks(vo.getCostRemarks());
			
			if(null != vo.getPaymentAgencyVo())
				entity.setPaymentAgency(AgencyVo.toEntity(null, vo.getPaymentAgencyVo(), false));

			if(null != vo.getAccrualCodeVo())
				entity.setAccrualCode(AccrualCodeVo.toEntity(vo.getAccrualCodeVo(), false));

			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Returns a collection of CostData entities from a collection of CostData vos.
	 * 
	 * @param vos
	 * 			the source collection of CostData vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of CostData entities
	 * @throws Exception
	 */
	public static Collection<CostData> toEntityList(Collection<CostDataVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<CostData> entities = new ArrayList<CostData>();

		for(CostDataVo vo : vos){
			entities.add(CostDataVo.toEntity(vo, cascadable, persistables));
		}

		return entities;
	}

	/**
	 * Perform some validation on the CostData field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source CostData entity
	 * @throws ValidationException
	 */
	private static void validateEntity(CostData entity) throws ValidationException {
	}

	/**
	 * @return the accrualCodeVo
	 */
	public AccrualCodeVo getAccrualCodeVo() {
		return accrualCodeVo;
	}

	/**
	 * @param accrualCodeVo the accrualCodeVo to set
	 */
	public void setAccrualCodeVo(AccrualCodeVo accrualCodeVo) {
		this.accrualCodeVo = accrualCodeVo;
	}

	/**
	 * @return the paymentAgencyVo
	 */
	public AgencyVo getPaymentAgencyVo() {
		return paymentAgencyVo;
	}

	/**
	 * @param paymentAgencyVo the paymentAgencyVo to set
	 */
	public void setPaymentAgencyVo(AgencyVo paymentAgencyVo) {
		this.paymentAgencyVo = paymentAgencyVo;
	}

	/**
	 * @return the assignDate
	 */
	public Date getAssignDate() {
		return assignDate;
	}

	/**
	 * @param assignDate the assignDate to set
	 */
	public void setAssignDate(Date assignDate) {
		this.assignDate = assignDate;
	}

	/**
	 * @return the accrualLocked
	 */
	public Boolean getAccrualLocked() {
		return accrualLocked;
	}

	/**
	 * @param accrualLocked the accrualLocked to set
	 */
	public void setAccrualLocked(Boolean accrualLocked) {
		this.accrualLocked = accrualLocked;
	}

	/**
	 * @return the useAccrualsOnly
	 */
	public Boolean getUseAccrualsOnly() {
		return useAccrualsOnly;
	}

	/**
	 * @param useAccrualsOnly the useAccrualsOnly to set
	 */
	public void setUseAccrualsOnly(Boolean useAccrualsOnly) {
		this.useAccrualsOnly = useAccrualsOnly;
	}

	/**
	 * @return the generateCosts
	 */
	public Boolean getGenerateCosts() {
		return generateCosts;
	}

	/**
	 * @param generateCosts the generateCosts to set
	 */
	public void setGenerateCosts(Boolean generateCosts) {
		this.generateCosts = generateCosts;
	}

	/**
	 * @return the costRemarks
	 */
	public String getCostRemarks() {
		return costRemarks;
	}

	/**
	 * @param costRemarks the costRemarks to set
	 */
	public void setCostRemarks(String costRemarks) {
		this.costRemarks = costRemarks;
	}

	/**
	 * @return the assignmentStatusVo
	 */
	public AssignmentStatusVo getAssignmentStatusVo() {
		return assignmentStatusVo;
	}

	/**
	 * @param assignmentStatusVo the assignmentStatusVo to set
	 */
	public void setAssignmentStatusVo(AssignmentStatusVo assignmentStatusVo) {
		this.assignmentStatusVo = assignmentStatusVo;
	}
	
}
