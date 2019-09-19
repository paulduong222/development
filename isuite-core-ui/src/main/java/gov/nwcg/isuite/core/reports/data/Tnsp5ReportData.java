package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;

public class Tnsp5ReportData {
	private String field1="";
	private String toDate;
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
	private String traineeHUPhone;
	private String traineeHUEmail;
	private String trainerRecommend;
	private String incidentName;
	private String incidentNumber;
	private String incidentType;
	private String incidentComplexity;
	private String incidentAcres;
	private String incidentFuelType;
	private String trainerComments;
	private String tnspFullName;
	private String tnspUnit;
	private String tnspAgency;
	private String tnspPhone;
	private String tnspEmail;
	
	public Tnsp5ReportData() {
	}

	public static Tnsp5ReportData getInstanceBlankForm() throws Exception {
		Tnsp5ReportData reportData = new Tnsp5ReportData();
		return reportData;
	}

	public static Tnsp5ReportData getInstance() throws Exception {
		Tnsp5ReportData reportData = new Tnsp5ReportData();
		
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
		return requestNumber;
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

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTrainerComments() {
		return trainerComments;
	}

	public void setTrainerComments(String trainerComments) {
		this.trainerComments = trainerComments;
	}

	public String getTnspFullName() {
		return tnspFullName;
	}

	public void setTnspFullName(String tnspFullName) {
		this.tnspFullName = tnspFullName;
	}

	public String getTnspUnit() {
		return tnspUnit;
	}

	public void setTnspUnit(String tnspUnit) {
		this.tnspUnit = tnspUnit;
	}

	public String getTnspAgency() {
		return tnspAgency;
	}

	public void setTnspAgency(String tnspAgency) {
		this.tnspAgency = tnspAgency;
	}

	public String getTnspPhone() {
		return tnspPhone;
	}

	public void setTnspPhone(String tnspPhone) {
		this.tnspPhone = tnspPhone;
	}

	public String getTnspEmail() {
		return tnspEmail;
	}

	public void setTnspEmail(String tnspEmail) {
		this.tnspEmail = tnspEmail;
	}


}
