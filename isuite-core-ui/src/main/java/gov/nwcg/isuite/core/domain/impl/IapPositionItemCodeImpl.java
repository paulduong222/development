package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.IapPositionItemCode;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.IapSectionEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

/**
 * IapPositionItemCode entity.
 */
@Entity
@Table(name = "isw_iap_position_item_code")
@SequenceGenerator(name="SEQ_IAP_POSITION_ITEM_CODE", sequenceName="SEQ_IAP_POSITION_ITEM_CODE")
public class IapPositionItemCodeImpl extends PersistableImpl implements IapPositionItemCode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_POSITION_ITEM_CODE")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", nullable = true)
	private Incident incident;
	
	@Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", nullable = true)
	private IncidentGroup incidentGroup;
	
	@Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentGroupId;

	@Column(name = "POSITION", nullable = false, length = 50)
	private String position;

	@ManyToOne(targetEntity=KindImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", nullable = false)
    //@BatchSize(size=100)
	private Kind kind;
	
	@Column(name = "KIND_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long kindId;
	
	@ManyToOne(targetEntity=AgencyImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID")
	private Agency agency;
	
	@Column(name = "AGENCY_ID", insertable = false, updatable = false)
	private Long agencyId;
	
	@Column(name = "FORM", length = 20)
	private String form;
	
	@Column(name = "SECTION", length = 20)
	@Enumerated(EnumType.STRING)
	private IapSectionEnum section;
	
	/** 
	 * Default constructor 
	 */
	public IapPositionItemCodeImpl() {
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
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
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
	 * @return the agency
	 */
	public Agency getAgency() {
		return agency;
	}

	/**
	 * @param agency
	 */
	public void setAgency(Agency agency) {
		this.agency = agency;
	}
	
	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}
	
	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the section
	 */
	public IapSectionEnum getSection() {
		return section;
	}

	/**
	 * @param section
	 */
	public void setSection(IapSectionEnum section) {
		this.section = section;
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
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

}
