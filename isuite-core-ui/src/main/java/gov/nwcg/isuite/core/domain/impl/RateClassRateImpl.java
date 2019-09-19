package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdPaymentInfo;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.Collection;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_RATE_CLASS_RATE", sequenceName="SEQ_RATE_CLASS_RATE")
@Table(name = "iswl_rate_class_rate")
public class RateClassRateImpl extends PersistableImpl implements RateClassRate {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RATE_CLASS_RATE")
	private Long id = 0L;

	@ManyToOne(targetEntity=RateClassImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "RATE_CLASS_ID", insertable = true, updatable = true, nullable = false)
	private RateClass rateClass;
	
    @Column(name="RATE_CLASS_ID", insertable = false, updatable = false, nullable = false)
	private Long rateClassId;
	
	@Column(name = "AREA", length=60)
	private String area;

	@Column(name = "RATE", precision = 22, scale = 0)
	private BigDecimal rate;

	@Column(name = "RATE_YEAR" )
	private Integer rateYear;

	@Column(name = "IS_STANDARD")
	private Boolean standard;

	@Column(name = "RATE_CLASSNAME", length=10)
	private String rateClassName;

	@Column(name = "RATE_CLASSCODE", length=10)
	private String rateClassCode;
	
	@OneToMany(targetEntity=AdPaymentInfoImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rateClassRate")
	private Collection<AdPaymentInfo> adPaymentInfos;
	
	@OneToMany(targetEntity=AssignmentTimePostImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rateClassRate")
	private Collection<AssignmentTimePost> assignmentTimePosts;

	@OneToOne(targetEntity=RateClassRateImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "TRAINING_RATE_ID", insertable = true, updatable = true)
	private RateClassRate trainingRateClassRate;
	
    @Column(name="TRAINING_RATE_ID", insertable = false, updatable = false)
	private Long trainingRateClassRateId;
    
    @Column(name = "IS_ACTIVE",nullable=false)
    @Enumerated(EnumType.STRING)
    private StringBooleanEnum active;
	
	public RateClassRateImpl() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public BigDecimal getRate() {
		return rate;
	}


	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}


	public Integer getRateYear() {
		return rateYear;
	}


	public void setRateYear(Integer rateYear) {
		this.rateYear = rateYear;
	}


	public Boolean isStandard() {
		return standard;
	}


	public void setStandard(Boolean standard) {
		this.standard = standard;
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


	/**
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}


	public RateClass getRateClass() {
		return rateClass;
	}


	public void setRateClass(RateClass rateClass) {
		this.rateClass = rateClass;
	}


	public Long getRateClassId() {
		return rateClassId;
	}


	public void setRateClassId(Long rateClassId) {
		this.rateClassId = rateClassId;
	}


	public String getRateClassName() {
		return rateClassName;
	}


	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
	}


	public String getRateClassCode() {
		return rateClassCode;
	}


	public void setRateClassCode(String rateClassCode) {
		this.rateClassCode = rateClassCode;
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
		RateClassRateImpl o = (RateClassRateImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,rateClassId,area,rateYear,standard,rateClassName,rateClassCode},
				new Object[]{o.id,o.rateClassId,o.area,o.rateYear,o.standard,o.rateClassName,o.rateClassCode})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,rateClassId,area,rateYear,standard,rateClassName,rateClassCode})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("rateClassId", rateClassId)
		.append("area", area)
		.append("rateYear", rateYear)
		.append("standard", standard)
		.append("rateClassName", rateClassName)
		.append("rateClassCode", rateClassCode)
		.appendSuper(super.toString())
		.toString();
	}


	public RateClassRate getTrainingRateClassRate() {
		return trainingRateClassRate;
	}


	public void setTrainingRateClassRate(RateClassRate trainingRateClassRate) {
		this.trainingRateClassRate = trainingRateClassRate;
	}


	public Long getTrainingRateClassRateId() {
		return trainingRateClassRateId;
	}


	public void setTrainingRateClassRateId(Long trainingRateClassRateId) {
		this.trainingRateClassRateId = trainingRateClassRateId;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive() {
		return active;
	}

	/**
	 * @param adPaymentInfos the adPaymentInfos to set
	 */
	public void setAdPaymentInfos(Collection<AdPaymentInfo> adPaymentInfos) {
		this.adPaymentInfos = adPaymentInfos;
	}

	/**
	 * @return the adPaymentInfos
	 */
	public Collection<AdPaymentInfo> getAdPaymentInfos() {
		return adPaymentInfos;
	}
	
}