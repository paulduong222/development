package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_RESOURCE_OTHER", sequenceName="SEQ_RESOURCE_OTHER")
@Table(name = "isw_resource_other")
public class ResourceOtherImpl extends PersistableImpl implements ResourceOther {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE_OTHER")
	private Long id = 0L;

	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", updatable=true, insertable=true)
	private Kind kind;

	@Column(name = "KIND_ID", updatable=false, insertable=false)
	private Long kindId;
	
	@ManyToOne(targetEntity=IncidentAccountCodeImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ACCOUNT_CODE_ID", updatable=true, insertable=true)
	private IncidentAccountCode incidentAccountCode;
	
	@Column(name = "INCIDENT_ACCOUNT_CODE_ID", updatable=false, insertable=false)
	private Long incidentAccountCodeId;
	
	@ManyToOne(targetEntity=AgencyImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID")
	private Agency agency;

	@Column(name = "REQUEST_NUMBER", length = 20)
	private String requestNumber;

	@Column(name = "COST_DESCRIPTION", length = 50)
	private String costDescription;
	
	@Column(name = "ACTUAL_RELEASE_DATE", length = 11)
	private Date actualReleaseDate;
	
	@ManyToMany(targetEntity=CostGroupImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "ISW_COST_GROUP_RESOURCE_OTHER", 
			 joinColumns = { 
				@JoinColumn(name = "RESOURCE_OTHER_ID", updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "COST_GROUP_ID", updatable = false) })
	private Collection<CostGroup> costGroups = new ArrayList<CostGroup>();

	@OneToMany(targetEntity=IncidentResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resourceOther")
	//@OneToOne(targetEntity=IncidentResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resourceOther")
	private Collection<IncidentResourceOther> incidentResourceOthers = new ArrayList<IncidentResourceOther>();

	
	public ResourceOtherImpl() {
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
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}


	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}


	/**
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode() {
		return incidentAccountCode;
	}


	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) {
		this.incidentAccountCode = incidentAccountCode;
	}


	/**
	 * @return the agency
	 */
	public Agency getAgency() {
		return agency;
	}


	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}


	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}


	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}


	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}


	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}


	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() {
		return costGroups;
	}


	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) {
		this.costGroups = costGroups;
	}


	/**
	 * @return the incidentResourceOthers
	 */
	public Collection<IncidentResourceOther> getIncidentResourceOthers() {
		if(null ==incidentResourceOthers)
			incidentResourceOthers = new ArrayList<IncidentResourceOther>();
		return incidentResourceOthers;
	}


	/**
	 * @param incidentResourceOthers the incidentResourceOthers to set
	 */
	public void setIncidentResourceOthers(
			Collection<IncidentResourceOther> incidentResourceOthers) {
		this.incidentResourceOthers = incidentResourceOthers;
	}

	/**
	 * @return the costDescription
	 */
	public String getCostDescription() {
		return costDescription;
	}

	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription) {
		this.costDescription = costDescription;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		ResourceOtherImpl o = (ResourceOtherImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(id)
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}


	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}


	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}


	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}


	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

}
