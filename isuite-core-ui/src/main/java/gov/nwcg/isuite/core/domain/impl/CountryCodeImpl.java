/**
 * 
 */
package gov.nwcg.isuite.core.domain.impl;



import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Represents a country code.
 * @author doug
 *
 */
@Entity
@Table(name="iswl_country")
public class CountryCodeImpl extends PersistableImpl implements CountryCode {

   @Id
   @GeneratedValue()
   @Column(name = "ID", length=19)
   private Long id = 0L;
   
   @Column(name="CC_NAME", length=255)
	private String name;
	
   @Column(name="CC_ABBREVIATION", length=4)
	private String abbreviation;
	
   /**
    * Full constructor.
    * @param identity unique Identity, can't be null
    * @param name name of country code, can't be null
    * @param abbreviation abbreviation of country, can't be null
    */
	public CountryCodeImpl(String identity, String name, String abbreviation) {
      super();
		setName(name);
		setAbbreviation(abbreviation);
	}
	
   /**
    * Constructor.
    * @param name name of country code, can't be null
    * @param abbreviation abbreviation of country, can't be null
    */
	public CountryCodeImpl(String name, String abbreviation) {
		super();
      setName(name);
		setAbbreviation(abbreviation);
	}
   
   public CountryCodeImpl() {
      super();
   }
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCode#setAbbreviation(java.lang.String)
	 */
	public final void setAbbreviation(String abbreviation) {
		if (abbreviation == null) {
			throw new IllegalArgumentException("abbreviation can not be null");
		}
		this.abbreviation = abbreviation;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCode#getAbbreviation()
	 */
	public final String getAbbreviation() {
		return this.abbreviation;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCode#setName(java.lang.String)
	 */
	public final void setName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name can not be null");
		}
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.CountryCode#getName()
	 */
	public final String getName() {
		return this.name;
	}

    /*
     * 
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
       CountryCodeImpl o = (CountryCodeImpl)obj;
       return new EqualsBuilder()
       	.append(new Object[]{id,name,abbreviation},
       			new Object[]{o.id,o.name,o.abbreviation})
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
 	       .append("abbreviation", abbreviation)
 	       .appendSuper(super.toString())
 	       .toString();
    }   

}
