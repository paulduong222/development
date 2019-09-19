package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.ContractorAgreementNumberHistory;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_CONTRACTOR_AGREE_NBR_HIST", sequenceName="SEQ_CONTRACTOR_AGREE_NBR_HIST")
@Table(name = "isw_contractor_agree_nbr_hist")
public class ContractorAgreementNumberHistoryImpl extends PersistableImpl implements ContractorAgreementNumberHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTRACTOR_AGREE_NBR_HIST")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=ContractorAgreementImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "CONTRACTOR_AGREEMENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private ContractorAgreement contractorAgreement;
	
	@Column(name="CONTRACTOR_AGREEMENT_ID", insertable = false, updatable = false, nullable = true)
	private Long contractorAgreementId;
	
	@Column(name="REASON_TEXT", length=255)
	private String reasonText;
	
	@Column(name="NEW_AGREEMENT_NUMBER", length=30)
	private String newAgreementNumber;
	
	@Column(name="OLD_AGREEMENT_NUMBER", length=30)
	private String oldAgreementNumber;
	
	public ContractorAgreementNumberHistoryImpl() {
		
	}
	
	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the contractorAgreement
	 * 
	 * @return
	 * 		the contractorAgreement to return
	 */
	public ContractorAgreement getContractorAgreement() {
		return contractorAgreement;
	}

	/**
	 * Returns the contractorAgreementId
	 * 
	 * @return
	 * 		the contractorAgreeementId to return
	 */
	public Long getContractorAgreementId() {
		return contractorAgreementId;
	}

	/**
	 * Returns the newAgreementNumber
	 * 
	 * @return
	 * 		the newAgreementNumber to return
	 */
	public String getNewAgreementNumber() {
		return newAgreementNumber;
	}

	/**
	 * Returns the oldAgreementNumber
	 * 
	 * @return
	 * 		the oldAgreementNumber to return
	 */
	public String getOldAgreementNumber() {
		return oldAgreementNumber;
	}

	/**
	 * Returns the reasonText
	 * 
	 * @return
	 * 		the reasonText to return
	 */
	public String getReasonText() {
		return reasonText;
	}

	/**
	 * Sets the contractorAgreement
	 * 
	 * @param contractorAgreement
	 * 			the contractorAgreement to set
	 */
	public void setContractorAgreement(ContractorAgreement contractorAgreement) {
		this.contractorAgreement = contractorAgreement;
	}

	/**
	 * Sets the contractorAgreementId
	 * 
	 * @param contractorAgreementId
	 * 			the contractorAgreementId to set
	 */
	public void setContractorAgreementId(Long contractorAgreementId) {
		this.contractorAgreementId = contractorAgreementId;
	}

	/**
	 * Sets the newAgreementNumber
	 * 
	 * @param newAgreementNumber
	 * 			the newAgreementNumber to set
	 */
	public void setNewAgreementNumber(String newAgreementNumber) {
		this.newAgreementNumber = newAgreementNumber;
	}

	/**
	 * Sets the oldAgreementNumber
	 * 
	 * @param oldAgreementNumber
	 * 			the oldAgreementNumber to set
	 */
	public void setOldAgreementNumber(String oldAgreementNumber) {
		this.oldAgreementNumber = oldAgreementNumber;
	}

	/**
	 * Sets the reasonText
	 * 
	 * @param reasonText
	 * 			the reasonText to set
	 */
	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

}
