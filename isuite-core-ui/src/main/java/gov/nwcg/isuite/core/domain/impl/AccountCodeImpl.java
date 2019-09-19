package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AccountCode;
import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.RegionCode;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;

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
 * 
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_ACCOUNT_CODE", sequenceName="SEQ_ACCOUNT_CODE")
@Table(name="isw_account_code")
public class AccountCodeImpl extends PersistableImpl implements AccountCode {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ACCOUNT_CODE")
   private Long id = 0L;
   
   @Column(name="ACCOUNT_CODE", length=50, nullable=false, unique = true)
   private String accountCode;
   
   @OneToOne(targetEntity=AgencyImpl.class,fetch=FetchType.LAZY)
   @JoinColumn(name = "AGENCY_ID", insertable = true, updatable = true, unique = false, nullable = false)
   private Agency agency;
   
   @Column(name="AGENCY_ID", insertable = false, updatable = false, nullable = false, length=19)
   private Long agencyId;
   
   @OneToOne(targetEntity=RegionCodeImpl.class,fetch=FetchType.LAZY)
   @JoinColumn(name = "REGION_UNIT_ID", insertable = true, updatable = true, unique = false, nullable = true)
   private RegionCode regionUnit;
   
   @Column(name="REGION_UNIT_ID", insertable = false, updatable = false, nullable = true, length=19)
   private Long regionUnitId;

   @OneToMany(targetEntity=IncidentAccountCodeImpl.class,fetch=FetchType.LAZY,mappedBy="accountCode")
   private Collection<IncidentAccountCode> incidentAccountCodes=new ArrayList<IncidentAccountCode>();
   
   /**
    * Default constructor.
    *
    */
   public AccountCodeImpl() {
      super();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#getAccountCode()
    */
   public String getAccountCode() {
      return this.accountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#getAgency()
    */
   public Agency getAgency() {
      return this.agency;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#getAgencyId()
    */
   public Long getAgencyId() {
      return this.agencyId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#getRegionUnitId()
    */
   public Long getRegionUnitId() {
      return this.regionUnitId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#setAccountCode(java.lang.String)
    */
   public void setAccountCode(String accountCode) {
      this.accountCode = accountCode;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#setAgency(gov.nwcg.isuite.domain.organization.Agency)
    */
   public void setAgency(Agency agency) {
      this.agency = agency;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#setAgencyId(java.lang.Long)
    */
   public void setAgencyId(Long agencyId) {
      this.agencyId = agencyId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.domain.finance.AccountCode#setRegionUnitId(java.lang.Long)
    */
   public void setRegionUnitId(Long regionUnitId) {
      this.regionUnitId = regionUnitId;
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
      AccountCodeImpl o = (AccountCodeImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,accountCode,agencyId,regionUnitId},
      			new Object[]{o.id,o.accountCode,o.agencyId,o.regionUnitId})
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
	  	.append(accountCode)
	  	.append(agencyId)
	  	.append(regionUnitId)
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("accountCode", accountCode)
	       .append("agencyId", agencyId)
	       .append("regionUnitId", regionUnitId)
	       .appendSuper(super.toString())
	       .toString();
   }

   /**
    * Returns the incidentAccountCodes.
    *
    * @return 
    *		the incidentAccountCodes to return
    */
   public Collection<IncidentAccountCode> getIncidentAccountCodes() {
	   return incidentAccountCodes;
   }

   /**
    * Sets the incidentAccountCodes.
    *
    * @param incidentAccountCodes 
    *			the incidentAccountCodes to set
    */
   public void setIncidentAccountCodes(
		   Collection<IncidentAccountCode> incidentAccountCodes) {
	   this.incidentAccountCodes = incidentAccountCodes;
   }

   /**
    * Returns the regionUnit.
    *
    * @return 
    *		the regionUnit to return
    */
   public RegionCode getRegionUnit() {
	   return regionUnit;
   }

   /**
    * Sets the regionUnit.
    *
    * @param regionUnit 
    *			the regionUnit to set
    */
   public void setRegionUnit(RegionCode regionUnit) {
	   this.regionUnit = regionUnit;
   }   

}
