package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.ContractorRate;
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
@SequenceGenerator(name="SEQ_CONTRACTOR_RATE", sequenceName="SEQ_CONTRACTOR_RATE")
@Table(name="isw_contractor_rate")
public class ContractorRateImpl extends PersistableImpl implements ContractorRate{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTRACTOR_RATE")
	private Long id = 0L;

	@ManyToOne(targetEntity=ContractorRateImpl.class,fetch = FetchType.LAZY)
    @JoinColumn(name="SUPERCEDED_BY", insertable=true, updatable=true, unique=false, nullable=true)
	private ContractorRate supercededBy;

    @Column(name="SUPERCEDED_BY", insertable=false, updatable=false, unique=false, nullable=true)
	private Long supercededById;
	
	@Column(name = "RATE_TYPE", length = 20)
	private String rateType;

	@Column(name = "UNIT_OF_MEASURE", length = 20)
	private String unitOfMeasure;
	
	@Column(name = "RATE_AMOUNT", precision = 22, scale = 0)
	private BigDecimal rateAmount;
	
	@Column(name = "GUARANTEE_AMOUNT", precision = 22, scale = 0)
	private BigDecimal guaranteeAmount;
	
	@Column(name = "DESCRIPTION", length = 128)
	private String description;
	
	@OneToMany(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "refContractorRate")
	private Collection<AssignmentTimePost> assignmentTimePosts = new ArrayList<AssignmentTimePost>();
	
	public ContractorRateImpl(){
		
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
	 * @return the supercededBy
	 */
	public ContractorRate getSupercededBy() {
		return supercededBy;
	}

	/**
	 * @param supercededBy the supercededBy to set
	 */
	public void setSupercededBy(ContractorRate supercededBy) {
		this.supercededBy = supercededBy;
	}

	/**
	 * @return the supercededById
	 */
	public Long getSupercededById() {
		return supercededById;
	}

	/**
	 * @param supercededById the supercededById to set
	 */
	public void setSupercededById(Long supercededById) {
		this.supercededById = supercededById;
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
	 * @return the unitOfMeasure
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * @param unitOfMeasure the unitOfMeasure to set
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * @return the rateAmmount
	 */
	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	/**
	 * @param rateAmount the rateAmmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
	}

	/**
	 * @return the guaranteeAmount
	 */
	public BigDecimal getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount the guaranteeAmount to set
	 */
	public void setGuaranteeAmount(BigDecimal guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts() {
		return assignmentTimePosts;
	}

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(
			Collection<AssignmentTimePost> assignmentTimePosts) {
		this.assignmentTimePosts = assignmentTimePosts;
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
		ContractorRateImpl o = (ContractorRateImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,supercededById,rateType,unitOfMeasure,rateAmount,guaranteeAmount,description},
				new Object[]{o.id,o.supercededById,o.rateType,o.unitOfMeasure,o.rateAmount,o.guaranteeAmount,o.description})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,supercededById,rateType,unitOfMeasure,rateAmount,guaranteeAmount,description})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("supercededById", supercededById)
		.append("rateType", rateType)
		.append("unitOfMeasure", unitOfMeasure)
		.append("rateAmount", rateAmount)
		.append("guaranteeAmount", guaranteeAmount)
		.append("description", description)
		.appendSuper(super.toString())
		.toString();
	}

}
