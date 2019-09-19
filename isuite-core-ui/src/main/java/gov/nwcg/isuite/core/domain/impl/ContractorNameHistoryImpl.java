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

import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorNameHistory;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;


@Entity
@SequenceGenerator(name="SEQ_CONTRACTOR_NAME_HISTORY", sequenceName="SEQ_CONTRACTOR_NAME_HISTORY")
@Table(name = "isw_contractor_name_history")
public class ContractorNameHistoryImpl extends PersistableImpl implements ContractorNameHistory {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTRACTOR_NAME_HISTORY")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=ContractorImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name = "CONTRACTOR_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Contractor contractor;
	
	@Column(name="CONTRACTOR_ID", insertable = false, updatable = false, nullable = true)
	private Long contractorId;
	
	@Column(name="REASON_TEXT", length=255)
	private String reasonText;
	
	@Column(name="NEW_CONTRACTOR_NAME", length=50)
	private String newContractorName;
	
	@Column(name="OLD_CONTRACTOR_NAME", length=50)
	private String oldContractorName;
	
	public ContractorNameHistoryImpl() {
		
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
	 * Returns the contractor
	 * 
	 * @return
	 * 		the contractor to return
	 */
	public Contractor getContractor() {
		return contractor;
	}

	/**
	 * Returns the contractorId
	 * 
	 * @return
	 * 		the contractorId to return
	 */
	public Long getContractorId() {
		return contractorId;
	}

	/**
	 * Returns the newContractorName
	 * 
	 * @return
	 * 		the newContractorName to return
	 */
	public String getNewContractorName() {
		return newContractorName;
	}

	/**
	 * Returns the oldContractorName
	 * 
	 * @return
	 * 		the oldContractorName to return
	 */
	public String getOldContractorName() {
		return oldContractorName;
	}

	/**
	 * Returns the reasonText
	 * 
	 * @return
	 * 		the reastonText to return
	 */
	public String getReasonText() {
		return reasonText;
	}

	/**
	 * Sets the contractor
	 * 
	 * @param contractor
	 * 			the contractor to set
	 */
	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}
	
	/**
	 * Sets the contractorId
	 * 
	 * @param contractorId
	 * 			the contractorId to set
	 */
	public void setContractorId(Long contractorId) {
		this.contractorId = contractorId;
	}

	/**
	 * Sets the newContractorName
	 * 
	 * @param newContractorName
	 * 			the newContractorName to set
	 */
	public void setNewContractorName(String newContractorName) {
		this.newContractorName = newContractorName;
	}

	/**
	 * Sets the oldContractorName
	 * 
	 * @param oldContractorName
	 * 			the oldContractorName to set
	 */
	public void setOldContractorName(String oldContractorName) {
		this.oldContractorName = oldContractorName;
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
