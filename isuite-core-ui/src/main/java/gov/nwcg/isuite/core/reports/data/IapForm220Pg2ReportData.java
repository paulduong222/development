package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IapForm220Pg2ReportData  {
	private String incidentName;
	private Date fromDate;
	private Date toDate;
	private String fromTime;
	private String toTime;
	private String sunrise;
	private String sunset;
	private String preparedBy;
	private String preparedByPosition;
	private String preparedByDateTime;
	private ArrayList<IapForm220HelicopterData> helicopterData = new ArrayList<IapForm220HelicopterData>();
	private JRBeanCollectionDataSource helicopters;
	private ArrayList<IapForm220TaskData> taskData = new ArrayList<IapForm220TaskData>();
	private JRBeanCollectionDataSource tasks;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	
	public IapForm220Pg2ReportData() {
	}

	public static IapForm220Pg2ReportData getInstance(IapPlan iapPlan, IapForm220 entity) throws Exception {
		IapForm220Pg2ReportData reportData = new IapForm220Pg2ReportData();
		
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
		
		// Block 10 Helicopters
		if(CollectionUtility.hasValue(entity.getIapAircrafts())){
			for(IapAircraft a : entity.getIapAircrafts()){
				if(a.getWingType().equals("HELI")){
					IapForm220HelicopterData hData = new IapForm220HelicopterData();
					hData.setFaaNbr(a.getFaaNbr());
					hData.setType(a.getType());
					hData.setModel(a.getMakeModel());
					hData.setRemarks(a.getRemarks());
					hData.setStart(a.getStartTime());
					hData.setBase(a.getBase());
					hData.setAvailable(a.getAvailable());
					/*
					if(DateUtil.hasValue(a.getAvailableDate())){
						hData.setAvailable(DateUtil.toDateString(a.getAvailableDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY));
					}
					*/
					reportData.getHelicopterData().add(hData);
				}
			}
		}
		
		if(reportData.getHelicopterData().size()<5){
			int addEmptyCnt=5 - reportData.getHelicopterData().size();
			for(int i=0;i<addEmptyCnt;i++){
				IapForm220HelicopterData hData = new IapForm220HelicopterData();
				reportData.getHelicopterData().add(hData);
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
		
		// Block 12 Tasks
		if(CollectionUtility.hasValue(entity.getIapAircraftTasks())){
			for(IapAircraftTask t : entity.getIapAircraftTasks()){
				IapForm220TaskData tData = new IapForm220TaskData();
				tData.setType(t.getType());
				tData.setName(t.getName());
				tData.setStart(t.getStartTime());
				tData.setFlyFrom(t.getFlyFrom());
				tData.setFlyTo(t.getFlyTo());
				reportData.getTaskData().add(tData);
			}
		}
		
		if(reportData.getTaskData().size()<5){
			int addEmptyCnt=5 - reportData.getTaskData().size();
			for(int i=0;i<addEmptyCnt;i++){
				IapForm220TaskData tData = new IapForm220TaskData();
				reportData.getTaskData().add(tData);
			}
		}
		
		return reportData;
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
	 * @return the helicopterData
	 */
	public ArrayList<IapForm220HelicopterData> getHelicopterData() {
		return helicopterData;
	}

	/**
	 * @param helicopterData the helicopterData to set
	 */
	public void setHelicopterData(ArrayList<IapForm220HelicopterData> helicopterData) {
		this.helicopterData = helicopterData;
	}

	/**
	 * @return the helicopters
	 */
	public JRBeanCollectionDataSource getHelicopters() {
		return new JRBeanCollectionDataSource(this.helicopterData);
	}

	/**
	 * @param helicopters the helicopters to set
	 */
	public void setHelicopters(JRBeanCollectionDataSource helicopters) {
		this.helicopters = helicopters;
	}

	/**
	 * @return the taskData
	 */
	public ArrayList<IapForm220TaskData> getTaskData() {
		return taskData;
	}

	/**
	 * @param taskData the taskData to set
	 */
	public void setTaskData(ArrayList<IapForm220TaskData> taskData) {
		this.taskData = taskData;
	}

	/**
	 * @return the tasks
	 */
	public JRBeanCollectionDataSource getTasks() {
		return new JRBeanCollectionDataSource(this.taskData);
	}

	/**
	 * @param tasks the tasks to set
	 */
	public void setTasks(JRBeanCollectionDataSource tasks) {
		this.tasks = tasks;
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
