package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.WorkAreaUser;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Object Relational Mapping to a Work Area.
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_WORK_AREA_USER", sequenceName="SEQ_WORK_AREA_USER")
@Table(name="isw_work_area_user")
public class WorkAreaUserImpl extends PersistableImpl implements WorkAreaUser {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_WORK_AREA_USER")
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @ManyToOne(targetEntity=WorkAreaImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="WORK_AREA_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private WorkArea workArea;
   
   @Column(name="WORK_AREA_ID", insertable=false, updatable=false, unique=false, nullable=true)
   private Long workAreaId;
   
   @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private User user;
   
   @Column(name="USER_ID", insertable=false, updatable=false, unique=false, nullable=true)
   private Long userId;
   
//   @ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//   @JoinTable(name = "isw_wauser_role", 
//		   		joinColumns = { 
//		   			@JoinColumn(name = "work_area_user_id", nullable = false, updatable = false) }
//   				, inverseJoinColumns = { 
//		   			@JoinColumn(name = "role_id", nullable = false, updatable = false) })
//   private Collection<SystemRole> workAreaUserRoles = new ArrayList<SystemRole>();

   @Column(name="USER_TYPE", length=15, nullable=true)
   @Enumerated(EnumType.STRING)
   private WorkAreaUserTypeEnum userType;
   
   @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="SHARED_BY_USER_ID", insertable=true, updatable=true, unique=false)
   private User sharedByUser;
   
   @Column(name="SHARED_BY_USER_ID", insertable=false, updatable=false, unique=false, nullable=true)
   private Long sharedByUserId;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaUser#getWorkArea()
    */
   public WorkArea getWorkArea() {
      return this.workArea;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaUser#getWorkAreaId()
    */
   public Long getWorkAreaId() {
      return this.workAreaId;
   }

   /**
    * @param workArea the workArea to set
    */
   public void setWorkArea(WorkArea workArea) {
      this.workArea = workArea;
   }

   /**
    * @param workAreaId the workAreaId to set
    */
   public void setWorkAreaId(Long workAreaId) {
      this.workAreaId = workAreaId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaUser#getUser()
    */
   public User getUser() {
      return this.user;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.WorkAreaUser#getUserId()
    */
   public Long getUserId() {
      return this.userId;
   }

   /**
    * @param user the user to set
    */
   public void setUser(User user) {
      this.user = user;
   }

   /**
    * @param userId the userId to set
    */
   public void setUserId(Long userId) {
      this.userId = userId;
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
      WorkAreaUserImpl o = (WorkAreaUserImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,userId,workAreaId,userType},
      			new Object[]{o.id,o.userId,o.workAreaId,o.userType})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,userId,workAreaId,userType})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("userId", userId)
	       .append("workAreaId", workAreaId)
	       .append("userType",userType)
	       .appendSuper(super.toString())
	       .toString();
   }

//	/**
//	 * Returns the workAreaUserRoles.
//	 *
//	 * @return 
//	 *		the workAreaUserRoles to return
//	 */
//	public Collection<SystemRole> getWorkAreaUserRoles() {
//		return workAreaUserRoles;
//	}
	
//	/**
//	 * Sets the workAreaUserRoles.
//	 *
//	 * @param workAreaUserRoles 
//	 *			the workAreaUserRoles to set
//	 */
//	public void setWorkAreaUserRoles(Collection<SystemRole> workAreaUserRoles) {
//		this.workAreaUserRoles = workAreaUserRoles;
//	}

	/**
	 * Returns the userType.
	 *
	 * @return 
	 *		the userType to return
	 */
	public WorkAreaUserTypeEnum getUserType() {
		return userType;
	}

	/**
	 * Sets the userType.
	 *
	 * @param userType 
	 *			the userType to set
	 */
	public void setUserType(WorkAreaUserTypeEnum userType) {
		this.userType = userType;
	}

	/**
	 * @param sharedByUser the sharedByUser to set
	 */
	public void setSharedByUser(User sharedByUser) {
		this.sharedByUser = sharedByUser;
	}

	/**
	 * @return the sharedByUser
	 */
	public User getSharedByUser() {
		return sharedByUser;
	}

	/**
	 * @param sharedByUserId the sharedByUserId to set
	 */
	public void setSharedByUserId(Long sharedByUserId) {
		this.sharedByUserId = sharedByUserId;
	}

	/**
	 * @return the sharedByUserId
	 */
	public Long getSharedByUserId() {
		return sharedByUserId;
	}   

}
