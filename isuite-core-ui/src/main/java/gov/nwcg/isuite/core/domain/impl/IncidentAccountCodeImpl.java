package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.ResourceOther;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * The join between Incidents and Account Codes.  Most accounting information will
 * likely stem from this association.
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_INCIDENT_ACCOUNT_CODE", sequenceName="SEQ_INCIDENT_ACCOUNT_CODE")
@Table(name="isw_incident_account_code")
public class IncidentAccountCodeImpl extends PersistableImpl implements IncidentAccountCode {

   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENT_ACCOUNT_CODE")
   private Long id = 0L;
   
   @ManyToOne(targetEntity=AccountCodeImpl.class,fetch = FetchType.LAZY,cascade=CascadeType.ALL)
   @JoinColumn(name = "ACCOUNT_CODE_ID", nullable = false, insertable = true, updatable = true)
//	@org.hibernate.annotations.Cascade(
//	   {org.hibernate.annotations.CascadeType.ALL,org.hibernate.annotations.CascadeType.DELETE_ORPHAN}
//	)
   private AccountCode accountCode;
   
   @Column(name = "ACCOUNT_CODE_ID", insertable = false, updatable = false, nullable = false)
   private Long accountCodeId;
   
   @ManyToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = false)
   private Incident incident;
   
   @Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = false)
   private Long incidentId;

   @OneToOne(targetEntity=AccountCodeImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "OVERRIDE_ACCOUNT_CODE_ID", insertable = true, updatable = true, unique = false, nullable = true)
   private AccountCode overrideAccountCode;

   @Column(name = "OVERRIDE_ACCOUNT_CODE_ID", insertable = false, updatable = false, nullable = true)
   private Long overrideAccountCodeId;
   
   /* Defines whether this is the default accounting code for the incident. */
   @Column(name="DEFAULT_FLG")
   private Boolean defaultFlag;

   @OneToMany(targetEntity=TimeAssignAdjustImpl.class,fetch = FetchType.LAZY, mappedBy = "incidentAccountCode")
   private Collection<TimeAssignAdjust> timeAssignAdjusts;

   @OneToMany(targetEntity=AssignmentTimePostImpl.class, fetch = FetchType.LAZY, mappedBy = "incidentAccountCode")
   private Collection<AssignmentTimePost> assignmentTimePosts;
   
   @OneToMany(targetEntity=ResourceOtherImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "incidentAccountCode")
   private Collection<ResourceOther> resourceOthers = new ArrayList<ResourceOther>();

   @Column(name="ACCRUAL_ACCOUNT_CODE", length=11)
   private String accrualAccountCode;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getAccountCode()
    */
   public AccountCode getAccountCode() {
      return this.accountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getAccountCodeId()
    */
   public Long getAccountCodeId() {
      return this.accountCodeId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getIncident()
    */
   public Incident getIncident() {
      return this.incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getIncidentId()
    */
   public Long getIncidentId() {
      return this.incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getOverrideAccountCode()
    */
   public AccountCode getOverrideAccountCode() {
      return this.overrideAccountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#getOverrideAccountCodeId()
    */
   public Long getOverrideAccountCodeId() {
      return this.overrideAccountCodeId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#isDefaultFlag()
    */
   public Boolean getDefaultFlag() {
      return this.defaultFlag;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setAccountCode(gov.nwcg.isuite.domain.finance.AccountCode)
    */
   public void setAccountCode(AccountCode accountCode) {
      this.accountCode = accountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setAccountCodeId(java.lang.Long)
    */
   public void setAccountCodeId(Long accountCodeId) {
      this.accountCodeId = accountCodeId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setDefaultFlag(java.lang.Boolean)
    */
   public void setDefaultFlag(Boolean defaultFlag) {
      this.defaultFlag = defaultFlag;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setIncident(gov.nwcg.isuite.domain.incident.Incident)
    */
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setIncidentId(java.lang.Long)
    */
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setOverrideAccountCode(gov.nwcg.isuite.domain.finance.AccountCode)
    */
   public void setOverrideAccountCode(AccountCode overrideAccountCode) {
      this.overrideAccountCode = overrideAccountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.IncidentAccountCode#setOverrideAccountCodeId(java.lang.Long)
    */
   public void setOverrideAccountCodeId(Long overrideAccountCodeId) {
      this.overrideAccountCodeId = overrideAccountCodeId;
   }

    /* 
     * (non-Javadoc)
     * @see gov.nwcg.isuite.domain.Persistable#getId()
     */
    public Long getId() {
       return this.id;
    }

    /*
     * (non-Javadoc)
     * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
     */
    public void setId(Long id) {
       this.id = id;
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
       IncidentAccountCodeImpl o = (IncidentAccountCodeImpl)obj;
       return new EqualsBuilder()
       	.append(new Object[]{id,accountCodeId,defaultFlag,incidentId,overrideAccountCodeId},
       			new Object[]{o.id,o.accountCodeId,o.defaultFlag,o.incidentId,o.overrideAccountCodeId})
   	    .appendSuper(super.equals(o))
       	.isEquals();
    }   
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
 	  return new HashCodeBuilder(31,33)
 	  	.append(super.hashCode())
 	  	.append(new Object[]{id,accountCodeId,defaultFlag,incidentId,overrideAccountCodeId})
 	  	.toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
 	   return new ToStringBuilder(this)
 	       .append("id", id)
 	       .append("accountCodeId", accountCodeId)
 	       .append("defaultFlag", defaultFlag)
 	       .append("incidentId",incidentId)
 	       .append("overrideAccountCodeId", overrideAccountCodeId)
 	       .appendSuper(super.toString())
 	       .toString();
    }

	/**
	 * @return the timeAssignAdjusts
	 */
	public Collection<TimeAssignAdjust> getTimeAssignAdjusts() {
		return timeAssignAdjusts;
	}

	/**
	 * @param timeAssignAdjusts the timeAssignAdjusts to set
	 */
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts) {
		this.timeAssignAdjusts = timeAssignAdjusts;
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
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() {
		return resourceOthers;
	}

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(
			Collection<ResourceOther> resourceOthers) {
		this.resourceOthers = resourceOthers;
	}

	public String getAccrualAccountCode() {
		return accrualAccountCode;
	}

	public void setAccrualAccountCode(String accrualAccountCode) {
		this.accrualAccountCode = accrualAccountCode;
	}


}
