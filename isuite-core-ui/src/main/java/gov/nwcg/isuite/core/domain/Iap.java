package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public interface Iap extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @param nDate the nDate to set
	 */
	public void setNDate(Date nDate);

	/**
	 * @return the nDate
	 */
	public Date getNDate();

	/**
	 * @param nDay the nDay to set
	 */
	public void setNDay(String nDay);

	/**
	 * @return the nDay
	 */
	public String getNDay();

	/**
	 * @param dFromDate the dFromDate to set
	 */
	public void setDFromDate(Date dFromDate);

	/**
	 * @return the dFromDate
	 */
	public Date getDFromDate();

	/**
	 * @param dToDate the dToDate to set
	 */
	public void setDToDate(Date dToDate);

	/**
	 * @return the dToDate
	 */
	public Date getDToDate();

	/**
	 * @param b202PreparedBy the b202PreparedBy to set
	 */
	public void setB202PreparedBy(String b202PreparedBy);

	/**
	 * @return the b202PreparedBy
	 */
	public String getB202PreparedBy();

	/**
	 * @param b202ApprovedBy the b202ApprovedBy to set
	 */
	public void setB202ApprovedBy(String b202ApprovedBy);

	/**
	 * @return the b202ApprovedBy
	 */
	public String getB202ApprovedBy();

	/**
	 * @param n202B2PreparedDate the n202B2PreparedDate to set
	 */
	public void setN202B2PreparedDate(Date n202B2PreparedDate);

	/**
	 * @return the n202B2PreparedDate
	 */
	public Date getN202B2PreparedDate();

	/**
	 * @param n202B5GeneralCntlObj the n202B5GeneralCntlObj to set
	 */
	public void setN202B5GeneralCntlObj(String n202B5GeneralCntlObj);

	/**
	 * @return the n202B5GeneralCntlObj
	 */
	public String getN202B5GeneralCntlObj();

	/**
	 * @param n202B6WeatherFcstPeriod the n202B6WeatherFcstPeriod to set
	 */
	public void setN202B6WeatherFcstPeriod(String n202B6WeatherFcstPeriod);

	/**
	 * @return the n202B6WeatherFcstPeriod
	 */
	public String getN202B6WeatherFcstPeriod();

	/**
	 * @param n202B7GeneralSafetyMsg the n202B7GeneralSafetyMsg to set
	 */
	public void setN202B7GeneralSafetyMsg(String n202B7GeneralSafetyMsg);

	/**
	 * @return the n202B7GeneralSafetyMsg
	 */
	public String getN202B7GeneralSafetyMsg();

	/**
	 * @param d202B3Objectives the d202B3Objectives to set
	 */
	public void setD202B3Objectives(String d202B3Objectives);

	/**
	 * @return the d202B3Objectives
	 */
	public String getD202B3Objectives();

	/**
	 * @param d202B4CommandEmphasis the d202B4CommandEmphasis to set
	 */
	public void setD202B4CommandEmphasis(String d202B4CommandEmphasis);

	/**
	 * @return the d202B4CommandEmphasis
	 */
	public String getD202B4CommandEmphasis();

	/**
	 * @param d202B4GenSitAwareness the d202B4GenSitAwareness to set
	 */
	public void setD202B4GenSitAwareness(String d202B4GenSitAwareness);

	/**
	 * @return the d202B4GenSitAwareness
	 */
	public String getD202B4GenSitAwareness();

	/**
	 * @param d202B5SiteSafetyPlanRqrd the d202B5SiteSafetyPlanRqrd to set
	 */
	public void setD202B5SiteSafetyPlanRqrd(StringBooleanEnum d202B5SiteSafetyPlanRqrd);

	/**
	 * @return the d202B5SiteSafetyPlanRqrd
	 */
	public StringBooleanEnum getD202B5SiteSafetyPlanRqrd();

	/**
	 * @param d202B5SiteSafetyPlanLoc the d202B5SiteSafetyPlanLoc to set
	 */
	public void setD202B5SiteSafetyPlanLoc(String d202B5SiteSafetyPlanLoc);

	/**
	 * @return the d202B5SiteSafetyPlanLoc
	 */
	public String getD202B5SiteSafetyPlanLoc();

	/**
	 * @param n203B2PreparedDate the n203B2PreparedDate to set
	 */
	public void setN203B2PreparedDate(Date n203B2PreparedDate);

	/**
	 * @return the n203B2PreparedDate
	 */
	public Date getN203B2PreparedDate();

	/**
	 * @param b203PreparedBy the b203PreparedBy to set
	 */
	public void setB203PreparedBy(String b203PreparedBy);

	/**
	 * @return the b203PreparedBy
	 */
	public String getB203PreparedBy();

	/**
	 * @param b206PreparedDate the b206PreparedDate to set
	 */
	public void setB206PreparedDate(Date b206PreparedDate);

	/**
	 * @return the b206PreparedDate
	 */
	public Date getB206PreparedDate();

	/**
	 * @param b206MedicalEmergencyProc the b206MedicalEmergencyProc to set
	 */
	public void setB206MedicalEmergencyProc(String b206MedicalEmergencyProc);

	/**
	 * @return the b206MedicalEmergencyProc
	 */
	public String getB206MedicalEmergencyProc();

	/**
	 * @param b206PreparedBy the b206PreparedBy to set
	 */
	public void setB206PreparedBy(String b206PreparedBy);

	/**
	 * @return the b206PreparedBy
	 */
	public String getB206PreparedBy();

	/**
	 * @param b206ApprovedBy the b206ApprovedBy to set
	 */
	public void setB206ApprovedBy(String b206ApprovedBy);

	/**
	 * @return the b206ApprovedBy
	 */
	public String getB206ApprovedBy();

	/**
	 * @param d205B5SpecialInstruction the d205B5SpecialInstruction to set
	 */
	public void setD205B5SpecialInstruction(String d205B5SpecialInstruction);

	/**
	 * @return the d205B5SpecialInstruction
	 */
	public String getD205B5SpecialInstruction();

	/**
	 * @param d205B6PreparedBy the d205B6PreparedBy to set
	 */
	public void setD205B6PreparedBy(String d205B6PreparedBy);

	/**
	 * @return the d205B6PreparedBy
	 */
	public String getD205B6PreparedBy();

	/**
	 * @param b220PreparedBy the b220PreparedBy to set
	 */
	public void setB220PreparedBy(String b220PreparedBy);

	/**
	 * @return the b220PreparedBy
	 */
	public String getB220PreparedBy();

	/**
	 * @param b220PreparedDate the b220PreparedDate to set
	 */
	public void setB220PreparedDate(Date b220PreparedDate);

	/**
	 * @return the b220PreparedDate
	 */
	public Date getB220PreparedDate();

	/**
	 * @param b220Sunrise the b220Sunrise to set
	 */
	public void setB220Sunrise(Date b220Sunrise);

	/**
	 * @return the b220Sunrise
	 */
	public Date getB220Sunrise();

	/**
	 * @param b220Sunset the b220Sunset to set
	 */
	public void setB220Sunset(Date b220Sunset);

	/**
	 * @return the b220Sunset
	 */
	public Date getB220Sunset();

	/**
	 * @param n220B3Remarks the n220B3Remarks to set
	 */
	public void setN220B3Remarks(String n220B3Remarks);

	/**
	 * @return the n220B3Remarks
	 */
	public String getN220B3Remarks();

	/**
	 * @param n220B4MedivacAircraft the n220B4MedivacAircraft to set
	 */
	public void setN220B4MedivacAircraft(String n220B4MedivacAircraft);

	/**
	 * @return the n220B4MedivacAircraft
	 */
	public String getN220B4MedivacAircraft();

	/**
	 * @param n220B5Tfr the n220B5Tfr to set
	 */
	public void setN220B5Tfr(String n220B5Tfr);

	/**
	 * @return the n220B5Tfr
	 */
	public String getN220B5Tfr();

	/**
	 * @param d220B5ReadyAlertAircraft the d220B5ReadyAlertAircraft to set
	 */
	public void setD220B5ReadyAlertAircraft(String d220B5ReadyAlertAircraft);

	/**
	 * @return the d220B5ReadyAlertAircraft
	 */
	public String getD220B5ReadyAlertAircraft();

	/**
	 * @param d220B5Medivac the d220B5Medivac to set
	 */
	public void setD220B5Medivac(String d220B5Medivac);

	/**
	 * @return the d220B5Medivac
	 */
	public String getD220B5Medivac();

	/**
	 * @param d220B5NewIncident the d220B5NewIncident to set
	 */
	public void setD220B5NewIncident(String d220B5NewIncident);

	/**
	 * @return the d220B5NewIncident
	 */
	public String getD220B5NewIncident();

	/**
	 * @param d220B6TfrNbr the d220B6TfrNbr to set
	 */
	public void setD220B6TfrNbr(String d220B6TfrNbr);

	/**
	 * @return the d220B6TfrNbr
	 */
	public String getD220B6TfrNbr();

	/**
	 * @param d220B6Altitude the d220B6Altitude to set
	 */
	public void setD220B6Altitude(String d220B6Altitude);

	/**
	 * @return the d220B6Altitude
	 */
	public String getD220B6Altitude();

	/**
	 * @param d220B6CentralPoint the d220B6CentralPoint to set
	 */
	public void setD220B6CentralPoint(String d220B6CentralPoint);

	/**
	 * @return the d220B6CentralPoint
	 */
	public String getD220B6CentralPoint();

	/**
	 * @param d220B4Remarks the d220B4Remarks to set
	 */
	public void setD220B4Remarks(String d220B4Remarks);

	/**
	 * @return the d220B4Remarks
	 */
	public String getD220B4Remarks();

	/**
	 * @param attachForm203 the attachForm203 to set
	 */
	public void setAttachForm203(StringBooleanEnum attachForm203);

	/**
	 * @return the attachForm203
	 */
	public StringBooleanEnum getAttachForm203();

	/**
	 * @param attachForm204 the attachForm204 to set
	 */
	public void setAttachForm204(StringBooleanEnum attachForm204);

	/**
	 * @return the attachForm204
	 */
	public StringBooleanEnum getAttachForm204();

	/**
	 * @param attachForm205 the attachForm205 to set
	 */
	public void setAttachForm205(StringBooleanEnum attachForm205);

	/**
	 * @return the attachForm205
	 */
	public StringBooleanEnum getAttachForm205();

	/**
	 * @param attachForm205a the attachForm205a to set
	 */
	public void setAttachForm205a(StringBooleanEnum attachForm205a);

	/**
	 * @return the attachForm205a
	 */
	public StringBooleanEnum getAttachForm205a();

	/**
	 * @param attachForm206 the attachForm206 to set
	 */
	public void setAttachForm206(StringBooleanEnum attachForm206);

	/**
	 * @return the attachForm206
	 */
	public StringBooleanEnum getAttachForm206();

	/**
	 * @param attachForm207 the attachForm207 to set
	 */
	public void setAttachForm207(StringBooleanEnum attachForm207);

	/**
	 * @return the attachForm207
	 */
	public StringBooleanEnum getAttachForm207();

	/**
	 * @param attachForm208 the attachForm208 to set
	 */
	public void setAttachForm208(StringBooleanEnum attachForm208);

	/**
	 * @return the attachForm208
	 */
	public StringBooleanEnum getAttachForm208();

	/**
	 * @param attachForm220 the attachForm220 to set
	 */
	public void setAttachForm220(StringBooleanEnum attachForm220);

	/**
	 * @return the attachForm220
	 */
	public StringBooleanEnum getAttachForm220();

	/**
	 * @param attachIncidentMap the attachIncidentMap to set
	 */
	public void setAttachIncidentMap(StringBooleanEnum attachIncidentMap);

	/**
	 * @return the attachIncidentMap
	 */
	public StringBooleanEnum getAttachIncidentMap();

	/**
	 * @param attachSafetyMsg the attachSafetyMsg to set
	 */
	public void setAttachSafetyMsg(StringBooleanEnum attachSafetyMsg);

	/**
	 * @return the attachSafetyMsg
	 */
	public StringBooleanEnum getAttachSafetyMsg();

	/**
	 * @param attachTrafficPlan the attachTrafficPlan to set
	 */
	public void setAttachTrafficPlan(StringBooleanEnum attachTrafficPlan);

	/**
	 * @return the attachTrafficPlan
	 */
	public StringBooleanEnum getAttachTrafficPlan();

	/**
	 * @param attachWeatherForecast the attachWeatherForecast to set
	 */
	public void setAttachWeatherForecast(StringBooleanEnum attachWeatherForecast);

	/**
	 * @return the attachWeatherForecast
	 */
	public StringBooleanEnum getAttachWeatherForecast();

	/**
	 * @param form202Type the form202Type to set
	 */
	public void setForm202Type(StringBooleanEnum form202Type);

	/**
	 * @return the form202Type
	 */
	public StringBooleanEnum getForm202Type();

	/**
	 * @param form203Type the form203Type to set
	 */
	public void setForm203Type(StringBooleanEnum form203Type);

	/**
	 * @return the form203Type
	 */
	public StringBooleanEnum getForm203Type();

	/**
	 * @param form204Type the form204Type to set
	 */
	public void setForm204Type(StringBooleanEnum form204Type);

	/**
	 * @return the form204Type
	 */
	public StringBooleanEnum getForm204Type();

	/**
	 * @param form205Type the form205Type to set
	 */
	public void setForm205Type(StringBooleanEnum form205Type);

	/**
	 * @return the form205Type
	 */
	public StringBooleanEnum getForm205Type();

	/**
	 * @param form206Type the form206Type to set
	 */
	public void setForm206Type(StringBooleanEnum form206Type);

	/**
	 * @return the form206Type
	 */
	public StringBooleanEnum getForm206Type();

	/**
	 * @param form220Type the form220Type to set
	 */
	public void setForm220Type(StringBooleanEnum form220Type);

	/**
	 * @return the form220Type
	 */
	public StringBooleanEnum getForm220Type();

	/**
	 * @param iapMedicalAids the iapMedicalAids to set
	 */
	public void setIapMedicalAids(Collection<IapMedicalAid> iapMedicalAids);

	/**
	 * @return the iapMedicalAids
	 */
	public Collection<IapMedicalAid> getIapMedicalAids();
	
	/**
	 * @param iapBranches the iapBranches to set
	 */
	public void setIapBranches(Collection<IapBranch> iapBranches);

	/**
	 * @return the iapBranches
	 */
	public Collection<IapBranch> getIapBranches();
	
	/**
	 * @param iapFrequencies the iapFrequencies to set
	 */
	public void setIapFrequencies(Collection<IapFrequency> iapFrequencies);

	/**
	 * @return the iapFrequencies
	 */
	public Collection<IapFrequency> getIapFrequencies();
	
	/**
	 * @param iapAircraftFrequencies the iapAircraftFrequencies to set
	 */
	public void setIapAircraftFrequencies(Collection<IapAircraftFrequency> iapAircraftFrequencies);
	
	/**
	 * @return the iapAircraftFrequencies
	 */
	public Collection<IapAircraftFrequency> getIapAircraftFrequencies();
	
	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels);

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels();
	
	/**
	 * @param iapAircraftTasks the iapAircraftTasks to set
	 */
	public void setIapAircraftTasks(Collection<IapAircraftTask> iapAircraftTasks);

	/**
	 * @return the iapAircraftTasks
	 */
	public Collection<IapAircraftTask> getIapAircraftTasks();
	
	/**
	 * @param iapAttachments the iapAttachments to set
	 */
	public void setIapAttachments(Collection<IapAttachment> iapAttachments);

	/**
	 * @return the iapAttachments
	 */
	public Collection<IapAttachment> getIapAttachments();
	
	/**
	 * @param iapAircrafts the iapAircrafts to set
	 */
	public void setIapAircrafts(Collection<IapAircraft> iapAircrafts);

	/**
	 * @return the iapAircrafts
	 */
	public Collection<IapAircraft> getIapAircrafts();
	
	/**
	 * @param iapHospitals the iapHospitals to set
	 */
	public void setIapHospitals(Collection<IapHospital> iapHospitals);

	/**
	 * @return the iapHospitals
	 */
	public Collection<IapHospital> getIapHospitals();

	/**
	 * @return the isPlanLocked
	 */
	public StringBooleanEnum getIsPlanLocked() ;

	/**
	 * @param isPlanLocked the isPlanLocked to set
	 */
	public void setIsPlanLocked(StringBooleanEnum isPlanLocked);

	/**
	 * @return the isForm203Locked
	 */
	public StringBooleanEnum getIsForm203Locked() ;

	/**
	 * @param isForm203Locked the isForm203Locked to set
	 */
	public void setIsForm203Locked(StringBooleanEnum isForm203Locked);

	/**
	 * @return the isForm202Locked
	 */
	public StringBooleanEnum getIsForm202Locked() ;

	/**
	 * @param isForm202Locked the isForm202Locked to set
	 */
	public void setIsForm202Locked(StringBooleanEnum isForm202Locked);

	/**
	 * @return the isForm205Locked
	 */
	public StringBooleanEnum getIsForm205Locked();

	/**
	 * @param isForm205Locked the isForm205Locked to set
	 */
	public void setIsForm205Locked(StringBooleanEnum isForm205Locked) ;

	/**
	 * @return the isForm206Locked
	 */
	public StringBooleanEnum getIsForm206Locked() ;

	/**
	 * @param isForm206Locked the isForm206Locked to set
	 */
	public void setIsForm206Locked(StringBooleanEnum isForm206Locked);

	/**
	 * @return the isForm220Locked
	 */
	public StringBooleanEnum getIsForm220Locked();

	/**
	 * @param isForm220Locked the isForm220Locked to set
	 */
	public void setIsForm220Locked(StringBooleanEnum isForm220Locked);

	/**
	 * @return the b205PreparedDate
	 */
	public Date getB205PreparedDate();

	/**
	 * @param preparedDate the b205PreparedDate to set
	 */
	public void setB205PreparedDate(Date preparedDate);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);

	/**
	 * @return the incidentName
	 */
	public String getIncidentName();

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * @return the operationPeriod
	 */
	public String getOperationPeriod();

	/**
	 * @param operationPeriod the operationPeriod to set
	 */
	public void setOperationPeriod(String operationPeriod);

	/**
	 * @return the iapPlan
	 */
	public Iap getIapPlan();

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(Iap iapPlan);

	/**
	 * @return the iapForms
	 */
	public Collection<Iap> getIapForms();

	/**
	 * @param iapForms the iapForms to set
	 */
	public void setIapForms(Collection<Iap> iapForms);

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId();

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId);
	
}
