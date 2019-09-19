package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;

public class Tnsp3ReportData extends BaseReportData {
	private Collection<Tnsp3SubReportData> subReportData = new ArrayList<Tnsp3SubReportData>();
	private JRBeanCollectionDataSource tnsp3SubReportDatasource;

	private Collection<Tnsp3SubReportTnspData> subReportTnspData = new ArrayList<Tnsp3SubReportTnspData>();
	private JRBeanCollectionDataSource tnsp3SubReportTnspDatasource;

		
	private Collection<Tnsp3SubReportPPData> subReportPPData = new ArrayList<Tnsp3SubReportPPData>();
	private JRBeanCollectionDataSource tnsp3SubReportPPDatasource;
	
	private String field1;
	private String incidentName;
	private String incidentNumber;
	private String dateRange;
	private String code1Count;
	private String code2Count;
	private String code3Count;
	private String code4Count;
	private String code5Count;
	private String trainingCount;
	private String incTrainingCount;
	
	public Tnsp3ReportData() {
	}

	public static Tnsp3ReportData getInstance() throws Exception {
		Tnsp3ReportData reportData = new Tnsp3ReportData();

		return reportData;
	}

	public JRBeanCollectionDataSource getTnsp3SubReportDatasource() {
		return new JRBeanCollectionDataSource(this.subReportData);
	}

	public void setTnsp3SubReportDatasource(JRBeanCollectionDataSource tnsp3SubReportDatasource) {
		this.tnsp3SubReportDatasource = tnsp3SubReportDatasource;
	}

	public Collection<Tnsp3SubReportData> getSubReportData() {
		return subReportData;
	}

	public void setSubReportData(Collection<Tnsp3SubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
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

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public Collection<Tnsp3SubReportTnspData> getSubReportTnspData() {
		return subReportTnspData;
	}

	public void setSubReportTnspData(
			Collection<Tnsp3SubReportTnspData> subReportTnspData) {
		this.subReportTnspData = subReportTnspData;
	}

	public JRBeanCollectionDataSource getTnsp3SubReportTnspDatasource() {
		return new JRBeanCollectionDataSource(this.subReportTnspData);
	}

	public String getCode1Count() {
		return code1Count;
	}

	public void setCode1Count(String code1Count) {
		this.code1Count = code1Count;
	}

	public String getCode2Count() {
		return code2Count;
	}

	public void setCode2Count(String code2Count) {
		this.code2Count = code2Count;
	}

	public String getCode3Count() {
		return code3Count;
	}

	public void setCode3Count(String code3Count) {
		this.code3Count = code3Count;
	}

	public String getCode4Count() {
		return code4Count;
	}

	public void setCode4Count(String code4Count) {
		this.code4Count = code4Count;
	}

	public String getCode5Count() {
		return code5Count;
	}

	public void setCode5Count(String code5Count) {
		this.code5Count = code5Count;
	}

	public String getTrainingCount() {
		return trainingCount;
	}

	public void setTrainingCount(String trainingCount) {
		this.trainingCount = trainingCount;
	}

	public String getIncTrainingCount() {
		return incTrainingCount;
	}

	public void setIncTrainingCount(String incTrainingCount) {
		this.incTrainingCount = incTrainingCount;
	}

	public JRBeanCollectionDataSource getTnsp3SubReportPPDatasource() {
		return new JRBeanCollectionDataSource(this.subReportPPData);
	}

	public Collection<Tnsp3SubReportPPData> getSubReportPPData() {
		return subReportPPData;
	}

	public void setSubReportPPData(Collection<Tnsp3SubReportPPData> subReportPPData) {
		this.subReportPPData = subReportPPData;
	}

}
