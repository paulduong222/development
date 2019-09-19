package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
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
import org.hibernate.annotations.BatchSize;

@Entity
@SequenceGenerator(name="SEQ_COST_GROUP_AG_DS", sequenceName="SEQ_COST_GROUP_AG_DS")
@Table(name = "isw_cost_group_ag_ds")
public class CostGroupAgencyDayShareImpl extends PersistableImpl implements CostGroupAgencyDayShare {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_COST_GROUP_AG_DS")
	private Long id = 0L;

	@ManyToOne(targetEntity=CostGroupImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "COST_GROUP_ID", updatable=true, insertable=true)
	private CostGroup costGroup;

	@Column(name = "COST_GROUP_ID", updatable=false, insertable=false)
	private Long costGroupId;
	
	@Column(name = "AGENCY_SHARE_DATE", length = 11)
	private Date agencyShareDate;
	
	@OneToMany(targetEntity=CostGroupAgencyDaySharePercentageImpl.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "costGroupAgencyDayShare")
	@org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
//	@BatchSize(size=100)
	private Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages = null;

	@Column(name="DELETED_DATE")
	private Date deletedDate;
	
	public CostGroupAgencyDayShareImpl() {
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
	 * @return the costGroup
	 */
	public CostGroup getCostGroup() {
		return costGroup;
	}


	/**
	 * @param costGroup the costGroup to set
	 */
	public void setCostGroup(CostGroup costGroup) {
		this.costGroup = costGroup;
	}


	/**
	 * @return the agencyShareDate
	 */
	public Date getAgencyShareDate() {
		return agencyShareDate;
	}


	/**
	 * @param agencyShareDate the agencyShareDate to set
	 */
	public void setAgencyShareDate(Date agencyShareDate) {
		this.agencyShareDate = agencyShareDate;
	}


	/**
	 * @return the costGroupAgencyDaySharePercents
	 */
	public Collection<CostGroupAgencyDaySharePercentage> getCostGroupAgencyDaySharePercentages() {
		if(null==costGroupAgencyDaySharePercentages)
			costGroupAgencyDaySharePercentages=new ArrayList<CostGroupAgencyDaySharePercentage>();
		return costGroupAgencyDaySharePercentages;
	}


	/**
	 * @param costGroupAgencyDaySharePercentages the costGroupAgencyDaySharePercentages to set
	 */
	public void setCostGroupAgencyDaySharePercentages(Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages) {
		this.costGroupAgencyDaySharePercentages = costGroupAgencyDaySharePercentages;
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
		CostGroupAgencyDayShareImpl o = (CostGroupAgencyDayShareImpl)obj;
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
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}


	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}


	/**
	 * @return the costGroupId
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}


	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
	}


}
