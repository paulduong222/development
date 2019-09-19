package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.CostAccrualExtractRsc;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@SequenceGenerator(name="SEQ_COST_ACCRUAL_EXTRACT", sequenceName="SEQ_COST_ACCRUAL_EXTRACT")
@Table(name="isw_cost_accrual_extract")
public class CostAccrualExtractImpl extends PersistableImpl implements CostAccrualExtract {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_ACCRUAL_EXTRACT")
	private Long id = 0L;

	@Column(name="extract_date", nullable=false)
	private Date extractDate;
	
    @Column(name="FINALIZED", length=10)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum finalized;

    @Column(name="IS_FROM_SINGLE_INCIDENT", length=10)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFromSingleIncident;

    @Column(name="finalized_date", nullable=true)
	private Date finalizedDate;
    
	@Column(name="PREPARED_BY", length=50, nullable=true, unique = false)
	private String preparedBy;

	@Column(name="PREPARED_PHONE", length=50, nullable=true, unique = false)
	private String preparedPhone;
	
	@ManyToOne(targetEntity=IncidentImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Incident incident;

	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentId;
	
	@ManyToOne(targetEntity=IncidentGroupImpl.class,fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private IncidentGroup incidentGroup;

	@Column(name="INCIDENT_GROUP_ID", insertable = false, updatable = false, nullable = true, length=19)
	private Long incidentGroupId;

	@OneToMany(targetEntity=CostAccrualExtractRscImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "costAccrualExtract")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OrderBy("incidentResourceId")
	private Collection<CostAccrualExtractRsc> costAccrualExtractRscs = null;
	
	@Column(name = "SEQUENCE_NUMBER")
	private Short sequenceNumber;
	
	@Column(name = "is_exported",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum exported;
	
	/**
	 * Default constructor.
	 *
	 */
	public CostAccrualExtractImpl() {
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
	 * @return the extractDate
	 */
	public Date getExtractDate() {
		return extractDate;
	}

	/**
	 * @param extractDate the extractDate to set
	 */
	public void setExtractDate(Date extractDate) {
		this.extractDate = extractDate;
	}
	
	

	/**
	 * @return the finalized
	 */
	public StringBooleanEnum isFinalized() {
		return finalized;
	}

	/**
	 * @param finalized the finalized to set
	 */
	public void setFinalized(StringBooleanEnum finalized) {
		this.finalized = finalized;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedPhone
	 */
	public String getPreparedPhone() {
		return preparedPhone;
	}

	/**
	 * @param preparedPhone the preparedPhone to set
	 */
	public void setPreparedPhone(String preparedPhone) {
		this.preparedPhone = preparedPhone;
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
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @param extractDate the extractDate to set
	 */
	public Date getFinalizedDate() {
		return this.finalizedDate;
	}

	public void setFinalizedDate(Date val){
		this.finalizedDate=val;
	}
	
	public Collection<CostAccrualExtractRsc> getCostAccrualExtractRscs() {
		if(null==this.costAccrualExtractRscs)
			this.costAccrualExtractRscs=new ArrayList<CostAccrualExtractRsc>();
		
		return costAccrualExtractRscs;
	}

	public void setCostAccrualExtractRscs(
			Collection<CostAccrualExtractRsc> costAccrualExtractRscs) {
		this.costAccrualExtractRscs = costAccrualExtractRscs;
	}

	/**
	 * @return the sequenceNumber
	 */
	public Short getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Short sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the finalized
	 */
	public StringBooleanEnum getFinalized() {
		return finalized;
	}

	
	
	
	public StringBooleanEnum getIsExported() {
	
		return exported;
	}

	
	public void setIsExported(StringBooleanEnum exported) {
		
		this.exported = exported;
	}

	/**
	 * @return the isFromSingleIncident
	 */
	public StringBooleanEnum getIsFromSingleIncident() {
		return isFromSingleIncident;
	}

	/**
	 * @param isFromSingleIncident the isFromSingleIncident to set
	 */
	public void setIsFromSingleIncident(StringBooleanEnum isFromSingleIncident) {
		this.isFromSingleIncident = isFromSingleIncident;
	}
	
}
