package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
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
@SequenceGenerator(name="SEQ_COST_GROUP_AG_DS_PCT", sequenceName="SEQ_COST_GROUP_AG_DS_PCT")
@Table(name = "isw_cost_group_ag_ds_pct")
public class CostGroupAgencyDaySharePercentageImpl extends PersistableImpl implements CostGroupAgencyDaySharePercentage {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_GROUP_AG_DS_PCT")
	private Long id = 0L;

	@ManyToOne(targetEntity=CostGroupAgencyDayShareImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "COST_GROUP_DAY_SHARE_ID")
	private CostGroupAgencyDayShare costGroupAgencyDayShare;

	@ManyToOne(targetEntity=AgencyImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENCY_ID")
	private Agency agency;
	
	@Column(name = "PERCENTAGE", precision = 22)
	private BigDecimal percentage;
	
	public CostGroupAgencyDaySharePercentageImpl() {
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
	 * @return the costGroupAgencyDayShare
	 */
	public CostGroupAgencyDayShare getCostGroupAgencyDayShare() {
		return costGroupAgencyDayShare;
	}


	/**
	 * @param costGroupAgencyDayShare the costGroupAgencyDayShare to set
	 */
	public void setCostGroupAgencyDayShare(
			CostGroupAgencyDayShare costGroupAgencyDayShare) {
		this.costGroupAgencyDayShare = costGroupAgencyDayShare;
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
	 * @return the percentage
	 */
	public BigDecimal getPercentage() {
		return percentage;
	}


	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
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
		CostGroupAgencyDaySharePercentageImpl o = (CostGroupAgencyDaySharePercentageImpl)obj;
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


}
