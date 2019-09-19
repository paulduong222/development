package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossImportProcessResourceError;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.math.BigDecimal;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ROSS_XML_FILE_DATA", sequenceName="SEQ_ROSS_XML_FILE_DATA")
@Table(name = "isw_ross_xml_file_data")
public class RossXmlFileDataImpl extends PersistableImpl implements RossXmlFileData {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_XML_FILE_DATA")
	private Long id;

	@ManyToOne(targetEntity=RossXmlFileImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_XML_FILE_ID", nullable = false)
	private RossXmlFile rossXmlFile;
	
	@Column(name = "INC_NUMBER", length = 60)
	private String incNumber;

	@Column(name = "INC_NAME", length = 120)
	private String incName;
	
	@Column(name = "INC_TYPE", length = 70)
	private String incType;
	
	@Column(name = "INC_STATE", length = 6)
	private String incState;
	
	@Column(name = "INITIAL_DATE")
	private Date initialDate;
	
	@Column(name = "INC_AGENCY_ABBREV", length = 30)
	private String incAgencyAbbrev;
	
	@Column(name = "INC_DIS_ORG_UNIT_CODE", length = 30)
	private String incDisOrgUnitCode;
	
	@Column(name = "COMPLEX_FLAG", length = 10)
	private String complexFlag;
	
	@Column(name = "COMPLEX_MEMBER_FLAG", length = 10)
	private String complexMemberFlag;
	
	@Column(name = "COMPLEX_INC_NAME", length = 120)
	private String complexIncName;
	
	@Column(name = "COMPLEX_INC_NUMBER", length = 60)
	private String complexIncNumber;
	
	@Column(name = "MERGED_INC_FLAG", length = 10)
	private String mergedIncFlag;
	
	@Column(name = "PREVIOUS_INC_NUMBER", length = 60)
	private String previousIncNumber;
	
	@Column(name = "TRANSFER_DATE")
	private Date transferDate;
	
	@Column(name = "TRANSFER_DATE_GMT")
	private Date transferDateGmt;
	
	@Column(name = "TRANSFER_DATE_TZ_CODE", length = 10)
	private String transferDateTzCode;
	
	@Column(name = "TRANSFER_FROM_ORG_NAME", length = 410)
	private String transferFromOrgName;
	
	@Column(name = "TRANSFER_FROM_ORG_UNIT_CODE", length = 30)
	private String transferFromOrgUnitCode;
	
	@Column(name = "TRANSFER_TO_ORG_NAME", length = 410)
	private String transferToOrgName;
	
	@Column(name = "TRANSFER_TO_ORG_UNIT_CODE", length = 30)
	private String transferToOrgUnitCode;
	
	@Column(name = "TRANSFERRED_FLAG", length = 10)
	private String transferredFlag;
	
	@Column(name = "RES_ID")
	private Long resId;
	
	@Column(name = "REQ_ID", precision = 22, scale = 0)
	private BigDecimal reqId;
	
	@Column(name = "ROOT_REQ_FLAG", length = 10)
	private String rootReqFlag;
	
	@Column(name = "REQ_NUMBER_PREFIX", length = 6)
	private String reqNumberPrefix;
	
	@Column(name = "REQ_NUMBER", length = 36)
	private String reqNumber;
	
	@Column(name = "RES_NAME", length = 200)
	private String resName;
	
	@Column(name = "ASSIGNMENT_NAME", length = 410)
	private String assignmentName;
	
	@Column(name = "RES_PROV_AGENCY_ABBREV", length = 30)
	private String resProvAgencyAbbrev;
	
	@Column(name = "FILLED_CATALOG_ITEM_CODE", length = 16)
	private String filledCatalogItemCode;
	
	@Column(name = "FILLED_CATALOG_ITEM_NAME", length = 310)
	private String filledCatalogItemName;
	
	@Column(name = "EMPLOYMENT_CLASS", length = 65)
	private String employmentClass;
	
	@Column(name = "CATALOG_ITEM_CODE", length = 18)
	private String catalogItemCode;
	
	@Column(name = "CATALOG_ITEM_NAME", length = 180)
	private String catalogItemName;
	
	@Column(name = "QUAL_STATUS", length = 45)
	private String qualStatus;
	
	@Column(name = "RES_PROV_UNIT_CODE", length = 30)
	private String resProvUnitCode;
	
	@Column(name = "JET_PORT", length = 200)
	private String jetPort;
	
	@Column(name = "MOB_ETD")
	private Date mobEtd;
	
	@Column(name = "MOB_ETD_TZ_CODE", length = 10)
	private String mobEtdTzCode;
	
	@Column(name = "MOB_ETA")
	private Date mobEta;
	
	@Column(name = "MOB_ETA_TZ_CODE", length = 10)
	private String mobEtaTzCode;
	
	@Column(name = "MOB_ETE", length = 20)
	private String mobEte;
	
	@Column(name = "DEMOB_ETD")
	private Date demobEtd;
	
	@Column(name = "DEMOB_ETD_TZ_CODE", length = 10)
	private String demobEtdTzCode;
	
	@Column(name = "DEMOB_ETE", length = 20)
	private String demobEte;
	
	@Column(name = "VENDOR_OWNED_FLAG", length = 10)
	private String vendorOwnedFlag;
	
	@Column(name = "VENDOR_NAME", length = 410)
	private String vendorName;
	
	@Column(name = "CONTRACT_TYPE", length = 50)
	private String contractType;
	
	@Column(name = "CONTRACT_NUMBER", length = 110)
	private String contractNumber;
	
	@Column(name = "REQ_CATALOG_NAME", length = 60)
	private String reqCatalogName;
	
	@Column(name = "REQ_CATEGORY_NAME", length = 65)
	private String reqCategoryName;
	
	@OneToMany(targetEntity=RossImportProcessResourceErrorImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossXmlFileData")
	private Collection<RossImportProcessResourceError> rossImportProcessResourceErrors;
	
	@Column(name="IS_ROSS_ASSIGNMENT",nullable=false)
	private Short rossAssignment;

	@Column(name = "IMPORT_STATUS", length = 30)
	private String importStatus;

	@Column(name = "LAST_NAME", length = 62)
	private String lastName;

	@Column(name = "FIRST_NAME", length = 62)
	private String firstName;

	@Column(name = "MIDDLE_NAME", length = 62)
	private String middleName;
	
	public RossXmlFileDataImpl(){
		
	}

	/**
	 * return the id
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
	 * @return the rossXmlFile
	 */
	public RossXmlFile getRossXmlFile() {
		return rossXmlFile;
	}

	/**
	 * @param rossXmlFile the rossXmlFile to set
	 */
	public void setRossXmlFile(RossXmlFile rossXmlFile) {
		this.rossXmlFile = rossXmlFile;
	}

	/**
	 * @return the incNumber
	 */
	public String getIncNumber() {
		return incNumber;
	}

	/**
	 * @param incNumber the incNumber to set
	 */
	public void setIncNumber(String incNumber) {
		this.incNumber = incNumber;
	}

	/**
	 * @return the incName
	 */
	public String getIncName() {
		return incName;
	}

	/**
	 * @param incName the incName to set
	 */
	public void setIncName(String incName) {
		this.incName = incName;
	}

	/**
	 * @return the incType
	 */
	public String getIncType() {
		return incType;
	}

	/**
	 * @param incType the incType to set
	 */
	public void setIncType(String incType) {
		this.incType = incType;
	}

	/**
	 * @return the incState
	 */
	public String getIncState() {
		return incState;
	}

	/**
	 * @param incState the incState to set
	 */
	public void setIncState(String incState) {
		this.incState = incState;
	}

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate() {
		return initialDate;
	}

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	/**
	 * @return the incAgencyAbbrev
	 */
	public String getIncAgencyAbbrev() {
		return incAgencyAbbrev;
	}

	/**
	 * @param incAgencyAbbrev the incAgencyAbbrev to set
	 */
	public void setIncAgencyAbbrev(String incAgencyAbbrev) {
		this.incAgencyAbbrev = incAgencyAbbrev;
	}

	/**
	 * @return the incDisOrgUnitCode
	 */
	public String getIncDisOrgUnitCode() {
		return incDisOrgUnitCode;
	}

	/**
	 * @param incDisOrgUnitCode the incDisOrgUnitCode to set
	 */
	public void setIncDisOrgUnitCode(String incDisOrgUnitCode) {
		this.incDisOrgUnitCode = incDisOrgUnitCode;
	}

	/**
	 * @return the complexFlag
	 */
	public String getComplexFlag() {
		return complexFlag;
	}

	/**
	 * @param complexFlag the complexFlag to set
	 */
	public void setComplexFlag(String complexFlag) {
		this.complexFlag = complexFlag;
	}

	/**
	 * @return the complexMemberFlag
	 */
	public String getComplexMemberFlag() {
		return complexMemberFlag;
	}

	/**
	 * @param complexMemberFlag the complexMemberFlag to set
	 */
	public void setComplexMemberFlag(String complexMemberFlag) {
		this.complexMemberFlag = complexMemberFlag;
	}

	/**
	 * @return the complexIncName
	 */
	public String getComplexIncName() {
		return complexIncName;
	}

	/**
	 * @param complexIncName the complexIncName to set
	 */
	public void setComplexIncName(String complexIncName) {
		this.complexIncName = complexIncName;
	}

	/**
	 * @return the complexIncNumber
	 */
	public String getComplexIncNumber() {
		return complexIncNumber;
	}

	/**
	 * @param complexIncNumber the complexIncNumber to set
	 */
	public void setComplexIncNumber(String complexIncNumber) {
		this.complexIncNumber = complexIncNumber;
	}

	/**
	 * @return the mergedIncFlag
	 */
	public String getMergedIncFlag() {
		return mergedIncFlag;
	}

	/**
	 * @param mergedIncFlag the mergedIncFlag to set
	 */
	public void setMergedIncFlag(String mergedIncFlag) {
		this.mergedIncFlag = mergedIncFlag;
	}

	/**
	 * @return the previousIncNumber
	 */
	public String getPreviousIncNumber() {
		return previousIncNumber;
	}

	/**
	 * @param previousIncNumber the previousIncNumber to set
	 */
	public void setPreviousIncNumber(String previousIncNumber) {
		this.previousIncNumber = previousIncNumber;
	}

	/**
	 * @return the transferDate
	 */
	public Date getTransferDate() {
		return transferDate;
	}

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * @return the transferDateGmt
	 */
	public Date getTransferDateGmt() {
		return transferDateGmt;
	}

	/**
	 * @param transferDateGmt the transferDateGmt to set
	 */
	public void setTransferDateGmt(Date transferDateGmt) {
		this.transferDateGmt = transferDateGmt;
	}

	/**
	 * @return the transferDateTzCode
	 */
	public String getTransferDateTzCode() {
		return transferDateTzCode;
	}

	/**
	 * @param transferDateTzCode the transferDateTzCode to set
	 */
	public void setTransferDateTzCode(String transferDateTzCode) {
		this.transferDateTzCode = transferDateTzCode;
	}

	/**
	 * @return the transferFromOrgName
	 */
	public String getTransferFromOrgName() {
		return transferFromOrgName;
	}

	/**
	 * @param transferFromOrgName the transferFromOrgName to set
	 */
	public void setTransferFromOrgName(String transferFromOrgName) {
		this.transferFromOrgName = transferFromOrgName;
	}

	/**
	 * @return the transferFromOrgUnitCode
	 */
	public String getTransferFromOrgUnitCode() {
		return transferFromOrgUnitCode;
	}

	/**
	 * @param transferFromOrgUnitCode the transferFromOrgUnitCode to set
	 */
	public void setTransferFromOrgUnitCode(String transferFromOrgUnitCode) {
		this.transferFromOrgUnitCode = transferFromOrgUnitCode;
	}

	/**
	 * @return the transferToOrgName
	 */
	public String getTransferToOrgName() {
		return transferToOrgName;
	}

	/**
	 * @param transferToOrgName the transferToOrgName to set
	 */
	public void setTransferToOrgName(String transferToOrgName) {
		this.transferToOrgName = transferToOrgName;
	}

	/**
	 * @return the transferToOrgUnitCode
	 */
	public String getTransferToOrgUnitCode() {
		return transferToOrgUnitCode;
	}

	/**
	 * @param transferToOrgUnitCode the transferToOrgUnitCode to set
	 */
	public void setTransferToOrgUnitCode(String transferToOrgUnitCode) {
		this.transferToOrgUnitCode = transferToOrgUnitCode;
	}

	/**
	 * @return the transferredFlag
	 */
	public String getTransferredFlag() {
		return transferredFlag;
	}

	/**
	 * @param transferredFlag the transferredFlag to set
	 */
	public void setTransferredFlag(String transferredFlag) {
		this.transferredFlag = transferredFlag;
	}

	/**
	 * @return the resId
	 */
	public Long getResId() {
		return resId;
	}

	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId) {
		this.resId = resId;
	}

	/**
	 * @return the reqId
	 */
	public BigDecimal getReqId() {
		return reqId;
	}

	/**
	 * @param reqId the reqId to set
	 */
	public void setReqId(BigDecimal reqId) {
		this.reqId = reqId;
	}

	/**
	 * @return the rootReqFlag
	 */
	public String getRootReqFlag() {
		return rootReqFlag;
	}

	/**
	 * @param rootReqFlag the rootReqFlag to set
	 */
	public void setRootReqFlag(String rootReqFlag) {
		this.rootReqFlag = rootReqFlag;
	}

	/**
	 * @return the reqNumberPrefix
	 */
	public String getReqNumberPrefix() {
		return reqNumberPrefix;
	}

	/**
	 * @param reqNumberPrefix the reqNumberPrefix to set
	 */
	public void setReqNumberPrefix(String reqNumberPrefix) {
		this.reqNumberPrefix = reqNumberPrefix;
	}

	/**
	 * @return the reqNumber
	 */
	public String getReqNumber() {
		return reqNumber;
	}

	/**
	 * @param reqNumber the reqNumber to set
	 */
	public void setReqNumber(String reqNumber) {
		this.reqNumber = reqNumber;
	}

	/**
	 * @return the resName
	 */
	public String getResName() {
		return resName;
	}

	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName) {
		this.resName = resName;
	}

	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName() {
		return assignmentName;
	}

	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	/**
	 * @return the resProvAgencyAbbrev
	 */
	public String getResProvAgencyAbbrev() {
		return resProvAgencyAbbrev;
	}

