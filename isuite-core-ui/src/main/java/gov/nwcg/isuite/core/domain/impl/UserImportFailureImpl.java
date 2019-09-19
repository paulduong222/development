package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.UserImportFailure;
import gov.nwcg.isuite.core.persistence.hibernate.query.UserImportFailureQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Implementation of a SiteUserImportFailure on the Site.
 * 
 * @author mpoll
 */
@Entity
@SequenceGenerator(name = "SEQ_USER_IMPORT_FAILURE", sequenceName = "SEQ_USER_IMPORT_FAILURE")
@Table(name = "isw_user_import_failure")
@NamedQueries({
	@NamedQuery(name=UserImportFailureQuery.DELETE_ALL,query=UserImportFailureQuery.DELETE_ALL_QUERY)
})public class UserImportFailureImpl extends PersistableImpl implements UserImportFailure {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_USER_IMPORT_FAILURE")
   private Long id = 0L;
   
   @Column(name = "FIRST_NAME", length = 30)
   private String firstName;

   @Column(name = "LAST_NAME", length = 35)
   private String lastName;

   @Column(name = "LOGIN_NAME", unique = true, length = 255)
   private String loginName;

   @Column(name = "USER_PASSWORD", length = 100)
   private String password;

   @Column(name = "HOME_UNIT_CODE", length = 8)
   private String homeUnitCode;

   @Column(name = "PDC_UNIT_CODE", length = 8)
   private String pdcUnitCode;
   
   @Column(name = "FAILURE_REASON", length = 255)
   private String failureReason;
   
   @ManyToMany(targetEntity = gov.nwcg.isuite.core.domain.impl.SystemRoleImpl.class)
   @JoinTable(name = "isw_user_failure_role", joinColumns = {
      @JoinColumn(name = "USER_FAILURE_ID")
   }, inverseJoinColumns = {
      @JoinColumn(name = "ROLE_ID")
   })
   @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
   private Collection<SystemRole> roles = new ArrayList<SystemRole>();

   public UserImportFailureImpl() {
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#getLoginName()
    */
   public String getLoginName() {
      return loginName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#setLoginName(java.lang.String)
    */
   public void setLoginName(String name) {
      this.loginName = name;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#getPassword()
    */
   public String getPassword() {
      return password;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#setPassword(java.lang.String)
    *      NOTE: SHOULD ONLY BE USED FOR HIBERNATE PURPOSES. ALL OTHER CODE
    *      SHOULD BE CALLING SETENCRYPTEDPASSWORD!!!!
    */
   public void setPassword(String password) {
      if ( password == null ) {
         password = "";
      }
      this.password = password;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#getFirstName()
    */
   public String getFirstName() {
      return firstName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.SiteUserImportFailure#setFirstName(java.lang.String)
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#getLastName()
    */
   public String getLastName() {
      return lastName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#setLastName(java.lang.String)
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#getHomeUnit()
    */
   public String getHomeUnitCode() {
      return homeUnitCode;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.site.SiteUserImportFailure#setHomeUnit(java.lang.String)
    */
   public void setHomeUnitCode(String homeUnitCode) {
      this.homeUnitCode = homeUnitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.site.SiteUserImportFailure#getPdcUnitCode()
    */
   public String getPdcUnitCode() {
      return pdcUnitCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.site.SiteUserImportFailure#setPdcUnitCode(java.lang.String)
    */
   public void setPdcUnitCode(String pdcUnitCode) {
      this.pdcUnitCode = pdcUnitCode;
   }

   /*
    * (non-Javadoc)
    * 
    * @see #getFailureReason()
    */
   public String getFailureReason() {
      return failureReason;
   }

   /*
    * (non-Javadoc)
    * 
    * @see #setFailureReason(java.lang.String)
    */
   public void setFailureReason(String failureReason) {
      this.failureReason = failureReason;

   }

   /**
    * @return the roles
    */
   public Collection<SystemRole> getRoles() {
      return roles;
   }

   /**
    * @param roles the roles to set
    */
   public void setRoles(Collection<SystemRole> roles) {
      this.roles = roles;
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
      UserImportFailureImpl o = (UserImportFailureImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,failureReason,firstName,homeUnitCode
      						 ,lastName,loginName//,nationalUniqueId
      						 ,password,roles},
				new Object[]{o.id,o.failureReason,o.firstName,o.homeUnitCode
					 		,o.lastName,o.loginName//,o.nationalUniqueId
					 		,o.password,o.roles}) 
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,failureReason,firstName,homeUnitCode,
	  					 	lastName,loginName,
	  					 	password,roles})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("failureReason", failureReason)
	       .append("firstName", firstName)
	       .append("homeUnit",homeUnitCode)
	       .append("lastName",lastName)
	       .append("loginName",loginName)
	       .append("password",password)
	       .append("roles",roles)
	       .appendSuper(super.toString())
	       .toString();
   }

}
