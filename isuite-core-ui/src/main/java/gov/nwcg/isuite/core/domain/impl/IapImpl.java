package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapAircraftFrequency;
import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


/**
 * Iap entity. 
 */
@Entity
@Table(name = "isw_iap")
@SequenceGenerator(name="SEQ_IAP", sequenceName="SEQ_IAP")
public class IapImpl extends PersistableImpl implements Iap {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP")
	private Long id = 0L;
	
	@Column(name = "N_DATE")
	private Date NDate;
	
	@Column(name = "N_DAY", length = 10)
	private String NDay;
	
	@Column(name = "D_FROM_DATE")
	private Date DFromDate;

	@Column(name = "D_TO_DATE")
	private Date DToDate;
	
	@Column(name = "B202_PREPARED_BY", length = 50)
	private String b202PreparedBy;
	
	@Column(name = "B202_APPROVED_BY", length = 50)
	private String b202ApprovedBy;
	
	@Column(name = "N202_B2_PREPARED_DATE")
	private Date n202B2PreparedDate;
	
	@Column(name = "N202_B5_GENERAL_CNTL_OBJ", length = 50)
	private String n202B5GeneralCntlObj;
	
	@Column(name = "N202_B6_WEATHER_FCST_PERIOD", length = 50)
	private String n202B6WeatherFcstPeriod;
	
	@Column(name = "N202_B7_GENERAL_SAFETY_MSG", length = 4000)
	private String n202B7GeneralSafetyMsg;
	
	@Column(name = "D202_B3_OBJECTIVES", length = 4000)
	private String d202B3Objectives;
	
	@Column(name = "D202_B4_COMMAND_EMPHASIS", length = 4000)
	private String d202B4CommandEmphasis;
	
	@Column(name = "D202_B4_GEN_SIT_AWARENESS", length = 4000)
	private String d202B4GenSitAwareness;
	
	@Column(name = "D202_B5_SITE_SAFETY_PLAN_RQRD")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum d202B5SiteSafetyPlanRqrd;
	
	@Column(name = "D202_B5_SITE_SAFETY_PLAN_LOC", length = 50)
	private String d202B5SiteSafetyPlanLoc;
	
	@Column(name = "N203_B2_PREPARED_DATE")
	private Date n203B2PreparedDate;
	
	@Column(name = "B203_PREPARED_BY", length = 50)
	private String b203PreparedBy;
	
	@Column(name = "B205_PREPARED_DATE")
	private Date b205PreparedDate;
	
	@Column(name = "B206_PREPARED_DATE")
	private Date b206PreparedDate;
	
	@Column(name = "B206_MEDICAL_EMERGENCY_PROC", length = 4000)
	private String b206MedicalEmergencyProc;
	
	@Column(name = "B206_PREPARED_BY", length = 50)
	private String b206PreparedBy;
	
	@Column(name = "B206_APPROVED_BY", length = 50)
	private String b206ApprovedBy;
	
	@Column(name = "D205_B5_SPECIAL_INSTRUCTION", length = 4000)
	private String d205B5SpecialInstruction;
	
	@Column(name = "D205_B6_PREPARED_BY", length = 50)
	private String d205B6PreparedBy;
	
	@Column(name = "B220_PREPARED_BY", length = 50)
	private String b220PreparedBy;
	
	@Column(name = "B220_PREPARED_DATE")
	private Date b220PreparedDate;
	
	@Column(name = "B220_SUNRISE")
	private Date b220Sunrise;
	
	@Column(name = "B220_SUNSET")
	private Date b220Sunset;
	
	@Column(name = "N220_B3_REMARKS", length = 4000)
	private String n220B3Remarks;
	
	@Column(name = "N220_B4_MEDIVAC_AIRCRAFT", length = 4000)
	private String n220B4MedivacAircraft;
	
	@Column(name = "N220_B5_TFR", length = 4000)
	private String n220B5Tfr;
	
	@Column(name = "D220_B5_READY_ALERT_AIRCRAFT", length = 200)
	private String d220B5ReadyAlertAircraft;
	
	@Column(name = "D220_B5_MEDIVAC", length = 200)
	private String d220B5Medivac;
	
	@Column(name = "D220_B5_NEW_INCIDENT", length = 200)
	private String d220B5NewIncident;
	
	@Column(name = "D220_B6_TFR_NBR", length = 200)
	private String d220B6TfrNbr;
	
