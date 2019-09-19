package gov.nwcg.isuite.core.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface RossXmlFileData extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the rossXmlFile
	 */
	public RossXmlFile getRossXmlFile();

	/**
	 * @param rossXmlFile the rossXmlFile to set
	 */
	public void setRossXmlFile(RossXmlFile rossXmlFile);

	/**
	 * @return the incNumber
	 */
	public String getIncNumber();

	/**
	 * @param incNumber the incNumber to set
	 */
	public void setIncNumber(String incNumber);

	/**
	 * @return the incName
	 */
	public String getIncName();

	/**
	 * @param incName the incName to set
	 */
	public void setIncName(String incName);

	/**
	 * @return the incType
	 */
	public String getIncType();

	/**
	 * @param incType the incType to set
	 */
	public void setIncType(String incType);

	/**
	 * @return the incState
	 */
	public String getIncState();

	/**
	 * @param incState the incState to set
	 */
	public void setIncState(String incState);

	/**
	 * @return the initialDate
	 */
	public Date getInitialDate();

	/**
	 * @param initialDate the initialDate to set
	 */
	public void setInitialDate(Date initialDate);

	/**
	 * @return the incAgencyAbbrev
	 */
	public String getIncAgencyAbbrev();

	/**
	 * @param incAgencyAbbrev the incAgencyAbbrev to set
	 */
	public void setIncAgencyAbbrev(String incAgencyAbbrev);

	/**
	 * @return the incDisOrgUnitCode
	 */
	public String getIncDisOrgUnitCode();

	/**
	 * @param incDisOrgUnitCode the incDisOrgUnitCode to set
	 */
	public void setIncDisOrgUnitCode(String incDisOrgUnitCode);

	/**
	 * @return the complexFlag
	 */
	public String getComplexFlag();

	/**
	 * @param complexFlag the complexFlag to set
	 */
	public void setComplexFlag(String complexFlag);

	/**
	 * @return the complexMemberFlag
	 */
	public String getComplexMemberFlag();

	/**
	 * @param complexMemberFlag the complexMemberFlag to set
	 */
	public void setComplexMemberFlag(String complexMemberFlag);

	/**
	 * @return the complexIncName
	 */
	public String getComplexIncName();

	/**
	 * @param complexIncName the complexIncName to set
	 */
	public void setComplexIncName(String complexIncName);

	/**
	 * @return the complexIncNumber
	 */
	public String getComplexIncNumber();

	/**
	 * @param complexIncNumber the complexIncNumber to set
	 */
	public void setComplexIncNumber(String complexIncNumber);

	/**
	 * @return the mergedIncFlag
	 */
	public String getMergedIncFlag();

	/**
	 * @param mergedIncFlag the mergedIncFlag to set
	 */
	public void setMergedIncFlag(String mergedIncFlag);

	/**
	 * @return the previousIncNumber
	 */
	public String getPreviousIncNumber();

	/**
	 * @param previousIncNumber the previousIncNumber to set
	 */
	public void setPreviousIncNumber(String previousIncNumber);

	/**
	 * @return the transferDate
	 */
	public Date getTransferDate();

	/**
	 * @param transferDate the transferDate to set
	 */
	public void setTransferDate(Date transferDate);

	/**
	 * @return the transferDateGmt
	 */
	public Date getTransferDateGmt();

	/**
	 * @param transferDateGmt the transferDateGmt to set
	 */
	public void setTransferDateGmt(Date transferDateGmt);

	/**
	 * @return the transferDateTzCode
	 */
	public String getTransferDateTzCode();

	/**
	 * @param transferDateTzCode the transferDateTzCode to set
	 */
	public void setTransferDateTzCode(String transferDateTzCode);

	/**
	 * @return the transferFromOrgName
	 */
	public String getTransferFromOrgName();

	/**
	 * @param transferFromOrgName the transferFromOrgName to set
	 */
	public void setTransferFromOrgName(String transferFromOrgName);

	/**
	 * @return the transferFromOrgUnitCode
	 */
	public String getTransferFromOrgUnitCode();

	/**
	 * @param transferFromOrgUnitCode the transferFromOrgUnitCode to set
	 */
	public void setTransferFromOrgUnitCode(String transferFromOrgUnitCode);

	/**
	 * @return the transferToOrgName
	 */
	public String getTransferToOrgName();

	/**
	 * @param transferToOrgName the transferToOrgName to set
	 */
	public void setTransferToOrgName(String transferToOrgName);

	/**
	 * @return the transferToOrgUnitCode
	 */
	public String getTransferToOrgUnitCode();

	/**
	 * @param transferToOrgUnitCode the transferToOrgUnitCode to set
	 */
	public void setTransferToOrgUnitCode(String transferToOrgUnitCode);

	/**
	 * @return the transferredFlag
	 */
	public String getTransferredFlag();

	/**
	 * @param transferredFlag the transferredFlag to set
	 */
	public void setTransferredFlag(String transferredFlag);

	/**
	 * @return the resId
	 */
	public Long getResId();

	/**
	 * @param resId the resId to set
	 */
	public void setResId(Long resId);

	/**
	 * @return the reqId
	 */
	public BigDecimal getReqId();

	/**
	 * @param reqId the reqId to set
	 */
	public void setReqId(BigDecimal reqId);

	/**
	 * @return the rootReqFlag
	 */
	public String getRootReqFlag();

	/**
	 * @param rootReqFlag the rootReqFlag to set
	 */
	public void setRootReqFlag(String rootReqFlag);

	/**
	 * @return the reqNumberPrefix
	 */
	public String getReqNumberPrefix();

	/**
	 * @param reqNumberPrefix the reqNumberPrefix to set
	 */
	public void setReqNumberPrefix(String reqNumberPrefix);

	/**
	 * @return the reqNumber
	 */
	public String getReqNumber();

	/**
	 * @param reqNumber the reqNumber to set
	 */
	public void setReqNumber(String reqNumber);

	/**
	 * @return the resName
	 */
	public String getResName();

	/**
	 * @param resName the resName to set
	 */
	public void setResName(String resName);

	/**
	 * @return the assignmentName
	 */
	public String getAssignmentName();

	/**
	 * @param assignmentName the assignmentName to set
	 */
	public void setAssignmentName(String assignmentName);

	/**
	 * @return the resProvAgencyAbbrev
	 */
	public String getResProvAgencyAbbrev();

	/**
	 * @param resProvAgencyAbbrev the resProvAgencyAbbrev to set
	 */
	public void setResProvAgencyAbbrev(String resProvAgencyAbbrev);

	/**
	 * @return the filledCatalogItemCode
	 */
	public String getFilledCatalogItemCode();

	/**
	 * @param filledCatalogItemCode the filledCatalogItemCode to set
	 */
	public void setFilledCatalogItemCode(String filledCatalogItemCode);

	/**
	 * @return the filledCatalogItemName
	 */
	public String getFilledCatalogItemName();

	/**
	 * @param filledCatalogItemName the filledCatalogItemName to set
	 */
	public void setFilledCatalogItemName(String filledCatalogItemName);

	/**
	 * @return the employmentClass
	 */
	public String getEmploymentClass();

	/**
	 * @param employmentClass the employmentClass to set
	 */
	public void setEmploymentClass(String employmentClass);

	/**
	 * @return the catalogItemCode
	 */
	public String getCatalogItemCode();

	/**
	 * @param catalogItemCode the catalogItemCode to set
	 */
	public void setCatalogItemCode(String catalogItemCode);

	/**
	 * @return the catalogItemName
	 */
	public String getCatalogItemName();

	/**
	 * @param catalogItemName the catalogItemName to set
	 */
	public void setCatalogItemName(String catalogItemName);

	/**
	 * @return the qualStatus
	 */
	public String getQualStatus();

	/**
	 * @param qualStatus the qualStatus to set
	 */
	public void setQualStatus(String qualStatus);

	/**
	 * @return the resProvUnitCode
	 */
	public String getResProvUnitCode();

	/**
	 * @param resProvUnitCode the resProvUnitCode to set
	 */
	public void setResProvUnitCode(String resProvUnitCode);

	/**
	 * @return the jetPort
	 */
	public String getJetPort();

	/**
	 * @param jetPort the jetPort to set
	 */
	public void setJetPort(String jetPort);

	/**
	 * @return the mobEtd
	 */
	public Date getMobEtd();

	/**
	 * @param mobEtd the mobEtd to set
	 */
	public void setMobEtd(Date mobEtd);

	/**
	 * @return the mobEtdTzCode
	 */
	public String getMobEtdTzCode();

	/**
	 * @param mobEtdTzCode the mobEtdTzCode to set
	 */
	public void setMobEtdTzCode(String mobEtdTzCode);

	/**
	 * @return the mobEta
	 */
	public Date getMobEta();

	/**
	 * @param mobEta the mobEta to set
	 */
	public void setMobEta(Date mobEta);

	/**
	 * @return the mobEtaTzCode
	 */
	public String getMobEtaTzCode();

	/**
	 * @param mobEtaTzCode the mobEtaTzCode to set
	 */
	public void setMobEtaTzCode(String mobEtaTzCode);

	/**
	 * @return the mobEte
	 */
	public String getMobEte();

	/**
	 * @param mobEte the mobEte to set
	 */
	public void setMobEte(String mobEte);

	/**
	 * @return the demobEtd
	 */
	public Date getDemobEtd();

	/**
	 * @param demobEtd the demobEtd to set
	 */
	public void setDemobEtd(Date demobEtd);

	/**
	 * @return the demobEtdTzCode
	 */
	public String getDemobEtdTzCode();

	/**
	 * @param demobEtdTzCode the demobEtdTzCode to set
	 */
	public void setDemobEtdTzCode(String demobEtdTzCode);

	/**
	 * @return the demobEte
	 */
	public String getDemobEte();

	/**
	 * @param demobEte the demobEte to set
	 */
	public void setDemobEte(String demobEte);

	/**
	 * @return the vendorOwnedFlag
	 */
	public String getVendorOwnedFlag();

	/**
	 * @param vendorOwnedFlag the vendorOwnedFlag to set
	 */
	public void setVendorOwnedFlag(String vendorOwnedFlag);

	/**
	 * @return the vendorName
	 */
	public String getVendorName();

	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName);

	/**
	 * @return the contractType
	 */
	public String getContractType();

	/**
	 * @param contractType the contractType to set
	 */
	public void setContractType(String contractType);

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber();

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber);

	/**
	 * @return the reqCatalogName
	 */
	public String getReqCatalogName();

	/**
	 * @param reqCatalogName the reqCatalogName to set
	 */
	public void setReqCatalogName(String reqCatalogName);

	/**
	 * @return the reqCategoryName
	 */
	public String getReqCategoryName();

	/**
	 * @param reqCategoryName the reqCategoryName to set
	 */
	public void setReqCategoryName(String reqCategoryName);

	/**
	 * @return the rossImportProcessResourceError
	 */
	public Collection<RossImportProcessResourceError> getRossImportProcessResourceErrors();
	
	/**
	 * @param rossImportProcessResourceError the rossImportProcessResourceError to set
	 */
	public void setRossImportProcessResourceErrors(Collection<RossImportProcessResourceError> rossImportProcessResourceErrors);

	/**
	 * @return the rossAssignment
	 */
	public Short getRossAssignment();

	/**
	 * @param rossAssignment the rossAssignment to set
	 */
	public void setRossAssignment(Short rossAssignment);

	/**
	 * @return the importStatus
	 */
	public String getImportStatus();

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus);

	public String getLastName() ;
	
	public void setLastName(String lastName);

	public String getFirstName() ;

	public void setFirstName(String firstName);

	public String getMiddleName() ;

	public void setMiddleName(String middleName);
	
}
