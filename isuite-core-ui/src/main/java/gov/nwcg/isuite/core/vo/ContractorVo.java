package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.impl.ContractorImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class ContractorVo extends AbstractVo {
	public static String DISPLAY_TIN="888888888";
	private String name;
	private String tin;
	private String verifyTin;
	private String duns;
	private String phone;
    private String fax;
    private AddressVo addressVo;
	private Date deletedDate;
	private Boolean enabled;
	private Collection<IncidentVo> incidentVos;
	private Collection<WorkAreaVo> workAreaVos;
	private Collection<ContractorAgreementVo> contractorAgreementVos = new ArrayList<ContractorAgreementVo>();
	private IncidentVo incidentVo;
	private String actualTin="";
	
	public ContractorVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ContractorVo getInstance(Contractor entity, Boolean cascadable) throws Exception {
		ContractorVo vo = new ContractorVo();
		
		if(null==entity)
			throw new Exception("Unable to create contractorVo from null entity.");

		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setName(entity.getName());
			
			if(StringUtility.hasValue(entity.getTin())) {
				/*
				FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
				byte[] bytes = Base64.decode(entity.getTin());
				vo.setActualTin(new String(enc.decrypt(bytes)));	
				vo.setTin(DISPLAY_TIN);
				vo.setVerifyTin(DISPLAY_TIN);
				*/
			}
			
//			vo.setTin(entity.getTin());
//			vo.setVerifyTin(entity.getTin());
			
			vo.setDuns(entity.getDuns());
			vo.setFax(entity.getFax());
			vo.setPhone(entity.getPhone());
			vo.setEnabled(entity.isEnabled());
			
			if(null != entity.getAddress()) {
				vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
			}

			if(null != entity.getContractorAgreements()){
				vo.setContractorAgreementVos(ContractorAgreementVo.getInstances(entity.getContractorAgreements(), true));
			}
			
			if(CollectionUtility.hasValue(entity.getIncidents())){
				Incident inc = entity.getIncidents().iterator().next();
				IncidentVo incVo=IncidentVo.getInstance(inc, false);
				incVo.setIncidentName(inc.getIncidentName());
				vo.setIncidentVo(incVo);
				
				vo.setIncidentVos(IncidentVo.getInstances(entity.getIncidents(), false));
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
	public static Collection<ContractorVo> getInstances(Collection<Contractor> entities,Boolean cascadable) throws Exception {
		Collection<ContractorVo> vos = new ArrayList<ContractorVo>();
		
		for(Contractor entity : entities){
			vos.add(ContractorVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Contractor toEntity(Contractor entity,ContractorVo vo, Boolean cascadable) throws Exception {
		if(null==entity)
			entity = new ContractorImpl();
		
		if(null==vo)
			throw new Exception("Unable to create contractor entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setName(vo.getName());
			
			if(StringUtility.hasValue(vo.getTin())){
				if(!vo.getTin().equals(DISPLAY_TIN)){
					/*
					FIPSEncryptor enc = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
					byte[] encryptedBytes = enc.encrypt(vo.getTin().getBytes());
					entity.setTin(Base64.encode(encryptedBytes));
					*/
				}
			} else {
				if(StringUtility.hasValue(vo.getActualTin())){
					entity.setTin(vo.getActualTin());
				}else
					entity.setTin(null);
			}
			
			entity.setDuns(vo.getDuns());
			entity.setFax(vo.getFax());
			entity.setPhone(vo.getPhone());
			entity.setEnabled(vo.getEnabled());
			
			if( null != vo.getAddressVo() && (!vo.getAddressVo().isEmpty() || vo.getId() != 0 ))
				entity.setAddress(AddressVo.toEntity(entity.getAddress(), vo.getAddressVo(), true, entity));
			
		}
		
		return entity;
	}

	/**
	 * Perform some validation on the Contractor entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source Contractor entity
	 * @throws ValidationException
	 */
	private static void validateEntity(Contractor entity) throws ValidationException {
		Validator.validateStringField("name",entity.getName().toString(),50,true);
		if (null != entity.getPhone()) {
			Validator.validateStringField("phone",entity.getPhone().toString(),12,false);
		}
		//Validator.validateStringField("fax",entity.getFax().toString(),12,false);
		if (null != entity.getDuns()) {
			Validator.validateStringField("duns",entity.getDuns().toString(),13,false);
		}
		if (null != entity.getTin()) {
			Validator.validateStringField("tin",entity.getTin().toString(),128,false);
		}
	}
	
	public static Collection<ContractorAgreementVo> removeDeletedAgreements(Collection<ContractorAgreementVo> vos){
		Collection<ContractorAgreementVo> newVos = new ArrayList<ContractorAgreementVo>();
		
		if(CollectionUtility.hasValue(vos)){
			for(ContractorAgreementVo v : vos){
				if(!DateUtil.hasValue(v.getDeletedDate()))
					newVos.add(v);
			}
		}
		
		return newVos;
	}
	
	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the tin.
	 *
	 * @return 
	 *		the tin to return
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin.
	 *
	 * @param tin 
	 *			the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns) {
		this.duns = duns;
	}

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the fax.
	 *
	 * @return 
	 *		the fax to return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax 
	 *			the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Returns the addressVo.
	 *
	 * @return 
	 *		the addressVo to return
	 */
	public AddressVo getAddressVo() {
		return addressVo;
	}

	/**
	 * Sets the addressVo.
	 *
	 * @param addressVo 
	 *			the addressVo to set
	 */
	public void setAddressVo(AddressVo addressVo) {
		this.addressVo = addressVo;
	}

	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * Returns enabled
	 * 
	 * @return enabled
	 */
	@JsonIgnore
	public Boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * 
	 * @return
	 */
	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return isEnabled();
	}
	
	/**
	 * 
	 * @param enabled
	 */
	@JsonProperty("enabled")
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param incidentVos the incidentVos to set
	 */
	public void setIncidentVos(Collection<IncidentVo> incidentVos) {
		this.incidentVos = incidentVos;
	}

	/**
	 * @return the incidentVos
	 */
	public Collection<IncidentVo> getIncidentVos() {
		return incidentVos;
	}

	/**
	 * @param workAreaVos the workAreaVos to set
	 */
	@JsonIgnore
	public void setWorkAreaVos(Collection<WorkAreaVo> workAreaVos) {
		this.workAreaVos = workAreaVos;
	}

	/**
	 * @return the workAreaVos
	 */
	@JsonIgnore
	public Collection<WorkAreaVo> getWorkAreaVos() {
		return workAreaVos;
	}

	public Collection<ContractorAgreementVo> getContractorAgreementVos() {
		return contractorAgreementVos;
	}

	public void setContractorAgreementVos(
			Collection<ContractorAgreementVo> contractorAgreementVos) {
		this.contractorAgreementVos = contractorAgreementVos;
	}

	public String getVerifyTin() {
		return verifyTin;
	}

	public void setVerifyTin(String verifyTin) {
		this.verifyTin = verifyTin;
	}

	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	public String getActualTin() {
		return actualTin;
	}

	public void setActualTin(String actualTin) {
		this.actualTin = actualTin;
	}
}
