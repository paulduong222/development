package gov.nwcg.isuite.core.irwin;

import java.util.Date;

public class IncidentModel implements Comparable<IncidentModel> {
	private String IrwinID;
	private String FireDiscoveryDateTime;
	//private String POOResponsibleUnit;
	private String POOProtectingUnit;
	private String LocalIncidentIdentifier;
	private String IncidentName;
	private String IncidentTypeKind;
	private String IncidentTypeCategory;
	private String FireCode;
	private String FSJobCode;
	private String FSOverrideCode;
	private String IsActive;
	private String RecordSource;
	private String CreatedBySystem;
	private String CreatedOnDateTime;
	private String ModifiedBySystem;
	private String ModifiedOnDateTime;
	private Date ModifiedOnDateTimeAsDate;
	private Date CreatedOnDateTimeAsDate;
	private Date FireDiscoveryDateTimeAsDate;
	private String UniqueFireIdentifier;
	private String IsComplex;
	private String ComplexParentIrwinID;
	private String ABCDMisc;
	private String IsValid;
	
	public String getABCDMisc() {
		return ABCDMisc;
	}

	public void setABCDMisc(String misc) {
		ABCDMisc = misc;
	}

	public Date getModifiedOnDateTimeAsDate() {
		return ModifiedOnDateTimeAsDate;
	}

	public void setModifiedOnDateTimeAsDate(Date modifiedOnDateTimeAsDate) {
		ModifiedOnDateTimeAsDate = modifiedOnDateTimeAsDate;
	}

	public Date getCreatedOnDateTimeAsDate() {
		return CreatedOnDateTimeAsDate;
	}

	public void setCreatedOnDateTimeAsDate(Date createdOnDateTimeAsDate) {
		CreatedOnDateTimeAsDate = createdOnDateTimeAsDate;
	}

	public Date getFireDiscoveryDateTimeAsDate() {
		return FireDiscoveryDateTimeAsDate;
	}

	public void setFireDiscoveryDateTimeAsDate(Date fireDiscoveryDateTimeAsDate) {
		FireDiscoveryDateTimeAsDate = fireDiscoveryDateTimeAsDate;
	}
    
    @Override
	public int compareTo(IncidentModel im) {
	  return getModifiedOnDateTimeAsDate().compareTo(im.getModifiedOnDateTimeAsDate());
	}

	public String getIrwinID() {
		return IrwinID;
	}
	public void setIrwinID(String irwinID) {
		IrwinID = irwinID;
	}
	public String getFireDiscoveryDateTime() {
		return FireDiscoveryDateTime;
	}
	public void setFireDiscoveryDateTime(String fireDiscoveryDateTime) {
		FireDiscoveryDateTime = fireDiscoveryDateTime;
	}
//	public String getPOOResponsibleUnit() {
//		return POOResponsibleUnit;
//	}
//	public void setPOOResponsibleUnit(String responsibleUnit) {
//		POOResponsibleUnit = responsibleUnit;
//	}
	public String getPOOProtectingUnit() {
		return this.POOProtectingUnit;
	}
	public void setPOOProtectingUnit(String protectingUnit) {
		this.POOProtectingUnit = protectingUnit;
	}
	public String getLocalIncidentIdentifier() {
		return LocalIncidentIdentifier;
	}
	public void setLocalIncidentIdentifier(String localIncidentIdentifier) {
		LocalIncidentIdentifier = localIncidentIdentifier;
	}
	public String getIncidentName() {
		return IncidentName;
	}
	public void setIncidentName(String incidentName) {
		IncidentName = incidentName;
	}
	public String getIncidentTypeKind() {
		return IncidentTypeKind;
	}
	public void setIncidentTypeKind(String incidentTypeKind) {
		IncidentTypeKind = incidentTypeKind;
	}
	public String getIncidentTypeCategory() {
		return IncidentTypeCategory;
	}
	public void setIncidentTypeCategory(String incidentTypeCategory) {
		IncidentTypeCategory = incidentTypeCategory;
	}
	public String getFireCode() {
		return FireCode;
	}
	public void setFireCode(String fireCode) {
		FireCode = fireCode;
	}
	public String getFSJobCode() {
		return FSJobCode;
	}
	public void setFSJobCode(String jobCode) {
		FSJobCode = jobCode;
	}
	public String getFSOverrideCode() {
		return FSOverrideCode;
	}
	public void setFSOverrideCode(String overrideCode) {
		FSOverrideCode = overrideCode;
	}
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	public String getRecordSource() {
		return RecordSource;
	}
	public void setRecordSource(String recordSource) {
		RecordSource = recordSource;
	}
	public String getCreatedBySystem() {
		return CreatedBySystem;
	}
	public void setCreatedBySystem(String createdBySystem) {
		CreatedBySystem = createdBySystem;
	}
	public String getCreatedOnDateTime() {
		return CreatedOnDateTime;
	}
	public void setCreatedOnDateTime(String createdOnDateTime) {
		CreatedOnDateTime = createdOnDateTime;
	}
	public String getModifiedBySystem() {
		return ModifiedBySystem;
	}
	public void setModifiedBySystem(String modifiedBySystem) {
		ModifiedBySystem = modifiedBySystem;
	}
	public String getModifiedOnDateTime() {
		return ModifiedOnDateTime;
	}
	public void setModifiedOnDateTime(String modifiedOnDateTime) {
		ModifiedOnDateTime = modifiedOnDateTime;
	}

	public String getUniqueFireIdentifier() {
		return UniqueFireIdentifier;
	}

	public void setUniqueFireIdentifier(String uniqueFireIdentifier) {
		UniqueFireIdentifier = uniqueFireIdentifier;
	}

	public String getIsComplex() {
		return IsComplex;
	}

	public void setIsComplex(String isComplex) {
		IsComplex = isComplex;
	}

	public String getComplexParentIrwinID() {
		return ComplexParentIrwinID;
	}

	public void setComplexParentIrwinID(String complexParentIrwinID) {
		ComplexParentIrwinID = complexParentIrwinID;
	}
	
	public String getIsValid() {
		return IsValid;
	}
	public void setIsValid(String isValid) {
		IsValid = isValid;
	}
}
