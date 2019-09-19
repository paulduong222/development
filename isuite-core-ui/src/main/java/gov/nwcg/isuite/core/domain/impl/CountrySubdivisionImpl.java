/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;



import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author doug
 *
 */
@Entity
@Table(name="iswl_country_subdivision")
@NamedQuery(name=ReferenceDataQuery.IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE,query=ReferenceDataQuery.IS_COUNTRY_SUBDIVISION_ABRV_UNIQUE_QUERY)
public class CountrySubdivisionImpl extends PersistableImpl implements CountrySubdivision {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
  
   @Column(name="CS_NAME", length=255)
	private String name;
	
   @Column(name="CS_ABBREVIATION", length=4)
	private String abbreviation;

   @Column(name="CS_STANDARD")
   private Boolean standard;

   @OneToOne(targetEntity=CountryCodeImpl.class, fetch=FetchType.EAGER)
//   @PrimaryKeyJoinColumn
   @JoinColumn(name = "COUNTRY_ID", insertable = true, updatable = true, unique = false, nullable = false)
//   @ForeignKey(name="FK_CS__COUNTRY_CODE_ID")
   private CountryCode countryCode;
    
   @Column(name="COUNTRY_ID", insertable = false, updatable = false, nullable = false)
   private Long countryCodeId;
	
   @OneToMany(targetEntity=JetPortImpl.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "countrySubdivision")
   private Collection<JetPort> jetPorts;
   
   /**
    * Default Constructor
    */
   public CountrySubdivisionImpl() {
      super();
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
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#getAbbreviation()
	 */
	public String getAbbreviation() {
		return this.abbreviation;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#setAbbreviation(java.lang.String)
	 */
	public void setAbbreviation(String abbreviation) {
		if (abbreviation == null) {
			throw new IllegalArgumentException("abbreviation can not be null");
		}
		this.abbreviation = abbreviation;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#getName()
	 */
	public  String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#setName(java.lang.String)
	 */
	public void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name can not be null");
		}
		this.name = name; 
	}

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#isStandard()
    */
   public  Boolean isStandard() {
      return (null==this.standard ? Boolean.TRUE : this.standard);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.impl.CountryCodeSubdivision#setStandard(java.lang.boolean)
    */
   public void setStandard(Boolean standard) {
      this.standard = standard; 
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountryCodeSubdivision#getCountryCodeId()
    */
   public Long getCountryCodeId() {
      return this.countryCodeId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountryCodeSubdivision#setCountryCodeId(java.lang.Long)
    */
   public void setCountryCodeId(Long id) {
      if ( id == null ) {
         throw new IllegalArgumentException("countryCodeId can not be null");
      }
      this.countryCodeId = id;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountryCodeSubdivision#getCountryCode()
    */
   public CountryCode getCountryCode() {
      return this.countryCode;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.CountryCodeSubdivision#setCountryCode(gov.nwcg.isuite.domain.access.CountryCode)
    */
   public void setCountryCode(CountryCode countryCode) {
      this.countryCode = countryCode;
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
      CountrySubdivisionImpl o = (CountrySubdivisionImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,name,abbreviation,countryCodeId,countryCode,standard},
      			new Object[]{o.id,o.name,o.abbreviation,o.countryCodeId,o.countryCode,o.standard})
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
	  	.append(name)
	  	.append(standard)
	  	.append(countryCodeId)
	  	.append(countryCode)
	  	.append(abbreviation)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("name", name)
	       .append("countryCodeId",countryCodeId)
	       .append("standard",standard)
	       .append("abbreviation", abbreviation)
	       .appendSuper(super.toString())
	       .toString();
   }

	public Collection<JetPort> getJetPorts() {
		return jetPorts;
	}
	
	public void setJetPorts(Collection<JetPort> jetPorts) {
		this.jetPorts = jetPorts;
	}


}
