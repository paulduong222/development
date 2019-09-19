package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.AssignmentTime;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Geoff Dyer
 * 
 */
@Entity
@Table(name = "isw_address")
@SequenceGenerator(name="SEQ_ADDRESS", sequenceName="SEQ_ADDRESS")
public class AddressImpl extends PersistableImpl implements Address {

   @Id 
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ADDRESS")
   private Long id = 0L;
   
   @Column(name = "ADDRESS_LINE_1", length = 35)
   private String addressLine1;

   @Column(name = "ADDRESS_LINE_2", length = 35)
   private String addressLine2;
   
   @Column(name = "CITY", length = 30)
   private String city;
   
   @Column(name = "POSTAL_CODE", length = 10)
   private String postalCode;
   
   @OneToOne(targetEntity=CountrySubdivisionImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name = "COUNTRY_SUBDIVISION_ID", insertable = true, updatable = true, unique = false, nullable = true)
   private CountrySubdivision countrySubdivision;

   @OneToMany(targetEntity=ContractorImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
   private Collection<Contractor> contractors = new ArrayList<Contractor>();
   
   @OneToMany(targetEntity=AssignmentTimeImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mailingAddress")
   private Collection<AssignmentTime> assignmentTimes = new ArrayList<AssignmentTime>();
   
   @OneToMany(targetEntity=RscTrainingTrainerImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "address")
   private Collection<RscTrainingTrainer> rscTrainingTrainers = new ArrayList<RscTrainingTrainer>();
   
   @OneToMany(targetEntity=ResourceHomeUnitContactImpl.class, fetch = FetchType.LAZY, mappedBy = "address")
   private Collection<ResourceHomeUnitContact> resourceHomeUnitContacts = new ArrayList<ResourceHomeUnitContact>();

   public AddressImpl(String addressLine1, String addressLine2, String city, 
		   				String postalCode, CountrySubdivision countrySubdivision) {
      super();
      
      if ( addressLine1 == null ) {
          throw new IllegalArgumentException("Address Line 1 cannot be null.");
       }
      this.addressLine1 = addressLine1;
      this.addressLine2 = addressLine2;
      
      if ( city == null ) {
          throw new IllegalArgumentException("City cannot be null.");
       }
      this.city = city;
      
      if ( postalCode == null ) {
          throw new IllegalArgumentException("Postal Code cannot be null.");
       }
      this.postalCode = postalCode;
      
      if ( countrySubdivision == null ) {
          throw new IllegalArgumentException("Country Subdivision cannot be null.");
       }
      this.countrySubdivision = countrySubdivision;
   }

   /**
    * Default constructor.
    * 
    */
   public AddressImpl() {
      super();
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#getAddressLine1()
    */
   public String getAddressLine1() {
      return this.addressLine1;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#setAddressLine1(java.lang.String)
    */
   public void setAddressLine1(String addressLine1) {
      this.addressLine1 = addressLine1;
   }
   
   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#getAddressLine2()
    */
   public String getAddressLine2() {
      return this.addressLine2;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#setAddressLine2(java.lang.String)
    */
   public void setAddressLine2(String addressLine2) {
      this.addressLine2 = addressLine2;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#getCity()
    */
   public String getCity() {
      return this.city;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#setAddressLine2(java.lang.String)
    */
   public void setCity(String city) {
      this.city = city;
   }

   
    /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#getPostalCode()
    */
    public String getPostalCode() {
		return postalCode;
	}
	
	/*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#setPostalCode(java.lang.String)
    */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#getCountrySubdivision()
    */
	public CountrySubdivision getCountrySubdivision() {
		return countrySubdivision;
	}
	
	/*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.domain.access.Address#setCountrySubdivision(gov.nwcg.isuite.core.domain.CountrySubdivision)
    */
	public void setCountrySubdivision(CountrySubdivision countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
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
    * Returns the contractors.
    *
    * @return 
    *		the contractors to return
    */
   public Collection<Contractor> getContractors() {
	   return contractors;
   }

   /**
    * Sets the contractors.
    *
    * @param contractors 
    *			the contractors to set
    */
   public void setContractors(Collection<Contractor> contractors) {
	   this.contractors = contractors;
   }   
   
   /**
	 * @return the assignmentTimes
	 */
	public Collection<AssignmentTime> getAssignmentTimes() {
		return assignmentTimes;
	}
	
	/**
	 * @param assignmentTimes the assignmentTimes to set
	 */
	public void setAssignmentTimes(Collection<AssignmentTime> assignmentTimes) {
		this.assignmentTimes = assignmentTimes;
	}
	
	/**
	 * @param rscTrainingTrainers the rscTrainingTrainers to set
	 */
	public void setRscTrainingTrainers(Collection<RscTrainingTrainer> rscTrainingTrainers) {
		this.rscTrainingTrainers = rscTrainingTrainers;
	}

	/**
	 * @return the rscTrainingTrainers
	 */
	public Collection<RscTrainingTrainer> getRscTrainingTrainers() {
		return rscTrainingTrainers;
	}

	/**
	 * @param resourceHomeUnitContacts the resourceHomeUnitContacts to set
	 */
	public void setResourceHomeUnitContacts(Collection<ResourceHomeUnitContact> resourceHomeUnitContacts) {
		this.resourceHomeUnitContacts = resourceHomeUnitContacts;
	}
	
	/**
	 * @return the resourceHomeUnitContacts
	 */
	public Collection<ResourceHomeUnitContact> getResourceHomeUnitContacts() {
		return resourceHomeUnitContacts;
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
      AddressImpl o = (AddressImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,addressLine1, addressLine2,city,postalCode, countrySubdivision},
      			new Object[]{o.id,o.addressLine1, o.addressLine2,o.city,o.postalCode, 
      							o.countrySubdivision})
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
	  	.append(addressLine1)
	  	.append(addressLine2)
	  	.append(city)
	  	.append(postalCode)
	  	.append(countrySubdivision)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("addressLine1",addressLine1)
		  	.append("addressLine2",addressLine2)
		  	.append("city",city)
		  	.append("postalCode",postalCode)
		  	.append("countrySubdivision",countrySubdivision)
	       .appendSuper(super.toString())
	       .toString();
   }

}
