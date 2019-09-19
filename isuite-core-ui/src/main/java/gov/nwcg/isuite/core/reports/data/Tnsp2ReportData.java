package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Tnsp2ReportData extends BaseReportData {
	private Collection<Tnsp2SubReportData> subReportData = new ArrayList<Tnsp2SubReportData>();
	private JRBeanCollectionDataSource subReportDatasource;

	private Collection<Tnsp2SubReportTnspData> subReportTnspData = new ArrayList<Tnsp2SubReportTnspData>();
	private JRBeanCollectionDataSource subReportTnspDatasource;

	private String field1;
	private String incidentName;
	private String incidentGroupName;
	private String incidentNumber;
	private String reportDateRange;
	
	public Tnsp2ReportData() {
	}

	public Collection<Tnsp2SubReportData> getSubReportData() {
		return subReportData;
	}

	public void setSubReportData(Collection<Tnsp2SubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	public Collection<Tnsp2SubReportTnspData> getSubReportTnspData() {
		return subReportTnspData;
	}

	public void setSubReportTnspData(
			Collection<Tnsp2SubReportTnspData> subReportTnspData) {
		this.subReportTnspData = subReportTnspData;
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

	public String getIncidentGroupName() {
		return incidentGroupName;
	}

	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getReportDateRange() {
		return reportDateRange;
	}

	public void setReportDateRange(String reportDateRange) {
		this.reportDateRange = reportDateRange;
	}

	public JRBeanCollectionDataSource getSubReportDatasource() {
		return new JRBeanCollectionDataSource(this.subReportData);
	}

	public JRBeanCollectionDataSource getSubReportTnspDatasource() {
		return new JRBeanCollectionDataSource(this.subReportTnspData);
	}
	
}
