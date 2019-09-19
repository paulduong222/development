package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.StringUtility;

public class TnspIncidentTraineeReportData extends BaseReportData {
	private Collection<TnspIncidentTraineeSubReportData> subReportData = new ArrayList<TnspIncidentTraineeSubReportData>();
	private JRBeanCollectionDataSource datasource;
	private String traineeName;
	private String requestNumber;
	private String traineeItemCode;
	private String traineeItemCodeDesc;
	private String traineeSection;
	private String traineeIA;
	private String traineeAgency;
	private String traineeUnitCode;
	private String traineeUnitDesc;
	private String traineeAssnStart;
	private String traineeAssnEnd;
	private String traineeRedCard;
	private String traineeCurrentTaskBook;
	private String traineeIncidentTaskBook;
	private String traineePriority;
	private String traineePriorityProgram;
	private String traineeHUName;
	private String traineeHUCode;
	private String traineeHUDesc;
	private String traineeHUAddress;
	private String traineeHUCity;
	private String traineeHUState;
	private String traineeHUZip;
	private String traineeHUPhone;
	private String traineeHUEmail;
	private String traineeGoal1;
	private String traineeGoal2;
	private String traineeGoal3;
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
	private String trainerPhone;
	private String trainerEmail;
	private String trainerRecommend;
	private String trainerPtbProgress;
	private String incidentName;
	private String incidentNumber;
	private String incidentType;
	private String incidentComplexity;
	private String incidentAcres;
	private String incidentFuelType;
	private String tnspFullName;
	private String tnspUnit;
	private String tnspUnitDescription;
	private String tnspAgency;
	private String tnspPhone;
	private String tnspEmail;
	
	private String field1;
	
	public TnspIncidentTraineeReportData() {
	}

	public static TnspIncidentTraineeReportData getInstanceBlankForm() throws Exception {
		TnspIncidentTraineeReportData reportData = new TnspIncidentTraineeReportData();
		return reportData;
	}
	
	public static TnspIncidentTraineeReportData getInstance() throws Exception {
		TnspIncidentTraineeReportData reportData = new TnspIncidentTraineeReportData();

		return reportData;
	}

	public JRBeanCollectionDataSource getDatasource() {
		return new JRBeanCollectionDataSource(this.subReportData);
	}

	public void setDatasource(JRBeanCollectionDataSource datasource) {
		this.datasource = datasource;
	}

	public Collection<TnspIncidentTraineeSubReportData> getSubReportData() {
		return subReportData;
	}

	public void setTnspIncidentTraineeSubReportData(Collection<TnspIncidentTraineeSubReportData> subReportData) {
		this.subReportData = subReportData;
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

	public String getTraineeSection() {
		return traineeSection;
	}

	public void setTraineeSection(String traineeSection) {
		this.traineeSection = traineeSection;
	}

	public String getTraineeIA() {
		if(StringUtility.hasValue(this.traineeName) && StringUtility.hasValue(traineeIA) && traineeIA.equalsIgnoreCase("Y"))
			return "YES";
		else if(!StringUtility.hasValue(this.traineeName))
			return "";
		else
			return "NO";
		//return traineeIA;
	}

	public void setTraineeIA(String traineeIA) {
		this.traineeIA = traineeIA;
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

	public String getTraineeRedCard() {
		return traineeRedCard;
	}

	public void setTraineeRedCard(String traineeRedCard) {
		this.traineeRedCard = traineeRedCard;
	}

	public String getTraineeCurrentTaskBook() {
		return traineeCurrentTaskBook;
	}

	public void setTraineeCurrentTaskBook(String traineeCurrentTaskBook) {
		this.traineeCurrentTaskBook = traineeCurrentTaskBook;
	}

	public String getTraineeIncidentTaskBook() {
		return traineeIncidentTaskBook;
	}

	public void setTraineeIncidentTaskBook(String traineeIncidentTaskBook) {
		this.traineeIncidentTaskBook = traineeIncidentTaskBook;
	}

	public String getTraineePriority() {
		if(StringUtility.hasValue(this.traineeName) && StringUtility.hasValue(this.traineePriority)
				&& this.traineePriority.equalsIgnoreCase("Y"))
			return "YES";
		else if(!StringUtility.hasValue(this.traineeName))
			return "";
		else
			return "NO";
	}

	public void setTraineePriority(String traineePriority) {
		this.traineePriority = traineePriority;
	}

	public String getTraineePriorityProgram() {
		if(StringUtility.hasValue(this.traineeName) && StringUtility.hasValue(this.traineePriority)
				&& this.traineePriority.equalsIgnoreCase("Y"))
				return this.traineePriorityProgram;
		else if(!StringUtility.hasValue(this.traineeName))
			return "";
		else
			return "";
		//return traineePriorityProgram;
	}

	public void setTraineePriorityProgram(String traineePriorityProgram) {
		this.traineePriorityProgram = traineePriorityProgram;
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

	public String getTraineeGoal1() {
		return traineeGoal1;
	}

	public void setTraineeGoal1(String traineeGoal1) {
		this.traineeGoal1 = traineeGoal1;
	}

	public String getTraineeGoal2() {
		return traineeGoal2;
	}

	public void setTraineeGoal2(String traineeGoal2) {
		this.traineeGoal2 = traineeGoal2;
	}

	public String getTraineeGoal3() {
		return traineeGoal3;
	}

	public void setTraineeGoal3(String traineeGoal3) {
		this.traineeGoal3 = traineeGoal3;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
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

	public String getTrainerPtbProgress() {
		return trainerPtbProgress;
	}

	public void setTrainerPtbProgress(String trainerPtbProgress) {
		this.trainerPtbProgress = trainerPtbProgress;
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

	public void setSubReportData(
			Collection<TnspIncidentTraineeSubReportData> subReportData) {
		this.subReportData = subReportData;
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

	public String getTrainerRequestNumber() {
		return (StringUtility.hasValue(trainerRequestNumber) ? trainerRequestNumber : "");
	}

	public void setTrainerRequestNumber(String trainerRequestNumber) {
		this.trainerRequestNumber = trainerRequestNumber;
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

	public String getTnspUnitDescription() {
		return tnspUnitDescription;
	}

	public void setTnspUnitDescription(String tnspUnitDescription) {
		this.tnspUnitDescription = tnspUnitDescription;
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
