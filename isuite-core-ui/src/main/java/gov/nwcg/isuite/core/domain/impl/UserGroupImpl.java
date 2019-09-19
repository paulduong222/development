package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserGroup;
import gov.nwcg.isuite.core.domain.UserGroupUser;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserGroupQuery;
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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;

/**
 * Container object representing a user group
 * 
 * @author bsteiner
 *
 */
@Entity
@SequenceGenerator(name="SEQ_USER_GROUP", sequenceName="SEQ_USER_GROUP")
@Table(name="isw_user_group")
@NamedQuery(name=UserGroupQuery.IS_GROUP_NAME_UNIQUE_TO_USER,query=UserGroupQuery.IS_GROUP_NAME_UNIQUE_TO_USER_QUERY)
public class UserGroupImpl extends PersistableImpl implements UserGroup {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_GROUP")
   private Long id = 0L;   
   
   @Column(name="GROUP_NAME", length=50, unique=true)
   private String groupName;
   
   @ManyToOne(targetEntity=UserImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="GROUP_OWNER_ID", insertable=true, updatable=true, unique=true, nullable=false)
   private User groupOwner;

   @Column(name="GROUP_OWNER_ID", length=19, insertable=false, updatable=false)
   private Long groupOwnerId;
   
   @OneToMany(targetEntity=UserGroupUserImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userGroup")
   @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
   private Collection<UserGroupUser> userGroupUsers = new ArrayList<UserGroupUser>();

   public UserGroupImpl(){
	   
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#getGroupName()
    */
   public String getGroupName() {
      return this.groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#setGroupName(java.lang.String)
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#getGroupOwner()
    */
   public User getGroupOwner() {
      return groupOwner;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#setGroupOwner(gov.nwcg.isuite.domain.access.User)
    */
   public void setGroupOwner(User groupOwner) {
      this.groupOwner = groupOwner;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#getGroupOwnerId()
    */
   public Long getGroupOwnerId() {
      return groupOwnerId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.enterprise.UserGroup#setGroupOwnerId(java.lang.Long)
    */
   public void setGroupOwnerId(Long groupOwnerId) {
      this.groupOwnerId = groupOwnerId;
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

   /**
    * Returns the userGroupUsers.
    *
    * @return 
    *		the userGroupUsers to return
    */
   public Collection<UserGroupUser> getUserGroupUsers() {
	   return userGroupUsers;
   }

   /**
    * Sets the userGroupUsers.
    *
    * @param userGroupUsers 
    *			the userGroupUsers to set
    */
   public void setUserGroupUsers(Collection<UserGroupUser> userGroupUsers) {
	   this.userGroupUsers = userGroupUsers;
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
      UserGroupImpl o = (UserGroupImpl)obj;
      return new EqualsBuilder()
      	  .append(new Object[]{id,groupName,groupOwnerId},
      			  new Object[]{o.id,o.groupName,o.groupOwnerId})
      	  .appendSuper(super.equals(o))
	      .isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,groupName,groupOwnerId})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("groupName", groupName)
	       .append("groupOwnerId", groupOwnerId)
	       .appendSuper(super.toString())
	       .toString();
   }




}
