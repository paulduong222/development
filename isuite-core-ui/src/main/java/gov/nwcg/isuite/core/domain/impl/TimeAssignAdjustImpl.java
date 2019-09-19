package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AdjustCategory;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_TIME_ASSIGN_ADJUST", sequenceName="SEQ_TIME_ASSIGN_ADJUST")
@Table(name = "isw_time_assign_adjust")
public class TimeAssignAdjustImpl extends PersistableImpl implements TimeAssignAdjust {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TIME_ASSIGN_ADJUST")
	private Long id = 0L;
	
	//TODO: annotations ok?
	@ManyToOne(targetEntity=AssignmentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="ASSIGNMENT_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private Assignment assignment;
	
	@Column(name="assignment_id", updatable=false, insertable=false)
	private Long assignmentId;
	
	@Column(name="activity_date", nullable=false)
	private Date activityDate;
	
	//TODO: should this be an enum?
    @Column(name="ADJUSTMENT_TYPE", length=10)
	@Enumerated(EnumType.STRING)
	private AdjustmentTypeEnum adjustmentType;
	
	//TODO: annotations ok?
	@ManyToOne(targetEntity=AdjustCategoryImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="adjust_category_id", insertable=true, updatable=true, unique=false, nullable=true)
	private AdjustCategory adjustCategory;
	
	@Column(name="adjust_category_id", updatable=false, insertable=false)
	private Long adjustmentCategoryId;
	
	@Column(name = "adjustment_amount", precision = 22, scale = 0)
	private BigDecimal adjustmentAmount;
	
	//TODO: annotations ok?
	@ManyToOne(targetEntity=IncidentAccountCodeImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="incident_account_code_id", insertable=true, updatable=true, unique=false, nullable=false)
	private IncidentAccountCode incidentAccountCode;
	
	@Column(name="incident_account_code_id", updatable=false, insertable=false)
	private Long incidentAccountCodeId;
	
	@Column(name = "COMMODITY", length = 90, nullable=true)
	private String commodity;
	
	@Column(name="deleted_date", nullable=true)
	private Date deletedDate;

	@ManyToMany(targetEntity=TimeInvoiceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "timeAssignmentAdjusts")
	private Collection<TimeInvoice> timeInvoices;

	@ManyToOne(targetEntity=IncidentResourceDailyCostImpl.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "INC_RES_DAILY_COST_ID")
	private IncidentResourceDailyCost incidentResourceDailyCost;
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the assignment
	 */
	public Assignment getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment the assignment to set
	 */
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the assignmentId
	 */
	public Long getAssignmentId() {
		return assignmentId;
	}

	/**
	 * @param assignmentId the assignmentId to set
	 */
	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

	/**
	 * @return the activityDate
	 */
	public Date getActivityDate() {
		return activityDate;
	}

	/**
	 * @param activityDate the activityDate to set
	 */
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	/**
	 * @return the adjustmentType
	 */
	public AdjustmentTypeEnum getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 * @param adjustmentType the adjustmentType to set
	 */
	public void setAdjustmentType(AdjustmentTypeEnum adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	/**
	 * @return the adjustCategory
	 */
	public AdjustCategory getAdjustCategory() {
		return adjustCategory;
	}

	/**
	 * @param adjustCategory the adjustCategory to set
	 */
	public void setAdjustCategory(AdjustCategory adjustCategory) {
		this.adjustCategory = adjustCategory;
	}

	/**
	 * @return the adjustmentCategoryId
	 */
	public Long getAdjustmentCategoryId() {
		return adjustmentCategoryId;
	}

	/**
	 * @param adjustmentCategoryId the adjustmentCategoryId to set
	 */
	public void setAdjustmentCategoryId(Long adjustmentCategoryId) {
		this.adjustmentCategoryId = adjustmentCategoryId;
	}

	/**
	 * @return the adjustmentAmount
	 */
	public BigDecimal getAdjustmentAmount() {
		return adjustmentAmount;
	}

	/**
	 * @param adjustmentAmount the adjustmentAmount to set
	 */
	public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
		this.adjustmentAmount = adjustmentAmount;
	}

	/**
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode() {
		return incidentAccountCode;
	}

	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) {
		this.incidentAccountCode = incidentAccountCode;
	}

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() {
		return incidentAccountCodeId;
	}

	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId) {
		this.incidentAccountCodeId = incidentAccountCodeId;
	}

	/**
	 * @return the commodity
	 */
	public String getCommodity() {
		return commodity;
	}

	/**
	 * @param commodity the commodity to set
	 */
	public void setCommodity(String commodity) {
		this.commodity = commodity;	
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
	 * @return the timeInvoices
	 */
	public Collection<TimeInvoice> getTimeInvoices() {
		return timeInvoices;
	}

	/**
	 * @param timeInvoices the timeInvoices to set
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices) {
		this.timeInvoices = timeInvoices;
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
		TimeAssignAdjustImpl o = (TimeAssignAdjustImpl)obj;
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

	public IncidentResourceDailyCost getincidentResourceDailyCost() {
		return this.incidentResourceDailyCost;
	}

	public void setIncidentResourceDailyCost(IncidentResourceDailyCost incidentResourceDailyCost) {
		this.incidentResourceDailyCost = incidentResourceDailyCost;
	}
	
}
