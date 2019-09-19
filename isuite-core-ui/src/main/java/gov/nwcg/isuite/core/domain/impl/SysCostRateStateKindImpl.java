package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.math.BigDecimal;

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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_SYSCOST_RATE_STATE_KIND", sequenceName="SEQ_SYSCOST_RATE_STATE_KIND")
@Table(name="isw_syscost_rate_state_kind")
public class SysCostRateStateKindImpl extends PersistableImpl implements SysCostRateStateKind {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SYSCOST_RATE_STATE_KIND")
	private Long id = 0L;

	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", updatable=true, insertable=true)
	private Kind kind;
	
	@Column(name = "KIND_ID", updatable=false, insertable=false)
	private Long kindId;
	
	@Column(name = "RATE_TYPE", nullable = false, length = 40)
	private String rateType;
	
	@Column(name = "RATE_AMOUNT", nullable = false, precision = 22)
	private BigDecimal rateAmount;

	@ManyToOne(targetEntity=SysCostRateStateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "SYSCOST_RATE_STATE_ID", nullable = false)
	private SysCostRateState sysCostRateState;
	
	/**
	 * Default constructor.
	 *
	 */
	public SysCostRateStateKindImpl() {
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
		SysCostRateStateKindImpl o = (SysCostRateStateKindImpl)obj;
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



	/**
	 * @return the rateType
	 */
	public String getRateType() {
		return rateType;
	}



	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}



	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}



	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}



	/**
	 * @return the sysCostRateState
	 */
	public SysCostRateState getSysCostRateState() {
		return sysCostRateState;
	}



	/**
	 * @param sysCostRateState the sysCostRateState to set
	 */
	public void setSysCostRateState(SysCostRateState sysCostRateState) {
		this.sysCostRateState = sysCostRateState;
	}

}
