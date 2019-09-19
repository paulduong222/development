/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.domain.UserOrganization;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.ForeignKey;

/**
 * @author dbudge
 *
 */
@Table(name = "isw_user_organization")
public class UserOrganizationImpl implements UserOrganization {

   @Column(name = "USER_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long userId;
   
   @ManyToOne(targetEntity=UserImpl.class,fetch=FetchType.LAZY)
   @JoinColumn(name="USER_ID", insertable=true, updatable=true, unique=false, nullable=false)
   @ForeignKey(name="FK_USER_ORG__USER")
   private User user;
   
   @Column(name = "ORGANIZATION_ID", insertable=false, updatable=false, unique=false, nullable=false)
   private Long organizationId;
   
   @ManyToOne(targetEntity=OrganizationImpl.class,fetch=FetchType.LAZY)
   @JoinColumn(name="ORGANIZATION_ID", insertable=true, updatable=true, unique=false, nullable=false)
   @ForeignKey(name="FK_USER_ORG__ORGANIZATION")
   private Organization organization;
   
   
   public UserOrganizationImpl() {
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#getOrganization()
    */
   public Organization getOrganization() {
      return this.organization;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#getOrganizationId()
    */
   public Long getOrganizationId() {
      return this.organizationId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#getUser()
    */
   public User getUser() {
      return this.user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#getUserId()
    */
   public Long getUserId() {
      return this.userId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#setOrganization(gov.nwcg.isuite.core.domain.Organization)
    */
   public void setOrganization(Organization organization) {
      this.organization = organization;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#setOrganizationId(java.lang.Long)
    */
   public void setOrganizationId(Long organizationId) {
      this.organizationId = organizationId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#setUser(gov.nwcg.isuite.core.domain.User)
    */
   public void setUser(User user) {
      this.user = user;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.IUserOrganization#setUserId(java.lang.Long)
    */
   public void setUserId(Long userId) {
      this.userId = userId;
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
		UserOrganizationImpl o = (UserOrganizationImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{userId},
				new Object[]{o.userId})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{userId})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("userId", userId)
		.appendSuper(super.toString())
		.toString();
	}
   
}
