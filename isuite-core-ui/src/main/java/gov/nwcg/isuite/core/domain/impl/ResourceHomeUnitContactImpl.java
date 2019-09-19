package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_RESOURCE_HOME_UNIT_CONTACT", sequenceName="SEQ_RESOURCE_HOME_UNIT_CONTACT")
@Table(name="isw_resource_home_unit_contact")
public class ResourceHomeUnitContactImpl extends PersistableImpl implements
		ResourceHomeUnitContact {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESOURCE_HOME_UNIT_CONTACT")
	private Long id;
	
	@ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long incidentResourceId;
	
	@ManyToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Organization unit;
  
	@Column(name="UNIT_ID", insertable = false, updatable = false, nullable = true)
	private Long unitId;
	
	@ManyToOne(targetEntity=AddressImpl.class, fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Address address;
  
	@Column(name="ADDRESS_ID", insertable = false, updatable = false, nullable = true)
	private Long addressId;
	
	@Column(name = "PHONE", length = 20)
	private String phone;
	
	@Column(name = "EMAIL", length = 50)
	private String email;
	
	@Column(name = "CONTACT_NAME", length = 70)
	private String contactName;
	
	
	/**
	 * Default constructor
	 */
	public ResourceHomeUnitContactImpl() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * @param incidentResource the incidentResource to set
	 */
	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	/**
	 * @return the incidentResource
	 */
	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * @param unit the unit to set
	 */
	public void setUnit(Organization unit) {
		this.unit = unit;
	}

	/**
	 * @return the unit
	 */
	public Organization getUnit() {
		return unit;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the unitId
	 */
	public Long getUnitId() {
		return unitId;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	
}
