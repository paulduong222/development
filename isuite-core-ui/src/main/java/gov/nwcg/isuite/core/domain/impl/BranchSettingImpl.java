package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.BranchSettingPosition;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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

@Entity
@SequenceGenerator(name = "SEQ_BRANCH_SETTING", sequenceName = "SEQ_BRANCH_SETTING")
@Table(name = "isw_branch_setting")
public class BranchSettingImpl extends PersistableImpl implements BranchSetting {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_BRANCH_SETTING")
	private Long id = 0L;

	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", nullable = true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentGroupId;

	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", nullable = true)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentId;

	@Column(name="BRANCH_NAME",length=50)
	private String branchName;

	@Column(name="POSITION_NUM")
	private Integer positionNum;
	
	@OneToMany(targetEntity=BranchSettingPositionImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="branchSetting")
	private Collection<BranchSettingPosition> branchSettingPositions = new ArrayList<BranchSettingPosition>();
	
	@OneToMany(targetEntity=IapPersonnelImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="branchSetting")
	private Collection<IapPersonnel> iapPersonnelPositions = new ArrayList<IapPersonnel>();

	public BranchSettingImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#getId()
	 */
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId
	 *            the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId
	 *            the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchSettingPositions
	 */
	public Collection<BranchSettingPosition> getBranchSettingPositions() {
		return branchSettingPositions;
	}

	/**
	 * @param branchSettingPositions the branchSettingPositions to set
	 */
	public void setBranchSettingPositions(
			Collection<BranchSettingPosition> branchSettingPositions) {
		this.branchSettingPositions = branchSettingPositions;
	}

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
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
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	/**
	 * @return the iapPersonnelPositions
	 */
	public Collection<IapPersonnel> getIapPersonnelPositions() {
		return iapPersonnelPositions;
	}

	/**
	 * @param iapPersonnelPositions the iapPersonnelPositions to set
	 */
	public void setIapPersonnelPositions(
			Collection<IapPersonnel> iapPersonnelPositions) {
		this.iapPersonnelPositions = iapPersonnelPositions;
	}

}
