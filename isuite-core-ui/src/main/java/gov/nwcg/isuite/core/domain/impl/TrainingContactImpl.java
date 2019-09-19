package gov.nwcg.isuite.core.domain.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.TrainingContact;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

@Entity
@Table(name = "isw_training_contact")
@SequenceGenerator(name="SEQ_TRAINING_CONTACT", sequenceName="SEQ_TRAINING_CONTACT")
public class TrainingContactImpl extends PersistableImpl implements
		TrainingContact {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_TRAINING_CONTACT")
	private Long id = 0L;
	
	@Column(name = "PHONE", length = 20)
	private String phone;
	
	@Column(name="EMAIL", length=50)
	private String email;
	
	@OneToOne(targetEntity=AddressImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Address address;
    
    @Column(name="ADDRESS_ID", insertable = false, updatable = false, nullable = true)
	private Long addressId;
    
    @ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = false)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentResourceId;
	
	@Column(name = "IS_ACTIVE",nullable=false)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum active;
	
	/**
	 * Default constructor.
	 * 
	 */
	public TrainingContactImpl() {
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
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public StringBooleanEnum getActive() {
		return active;
	}

}
