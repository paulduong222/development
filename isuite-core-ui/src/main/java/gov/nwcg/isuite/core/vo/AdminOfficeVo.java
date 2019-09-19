package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.impl.AdminOfficeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminOfficeVo extends AbstractVo implements PersistableVo {
	private String officeName;
	private String phone;
	private Date deletedDate;
	private AddressVo addressVo;
	private Boolean standard;
	private Collection<ContractorAgreementVo> contractorAgreementVos = new ArrayList<ContractorAgreementVo>();

	/**
	 * Constructor
	 */
	public AdminOfficeVo() {
		super();
	}


	public static AdminOfficeVo getInstance(AdminOffice entity, boolean cascadable) throws Exception {
		AdminOfficeVo vo = new AdminOfficeVo();

		if(null == entity)
			throw new Exception("Unable to build AdminOfficeVo instance from null AdminOffice entity");

		vo.setId(entity.getId());
		// set name regardless of cascadable
		vo.setOfficeName(StringUtility.toUpper(entity.getOfficeName()));

		if(cascadable){
			vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
			vo.setOfficeName(StringUtility.toUpper(entity.getOfficeName()));
			vo.setPhone(StringUtility.toUpper(entity.getPhone()));
			vo.setDeletedDate(entity.getDeletedDate());
			vo.setId(entity.getId());
			vo.setStandard(entity.isStandard());
			
			if(null != entity.getContractorAgreements())
				vo.setContractorAgreementVos(ContractorAgreementVo.getInstances(entity.getContractorAgreements(), true));			
		}

		return vo;
	}

	public static Collection<AdminOfficeVo> getInstances(Collection<AdminOffice> entities, boolean cascadable) throws Exception {
		Collection<AdminOfficeVo> vos = new ArrayList<AdminOfficeVo>();

		for(AdminOffice entity : entities){
			vos.add(AdminOfficeVo.getInstance(entity, cascadable));
		}
		return vos;
	}


	public static AdminOffice toEntity(AdminOfficeVo sourceVo,boolean cascadable) throws Exception {
		AdminOffice entity = new AdminOfficeImpl();

		entity.setId(sourceVo.getId());

		if(cascadable){
			entity.setOfficeName(StringUtility.toUpper(sourceVo.getOfficeName()));
			entity.setDeletedDate(sourceVo.getDeletedDate());	
			entity.setPhone(StringUtility.toUpper(sourceVo.getPhone()));
			entity.setAddress(AddressVo.toEntity(sourceVo.getAddressVo(), true));  
			if( (null != sourceVo.getContractorAgreementVos()) && (sourceVo.getContractorAgreementVos().size()>0))
				entity.setContractorAgreements(ContractorAgreementVo.toEntityList(sourceVo.getContractorAgreementVos(),true,entity));
			entity.setStandard(sourceVo.isStandard());
		}
		
		/*
		 * Validate the entity
		 */
		 //validateEntity(entity);

		return entity;
	}
	
	/**
	 * Perform some validation on the incident entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity the source AdminOffice entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AdminOffice entity) throws ValidationException {
		//Validator.validateStringField("officeName", entity.getOfficeName(), 55, true);
		//Validator.validateStringField("phone", entity.getPhone(), 12, false);		
	}

	
	/**
	 * Returns a collection of AdminOfficeVo entities from a collection of AdminOffice vos.
	 * 
	 * @param vos
	 * 			the source collection of AdminOffice vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a AdminOffice entities
	 * @return
	 * 			collection of AdminOffice entities
	 * @throws Exception
	 */
	public static Collection<AdminOffice> toEntityList(Collection<AdminOfficeVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<AdminOffice> entities = new ArrayList<AdminOffice>();

		for(AdminOfficeVo vo : vos){
			entities.add(AdminOfficeVo.toEntity(vo, cascadable));
		}

		return entities;
	}


	/**
	 * @return the officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * @param officeName the officeName to set
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;	
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = StringUtility.removeNonNumeric(phone);
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
	 * @return the addressVo
	 */
	public AddressVo getAddressVo() {
		return addressVo;
	}

	/**
	 * @param addressVo the addressVo to set
	 */
	public void setAddressVo(AddressVo addressVo) {
		this.addressVo = addressVo;
	}
	
	/**
	 * @return standard
	 */
	@JsonProperty("standard")
	public Boolean getStandard() {
		return standard;
	}
	
	/**
	 * @return standard
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return standard;
	}
	
	/**
	 * @param standard
	 */
	@JsonProperty("standard")
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * Returns the contractorAgreementVos.
	 *
	 * @return 
	 *		the contractorAgreementVos to return
	 */
	public Collection<ContractorAgreementVo> getContractorAgreementVos() {
		return contractorAgreementVos;
	}

	/**
	 * Sets the contractorAgreementVos.
	 *
	 * @param contractorAgreementVos 
	 *			the contractorAgreementVos to set
	 */
	public void setContractorAgreementVos(
			Collection<ContractorAgreementVo> contractorAgreementVos) {
		this.contractorAgreementVos = contractorAgreementVos;
	}

}
