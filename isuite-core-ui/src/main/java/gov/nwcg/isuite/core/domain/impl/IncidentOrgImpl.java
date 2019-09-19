package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentOrg;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ForeignKey;

@Entity
@SequenceGenerator(name="SEQ_INCIDENT_ORG", sequenceName="SEQ_INCIDENT_ORG")
@Table(name = "isw_incident_org")
public class IncidentOrgImpl extends PersistableImpl implements IncidentOrg {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_ORG")
	private Long id = 0L;
	
	@Column(name = "INCIDENT_ID", insertable=false, updatable=false, unique=false, nullable=false)
	private Long incident_id;
	
	@ManyToOne(targetEntity=IncidentImpl.class)
	@JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, unique=false, nullable=false)
	@ForeignKey(name="FK_INCIDENT_ORG_INC")
	@BatchSize(size=100)
	private Incident incident;
	
	@Column(name = "ORGANIZATION_ID", insertable=false, updatable=false, unique=false, nullable=false)
	private Long organization_id;
	
	@ManyToOne(targetEntity=OrganizationImpl.class)
	@JoinColumn(name="ORGANIZATION_ID", insertable=true, updatable=true, unique=false, nullable=false)
	@ForeignKey(name="FK_INCIDENT_ORG_ORG")
	@BatchSize(size=300)
	private Organization organization;
	
	@Column(name="TYPE", length=1)
	private String type;
	
	public IncidentOrgImpl() {
		
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
	 * @param incident_id the incident_id to set
	 */
	public void setIncident_id(Long incident_id) {
		this.incident_id = incident_id;
	}

	/**
	 * @return the incident_id
	 */
	public Long getIncident_id() {
		return incident_id;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param organization_id the organization_id to set
	 */
	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}

	/**
	 * @return the organization_id
	 */
	public Long getOrganization_id() {
		return organization_id;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
