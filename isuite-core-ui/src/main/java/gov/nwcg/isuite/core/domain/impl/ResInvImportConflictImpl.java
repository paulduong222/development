package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.domain.ResInvImportConflict;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "isw_resinv_import_conflict")
@SequenceGenerator(name="SEQ_RESINV_IMPORT_CONFLICT", sequenceName="SEQ_RESINV_IMPORT_CONFLICT")
public class ResInvImportConflictImpl extends PersistableImpl implements ResInvImportConflict {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_RESINV_IMPORT_CONFLICT")
	private Long id = 0L;

	@Column(name="DESCRIPTION",length=300)
	private String description;

	@ManyToOne(targetEntity=ResInvImportImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "RESINV_IMPORT_ID", nullable = false)
	private ResInvImport resInvImport;
	
	@Column(name="RESINV_IMPORT_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long resInvImportId;
	
    @Column(name="IS_RESOLVED",nullable=false, length=1)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isResolved;
	
	@Column(name="ROSS_RES_ID",length=30)
	private String rossResId;

	@Column(name="RESOURCE_NAME",length=75)
	private String resourceName;
	
	@Column(name="FIRST_NAME",length=75)
	private String firstName;

	@Column(name="LAST_NAME",length=75)
	private String lastName;
	
	@Column(name="GACC_ORG_UNIT_CODE",length=20)
	private String gaccOrgUnitCodeName;
	
	@Column(name="GACC_DISP_UNIT_CODE",length=20)
	private String gaccDispUnitCodeName;

	@Column(name="RES_PROV_UNIT_CODE",length=20)
	private String resProvUnitCodeName;
	
	public ResInvImportConflictImpl(){

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the resInvImport
	 */
	public ResInvImport getResInvImport() {
		return resInvImport;
	}

	/**
	 * @param resInvImport the resInvImport to set
	 */
	public void setResInvImport(ResInvImport resInvImport) {
		this.resInvImport = resInvImport;
	}

	/**
	 * @return the resInvImportId
	 */
	public Long getResInvImportId() {
		return resInvImportId;
	}

	/**
	 * @param resInvImportId the resInvImportId to set
	 */
	public void setResInvImportId(Long resInvImportId) {
		this.resInvImportId = resInvImportId;
	}

	/**
	 * @return the isResolved
	 */
	public StringBooleanEnum getIsResolved() {
		return isResolved;
	}

	/**
	 * @param isResolved the isResolved to set
	 */
	public void setIsResolved(StringBooleanEnum isResolved) {
		this.isResolved = isResolved;
	}

	/**
	 * @return the rossResId
	 */
	public String getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(String rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the gaccOrgUnitCodeName
	 */
	public String getGaccOrgUnitCodeName() {
		return gaccOrgUnitCodeName;
	}

	/**
	 * @param gaccOrgUnitCodeName the gaccOrgUnitCodeName to set
	 */
	public void setGaccOrgUnitCodeName(String gaccOrgUnitCodeName) {
		this.gaccOrgUnitCodeName = gaccOrgUnitCodeName;
	}

	/**
	 * @return the gaccDispUnitCodeName
	 */
	public String getGaccDispUnitCodeName() {
		return gaccDispUnitCodeName;
	}

	/**
	 * @param gaccDispUnitCodeName the gaccDispUnitCodeName to set
	 */
	public void setGaccDispUnitCodeName(String gaccDispUnitCodeName) {
		this.gaccDispUnitCodeName = gaccDispUnitCodeName;
	}

	/**
	 * @return the resProvUnitCodeName
	 */
	public String getResProvUnitCodeName() {
		return resProvUnitCodeName;
	}

	/**
	 * @param resProvUnitCodeName the resProvUnitCodeName to set
	 */
	public void setResProvUnitCodeName(String resProvUnitCodeName) {
		this.resProvUnitCodeName = resProvUnitCodeName;
	}
}
