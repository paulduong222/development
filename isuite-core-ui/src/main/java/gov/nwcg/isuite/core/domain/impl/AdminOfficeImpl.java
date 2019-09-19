package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "isw_admin_office")
@SequenceGenerator(name="SEQ_ADMIN_OFFICE", sequenceName="SEQ_ADMIN_OFFICE")
public class AdminOfficeImpl extends PersistableImpl implements AdminOffice {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ADMIN_OFFICE")
   private Long id = 0L;
   
   @Column(name = "OFFICE_NAME", length = 55, nullable = false, unique = true)
   private String officeName;

   @Column(name = "PHONE", length = 12)
   private String phone;
   
   @Column(name="DELETED_DATE")
   private Date deletedDate;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;
   
   @OneToOne(targetEntity=AddressImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
   @JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = false)
   private Address address;
   
   @OneToMany(targetEntity=ContractorAgreementImpl.class,fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="adminOffice")		 
   private Collection<ContractorAgreement> contractorAgreements = new ArrayList<ContractorAgreement>();

   public AdminOfficeImpl(String officeName, String phone, Date deletedDate, Address address) 
   {
      super();
      
      if ( officeName == null ) 
      {
         throw new IllegalArgumentException("Office Name cannot be null.");
      }
      this.officeName = officeName;
      
      this.phone = phone;
            
      if ( address == null ) 
      {
         throw new IllegalArgumentException("Address cannot be null.");
      }
      this.address = address;
   }

   /**
    * Default constructor.
    * 
    */
   public AdminOfficeImpl() {
      super();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#getAddress()
    */
   public Address getAddress() {
      return this.address;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#setAddress(gov.nwcg.isuite.core.domain.Address)
    */
   public void setAddress(Address address) {
      this.address = address;
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#getOfficeName()
    */
   public String getOfficeName() {
      return this.officeName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#setOfficeName(java.lang.String)
    */
   public void setOfficeName(String officeName) {
      this.officeName = officeName;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#getPhone()
    */
   public String getPhone() {
      return this.phone;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.AdminOffice#setPhone(java.lang.String)
    */
   public void setPhone(String phone) {
      this.phone = phone;
   }

   
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AdminOffice#getDeletedDate()
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AdminOffice#setDeletedDate(java.util.Date)
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
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
    * @see gov.nwcg.isuite.core.domain.AdminOffice#setStandard(java.lang.Boolean)
    */
   public void setStandard(Boolean standard) {
   	this.standard = standard;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.AdminOffice#isStandard()
    */
   public Boolean isStandard() {
   	return standard;
   }   
   
  	 
   /**		 
    * Returns the contractorAgreements.		 
    *		 
    * @return 		 
    * the contractorAgreements to return		 
   */		 
   public Collection<ContractorAgreement> getContractorAgreements() {		 
	   return contractorAgreements;		 
   }		 
   	 		 
   /**		 
    * Sets the contractorAgreements.		 
    *		 
    * @param contractorAgreements 		 
    * the contractorAgreements to set		 
    */		 
   public void setContractorAgreements(		 
		   Collection<ContractorAgreement> contractorAgreements) {		 
	   this.contractorAgreements = contractorAgreements;		 
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
      AdminOfficeImpl o = (AdminOfficeImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,officeName, phone, address, deletedDate},
      			new Object[]{o.id,o.officeName, o.phone, o.address, o.deletedDate})
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
	  	.append(officeName)
	  	.append(phone)
	  	.append(address)
	  	.append(deletedDate)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("officeName",officeName)
		  	.append("phone",phone)
		  	.append("address",address)
		  	.append("deletedDate",deletedDate)
	       .appendSuper(super.toString())
	       .toString();
   }
   
}
