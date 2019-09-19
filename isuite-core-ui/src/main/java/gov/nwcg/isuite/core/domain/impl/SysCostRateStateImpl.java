package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
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
@SequenceGenerator(name="SEQ_SYSCOST_RATE_STATE", sequenceName="SEQ_SYSCOST_RATE_STATE")
@Table(name="isw_syscost_rate_state")
public class SysCostRateStateImpl extends PersistableImpl implements SysCostRateState {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSCOST_RATE_STATE")
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
	
	@ManyToOne(targetEntity=SysCostRateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSCOST_RATE_ID", nullable = false)
	private SysCostRate sysCostRate;

	@OneToMany(targetEntity=SysCostRateStateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysCostRateState")
	private Collection<SysCostRateStateKind> sysCostStateKinds = new ArrayList<SysCostRateStateKind>();
	
	
	/**
	 * Default constructor.
	 *
	 */
	public SysCostRateStateImpl() {
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
		SysCostRateStateImpl o = (SysCostRateStateImpl)obj;
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
	 * @return the sysCostRate
	 */
	public SysCostRate getSysCostRate() {
		return sysCostRate;
	}

	/**
	 * @param sysCostRate the sysCostRate to set
	 */
	public void setSysCostRate(SysCostRate sysCostRate) {
		this.sysCostRate = sysCostRate;
	}

	/**
	 * @return the sysCostStateKinds
	 */
	public Collection<SysCostRateStateKind> getSysCostStateKinds() {
		return sysCostStateKinds;
	}

	/**
	 * @param sysCostStateKinds the sysCostStateKinds to set
	 */
	public void setSysCostStateKinds(
			Collection<SysCostRateStateKind> sysCostStateKinds) {
		this.sysCostStateKinds = sysCostStateKinds;
	}




}