	/**
	 * @param resProvAgencyAbbrev the resProvAgencyAbbrev to set
	 */
	public void setResProvAgencyAbbrev(String resProvAgencyAbbrev) {
		this.resProvAgencyAbbrev = resProvAgencyAbbrev;
	}

	/**
	 * @return the filledCatalogItemCode
	 */
	public String getFilledCatalogItemCode() {
		return filledCatalogItemCode;
	}

	/**
	 * @param filledCatalogItemCode the filledCatalogItemCode to set
	 */
	public void setFilledCatalogItemCode(String filledCatalogItemCode) {
		this.filledCatalogItemCode = filledCatalogItemCode;
	}

	/**
	 * @return the filledCatalogItemName
	 */
	public String getFilledCatalogItemName() {
		return filledCatalogItemName;
	}

	/**
	 * @param filledCatalogItemName the filledCatalogItemName to set
	 */
	public void setFilledCatalogItemName(String filledCatalogItemName) {
		this.filledCatalogItemName = filledCatalogItemName;
	}

	/**
	 * @return the employmentClass
	 */
	public String getEmploymentClass() {
		return employmentClass;
	}

	/**
	 * @param employmentClass the employmentClass to set
	 */
	public void setEmploymentClass(String employmentClass) {
		this.employmentClass = employmentClass;
	}

