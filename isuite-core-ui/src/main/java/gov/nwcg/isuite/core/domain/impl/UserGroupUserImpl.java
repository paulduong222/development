package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserGroupQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Container object representing a user group user
 * 
 * @author bsteiner
 *
 */
@Entity
@SequenceGenerator(name="SEQ_USER_GROUP_USER", sequenceName="SEQ_USER_GROUP_USER")
@NamedQuery(name=UserGroupQuery.DELETE_USER_GROUP_USERS_BY_ID,query=UserGroupQuery.DELETE_USER_GROUP_USERS_BY_ID_QUERY)
@Table(name="isw_user_group_user")
public class UserGroupUserImpl extends PersistableImpl implements UserGroupUser {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_GROUP_USER")
   private Long id = 0L;
   
   @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private User user;
   
   @Column(name="USER_ID", length=19, insertable=false, updatable=false)
   private Long userId;
   
   @ManyToOne(targetEntity=UserGroupImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="USER_GROUP_ID", insertable=true, updatable=true, unique=false, nullable=false)
   private UserGroup userGroup;
   
   @Column(name="USER_GROUP_ID", length=19, insertable=false, updatable=false)
   private Long userGroupId;
   
//   @ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//   @JoinTable(name = "isw_user_group_user_role", 
//		   		joinColumns = { 
//		   			@JoinColumn(name = "user_group_user_id", nullable = false, updatable = false) }
//   				, inverseJoinColumns = { 
//		   			@JoinColumn(name = "role_id", nullable = false, updatable = false) })
//   private Collection<SystemRole> userGroupUserRoles;
	
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#getUser()
    */
   public User getUser() {
      return this.user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#getUserGroup()
    */
   public UserGroup getUserGroup() {
      return this.userGroup;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#getUserGroupId()
    */
   public Long getUserGroupId() {
      return this.userGroupId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#getUserId()
    */
   public Long getUserId() {
      return this.userId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#setUser(gov.nwcg.isuite.domain.access.User)
    */
   public void setUser(User user) {
      this.user = user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#setUserGroup(gov.nwcg.isuite.domain.access.enterprise.UserGroup)
    */
   public void setUserGroup(UserGroup group) {
      this.userGroup = group;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#setUserGroupId(java.lang.Long)
    */
   public void setUserGroupId(Long groupId) {
      this.userGroupId = groupId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroupUser#setUserId(java.lang.Long)
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

//   /**
//    * Returns the userGroupUserRoles.
//    *
//    * @return 
//    *		the userGroupUserRoles to return
//    */
//   public Collection<SystemRole> getUserGroupUserRoles() {
//	   return userGroupUserRoles;
//   }

//   /**
//    * Sets the userGroupUserRoles.
//    *
//    * @param userGroupUserRoles 
//    *			the userGroupUserRoles to set
//    */
//   public void setUserGroupUserRoles(Collection<SystemRole> userGroupUserRoles) {
//	   this.userGroupUserRoles = userGroupUserRoles;
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
      UserGroupUserImpl o = (UserGroupUserImpl)obj;
      return new EqualsBuilder()
      	  .append(new Object[]{id,userGroupId,userId},
      			  new Object[]{o.id,o.userGroupId,o.userId})
      	  .appendSuper(super.equals(o))
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,userGroupId,userId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("userGroupId", userGroupId)
	       .append("userId", userId)
	       .appendSuper(super.toString())
	       .toString();
   }

}
