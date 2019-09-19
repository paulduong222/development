package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.Contractor;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.persistence.hibernate.query.ContractorQuery;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


@Entity
@SequenceGenerator(name="SEQ_CONTRACTOR", sequenceName="SEQ_CONTRACTOR")
@NamedQueries({
	@NamedQuery(name=ContractorQuery.DISABLE_CONTRACTORS,query=ContractorQuery.DISABLE_CONTRACTORS_QUERY)
	,@NamedQuery(name=ContractorQuery.ENABLE_CONTRACTORS,query=ContractorQuery.ENABLE_CONTRACTORS_QUERY)
})
@Table(name = "isw_contractor")
public class ContractorImpl extends PersistableImpl implements Contractor {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CONTRACTOR")
	private Long id = 0L;

    @Column(name="NAME", length=50,nullable=false)
	private String name;
	
    @Column(name="TIN", length=128)
	private String tin;
	
    @Column(name="DUNS", length=11)
	private String duns;
	
    @Column(name="PHONE", length=12)
	private String phone;

    @Column(name="FAX", length=12)
    private String fax;

    @ManyToOne(targetEntity=AddressImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Address address;
    
    @Column(name="ADDRESS_ID", insertable = false, updatable = false, nullable = true)
	private Long addressId;
	
    @Column(name="DELETED_DATE")
	private Date deletedDate;
	
    @OneToMany(targetEntity=ContractorAgreementImpl.class,fetch=FetchType.LAZY,cascade=CascadeType.REMOVE,mappedBy="contractor")
    private Collection<ContractorAgreement> contractorAgreements = new ArrayList<ContractorAgreement>();
    
    @ManyToMany(targetEntity=IncidentImpl.class,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "isw_incident_contractor", 
			   joinColumns = 
			   		{@JoinColumn(name = "contractor_id",  nullable = false, updatable = false) },
			   inverseJoinColumns = 
			   		{ @JoinColumn(name = "incident_id", nullable = false, updatable = false) }
    )
    private Collection<Incident> incidents = new ArrayList<Incident>();
    
    @Column(name="IS_ENABLED",nullable=false)
    private Boolean enabled;

	@ManyToMany(targetEntity=ResourceImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "contractors")
	private Collection<Resource> resources;
	
	@OneToMany(targetEntity=TimeInvoiceImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="contractor")
	private Collection<TimeInvoice> timeInvoices;
	
	//@OneToMany(targetEntity=ContractorPaymentInfoImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="Contractor")
	//private Collection<ContractorPaymentInfo> contractorPaymentInfos;
	
	public ContractorImpl(){

	}

	/**
	 * Returns the id.
	 *
	 * @return 
	 *		the id to return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the name.
	 *
	 * @return 
	 *		the name to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name 
	 *			the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the tin.
	 *
	 * @return 
	 *		the tin to return
	 */
	public String getTin() {
		return tin;
	}

	/**
	 * Sets the tin.
	 *
	 * @param tin 
	 *			the tin to set
	 */
	public void setTin(String tin) {
		this.tin = tin;
	}

	/**
	 * Returns the duns.
	 *
	 * @return 
	 *		the duns to return
	 */
	public String getDuns() {
		return duns;
	}

	/**
	 * Sets the duns.
	 *
	 * @param duns 
	 *			the duns to set
	 */
	public void setDuns(String duns) {
		this.duns = duns;
	}

	/**
	 * Returns the phone.
	 *
	 * @return 
	 *		the phone to return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone 
	 *			the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Returns the fax.
	 *
	 * @return 
	 *		the fax to return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax 
	 *			the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * Returns the address.
	 *
	 * @return 
	 *		the address to return
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address 
	 *			the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Returns the addressId.
	 *
	 * @return 
	 *		the addressId to return
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * Sets the addressId.
	 *
	 * @param addressId 
	 *			the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * Returns the deletedDate.
	 *
	 * @return 
	 *		the deletedDate to return
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * Sets the deletedDate.
	 *
	 * @param deletedDate 
	 *			the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * Returns the contractorAgreements.
	 *
	 * @return 
	 *		the contractorAgreements to return
	 */
	public Collection<ContractorAgreement> getContractorAgreements() {
		return contractorAgreements;
	}

	/**
	 * Sets the contractorAgreements.
	 *
	 * @param contractorAgreements 
	 *			the contractorAgreements to set
	 */
	public void setContractorAgreements(
			Collection<ContractorAgreement> contractorAgreements) {
		this.contractorAgreements = contractorAgreements;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#isEnabled()
	 */
	public Boolean isEnabled() {
		return enabled;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#setEnabled(java.lang.Boolean)
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#getIncidents()
	 */
  	public Collection<Incident> getIncidents() {
  		return incidents;
  	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#setIncidents(java.util.Collection)
	 */
  	public void setIncidents(Collection<Incident> incidents) {
  		this.incidents = incidents;
  	}

	/**
	 * @return the resources
	 */
	public Collection<Resource> getResources() {
		return resources;
	}

	/**
	 * @param resources the resources to set
	 */
	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
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
		ContractorImpl o = (ContractorImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,name,tin,duns,phone,fax,addressId,deletedDate,enabled},
				new Object[]{o.id,o.name,o.tin,o.duns,o.phone,o.fax,o.addressId,o.deletedDate,o.enabled})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,name,tin,duns,phone,fax,addressId,deletedDate,enabled})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("name", name)
		.append("tin", tin)
		.append("duns", duns)
		.append("phone", phone)
		.append("fax", fax)
		.append("addressId", addressId)
		.append("deletedDate", deletedDate)
		.append("enabled", enabled)
		.appendSuper(super.toString())
		.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#setTimeInvoices(java.util.Collection)
	 */
	public void setTimeInvoices(Collection<TimeInvoice> timeInvoices) {
		this.timeInvoices = timeInvoices;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.Contractor#getTimeInvoices()
	 */
	public Collection<TimeInvoice> getTimeInvoices() {
		return timeInvoices;
	}

}