	/**
	 * @return the catalogItemCode
	 */
	public String getCatalogItemCode() {
		return catalogItemCode;
	}

	/**
	 * @param catalogItemCode the catalogItemCode to set
	 */
	public void setCatalogItemCode(String catalogItemCode) {
		this.catalogItemCode = catalogItemCode;
	}

	/**
	 * @return the catalogItemName
	 */
	public String getCatalogItemName() {
		return catalogItemName;
	}

	/**
	 * @param catalogItemName the catalogItemName to set
	 */
	public void setCatalogItemName(String catalogItemName) {
		this.catalogItemName = catalogItemName;
	}

	/**
	 * @return the qualStatus
	 */
	public String getQualStatus() {
		return qualStatus;
	}

	/**
	 * @param qualStatus the qualStatus to set
	 */
	public void setQualStatus(String qualStatus) {
		this.qualStatus = qualStatus;
	}

	/**
	 * @return the resProvUnitCode
	 */
	public String getResProvUnitCode() {
		return resProvUnitCode;
	}

	/**
	 * @param resProvUnitCode the resProvUnitCode to set
	 */
	public void setResProvUnitCode(String resProvUnitCode) {
		this.resProvUnitCode = resProvUnitCode;
	}

	/**
	 * @return the jetPort
	 */
	public String getJetPort() {
		return jetPort;
	}

