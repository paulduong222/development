package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IapForm206ReportData extends BaseReportData {
	private String incidentName;
	private String fromDateTime;
	private String toDateTime;
	private String shift;
	private String preparedBy;
	private String preparedDateTime;
	private String reviewedBy;
	private String reviewedDateTime;
	private String draftFinal;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	private ArrayList<IapForm206AmbulanceData> ambulanceData = new ArrayList<IapForm206AmbulanceData>();
	private JRBeanCollectionDataSource ambulanceServices;
	private ArrayList<IapForm206AirAmbulanceData> airAmbulanceData = new ArrayList<IapForm206AirAmbulanceData>();
	private JRBeanCollectionDataSource airAmbulanceServices;
	private ArrayList<IapForm206HospitalData> hospitalData = new ArrayList<IapForm206HospitalData>();
	private JRBeanCollectionDataSource hospitals;
	private ArrayList<IapForm206AreaLocCapData> areaLocationCapabilitiesData = new ArrayList<IapForm206AreaLocCapData>();
	private JRBeanCollectionDataSource areaLocationCapabilities;
	private ArrayList<IapForm206RemoteCampLocData> remoteCampLocationsData = new ArrayList<IapForm206RemoteCampLocData>();
	private JRBeanCollectionDataSource remoteCampLocations;
	
	public IapForm206ReportData() {
	}

	public static IapForm206ReportData getInstance(IapPlan iapPlan, IapForm206 entity) throws Exception {
		IapForm206ReportData reportData = new IapForm206ReportData();
		
		if(BooleanUtility.isTrue(entity.getIsFormLocked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");

		// Block 1
		reportData.setIncidentName(iapPlan.getIncidentName());
		
		// Block 2
		if(DateUtil.hasValue(iapPlan.getFromDate())){
			String sdate = DateUtil.toDateString(iapPlan.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime = DateUtil.toMilitaryTime(iapPlan.getFromDate());
			reportData.setFromDateTime(sdate + " " + stime);
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getFromDate()));
		}
		if(DateUtil.hasValue(iapPlan.getToDate())){
			String sdate = DateUtil.toDateString(iapPlan.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime = DateUtil.toMilitaryTime(iapPlan.getToDate());
			
			if(stime.equals("2359"))
				stime = "2400";
			
			reportData.setToDateTime(sdate + " " + stime);
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getToDate()));
		}
		if(StringUtility.hasValue(iapPlan.getOperationPeriod())){
			reportData.setShift(iapPlan.getOperationPeriod());
		}

		// Block 3 Ambulances
		if(CollectionUtility.hasValue(entity.getIapMedicalAids())){
			for(IapMedicalAid ma : entity.getIapMedicalAids()){
				if(ma.getType().equals("AMBULANCE")){
					IapForm206AmbulanceData ambData = new IapForm206AmbulanceData();
					ambData.setName(ma.getName());
					ambData.setPhone(ma.getPhone());
					ambData.setEmsFrequency(ma.getEmsFrequency());
					
					String addr=(StringUtility.hasValue(ma.getAddress().getAddressLine1()) ? ma.getAddress().getAddressLine1() + "\n" : "" );
					addr=addr+(StringUtility.hasValue(ma.getAddress().getAddressLine2()) ? ma.getAddress().getAddressLine2() + "\n" : "" );
					addr=addr+(StringUtility.hasValue(ma.getAddress().getCity()) ? ma.getAddress().getCity() + ", " : "" );
					if(null != ma.getAddress().getCountrySubdivision()){
						addr=addr+(StringUtility.hasValue(ma.getAddress().getCountrySubdivision().getAbbreviation()) ? ma.getAddress().getCountrySubdivision().getAbbreviation() + " " : " " );
					}else{
						addr=addr+" ";
					}
					
					addr=addr+(StringUtility.hasValue(ma.getAddress().getPostalCode()) ? ma.getAddress().getPostalCode() + " " : "" );
					
					ambData.setAddress(addr);
					
					if (BooleanUtility.isTrue(ma.getIsBlankLine().getValue())) {
						ambData.setAlsYes("");
						ambData.setAlsNo("");
					}
					else {
						if(BooleanUtility.isTrue(ma.getLifeSupport().getValue())){
							ambData.setAlsYes("X");
							ambData.setAlsNo("");
						}else{
							ambData.setAlsYes("");
							ambData.setAlsNo("X");
						}
					}
					
					
					reportData.getAmbulanceData().add(ambData);
				}
			}
		}
		
		if(reportData.getAmbulanceData().size()<1){
			IapForm206AmbulanceData ambData = new IapForm206AmbulanceData();
			reportData.getAmbulanceData().add(ambData);
		}
		
		// Block 4 Air Ambulances
		if(CollectionUtility.hasValue(entity.getIapMedicalAids())){
			for(IapMedicalAid ma : entity.getIapMedicalAids()){
				if(ma.getType().equals("AIRAMBULANCE")){
					IapForm206AirAmbulanceData airAmbData = new IapForm206AirAmbulanceData();
					airAmbData.setName(ma.getName());
					airAmbData.setPhone(ma.getPhone());
					airAmbData.setAirType(ma.getAirType());
					airAmbData.setCapability(ma.getCapability());
					reportData.getAirAmbulanceData().add(airAmbData);
				}
			}
		}
		
		if(reportData.getAirAmbulanceData().size()<1){
			IapForm206AirAmbulanceData airAmbData = new IapForm206AirAmbulanceData();
			reportData.getAirAmbulanceData().add(airAmbData);
		}

		// Block 5 Hospitals
		if(CollectionUtility.hasValue(entity.getIapHospitals())){
			for(IapHospital h : entity.getIapHospitals()){
				IapForm206HospitalData hData = new IapForm206HospitalData();
				hData.setName(h.getName());
				hData.setPhone(h.getPhone());
				hData.setLat(h.getLatitude());
				hData.setLongitude(h.getLongitude());
				hData.setVhf(h.getVhf());
				hData.setAirTravelTime(h.getAirTravelTime());
				hData.setGroundTravelTime(h.getGroundTravelTime());
				hData.setLevelOfCare(h.getLevelOfCare());
				
				String addr=(StringUtility.hasValue(h.getAddress().getAddressLine1()) ? h.getAddress().getAddressLine1() + "\n" : "" );
				addr=addr+(StringUtility.hasValue(h.getAddress().getAddressLine2()) ? h.getAddress().getAddressLine2() + "\n" : "" );
				addr=addr+(StringUtility.hasValue(h.getAddress().getCity()) ? h.getAddress().getCity() + ", " : "" );
				if(null != h.getAddress().getCountrySubdivision()){
					addr=addr+(StringUtility.hasValue(h.getAddress().getCountrySubdivision().getAbbreviation()) ? h.getAddress().getCountrySubdivision().getAbbreviation() + " " : "" );
				}
				addr=addr+(StringUtility.hasValue(h.getAddress().getPostalCode()) ? h.getAddress().getPostalCode() + " " : "" );
				hData.setAddress(addr);
					
				if (BooleanUtility.isTrue(h.getIsBlankLine().getValue())) {
					hData.setHelipadYes("");
					hData.setHelipadNo("");
				}
				else {
					if(BooleanUtility.isTrue(h.getHelipad().getValue())){
						hData.setHelipadYes("X");
						hData.setHelipadNo("");
					}else{
						hData.setHelipadYes("");
						hData.setHelipadNo("X");
					}
				}
				
					
				reportData.getHospitalData().add(hData);
			}
		}
		
		if(reportData.getHospitalData().size()<1){
			IapForm206HospitalData hData = new IapForm206HospitalData();
			reportData.getHospitalData().add(hData);
		}

		// Block 6 Area Location Capabilities
		if(CollectionUtility.hasValue(entity.getIapAreaLocationCapabilities())){
			for(IapAreaLocationCapability alc : entity.getIapAreaLocationCapabilities()){
				IapForm206AreaLocCapData alcData = new IapForm206AreaLocCapData();
				//alcData.setDivisionName(alc.getDivisionName());
				//alcData.setGroupName(alc.getGroupName());
				//alcData.setBranchName(alc.getBranchName());
				
				//String divBranchGroup = (StringUtility.hasValue(alc.getDivisionName()) ? alc.getDivisionName()+"\n" : "-"+"\n" );
				String divBranchGroup = (StringUtility.hasValue(alc.getBranchName()) ? alc.getBranchName()+"\n" : "" );
				divBranchGroup = divBranchGroup + (StringUtility.hasValue(alc.getDivisionName()) ? alc.getDivisionName() : "" );
				
				alcData.setDivisionName(divBranchGroup);
				
				alcData.setEmergencyChannel(alc.getEmergencyChannel());
				alcData.setAvailEquipment(alc.getAvailableEquipment());
				if(StringUtility.hasValue(alc.getEmsResponders())){
					String s=alc.getEmsResponders();
					if(StringUtility.hasValue(alc.getCapability())){
						s=s+"/"+alc.getCapability();
					}
					alcData.setEmsResponders(s);
				}else if(StringUtility.hasValue(alc.getCapability())){
					String s=alc.getCapability();
					alcData.setEmsResponders(s);
				}
				//alcData.setCapability(alc.getCapability());
				alcData.setGroundEta(alc.getAmbGroundEta());
				alcData.setAirEta(alc.getAmbAirEta());
				alcData.setHeliLat(alc.getLatitude());
				alcData.setHeliLong(alc.getLongitude());
				
				reportData.getAreaLocationCapabilitiesData().add(alcData);
			}
		}
		
		if(reportData.getAreaLocationCapabilitiesData().size()<1){
			IapForm206AreaLocCapData alcData = new IapForm206AreaLocCapData();
			reportData.getAreaLocationCapabilitiesData().add(alcData);
		}
		
		// Block 7 Remote Camp Locations
		if(CollectionUtility.hasValue(entity.getIapRemoteCampLocations())){
			for(IapRemoteCampLocations rcl : entity.getIapRemoteCampLocations()){
				IapForm206RemoteCampLocData rclData = new IapForm206RemoteCampLocData();
				rclData.setEmergencyChannel(rcl.getEmergencyChannel());
				rclData.setAvailEquipment(rcl.getAvailableEquipment());
				//rclData.setEmsResponders(rcl.getEmsResponders());
				String s="";
				if(StringUtility.hasValue(rcl.getEmsResponders())){
					s=rcl.getEmsResponders();
					if(StringUtility.hasValue(rcl.getCapability())){
						s=s+"/"+rcl.getCapability();
					}
				}else if(StringUtility.hasValue(rcl.getCapability())){
					s=rcl.getCapability();
				}
				rclData.setEmsResponders(s);
				rclData.setCapability(s);
				rclData.setGroundEta(rcl.getAmbGroundEta());
				rclData.setAirEta(rcl.getAmbAirEta());
				rclData.setHeliLat(rcl.getLatitude());
				rclData.setHeliLong(rcl.getLongitude());
				rclData.setName(rcl.getName());
				rclData.setLocation(rcl.getLocation());
				rclData.setPointOfContact(rcl.getPointOfContact());
				
				reportData.getRemoteCampLocationsData().add(rclData);
			}
		}
		
		if(reportData.getRemoteCampLocationsData().size()<1){
			IapForm206RemoteCampLocData rclData = new IapForm206RemoteCampLocData();
			rclData.setEmsResponders("");
			rclData.setCapability("");
			reportData.getRemoteCampLocationsData().add(rclData);
		}
		
		// Block 12
		reportData.setPreparedBy(entity.getPreparedBy());
		reportData.setReviewedBy(entity.getReviewedBy());
		if(DateUtil.hasValue(entity.getPreparedDate())){
			String dt=DateUtil.toDateString(entity.getPreparedDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String time=DateUtil.toMilitaryTime(entity.getPreparedDate());
			reportData.setPreparedDateTime(dt+" "+time);
		}
		if(DateUtil.hasValue(entity.getReviewedDate())){
			String dt=DateUtil.toDateString(entity.getReviewedDate(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String time=DateUtil.toMilitaryTime(entity.getReviewedDate());
			reportData.setReviewedDateTime(dt+" "+time);
		}
		
		return reportData;
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
	 * @return the fromDateTime
	 */
	public String getFromDateTime() {
		return fromDateTime;
	}

	/**
	 * @param fromDateTime the fromDateTime to set
	 */
	public void setFromDateTime(String fromDateTime) {
		this.fromDateTime = fromDateTime;
	}

	/**
	 * @return the toDateTime
	 */
	public String getToDateTime() {
		return toDateTime;
	}

	/**
	 * @param toDateTime the toDateTime to set
	 */
	public void setToDateTime(String toDateTime) {
		this.toDateTime = toDateTime;
	}

	/**
	 * @return the ambulanceServices
	 */
	public JRBeanCollectionDataSource getAmbulanceServices() {
		return new JRBeanCollectionDataSource(this.ambulanceData);
	}

	/**
	 * @param ambulanceServices the ambulanceServices to set
	 */
	public void setAmbulanceServices(JRBeanCollectionDataSource ambulanceServices) {
		this.ambulanceServices = ambulanceServices;
	}

	/**
	 * @return the ambulanceData
	 */
	public ArrayList<IapForm206AmbulanceData> getAmbulanceData() {
		return ambulanceData;
	}

	/**
	 * @param ambulanceData the ambulanceData to set
	 */
	public void setAmbulanceData(ArrayList<IapForm206AmbulanceData> ambulanceData) {
		this.ambulanceData = ambulanceData;
	}

	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * @return the airAmbulanceData
	 */
	public ArrayList<IapForm206AirAmbulanceData> getAirAmbulanceData() {
		return airAmbulanceData;
	}

	/**
	 * @param airAmbulanceData the airAmbulanceData to set
	 */
	public void setAirAmbulanceData(
			ArrayList<IapForm206AirAmbulanceData> airAmbulanceData) {
		this.airAmbulanceData = airAmbulanceData;
	}

	/**
	 * @return the airAmbulanceServices
	 */
	public JRBeanCollectionDataSource getAirAmbulanceServices() {
		return new JRBeanCollectionDataSource(this.airAmbulanceData);
	}

	/**
	 * @param airAmbulanceServices the airAmbulanceServices to set
	 */
	public void setAirAmbulanceServices(
			JRBeanCollectionDataSource airAmbulanceServices) {
		this.airAmbulanceServices = airAmbulanceServices;
	}

	/**
	 * @return the hospitalData
	 */
	public ArrayList<IapForm206HospitalData> getHospitalData() {
		return hospitalData;
	}

	/**
	 * @param hospitalData the hospitalData to set
	 */
	public void setHospitalData(ArrayList<IapForm206HospitalData> hospitalData) {
		this.hospitalData = hospitalData;
	}

	/**
	 * @return the hospitals
	 */
	public JRBeanCollectionDataSource getHospitals() {
		return new JRBeanCollectionDataSource(this.hospitalData);
	}

	/**
	 * @param hospitals the hospitals to set
	 */
	public void setHospitals(JRBeanCollectionDataSource hospitals) {
		this.hospitals = hospitals;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedDateTime
	 */
	public String getPreparedDateTime() {
		return preparedDateTime;
	}

	/**
	 * @param preparedDateTime the preparedDateTime to set
	 */
	public void setPreparedDateTime(String preparedDateTime) {
		this.preparedDateTime = preparedDateTime;
	}

	/**
	 * @return the reviewedBy
	 */
	public String getReviewedBy() {
		return reviewedBy;
	}

	/**
	 * @param reviewedBy the reviewedBy to set
	 */
	public void setReviewedBy(String reviewedBy) {
		this.reviewedBy = reviewedBy;
	}

	/**
	 * @return the reviewedDateTime
	 */
	public String getReviewedDateTime() {
		return reviewedDateTime;
	}

	/**
	 * @param reviewedDateTime the reviewedDateTime to set
	 */
	public void setReviewedDateTime(String reviewedDateTime) {
		this.reviewedDateTime = reviewedDateTime;
	}

	/**
	 * @return the areaLocationCapabilitiesData
	 */
	public ArrayList<IapForm206AreaLocCapData> getAreaLocationCapabilitiesData() {
		return areaLocationCapabilitiesData;
	}

	/**
	 * @param areaLocationCapabilitiesData the areaLocationCapabilitiesData to set
	 */
	public void setAreaLocationCapabilitiesData(
			ArrayList<IapForm206AreaLocCapData> areaLocationCapabilitiesData) {
		this.areaLocationCapabilitiesData = areaLocationCapabilitiesData;
	}

	/**
	 * @return the areaLocationCapabilities
	 */
	public JRBeanCollectionDataSource getAreaLocationCapabilities() {
		return new JRBeanCollectionDataSource(this.areaLocationCapabilitiesData);
	}

	/**
	 * @param areaLocationCapabilities the areaLocationCapabilities to set
	 */
	public void setAreaLocationCapabilities(
			JRBeanCollectionDataSource areaLocationCapabilities) {
		this.areaLocationCapabilities = areaLocationCapabilities;
	}

	/**
	 * @return the remoteCampLocationsData
	 */
	public ArrayList<IapForm206RemoteCampLocData> getRemoteCampLocationsData() {
		return remoteCampLocationsData;
	}

	/**
	 * @param remoteCampLocationsData the remoteCampLocationsData to set
	 */
	public void setRemoteCampLocationsData(
			ArrayList<IapForm206RemoteCampLocData> remoteCampLocationsData) {
		this.remoteCampLocationsData = remoteCampLocationsData;
	}

	/**
	 * @return the remoteCampLocations
	 */
	public JRBeanCollectionDataSource getRemoteCampLocations() {
		return new JRBeanCollectionDataSource(this.remoteCampLocationsData);
	}

	/**
	 * @param remoteCampLocations the remoteCampLocations to set
	 */
	public void setRemoteCampLocations(
			JRBeanCollectionDataSource remoteCampLocations) {
		this.remoteCampLocations = remoteCampLocations;
	}

	/**
	 * @return the draftFinal
	 */
	public String getDraftFinal() {
		return draftFinal;
	}

	/**
	 * @param draftFinal the draftFinal to set
	 */
	public void setDraftFinal(String draftFinal) {
		this.draftFinal = draftFinal;
	}

	/**
	 * @return the fromDayOfWeek
	 */
	public String getFromDayOfWeek() {
		return fromDayOfWeek;
	}

	/**
	 * @param fromDayOfWeek the fromDayOfWeek to set
	 */
	public void setFromDayOfWeek(String fromDayOfWeek) {
		this.fromDayOfWeek = fromDayOfWeek;
	}

	/**
	 * @return the toDayOfWeek
	 */
	public String getToDayOfWeek() {
		return toDayOfWeek;
	}

	/**
	 * @param toDayOfWeek the toDayOfWeek to set
	 */
	public void setToDayOfWeek(String toDayOfWeek) {
		this.toDayOfWeek = toDayOfWeek;
	}

	
}
