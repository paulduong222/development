package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Iap;
import gov.nwcg.isuite.core.domain.impl.IapImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapVo extends AbstractVo implements PersistableVo {
	
	private Date nwcgDate;
	private String nwcgDay;
	private Date nwcg202B2PreparedDate;
	private String nwcg202B5GeneralCntlObj;
	private String nwcg202B6WeatherFcstPeriod;
	private String nwcg202B7GeneralSafetyMsg;
	private Date nwcg203B2PreparedDate;
	private String nwcg220B3Remarks;
	private String nwcg220B4MedivacAircraft;
	private String nwcg220B5Tfr;
	
	private Date dhsFromDate;
	private Date dhsToDate;
	private String sdhsFromDate; // helper field
	private String sdhsToDate; // helper field
	private String sdhsFromDateTime; // helper field
	private String sdhsToDateTime; // helper field
	
	private String block202PreparedBy;
	private String block202ApprovedBy;
	private String block203PreparedBy;
	private Date block206PreparedDate;
	private String block206PreparedDateString; // helper field
	private String block206PreparedTimeString; // helper field
	private String block206MedicalEmergencyProc;
	private String block206PreparedBy;
	private String block206ApprovedBy;
	private String block220PreparedBy;
	private Date block205PreparedDate;
	private Date block220PreparedDate;
	private Date block220Sunrise;
	private Date block220Sunset;
	
	private String dhs202B3Objectives;
	private String dhs202B4CommandEmphasis;
	private String dhs202B4GenSitAwareness;
	private Boolean dhs202B5SiteSafetyPlanRqrd;
	private String dhs202B5SiteSafetyPlanLoc;
	private String dhs205B5SpecialInstruction;
	private String dhs205B6PreparedBy;
	private String dhs220B5ReadyAlertAircraft;
	private String dhs220B5Medivac;
	private String dhs220B5NewIncident;
	private String dhs220B6TfrNbr;
	private String dhs220B6Altitude;
	private String dhs220B6CentralPoint;
	private String dhs220B4Remarks;

	private Boolean attachForm203;
	private Boolean attachForm204;
	private Boolean attachForm205;
	private Boolean attachForm205a;
	private Boolean attachForm206;
	private Boolean attachForm207;
	private Boolean attachForm208;
	private Boolean attachForm220;
	private Boolean attachIncidentMap;
	private Boolean attachSafetyMsg;
	private Boolean attachTrafficPlan;
	private Boolean attachWeatherForecast;
	
	private Boolean form202Type;
	private Boolean form203Type;
	private Boolean form204Type;
	private Boolean form205Type;
	private Boolean form206Type;
	private Boolean form220Type;
	
	private Collection<IapMedicalAidVo> iapMedicalAidVos = new ArrayList<IapMedicalAidVo>();
	private Collection<IapBranchVo> iapBranchVos = new ArrayList<IapBranchVo>();
	private Collection<IapFrequencyVo> iapFrequencyVos = new ArrayList<IapFrequencyVo>();
	private Collection<IapAircraftFrequencyVo> iapAircraftFrequencyVos = new ArrayList<IapAircraftFrequencyVo>();
	private Collection<IapPersonnelVo> iapPersonnelVos = new ArrayList<IapPersonnelVo>();
	private Collection<IapAircraftTaskVo> iapAircraftTaskVos = new ArrayList<IapAircraftTaskVo>();
	private Collection<IapAttachmentVo> iapAttachmentVos = new ArrayList<IapAttachmentVo>();
	private Collection<IapAircraftVo> iapAircraftVos = new ArrayList<IapAircraftVo>();
	private Collection<IapHospitalVo> iapHospitalVos = new ArrayList<IapHospitalVo>();

	private Long incidentId;
	private Long incidentGroupId;
	
	/**
	 * Constructor
	 */
	public IapVo() {
	}

	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapVo getInstance(Iap entity, boolean cascadable) throws Exception {
		IapVo vo = new IapVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapVo from null Iap entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIncidentGroupId(entity.getIncidentGroupId());
			vo.setIncidentId(entity.getIncidentId());
			vo.setBlock205PreparedDate(entity.getB205PreparedDate());
			vo.setNwcgDate(entity.getNDate());
			vo.setNwcgDay(entity.getNDay());
			vo.setNwcg202B2PreparedDate(entity.getN202B2PreparedDate());
			vo.setNwcg202B5GeneralCntlObj(entity.getN202B5GeneralCntlObj());
			vo.setNwcg202B6WeatherFcstPeriod(entity.getN202B6WeatherFcstPeriod());
			vo.setNwcg202B7GeneralSafetyMsg(entity.getN202B7GeneralSafetyMsg());
			vo.setNwcg203B2PreparedDate(entity.getN203B2PreparedDate());
			vo.setNwcg203B2PreparedDate(entity.getN203B2PreparedDate());
			vo.setNwcg220B3Remarks(entity.getN220B3Remarks());
			vo.setNwcg220B4MedivacAircraft(entity.getN220B4MedivacAircraft());
			vo.setNwcg220B5Tfr(entity.getN220B5Tfr());

			vo.setDhsFromDate(entity.getDFromDate());
			if(DateUtil.hasValue(entity.getDFromDate()))
				vo.setSdhsFromDate(DateUtil.toDateString(entity.getDFromDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
			
			vo.setDhsToDate(entity.getDToDate());
			if(DateUtil.hasValue(entity.getDToDate()))
				vo.setSdhsToDate(DateUtil.toDateString(entity.getDToDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));

			vo.setDhs202B3Objectives(entity.getD202B3Objectives());
			vo.setDhs202B4CommandEmphasis(entity.getD202B4CommandEmphasis());
			vo.setDhs202B4GenSitAwareness(entity.getD202B4GenSitAwareness());
			vo.setDhs202B5SiteSafetyPlanRqrd(entity.getD202B5SiteSafetyPlanRqrd().getValue());
			vo.setDhs202B5SiteSafetyPlanLoc(entity.getD202B5SiteSafetyPlanLoc());
			vo.setDhs205B5SpecialInstruction(entity.getD205B5SpecialInstruction());
			vo.setDhs205B6PreparedBy(entity.getD205B6PreparedBy());
			vo.setDhs220B5ReadyAlertAircraft(entity.getD220B5ReadyAlertAircraft());
			vo.setDhs220B5Medivac(entity.getD220B5Medivac());
			vo.setDhs220B5NewIncident(entity.getD220B5NewIncident());
			vo.setDhs220B6TfrNbr(entity.getD220B6TfrNbr());
			vo.setDhs220B6Altitude(entity.getD220B6Altitude());
			vo.setDhs220B6CentralPoint(entity.getD220B6CentralPoint());
			vo.setDhs220B4Remarks(entity.getD220B4Remarks());

			vo.setBlock202PreparedBy(entity.getB202PreparedBy());
			vo.setBlock202ApprovedBy(entity.getB202ApprovedBy());
			vo.setBlock203PreparedBy(entity.getB203PreparedBy());
			
			vo.setBlock206PreparedDate(entity.getB206PreparedDate());
			if(DateUtil.hasValue(entity.getB206PreparedDate())){
				vo.setBlock206PreparedDateString(DateUtil.toDateString(entity.getB206PreparedDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY));
				String tm=DateUtil.toMilitaryTime(entity.getB206PreparedDate());
				vo.setBlock206PreparedTimeString(tm);
			}
			
			vo.setBlock206MedicalEmergencyProc(entity.getB206MedicalEmergencyProc());
			vo.setBlock206PreparedBy(entity.getB206PreparedBy());
			vo.setBlock206ApprovedBy(entity.getB206ApprovedBy());
			vo.setBlock220PreparedBy(entity.getB220PreparedBy());
			vo.setBlock220PreparedDate(entity.getB220PreparedDate());
			vo.setBlock220Sunrise(entity.getB220Sunrise());
			vo.setBlock220Sunset(entity.getB220Sunset());

			vo.setAttachForm203(entity.getAttachForm203().getValue());
			vo.setAttachForm204(entity.getAttachForm204().getValue());
			vo.setAttachForm205(entity.getAttachForm205().getValue());
			vo.setAttachForm205a(entity.getAttachForm205a().getValue());
			vo.setAttachForm206(entity.getAttachForm206().getValue());
			vo.setAttachForm207(entity.getAttachForm207().getValue());
			vo.setAttachForm208(entity.getAttachForm208().getValue());
			vo.setAttachForm220(entity.getAttachForm220().getValue());
			vo.setAttachIncidentMap(entity.getAttachIncidentMap().getValue());
			vo.setAttachSafetyMsg(entity.getAttachSafetyMsg().getValue());
			vo.setAttachTrafficPlan(entity.getAttachTrafficPlan().getValue());
			vo.setAttachWeatherForecast(entity.getAttachWeatherForecast().getValue());

			vo.setForm202Type(entity.getForm202Type().getValue());
			vo.setForm203Type(entity.getForm203Type().getValue());
			vo.setForm204Type(entity.getForm204Type().getValue());
			vo.setForm205Type(entity.getForm205Type().getValue());
			vo.setForm206Type(entity.getForm206Type().getValue());
			vo.setForm220Type(entity.getForm220Type().getValue());

			if(null != entity.getIapMedicalAids()) {
				vo.setIapMedicalAidVos(IapMedicalAidVo.getInstances(entity.getIapMedicalAids(), true));
			}
			
			if(null != entity.getIapBranches()) {
				vo.setIapBranchVos(IapBranchVo.getInstances(entity.getIapBranches(), true));
			}
			
			if(null != entity.getIapFrequencies()) {
				vo.setIapFrequencyVos(IapFrequencyVo.getInstances(entity.getIapFrequencies(), true));
			}
			
			if(null != entity.getIapAircraftFrequencies()) {
				vo.setIapAircraftFrequencyVos(IapAircraftFrequencyVo.getInstances(entity.getIapAircraftFrequencies(), true));
			}
			
			if(null != entity.getIapPersonnels()) {
				//vo.setIapPersonnelVos(IapPersonnelVo.getInstances(entity.getIapPersonnels(), true));
			}
			
			if(null != entity.getIapAircraftTasks()) {
				vo.setIapAircraftTaskVos(IapAircraftTaskVo.getInstances(entity.getIapAircraftTasks(), true));
			}
			
			if(null != entity.getIapAttachments()) {
				vo.setIapAttachmentVos(IapAttachmentVo.getInstances(entity.getIapAttachments(), true));
			}
			
			if(null != entity.getIapAircrafts()) {
				vo.setIapAircraftVos(IapAircraftVo.getInstances(entity.getIapAircrafts(), true));
			}
			
			if(null != entity.getIapHospitals()) {
				vo.setIapHospitalVos(IapHospitalVo.getInstances(entity.getIapHospitals(), true));
			}
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapVo> getInstances(Collection<Iap> entities, boolean cascadable) throws Exception {
		Collection<IapVo> vos = new ArrayList<IapVo>();
		
		for(Iap entity : entities) {
			vos.add(IapVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Iap toEntity(Iap entity, IapVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			
			entity.setNDate(vo.getNwcgDate());
			entity.setNDay(vo.getNwcgDay());
			
			// use string date to avoid timezone issue when passing dates
			if(StringUtility.hasValue(vo.getSdhsFromDate())){
				entity.setDFromDate(DateUtil.toDate(vo.getSdhsFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}else
				entity.setDFromDate(null);

			if(StringUtility.hasValue(vo.getSdhsToDate())){
				entity.setDToDate(DateUtil.toDate(vo.getSdhsToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
			}else
				entity.setDToDate(null);

			entity.setB205PreparedDate(vo.getBlock205PreparedDate());
			entity.setIncidentGroupId(vo.getIncidentGroupId());
			entity.setIncidentId(vo.getIncidentId());
			
			entity.setB202PreparedBy(vo.getBlock202PreparedBy());
			entity.setB202ApprovedBy(vo.getBlock202ApprovedBy());
			entity.setN202B2PreparedDate(vo.getNwcg202B2PreparedDate());
			entity.setN202B5GeneralCntlObj(vo.getNwcg202B5GeneralCntlObj());
			entity.setN202B6WeatherFcstPeriod(vo.getNwcg202B6WeatherFcstPeriod());
			entity.setN202B7GeneralSafetyMsg(vo.getNwcg202B7GeneralSafetyMsg());
			entity.setD202B3Objectives(vo.getDhs202B3Objectives());
			entity.setD202B4CommandEmphasis(vo.getDhs202B4CommandEmphasis());
			entity.setD202B4GenSitAwareness(vo.getDhs202B4GenSitAwareness());
			entity.setD202B5SiteSafetyPlanRqrd(StringBooleanEnum.toEnumValue(vo.getDhs202B5SiteSafetyPlanRqrd()));
			entity.setD202B5SiteSafetyPlanLoc(vo.getDhs202B5SiteSafetyPlanLoc());
			entity.setN203B2PreparedDate(vo.getNwcg203B2PreparedDate());
			entity.setB203PreparedBy(vo.getBlock203PreparedBy());

			entity.setB206PreparedDate(vo.getBlock206PreparedDate());
			if(StringUtility.hasValue(vo.getBlock206PreparedDateString())){
				// use string date instead
				Date newDate=DateUtil.toDate(vo.getBlock206PreparedDateString(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
				if(StringUtility.hasValue(vo.getBlock206PreparedTimeString())){
					newDate=DateUtil.addMilitaryTimeToDate(newDate, vo.getBlock206PreparedTimeString());
				}
				entity.setB206PreparedDate(newDate);
			}	
			
			entity.setB206MedicalEmergencyProc(vo.getBlock206MedicalEmergencyProc());
			entity.setB206PreparedBy(vo.getBlock206PreparedBy());
			entity.setB206ApprovedBy(vo.getBlock206ApprovedBy());
			entity.setD205B5SpecialInstruction(vo.getDhs205B5SpecialInstruction());
			entity.setD205B6PreparedBy(vo.getDhs205B6PreparedBy());
			entity.setB220PreparedBy(vo.getBlock220PreparedBy());
			entity.setB220PreparedDate(vo.getBlock220PreparedDate());
			entity.setB220Sunrise(vo.getBlock220Sunrise());
			entity.setB220Sunset(vo.getBlock220Sunset());
			entity.setN220B3Remarks(vo.getNwcg220B3Remarks());
			entity.setN220B4MedivacAircraft(vo.getNwcg220B4MedivacAircraft());
			entity.setN220B5Tfr(vo.getNwcg220B5Tfr());
			entity.setD220B5ReadyAlertAircraft(vo.getDhs220B5ReadyAlertAircraft());
			entity.setD220B5Medivac(vo.getDhs220B5Medivac());
			entity.setD220B5NewIncident(vo.getDhs220B5NewIncident());
			entity.setD220B6TfrNbr(vo.getDhs220B6TfrNbr());
			entity.setD220B6Altitude(vo.getDhs220B6Altitude());
			entity.setD220B6CentralPoint(vo.getDhs220B6CentralPoint());
			entity.setD220B4Remarks(vo.getDhs220B4Remarks());
			entity.setAttachForm203(StringBooleanEnum.toEnumValue(vo.getAttachForm203()));
			entity.setAttachForm204(StringBooleanEnum.toEnumValue(vo.getAttachForm204()));
			entity.setAttachForm205(StringBooleanEnum.toEnumValue(vo.getAttachForm205()));
			entity.setAttachForm205a(StringBooleanEnum.toEnumValue(vo.getAttachForm205a()));
			entity.setAttachForm206(StringBooleanEnum.toEnumValue(vo.getAttachForm206()));
			entity.setAttachForm207(StringBooleanEnum.toEnumValue(vo.getAttachForm207()));
			entity.setAttachForm208(StringBooleanEnum.toEnumValue(vo.getAttachForm208()));
			entity.setAttachForm220(StringBooleanEnum.toEnumValue(vo.getAttachForm220()));
			entity.setAttachIncidentMap(StringBooleanEnum.toEnumValue(vo.getAttachIncidentMap()));
			entity.setAttachSafetyMsg(StringBooleanEnum.toEnumValue(vo.getAttachSafetyMsg()));
			entity.setAttachTrafficPlan(StringBooleanEnum.toEnumValue(vo.getAttachTrafficPlan()));
			entity.setAttachWeatherForecast(StringBooleanEnum.toEnumValue(vo.getAttachWeatherForecast()));
			entity.setForm202Type(StringBooleanEnum.toEnumValue(vo.getForm202Type()));
			entity.setForm203Type(StringBooleanEnum.toEnumValue(vo.getForm203Type()));
			entity.setForm204Type(StringBooleanEnum.toEnumValue(vo.getForm204Type()));
			entity.setForm205Type(StringBooleanEnum.toEnumValue(vo.getForm205Type()));
			entity.setForm206Type(StringBooleanEnum.toEnumValue(vo.getForm206Type()));
			entity.setForm220Type(StringBooleanEnum.toEnumValue(vo.getForm220Type()));

			if(CollectionUtility.hasValue(vo.getIapMedicalAidVos())){
				entity.setIapMedicalAids(IapMedicalAidVo.toEntityList(vo.getIapMedicalAidVos(), true));
			}else
				entity.getIapMedicalAids().clear();
			
			if(null != vo.getIapBranchVos()) {
				entity.setIapBranches(IapBranchVo.toEntityList(vo.getIapBranchVos(), true));
			}
			
			if(null != vo.getIapFrequencyVos()) {
				entity.setIapFrequencies(IapFrequencyVo.toEntityList(vo.getIapFrequencyVos(), true));
			}
			
			if(null != vo.getIapAircraftFrequencyVos()) {
				entity.setIapAircraftFrequencies(IapAircraftFrequencyVo.toEntityList(vo.getIapAircraftFrequencyVos(), true));
			}
			
			if(null != vo.getIapPersonnelVos()) {
				entity.setIapPersonnels(IapPersonnelVo.toEntityList(vo.getIapPersonnelVos(), true));
			}
			
			if(null != vo.getIapAircraftTaskVos()) {
				entity.setIapAircraftTasks(IapAircraftTaskVo.toEntityList(vo.getIapAircraftTaskVos(), true));
			}
			
			if(null != vo.getIapAttachmentVos()) {
				entity.setIapAttachments(IapAttachmentVo.toEntityList(vo.getIapAttachmentVos(), true));
			}
			
			if(null != vo.getIapAircraftVos()) {
				entity.setIapAircrafts(IapAircraftVo.toEntityList(vo.getIapAircraftVos(), true));
			}
			
			if(null != vo.getIapHospitalVos()) {
				entity.setIapHospitals(IapHospitalVo.toEntityList(vo.getIapHospitalVos(), true));
			}
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<Iap> toEntityList(Collection<IapVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Iap> entities = new ArrayList<Iap>();
		
		for(IapVo vo : vos) {
			entities.add(IapVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}


	/**
	 * @return the nwcgDate
	 */
	public Date getNwcgDate() {
		return nwcgDate;
	}


	/**
	 * @param nwcgDate the nwcgDate to set
	 */
	public void setNwcgDate(Date nwcgDate) {
		this.nwcgDate = nwcgDate;
	}


	/**
	 * @return the nwcgDay
	 */
	public String getNwcgDay() {
		return nwcgDay;
	}


	/**
	 * @param nwcgDay the nwcgDay to set
	 */
	public void setNwcgDay(String nwcgDay) {
		this.nwcgDay = nwcgDay;
	}


	/**
	 * @return the dhsFromDate
	 */
	public Date getDhsFromDate() {
		return dhsFromDate;
	}


	/**
	 * @param dhsFromDate the dhsFromDate to set
	 */
	public void setDhsFromDate(Date dhsFromDate) {
		this.dhsFromDate = dhsFromDate;
	}


	/**
	 * @return the dhsToDate
	 */
	public Date getDhsToDate() {
		return dhsToDate;
	}


	/**
	 * @param dhsToDate the dhsToDate to set
	 */
	public void setDhsToDate(Date dhsToDate) {
		this.dhsToDate = dhsToDate;
	}


	/**
	 * @return the sdhsFromDate
	 */
	public String getSdhsFromDate() {
		return sdhsFromDate;
	}


	/**
	 * @param sdhsFromDate the sdhsFromDate to set
	 */
	public void setSdhsFromDate(String sdhsFromDate) {
		this.sdhsFromDate = sdhsFromDate;
	}


	/**
	 * @return the sdhsToDate
	 */
	public String getSdhsToDate() {
		return sdhsToDate;
	}


	/**
	 * @param sdhsToDate the sdhsToDate to set
	 */
	public void setSdhsToDate(String sdhsToDate) {
		this.sdhsToDate = sdhsToDate;
	}


	/**
	 * @return the sdhsFromDateTime
	 */
	public String getSdhsFromDateTime() {
		return sdhsFromDateTime;
	}


	/**
	 * @param sdhsFromDateTime the sdhsFromDateTime to set
	 */
	public void setSdhsFromDateTime(String sdhsFromDateTime) {
		this.sdhsFromDateTime = sdhsFromDateTime;
	}


	/**
	 * @return the sdhsToDateTime
	 */
	public String getSdhsToDateTime() {
		return sdhsToDateTime;
	}


	/**
	 * @param sdhsToDateTime the sdhsToDateTime to set
	 */
	public void setSdhsToDateTime(String sdhsToDateTime) {
		this.sdhsToDateTime = sdhsToDateTime;
	}


	/**
	 * @return the block202PreparedBy
	 */
	public String getBlock202PreparedBy() {
		return block202PreparedBy;
	}


	/**
	 * @param block202PreparedBy the block202PreparedBy to set
	 */
	public void setBlock202PreparedBy(String block202PreparedBy) {
		this.block202PreparedBy = block202PreparedBy;
	}


	/**
	 * @return the block202ApprovedBy
	 */
	public String getBlock202ApprovedBy() {
		return block202ApprovedBy;
	}


	/**
	 * @param block202ApprovedBy the block202ApprovedBy to set
	 */
	public void setBlock202ApprovedBy(String block202ApprovedBy) {
		this.block202ApprovedBy = block202ApprovedBy;
	}


	/**
	 * @return the nwcg202B2PreparedDate
	 */
	public Date getNwcg202B2PreparedDate() {
		return nwcg202B2PreparedDate;
	}


	/**
	 * @param nwcg202B2PreparedDate the nwcg202B2PreparedDate to set
	 */
	public void setNwcg202B2PreparedDate(Date nwcg202B2PreparedDate) {
		this.nwcg202B2PreparedDate = nwcg202B2PreparedDate;
	}


	/**
	 * @return the nwcg202B5GeneralCntlObj
	 */
	public String getNwcg202B5GeneralCntlObj() {
		return nwcg202B5GeneralCntlObj;
	}


	/**
	 * @param nwcg202B5GeneralCntlObj the nwcg202B5GeneralCntlObj to set
	 */
	public void setNwcg202B5GeneralCntlObj(String nwcg202B5GeneralCntlObj) {
		this.nwcg202B5GeneralCntlObj = nwcg202B5GeneralCntlObj;
	}


	/**
	 * @return the nwcg202B6WeatherFcstPeriod
	 */
	public String getNwcg202B6WeatherFcstPeriod() {
		return nwcg202B6WeatherFcstPeriod;
	}


	/**
	 * @param nwcg202B6WeatherFcstPeriod the nwcg202B6WeatherFcstPeriod to set
	 */
	public void setNwcg202B6WeatherFcstPeriod(String nwcg202B6WeatherFcstPeriod) {
		this.nwcg202B6WeatherFcstPeriod = nwcg202B6WeatherFcstPeriod;
	}


	/**
	 * @return the nwcg202B7GeneralSafetyMsg
	 */
	public String getNwcg202B7GeneralSafetyMsg() {
		return nwcg202B7GeneralSafetyMsg;
	}


	/**
	 * @param nwcg202B7GeneralSafetyMsg the nwcg202B7GeneralSafetyMsg to set
	 */
	public void setNwcg202B7GeneralSafetyMsg(String nwcg202B7GeneralSafetyMsg) {
		this.nwcg202B7GeneralSafetyMsg = nwcg202B7GeneralSafetyMsg;
	}


	/**
	 * @return the dhs202B3Objectives
	 */
	public String getDhs202B3Objectives() {
		return dhs202B3Objectives;
	}


	/**
	 * @param dhs202B3Objectives the dhs202B3Objectives to set
	 */
	public void setDhs202B3Objectives(String dhs202B3Objectives) {
		this.dhs202B3Objectives = dhs202B3Objectives;
	}


	/**
	 * @return the dhs202B4CommandEmphasis
	 */
	public String getDhs202B4CommandEmphasis() {
		return dhs202B4CommandEmphasis;
	}


	/**
	 * @param dhs202B4CommandEmphasis the dhs202B4CommandEmphasis to set
	 */
	public void setDhs202B4CommandEmphasis(String dhs202B4CommandEmphasis) {
		this.dhs202B4CommandEmphasis = dhs202B4CommandEmphasis;
	}


	/**
	 * @return the dhs202B4GenSitAwareness
	 */
	public String getDhs202B4GenSitAwareness() {
		return dhs202B4GenSitAwareness;
	}


	/**
	 * @param dhs202B4GenSitAwareness the dhs202B4GenSitAwareness to set
	 */
	public void setDhs202B4GenSitAwareness(String dhs202B4GenSitAwareness) {
		this.dhs202B4GenSitAwareness = dhs202B4GenSitAwareness;
	}


	/**
	 * @return the dhs202B5SiteSafetyPlanRqrd
	 */
	public Boolean getDhs202B5SiteSafetyPlanRqrd() {
		return dhs202B5SiteSafetyPlanRqrd;
	}


	/**
	 * @param dhs202B5SiteSafetyPlanRqrd the dhs202B5SiteSafetyPlanRqrd to set
	 */
	public void setDhs202B5SiteSafetyPlanRqrd(Boolean dhs202B5SiteSafetyPlanRqrd) {
		this.dhs202B5SiteSafetyPlanRqrd = dhs202B5SiteSafetyPlanRqrd;
	}


	/**
	 * @return the dhs202B5SiteSafetyPlanLoc
	 */
	public String getDhs202B5SiteSafetyPlanLoc() {
		return dhs202B5SiteSafetyPlanLoc;
	}


	/**
	 * @param dhs202B5SiteSafetyPlanLoc the dhs202B5SiteSafetyPlanLoc to set
	 */
	public void setDhs202B5SiteSafetyPlanLoc(String dhs202B5SiteSafetyPlanLoc) {
		this.dhs202B5SiteSafetyPlanLoc = dhs202B5SiteSafetyPlanLoc;
	}


	/**
	 * @return the nwcg203B2PreparedDate
	 */
	public Date getNwcg203B2PreparedDate() {
		return nwcg203B2PreparedDate;
	}


	/**
	 * @param nwcg203B2PreparedDate the nwcg203B2PreparedDate to set
	 */
	public void setNwcg203B2PreparedDate(Date nwcg203B2PreparedDate) {
		this.nwcg203B2PreparedDate = nwcg203B2PreparedDate;
	}


	/**
	 * @return the block203PreparedBy
	 */
	public String getBlock203PreparedBy() {
		return block203PreparedBy;
	}


	/**
	 * @param block203PreparedBy the block203PreparedBy to set
	 */
	public void setBlock203PreparedBy(String block203PreparedBy) {
		this.block203PreparedBy = block203PreparedBy;
	}


	/**
	 * @return the block206PreparedDate
	 */
	public Date getBlock206PreparedDate() {
		return block206PreparedDate;
	}


	/**
	 * @param block206PreparedDate the block206PreparedDate to set
	 */
	public void setBlock206PreparedDate(Date block206PreparedDate) {
		this.block206PreparedDate = block206PreparedDate;
	}


	/**
	 * @return the block206MedicalEmergencyProc
	 */
	public String getBlock206MedicalEmergencyProc() {
		return block206MedicalEmergencyProc;
	}


	/**
	 * @param block206MedicalEmergencyProc the block206MedicalEmergencyProc to set
	 */
	public void setBlock206MedicalEmergencyProc(String block206MedicalEmergencyProc) {
		this.block206MedicalEmergencyProc = block206MedicalEmergencyProc;
	}


	/**
	 * @return the block206PreparedBy
	 */
	public String getBlock206PreparedBy() {
		return block206PreparedBy;
	}


	/**
	 * @param block206PreparedBy the block206PreparedBy to set
	 */
	public void setBlock206PreparedBy(String block206PreparedBy) {
		this.block206PreparedBy = block206PreparedBy;
	}


	/**
	 * @return the block206ApprovedBy
	 */
	public String getBlock206ApprovedBy() {
		return block206ApprovedBy;
	}


	/**
	 * @param block206ApprovedBy the block206ApprovedBy to set
	 */
	public void setBlock206ApprovedBy(String block206ApprovedBy) {
		this.block206ApprovedBy = block206ApprovedBy;
	}


	/**
	 * @return the dhs205B5SpecialInstruction
	 */
	public String getDhs205B5SpecialInstruction() {
		return dhs205B5SpecialInstruction;
	}


	/**
	 * @param dhs205B5SpecialInstruction the dhs205B5SpecialInstruction to set
	 */
	public void setDhs205B5SpecialInstruction(String dhs205B5SpecialInstruction) {
		this.dhs205B5SpecialInstruction = dhs205B5SpecialInstruction;
	}


	/**
	 * @return the dhs205B6PreparedBy
	 */
	public String getDhs205B6PreparedBy() {
		return dhs205B6PreparedBy;
	}


	/**
	 * @param dhs205B6PreparedBy the dhs205B6PreparedBy to set
	 */
	public void setDhs205B6PreparedBy(String dhs205B6PreparedBy) {
		this.dhs205B6PreparedBy = dhs205B6PreparedBy;
	}


	/**
	 * @return the block220PreparedBy
	 */
	public String getBlock220PreparedBy() {
		return block220PreparedBy;
	}


	/**
	 * @param preparedBy the block220PreparedBy to set
	 */
	public void setBlock220PreparedBy(String preparedBy) {
		block220PreparedBy = preparedBy;
	}


	/**
	 * @return the block220PreparedDate
	 */
	public Date getBlock220PreparedDate() {
		return block220PreparedDate;
	}


	/**
	 * @param block220PreparedDate the block220PreparedDate to set
	 */
	public void setBlock220PreparedDate(Date block220PreparedDate) {
		this.block220PreparedDate = block220PreparedDate;
	}


	/**
	 * @return the block220Sunrise
	 */
	public Date getBlock220Sunrise() {
		return block220Sunrise;
	}


	/**
	 * @param block220Sunrise the block220Sunrise to set
	 */
	public void setBlock220Sunrise(Date block220Sunrise) {
		this.block220Sunrise = block220Sunrise;
	}


	/**
	 * @return the block220Sunset
	 */
	public Date getBlock220Sunset() {
		return block220Sunset;
	}


	/**
	 * @param block220Sunset the block220Sunset to set
	 */
	public void setBlock220Sunset(Date block220Sunset) {
		this.block220Sunset = block220Sunset;
	}


	/**
	 * @return the nwcg220B3Remarks
	 */
	public String getNwcg220B3Remarks() {
		return nwcg220B3Remarks;
	}


	/**
	 * @param nwcg220B3Remarks the nwcg220B3Remarks to set
	 */
	public void setNwcg220B3Remarks(String nwcg220B3Remarks) {
		this.nwcg220B3Remarks = nwcg220B3Remarks;
	}


	/**
	 * @return the nwcg220B4MedivacAircraft
	 */
	public String getNwcg220B4MedivacAircraft() {
		return nwcg220B4MedivacAircraft;
	}


	/**
	 * @param nwcg220B4MedivacAircraft the nwcg220B4MedivacAircraft to set
	 */
	public void setNwcg220B4MedivacAircraft(String nwcg220B4MedivacAircraft) {
		this.nwcg220B4MedivacAircraft = nwcg220B4MedivacAircraft;
	}


	/**
	 * @return the nwcg220B5Tfr
	 */
	public String getNwcg220B5Tfr() {
		return nwcg220B5Tfr;
	}


	/**
	 * @param nwcg220B5Tfr the nwcg220B5Tfr to set
	 */
	public void setNwcg220B5Tfr(String nwcg220B5Tfr) {
		this.nwcg220B5Tfr = nwcg220B5Tfr;
	}


	/**
	 * @return the dhs220B5ReadyAlertAircraft
	 */
	public String getDhs220B5ReadyAlertAircraft() {
		return dhs220B5ReadyAlertAircraft;
	}


	/**
	 * @param dhs220B5ReadyAlertAircraft the dhs220B5ReadyAlertAircraft to set
	 */
	public void setDhs220B5ReadyAlertAircraft(String dhs220B5ReadyAlertAircraft) {
		this.dhs220B5ReadyAlertAircraft = dhs220B5ReadyAlertAircraft;
	}


	/**
	 * @return the dhs220B5Medivac
	 */
	public String getDhs220B5Medivac() {
		return dhs220B5Medivac;
	}


	/**
	 * @param dhs220B5Medivac the dhs220B5Medivac to set
	 */
	public void setDhs220B5Medivac(String dhs220B5Medivac) {
		this.dhs220B5Medivac = dhs220B5Medivac;
	}


	/**
	 * @return the dhs220B5NewIncident
	 */
	public String getDhs220B5NewIncident() {
		return dhs220B5NewIncident;
	}


	/**
	 * @param dhs220B5NewIncident the dhs220B5NewIncident to set
	 */
	public void setDhs220B5NewIncident(String dhs220B5NewIncident) {
		this.dhs220B5NewIncident = dhs220B5NewIncident;
	}


	/**
	 * @return the dhs220B6TfrNbr
	 */
	public String getDhs220B6TfrNbr() {
		return dhs220B6TfrNbr;
	}


	/**
	 * @param dhs220B6TfrNbr the dhs220B6TfrNbr to set
	 */
	public void setDhs220B6TfrNbr(String dhs220B6TfrNbr) {
		this.dhs220B6TfrNbr = dhs220B6TfrNbr;
	}


	/**
	 * @return the dhs220B6Altitude
	 */
	public String getDhs220B6Altitude() {
		return dhs220B6Altitude;
	}


	/**
	 * @param dhs220B6Altitude the dhs220B6Altitude to set
	 */
	public void setDhs220B6Altitude(String dhs220B6Altitude) {
		this.dhs220B6Altitude = dhs220B6Altitude;
	}


	/**
	 * @return the dhs220B6CentralPoint
	 */
	public String getDhs220B6CentralPoint() {
		return dhs220B6CentralPoint;
	}


	/**
	 * @param dhs220B6CentralPoint the dhs220B6CentralPoint to set
	 */
	public void setDhs220B6CentralPoint(String dhs220B6CentralPoint) {
		this.dhs220B6CentralPoint = dhs220B6CentralPoint;
	}


	/**
	 * @return the dhs220B4Remarks
	 */
	public String getDhs220B4Remarks() {
		return dhs220B4Remarks;
	}


	/**
	 * @param dhs220B4Remarks the dhs220B4Remarks to set
	 */
	public void setDhs220B4Remarks(String dhs220B4Remarks) {
		this.dhs220B4Remarks = dhs220B4Remarks;
	}


	/**
	 * @return the attachForm203
	 */
	public Boolean getAttachForm203() {
		return attachForm203;
	}


	/**
	 * @param attachForm203 the attachForm203 to set
	 */
	public void setAttachForm203(Boolean attachForm203) {
		this.attachForm203 = attachForm203;
	}


	/**
	 * @return the attachForm204
	 */
	public Boolean getAttachForm204() {
		return attachForm204;
	}


	/**
	 * @param attachForm204 the attachForm204 to set
	 */
	public void setAttachForm204(Boolean attachForm204) {
		this.attachForm204 = attachForm204;
	}


	/**
	 * @return the attachForm205
	 */
	public Boolean getAttachForm205() {
		return attachForm205;
	}


	/**
	 * @param attachForm205 the attachForm205 to set
	 */
	public void setAttachForm205(Boolean attachForm205) {
		this.attachForm205 = attachForm205;
	}


	/**
	 * @return the attachForm205a
	 */
	public Boolean getAttachForm205a() {
		return attachForm205a;
	}


	/**
	 * @param attachForm205a the attachForm205a to set
	 */
	public void setAttachForm205a(Boolean attachForm205a) {
		this.attachForm205a = attachForm205a;
	}


	/**
	 * @return the attachForm206
	 */
	public Boolean getAttachForm206() {
		return attachForm206;
	}


	/**
	 * @param attachForm206 the attachForm206 to set
	 */
	public void setAttachForm206(Boolean attachForm206) {
		this.attachForm206 = attachForm206;
	}


	/**
	 * @return the attachForm207
	 */
	public Boolean getAttachForm207() {
		return attachForm207;
	}


	/**
	 * @param attachForm207 the attachForm207 to set
	 */
	public void setAttachForm207(Boolean attachForm207) {
		this.attachForm207 = attachForm207;
	}


	/**
	 * @return the attachForm208
	 */
	public Boolean getAttachForm208() {
		return attachForm208;
	}


	/**
	 * @param attachForm208 the attachForm208 to set
	 */
	public void setAttachForm208(Boolean attachForm208) {
		this.attachForm208 = attachForm208;
	}


	/**
	 * @return the attachForm220
	 */
	public Boolean getAttachForm220() {
		return attachForm220;
	}


	/**
	 * @param attachForm220 the attachForm220 to set
	 */
	public void setAttachForm220(Boolean attachForm220) {
		this.attachForm220 = attachForm220;
	}


	/**
	 * @return the attachIncidentMap
	 */
	public Boolean getAttachIncidentMap() {
		return attachIncidentMap;
	}


	/**
	 * @param attachIncidentMap the attachIncidentMap to set
	 */
	public void setAttachIncidentMap(Boolean attachIncidentMap) {
		this.attachIncidentMap = attachIncidentMap;
	}


	/**
	 * @return the attachSafetyMsg
	 */
	public Boolean getAttachSafetyMsg() {
		return attachSafetyMsg;
	}


	/**
	 * @param attachSafetyMsg the attachSafetyMsg to set
	 */
	public void setAttachSafetyMsg(Boolean attachSafetyMsg) {
		this.attachSafetyMsg = attachSafetyMsg;
	}


	/**
	 * @return the attachTrafficPlan
	 */
	public Boolean getAttachTrafficPlan() {
		return attachTrafficPlan;
	}


	/**
	 * @param attachTrafficPlan the attachTrafficPlan to set
	 */
	public void setAttachTrafficPlan(Boolean attachTrafficPlan) {
		this.attachTrafficPlan = attachTrafficPlan;
	}


	/**
	 * @return the attachWeatherForecast
	 */
	public Boolean getAttachWeatherForecast() {
		return attachWeatherForecast;
	}


	/**
	 * @param attachWeatherForecast the attachWeatherForecast to set
	 */
	public void setAttachWeatherForecast(Boolean attachWeatherForecast) {
		this.attachWeatherForecast = attachWeatherForecast;
	}


	/**
	 * @return the form202Type
	 */
	public Boolean getForm202Type() {
		return form202Type;
	}


	/**
	 * @param form202Type the form202Type to set
	 */
	public void setForm202Type(Boolean form202Type) {
		this.form202Type = form202Type;
	}


	/**
	 * @return the form203Type
	 */
	public Boolean getForm203Type() {
		return form203Type;
	}


	/**
	 * @param form203Type the form203Type to set
	 */
	public void setForm203Type(Boolean form203Type) {
		this.form203Type = form203Type;
	}


	/**
	 * @return the form204Type
	 */
	public Boolean getForm204Type() {
		return form204Type;
	}


	/**
	 * @param form204Type the form204Type to set
	 */
	public void setForm204Type(Boolean form204Type) {
		this.form204Type = form204Type;
	}


	/**
	 * @return the form205Type
	 */
	public Boolean getForm205Type() {
		return form205Type;
	}


	/**
	 * @param form205Type the form205Type to set
	 */
	public void setForm205Type(Boolean form205Type) {
		this.form205Type = form205Type;
	}


	/**
	 * @return the form206Type
	 */
	public Boolean getForm206Type() {
		return form206Type;
	}


	/**
	 * @param form206Type the form206Type to set
	 */
	public void setForm206Type(Boolean form206Type) {
		this.form206Type = form206Type;
	}


	/**
	 * @return the form220Type
	 */
	public Boolean getForm220Type() {
		return form220Type;
	}


	/**
	 * @param form220Type the form220Type to set
	 */
	public void setForm220Type(Boolean form220Type) {
		this.form220Type = form220Type;
	}


	/**
	 * @return the iapMedicalAidVos
	 */
	public Collection<IapMedicalAidVo> getIapMedicalAidVos() {
		return iapMedicalAidVos;
	}


	/**
	 * @param iapMedicalAidVos the iapMedicalAidVos to set
	 */
	public void setIapMedicalAidVos(Collection<IapMedicalAidVo> iapMedicalAidVos) {
		this.iapMedicalAidVos = iapMedicalAidVos;
	}


	/**
	 * @return the iapBranchVos
	 */
	public Collection<IapBranchVo> getIapBranchVos() {
		return iapBranchVos;
	}


	/**
	 * @param iapBranchVos the iapBranchVos to set
	 */
	public void setIapBranchVos(Collection<IapBranchVo> iapBranchVos) {
		this.iapBranchVos = iapBranchVos;
	}


	/**
	 * @return the iapFrequencyVos
	 */
	public Collection<IapFrequencyVo> getIapFrequencyVos() {
		return iapFrequencyVos;
	}


	/**
	 * @param iapFrequencyVos the iapFrequencyVos to set
	 */
	public void setIapFrequencyVos(Collection<IapFrequencyVo> iapFrequencyVos) {
		this.iapFrequencyVos = iapFrequencyVos;
	}


	/**
	 * @return the iapAircraftFrequencyVos
	 */
	public Collection<IapAircraftFrequencyVo> getIapAircraftFrequencyVos() {
		return iapAircraftFrequencyVos;
	}


	/**
	 * @param iapAircraftFrequencyVos the iapAircraftFrequencyVos to set
	 */
	public void setIapAircraftFrequencyVos(
			Collection<IapAircraftFrequencyVo> iapAircraftFrequencyVos) {
		this.iapAircraftFrequencyVos = iapAircraftFrequencyVos;
	}


	/**
	 * @return the iapPersonnelVos
	 */
	public Collection<IapPersonnelVo> getIapPersonnelVos() {
		return iapPersonnelVos;
	}


	/**
	 * @param iapPersonnelVos the iapPersonnelVos to set
	 */
	public void setIapPersonnelVos(Collection<IapPersonnelVo> iapPersonnelVos) {
		this.iapPersonnelVos = iapPersonnelVos;
	}


	/**
	 * @return the iapAircraftTaskVos
	 */
	public Collection<IapAircraftTaskVo> getIapAircraftTaskVos() {
		return iapAircraftTaskVos;
	}


	/**
	 * @param iapAircraftTaskVos the iapAircraftTaskVos to set
	 */
	public void setIapAircraftTaskVos(
			Collection<IapAircraftTaskVo> iapAircraftTaskVos) {
		this.iapAircraftTaskVos = iapAircraftTaskVos;
	}


	/**
	 * @return the iapAttachmentVos
	 */
	public Collection<IapAttachmentVo> getIapAttachmentVos() {
		return iapAttachmentVos;
	}


	/**
	 * @param iapAttachmentVos the iapAttachmentVos to set
	 */
	public void setIapAttachmentVos(Collection<IapAttachmentVo> iapAttachmentVos) {
		this.iapAttachmentVos = iapAttachmentVos;
	}


	/**
	 * @return the iapAircraftVos
	 */
	public Collection<IapAircraftVo> getIapAircraftVos() {
		return iapAircraftVos;
	}


	/**
	 * @param iapAircraftVos the iapAircraftVos to set
	 */
	public void setIapAircraftVos(Collection<IapAircraftVo> iapAircraftVos) {
		this.iapAircraftVos = iapAircraftVos;
	}


	/**
	 * @return the iapHospitalVos
	 */
	public Collection<IapHospitalVo> getIapHospitalVos() {
		return iapHospitalVos;
	}


	/**
	 * @param iapHospitalVos the iapHospitalVos to set
	 */
	public void setIapHospitalVos(Collection<IapHospitalVo> iapHospitalVos) {
		this.iapHospitalVos = iapHospitalVos;
	}


	/**
	 * @return the block206PreparedDateString
	 */
	public String getBlock206PreparedDateString() {
		return block206PreparedDateString;
	}


	/**
	 * @param block206PreparedDateString the block206PreparedDateString to set
	 */
	public void setBlock206PreparedDateString(String block206PreparedDateString) {
		this.block206PreparedDateString = block206PreparedDateString;
	}


	/**
	 * @return the block206PreparedTimeString
	 */
	public String getBlock206PreparedTimeString() {
		return block206PreparedTimeString;
	}


	/**
	 * @param block206PreparedTimeString the block206PreparedTimeString to set
	 */
	public void setBlock206PreparedTimeString(String block206PreparedTimeString) {
		this.block206PreparedTimeString = block206PreparedTimeString;
	}


	/**
	 * @return the block205PreparedDate
	 */
	public Date getBlock205PreparedDate() {
		return block205PreparedDate;
	}


	/**
	 * @param block205PreparedDate the block205PreparedDate to set
	 */
	public void setBlock205PreparedDate(Date block205PreparedDate) {
		this.block205PreparedDate = block205PreparedDate;
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


	
}