	/**
	 * @param jetPort the jetPort to set
	 */
	public void setJetPort(String jetPort) {
		this.jetPort = jetPort;
	}

	/**
	 * @return the mobEtd
	 */
	public Date getMobEtd() {
		return mobEtd;
	}

	/**
	 * @param mobEtd the mobEtd to set
	 */
	public void setMobEtd(Date mobEtd) {
		this.mobEtd = mobEtd;
	}

	/**
	 * @return the mobEtdTzCode
	 */
	public String getMobEtdTzCode() {
		return mobEtdTzCode;
	}

	/**
	 * @param mobEtdTzCode the mobEtdTzCode to set
	 */
	public void setMobEtdTzCode(String mobEtdTzCode) {
		this.mobEtdTzCode = mobEtdTzCode;
	}

	/**
	 * @return the mobEta
	 */
	public Date getMobEta() {
		return mobEta;
	}

	/**
	 * @param mobEta the mobEta to set
	 */
	public void setMobEta(Date mobEta) {
		this.mobEta = mobEta;
	}

	/**
	 * @return the mobEtaTzCode
	 */
	public String getMobEtaTzCode() {
		return mobEtaTzCode;
	}

	/**
	 * @param mobEtaTzCode the mobEtaTzCode to set
	 */
	public void setMobEtaTzCode(String mobEtaTzCode) {
		this.mobEtaTzCode = mobEtaTzCode;
	}

