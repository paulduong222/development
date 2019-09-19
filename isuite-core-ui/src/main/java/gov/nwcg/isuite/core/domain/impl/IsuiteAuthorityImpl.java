package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.IsuiteAuthorityNameEnum;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * This Implementation class encapsulates both the authorities and the roles that are to 
 * be used in isuite.  Due to the fact that a role "is-a"(n) authority, this table will 
 * be used for both.
 * 
 * Note that the hibernate mapping for this inheritance is mapped using a discriminator value.
 * 
 * @author bsteiner
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="AUTHORITY_TYPE", discriminatorType=DiscriminatorType.STRING, length=15)
@DiscriminatorValue("AUTHORITY")
@SequenceGenerator(name="SEQ_AUTHORITY", sequenceName="SEQ_AUTHORITY")
@Table(name="isw_authority", uniqueConstraints={@UniqueConstraint(columnNames={"AUTHORITY_NAME"})})
public class IsuiteAuthorityImpl extends PersistableImpl implements Persistable{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_AUTHORITY")
	private Long id = 0L;

	@Column(name="AUTHORITY_NAME", length=50, nullable=false, insertable=false, updatable=false, unique=true)
	private String authority;

	@Column(name="ADMINISTRATIVE_ROLE_FLG")
	private Boolean administrativeRoleFlg;
   
	
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   /**
    * Default Constructor
    */
   public IsuiteAuthorityImpl() {
   }
   
   /**
    * Constructor.
    * 
    * @param authority String representation of the authority.
    */
   public IsuiteAuthorityImpl(IsuiteAuthorityNameEnum authority) {
      setAuthority(authority);     
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
   
   /* (non-Javadoc)
    * @see org.acegisecurity.GrantedAuthority#getAuthority()
    */
   public String getAuthority() {
      return this.authority;
   }
   
   public void setAuthority(IsuiteAuthorityNameEnum authorityName) {
      if (authorityName == null) {
         throw new IllegalArgumentException("Authority must not be null");
      }
      this.authority = authorityName.getName();
   }

   /**
    * 
    * @param authority
    */
   public void setAuthority(String authority) {
      if (authority == null) {
         throw new IllegalArgumentException("Authority must not be null");
      }
      this.authority = authority;
   }

   /**
    * @return the administrativeRoleFlg
    */
   public Boolean getAdministrativeRoleFlg() {
      return administrativeRoleFlg;
   }

   /**
    * @param administrativeRoleFlg the administrativeRoleFlg to set
    */
   public void setAdministrativeRoleFlg(Boolean administrativeRoleFlg) {
      this.administrativeRoleFlg = administrativeRoleFlg;
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
      IsuiteAuthorityImpl o = (IsuiteAuthorityImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,administrativeRoleFlg,authority},
      			new Object[]{o.id,o.administrativeRoleFlg,o.authority})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,administrativeRoleFlg,authority})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("administrativeRoleFlg", administrativeRoleFlg)
	       .append("authority", authority)
	       .appendSuper(super.toString())
	       .toString();
   }



}
