package gov.nwcg.isuite.core.reports.data;

import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GlidePathReportData {
	
	public static final int MINIMUM_NUMBER_OF_DAYS = 7;
	public static final int MAXIMUM_NUMBER_OF_DAYS = 30;

	private String sectionName; // Used in group expression by Jasper to break content into separate tabs.
	private List<GlidePathReportResourceData> resourceDataList;	// Is null for summary sheet
	private List<GlidePathReportSummaryData> summaryDataList;	// Is null for all section sheets
	
	public String getSectionName() {
		return sectionName;
	}
	
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	public List<GlidePathReportResourceData> getResourceDataList() {
		return resourceDataList;
	}
	
	public void setResourceDataList(
			List<GlidePathReportResourceData> resourceDataList) {
		this.resourceDataList = resourceDataList;
	}
	
	public List<GlidePathReportSummaryData> getSummaryDataList() {
		return summaryDataList;
	}
	
	public void setSummaryDataList(List<GlidePathReportSummaryData> summaryDataList) {
		this.summaryDataList = summaryDataList;
	}
	
	public JRBeanCollectionDataSource getResourceDataSource() {
		return new JRBeanCollectionDataSource(this.getResourceDataList());
	}
	
	public JRBeanCollectionDataSource getSummaryDataSource() {
		return new JRBeanCollectionDataSource(this.getSummaryDataList());
	}
}
