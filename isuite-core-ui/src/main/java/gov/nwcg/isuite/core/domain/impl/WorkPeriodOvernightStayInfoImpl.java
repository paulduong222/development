package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodOvernightStayInfo;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_WP_OVERNIGHT_STAY_INFO", sequenceName="SEQ_WP_OVERNIGHT_STAY_INFO")
@Table(name = "isw_wp_overnight_stay_info")
public class WorkPeriodOvernightStayInfoImpl extends PersistableImpl implements WorkPeriodOvernightStayInfo {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_WP_OVERNIGHT_STAY_INFO")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=WorkPeriodImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "WORK_PERIOD_ID", insertable = true, updatable = true, nullable = false)
	private WorkPeriod workPeriod;
	
	@Column(name = "WORK_PERIOD_ID", length = 19, insertable=false, updatable=false, nullable=false)
	private Long workPeriodId;

    @OneToOne(targetEntity=CountrySubdivisionImpl.class, fetch=FetchType.EAGER)
    @JoinColumn(name="STATE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private CountrySubdivision countrySubdivision;

    @Column(name="STATE_ID", insertable=false, updatable=false, nullable=true, unique=false)
    private Long stateId;
    
	@Column(name = "CITY", length = 30)
	private String city;
	
    @Column(name="ESTIMATED_ARRIVAL_DATE")
	private Date estimatedArrivalDate;
	
	@Column(name = "LENGTH_OF_STAY")
	private Long lengthOfStay;
	
	@Column(name = "REMARKS", length = 50, nullable=true)
	private String remarks;

	public WorkPeriodOvernightStayInfoImpl() {
		super();
	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the workPeriod.
	 *
	 * @return 
	 *		the workPeriod to return
	 */
	public WorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	/**
	 * Sets the workPeriod.
	 *
	 * @param workPeriod 
	 *			the workPeriod to set
	 */
	public void setWorkPeriod(WorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
	}

	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * Returns the countrySubdivision.
	 *
	 * @return 
	 *		the countrySubdivision to return
	 */
	public CountrySubdivision getCountrySubdivision() {
		return countrySubdivision;
	}

	/**
	 * Sets the countrySubdivision.
	 *
	 * @param countrySubdivision 
	 *			the countrySubdivision to set
	 */
	public void setCountrySubdivision(CountrySubdivision countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}

	/**
	 * Returns the stateId.
	 *
	 * @return 
	 *		the stateId to return
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * Sets the stateId.
	 *
	 * @param stateId 
	 *			the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * Returns the city.
	 *
	 * @return 
	 *		the city to return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city 
	 *			the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns the estimatedArrivalDate.
	 *
	 * @return 
	 *		the estimatedArrivalDate to return
	 */
	public Date getEstimatedArrivalDate() {
		return estimatedArrivalDate;
	}

	/**
	 * Sets the estimatedArrivalDate.
	 *
	 * @param estimatedArrivalDate 
	 *			the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
		this.estimatedArrivalDate = estimatedArrivalDate;
	}

	/**
	 * Returns the lengthOfStay.
	 *
	 * @return 
	 *		the lengthOfStay to return
	 */
	public Long getLengthOfStay() {
		return lengthOfStay;
	}

	/**
	 * Sets the lengthOfStay.
	 *
	 * @param lengthOfStay 
	 *			the lengthOfStay to set
	 */
	public void setLengthOfStay(Long lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	/**
	 * Returns the remarks.
	 *
	 * @return 
	 *		the remarks to return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Sets the remarks.
	 *
	 * @param remarks 
	 *			the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
		WorkPeriodOvernightStayInfoImpl o = (WorkPeriodOvernightStayInfoImpl)obj;
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
		.append(new Object[]{id})
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
