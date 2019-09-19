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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import gov.nwcg.isuite.core.domain.Address;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

@Entity
@SequenceGenerator(name="SEQ_RSC_TRAINING_TRAINER", sequenceName="SEQ_RSC_TRAINING_TRAINER")
@Table(name = "isw_rsc_training_trainer")
public class RscTrainingTrainerImpl extends PersistableImpl implements RscTrainingTrainer {

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RSC_TRAINING_TRAINER")
	private Long id = 0L;
	
	@ManyToOne(targetEntity=ResourceTrainingImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "RESOURCE_TRAINING_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private ResourceTraining resourceTraining;
    
    @Column(name="RESOURCE_TRAINING_ID", insertable = false, updatable = false, nullable = true)
	private Long resourceTrainingId;
    
    @ManyToOne(targetEntity=OrganizationImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "UNIT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Organization unit;
  
	@Column(name="UNIT_ID", insertable = false, updatable = false, nullable = true)
	private Long unitId;
    
    @ManyToOne(targetEntity=AddressImpl.class, fetch=FetchType.LAZY,cascade=CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Address address;
    
    @Column(name="ADDRESS_ID", insertable = false, updatable = false, nullable = true)
	private Long addressId;
    
    @ManyToOne(targetEntity=KindImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "KIND_ID", insertable = true, updatable = true, unique = false, nullable = true)
    private Kind kind;
    
    @Column(name="KIND_ID", insertable = false, updatable = false, nullable = true)
	private Long kindId;
    
    @Column(name="REQEST_NUMBER", length=20)
	private String reqestNumber;
    
    @Column(name="EMAIL", length=50)
	private String email;
    
    @Column(name="PHONE", length=20)
	private String phone;
    
    @Column(name="RESOURCE_NAME", length=70)
	private String resourceName;
    
    @ManyToOne(targetEntity=IncidentResourceImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_RESOURCE_ID", nullable = true)
	private IncidentResource incidentResource;
	
	@Column(name = "INCIDENT_RESOURCE_ID", insertable = false, updatable = false, unique=false, nullable = true)
	private Long incidentResourceId;
    
    
	public RscTrainingTrainerImpl(){
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
	 * @param resourceTraining the resourceTraining to set
	 */
	public void setResourceTraining(ResourceTraining resourceTraining) {
		this.resourceTraining = resourceTraining;
	}

	/**
	 * @return the resourceTraining
	 */
	public ResourceTraining getResourceTraining() {
		return resourceTraining;
	}

	/**
	 * @param resourceTrainingId the resourceTrainingId to set
	 */
	public void setResourceTrainingId(Long resourceTrainingId) {
		this.resourceTrainingId = resourceTrainingId;
	}

	/**
	 * @return the resourceTrainingId
	 */
	public Long getResourceTrainingId() {
		return resourceTrainingId;
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
	 * @param reqestNumber the reqestNumber to set
	 */
	public void setReqestNumber(String reqestNumber) {
		this.reqestNumber = reqestNumber;
	}

	/**
	 * @return the reqestNumber
	 */
	public String getReqestNumber() {
		return reqestNumber;
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
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
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

}
