package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.StringUtility;

public class Tnsp4ReportData {
	private String traineeName;
	private String requestNumber;
	private String traineeItemCode;
	private String traineeItemCodeDesc;
	private String trainerName;
	private String trainerRequestNumber;
	private String trainerUnit;
	private String trainerUnitDesc;
	private String trainerItemCode;
	private String trainerItemDesc;
	private String incidentName;
	private String incidentNumber;
	private String tnspFullName;
	
	public Tnsp4ReportData() {
	}

	public static Tnsp4ReportData getInstanceBlankForm() throws Exception {
		Tnsp4ReportData reportData = new Tnsp4ReportData();
		return reportData;
	}

	public static Tnsp4ReportData getInstance() throws Exception {
		Tnsp4ReportData reportData = new Tnsp4ReportData();
		
		return reportData;
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

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}

	public String getTrainerRequestNumber() {
		return trainerRequestNumber;
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

	public String getIncidentName() {
		if(StringUtility.hasValue(this.traineeName))
			return incidentName;
		else
			return "";
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		if(StringUtility.hasValue(this.traineeName))
			return incidentNumber;
		else
			return "";
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getTnspFullName() {
		return tnspFullName;
	}

	public void setTnspFullName(String tnspFullName) {
		this.tnspFullName = tnspFullName;
	}

}
