package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class ContractorAgreementVo extends AbstractVo {
	private AdminOfficeVo adminOfficeVo;
	private Long adminOfficeId;
	private String agreementNumber;
	//private Date startDate;
	private DateTransferVo startDateVo = new DateTransferVo();
	//private Date endDate;
	private DateTransferVo endDateVo = new DateTransferVo();
	private String pointOfHire;
	private ContractorVo contractorVo;
	private Boolean enabled;
	private Date deletedDate;
	private ContractorAgreementNumberHistoryVo contractorAgreementNumberHistoryVo;

	public ContractorAgreementVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ContractorAgreementVo getInstance(ContractorAgreement entity, Boolean cascadable) throws Exception {
		ContractorAgreementVo vo = new ContractorAgreementVo();
		
		if(null==entity)
			throw new Exception("Unable to create contractorAgreementVo from null entity.");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setAgreementNumber(entity.getAgreementNumber());
			if(DateUtil.hasValue(entity.getEndDate())){
				DateTransferVo.populateDate(vo.getEndDateVo(), entity.getEndDate());
			}
			if(DateUtil.hasValue(entity.getStartDate())){
				DateTransferVo.populateDate(vo.getStartDateVo(), entity.getStartDate());
			}
			//vo.setStartDate(entity.getStartDate());
			vo.setPointOfHire(entity.getPointOfHire());
			vo.setDeletedDate(entity.getDeletedDate());
			vo.setEnabled(entity.isEnabled());

			if(LongUtility.hasValue(entity.getContractorId())){
				ContractorVo cvo = new ContractorVo();
				cvo.setId(entity.getContractorId());
				vo.setContractorVo(cvo);
			}

			if(null != entity.getAdminOffice()){
				vo.setAdminOfficeVo(AdminOfficeVo.getInstance(entity.getAdminOffice(), false));
			}
		}
		
		return vo;
		
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<ContractorAgreementVo> getInstances(Collection<ContractorAgreement> entities,Boolean cascadable) throws Exception {
		Collection<ContractorAgreementVo> vos = new ArrayList<ContractorAgreementVo>();
		
		for(ContractorAgreement entity : entities){
			// exclude deleted ones
			if(!DateUtil.hasValue(entity.getDeletedDate()))
				vos.add(ContractorAgreementVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ContractorAgreement toEntity(ContractorAgreement entity, ContractorAgreementVo vo, Boolean cascadable, Persistable...persistables) throws Exception {
		
		if(null==entity) 
			entity =new ContractorAgreementImpl();
		
		if(null==vo)
			throw new Exception("Unable to create contractorAgreement entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setAgreementNumber(vo.getAgreementNumber());
			if(DateTransferVo.hasDateString(vo.getStartDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getStartDateVo());
				entity.setStartDate(dt);
			}
			
			if(DateTransferVo.hasDateString(vo.getEndDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getEndDateVo());
				entity.setEndDate(dt);
			}else
				entity.setEndDate(null);
			entity.setPointOfHire(vo.getPointOfHire());
			entity.setDeletedDate(vo.getDeletedDate());
			entity.setEnabled(vo.getEnabled());

			if(AbstractVo.hasValue(vo.getAdminOfficeVo())){
				entity.setAdminOffice(AdminOfficeVo.toEntity(vo.getAdminOfficeVo(), false));
			}
				
			// AdminOfficeVo needs to create ContractorAgreementVo object. ContractorAgreementVo should possibly only return AdminOfficeId.
//			if( (null != vo.getAdminOfficeVo()) && (vo.getAdminOfficeVo().getId()>0) )
//				entity.setAdminOffice(AdminOfficeVo.toEntity(vo.getAdminOfficeVo(), false));
	
			
			Contractor contractorEntity = (Contractor)getPersistableObject(persistables,ContractorImpl.class);
			if(null != contractorEntity)
				entity.setContractor(contractorEntity);
			else if(AbstractVo.hasValue(vo.getContractorVo())){
				entity.setContractor(ContractorVo.toEntity(null, vo.getContractorVo(), false));
			}
			
			//validateEntity(entity);
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
	public static Collection<ContractorAgreement> toEntityList(Collection<ContractorAgreementVo> vos, Boolean cascadable, Persistable... persistables) throws Exception {
		Collection<ContractorAgreement> entities = new ArrayList<ContractorAgreement>();
		
		for(ContractorAgreementVo vo : vos){
			entities.add(ContractorAgreementVo.toEntity(null, vo,cascadable,persistables));
		}
		return entities;
	}
	
	/**
	 * Perform some validation on the ContractorAgreement entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source ContractorAgreement entity
	 * @throws ValidationException
	 */
	private static void validateEntity(ContractorAgreement entity) throws ValidationException {
		Validator.validateStringField("agreementNumber",entity.getAgreementNumber().toString(),30,true);
		//point of hire is not required
		if (null != entity.getPointOfHire()) {
			Validator.validateStringField("pointOfHire",entity.getPointOfHire().toString(),30,false);
		}
	}
	
	
	/**
	 * Returns the adminOfficeId.
	 *
	 * @return 
	 *		the adminOfficeId to return
	 */
	public Long getAdminOfficeId() {
		return adminOfficeId;
	}

	/**
	 * Sets the adminOfficeId.
	 *
	 * @param adminOfficeId 
	 *			the adminOfficeId to set
	 */
	public void setAdminOfficeId(Long adminOfficeId) {
		this.adminOfficeId = adminOfficeId;
	}

	

	/**
	 * Returns the adminOfficeVo.
	 *
	 * @return 
	 *		the adminOfficeVo to return
	 */
	public AdminOfficeVo getAdminOfficeVo() {
		return adminOfficeVo;
	}

	/**
	 * Sets the adminOfficeVo.
	 *
	 * @param adminOfficeVo 
	 *			the adminOfficeVo to set
	 */
	public void setAdminOfficeVo(AdminOfficeVo adminOfficeVo) {
		this.adminOfficeVo = adminOfficeVo;
	}

	/**
	 * Returns the agreementNumber.
	 *
	 * @return 
	 *		the agreementNumber to return
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * Sets the agreementNumber.
	 *
	 * @param agreementNumber 
	 *			the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}


	/**
	 * Returns the pointOfHire.
	 *
	 * @return 
	 *		the pointOfHire to return
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * Sets the pointOfHire.
	 *
	 * @param pointOfHire 
	 *			the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}
	
	/**
	 * @param contractorVo the contractorVo to set
	 */
	public void setContractorVo(ContractorVo contractorVo) {
		this.contractorVo = contractorVo;
	}

	/**
	 * @return the contractorVo
	 */
	public ContractorVo getContractorVo() {
		return contractorVo;
	}
	
	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @param enabled the enabled to set
	 */
	@JsonProperty("enabled")
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the enabled
	 */
	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return enabled;
	}
	
	/**
	 * @return enabled
	 */
	@JsonIgnore
	public Boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param contractorAgreementNumberHistoryVo the contractorAgreementNumberHistoryVo to set
	 */
	public void setContractorAgreementNumberHistoryVo(ContractorAgreementNumberHistoryVo contractorAgreementNumberHistoryVo) {
		this.contractorAgreementNumberHistoryVo = contractorAgreementNumberHistoryVo;
	}

	/**
	 * @return the contractorAgreementNumberHistoryVo
	 */
	public ContractorAgreementNumberHistoryVo getContractorAgreementNumberHistoryVo() {
		return contractorAgreementNumberHistoryVo;
	}

	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}

}
