package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.Kind;
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
@SequenceGenerator(name="SEQ_INCCOST_RATE_KIND", sequenceName="SEQ_INCCOST_RATE_KIND")
@Table(name="isw_inccost_rate_kind")
public class IncidentCostRateKindImpl extends PersistableImpl implements IncidentCostRateKind {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCCOST_RATE_KIND")
	private Long id = 0L;

	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "KIND_ID", insertable=true, updatable = true, nullable = false)
	private Kind kind;

	@Column(name = "KIND_ID", insertable=false, updatable = false, nullable = false)
	private Long kindId;
	
	@Column(name = "RATE_TYPE", nullable = false, length = 40)
	private String rateType;
	
	@Column(name = "RATE_AMOUNT", nullable = false, precision = 22)
	private BigDecimal rateAmount;
	
	@ManyToOne(targetEntity=IncidentCostRateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "INCCOST_RATE_ID", updatable=true, insertable=true, nullable = false)
	private IncidentCostRate incidentCostRate;
	
	@Column(name = "INCCOST_RATE_ID", updatable=false, insertable=false, nullable = false)
	private Long incidentCostRateId;
	
	/**
	 * Default constructor.
	 *
	 */
	public IncidentCostRateKindImpl() {
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
		IncidentCostRateKindImpl o = (IncidentCostRateKindImpl)obj;
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
