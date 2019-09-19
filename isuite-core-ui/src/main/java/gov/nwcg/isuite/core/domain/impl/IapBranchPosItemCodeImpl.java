package gov.nwcg.isuite.core.domain.impl;

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

import gov.nwcg.isuite.core.domain.IapBranchPosItemCode;
import gov.nwcg.isuite.core.domain.IncidentBranch;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

/**
 * IapBranchPosItemCode entity.
 */
@Entity
@Table(name = "isw_iap_branch_pos_item_code")
@SequenceGenerator(name="SEQ_IAP_BRANCH_POS_ITEM_CODE", sequenceName="SEQ_IAP_BRANCH_POS_ITEM_CODE")
public class IapBranchPosItemCodeImpl extends PersistableImpl implements IapBranchPosItemCode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_BRANCH_POS_ITEM_CODE")
	private Long id = 0L;
	
	@ManyToOne(targetEntity = KindImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", nullable = false)
	private Kind kind;
	
	@Column(name = "KIND_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long kindId;
	
	@ManyToOne(targetEntity = IncidentBranchImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCH_ID", nullable = false)
	private IncidentBranch incidentBranch;
	
	@Column(name = "BRANCH_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long branchId;
	
	@Column(name = "POSITION", nullable = false, length = 50)
	private String position;
	
	@Column(name = "FORM", length = 20)
	private String form;
	
	
	/** 
	 * Default constructor 
	 */
	public IapBranchPosItemCodeImpl() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param incidentBranch the incidentBranch to set
	 */
	public void setIncidentBranch(IncidentBranch incidentBranch) {
		this.incidentBranch = incidentBranch;
	}

	/**
	 * @return the incidentBranch
	 */
	public IncidentBranch getIncidentBranch() {
		return incidentBranch;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchId
	 */
	public Long getBranchId() {
		return branchId;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

}
