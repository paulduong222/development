package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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

/**
 * 
 * @author dprice
 */
@Entity
@SequenceGenerator(name="SEQ_RESOURCE_MOBILIZATION", sequenceName="SEQ_RESOURCE_MOBILIZATION")
@Table(name="isw_resource_mobilization")
public class ResourceMobilizationImpl extends PersistableImpl implements ResourceMobilization{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE_MOBILIZATION")
	private Long id = 0L;

	@Column(name="RESOURCE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long resourceId;

	@ManyToOne(targetEntity=gov.nwcg.isuite.core.domain.impl.ResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "RESOURCE_ID", nullable = false)
	private Resource resource;

	@Column(name="START_DATE", nullable=true)
	private Date startDate;

	@OneToMany(targetEntity=WorkPeriodImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "ciResourceMobilization")
	public Collection<WorkPeriod> workPeriods;

	public ResourceMobilizationImpl(){

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
	 */
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#getResourceId()
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#setResourceId(java.lang.Long)
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#getResource()
	 */
	public Resource getResource() {
		return resource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#setResource(gov.nwcg.isuite.core.domain.Resource)
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#getStartDate()
	 */
	public Date getStartDate() {
		return startDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ResourceMobilization#setStartDate(java.util.Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		ResourceMobilizationImpl o = (ResourceMobilizationImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,resourceId,startDate},
				new Object[]{o.id,o.resourceId,o.startDate})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,resourceId,startDate})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("resourceId", resourceId)
		.append("startDate",startDate)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * Returns the workPeriods.
	 *
	 * @return 
	 *		the workPeriods to return
	 */
	public Collection<WorkPeriod> getWorkPeriods() {
		if(null == workPeriods)
			workPeriods = new ArrayList<WorkPeriod>();
		return workPeriods;
	}

	/**
	 * Sets the workPeriods.
	 *
	 * @param workPeriods 
	 *			the workPeriods to set
	 */
	public void setWorkPeriods(Collection<WorkPeriod> workPeriods) {
		this.workPeriods = workPeriods;
	}   

}
