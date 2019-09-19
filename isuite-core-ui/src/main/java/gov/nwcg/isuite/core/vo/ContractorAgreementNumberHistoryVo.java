package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.core.domain.impl.ContractorAgreementNumberHistoryImpl;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.util.LongUtility;

public class ContractorAgreementNumberHistoryVo extends AbstractVo {
	private ContractorAgreementVo contractorAgreementVo;
	private Long contractorAgreementId;
	private String reasonText;
	private String newAgreementNumber;
	private String oldAgreementNumber;
	
	public ContractorAgreementNumberHistoryVo() {
	}
	
	public static ContractorAgreementNumberHistory toEntity(ContractorAgreementNumberHistoryVo vo, Boolean cascadable) throws Exception {
		ContractorAgreementNumberHistory entity = new ContractorAgreementNumberHistoryImpl();
		
		if(null==vo)
			throw new Exception("Unable to create contractorAgreementNumberHistory entity from null vo.");
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setContractorAgreementId(vo.getContractorAgreementId());
			if(null != vo.getContractorAgreementVo() && LongUtility.hasValue(vo.getContractorAgreementVo().getId()))
				entity.setContractorAgreement(ContractorAgreementVo.toEntity(null, vo.getContractorAgreementVo(), false));
			
			entity.setReasonText(vo.getReasonText());
			entity.setNewAgreementNumber(vo.getNewAgreementNumber());
			entity.setOldAgreementNumber(vo.getOldAgreementNumber());
		}
		
		
		return entity;
	}

	/**
	 * @param contractorAgreementVo the contractorAgreementVo to set
	 */
	public void setContractorAgreementVo(ContractorAgreementVo contractorAgreementVo) {
		this.contractorAgreementVo = contractorAgreementVo;
	}

	/**
	 * @return the contractorAgreementVo
	 */
	public ContractorAgreementVo getContractorAgreementVo() {
		return contractorAgreementVo;
	}

	/**
	 * @param contractorAgreementId the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * @return the contractorAgreementId
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * @param reasonText the reasonText to set
	 */
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	/**
	 * @return the reasonText
	 */
	public String getReasonText() {
		return reasonText;
	}

	/**
	 * @param newAgreementNumber the newAgreementNumber to set
	 */
	public void setNewAgreementNumber(String newAgreementNumber) {
		this.newAgreementNumber = newAgreementNumber;
	}

	/**
	 * @return the newAgreementNumber
	 */
	public String getNewAgreementNumber() {
		return newAgreementNumber;
	}

	/**
	 * @param oldAgreementNumber the oldAgreementNumber to set
	 */
	public void setOldAgreementNumber(String oldAgreementNumber) {
		this.oldAgreementNumber = oldAgreementNumber;
	}

	/**
	 * @return the oldAgreementNumber
	 */
	public String getOldAgreementNumber() {
		return oldAgreementNumber;
	}

}
