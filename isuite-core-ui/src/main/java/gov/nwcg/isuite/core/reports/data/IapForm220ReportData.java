package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapAircraftFrequency;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class IapForm220ReportData {
	private String incidentName;
	private String field;
	private Date fromDate;
	private Date toDate;
	private String fromTime;
	private String toTime;
	private String sunrise;
	private String sunset;
	private String medivac;
	private String newIncident;
	private String altitude;
	private String centerPoint;
	private String remarks;
	private String preparedBy;
	private String preparedByPosition;
	private String preparedByDateTime;
	private String personnelPos1;
	private String personnelPos2;
	private String personnelPos3;
	private String personnelPos4;
	private String personnelPos5;
	private String personnelPos6;
	private String personnelPos7;
	private String personnelPos8;
	private String personnelPos9;
	private String personnelPos10;
	private String personnelPos11;
	private String personnelName1;
	private String personnelName2;
	private String personnelName3;
	private String personnelName4;
	private String personnelName5;
	private String personnelName6;
	private String personnelName7;
	private String personnelName8;
	private String personnelName9;
	private String personnelName10;
	private String personnelName11;
	private String personnelPhone1;
	private String personnelPhone2;
	private String personnelPhone3;
	private String personnelPhone4;
	private String personnelPhone5;
	private String personnelPhone6;
	private String personnelPhone7;
	private String personnelPhone8;
	private String personnelPhone9;
	private String personnelPhone10;
	private String personnelPhone11;
	private String frequencyName1;
	private String frequencyName2;
	private String frequencyName3;
	private String frequencyName4;
	private String frequencyName5;
	private String frequencyName6;
	private String frequencyName7;
	private String frequencyName8;
	private String frequencyName9;
	private String frequencyName10;
	private String frequencyName11;
	private String frequencyName12;
	private String frequencyAm1;
	private String frequencyAm2;
	private String frequencyAm3;
	private String frequencyAm4;
	private String frequencyAm5;
	private String frequencyAm6;
	private String frequencyAm7;
	private String frequencyAm8;
	private String frequencyAm9;
	private String frequencyAm10;
	private String frequencyAm11;
	private String frequencyAm12;
	private String frequencyAmTone1;
	private String frequencyAmTone2;
	private String frequencyAmTone3;
	private String frequencyAmTone4;
	private String frequencyAmTone5;
	private String frequencyAmTone6;
	private String frequencyAmTone7;
	private String frequencyAmTone8;
	private String frequencyAmTone9;
	private String frequencyAmTone10;
	private String frequencyAmTone11;
	private String frequencyAmTone12;
	private String frequencyFm1;
	private String frequencyFm2;
	private String frequencyFm3;
	private String frequencyFm4;
	private String frequencyFm5;
	private String frequencyFm6;
	private String frequencyFm7;
	private String frequencyFm8;
	private String frequencyFm9;
	private String frequencyFm10;
	private String frequencyFm11;
	private String frequencyFm12;
	private String frequencyFmTone1;
	private String frequencyFmTone2;
	private String frequencyFmTone3;
	private String frequencyFmTone4;
	private String frequencyFmTone5;
	private String frequencyFmTone6;
	private String frequencyFmTone7;
	private String frequencyFmTone8;
	private String frequencyFmTone9;
	private String frequencyFmTone10;
	private String frequencyFmTone11;
	private String frequencyFmTone12;
	private String fixedWingName1;
	private String fixedWingName2;
	private String fixedWingName3;
	private String fixedWingName4;
	private String fixedWingName5;
	private String fixedWingName6;
	private String fixedWingName7;
	private String fixedWingName8;
	private String fixedWingName9;
	private String fixedWingName10;
	private String fixedWingName11;
	private String fixedWingName12;
	private String fixedWingType1;
	private String fixedWingType2;
	private String fixedWingType3;
	private String fixedWingType4;
	private String fixedWingType5;
	private String fixedWingType6;
	private String fixedWingType7;
	private String fixedWingType8;
	private String fixedWingType9;
	private String fixedWingType10;
	private String fixedWingType11;
	private String fixedWingType12;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	
	public IapForm220ReportData() {
	}

	public static IapForm220ReportData getInstance(IapPlan iapPlan, IapForm220 entity) throws Exception {
		IapForm220ReportData reportData = new IapForm220ReportData();

		if(BooleanUtility.isTrue(entity.getIsFormLocked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");
		
		reportData.setOperationalPeriod(iapPlan.getOperationPeriod());

		// Block 1
		reportData.setIncidentName(iapPlan.getIncidentName());
		
		// Block 2
		reportData.setFromDate(iapPlan.getFromDate());
		if(DateUtil.hasValue(iapPlan.getFromDate())){
			reportData.setFromTime(DateUtil.toMilitaryTime(iapPlan.getFromDate()));
			reportData.setFromDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getFromDate()));
		}
		reportData.setToDate(iapPlan.getToDate());
		if(DateUtil.hasValue(iapPlan.getToDate())){
			reportData.setToTime(DateUtil.toMilitaryTime(iapPlan.getToDate()));
			if(reportData.getToTime().equals("2359"))
				reportData.setToTime("2400");
			reportData.setToDayOfWeek(DateUtil.getDayOfWeekAbbrev(iapPlan.getToDate()));
		}

		// Block 3 
		if(StringUtility.hasValue(entity.getSunrise()))
			reportData.setSunrise(entity.getSunrise());
		if(StringUtility.hasValue(entity.getSunset()))
			reportData.setSunset(entity.getSunset());
		
		// Block 4
		if(StringUtility.hasValue(entity.getRemarks()))
			reportData.setRemarks(entity.getRemarks());
		
		// Block 5
		if(StringUtility.hasValue(entity.getMedivac()))
			reportData.setMedivac(entity.getMedivac());
		if(StringUtility.hasValue(entity.getNewIncident()))
			reportData.setNewIncident(entity.getNewIncident());
		
		// Block 6
		if(StringUtility.hasValue(entity.getAltitude()))
			reportData.setAltitude(entity.getAltitude());
		if(StringUtility.hasValue(entity.getCentralPoint()))
			reportData.setCenterPoint(entity.getCentralPoint());
		
		// Block 7
		if(CollectionUtility.hasValue(entity.getIapPersonnels())){
			int idx=1;
			for(IapPersonnel ip : entity.getIapPersonnels()){
				switch(idx){
					case 1:
						reportData.setPersonnelPos1(ip.getRole());
						reportData.setPersonnelName1(ip.getName());
						reportData.setPersonnelPhone1(ip.getPhone());
						break;
					case 2:
						reportData.setPersonnelPos2(ip.getRole());
						reportData.setPersonnelName2(ip.getName());
						reportData.setPersonnelPhone2(ip.getPhone());
						break;
					case 3:
						reportData.setPersonnelPos3(ip.getRole());
						reportData.setPersonnelName3(ip.getName());
						reportData.setPersonnelPhone3(ip.getPhone());
						break;
					case 4:
						reportData.setPersonnelPos4(ip.getRole());
						reportData.setPersonnelName4(ip.getName());
						reportData.setPersonnelPhone4(ip.getPhone());
						break;
					case 5:
						reportData.setPersonnelPos5(ip.getRole());
						reportData.setPersonnelName5(ip.getName());
						reportData.setPersonnelPhone5(ip.getPhone());
						break;
					case 6:
						reportData.setPersonnelPos6(ip.getRole());
						reportData.setPersonnelName6(ip.getName());
						reportData.setPersonnelPhone6(ip.getPhone());
						break;
					case 7:
						reportData.setPersonnelPos7(ip.getRole());
						reportData.setPersonnelName7(ip.getName());
						reportData.setPersonnelPhone7(ip.getPhone());
						break;
					case 8:
						reportData.setPersonnelPos8(ip.getRole());
						reportData.setPersonnelName8(ip.getName());
						reportData.setPersonnelPhone8(ip.getPhone());
						break;
					case 9:
						reportData.setPersonnelPos9(ip.getRole());
						reportData.setPersonnelName9(ip.getName());
						reportData.setPersonnelPhone9(ip.getPhone());
						break;
					case 10:
						reportData.setPersonnelPos10(ip.getRole());
						reportData.setPersonnelName10(ip.getName());
						reportData.setPersonnelPhone10(ip.getPhone());
						break;
					case 11:
						reportData.setPersonnelPos11(ip.getRole());
						reportData.setPersonnelName11(ip.getName());
						reportData.setPersonnelPhone11(ip.getPhone());
						break;
				}
				idx++;
			}
		}
		
		// Block 8
		if(CollectionUtility.hasValue(entity.getIapAircraftFrequencies())){
			int idx=1;
			for(IapAircraftFrequency af : entity.getIapAircraftFrequencies()){
				switch(idx){
					case 1:
						reportData.setFrequencyName1(af.getFrequency());
						reportData.setFrequencyAm1(af.getAmRxTx());
						reportData.setFrequencyAmTone1(af.getAmTone());
						reportData.setFrequencyFm1(af.getFmRxTx());
						reportData.setFrequencyFmTone1(af.getFmTone());
						break;
					case 2:
						reportData.setFrequencyName2(af.getFrequency());
						reportData.setFrequencyAm2(af.getAmRxTx());
						reportData.setFrequencyAmTone2(af.getAmTone());
						reportData.setFrequencyFm2(af.getFmRxTx());
						reportData.setFrequencyFmTone2(af.getFmTone());
						break;
					case 3:
						reportData.setFrequencyName3(af.getFrequency());
						reportData.setFrequencyAm3(af.getAmRxTx());
						reportData.setFrequencyAmTone3(af.getAmTone());
						reportData.setFrequencyFm3(af.getFmRxTx());
						reportData.setFrequencyFmTone3(af.getFmTone());
						break;
					case 4:
						reportData.setFrequencyName4(af.getFrequency());
						reportData.setFrequencyAm4(af.getAmRxTx());
						reportData.setFrequencyAmTone4(af.getAmTone());
						reportData.setFrequencyFm4(af.getFmRxTx());
						reportData.setFrequencyFmTone4(af.getFmTone());
						break;
					case 5:
						reportData.setFrequencyName5(af.getFrequency());
						reportData.setFrequencyAm5(af.getAmRxTx());
						reportData.setFrequencyAmTone5(af.getAmTone());
						reportData.setFrequencyFm5(af.getFmRxTx());
						reportData.setFrequencyFmTone5(af.getFmTone());
						break;
					case 6:
						reportData.setFrequencyName6(af.getFrequency());
						reportData.setFrequencyAm6(af.getAmRxTx());
						reportData.setFrequencyAmTone6(af.getAmTone());
						reportData.setFrequencyFm6(af.getFmRxTx());
						reportData.setFrequencyFmTone6(af.getFmTone());
						break;
					case 7:
						reportData.setFrequencyName7(af.getFrequency());
						reportData.setFrequencyAm7(af.getAmRxTx());
						reportData.setFrequencyAmTone7(af.getAmTone());
						reportData.setFrequencyFm7(af.getFmRxTx());
						reportData.setFrequencyFmTone7(af.getFmTone());
						break;
					case 8:
						reportData.setFrequencyName8(af.getFrequency());
						reportData.setFrequencyAm8(af.getAmRxTx());
						reportData.setFrequencyAmTone8(af.getAmTone());
						reportData.setFrequencyFm8(af.getFmRxTx());
						reportData.setFrequencyFmTone8(af.getFmTone());
						break;
					case 9:
						reportData.setFrequencyName9(af.getFrequency());
						reportData.setFrequencyAm9(af.getAmRxTx());
						reportData.setFrequencyAmTone9(af.getAmTone());
						reportData.setFrequencyFm9(af.getFmRxTx());
						reportData.setFrequencyFmTone9(af.getFmTone());
						break;
					case 10:
						reportData.setFrequencyName10(af.getFrequency());
						reportData.setFrequencyAm10(af.getAmRxTx());
						reportData.setFrequencyAmTone10(af.getAmTone());
						reportData.setFrequencyFm10(af.getFmRxTx());
						reportData.setFrequencyFmTone10(af.getFmTone());
						break;
					case 11:
						reportData.setFrequencyName11(af.getFrequency());
						reportData.setFrequencyAm11(af.getAmRxTx());
						reportData.setFrequencyAmTone11(af.getAmTone());
						reportData.setFrequencyFm11(af.getFmRxTx());
						reportData.setFrequencyFmTone11(af.getFmTone());
						break;
					case 12:
						reportData.setFrequencyName12(af.getFrequency());
						reportData.setFrequencyAm12(af.getAmRxTx());
						reportData.setFrequencyAmTone12(af.getAmTone());
						reportData.setFrequencyFm12(af.getFmRxTx());
						reportData.setFrequencyFmTone12(af.getFmTone());
						break;
				}
				idx++;
			}
		}

		// Block 9
		if(CollectionUtility.hasValue(entity.getIapAircrafts())){
			int idx=1;
			for(IapAircraft a : entity.getIapAircrafts()){
				int posNum=1;
				if(StringUtility.hasValue(a.getWingType()) && a.getWingType().equals("FIXED")){
					if(IntegerUtility.hasValue(a.getPositionNum()))
						posNum=a.getPositionNum().intValue();
					
					switch(posNum){
						case 1:
							reportData.setFixedWingName1(a.getAircraft());
							reportData.setFixedWingType1(a.getType());
							break;
						case 2:
							reportData.setFixedWingName2(a.getAircraft());
							reportData.setFixedWingType2(a.getType());
							break;
						case 3:
							reportData.setFixedWingName3(a.getAircraft());
							reportData.setFixedWingType3(a.getType());
							break;
						case 4:
							reportData.setFixedWingName4(a.getAircraft());
							reportData.setFixedWingType4(a.getType());
							break;
						case 5:
							reportData.setFixedWingName5(a.getAircraft());
							reportData.setFixedWingType5(a.getType());
							break;
						case 6:
							reportData.setFixedWingName6(a.getAircraft());
							reportData.setFixedWingType6(a.getType());
							break;
						case 7:
							reportData.setFixedWingName7(a.getAircraft());
							reportData.setFixedWingType7(a.getType());
							break;
						case 8:
							reportData.setFixedWingName8(a.getAircraft());
							reportData.setFixedWingType8(a.getType());
							break;
						case 9:
							reportData.setFixedWingName9(a.getAircraft());
							reportData.setFixedWingType9(a.getType());
							break;
						case 10:
							reportData.setFixedWingName10(a.getAircraft());
							reportData.setFixedWingType10(a.getType());
							break;
						case 11:
							reportData.setFixedWingName11(a.getAircraft());
							reportData.setFixedWingType11(a.getType());
							break;
						case 12:
							reportData.setFixedWingName12(a.getAircraft());
							reportData.setFixedWingType12(a.getType());
							break;
					}
					
				}
				idx++;
			}
		}

		// Block 11
		reportData.setPreparedBy(entity.getPreparedBy());
		reportData.setPreparedByPosition(entity.getPreparedByPosition());
		if(DateUtil.hasValue(entity.getPreparedDate())){
			String sdate=DateUtil.toDateString(entity.getPreparedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime=DateUtil.toMilitaryTime(entity.getPreparedDate());
			reportData.setPreparedByDateTime(sdate+" " + stime);
		}
		
		return reportData;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the fromTime
	 */
	public String getFromTime() {
		return fromTime;
	}

	/**
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * @return the toTime
	 */
	public String getToTime() {
		return toTime;
	}

	/**
	 * @param toTime the toTime to set
	 */
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	/**
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	/**
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	/**
	 * @return the medivac
	 */
	public String getMedivac() {
		return medivac;
	}

	/**
	 * @param medivac the medivac to set
	 */
	public void setMedivac(String medivac) {
		this.medivac = medivac;
	}

	/**
	 * @return the newIncident
	 */
	public String getNewIncident() {
		return newIncident;
	}

	/**
	 * @param newIncident the newIncident to set
	 */
	public void setNewIncident(String newIncident) {
		this.newIncident = newIncident;
	}

	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the centerPoint
	 */
	public String getCenterPoint() {
		return centerPoint;
	}

	/**
	 * @param centerPoint the centerPoint to set
	 */
	public void setCenterPoint(String centerPoint) {
		this.centerPoint = centerPoint;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the personnelPos1
	 */
	public String getPersonnelPos1() {
		return personnelPos1;
	}

	/**
	 * @param personnelPos1 the personnelPos1 to set
	 */
	public void setPersonnelPos1(String personnelPos1) {
		this.personnelPos1 = personnelPos1;
	}

	/**
	 * @return the personnelPos2
	 */
	public String getPersonnelPos2() {
		return personnelPos2;
	}

	/**
	 * @param personnelPos2 the personnelPos2 to set
	 */
	public void setPersonnelPos2(String personnelPos2) {
		this.personnelPos2 = personnelPos2;
	}

	/**
	 * @return the personnelPos3
	 */
	public String getPersonnelPos3() {
		return personnelPos3;
	}

	/**
	 * @param personnelPos3 the personnelPos3 to set
	 */
	public void setPersonnelPos3(String personnelPos3) {
		this.personnelPos3 = personnelPos3;
	}

	/**
	 * @return the personnelPos4
	 */
	public String getPersonnelPos4() {
		return personnelPos4;
	}

	/**
	 * @param personnelPos4 the personnelPos4 to set
	 */
	public void setPersonnelPos4(String personnelPos4) {
		this.personnelPos4 = personnelPos4;
	}

	/**
	 * @return the personnelPos5
	 */
	public String getPersonnelPos5() {
		return personnelPos5;
	}

	/**
	 * @param personnelPos5 the personnelPos5 to set
	 */
	public void setPersonnelPos5(String personnelPos5) {
		this.personnelPos5 = personnelPos5;
	}

	/**
	 * @return the personnelPos6
	 */
	public String getPersonnelPos6() {
		return personnelPos6;
	}

	/**
	 * @param personnelPos6 the personnelPos6 to set
	 */
	public void setPersonnelPos6(String personnelPos6) {
		this.personnelPos6 = personnelPos6;
	}

	/**
	 * @return the personnelPos7
	 */
	public String getPersonnelPos7() {
		return personnelPos7;
	}

	/**
	 * @param personnelPos7 the personnelPos7 to set
	 */
	public void setPersonnelPos7(String personnelPos7) {
		this.personnelPos7 = personnelPos7;
	}

	/**
	 * @return the personnelPos8
	 */
	public String getPersonnelPos8() {
		return personnelPos8;
	}

	/**
	 * @param personnelPos8 the personnelPos8 to set
	 */
	public void setPersonnelPos8(String personnelPos8) {
		this.personnelPos8 = personnelPos8;
	}

	/**
	 * @return the personnelName1
	 */
	public String getPersonnelName1() {
		return personnelName1;
	}

	/**
	 * @param personnelName1 the personnelName1 to set
	 */
	public void setPersonnelName1(String personnelName1) {
		this.personnelName1 = personnelName1;
	}

	/**
	 * @return the personnelName2
	 */
	public String getPersonnelName2() {
		return personnelName2;
	}

	/**
	 * @param personnelName2 the personnelName2 to set
	 */
	public void setPersonnelName2(String personnelName2) {
		this.personnelName2 = personnelName2;
	}

	/**
	 * @return the personnelName3
	 */
	public String getPersonnelName3() {
		return personnelName3;
	}

	/**
	 * @param personnelName3 the personnelName3 to set
	 */
	public void setPersonnelName3(String personnelName3) {
		this.personnelName3 = personnelName3;
	}

	/**
	 * @return the personnelName4
	 */
	public String getPersonnelName4() {
		return personnelName4;
	}

	/**
	 * @param personnelName4 the personnelName4 to set
	 */
	public void setPersonnelName4(String personnelName4) {
		this.personnelName4 = personnelName4;
	}

	/**
	 * @return the personnelName5
	 */
	public String getPersonnelName5() {
		return personnelName5;
	}

	/**
	 * @param personnelName5 the personnelName5 to set
	 */
	public void setPersonnelName5(String personnelName5) {
		this.personnelName5 = personnelName5;
	}

	/**
	 * @return the personnelName6
	 */
	public String getPersonnelName6() {
		return personnelName6;
	}

	/**
	 * @param personnelName6 the personnelName6 to set
	 */
	public void setPersonnelName6(String personnelName6) {
		this.personnelName6 = personnelName6;
	}

	/**
	 * @return the personnelName7
	 */
	public String getPersonnelName7() {
		return personnelName7;
	}

	/**
	 * @param personnelName7 the personnelName7 to set
	 */
	public void setPersonnelName7(String personnelName7) {
		this.personnelName7 = personnelName7;
	}

	/**
	 * @return the personnelName8
	 */
	public String getPersonnelName8() {
		return personnelName8;
	}

	/**
	 * @param personnelName8 the personnelName8 to set
	 */
	public void setPersonnelName8(String personnelName8) {
		this.personnelName8 = personnelName8;
	}

	/**
	 * @return the personnelPhone1
	 */
	public String getPersonnelPhone1() {
		return personnelPhone1;
	}

	/**
	 * @param personnelPhone1 the personnelPhone1 to set
	 */
	public void setPersonnelPhone1(String personnelPhone1) {
		this.personnelPhone1 = personnelPhone1;
	}

	/**
	 * @return the personnelPhone2
	 */
	public String getPersonnelPhone2() {
		return personnelPhone2;
	}

	/**
	 * @param personnelPhone2 the personnelPhone2 to set
	 */
	public void setPersonnelPhone2(String personnelPhone2) {
		this.personnelPhone2 = personnelPhone2;
	}

	/**
	 * @return the personnelPhone3
	 */
	public String getPersonnelPhone3() {
		return personnelPhone3;
	}

	/**
	 * @param personnelPhone3 the personnelPhone3 to set
	 */
	public void setPersonnelPhone3(String personnelPhone3) {
		this.personnelPhone3 = personnelPhone3;
	}

	/**
	 * @return the personnelPhone4
	 */
	public String getPersonnelPhone4() {
		return personnelPhone4;
	}

	/**
	 * @param personnelPhone4 the personnelPhone4 to set
	 */
	public void setPersonnelPhone4(String personnelPhone4) {
		this.personnelPhone4 = personnelPhone4;
	}

	/**
	 * @return the personnelPhone5
	 */
	public String getPersonnelPhone5() {
		return personnelPhone5;
	}

	/**
	 * @param personnelPhone5 the personnelPhone5 to set
	 */
	public void setPersonnelPhone5(String personnelPhone5) {
		this.personnelPhone5 = personnelPhone5;
	}

	/**
	 * @return the personnelPhone6
	 */
	public String getPersonnelPhone6() {
		return personnelPhone6;
	}

	/**
	 * @param personnelPhone6 the personnelPhone6 to set
	 */
	public void setPersonnelPhone6(String personnelPhone6) {
		this.personnelPhone6 = personnelPhone6;
	}

	/**
	 * @return the personnelPhone7
	 */
	public String getPersonnelPhone7() {
		return personnelPhone7;
	}

	/**
	 * @param personnelPhone7 the personnelPhone7 to set
	 */
	public void setPersonnelPhone7(String personnelPhone7) {
		this.personnelPhone7 = personnelPhone7;
	}

	/**
	 * @return the personnelPhone8
	 */
	public String getPersonnelPhone8() {
		return personnelPhone8;
	}

	/**
	 * @param personnelPhone8 the personnelPhone8 to set
	 */
	public void setPersonnelPhone8(String personnelPhone8) {
		this.personnelPhone8 = personnelPhone8;
	}

	/**
	 * @return the frequencyName1
	 */
	public String getFrequencyName1() {
		return frequencyName1;
	}

	/**
	 * @param frequencyName1 the frequencyName1 to set
	 */
	public void setFrequencyName1(String frequencyName1) {
		this.frequencyName1 = frequencyName1;
	}

	/**
	 * @return the frequencyName2
	 */
	public String getFrequencyName2() {
		return frequencyName2;
	}

	/**
	 * @param frequencyName2 the frequencyName2 to set
	 */
	public void setFrequencyName2(String frequencyName2) {
		this.frequencyName2 = frequencyName2;
	}

	/**
	 * @return the frequencyName3
	 */
	public String getFrequencyName3() {
		return frequencyName3;
	}

	/**
	 * @param frequencyName3 the frequencyName3 to set
	 */
	public void setFrequencyName3(String frequencyName3) {
		this.frequencyName3 = frequencyName3;
	}

	/**
	 * @return the frequencyName4
	 */
	public String getFrequencyName4() {
		return frequencyName4;
	}

	/**
	 * @param frequencyName4 the frequencyName4 to set
	 */
	public void setFrequencyName4(String frequencyName4) {
		this.frequencyName4 = frequencyName4;
	}

	/**
	 * @return the frequencyName5
	 */
	public String getFrequencyName5() {
		return frequencyName5;
	}

	/**
	 * @param frequencyName5 the frequencyName5 to set
	 */
	public void setFrequencyName5(String frequencyName5) {
		this.frequencyName5 = frequencyName5;
	}

	/**
	 * @return the frequencyName6
	 */
	public String getFrequencyName6() {
		return frequencyName6;
	}

	/**
	 * @param frequencyName6 the frequencyName6 to set
	 */
	public void setFrequencyName6(String frequencyName6) {
		this.frequencyName6 = frequencyName6;
	}

	/**
	 * @return the frequencyName7
	 */
	public String getFrequencyName7() {
		return frequencyName7;
	}

	/**
	 * @param frequencyName7 the frequencyName7 to set
	 */
	public void setFrequencyName7(String frequencyName7) {
		this.frequencyName7 = frequencyName7;
	}

	/**
	 * @return the frequencyName8
	 */
	public String getFrequencyName8() {
		return frequencyName8;
	}

	/**
	 * @param frequencyName8 the frequencyName8 to set
	 */
	public void setFrequencyName8(String frequencyName8) {
		this.frequencyName8 = frequencyName8;
	}

	/**
	 * @return the frequencyName9
	 */
	public String getFrequencyName9() {
		return frequencyName9;
	}

	/**
	 * @param frequencyName9 the frequencyName9 to set
	 */
	public void setFrequencyName9(String frequencyName9) {
		this.frequencyName9 = frequencyName9;
	}

	/**
	 * @return the frequencyAm1
	 */
	public String getFrequencyAm1() {
		return frequencyAm1;
	}

	/**
	 * @param frequencyAm1 the frequencyAm1 to set
	 */
	public void setFrequencyAm1(String frequencyAm1) {
		this.frequencyAm1 = frequencyAm1;
	}

	/**
	 * @return the frequencyAm2
	 */
	public String getFrequencyAm2() {
		return frequencyAm2;
	}

	/**
	 * @param frequencyAm2 the frequencyAm2 to set
	 */
	public void setFrequencyAm2(String frequencyAm2) {
		this.frequencyAm2 = frequencyAm2;
	}

	/**
	 * @return the frequencyAm3
	 */
	public String getFrequencyAm3() {
		return frequencyAm3;
	}

	/**
	 * @param frequencyAm3 the frequencyAm3 to set
	 */
	public void setFrequencyAm3(String frequencyAm3) {
		this.frequencyAm3 = frequencyAm3;
	}

	/**
	 * @return the frequencyAm4
	 */
	public String getFrequencyAm4() {
		return frequencyAm4;
	}

	/**
	 * @param frequencyAm4 the frequencyAm4 to set
	 */
	public void setFrequencyAm4(String frequencyAm4) {
		this.frequencyAm4 = frequencyAm4;
	}

	/**
	 * @return the frequencyAm5
	 */
	public String getFrequencyAm5() {
		return frequencyAm5;
	}

	/**
	 * @param frequencyAm5 the frequencyAm5 to set
	 */
	public void setFrequencyAm5(String frequencyAm5) {
		this.frequencyAm5 = frequencyAm5;
	}

	/**
	 * @return the frequencyAm6
	 */
	public String getFrequencyAm6() {
		return frequencyAm6;
	}

	/**
	 * @param frequencyAm6 the frequencyAm6 to set
	 */
	public void setFrequencyAm6(String frequencyAm6) {
		this.frequencyAm6 = frequencyAm6;
	}

	/**
	 * @return the frequencyAm7
	 */
	public String getFrequencyAm7() {
		return frequencyAm7;
	}

	/**
	 * @param frequencyAm7 the frequencyAm7 to set
	 */
	public void setFrequencyAm7(String frequencyAm7) {
		this.frequencyAm7 = frequencyAm7;
	}

	/**
	 * @return the frequencyAm8
	 */
	public String getFrequencyAm8() {
		return frequencyAm8;
	}

	/**
	 * @param frequencyAm8 the frequencyAm8 to set
	 */
	public void setFrequencyAm8(String frequencyAm8) {
		this.frequencyAm8 = frequencyAm8;
	}

	/**
	 * @return the frequencyAm9
	 */
	public String getFrequencyAm9() {
		return frequencyAm9;
	}

	/**
	 * @param frequencyAm9 the frequencyAm9 to set
	 */
	public void setFrequencyAm9(String frequencyAm9) {
		this.frequencyAm9 = frequencyAm9;
	}

	/**
	 * @return the frequencyFm1
	 */
	public String getFrequencyFm1() {
		return frequencyFm1;
	}

	/**
	 * @param frequencyFm1 the frequencyFm1 to set
	 */
	public void setFrequencyFm1(String frequencyFm1) {
		this.frequencyFm1 = frequencyFm1;
	}

	/**
	 * @return the frequencyFm2
	 */
	public String getFrequencyFm2() {
		return frequencyFm2;
	}

	/**
	 * @param frequencyFm2 the frequencyFm2 to set
	 */
	public void setFrequencyFm2(String frequencyFm2) {
		this.frequencyFm2 = frequencyFm2;
	}

	/**
	 * @return the frequencyFm3
	 */
	public String getFrequencyFm3() {
		return frequencyFm3;
	}

	/**
	 * @param frequencyFm3 the frequencyFm3 to set
	 */
	public void setFrequencyFm3(String frequencyFm3) {
		this.frequencyFm3 = frequencyFm3;
	}

	/**
	 * @return the frequencyFm4
	 */
	public String getFrequencyFm4() {
		return frequencyFm4;
	}

	/**
	 * @param frequencyFm4 the frequencyFm4 to set
	 */
	public void setFrequencyFm4(String frequencyFm4) {
		this.frequencyFm4 = frequencyFm4;
	}

	/**
	 * @return the frequencyFm5
	 */
	public String getFrequencyFm5() {
		return frequencyFm5;
	}

	/**
	 * @param frequencyFm5 the frequencyFm5 to set
	 */
	public void setFrequencyFm5(String frequencyFm5) {
		this.frequencyFm5 = frequencyFm5;
	}

	/**
	 * @return the frequencyFm6
	 */
	public String getFrequencyFm6() {
		return frequencyFm6;
	}

	/**
	 * @param frequencyFm6 the frequencyFm6 to set
	 */
	public void setFrequencyFm6(String frequencyFm6) {
		this.frequencyFm6 = frequencyFm6;
	}

	/**
	 * @return the frequencyFm7
	 */
	public String getFrequencyFm7() {
		return frequencyFm7;
	}

	/**
	 * @param frequencyFm7 the frequencyFm7 to set
	 */
	public void setFrequencyFm7(String frequencyFm7) {
		this.frequencyFm7 = frequencyFm7;
	}

	/**
	 * @return the frequencyFm8
	 */
	public String getFrequencyFm8() {
		return frequencyFm8;
	}

	/**
	 * @param frequencyFm8 the frequencyFm8 to set
	 */
	public void setFrequencyFm8(String frequencyFm8) {
		this.frequencyFm8 = frequencyFm8;
	}

	/**
	 * @return the frequencyFm9
	 */
	public String getFrequencyFm9() {
		return frequencyFm9;
	}

	/**
	 * @param frequencyFm9 the frequencyFm9 to set
	 */
	public void setFrequencyFm9(String frequencyFm9) {
		this.frequencyFm9 = frequencyFm9;
	}

	/**
	 * @return the personnelPos9
	 */
	public String getPersonnelPos9() {
		return personnelPos9;
	}

	/**
	 * @param personnelPos9 the personnelPos9 to set
	 */
	public void setPersonnelPos9(String personnelPos9) {
		this.personnelPos9 = personnelPos9;
	}

	/**
	 * @return the personnelPos10
	 */
	public String getPersonnelPos10() {
		return personnelPos10;
	}

	/**
	 * @param personnelPos10 the personnelPos10 to set
	 */
	public void setPersonnelPos10(String personnelPos10) {
		this.personnelPos10 = personnelPos10;
	}

	/**
	 * @return the personnelPos11
	 */
	public String getPersonnelPos11() {
		return personnelPos11;
	}

	/**
	 * @param personnelPos11 the personnelPos11 to set
	 */
	public void setPersonnelPos11(String personnelPos11) {
		this.personnelPos11 = personnelPos11;
	}

	/**
	 * @return the personnelName9
	 */
	public String getPersonnelName9() {
		return personnelName9;
	}

	/**
	 * @param personnelName9 the personnelName9 to set
	 */
	public void setPersonnelName9(String personnelName9) {
		this.personnelName9 = personnelName9;
	}

	/**
	 * @return the personnelName10
	 */
	public String getPersonnelName10() {
		return personnelName10;
	}

	/**
	 * @param personnelName10 the personnelName10 to set
	 */
	public void setPersonnelName10(String personnelName10) {
		this.personnelName10 = personnelName10;
	}

	/**
	 * @return the personnelName11
	 */
	public String getPersonnelName11() {
		return personnelName11;
	}

	/**
	 * @param personnelName11 the personnelName11 to set
	 */
	public void setPersonnelName11(String personnelName11) {
		this.personnelName11 = personnelName11;
	}

	/**
	 * @return the personnelPhone9
	 */
	public String getPersonnelPhone9() {
		return personnelPhone9;
	}

	/**
	 * @param personnelPhone9 the personnelPhone9 to set
	 */
	public void setPersonnelPhone9(String personnelPhone9) {
		this.personnelPhone9 = personnelPhone9;
	}

	/**
	 * @return the personnelPhone10
	 */
	public String getPersonnelPhone10() {
		return personnelPhone10;
	}

	/**
	 * @param personnelPhone10 the personnelPhone10 to set
	 */
	public void setPersonnelPhone10(String personnelPhone10) {
		this.personnelPhone10 = personnelPhone10;
	}

	/**
	 * @return the personnelPhone11
	 */
	public String getPersonnelPhone11() {
		return personnelPhone11;
	}

	/**
	 * @param personnelPhone11 the personnelPhone11 to set
	 */
	public void setPersonnelPhone11(String personnelPhone11) {
		this.personnelPhone11 = personnelPhone11;
	}

	/**
	 * @return the frequencyName10
	 */
	public String getFrequencyName10() {
		return frequencyName10;
	}

	/**
	 * @param frequencyName10 the frequencyName10 to set
	 */
	public void setFrequencyName10(String frequencyName10) {
		this.frequencyName10 = frequencyName10;
	}

	/**
	 * @return the frequencyName11
	 */
	public String getFrequencyName11() {
		return frequencyName11;
	}

	/**
	 * @param frequencyName11 the frequencyName11 to set
	 */
	public void setFrequencyName11(String frequencyName11) {
		this.frequencyName11 = frequencyName11;
	}

	/**
	 * @return the frequencyName12
	 */
	public String getFrequencyName12() {
		return frequencyName12;
	}

	/**
	 * @param frequencyName12 the frequencyName12 to set
	 */
	public void setFrequencyName12(String frequencyName12) {
		this.frequencyName12 = frequencyName12;
	}

	/**
	 * @return the frequencyAm10
	 */
	public String getFrequencyAm10() {
		return frequencyAm10;
	}

	/**
	 * @param frequencyAm10 the frequencyAm10 to set
	 */
	public void setFrequencyAm10(String frequencyAm10) {
		this.frequencyAm10 = frequencyAm10;
	}

	/**
	 * @return the frequencyAm11
	 */
	public String getFrequencyAm11() {
		return frequencyAm11;
	}

	/**
	 * @param frequencyAm11 the frequencyAm11 to set
	 */
	public void setFrequencyAm11(String frequencyAm11) {
		this.frequencyAm11 = frequencyAm11;
	}

	/**
	 * @return the frequencyAm12
	 */
	public String getFrequencyAm12() {
		return frequencyAm12;
	}

	/**
	 * @param frequencyAm12 the frequencyAm12 to set
	 */
	public void setFrequencyAm12(String frequencyAm12) {
		this.frequencyAm12 = frequencyAm12;
	}

	/**
	 * @return the frequencyFm10
	 */
	public String getFrequencyFm10() {
		return frequencyFm10;
	}

	/**
	 * @param frequencyFm10 the frequencyFm10 to set
	 */
	public void setFrequencyFm10(String frequencyFm10) {
		this.frequencyFm10 = frequencyFm10;
	}

	/**
	 * @return the frequencyFm11
	 */
	public String getFrequencyFm11() {
		return frequencyFm11;
	}

	/**
	 * @param frequencyFm11 the frequencyFm11 to set
	 */
	public void setFrequencyFm11(String frequencyFm11) {
		this.frequencyFm11 = frequencyFm11;
	}

	/**
	 * @return the frequencyFm12
	 */
	public String getFrequencyFm12() {
		return frequencyFm12;
	}

	/**
	 * @param frequencyFm12 the frequencyFm12 to set
	 */
	public void setFrequencyFm12(String frequencyFm12) {
		this.frequencyFm12 = frequencyFm12;
	}

	/**
	 * @return the fixedWingName1
	 */
	public String getFixedWingName1() {
		return fixedWingName1;
	}

	/**
	 * @param fixedWingName1 the fixedWingName1 to set
	 */
	public void setFixedWingName1(String fixedWingName1) {
		this.fixedWingName1 = fixedWingName1;
	}

	/**
	 * @return the fixedWingName2
	 */
	public String getFixedWingName2() {
		return fixedWingName2;
	}

	/**
	 * @param fixedWingName2 the fixedWingName2 to set
	 */
	public void setFixedWingName2(String fixedWingName2) {
		this.fixedWingName2 = fixedWingName2;
	}

	/**
	 * @return the fixedWingName3
	 */
	public String getFixedWingName3() {
		return fixedWingName3;
	}

	/**
	 * @param fixedWingName3 the fixedWingName3 to set
	 */
	public void setFixedWingName3(String fixedWingName3) {
		this.fixedWingName3 = fixedWingName3;
	}

	/**
	 * @return the fixedWingName4
	 */
	public String getFixedWingName4() {
		return fixedWingName4;
	}

	/**
	 * @param fixedWingName4 the fixedWingName4 to set
	 */
	public void setFixedWingName4(String fixedWingName4) {
		this.fixedWingName4 = fixedWingName4;
	}

	/**
	 * @return the fixedWingName5
	 */
	public String getFixedWingName5() {
		return fixedWingName5;
	}

	/**
	 * @param fixedWingName5 the fixedWingName5 to set
	 */
	public void setFixedWingName5(String fixedWingName5) {
		this.fixedWingName5 = fixedWingName5;
	}

	/**
	 * @return the fixedWingName6
	 */
	public String getFixedWingName6() {
		return fixedWingName6;
	}

	/**
	 * @param fixedWingName6 the fixedWingName6 to set
	 */
	public void setFixedWingName6(String fixedWingName6) {
		this.fixedWingName6 = fixedWingName6;
	}

	/**
	 * @return the fixedWingName7
	 */
	public String getFixedWingName7() {
		return fixedWingName7;
	}

	/**
	 * @param fixedWingName7 the fixedWingName7 to set
	 */
	public void setFixedWingName7(String fixedWingName7) {
		this.fixedWingName7 = fixedWingName7;
	}

	/**
	 * @return the fixedWingName8
	 */
	public String getFixedWingName8() {
		return fixedWingName8;
	}

	/**
	 * @param fixedWingName8 the fixedWingName8 to set
	 */
	public void setFixedWingName8(String fixedWingName8) {
		this.fixedWingName8 = fixedWingName8;
	}

	/**
	 * @return the fixedWingName9
	 */
	public String getFixedWingName9() {
		return fixedWingName9;
	}

	/**
	 * @param fixedWingName9 the fixedWingName9 to set
	 */
	public void setFixedWingName9(String fixedWingName9) {
		this.fixedWingName9 = fixedWingName9;
	}

	/**
	 * @return the fixedWingName10
	 */
	public String getFixedWingName10() {
		return fixedWingName10;
	}

	/**
	 * @param fixedWingName10 the fixedWingName10 to set
	 */
	public void setFixedWingName10(String fixedWingName10) {
		this.fixedWingName10 = fixedWingName10;
	}

	/**
	 * @return the fixedWingName11
	 */
	public String getFixedWingName11() {
		return fixedWingName11;
	}

	/**
	 * @param fixedWingName11 the fixedWingName11 to set
	 */
	public void setFixedWingName11(String fixedWingName11) {
		this.fixedWingName11 = fixedWingName11;
	}

	/**
	 * @return the fixedWingName12
	 */
	public String getFixedWingName12() {
		return fixedWingName12;
	}

	/**
	 * @param fixedWingName12 the fixedWingName12 to set
	 */
	public void setFixedWingName12(String fixedWingName12) {
		this.fixedWingName12 = fixedWingName12;
	}

	/**
	 * @return the fixedWingType1
	 */
	public String getFixedWingType1() {
		return fixedWingType1;
	}

	/**
	 * @param fixedWingType1 the fixedWingType1 to set
	 */
	public void setFixedWingType1(String fixedWingType1) {
		this.fixedWingType1 = fixedWingType1;
	}

	/**
	 * @return the fixedWingType2
	 */
	public String getFixedWingType2() {
		return fixedWingType2;
	}

	/**
	 * @param fixedWingType2 the fixedWingType2 to set
	 */
	public void setFixedWingType2(String fixedWingType2) {
		this.fixedWingType2 = fixedWingType2;
	}

	/**
	 * @return the fixedWingType3
	 */
	public String getFixedWingType3() {
		return fixedWingType3;
	}

	/**
	 * @param fixedWingType3 the fixedWingType3 to set
	 */
	public void setFixedWingType3(String fixedWingType3) {
		this.fixedWingType3 = fixedWingType3;
	}

	/**
	 * @return the fixedWingType4
	 */
	public String getFixedWingType4() {
		return fixedWingType4;
	}

	/**
	 * @param fixedWingType4 the fixedWingType4 to set
	 */
	public void setFixedWingType4(String fixedWingType4) {
		this.fixedWingType4 = fixedWingType4;
	}

	/**
	 * @return the fixedWingType5
	 */
	public String getFixedWingType5() {
		return fixedWingType5;
	}

	/**
	 * @param fixedWingType5 the fixedWingType5 to set
	 */
	public void setFixedWingType5(String fixedWingType5) {
		this.fixedWingType5 = fixedWingType5;
	}

	/**
	 * @return the fixedWingType6
	 */
	public String getFixedWingType6() {
		return fixedWingType6;
	}

	/**
	 * @param fixedWingType6 the fixedWingType6 to set
	 */
	public void setFixedWingType6(String fixedWingType6) {
		this.fixedWingType6 = fixedWingType6;
	}

	/**
	 * @return the fixedWingType7
	 */
	public String getFixedWingType7() {
		return fixedWingType7;
	}

	/**
	 * @param fixedWingType7 the fixedWingType7 to set
	 */
	public void setFixedWingType7(String fixedWingType7) {
		this.fixedWingType7 = fixedWingType7;
	}

	/**
	 * @return the fixedWingType8
	 */
	public String getFixedWingType8() {
		return fixedWingType8;
	}

	/**
	 * @param fixedWingType8 the fixedWingType8 to set
	 */
	public void setFixedWingType8(String fixedWingType8) {
		this.fixedWingType8 = fixedWingType8;
	}

	/**
	 * @return the fixedWingType9
	 */
	public String getFixedWingType9() {
		return fixedWingType9;
	}

	/**
	 * @param fixedWingType9 the fixedWingType9 to set
	 */
	public void setFixedWingType9(String fixedWingType9) {
		this.fixedWingType9 = fixedWingType9;
	}

	/**
	 * @return the fixedWingType10
	 */
	public String getFixedWingType10() {
		return fixedWingType10;
	}

	/**
	 * @param fixedWingType10 the fixedWingType10 to set
	 */
	public void setFixedWingType10(String fixedWingType10) {
		this.fixedWingType10 = fixedWingType10;
	}

	/**
	 * @return the fixedWingType11
	 */
	public String getFixedWingType11() {
		return fixedWingType11;
	}

	/**
	 * @param fixedWingType11 the fixedWingType11 to set
	 */
	public void setFixedWingType11(String fixedWingType11) {
		this.fixedWingType11 = fixedWingType11;
	}

	/**
	 * @return the fixedWingType12
	 */
	public String getFixedWingType12() {
		return fixedWingType12;
	}

	/**
	 * @param fixedWingType12 the fixedWingType12 to set
	 */
	public void setFixedWingType12(String fixedWingType12) {
		this.fixedWingType12 = fixedWingType12;
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
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedByDateTime
	 */
	public String getPreparedByDateTime() {
		return preparedByDateTime;
	}

	/**
	 * @param preparedByDateTime the preparedByDateTime to set
	 */
	public void setPreparedByDateTime(String preparedByDateTime) {
		this.preparedByDateTime = preparedByDateTime;
	}

	/**
	 * @return the frequencyAmTone1
	 */
	public String getFrequencyAmTone1() {
		return frequencyAmTone1;
	}

	/**
	 * @param frequencyAmTone1 the frequencyAmTone1 to set
	 */
	public void setFrequencyAmTone1(String frequencyAmTone1) {
		this.frequencyAmTone1 = frequencyAmTone1;
	}

	/**
	 * @return the frequencyAmTone2
	 */
	public String getFrequencyAmTone2() {
		return frequencyAmTone2;
	}

	/**
	 * @param frequencyAmTone2 the frequencyAmTone2 to set
	 */
	public void setFrequencyAmTone2(String frequencyAmTone2) {
		this.frequencyAmTone2 = frequencyAmTone2;
	}

	/**
	 * @return the frequencyAmTone3
	 */
	public String getFrequencyAmTone3() {
		return frequencyAmTone3;
	}

	/**
	 * @param frequencyAmTone3 the frequencyAmTone3 to set
	 */
	public void setFrequencyAmTone3(String frequencyAmTone3) {
		this.frequencyAmTone3 = frequencyAmTone3;
	}

	/**
	 * @return the frequencyAmTone4
	 */
	public String getFrequencyAmTone4() {
		return frequencyAmTone4;
	}

	/**
	 * @param frequencyAmTone4 the frequencyAmTone4 to set
	 */
	public void setFrequencyAmTone4(String frequencyAmTone4) {
		this.frequencyAmTone4 = frequencyAmTone4;
	}

	/**
	 * @return the frequencyAmTone5
	 */
	public String getFrequencyAmTone5() {
		return frequencyAmTone5;
	}

	/**
	 * @param frequencyAmTone5 the frequencyAmTone5 to set
	 */
	public void setFrequencyAmTone5(String frequencyAmTone5) {
		this.frequencyAmTone5 = frequencyAmTone5;
	}

	/**
	 * @return the frequencyAmTone6
	 */
	public String getFrequencyAmTone6() {
		return frequencyAmTone6;
	}

	/**
	 * @param frequencyAmTone6 the frequencyAmTone6 to set
	 */
	public void setFrequencyAmTone6(String frequencyAmTone6) {
		this.frequencyAmTone6 = frequencyAmTone6;
	}

	/**
	 * @return the frequencyAmTone7
	 */
	public String getFrequencyAmTone7() {
		return frequencyAmTone7;
	}

	/**
	 * @param frequencyAmTone7 the frequencyAmTone7 to set
	 */
	public void setFrequencyAmTone7(String frequencyAmTone7) {
		this.frequencyAmTone7 = frequencyAmTone7;
	}

	/**
	 * @return the frequencyAmTone8
	 */
	public String getFrequencyAmTone8() {
		return frequencyAmTone8;
	}

	/**
	 * @param frequencyAmTone8 the frequencyAmTone8 to set
	 */
	public void setFrequencyAmTone8(String frequencyAmTone8) {
		this.frequencyAmTone8 = frequencyAmTone8;
	}

	/**
	 * @return the frequencyAmTone9
	 */
	public String getFrequencyAmTone9() {
		return frequencyAmTone9;
	}

	/**
	 * @param frequencyAmTone9 the frequencyAmTone9 to set
	 */
	public void setFrequencyAmTone9(String frequencyAmTone9) {
		this.frequencyAmTone9 = frequencyAmTone9;
	}

	/**
	 * @return the frequencyAmTone10
	 */
	public String getFrequencyAmTone10() {
		return frequencyAmTone10;
	}

	/**
	 * @param frequencyAmTone10 the frequencyAmTone10 to set
	 */
	public void setFrequencyAmTone10(String frequencyAmTone10) {
		this.frequencyAmTone10 = frequencyAmTone10;
	}

	/**
	 * @return the frequencyAmTone11
	 */
	public String getFrequencyAmTone11() {
		return frequencyAmTone11;
	}

	/**
	 * @param frequencyAmTone11 the frequencyAmTone11 to set
	 */
	public void setFrequencyAmTone11(String frequencyAmTone11) {
		this.frequencyAmTone11 = frequencyAmTone11;
	}

	/**
	 * @return the frequencyAmTone12
	 */
	public String getFrequencyAmTone12() {
		return frequencyAmTone12;
	}

	/**
	 * @param frequencyAmTone12 the frequencyAmTone12 to set
	 */
	public void setFrequencyAmTone12(String frequencyAmTone12) {
		this.frequencyAmTone12 = frequencyAmTone12;
	}

	/**
	 * @return the frequencyFmTone1
	 */
	public String getFrequencyFmTone1() {
		return frequencyFmTone1;
	}

	/**
	 * @param frequencyFmTone1 the frequencyFmTone1 to set
	 */
	public void setFrequencyFmTone1(String frequencyFmTone1) {
		this.frequencyFmTone1 = frequencyFmTone1;
	}

	/**
	 * @return the frequencyFmTone2
	 */
	public String getFrequencyFmTone2() {
		return frequencyFmTone2;
	}

	/**
	 * @param frequencyFmTone2 the frequencyFmTone2 to set
	 */
	public void setFrequencyFmTone2(String frequencyFmTone2) {
		this.frequencyFmTone2 = frequencyFmTone2;
	}

	/**
	 * @return the frequencyFmTone3
	 */
	public String getFrequencyFmTone3() {
		return frequencyFmTone3;
	}

	/**
	 * @param frequencyFmTone3 the frequencyFmTone3 to set
	 */
	public void setFrequencyFmTone3(String frequencyFmTone3) {
		this.frequencyFmTone3 = frequencyFmTone3;
	}

	/**
	 * @return the frequencyFmTone4
	 */
	public String getFrequencyFmTone4() {
		return frequencyFmTone4;
	}

	/**
	 * @param frequencyFmTone4 the frequencyFmTone4 to set
	 */
	public void setFrequencyFmTone4(String frequencyFmTone4) {
		this.frequencyFmTone4 = frequencyFmTone4;
	}

	/**
	 * @return the frequencyFmTone5
	 */
	public String getFrequencyFmTone5() {
		return frequencyFmTone5;
	}

	/**
	 * @param frequencyFmTone5 the frequencyFmTone5 to set
	 */
	public void setFrequencyFmTone5(String frequencyFmTone5) {
		this.frequencyFmTone5 = frequencyFmTone5;
	}

	/**
	 * @return the frequencyFmTone6
	 */
	public String getFrequencyFmTone6() {
		return frequencyFmTone6;
	}

	/**
	 * @param frequencyFmTone6 the frequencyFmTone6 to set
	 */
	public void setFrequencyFmTone6(String frequencyFmTone6) {
		this.frequencyFmTone6 = frequencyFmTone6;
	}

	/**
	 * @return the frequencyFmTone7
	 */
	public String getFrequencyFmTone7() {
		return frequencyFmTone7;
	}

	/**
	 * @param frequencyFmTone7 the frequencyFmTone7 to set
	 */
	public void setFrequencyFmTone7(String frequencyFmTone7) {
		this.frequencyFmTone7 = frequencyFmTone7;
	}

	/**
	 * @return the frequencyFmTone8
	 */
	public String getFrequencyFmTone8() {
		return frequencyFmTone8;
	}

	/**
	 * @param frequencyFmTone8 the frequencyFmTone8 to set
	 */
	public void setFrequencyFmTone8(String frequencyFmTone8) {
		this.frequencyFmTone8 = frequencyFmTone8;
	}

	/**
	 * @return the frequencyFmTone9
	 */
	public String getFrequencyFmTone9() {
		return frequencyFmTone9;
	}

	/**
	 * @param frequencyFmTone9 the frequencyFmTone9 to set
	 */
	public void setFrequencyFmTone9(String frequencyFmTone9) {
		this.frequencyFmTone9 = frequencyFmTone9;
	}

	/**
	 * @return the frequencyFmTone10
	 */
	public String getFrequencyFmTone10() {
		return frequencyFmTone10;
	}

	/**
	 * @param frequencyFmTone10 the frequencyFmTone10 to set
	 */
	public void setFrequencyFmTone10(String frequencyFmTone10) {
		this.frequencyFmTone10 = frequencyFmTone10;
	}

	/**
	 * @return the frequencyFmTone11
	 */
	public String getFrequencyFmTone11() {
		return frequencyFmTone11;
	}

	/**
	 * @param frequencyFmTone11 the frequencyFmTone11 to set
	 */
	public void setFrequencyFmTone11(String frequencyFmTone11) {
		this.frequencyFmTone11 = frequencyFmTone11;
	}

	/**
	 * @return the frequencyFmTone12
	 */
	public String getFrequencyFmTone12() {
		return frequencyFmTone12;
	}

	/**
	 * @param frequencyFmTone12 the frequencyFmTone12 to set
	 */
	public void setFrequencyFmTone12(String frequencyFmTone12) {
		this.frequencyFmTone12 = frequencyFmTone12;
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
	 * @return the operationalPeriod
	 */
	public String getOperationalPeriod() {
		return operationalPeriod;
	}

	/**
	 * @param operationalPeriod the operationalPeriod to set
	 */
	public void setOperationalPeriod(String operationalPeriod) {
		this.operationalPeriod = operationalPeriod;
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
