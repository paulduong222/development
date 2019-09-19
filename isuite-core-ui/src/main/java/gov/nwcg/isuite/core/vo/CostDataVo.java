package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostData;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.CostDataImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CostDataVo extends AbstractVo {
	private AccrualCodeVo accrualCodeVo;
	private AgencyVo paymentAgencyVo;
	//private Date assignDate;
	private DateTransferVo assignDateVo=new DateTransferVo();
	private Boolean accrualLocked;
	private Boolean useAccrualsOnly;
	private Boolean generateCosts;
	private Boolean generateCostsSys;
	private String costRemarks;
	private String costOther1;
	private String costOther2;
	private String costOther3;
	private CostGroupVo defaultCostGroupVo;
	private IncidentShiftVo defaultIncidentShiftVo;
	
	public CostDataVo(){
		
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
	public static CostDataVo getInstance(CostData entity,boolean cascadable) throws Exception{
		CostDataVo vo = new CostDataVo();

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
			vo.setGenerateCostsSys(StringBooleanEnum.toBooleanValue(entity.getGenerateCostsSys()));
			
			if(DateUtil.hasValue(entity.getAssignDate()))
				DateTransferVo.populateDate(vo.getAssignDateVo(), entity.getAssignDate());
			
			vo.setCostOther1(entity.getCostOther1());
			vo.setCostOther2(entity.getCostOther2());
			vo.setCostOther3(entity.getCostOther3());
			vo.setCostRemarks(entity.getCostRemarks());
			
			if(null != entity.getDefaultCostGroup())
				vo.setDefaultCostGroupVo(CostGroupVo.getInstance(entity.getDefaultCostGroup(), false));

			if(null != entity.getDefaultIncidentShift())
				vo.setDefaultIncidentShiftVo(IncidentShiftVo.getInstance(entity.getDefaultIncidentShift(), false));
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
	public static Collection<CostDataVo> getInstances(Collection<CostData> entities, boolean cascadable) throws Exception {
		Collection<CostDataVo> vos = new ArrayList<CostDataVo>();

		for(CostData entity : entities){
			vos.add(CostDataVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	/**
	 * Returns a CostData entity from a CostData vo.
	 * 
	 * @param vo
	 * 			the source CostData vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of CostData entity
	 * @throws Exception
	 */
	public static CostData toEntity(CostDataVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		CostData entity = new CostDataImpl();

		entity.setId(vo.getId());

		if(cascadable){
			
			entity.setAccrualLocked(vo.getAccrualLocked());
			
			if(DateTransferVo.hasDateString(vo.getAssignDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getAssignDateVo());
				entity.setAssignDate(dt);
			}else
				entity.setAssignDate(null);
			
			entity.setGenerateCosts(vo.getGenerateCosts());
			entity.setGenerateCostsSys(StringBooleanEnum.toEnumValue(vo.getGenerateCostsSys()));
			entity.setUseAccrualsOnly(vo.getUseAccrualsOnly());
			entity.setCostOther1(StringUtility.toUpper(vo.getCostOther1()));
			entity.setCostOther2(StringUtility.toUpper(vo.getCostOther2()));
			entity.setCostOther3(StringUtility.toUpper(vo.getCostOther3()));
			entity.setCostRemarks(StringUtility.toUpper(vo.getCostRemarks()));
			
			if(AbstractVo.hasValue(vo.getPaymentAgencyVo()))
				entity.setPaymentAgency(AgencyVo.toEntity(null, vo.getPaymentAgencyVo(), false));

			if(null != vo.getAccrualCodeVo())
				entity.setAccrualCode(AccrualCodeVo.toEntity(vo.getAccrualCodeVo(), false));

			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);
			
			IncidentResourceOther incidentResourceOtherEntity=(IncidentResourceOther)getPersistableObject(persistables,IncidentResourceOtherImpl.class);
			if(null != incidentResourceOtherEntity)
				entity.setIncidentResourceOther(incidentResourceOtherEntity);

			if(null != vo.getDefaultCostGroupVo())
				entity.setDefaultCostGroup(CostGroupVo.toEntity(null, vo.getDefaultCostGroupVo(), false));
			
			if(null != vo.getDefaultIncidentShiftVo())
				entity.setDefaultIncidentShift(IncidentShiftVo.toEntity(null, vo.getDefaultIncidentShiftVo(), true));

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
	 * @return the costOther1
	 */
	public String getCostOther1() {
		return costOther1;
	}

	/**
	 * @param costOther1 the costOther1 to set
	 */
	public void setCostOther1(String costOther1) {
		this.costOther1 = costOther1;
	}

	/**
	 * @return the costOther2
	 */
	public String getCostOther2() {
		return costOther2;
	}

	/**
	 * @param costOther2 the costOther2 to set
	 */
	public void setCostOther2(String costOther2) {
		this.costOther2 = costOther2;
	}

	/**
	 * @return the costOther3
	 */
	public String getCostOther3() {
		return costOther3;
	}

	/**
	 * @param costOther3 the costOther3 to set
	 */
	public void setCostOther3(String costOther3) {
		this.costOther3 = costOther3;
	}

	/**
	 * @return the defaultCostGroupVo
	 */
	public CostGroupVo getDefaultCostGroupVo() {
		return defaultCostGroupVo;
	}

	/**
	 * @param defaultCostGroupVo the defaultCostGroupVo to set
	 */
	public void setDefaultCostGroupVo(CostGroupVo defaultCostGroupVo) {
		this.defaultCostGroupVo = defaultCostGroupVo;
	}

	/**
	 * @return the defaultIncidentShiftVo
	 */
	public IncidentShiftVo getDefaultIncidentShiftVo() {
		return defaultIncidentShiftVo;
	}

	/**
	 * @param defaultIncidentShiftVo the defaultIncidentShiftVo to set
	 */
	public void setDefaultIncidentShiftVo(IncidentShiftVo defaultIncidentShiftVo) {
		this.defaultIncidentShiftVo = defaultIncidentShiftVo;
	}

	/**
	 * @return the generateCostsSys
	 */
	public Boolean getGenerateCostsSys() {
		return generateCostsSys;
	}

	/**
	 * @param generateCostsSys the generateCostsSys to set
	 */
	public void setGenerateCostsSys(Boolean generateCostsSys) {
		this.generateCostsSys = generateCostsSys;
	}

	/**
	 * @return the assignDateVo
	 */
	public DateTransferVo getAssignDateVo() {
		return assignDateVo;
	}

	/**
	 * @param assignDateVo the assignDateVo to set
	 */
	public void setAssignDateVo(DateTransferVo assignDateVo) {
		this.assignDateVo = assignDateVo;
	}


}