	@Column(name = "D220_B6_ALTITUDE", length = 200)
	private String d220B6Altitude;
	
	@Column(name = "D220_B6_CENTRAL_POINT", length = 200)
	private String d220B6CentralPoint;
	
	@Column(name = "D220_B4_REMARKS", length = 4000)
	private String d220B4Remarks;
	
	@Column(name = "ATTACH_FORM_203")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm203;
	
	@Column(name = "ATTACH_FORM_204")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm204;
	
	@Column(name = "ATTACH_FORM_205")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm205;
	
	@Column(name = "ATTACH_FORM_205a")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm205a;
	
	@Column(name = "ATTACH_FORM_206")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm206;
	
	@Column(name = "ATTACH_FORM_207")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm207;
	
	@Column(name = "ATTACH_FORM_208")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm208;
	
	@Column(name = "ATTACH_FORM_220")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachForm220;
	
	@Column(name = "ATTACH_INCIDENT_MAP")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachIncidentMap;
	
	@Column(name = "ATTACH_SAFETY_MSG")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachSafetyMsg;
	
	@Column(name = "ATTACH_TRAFFICE_PLAN")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachTrafficPlan;
	
	@Column(name = "ATTACH_WEATHER_FORECAST")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum attachWeatherForecast;
	
	@Column(name = "FORM_202_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form202Type;
	
	@Column(name = "FORM_203_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form203Type;
	
	@Column(name = "FORM_204_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form204Type;
	
	@Column(name = "FORM_205_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form205Type;
	
	@Column(name = "FORM_206_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form206Type;
	
	@Column(name = "FORM_220_TYPE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum form220Type;

	@Column(name = "IS_PLAN_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isPlanLocked;
	
	@Column(name = "IS_FORM_203_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm203Locked;

	@Column(name = "IS_FORM_202_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm202Locked;

	@Column(name = "IS_FORM_205_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm205Locked;
	
	@Column(name = "IS_FORM_206_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm206Locked;

	@Column(name = "IS_FORM_220_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm220Locked;
	
	@OneToMany(targetEntity=IapMedicalAidImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<IapMedicalAid> iapMedicalAids;
	
	@OneToMany(targetEntity=IapBranchImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapBranch> iapBranches;
	
	@OneToMany(targetEntity=IapFrequencyImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")	
	private Collection<IapFrequency> iapFrequencies;
	
	@OneToMany(targetEntity=IapAircraftFrequencyImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapAircraftFrequency> iapAircraftFrequencies;
	
	@OneToMany(targetEntity=IapPersonnelImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
    //@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<IapPersonnel> iapPersonnels;
	
	@OneToMany(targetEntity=IapAircraftTaskImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapAircraftTask> iapAircraftTasks;
	
	@OneToMany(targetEntity=IapAttachmentImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapAttachment> iapAttachments;

	@OneToMany(targetEntity=IapAircraftImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapAircraft> iapAircrafts;

	@OneToMany(targetEntity=IapHospitalImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iap")
	private Collection<IapHospital> iapHospitals;

	@Column(name = "INCIDENT_NAME", length = 200)
	private String incidentName;

	@Column(name = "OPERATION_PERIOD", length = 200)
	private String operationPeriod;
	
	@Column(name = "INCIDENT_ID", unique=false, nullable = true)
	private Long incidentId;	

	@Column(name = "INCIDENT_GROUP_ID", unique=false, nullable = true)
	private Long incidentGroupId;	
	
	@ManyToOne(targetEntity=IapImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID")
	private Iap iapPlan;
	
	@OneToMany(targetEntity=IapImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapPlan")
	private Collection<Iap> iapForms;
	
	@Column(name = "IAP_PLAN_ID", updatable=false, insertable=false, unique=false, nullable = true)
	private Long iapPlanId;

	
	/** 
	 * Default constructor 
	 */
	public IapImpl() {
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
	 * @param nDate the nDate to set
	 */
	public void setNDate(Date nDate) {
		NDate = nDate;
	}

	/**
	 * @return the nDate
	 */
	public Date getNDate() {
		return NDate;
	}

	/**
	 * @param nDay the nDay to set
	 */
	public void setNDay(String nDay) {
		NDay = nDay;
	}

	/**
	 * @return the nDay
	 */
	public String getNDay() {
		return NDay;
	}

	/**
	 * @param dFromDate the dFromDate to set
	 */
	public void setDFromDate(Date dFromDate) {
		DFromDate = dFromDate;
	}

	/**
	 * @return the dFromDate
	 */
	public Date getDFromDate() {
		return DFromDate;
	}

	/**
	 * @param dToDate the dToDate to set
	 */
	public void setDToDate(Date dToDate) {
		DToDate = dToDate;
	}

	/**
	 * @return the dToDate
	 */
	public Date getDToDate() {
		return DToDate;
	}

	/**
	 * @param b202PreparedBy the b202PreparedBy to set
	 */
	public void setB202PreparedBy(String b202PreparedBy) {
		this.b202PreparedBy = b202PreparedBy;
	}

	/**
	 * @return the b202PreparedBy
	 */
	public String getB202PreparedBy() {
		return b202PreparedBy;
	}

	/**
	 * @param b202ApprovedBy the b202ApprovedBy to set
	 */
	public void setB202ApprovedBy(String b202ApprovedBy) {
		this.b202ApprovedBy = b202ApprovedBy;
	}

	/**
	 * @return the b202ApprovedBy
	 */
	public String getB202ApprovedBy() {
		return b202ApprovedBy;
	}

	/**
	 * @param n202B2PreparedDate the n202B2PreparedDate to set
	 */
	public void setN202B2PreparedDate(Date n202B2PreparedDate) {
		this.n202B2PreparedDate = n202B2PreparedDate;
	}

	/**
	 * @return the n202B2PreparedDate
	 */
	public Date getN202B2PreparedDate() {
		return n202B2PreparedDate;
	}

	/**
	 * @param n202B5GeneralCntlObj the n202B5GeneralCntlObj to set
	 */
	public void setN202B5GeneralCntlObj(String n202B5GeneralCntlObj) {
		this.n202B5GeneralCntlObj = n202B5GeneralCntlObj;
	}

	/**
	 * @return the n202B5GeneralCntlObj
	 */
	public String getN202B5GeneralCntlObj() {
		return n202B5GeneralCntlObj;
	}

	/**
	 * @param n202B6WeatherFcstPeriod the n202B6WeatherFcstPeriod to set
	 */
	public void setN202B6WeatherFcstPeriod(String n202B6WeatherFcstPeriod) {
		this.n202B6WeatherFcstPeriod = n202B6WeatherFcstPeriod;
	}

	/**
	 * @return the n202B6WeatherFcstPeriod
	 */
	public String getN202B6WeatherFcstPeriod() {
		return n202B6WeatherFcstPeriod;
	}

	/**
	 * @param n202B7GeneralSafetyMsg the n202B7GeneralSafetyMsg to set
	 */
	public void setN202B7GeneralSafetyMsg(String n202B7GeneralSafetyMsg) {
		this.n202B7GeneralSafetyMsg = n202B7GeneralSafetyMsg;
	}

	/**
	 * @return the n202B7GeneralSafetyMsg
	 */
	public String getN202B7GeneralSafetyMsg() {
		return n202B7GeneralSafetyMsg;
	}

	/**
	 * @param d202B3Objectives the d202B3Objectives to set
	 */
	public void setD202B3Objectives(String d202B3Objectives) {
		this.d202B3Objectives = d202B3Objectives;
	}

	/**
	 * @return the d202B3Objectives
	 */
	public String getD202B3Objectives() {
		return d202B3Objectives;
	}

	/**
	 * @param d202B4CommandEmphasis the d202B4CommandEmphasis to set
	 */
	public void setD202B4CommandEmphasis(String d202B4CommandEmphasis) {
		this.d202B4CommandEmphasis = d202B4CommandEmphasis;
	}

	/**
	 * @return the d202B4CommandEmphasis
	 */
	public String getD202B4CommandEmphasis() {
		return d202B4CommandEmphasis;
	}

	/**
	 * @param d202B4GenSitAwareness the d202B4GenSitAwareness to set
	 */
	public void setD202B4GenSitAwareness(String d202B4GenSitAwareness) {
		this.d202B4GenSitAwareness = d202B4GenSitAwareness;
	}

	/**
	 * @return the d202B4GenSitAwareness
	 */
	public String getD202B4GenSitAwareness() {
		return d202B4GenSitAwareness;
	}

	/**
	 * @param d202B5SiteSafetyPlanRqrd the d202B5SiteSafetyPlanRqrd to set
	 */
	public void setD202B5SiteSafetyPlanRqrd(StringBooleanEnum d202B5SiteSafetyPlanRqrd) {
		this.d202B5SiteSafetyPlanRqrd = d202B5SiteSafetyPlanRqrd;
	}

	/**
	 * @return the d202B5SiteSafetyPlanRqrd
	 */
	public StringBooleanEnum getD202B5SiteSafetyPlanRqrd() {
		return d202B5SiteSafetyPlanRqrd;
	}

	/**
	 * @param d202B5SiteSafetyPlanLoc the d202B5SiteSafetyPlanLoc to set
	 */
	public void setD202B5SiteSafetyPlanLoc(String d202B5SiteSafetyPlanLoc) {
		this.d202B5SiteSafetyPlanLoc = d202B5SiteSafetyPlanLoc;
	}

	/**
	 * @return the d202B5SiteSafetyPlanLoc
	 */
	public String getD202B5SiteSafetyPlanLoc() {
		return d202B5SiteSafetyPlanLoc;
	}

	/**
	 * @param n203B2PreparedDate the n203B2PreparedDate to set
	 */
	public void setN203B2PreparedDate(Date n203B2PreparedDate) {
		this.n203B2PreparedDate = n203B2PreparedDate;
	}

	/**
	 * @return the n203B2PreparedDate
	 */
	public Date getN203B2PreparedDate() {
		return n203B2PreparedDate;
	}

	/**
	 * @param b203PreparedBy the b203PreparedBy to set
	 */
	public void setB203PreparedBy(String b203PreparedBy) {
		this.b203PreparedBy = b203PreparedBy;
	}

	/**
	 * @return the b203PreparedBy
	 */
	public String getB203PreparedBy() {
		return b203PreparedBy;
	}

	/**
	 * @param b206PreparedDate the b206PreparedDate to set
	 */
	public void setB206PreparedDate(Date b206PreparedDate) {
		this.b206PreparedDate = b206PreparedDate;
	}

	/**
	 * @return the b206PreparedDate
	 */
	public Date getB206PreparedDate() {
		return b206PreparedDate;
	}

	/**
	 * @param b206MedicalEmergencyProc the b206MedicalEmergencyProc to set
	 */
	public void setB206MedicalEmergencyProc(String b206MedicalEmergencyProc) {
		this.b206MedicalEmergencyProc = b206MedicalEmergencyProc;
	}

	/**
	 * @return the b206MedicalEmergencyProc
	 */
	public String getB206MedicalEmergencyProc() {
		return b206MedicalEmergencyProc;
	}

	/**
	 * @param b206PreparedBy the b206PreparedBy to set
	 */
	public void setB206PreparedBy(String b206PreparedBy) {
		this.b206PreparedBy = b206PreparedBy;
	}

	/**
	 * @return the b206PreparedBy
	 */
	public String getB206PreparedBy() {
		return b206PreparedBy;
	}

	/**
	 * @param b206ApprovedBy the b206ApprovedBy to set
	 */
	public void setB206ApprovedBy(String b206ApprovedBy) {
		this.b206ApprovedBy = b206ApprovedBy;
	}

	/**
	 * @return the b206ApprovedBy
	 */
	public String getB206ApprovedBy() {
		return b206ApprovedBy;
	}

	/**
	 * @param d205B5SpecialInstruction the d205B5SpecialInstruction to set
	 */
	public void setD205B5SpecialInstruction(String d205B5SpecialInstruction) {
		this.d205B5SpecialInstruction = d205B5SpecialInstruction;
	}

	/**
	 * @return the d205B5SpecialInstruction
	 */
	public String getD205B5SpecialInstruction() {
		return d205B5SpecialInstruction;
	}

	/**
	 * @param d205B6PreparedBy the d205B6PreparedBy to set
	 */
	public void setD205B6PreparedBy(String d205B6PreparedBy) {
		this.d205B6PreparedBy = d205B6PreparedBy;
	}

	/**
	 * @return the d205B6PreparedBy
	 */
	public String getD205B6PreparedBy() {
		return d205B6PreparedBy;
	}

	/**
	 * @param b220PreparedBy the b220PreparedBy to set
	 */
	public void setB220PreparedBy(String b220PreparedBy) {
		this.b220PreparedBy = b220PreparedBy;
	}

	/**
	 * @return the b220PreparedBy
	 */
	public String getB220PreparedBy() {
		return b220PreparedBy;
	}

	/**
	 * @param b220PreparedDate the b220PreparedDate to set
	 */
	public void setB220PreparedDate(Date b220PreparedDate) {
		this.b220PreparedDate = b220PreparedDate;
	}

	/**
	 * @return the b220PreparedDate
	 */
	public Date getB220PreparedDate() {
		return b220PreparedDate;
	}

	/**
	 * @param b220Sunrise the b220Sunrise to set
	 */
	public void setB220Sunrise(Date b220Sunrise) {
		this.b220Sunrise = b220Sunrise;
	}

	/**
	 * @return the b220Sunrise
	 */
	public Date getB220Sunrise() {
		return b220Sunrise;
	}

	/**
	 * @param b220Sunset the b220Sunset to set
	 */
	public void setB220Sunset(Date b220Sunset) {
		this.b220Sunset = b220Sunset;
	}

	/**
	 * @return the b220Sunset
	 */
	public Date getB220Sunset() {
		return b220Sunset;
	}

	/**
	 * @param n220B3Remarks the n220B3Remarks to set
	 */
	public void setN220B3Remarks(String n220B3Remarks) {
		this.n220B3Remarks = n220B3Remarks;
	}

	/**
	 * @return the n220B3Remarks
	 */
	public String getN220B3Remarks() {
		return n220B3Remarks;
	}

	/**
	 * @param n220B4MedivacAircraft the n220B4MedivacAircraft to set
	 */
	public void setN220B4MedivacAircraft(String n220B4MedivacAircraft) {
		this.n220B4MedivacAircraft = n220B4MedivacAircraft;
	}

	/**
	 * @return the n220B4MedivacAircraft
	 */
	public String getN220B4MedivacAircraft() {
		return n220B4MedivacAircraft;
	}

	/**
	 * @param n220B5Tfr the n220B5Tfr to set
	 */
	public void setN220B5Tfr(String n220B5Tfr) {
		this.n220B5Tfr = n220B5Tfr;
	}

	/**
	 * @return the n220B5Tfr
	 */
	public String getN220B5Tfr() {
		return n220B5Tfr;
	}

	/**
	 * @param d220B5ReadyAlertAircraft the d220B5ReadyAlertAircraft to set
	 */
	public void setD220B5ReadyAlertAircraft(String d220B5ReadyAlertAircraft) {
		this.d220B5ReadyAlertAircraft = d220B5ReadyAlertAircraft;
	}

	/**
	 * @return the d220B5ReadyAlertAircraft
	 */
	public String getD220B5ReadyAlertAircraft() {
		return d220B5ReadyAlertAircraft;
	}

	/**
	 * @param d220B5Medivac the d220B5Medivac to set
	 */
	public void setD220B5Medivac(String d220B5Medivac) {
		this.d220B5Medivac = d220B5Medivac;
	}

	/**
	 * @return the d220B5Medivac
	 */
	public String getD220B5Medivac() {
		return d220B5Medivac;
	}

	/**
	 * @param d220B5NewIncident the d220B5NewIncident to set
	 */
	public void setD220B5NewIncident(String d220B5NewIncident) {
		this.d220B5NewIncident = d220B5NewIncident;
	}

	/**
	 * @return the d220B5NewIncident
	 */
	public String getD220B5NewIncident() {
		return d220B5NewIncident;
	}

	/**
	 * @param d220B6TfrNbr the d220B6TfrNbr to set
	 */
	public void setD220B6TfrNbr(String d220B6TfrNbr) {
		this.d220B6TfrNbr = d220B6TfrNbr;
	}

	/**
	 * @return the d220B6TfrNbr
	 */
	public String getD220B6TfrNbr() {
		return d220B6TfrNbr;
	}

	/**
	 * @param d220B6Altitude the d220B6Altitude to set
	 */
	public void setD220B6Altitude(String d220B6Altitude) {
		this.d220B6Altitude = d220B6Altitude;
	}

	/**
	 * @return the d220B6Altitude
	 */
	public String getD220B6Altitude() {
		return d220B6Altitude;
	}

	/**
	 * @param d220B6CentralPoint the d220B6CentralPoint to set
	 */
	public void setD220B6CentralPoint(String d220B6CentralPoint) {
		this.d220B6CentralPoint = d220B6CentralPoint;
	}

	/**
	 * @return the d220B6CentralPoint
	 */
	public String getD220B6CentralPoint() {
		return d220B6CentralPoint;
	}

	/**
	 * @param d220B4Remarks the d220B4Remarks to set
	 */
	public void setD220B4Remarks(String d220B4Remarks) {
		this.d220B4Remarks = d220B4Remarks;
	}

	/**
	 * @return the d220B4Remarks
	 */
	public String getD220B4Remarks() {
		return d220B4Remarks;
	}

	/**
	 * @param attachForm203 the attachForm203 to set
	 */
	public void setAttachForm203(StringBooleanEnum attachForm203) {
		this.attachForm203 = attachForm203;
	}

	/**
	 * @return the attachForm203
	 */
	public StringBooleanEnum getAttachForm203() {
		return attachForm203;
	}

	/**
	 * @param attachForm204 the attachForm204 to set
	 */
	public void setAttachForm204(StringBooleanEnum attachForm204) {
		this.attachForm204 = attachForm204;
	}

	/**
	 * @return the attachForm204
	 */
	public StringBooleanEnum getAttachForm204() {
		return attachForm204;
	}

	/**
	 * @param attachForm205 the attachForm205 to set
	 */
	public void setAttachForm205(StringBooleanEnum attachForm205) {
		this.attachForm205 = attachForm205;
	}

	/**
	 * @return the attachForm205
	 */
	public StringBooleanEnum getAttachForm205() {
		return attachForm205;
	}

	/**
	 * @param attachForm205a the attachForm205a to set
	 */
	public void setAttachForm205a(StringBooleanEnum attachForm205a) {
		this.attachForm205a = attachForm205a;
	}

	/**
	 * @return the attachForm205a
	 */
	public StringBooleanEnum getAttachForm205a() {
		return attachForm205a;
	}

	/**
	 * @param attachForm206 the attachForm206 to set
	 */
	public void setAttachForm206(StringBooleanEnum attachForm206) {
		this.attachForm206 = attachForm206;
	}

	/**
	 * @return the attachForm206
	 */
	public StringBooleanEnum getAttachForm206() {
		return attachForm206;
	}

	/**
	 * @param attachForm207 the attachForm207 to set
	 */
	public void setAttachForm207(StringBooleanEnum attachForm207) {
		this.attachForm207 = attachForm207;
	}

	/**
	 * @return the attachForm207
	 */
	public StringBooleanEnum getAttachForm207() {
		return attachForm207;
	}

	/**
	 * @param attachForm208 the attachForm208 to set
	 */
	public void setAttachForm208(StringBooleanEnum attachForm208) {
		this.attachForm208 = attachForm208;
	}

	/**
	 * @return the attachForm208
	 */
	public StringBooleanEnum getAttachForm208() {
		return attachForm208;
	}

	/**
	 * @param attachForm220 the attachForm220 to set
	 */
	public void setAttachForm220(StringBooleanEnum attachForm220) {
		this.attachForm220 = attachForm220;
	}

	/**
	 * @return the attachForm220
	 */
	public StringBooleanEnum getAttachForm220() {
		return attachForm220;
	}

	/**
	 * @param attachIncidentMap the attachIncidentMap to set
	 */
	public void setAttachIncidentMap(StringBooleanEnum attachIncidentMap) {
		this.attachIncidentMap = attachIncidentMap;
	}

	/**
	 * @return the attachIncidentMap
	 */
	public StringBooleanEnum getAttachIncidentMap() {
		return attachIncidentMap;
	}

	/**
	 * @param attachSafetyMsg the attachSafetyMsg to set
	 */
	public void setAttachSafetyMsg(StringBooleanEnum attachSafetyMsg) {
		this.attachSafetyMsg = attachSafetyMsg;
	}

	/**
	 * @return the attachSafetyMsg
	 */
	public StringBooleanEnum getAttachSafetyMsg() {
		return attachSafetyMsg;
	}

	/**
	 * @param attachTrafficPlan the attachTrafficPlan to set
	 */
	public void setAttachTrafficPlan(StringBooleanEnum attachTrafficPlan) {
		this.attachTrafficPlan = attachTrafficPlan;
	}

	/**
	 * @return the attachTrafficPlan
	 */
	public StringBooleanEnum getAttachTrafficPlan() {
		return attachTrafficPlan;
	}

	/**
	 * @param attachWeatherForecast the attachWeatherForecast to set
	 */
	public void setAttachWeatherForecast(StringBooleanEnum attachWeatherForecast) {
		this.attachWeatherForecast = attachWeatherForecast;
	}

	/**
	 * @return the attachWeatherForecast
	 */
	public StringBooleanEnum getAttachWeatherForecast() {
		return attachWeatherForecast;
	}

	/**
	 * @param form202Type the form202Type to set
	 */
	public void setForm202Type(StringBooleanEnum form202Type) {
		this.form202Type = form202Type;
	}

	/**
	 * @return the form202Type
	 */
	public StringBooleanEnum getForm202Type() {
		return form202Type;
	}

	/**
	 * @param form203Type the form203Type to set
	 */
	public void setForm203Type(StringBooleanEnum form203Type) {
		this.form203Type = form203Type;
	}

	/**
	 * @return the form203Type
	 */
	public StringBooleanEnum getForm203Type() {
		return form203Type;
	}

	/**
	 * @param form204Type the form204Type to set
	 */
	public void setForm204Type(StringBooleanEnum form204Type) {
		this.form204Type = form204Type;
	}

	/**
	 * @return the form204Type
	 */
	public StringBooleanEnum getForm204Type() {
		return form204Type;
	}

	/**
	 * @param form205Type the form205Type to set
	 */
	public void setForm205Type(StringBooleanEnum form205Type) {
		this.form205Type = form205Type;
	}

	/**
	 * @return the form205Type
	 */
	public StringBooleanEnum getForm205Type() {
		return form205Type;
	}

	/**
	 * @param form206Type the form206Type to set
	 */
	public void setForm206Type(StringBooleanEnum form206Type) {
		this.form206Type = form206Type;
	}

	/**
	 * @return the form206Type
	 */
	public StringBooleanEnum getForm206Type() {
		return form206Type;
	}

	/**
	 * @param form220Type the form220Type to set
	 */
	public void setForm220Type(StringBooleanEnum form220Type) {
		this.form220Type = form220Type;
	}

	/**
	 * @return the form220Type
	 */
	public StringBooleanEnum getForm220Type() {
		return form220Type;
	}

	/**
	 * @param iapMedicalAids the iapMedicalAids to set
	 */
	public void setIapMedicalAids(Collection<IapMedicalAid> iapMedicalAids) {
		this.iapMedicalAids = iapMedicalAids;
	}

	/**
	 * @return the iapMedicalAids
	 */
	public Collection<IapMedicalAid> getIapMedicalAids() {
		if(null==iapMedicalAids)
			iapMedicalAids=new ArrayList<IapMedicalAid>();
		
		return iapMedicalAids;
	}

	/**
	 * @param iapBranches the iapBranches to set
	 */
	public void setIapBranches(Collection<IapBranch> iapBranches) {
		this.iapBranches = iapBranches;
	}

	/**
	 * @return the iapBranches
	 */
	public Collection<IapBranch> getIapBranches() {
		return iapBranches;
	}

	/**
	 * @param iapFrequencies the iapFrequencies to set
	 */
	public void setIapFrequencies(Collection<IapFrequency> iapFrequencies) {
		this.iapFrequencies = iapFrequencies;
	}

	/**
	 * @return the iapFrequencies
	 */
	public Collection<IapFrequency> getIapFrequencies() {
		return iapFrequencies;
	}

	/**
	 * @param iapAircraftFrequencies the iapAircraftFrequencies to set
	 */
	public void setIapAircraftFrequencies(Collection<IapAircraftFrequency> iapAircraftFrequencies) {
		this.iapAircraftFrequencies = iapAircraftFrequencies;
	}

	/**
	 * @return the iapAircraftFrequencies
	 */
	public Collection<IapAircraftFrequency> getIapAircraftFrequencies() {
		return iapAircraftFrequencies;
	}

	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels) {
		this.iapPersonnels = iapPersonnels;
	}

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels() {
		return iapPersonnels;
	}

	/**
	 * @param iapAircraftTasks the iapAircraftTasks to set
	 */
	public void setIapAircraftTasks(Collection<IapAircraftTask> iapAircraftTasks) {
		this.iapAircraftTasks = iapAircraftTasks;
	}

	/**
	 * @return the iapAircraftTasks
	 */
	public Collection<IapAircraftTask> getIapAircraftTasks() {
		return iapAircraftTasks;
	}

	/**
	 * @param iapAttachments the iapAttachments to set
	 */
	public void setIapAttachments(Collection<IapAttachment> iapAttachments) {
		this.iapAttachments = iapAttachments;
	}

	/**
	 * @return the iapAttachments
	 */
	public Collection<IapAttachment> getIapAttachments() {
		return iapAttachments;
	}

	/**
	 * @param iapAircrafts the iapAircrafts to set
	 */
	public void setIapAircrafts(Collection<IapAircraft> iapAircrafts) {
		this.iapAircrafts = iapAircrafts;
	}

	/**
	 * @return the iapAircrafts
	 */
	public Collection<IapAircraft> getIapAircrafts() {
		return iapAircrafts;
	}

	/**
	 * @param iapHospitals the iapHospitals to set
	 */
	public void setIapHospitals(Collection<IapHospital> iapHospitals) {
		this.iapHospitals = iapHospitals;
	}

	/**
	 * @return the iapHospitals
	 */
	public Collection<IapHospital> getIapHospitals() {
		return iapHospitals;
	}

	/**
	 * @return the isPlanLocked
	 */
	public StringBooleanEnum getIsPlanLocked() {
		return isPlanLocked;
	}

	/**
	 * @param isPlanLocked the isPlanLocked to set
	 */
	public void setIsPlanLocked(StringBooleanEnum isPlanLocked) {
		this.isPlanLocked = isPlanLocked;
	}

	/**
	 * @return the isForm203Locked
	 */
	public StringBooleanEnum getIsForm203Locked() {
		return isForm203Locked;
	}

	/**
	 * @param isForm203Locked the isForm203Locked to set
	 */
	public void setIsForm203Locked(StringBooleanEnum isForm203Locked) {
		this.isForm203Locked = isForm203Locked;
	}

	/**
	 * @return the isForm202Locked
	 */
	public StringBooleanEnum getIsForm202Locked() {
		return isForm202Locked;
	}

	/**
	 * @param isForm202Locked the isForm202Locked to set
	 */
	public void setIsForm202Locked(StringBooleanEnum isForm202Locked) {
		this.isForm202Locked = isForm202Locked;
	}

	/**
	 * @return the isForm205Locked
	 */
	public StringBooleanEnum getIsForm205Locked() {
		return isForm205Locked;
	}

	/**
	 * @param isForm205Locked the isForm205Locked to set
	 */
	public void setIsForm205Locked(StringBooleanEnum isForm205Locked) {
		this.isForm205Locked = isForm205Locked;
	}

	/**
	 * @return the isForm206Locked
	 */
	public StringBooleanEnum getIsForm206Locked() {
		return isForm206Locked;
	}

	/**
	 * @param isForm206Locked the isForm206Locked to set
	 */
	public void setIsForm206Locked(StringBooleanEnum isForm206Locked) {
		this.isForm206Locked = isForm206Locked;
	}

	/**
	 * @return the isForm220Locked
	 */
	public StringBooleanEnum getIsForm220Locked() {
		return isForm220Locked;
	}

	/**
	 * @param isForm220Locked the isForm220Locked to set
	 */
	public void setIsForm220Locked(StringBooleanEnum isForm220Locked) {
		this.isForm220Locked = isForm220Locked;
	}

	/**
	 * @return the b205PreparedDate
	 */
	public Date getB205PreparedDate() {
		return b205PreparedDate;
	}

	/**
	 * @param preparedDate the b205PreparedDate to set
	 */
	public void setB205PreparedDate(Date preparedDate) {
		b205PreparedDate = preparedDate;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the operationPeriod
	 */
	public String getOperationPeriod() {
		return operationPeriod;
	}

	/**
	 * @param operationPeriod the operationPeriod to set
	 */
	public void setOperationPeriod(String operationPeriod) {
		this.operationPeriod = operationPeriod;
	}

	/**
	 * @return the iapPlan
	 */
	public Iap getIapPlan() {
		return iapPlan;
	}

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(Iap iapPlan) {
		this.iapPlan = iapPlan;
	}

	/**
	 * @return the iapForms
	 */
	public Collection<Iap> getIapForms() {
		return iapForms;
	}

	/**
	 * @param iapForms the iapForms to set
	 */
	public void setIapForms(Collection<Iap> iapForms) {
		this.iapForms = iapForms;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

}
