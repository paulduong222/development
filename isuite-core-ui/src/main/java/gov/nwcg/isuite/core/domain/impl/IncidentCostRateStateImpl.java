package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.math.BigDecimal;
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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_INCCOST_RATE_STATE", sequenceName="SEQ_INCCOST_RATE_STATE")
@Table(name="isw_inccost_rate_state")
public class IncidentCostRateStateImpl extends PersistableImpl implements IncidentCostRateState {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCCOST_RATE_STATE")
	private Long id = 0L;

	@ManyToOne(targetEntity=AgencyImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID", updatable=true, insertable=true)
	private Agency agency;
	
	@Column(name = "AGENCY_ID", updatable=false, insertable=false)
	private Long agencyId;
	
	@Column(name = "DIRECT_RATE", precision = 22)
	private BigDecimal directRate;

	@Column(name = "INDIRECT_RATE", precision = 22)
	private BigDecimal indirectRate;
	
	@Column(name = "SINGLE_RATE", precision = 22)
	private BigDecimal singleRate;
	
	@Column(name = "SUBORDINATE_RATE", precision = 22)
	private BigDecimal subordinateRate;
	
	@ManyToOne(targetEntity=IncidentCostRateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCCOST_RATE_ID", nullable = false)
	private IncidentCostRate incidentCostRate;

    @Column(name="INCCOST_RATE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long incidentCostRateId;
	
	@OneToMany(targetEntity=IncidentCostRateStateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentCostRateState")
	private Collection<IncidentCostRateStateKind> incidentCostStateKinds = new ArrayList<IncidentCostRateStateKind>();
	
	
	/**
	 * Default constructor.
	 *
	 */
	public IncidentCostRateStateImpl() {
		super();
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
		IncidentCostRateStateImpl o = (IncidentCostRateStateImpl)obj;
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
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the directRate
	 */
	public BigDecimal getDirectRate() {
		return directRate;
	}

	/**
	 * @param directRate the directRate to set
	 */
	public void setDirectRate(BigDecimal directRate) {
		this.directRate = directRate;
	}

	/**
	 * @return the indirectRate
	 */
	public BigDecimal getIndirectRate() {
		return indirectRate;
	}

	/**
	 * @param indirectRate the indirectRate to set
	 */
	public void setIndirectRate(BigDecimal indirectRate) {
		this.indirectRate = indirectRate;
	}

	/**
	 * @return the singleRate
	 */
	public BigDecimal getSingleRate() {
		return singleRate;
	}

	/**
	 * @param singleRate the singleRate to set
	 */
	public void setSingleRate(BigDecimal singleRate) {
		this.singleRate = singleRate;
	}

	/**
	 * @return the subordinateRate
	 */
	public BigDecimal getSubordinateRate() {
		return subordinateRate;
	}

	/**
	 * @param subordinateRate the subordinateRate to set
	 */
	public void setSubordinateRate(BigDecimal subordinateRate) {
		this.subordinateRate = subordinateRate;
	}

	/**
	 * @return the incidentCostRate
	 */
	public IncidentCostRate getIncidentCostRate() {
		return incidentCostRate;
	}

	/**
	 * @param incidentCostRate the incidentCostRate to set
	 */
	public void setIncidentCostRate(IncidentCostRate incidentCostRate) {
		this.incidentCostRate = incidentCostRate;
	}

	/**
	 * @return the incidentCostStateKinds
	 */
	public Collection<IncidentCostRateStateKind> getIncidentCostStateKinds() {
		return incidentCostStateKinds;
	}

	/**
	 * @param incidentCostStateKinds the incidentCostStateKinds to set
	 */
	public void setIncidentCostStateKinds(
			Collection<IncidentCostRateStateKind> incidentCostStateKinds) {
		this.incidentCostStateKinds = incidentCostStateKinds;
	}

	/**
	 * @return the incidentCostRateId
	 */
	public Long getIncidentCostRateId() {
		return incidentCostRateId;
	}

	/**
	 * @param incidentCostRateId the incidentCostRateId to set
	 */
	public void setIncidentCostRateId(Long incidentCostRateId) {
		this.incidentCostRateId = incidentCostRateId;
	}




}
