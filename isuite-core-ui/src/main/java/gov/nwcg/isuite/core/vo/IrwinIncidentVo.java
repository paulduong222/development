package gov.nwcg.isuite.core.vo;

import java.util.Date;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class IrwinIncidentVo extends AbstractVo implements PersistableVo {
	private String irwinID;
	private String fireDiscoveryDateTime;
	//private String POOResponsibleUnit;
	private String pooProtectingUnit;
	private String localIncidentIdentifier;
	private String incidentName;
	private String incidentTypeKind;
	private String incidentTypeCategory;
	private String fireCode;
	private String fsJobCode;
	private String fsOverrideCode;
	private String isActive;
	private String recordSource;
	private String createdBySystem;
	private String createdOnDateTime;
	private String modifiedBySystem;
	private String modifiedOnDateTime;
	private Date modifiedOnDateTimeAsDate;
	private Date createdOnDateTimeAsDate;
	private Date fireDiscoveryDateTimeAsDate;
	
	private String uniqueFireIdentifier;
	private String isComplex;
	private String complexParentIrwinID;
	
	private String abcdMisc;
	
	private String isValid;
	
	public IrwinIncidentVo(){
		   
	}

	public String getIrwinID() {
		return irwinID;
	}

	public void setIrwinID(String irwinID) {
		this.irwinID = irwinID;
	}

	public String getFireDiscoveryDateTime() {
		return fireDiscoveryDateTime;
	}

	public void setFireDiscoveryDateTime(String fireDiscoveryDateTime) {
		this.fireDiscoveryDateTime = fireDiscoveryDateTime;
	}

	public String getPooProtectingUnit() {
		return pooProtectingUnit;
	}

	public void setPooProtectingUnit(String pooProtectingUnit) {
		this.pooProtectingUnit = pooProtectingUnit;
	}

	public String getLocalIncidentIdentifier() {
		return localIncidentIdentifier;
	}

	public void setLocalIncidentIdentifier(String localIncidentIdentifier) {
		this.localIncidentIdentifier = localIncidentIdentifier;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentTypeKind() {
		return incidentTypeKind;
	}

	public void setIncidentTypeKind(String incidentTypeKind) {
		this.incidentTypeKind = incidentTypeKind;
	}

	public String getIncidentTypeCategory() {
		return incidentTypeCategory;
	}

	public void setIncidentTypeCategory(String incidentTypeCategory) {
		this.incidentTypeCategory = incidentTypeCategory;
	}

	public String getFireCode() {
		return fireCode;
	}

	public void setFireCode(String fireCode) {
		this.fireCode = fireCode;
	}

	public String getFsJobCode() {
		return fsJobCode;
	}

	public void setFsJobCode(String fsJobCode) {
		this.fsJobCode = fsJobCode;
	}

	public String getFsOverrideCode() {
		return fsOverrideCode;
	}

	public void setFsOverrideCode(String fsOverrideCode) {
		this.fsOverrideCode = fsOverrideCode;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRecordSource() {
		return recordSource;
	}

	public void setRecordSource(String recordSource) {
		this.recordSource = recordSource;
	}

	public String getCreatedBySystem() {
		return createdBySystem;
	}

	public void setCreatedBySystem(String createdBySystem) {
		this.createdBySystem = createdBySystem;
	}

	public String getCreatedOnDateTime() {
		return createdOnDateTime;
	}

	public void setCreatedOnDateTime(String createdOnDateTime) {
		this.createdOnDateTime = createdOnDateTime;
	}

	public String getModifiedBySystem() {
		return modifiedBySystem;
	}

	public void setModifiedBySystem(String modifiedBySystem) {
		this.modifiedBySystem = modifiedBySystem;
	}

	public String getModifiedOnDateTime() {
		return modifiedOnDateTime;
	}

	public void setModifiedOnDateTime(String modifiedOnDateTime) {
		this.modifiedOnDateTime = modifiedOnDateTime;
	}

	public Date getModifiedOnDateTimeAsDate() {
		return modifiedOnDateTimeAsDate;
	}

	public void setModifiedOnDateTimeAsDate(Date modifiedOnDateTimeAsDate) {
		this.modifiedOnDateTimeAsDate = modifiedOnDateTimeAsDate;
	}

	public Date getCreatedOnDateTimeAsDate() {
		return createdOnDateTimeAsDate;
	}

	public void setCreatedOnDateTimeAsDate(Date createdOnDateTimeAsDate) {
		this.createdOnDateTimeAsDate = createdOnDateTimeAsDate;
	}

	public Date getFireDiscoveryDateTimeAsDate() {
		return fireDiscoveryDateTimeAsDate;
	}

	public void setFireDiscoveryDateTimeAsDate(Date fireDiscoveryDateTimeAsDate) {
		this.fireDiscoveryDateTimeAsDate = fireDiscoveryDateTimeAsDate;
	}

	public String getUniqueFireIdentifier() {
		return uniqueFireIdentifier;
	}

	public void setUniqueFireIdentifier(String uniqueFireIdentifier) {
		this.uniqueFireIdentifier = uniqueFireIdentifier;
	}

	public String getIsComplex() {
		return isComplex;
	}

	public void setIsComplex(String isComplex) {
		this.isComplex = isComplex;
	}

	public String getComplexParentIrwinID() {
		return complexParentIrwinID;
	}

	public void setComplexParentIrwinID(String complexParentIrwinID) {
		this.complexParentIrwinID = complexParentIrwinID;
	}
	
	public String getAbcdMisc() {
		return abcdMisc;
	}

	public void setAbcdMisc(String abcdMisc) {
		this.abcdMisc = abcdMisc;
	}
	
	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	
}
