package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.ContractorPaymentInfo;
import gov.nwcg.isuite.core.domain.ContractorRate;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimeImpl;
import gov.nwcg.isuite.core.domain.impl.ContractorPaymentInfoImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ContractorPaymentInfoVo extends AbstractVo {
	private ContractorVo contractorVo;
	private String vinName;
	private String desc1;
	private String desc2;
	private ContractorAgreementVo contractorAgreementVo;
	//private Date hiredDate;
	//private String hiredTime;
	private DateTransferVo hiredDateVo=new DateTransferVo();
	private Boolean operation;
	private Boolean supplies;
	private Boolean withdrawn;
	private Date deletedDate;
	private String pointOfHire;
	
	private Collection<ContractorRateVo> contractorRateVos;

	public ContractorPaymentInfoVo() {
	}

	public static ContractorPaymentInfoVo getInstance(
			ContractorPaymentInfo entity, boolean cascadable) throws Exception {
		ContractorPaymentInfoVo vo = new ContractorPaymentInfoVo();

		if (null == entity)
			throw new Exception(
					"Unable to create vo from null ContractorPaymentInfo entity");

		vo.setId(entity.getId());

		if (cascadable) {

			vo.setVinName(entity.getVinName());
			vo.setDesc1(entity.getDesc1());
			vo.setDesc2(entity.getDesc2());
			vo.setPointOfHire(entity.getPointOfHire());
			
			if(DateUtil.hasValue(entity.getHiredDate()))
				DateTransferVo.populateDate(vo.getHiredDateVo(), entity.getHiredDate());
			
			vo.setOperation(entity.getOperation());
			vo.setSupplies(entity.getSupplies());
			vo.setWithdrawn(entity.getWithdrawn());
		}

		if (null != entity.getContractorAgreement()) {
			vo.setContractorAgreementVo(ContractorAgreementVo.getInstance(
					entity.getContractorAgreement(), true));
		}

		if (null != entity.getContractorRates()) {

			vo.setContractorRateVos(new ArrayList<ContractorRateVo>());
			for (ContractorRate rate : entity.getContractorRates()) {
				vo.getContractorRateVos().add(
						ContractorRateVo.getInstance(rate, true));
			}
		}

		if (null != entity.getContractor()) {
			vo.setContractorVo(ContractorVo.getInstance(entity.getContractor(),
					true));
		}

		if (null != entity.getContractorAgreement()) {
			vo.setContractorAgreementVo(ContractorAgreementVo.getInstance(
					entity.getContractorAgreement(), true));
		}

		return vo;
	}

	public static Collection<ContractorPaymentInfoVo> getInstances(
			Collection<ContractorPaymentInfo> entities, boolean cascadable)
			throws Exception {
		Collection<ContractorPaymentInfoVo> vos = new ArrayList<ContractorPaymentInfoVo>();

		for (ContractorPaymentInfo entity : entities) {
			vos.add(ContractorPaymentInfoVo.getInstance(entity, cascadable));
		}

		return vos;
	}

	public static ContractorPaymentInfo toEntity(ContractorPaymentInfo entity,
			ContractorPaymentInfoVo vo, boolean cascadable,
			Persistable... persistables) throws Exception {

		if (null == entity)
			entity = new ContractorPaymentInfoImpl();

		if(LongUtility.hasValue(vo.getId()))
			entity.setId(vo.getId());
		else
			entity.setId(null);
		
		if (cascadable) {

			entity.setVinName(StringUtility.toUpper(vo.getVinName()));
			entity.setDesc1(StringUtility.toUpper(vo.getDesc1()));
			entity.setDesc2(StringUtility.toUpper(vo.getDesc2()));
			
			if(StringUtility.hasValue(vo.getPointOfHire()))
				entity.setPointOfHire(vo.getPointOfHire().toUpperCase());
			

			if(DateTransferVo.hasDateString(vo.getHiredDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getHiredDateVo());
				entity.setHiredDate(dt);
			}else{
				entity.setHiredDate(null);
			}
						
			entity.setOperation(vo.getOperation());
			entity.setSupplies(vo.getSupplies());
			entity.setWithdrawn(vo.getWithdrawn());

			if(AbstractVo.hasValue(vo.getContractorAgreementVo())){
				//if (null != vo.getContractorAgreementVo()) {
				entity.setContractorAgreement(ContractorAgreementVo.toEntity(null, vo
						.getContractorAgreementVo(), false, entity));
			}else
				entity.setContractorAgreement(null);

			if (null != vo.getContractorRateVos()) {

				entity.setContractorRates(new ArrayList<ContractorRate>());
				for (ContractorRateVo rateVo : vo.getContractorRateVos()) {
					entity.getContractorRates().add(
							ContractorRateVo.toEntity(null, rateVo, true,
									entity));
				}
			}

			if(AbstractVo.hasValue(vo.getContractorVo())){
				//if (null != vo.getContractorVo()) {
				entity.setContractor(ContractorVo.toEntity(null,
						vo.getContractorVo(), false));
			}else
				entity.setContractor(null);

			AssignmentTime assignmentTime = (AssignmentTime) getPersistableObject(
					persistables, AssignmentTimeImpl.class);
			if (null != assignmentTime) {
				entity.setAssignmentTime(assignmentTime);
			}
		}

		return entity;
	}

	/**
	 * @return the contractorVo
	 */
	public ContractorVo getContractorVo() {
		return contractorVo;
	}

	/**
	 * @param contractorVo
	 *            the contractorVo to set
	 */
	public void setContractorVo(ContractorVo contractorVo) {
		this.contractorVo = contractorVo;
	}

	/**
	 * @return the vinName
	 */
	public String getVinName() {
		return vinName;
	}

	/**
	 * @param vinName
	 *            the vinName to set
	 */
	public void setVinName(String vinName) {
		this.vinName = vinName;
	}

	/**
	 * @return the desc1
	 */
	public String getDesc1() {
		return desc1;
	}

	/**
	 * @param desc1
	 *            the desc1 to set
	 */
	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	/**
	 * @return the desc2
	 */
	public String getDesc2() {
		return desc2;
	}

	/**
	 * @param desc2
	 *            the desc2 to set
	 */
	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}

	/**
	 * @return the contractorAgreementVo
	 */
	public ContractorAgreementVo getContractorAgreementVo() {
		return contractorAgreementVo;
	}

	/**
	 * @param contractorAgreementVo
	 *            the contractorAgreementVo to set
	 */
	public void setContractorAgreementVo(
			ContractorAgreementVo contractorAgreementVo) {
		this.contractorAgreementVo = contractorAgreementVo;
	}

	/**
	 * @return the operation
	 */
	public Boolean getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(Boolean operation) {
		this.operation = operation;
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
	 * @return the supplies
	 */
	public Boolean getSupplies() {
		return supplies;
	}

	/**
	 * @param supplies
	 *            the supplies to set
	 */
	public void setSupplies(Boolean supplies) {
		this.supplies = supplies;
	}

	/**
	 * @return the withdrawn
	 */
	public Boolean getWithdrawn() {
		return withdrawn;
	}

	/**
	 * @param withdrawn
	 *            the withdrawn to set
	 */
	public void setWithdrawn(Boolean withdrawn) {
		this.withdrawn = withdrawn;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the contractorRateVos
	 */
	public Collection<ContractorRateVo> getContractorRateVos() {
		return contractorRateVos;
	}

	/**
	 * @param contractorRateVos
	 *            the contractorRateVos to set
	 */
	public void setContractorRateVos(
			Collection<ContractorRateVo> contractorRateVos) {
		this.contractorRateVos = contractorRateVos;
	}

	/**
	 * @return true if the contractor has a primary rate assigned; otherwise return false;
	 */
	public static Boolean hasPrimaryRate(ContractorPaymentInfo contractorPaymentInfo) {
		
		Collection<ContractorRate> rates = contractorPaymentInfo.getContractorRates();
		
		Boolean rv = Boolean.FALSE;
		
		for(ContractorRate rate : rates) {
			if(rate.getRateType().equalsIgnoreCase("PRIMARY")) {
				rv = Boolean.TRUE;
			}
		}
		
		return rv;
	}

	/**
	 * @return the hiredDateVo
	 */
	public DateTransferVo getHiredDateVo() {
		return hiredDateVo;
	}

	/**
	 * @param hiredDateVo the hiredDateVo to set
	 */
	public void setHiredDateVo(DateTransferVo hiredDateVo) {
		this.hiredDateVo = hiredDateVo;
	}
}
