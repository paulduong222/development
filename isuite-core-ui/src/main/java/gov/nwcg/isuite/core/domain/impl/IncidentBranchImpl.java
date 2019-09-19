package gov.nwcg.isuite.core.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.IapBranchPosItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentBranch;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;


/**
 * IswIncidentBranch entity.
 */
@Entity
@Table(name = "isw_incident_branch")
@SequenceGenerator(name="SEQ_INCIDENT_BRANCH", sequenceName="SEQ_INCIDENT_BRANCH")
public class IncidentBranchImpl extends PersistableImpl implements IncidentBranch {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_BRANCH")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", nullable = false)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long incidentId;
	
	@Column(name = "BRANCH_NAME", nullable = false, length = 50)
	private String branchName;
	
	@Column(name = "DISPLAY_ORDER")
	private Short displayOrder;
	
	@OneToMany(targetEntity=IapBranchPosItemCodeImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentBranch")
	private Collection<IapBranchPosItemCode> iapBranchPosItemCodes = new ArrayList<IapBranchPosItemCode>();

	
	/** 
	 * Default constructor 
	 */
	public IncidentBranchImpl() {
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
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder() {
		return this.displayOrder;
	}

	/**
	 * @param displayOrder
	 */
	public void setDisplayOrder(Short displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @param iapBranchPosItemCodes the iapBranchPosItemCodes to set
	 */
	public void setIapBranchPosItemCodes(Collection<IapBranchPosItemCode> iapBranchPosItemCodes) {
		this.iapBranchPosItemCodes = iapBranchPosItemCodes;
	}

	/**
	 * @return the iapBranchPosItemCodes
	 */
	public Collection<IapBranchPosItemCode> getIapBranchPosItemCodes() {
		return iapBranchPosItemCodes;
	}

}
