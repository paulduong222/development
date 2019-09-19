package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.RateClassRate;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_RATE_CLASS", sequenceName="SEQ_RATE_CLASS")
@Table(name = "iswl_rate_class")
public class RateClassImpl extends PersistableImpl implements RateClass {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RATE_CLASS")
	private Long id = 0L;

	@Column(name = "RATE_CLASS_NAME", length = 30, nullable=false)
	private String rateClassName;

	@OneToMany(targetEntity=RateClassRateImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rateClass")
	private Collection<RateClassRate> rateClassRates;

	@Column(name = "RATE_YEAR" )
	private Integer rateYear;

	@Column(name = "IS_STANDARD",nullable=false)
	private Boolean standard;
	
	public RateClassImpl() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRateClassName() {
		return rateClassName;
	}


	public void setRateClassName(String rateClassName) {
		this.rateClassName = rateClassName;
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
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}


	public Collection<RateClassRate> getRateClassRates() {
		return rateClassRates;
	}


	public void setRateClassRates(Collection<RateClassRate> rateClassRates) {
		this.rateClassRates = rateClassRates;
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
		RateClassImpl o = (RateClassImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,rateClassName,rateYear,standard},
				new Object[]{o.id,o.rateClassName,o.rateYear,o.standard})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,rateClassName,rateYear,standard})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("rateClassName", rateClassName)
		.append("rateYear", rateYear)
		.append("standard", standard)
		.appendSuper(super.toString())
		.toString();
	}
	
}