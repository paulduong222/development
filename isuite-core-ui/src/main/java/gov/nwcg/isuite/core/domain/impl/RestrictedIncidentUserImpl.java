package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.RestrictedIncidentUser;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.RestrictedIncidentUserTypeEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Domain object used to represent a Restricted Incident User association.
 * <br />The unique key for this data object is: 
 * <ul>
 *   <li>userId</li>
 *   <li>incidentId</li>
 *   <li>accessEndDate</li>
 * </ul> 
 * 
 * @author mgreen
 */
@Entity
@SequenceGenerator(name="SEQ_RESTRICTED_INCIDENT_USER", sequenceName="SEQ_RESTRICTED_INCIDENT_USER")
@Table(name="isw_restricted_incident_user")
public class RestrictedIncidentUserImpl extends PersistableImpl implements RestrictedIncidentUser {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESTRICTED_INCIDENT_USER")
   private Long id = 0L;
       
   @ManyToOne(targetEntity=IncidentImpl.class)
   @JoinColumn(name="INCIDENT_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private Incident incident;
   
   @Column(name="INCIDENT_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long incidentId;
   
   @ManyToOne(targetEntity=UserImpl.class)
   @JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private User user;
   
   @Column(name="USER_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long userId;
   
   @Column(name="USER_TYPE", length=15)
   @Enumerated(EnumType.STRING)
   private RestrictedIncidentUserTypeEnum userType;
   
   @Transient
   private String accessGrantedBy;
   
   @Column(name = "ACCESS_END_DATE")
   private Date accessEndDate;

   @Column(name="DEFAULT_CHECKIN_DATE")   
   private Date defaultCheckinDate;
   
   @Column(name="DEFAULT_CHECKIN_TYPE",length=20)
   private String defaultCheckinType;
   
//   @ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//   @JoinTable(name = "isw_rest_inc_user_role", 
//		   		joinColumns = { 
//		   			@JoinColumn(name = "RESTRICTED_INCIDENT_USER_ID", nullable = false, updatable = false) }
//   				, inverseJoinColumns = { 
//		   			@JoinColumn(name = "role_id", nullable = false, updatable = false) })
//   private Collection<SystemRole> userRoles = new ArrayList<SystemRole>();
   
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
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getIncident()
    */
   public Incident getIncident() {
      return this.incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getIncidentId()
    */
   public Long getIncidentId() {
      return this.incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getUser()
    */
   public User getUser() {
      return this.user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getUserId()
    */
   public Long getUserId() {
      return this.userId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getUserType()
    */
   public RestrictedIncidentUserTypeEnum getUserType() {
      return this.userType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setIncident(gov.nwcg.isuite.domain.incident.Incident)
    */
   public void setIncident(Incident incident) {
      this.incident = incident;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setIncidentId(java.lang.Long)
    */
   public void setIncidentId(Long incidentId) {
      this.incidentId = incidentId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setUser(gov.nwcg.isuite.domain.access.User)
    */
   public void setUser(User user) {
      this.user = user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setUserId(java.lang.Long)
    */
   public void setUserId(Long userId) {
      this.userId = userId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setUserType(gov.nwcg.isuite.domain.access.UserTypeEnum)
    */
   public void setUserType(RestrictedIncidentUserTypeEnum userType) {
      this.userType = userType;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setAccessGrantedBy(java.lang.String)
    */
   public void setAccessGrantedBy(String loginName) {
      this.accessGrantedBy=loginName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getAccessEndDate()
    */
   public Date getAccessEndDate() {
      return this.accessEndDate;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#getAccessGrantedBy()
    */
   public String getAccessGrantedBy() {
      return this.accessGrantedBy;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.RestrictedIncidentUser#setAccessEndDate(java.util.Date)
    */
   public void setAccessEndDate(Date endDate) {
      this.accessEndDate = endDate;
   }

//   /**
//    * Returns the userRoles.
//    *
//    * @return 
//    *		the userRoles to return
//    */
//   public Collection<SystemRole> getUserRoles() {
//	   return userRoles;
//   }

//   /**
//    * Sets the userRoles.
//    *
//    * @param userRoles 
//    *			the userRoles to set
//    */
//   public void setUserRoles(Collection<SystemRole> userRoles) {
//	   this.userRoles = userRoles;
//   }   
   
   /*
    * (non-Javadoc)
    * 
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object obj) {
      if ( obj == null ) return false;
      if ( this == obj ) return true;
      if ( getClass() != obj.getClass() ) return false;
      RestrictedIncidentUserImpl o = (RestrictedIncidentUserImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,accessEndDate,accessGrantedBy,incidentId,userId,userType},
      			new Object[]{o.id,o.accessEndDate,o.accessGrantedBy,o.incidentId,o.userId,o.userType})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,accessEndDate,accessGrantedBy,incidentId,userId,userType})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("accessEndDate", accessEndDate)
	       .append("accessGrantedBy", accessGrantedBy)
	       .append("incidentId",incidentId)
	       .append("userId",userId)
	       .append("userType",userType)
	       .appendSuper(super.toString())
	       .toString();
   }

	public Date getDefaultCheckinDate() {
		return defaultCheckinDate;
	}
	
	public void setDefaultCheckinDate(Date defaultCheckinDate) {
		this.defaultCheckinDate = defaultCheckinDate;
	}
	
	public String getDefaultCheckinType() {
		return defaultCheckinType;
	}
	
	public void setDefaultCheckinType(String defaultCheckinType) {
		this.defaultCheckinType = defaultCheckinType;
	}

   
   
}
