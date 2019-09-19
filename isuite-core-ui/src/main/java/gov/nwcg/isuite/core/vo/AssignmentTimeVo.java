package gov.nwcg.isuite.core.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.AssignmentImpl;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

public class AssignmentTimeVo extends AbstractVo {
	private EmploymentTypeEnum employmentType;
	private AddressVo mailingAddressVo = new AddressVo();
	private String phone;
	private String fax;
	private String ofRemarks;
	private BigDecimal otherDefaultRate;
	private Date deletedDate;
	private AdPaymentInfoVo adPaymentInfoVo = new AdPaymentInfoVo();
	private ContractorPaymentInfoVo contractorPaymentInfoVo = new ContractorPaymentInfoVo();
	private Collection<AssignmentTimePostVo> assignmentTimePostVos = new ArrayList<AssignmentTimePostVo>();
	private String hiringUnitName;
	private String hiringUnitPhone;
	private String hiringUnitFax;
	
	public AssignmentTimeVo() {
	}

	public static AssignmentTimeVo getInstance(AssignmentTime entity,boolean cascadable) throws Exception {
		AssignmentTimeVo vo = new AssignmentTimeVo();

		if(null == entity)
			throw new Exception("Unable to create vo from null AssignmentTime entity");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setEmploymentType(entity.getEmploymentType());
			vo.setFax(entity.getFax());
			vo.setPhone(entity.getPhone());
			vo.setOfRemarks(entity.getOfRemarks());
			vo.setOtherDefaultRate(entity.getOtherDefaultRate());
			vo.setDeletedDate(entity.getDeletedDate());
			vo.setHiringUnitName(entity.getHiringUnitName());
			vo.setHiringUnitFax(entity.getHiringFax());
			vo.setHiringUnitPhone(entity.getHiringPhone());
			
			if(null != entity.getMailingAddress()){
				vo.setMailingAddressVo(AddressVo.getInstance(entity.getMailingAddress(), true));
			}

			if(null != entity.getAdPaymentInfo() && (null!=entity.getEmploymentType() && entity.getEmploymentType()==EmploymentTypeEnum.AD)){
				vo.setAdPaymentInfoVo(AdPaymentInfoVo.getInstance(entity.getAdPaymentInfo(), true));
			}

			if(null != entity.getContractorPaymentInfo()){// && (null!=entity.getEmploymentType() && entity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR)) {
				vo.setContractorPaymentInfoVo(ContractorPaymentInfoVo.getInstance(entity.getContractorPaymentInfo(), true));
			}

			if(null != entity.getAssignmentTimePosts()){
				Collection<AssignmentTimePostVo> atpvos = AssignmentTimePostVo.getInstances(entity.getAssignmentTimePosts(), true);
				if(CollectionUtility.hasValue(atpvos)){
					Collections.sort((List)atpvos,new AssignmentTimePostVo.StartDateComparator());
					vo.setAssignmentTimePostVos(atpvos);
				}
				
				//vo.setAssignmentTimePostVos(AssignmentTimePostVo.getInstances(entity.getAssignmentTimePosts(), true));
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
	public static Collection<AssignmentTimeVo> getInstances(Collection<AssignmentTime> entities,boolean cascadable) throws Exception {
		Collection<AssignmentTimeVo> vos = new ArrayList<AssignmentTimeVo>();

		for(AssignmentTime entity : entities){
			vos.add(AssignmentTimeVo.getInstance(entity, cascadable));
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
	public static AssignmentTime toEntity(AssignmentTime entity, AssignmentTimeVo vo,boolean cascadable,Persistable...persistables) throws Exception {
		if(null == entity)
			entity = new AssignmentTimeImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setEmploymentType(vo.getEmploymentType());
			entity.setFax(vo.getFax());
			entity.setPhone(vo.getPhone());
			entity.setHiringFax(vo.getHiringUnitFax());
			entity.setHiringPhone(vo.getHiringUnitPhone());
			entity.setHiringUnitName(vo.getHiringUnitName());
			
			// if not other emp type anymore, clear out otherrate
			if(vo.getEmploymentType()!=EmploymentTypeEnum.OTHER)
				entity.setOtherDefaultRate(new BigDecimal(0));
			else
				entity.setOtherDefaultRate(vo.getOtherDefaultRate());
			
			entity.setOfRemarks(StringUtility.toUpper(vo.getOfRemarks()));

			Assignment assignment = (Assignment)getPersistableObject(persistables, AssignmentImpl.class);
			if(null != assignment)
				entity.setAssignment(assignment);

			if(null != vo.getMailingAddressVo()  && !vo.getMailingAddressVo().isEmpty()){
				entity.setMailingAddress(AddressVo.toEntity(vo.getMailingAddressVo(), true, entity));
			}

			if(null != vo.getAdPaymentInfoVo() && (null != vo.getEmploymentType() && vo.getEmploymentType()==EmploymentTypeEnum.AD )){
				if(null != entity.getAdPaymentInfo() && LongUtility.hasValue(entity.getAdPaymentInfo().getId()))
					entity.setAdPaymentInfo(AdPaymentInfoVo.toEntity(entity.getAdPaymentInfo(), vo.getAdPaymentInfoVo(), true, entity));
				else
					entity.setAdPaymentInfo(AdPaymentInfoVo.toEntity(null, vo.getAdPaymentInfoVo(), true, entity));
			}else{
				// if not ad anymore, clear out ad info
				if(null != vo.getAdPaymentInfoVo() && vo.getEmploymentType()!=EmploymentTypeEnum.AD ){
					vo.getAdPaymentInfoVo().setPointOfHire("");
					vo.getAdPaymentInfoVo().setPointOfHireOrgVo(null);
					vo.getAdPaymentInfoVo().setRateClassRateVo(null);
					vo.getAdPaymentInfoVo().setRateClassVo(null);
					vo.getAdPaymentInfoVo().setEci("");
					entity.setAdPaymentInfo(AdPaymentInfoVo.toEntity(entity.getAdPaymentInfo(), vo.getAdPaymentInfoVo(), true, entity));
				}
			}

			if(null != vo.getContractorPaymentInfoVo() && (null!=entity.getEmploymentType() && entity.getEmploymentType()==EmploymentTypeEnum.CONTRACTOR)) {
				if(null != entity.getContractorPaymentInfo() && LongUtility.hasValue(entity.getContractorPaymentInfo().getId()))
					entity.setContractorPaymentInfo(ContractorPaymentInfoVo.toEntity(entity.getContractorPaymentInfo(), vo.getContractorPaymentInfoVo(), true, entity));
				else
					entity.setContractorPaymentInfo(ContractorPaymentInfoVo.toEntity(null, vo.getContractorPaymentInfoVo(), true, entity));
			}

			validateEntity(entity);
		}

		return entity;
	}

	/**
	 * Perform some validation on the entity field definitions.
	 * 
	 * @param entity
	 * 			the source entity
	 * @throws ValidationException
	 */
	private static void validateEntity(AssignmentTime entity) throws ValidationException {
		Validator.validateStringField("phone", entity.getPhone(), 30,false);
		Validator.validateStringField("fax", entity.getFax(), 30,false);
		Validator.validateStringField("ofRemarks", entity.getOfRemarks(), 256,false);
	}

	/**
	 * @param irEntity
	 * @return
	 * @throws Exception
	 */
	public static AssignmentTime getAssignmentTime(IncidentResource irEntity) throws Exception {
		AssignmentTime atEntity = null;
		
		if(null != irEntity && null != irEntity.getWorkPeriod()){
			WorkPeriod wp = irEntity.getWorkPeriod();
			
			for(Assignment assignment : wp.getAssignments()){
				if(!DateUtil.hasValue(assignment.getEndDate())){
					atEntity=assignment.getAssignmentTime();
					break;
				}
			}
		}
		
		return atEntity;
	}
	
	/**
	 * @return the employmentType
	 */
	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the mailingAddressVo
	 */
	public AddressVo getMailingAddressVo() {
		return mailingAddressVo;
	}

	/**
	 * @param mailingAddressVo the mailingAddressVo to set
	 */
	public void setMailingAddressVo(AddressVo mailingAddressVo) {
		this.mailingAddressVo = mailingAddressVo;
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
		this.phone = phone;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the ofRemarks
	 */
	public String getOfRemarks() {
		return ofRemarks;
	}

	/**
	 * @param ofRemarks the ofRemarks to set
	 */
	public void setOfRemarks(String ofRemarks) {
		this.ofRemarks = ofRemarks;
	}

	/**
	 * @return the otherDefaultRate
	 */
	public BigDecimal getOtherDefaultRate() {
		return otherDefaultRate;
	}

	/**
	 * @param otherDefaultRate the otherDefaultRate to set
	 */
	public void setOtherDefaultRate(BigDecimal otherDefaultRate) {
		this.otherDefaultRate = otherDefaultRate;
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
	 * @return the adPaymentInfoVo
	 */
	public AdPaymentInfoVo getAdPaymentInfoVo() {
		return adPaymentInfoVo;
	}

	/**
	 * @param adPaymentInfoVo the adPaymentInfoVo to set
	 */
	public void setAdPaymentInfoVo(AdPaymentInfoVo adPaymentInfoVo) {
		this.adPaymentInfoVo = adPaymentInfoVo;
	}

	/**
	 * @return the contractorPaymentInfoVos
	 */
	public ContractorPaymentInfoVo getContractorPaymentInfoVo() {
		return contractorPaymentInfoVo;
	}

	/**
	 * @param contractorPaymentInfoVos the contractorPaymentInfoVos to set
	 */
	public void setContractorPaymentInfoVo(
			ContractorPaymentInfoVo contractorPaymentInfoVo) {
		this.contractorPaymentInfoVo = contractorPaymentInfoVo;
	}

	/**
	 * @return the assignmentTimePostVos
	 */
	public Collection<AssignmentTimePostVo> getAssignmentTimePostVos() {
		return assignmentTimePostVos;
	}

	/**
	 * @param assignmentTimePostVos the assignmentTimePostVos to set
	 */
	public void setAssignmentTimePostVos(
			Collection<AssignmentTimePostVo> assignmentTimePostVos) {
		this.assignmentTimePostVos = assignmentTimePostVos;
	}

	public String getHiringUnitName() {
		return hiringUnitName;
	}

	public void setHiringUnitName(String hiringUnitName) {
		this.hiringUnitName = hiringUnitName;
	}

	public String getHiringUnitPhone() {
		return hiringUnitPhone;
	}

	public void setHiringUnitPhone(String hiringUnitPhone) {
		this.hiringUnitPhone = hiringUnitPhone;
	}

	public String getHiringUnitFax() {
		return hiringUnitFax;
	}

	public void setHiringUnitFax(String hiringUnitFax) {
		this.hiringUnitFax = hiringUnitFax;
	}


}
