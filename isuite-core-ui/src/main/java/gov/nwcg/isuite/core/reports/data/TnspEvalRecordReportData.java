package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TnspEvalRecordReportData {
	private String field1 = "";
	private String traineeName;
	private String requestNumber;
	private String traineeItemCode;
	private String traineeItemCodeDesc;
	private String traineeAgency;
	private String traineeUnitCode;
	private String traineeUnitDesc;
	private String traineeHUName;
	private String traineeHUCode;
	private String traineeHUDesc;
	private String traineeHUAddress;
	private String traineeHUCity;
	private String traineeHUState;
	private String traineeHUZip;
	private String traineeHUFullAddress;
	private String traineeHUPhone;
	private String traineeHUEmail;
	private String trainerName;
	private String trainerRequestNumber;
	private String trainerUnit;
	private String trainerUnitDesc;
	private String trainerItemCode;
	private String trainerItemDesc;
	private String trainerAddress;
	private String trainerCity;
	private String trainerState;
	private String trainerZip;
	private String trainerFullAddress;
	private String trainerPhone;
	private String trainerEmail;
	private String trainerRecommend;
	private String incidentName;
	private String incidentNumber;
	private String incidentType;
	private String incidentComplexity;
	private String incidentAcres;
	private String incidentFuelType;
	private String trainerComments;
	private String evalRecordNumber;
	private String traineeAssnStart;
	private String traineeAssnEnd;
	private String traineeDuration;
	
	public TnspEvalRecordReportData() {
	}

	public static TnspEvalRecordReportData getInstanceBlankForm() throws Exception {
		TnspEvalRecordReportData reportData = new TnspEvalRecordReportData();
		return reportData;
	}

	public static TnspEvalRecordReportData getInstance() throws Exception {
		TnspEvalRecordReportData reportData = new TnspEvalRecordReportData();
		
		return reportData;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getTraineeName() {
		return traineeName;
	}

	public void setTraineeName(String traineeName) {
		this.traineeName = traineeName;
	}

	public String getRequestNumber() {
		return (StringUtility.hasValue(requestNumber) ? requestNumber : "");
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getTraineeItemCode() {
		return traineeItemCode;
	}

	public void setTraineeItemCode(String traineeItemCode) {
		this.traineeItemCode = traineeItemCode;
	}

	public String getTraineeItemCodeDesc() {
		return traineeItemCodeDesc;
	}

	public void setTraineeItemCodeDesc(String traineeItemCodeDesc) {
		this.traineeItemCodeDesc = traineeItemCodeDesc;
	}

	public String getTraineeAgency() {
		return traineeAgency;
	}

	public void setTraineeAgency(String traineeAgency) {
		this.traineeAgency = traineeAgency;
	}

	public String getTraineeUnitCode() {
		return traineeUnitCode;
	}

	public void setTraineeUnitCode(String traineeUnitCode) {
		this.traineeUnitCode = traineeUnitCode;
	}

	public String getTraineeUnitDesc() {
		return traineeUnitDesc;
	}

	public void setTraineeUnitDesc(String traineeUnitDesc) {
		this.traineeUnitDesc = traineeUnitDesc;
	}

	public String getTraineeHUName() {
		return traineeHUName;
	}

	public void setTraineeHUName(String traineeHUName) {
		this.traineeHUName = traineeHUName;
	}

	public String getTraineeHUCode() {
		return traineeHUCode;
	}

	public void setTraineeHUCode(String traineeHUCode) {
		this.traineeHUCode = traineeHUCode;
	}

	public String getTraineeHUDesc() {
		return traineeHUDesc;
	}

	public void setTraineeHUDesc(String traineeHUDesc) {
		this.traineeHUDesc = traineeHUDesc;
	}

	public String getTraineeHUAddress() {
		return traineeHUAddress;
	}

	public void setTraineeHUAddress(String traineeHUAddress) {
		this.traineeHUAddress = traineeHUAddress;
	}

	public String getTraineeHUCity() {
		return traineeHUCity;
	}

	public void setTraineeHUCity(String traineeHUCity) {
		this.traineeHUCity = traineeHUCity;
	}

	public String getTraineeHUState() {
		return traineeHUState;
	}

	public void setTraineeHUState(String traineeHUState) {
		this.traineeHUState = traineeHUState;
	}

	public String getTraineeHUZip() {
		return traineeHUZip;
	}

	public void setTraineeHUZip(String traineeHUZip) {
		this.traineeHUZip = traineeHUZip;
	}

	public String getTraineeHUPhone() {
		return traineeHUPhone;
	}

	public void setTraineeHUPhone(String traineeHUPhone) {
		this.traineeHUPhone = traineeHUPhone;
	}

	public String getTraineeHUEmail() {
		return traineeHUEmail;
	}

	public void setTraineeHUEmail(String traineeHUEmail) {
		this.traineeHUEmail = traineeHUEmail;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getTrainerRequestNumber() {
		return (StringUtility.hasValue(trainerRequestNumber) ? trainerRequestNumber : "");
	}

	public void setTrainerRequestNumber(String trainerRequestNumber) {
		this.trainerRequestNumber = trainerRequestNumber;
	}

	public String getTrainerUnit() {
		return trainerUnit;
	}

	public void setTrainerUnit(String trainerUnit) {
		this.trainerUnit = trainerUnit;
	}

	public String getTrainerUnitDesc() {
		return trainerUnitDesc;
	}

	public void setTrainerUnitDesc(String trainerUnitDesc) {
		this.trainerUnitDesc = trainerUnitDesc;
	}

	public String getTrainerItemCode() {
		return trainerItemCode;
	}

	public void setTrainerItemCode(String trainerItemCode) {
		this.trainerItemCode = trainerItemCode;
	}

	public String getTrainerItemDesc() {
		return trainerItemDesc;
	}

	public void setTrainerItemDesc(String trainerItemDesc) {
		this.trainerItemDesc = trainerItemDesc;
	}

	public String getTrainerAddress() {
		return trainerAddress;
	}

	public void setTrainerAddress(String trainerAddress) {
		this.trainerAddress = trainerAddress;
	}

	public String getTrainerCity() {
		return trainerCity;
	}

	public void setTrainerCity(String trainerCity) {
		this.trainerCity = trainerCity;
	}

	public String getTrainerState() {
		return trainerState;
	}

	public void setTrainerState(String trainerState) {
		this.trainerState = trainerState;
	}

	public String getTrainerZip() {
		return trainerZip;
	}

	public void setTrainerZip(String trainerZip) {
		this.trainerZip = trainerZip;
	}

	public String getTrainerPhone() {
		return trainerPhone;
	}

	public void setTrainerPhone(String trainerPhone) {
		this.trainerPhone = trainerPhone;
	}

	public String getTrainerEmail() {
		return trainerEmail;
	}

	public void setTrainerEmail(String trainerEmail) {
		this.trainerEmail = trainerEmail;
	}

	public String getTrainerRecommend() {
		return trainerRecommend;
	}

	public void setTrainerRecommend(String trainerRecommend) {
		this.trainerRecommend = trainerRecommend;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(String incidentType) {
		this.incidentType = incidentType;
	}

	public String getIncidentComplexity() {
		return incidentComplexity;
	}

	public void setIncidentComplexity(String incidentComplexity) {
		this.incidentComplexity = incidentComplexity;
	}

	public String getIncidentAcres() {
		return incidentAcres;
	}

	public void setIncidentAcres(String incidentAcres) {
		this.incidentAcres = incidentAcres;
	}

	public String getIncidentFuelType() {
		return incidentFuelType;
	}

	public void setIncidentFuelType(String incidentFuelType) {
		this.incidentFuelType = incidentFuelType;
	}

	public String getTrainerComments() {
		return trainerComments;
	}

	public void setTrainerComments(String trainerComments) {
		this.trainerComments = trainerComments;
	}

	public String getEvalRecordNumber() {
		return evalRecordNumber;
	}

	public void setEvalRecordNumber(String evalRecordNumber) {
		this.evalRecordNumber = evalRecordNumber;
	}

	public String getTraineeHUFullAddress() {
		String rtn=(StringUtility.hasValue(this.traineeHUAddress)?this.traineeHUAddress:"") +
				" " + (StringUtility.hasValue(this.traineeHUCity)?this.traineeHUCity:"") +
				", " + (StringUtility.hasValue(this.traineeHUState)?this.traineeHUState:"") + 
				" " + (StringUtility.hasValue(this.traineeHUZip)?this.traineeHUZip:"");
				//"  " + (StringUtility.hasValue(this.traineeHUPhone)?this.traineeHUPhone:"");
		if(StringUtility.hasValue(rtn) && rtn.trim().equalsIgnoreCase(","))
			rtn=(StringUtility.hasValue(this.traineeHUPhone)?this.traineeHUPhone:"");
		else
			rtn=rtn+"  "+(StringUtility.hasValue(this.traineeHUPhone)?this.traineeHUPhone:"");
		
		return rtn;
		//return traineeHUFullAddress;
	}

	public void setTraineeHUFullAddress(String traineeHUFullAddress) {
		this.traineeHUFullAddress = traineeHUFullAddress;
	}

	public String getTrainerFullAddress() {
		String rtn=(StringUtility.hasValue(this.trainerAddress)?this.trainerAddress:"") +
		" " + (StringUtility.hasValue(this.trainerCity)?this.trainerCity:"") +
		", " + (StringUtility.hasValue(this.trainerState)?this.trainerState:"") + 
		" " + (StringUtility.hasValue(this.trainerZip)?this.trainerZip:"");
		//"  " + (StringUtility.hasValue(this.trainerPhone)?this.trainerPhone:"");
		if(StringUtility.hasValue(rtn) && rtn.trim().equalsIgnoreCase(","))
			rtn=(StringUtility.hasValue(this.trainerPhone)?this.trainerPhone:"");
		else
			rtn=rtn+"  "+(StringUtility.hasValue(this.trainerPhone)?this.trainerPhone:"");
		
		return rtn;
	}

	public void setTrainerFullAddress(String trainerFullAddress) {
		this.trainerFullAddress = trainerFullAddress;
	}

	public String getTraineeAssnStart() {
		return traineeAssnStart;
	}

	public void setTraineeAssnStart(String traineeAssnStart) {
		this.traineeAssnStart = traineeAssnStart;
	}

	public String getTraineeAssnEnd() {
		return traineeAssnEnd;
	}

	public void setTraineeAssnEnd(String traineeAssnEnd) {
		this.traineeAssnEnd = traineeAssnEnd;
	}

	public String getTraineeDuration() {
		String rtn = "";
		if(StringUtility.hasValue(this.traineeAssnStart))
			rtn = this.traineeAssnStart + " - ";
		
		if(StringUtility.hasValue(this.traineeAssnEnd)){
			if(StringUtility.hasValue(rtn))
				rtn = rtn + this.traineeAssnEnd;
			else
				rtn = " - " + this.traineeAssnEnd;
		}
		return rtn;
	}

	public void setTraineeDuration(String traineeDuration) {
		this.traineeDuration = traineeDuration;
	}

}