	/**
	 * @return the mobEte
	 */
	public String getMobEte() {
		return mobEte;
	}

	/**
	 * @param mobEte the mobEte to set
	 */
	public void setMobEte(String mobEte) {
		this.mobEte = mobEte;
	}

	/**
	 * @return the demobEtd
	 */
	public Date getDemobEtd() {
		return demobEtd;
	}

	/**
	 * @param demobEtd the demobEtd to set
	 */
	public void setDemobEtd(Date demobEtd) {
		this.demobEtd = demobEtd;
	}

	/**
	 * @return the demobEtdTzCode
	 */
	public String getDemobEtdTzCode() {
		return demobEtdTzCode;
	}

	/**
	 * @param demobEtdTzCode the demobEtdTzCode to set
	 */
	public void setDemobEtdTzCode(String demobEtdTzCode) {
		this.demobEtdTzCode = demobEtdTzCode;
	}

	/**
	 * @return the demobEte
	 */
	public String getDemobEte() {
		return demobEte;
	}

	/**
	 * @param demobEte the demobEte to set
	 */
	public void setDemobEte(String demobEte) {
		this.demobEte = demobEte;
	}

	/**
	 * @return the vendorOwnedFlag
	 */
	public String getVendorOwnedFlag() {
		return vendorOwnedFlag;
	}

	/**
	 * @param vendorOwnedFlag the vendorOwnedFlag to set
	 */
	public void setVendorOwnedFlag(String vendorOwnedFlag) {
		this.vendorOwnedFlag = vendorOwnedFlag;
	}

	/**
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	/**
	 * @return the contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * @return the reqCatalogName
	 */
	public String getReqCatalogName() {
		return reqCatalogName;
	}

	/**
	 * @param reqCatalogName the reqCatalogName to set
	 */
	public void setReqCatalogName(String reqCatalogName) {
		this.reqCatalogName = reqCatalogName;
	}

	/**
	 * @return the reqCategoryName
	 */
	public String getReqCategoryName() {
		return reqCategoryName;
	}

	/**
	 * @param reqCategoryName the reqCategoryName to set
	 */
	public void setReqCategoryName(String reqCategoryName) {
		this.reqCategoryName = reqCategoryName;
	}

	/**
	 * @return the rossImportProcessResourceErrors
	 */
	public Collection<RossImportProcessResourceError> getRossImportProcessResourceErrors() {
		return rossImportProcessResourceErrors;
	}

	/**
	 * @param rossImportProcessResourceErrors the rossImportProcessResourceErrors to set
	 */
	public void setRossImportProcessResourceErrors(
			Collection<RossImportProcessResourceError> rossImportProcessResourceErrors) {
		this.rossImportProcessResourceErrors = rossImportProcessResourceErrors;
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
		RossXmlFileDataImpl o = (RossXmlFileDataImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the rossAssignment
	 */
	public Short getRossAssignment() {
		return rossAssignment;
	}

	/**
	 * @param rossAssignment the rossAssignment to set
	 */
	public void setRossAssignment(Short rossAssignment) {
		this.rossAssignment = rossAssignment;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


}
