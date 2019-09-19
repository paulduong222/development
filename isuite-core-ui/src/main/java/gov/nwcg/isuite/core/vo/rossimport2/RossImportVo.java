package gov.nwcg.isuite.core.vo.rossimport2;

import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class RossImportVo extends AbstractVo {
	private Boolean isReimport=false;
	
	// Ross File Info
	private Long rossXmlFileId;
	private String rossIncidentName;
	private String rossIncidentNumber;
	private Date rossIncidentStartDate;
	private String rossIncidentStartDateString;
	private String rossIncidentEventType;
	private String rossIncidentId;
	
	// e-ISuite Incident Info
	private Boolean hasIncidentMatch=false;
	private Long eisuiteIncidentId;
	private String eisuiteIncidentName;
	private String eisuiteIncidentNumber;
	
	private AgencyVo incidentAgencyVo;
	
	// conflicts info
	private Boolean hasConflicts=false;
	private Collection<RossConflictVo> rossConflictVos = new ArrayList<RossConflictVo>();
	
	
	private Collection<RossResourceVo> rossResourceVos = new ArrayList<RossResourceVo>();
	private Collection<RossEISuiteResourceVo> eisuiteResourceVos = new ArrayList<RossEISuiteResourceVo>();

	/**
	 * @return the rossIncidentName
	 */
	public String getRossIncidentName() {
		return rossIncidentName;
	}

	/**
	 * @param rossIncidentName the rossIncidentName to set
	 */
	public void setRossIncidentName(String rossIncidentName) {
		this.rossIncidentName = rossIncidentName;
	}

	/**
	 * @return the rossIncidentNumber
	 */
	public String getRossIncidentNumber() {
		return rossIncidentNumber;
	}

	/**
	 * @param rossIncidentNumber the rossIncidentNumber to set
	 */
	public void setRossIncidentNumber(String rossIncidentNumber) {
		this.rossIncidentNumber = rossIncidentNumber;
	}

	/**
	 * @return the rossIncidentStartDate
	 */
	public Date getRossIncidentStartDate() {
		return rossIncidentStartDate;
	}

	/**
	 * @param rossIncidentStartDate the rossIncidentStartDate to set
	 */
	public void setRossIncidentStartDate(Date rossIncidentStartDate) {
		this.rossIncidentStartDate = rossIncidentStartDate;
	}

	/**
	 * @return the rossIncidentStartDateString
	 */
	public String getRossIncidentStartDateString() {
		return rossIncidentStartDateString;
	}

	/**
	 * @param rossIncidentStartDateString the rossIncidentStartDateString to set
	 */
	public void setRossIncidentStartDateString(String rossIncidentStartDateString) {
		this.rossIncidentStartDateString = rossIncidentStartDateString;
	}

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	/**
	 * @return the hasIncidentMatch
	 */
	public Boolean getHasIncidentMatch() {
		return hasIncidentMatch;
	}

	/**
	 * @param hasIncidentMatch the hasIncidentMatch to set
	 */
	public void setHasIncidentMatch(Boolean hasIncidentMatch) {
		this.hasIncidentMatch = hasIncidentMatch;
	}

	/**
	 * @return the eisuiteIncidentId
	 */
	public Long getEisuiteIncidentId() {
		return eisuiteIncidentId;
	}

	/**
	 * @param eisuiteIncidentId the eisuiteIncidentId to set
	 */
	public void setEisuiteIncidentId(Long eisuiteIncidentId) {
		this.eisuiteIncidentId = eisuiteIncidentId;
	}

	/**
	 * @return the eisuiteIncidentName
	 */
	public String getEisuiteIncidentName() {
		return eisuiteIncidentName;
	}

	/**
	 * @param eisuiteIncidentName the eisuiteIncidentName to set
	 */
	public void setEisuiteIncidentName(String eisuiteIncidentName) {
		this.eisuiteIncidentName = eisuiteIncidentName;
	}

	/**
	 * @return the eisuiteIncidentNumber
	 */
	public String getEisuiteIncidentNumber() {
		return eisuiteIncidentNumber;
	}

	/**
	 * @param eisuiteIncidentNumber the eisuiteIncidentNumber to set
	 */
	public void setEisuiteIncidentNumber(String eisuiteIncidentNumber) {
		this.eisuiteIncidentNumber = eisuiteIncidentNumber;
	}

	/**
	 * @return the rossResourceVos
	 */
	public Collection<RossResourceVo> getRossResourceVos() {
		return rossResourceVos;
	}

	/**
	 * @param rossResourceVos the rossResourceVos to set
	 */
	public void setRossResourceVos(Collection<RossResourceVo> rossResourceVos) {
		this.rossResourceVos = rossResourceVos;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	/**
	 * @return the hasConflicts
	 */
	public Boolean getHasConflicts() {
		return hasConflicts;
	}

	/**
	 * @param hasConflicts the hasConflicts to set
	 */
	public void setHasConflicts(Boolean hasConflicts) {
		this.hasConflicts = hasConflicts;
	}

	/**
	 * @return the rossConflictVos
	 */
	public Collection<RossConflictVo> getRossConflictVos() {
		return rossConflictVos;
	}

	/**
	 * @param rossConflictVos the rossConflictVos to set
	 */
	public void setRossConflictVos(Collection<RossConflictVo> rossConflictVos) {
		this.rossConflictVos = rossConflictVos;
	}

	/**
	 * @return the incidentAgencyVo
	 */
	public AgencyVo getIncidentAgencyVo() {
		return incidentAgencyVo;
	}

	/**
	 * @param incidentAgencyVo the incidentAgencyVo to set
	 */
	public void setIncidentAgencyVo(AgencyVo incidentAgencyVo) {
		this.incidentAgencyVo = incidentAgencyVo;
	}

	/**
	 * @return the rossIncidentEventType
	 */
	public String getRossIncidentEventType() {
		return rossIncidentEventType;
	}

	/**
	 * @param rossIncidentEventType the rossIncidentEventType to set
	 */
	public void setRossIncidentEventType(String rossIncidentEventType) {
		this.rossIncidentEventType = rossIncidentEventType;
	}

	/**
	 * @return the eisuiteResourceVos
	 */
	public Collection<RossEISuiteResourceVo> getEisuiteResourceVos() {
		return eisuiteResourceVos;
	}

	/**
	 * @param eisuiteResourceVos the eisuiteResourceVos to set
	 */
	public void setEisuiteResourceVos(
			Collection<RossEISuiteResourceVo> eisuiteResourceVos) {
		this.eisuiteResourceVos = eisuiteResourceVos;
	}

	/**
	 * @return the isReimport
	 */
	public Boolean getIsReimport() {
		return isReimport;
	}

	/**
	 * @param isReimport the isReimport to set
	 */
	public void setIsReimport(Boolean isReimport) {
		this.isReimport = isReimport;
	}

}
