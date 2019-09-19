package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapFrequency;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.ReportTextUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class IapForm205ReportData extends BaseReportData {
	private String incidentName;
	private Date fromDate;
	private Date toDate;
	private String fromTime;
	private String toTime;
	private String preparedBy;
	private String preparedByDate;
	private String preparedByTime;
	private String preparedDateTime;
	private String specialInstructions;
	private String draftFinal;
	private String operationalPeriod;
	private String fromDayOfWeek;
	private String toDayOfWeek;
	private ArrayList<IapForm205FrequencyData> frequencyData = new ArrayList<IapForm205FrequencyData>();
	private JRBeanCollectionDataSource frequencies;
	
	public IapForm205ReportData() {
	}

	public static IapForm205ReportData getInstance(IapPlan iapPlan, IapForm205 entity) throws Exception {
		IapForm205ReportData reportData = new IapForm205ReportData();

		if(BooleanUtility.isTrue(entity.getIsFormLocked().getValue()))
			reportData.setDraftFinal("FINAL");
		else
			reportData.setDraftFinal("DRAFT");

		reportData.setOperationalPeriod(iapPlan.getOperationPeriod());
		
		// Block 1
		reportData.setIncidentName(iapPlan.getIncidentName());
		
		// Block 2
		if(DateUtil.hasValue(entity.getPreparedDate())){
			String sdate=DateUtil.toDateString(entity.getPreparedDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			String stime=DateUtil.toMilitaryTime(entity.getPreparedDate());
			reportData.setPreparedByDate(sdate);
			reportData.setPreparedByTime(stime);
			reportData.setPreparedDateTime(sdate+" " + stime);
		}
		
		// Block 3 
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
		
		// Block 4 Frequencies
		if(CollectionUtility.hasValue(entity.getIapFrequencies())){
			for(IapFrequency freq : entity.getIapFrequencies()){
				IapForm205FrequencyData freqData = new IapForm205FrequencyData();
				freqData.setZoneGroup(freq.getZoneGroup());
				freqData.setAssignment(freq.getAssignment());
				freqData.setChannel(freq.getChannel());
				freqData.setChannelName(freq.getChannelName());
				freqData.setFrequencyRx(freq.getFrequencyRx());
				freqData.setFrequencyTx(freq.getFrequencyTx());
				freqData.setModeType(freq.getModeType());
				freqData.setRadioType(freq.getRadioType());
				freqData.setRemarks(freq.getRemarks());
				freqData.setSfunction(freq.getFunction());
				freqData.setToneRx(freq.getToneRx());
				freqData.setToneTx(freq.getToneTx());
				reportData.getFrequencyData().add(freqData);
			}
		}

		if(reportData.getFrequencyData().size()<4){
			int addEmptyCnt=4 - reportData.getFrequencyData().size();
			for(int i=0;i<addEmptyCnt;i++){
				IapForm205FrequencyData freqData = new IapForm205FrequencyData();
				reportData.getFrequencyData().add(freqData);
			}
		}

		// Block 5
		if(StringUtility.hasValue(entity.getSpecialInstruction())){
			// Convert fontsizes 14,16,18 to 1,2,3
			String str=ReportTextUtil.formatText(entity.getSpecialInstruction());
			reportData.setSpecialInstructions(str);
		}
		
		// Block 6
		if(StringUtility.hasValue(entity.getPreparedBy()))	
			reportData.setPreparedBy(entity.getPreparedBy().toUpperCase());
		else
			reportData.setPreparedBy("");
			
		return reportData;
	}

	/**
	 * @return the frequencyData
	 */
	public ArrayList<IapForm205FrequencyData> getFrequencyData() {
		return frequencyData;
	}

	/**
	 * @param frequencyData the frequencyData to set
	 */
	public void setFrequencyData(ArrayList<IapForm205FrequencyData> frequencyData) {
		this.frequencyData = frequencyData;
	}

	/**
	 * @return the frequencies
	 */
	public JRBeanCollectionDataSource getFrequencies() {
		return new JRBeanCollectionDataSource(this.frequencyData);
	}

	/**
	 * @param frequencies the frequencies to set
	 */
	public void setFrequencies(JRBeanCollectionDataSource frequencies) {
		this.frequencies = frequencies;
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
	 * @return the preparedByDate
	 */
	public String getPreparedByDate() {
		return preparedByDate;
	}

	/**
	 * @param preparedByDate the preparedByDate to set
	 */
	public void setPreparedByDate(String preparedByDate) {
		this.preparedByDate = preparedByDate;
	}

	/**
	 * @return the preparedByTime
	 */
	public String getPreparedByTime() {
		return preparedByTime;
	}

	/**
	 * @param preparedByTime the preparedByTime to set
	 */
	public void setPreparedByTime(String preparedByTime) {
		this.preparedByTime = preparedByTime;
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
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
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
