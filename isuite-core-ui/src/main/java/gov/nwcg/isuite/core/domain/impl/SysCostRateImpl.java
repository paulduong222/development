package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.domain.SysCostRateState;
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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_SYSCOST_RATE", sequenceName="SEQ_SYSCOST_RATE")
@Table(name="isw_syscost_rate")
public class SysCostRateImpl extends PersistableImpl implements SysCostRate {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSCOST_RATE")
	private Long id = 0L;

	@Column(name = "COST_RATE_CATEGORY", nullable = false, length = 30)
	private String costRateCategory;

	@ManyToMany(targetEntity=SysCostRateStateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysCostRate")
	private Collection<SysCostRateState> sysCostRateStates = new ArrayList<SysCostRateState>();

	@ManyToMany(targetEntity=SysCostRateKindImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysCostRate")
	private Collection<SysCostRateKind> sysCostRateKinds = new ArrayList<SysCostRateKind>();

	@ManyToMany(targetEntity=SysCostRateOvhdImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysCostRate")
	private Collection<SysCostRateOvhd> sysCostRateOvhds = new ArrayList<SysCostRateOvhd>();
	
	/**
	 * Default constructor.
	 *
	 */
	public SysCostRateImpl() {
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
		SysCostRateImpl o = (SysCostRateImpl)obj;
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
	 * @return the costRateCategory
	 */
	public String getCostRateCategory() {
		return costRateCategory;
	}



	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) {
		this.costRateCategory = costRateCategory;
	}



	/**
	 * @return the sysCostRateStates
	 */
	public Collection<SysCostRateState> getSysCostRateStates() {
		return sysCostRateStates;
	}



	/**
	 * @param sysCostRateStates the sysCostRateStates to set
	 */
	public void setSysCostRateStates(Collection<SysCostRateState> sysCostRateStates) {
		this.sysCostRateStates = sysCostRateStates;
	}



	/**
	 * @return the sysCostRateKinds
	 */
	public Collection<SysCostRateKind> getSysCostRateKinds() {
		return sysCostRateKinds;
	}



	/**
	 * @param sysCostRateKinds the sysCostRateKinds to set
	 */
	public void setSysCostRateKinds(Collection<SysCostRateKind> sysCostRateKinds) {
		this.sysCostRateKinds = sysCostRateKinds;
	}



	/**
	 * @return the sysCostRateOvhds
	 */
	public Collection<SysCostRateOvhd> getSysCostRateOvhds() {
		return sysCostRateOvhds;
	}



	/**
	 * @param sysCostRateOvhds the sysCostRateOvhds to set
	 */
	public void setSysCostRateOvhds(Collection<SysCostRateOvhd> sysCostRateOvhds) {
		this.sysCostRateOvhds = sysCostRateOvhds;
	}



}
