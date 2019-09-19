package gov.nwcg.isuite.framework.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseReport {
	
	protected static final String REPORT_NAME_SUFFIX_EXPORT_EXCEL ="_export_excel";
	protected static final String REPORT_NAME_SUFFIX_EXPORT_CSV ="_export_csv";
	protected static final String REPORT_NAME_SUFFIX_EXPORT_HTML ="_export_html";
	
	/*
	 * holder for the collection of data objects for the report
	 */
	protected Collection<? extends Object> reportData=null;

	/*
	 * holder for the jasper jrxml file
	 */
	private String reportName="";
	
	private Collection<String> subReports = new ArrayList<String>();
	
	/*
	 * holders for the common header fields
	 */
	private String headerTitle="";
	private String headerSubTitle="";
	
	/*
	 * holders for the common footer fields
	 */
	private String footerDisclaimer="Define the footer disclaimer";
	
	/*
	 * Define custom array to custom defined fields as needed
	 */
	private Map<String,Object> customFields = new HashMap<String,Object>();
	
	/*
	 * Denotes if this report is to be exported to excel
	 */
	private boolean exportToExcel = false;
	
	private List<String> excelWorksheetNames;
	
	
	/**
	 * Returns the collection of report data objects.
	 * 
	 * @return
	 * 		collection of report data objects
	 */
	public Collection<? extends Object> getReportData() {
		return reportData;
	}

	/**
	 * Sets the collection of reportdata. 
	 * This method is marked as protected, force usage to go
	 * through the ireportobject implementing class.
	 * 
	 * @param reportData
	 */
	protected void setReportData(Collection<?> reportData) {
		this.reportData = reportData;
	}

	/**
	 * Returns the reportName.
	 *
	 * @return 
	 *		the reportName to return
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * Sets the reportName.
	 *
	 * @param reportName 
	 *			the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * Returns the headerTitle.
	 *
	 * @return 
	 *		the headerTitle to return
	 */
	public String getHeaderTitle() {
		return headerTitle;
	}

	/**
	 * Sets the headerTitle.
	 *
	 * @param headerTitle 
	 *			the headerTitle to set
	 */
	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
	}

	/**
	 * Returns the headerSubTitle.
	 *
	 * @return 
	 *		the headerSubTitle to return
	 */
	public String getHeaderSubTitle() {
		return headerSubTitle;
	}

	/**
	 * Sets the headerSubTitle.
	 *
	 * @param headerSubTitle 
	 *			the headerSubTitle to set
	 */
	public void setHeaderSubTitle(String headerSubTitle) {
		this.headerSubTitle = headerSubTitle;
	}

	/**
	 * Returns the footerDisclaimer.
	 *
	 * @return 
	 *		the footerDisclaimer to return
	 */
	public String getFooterDisclaimer() {
		return footerDisclaimer;
	}

	/**
	 * Sets the footerDisclaimer.
	 *
	 * @param footerDisclaimer 
	 *			the footerDisclaimer to set
	 */
	public void setFooterDisclaimer(String footerDisclaimer) {
		this.footerDisclaimer = footerDisclaimer;
	}

	/**
	 * Returns the customFields.
	 *
	 * @return 
	 *		the customFields to return
	 */
	public Map<String, Object> getCustomFields() {
		return customFields;
	}

	/**
	 * Sets the customFields.
	 *
	 * @param customFields 
	 *			the customFields to set
	 */
	public void setCustomFields(Map<String, Object> customFields) {
		this.customFields = customFields;
	}

	public void addCustomField(String fieldName, Object value){
		customFields.put(fieldName, value);
	}
	
	/**
	 * Returns a hashmap containing all of the properties and values of this instance
	 * of ReportTemplate.
	 * 
	 * @return
	 * 		map of property/value pairs.
	 */
	public Map<String,Object> toParameterMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("headerTitle", headerTitle);
		map.put("headerSubTitle", headerSubTitle);
		map.put("footerDisclaimer", footerDisclaimer);

		if(customFields.size()>0){
			map.putAll(customFields);
		}
		
		return map;
		
	}
	
	public void addSubReportName(String subReportName) {
		this.subReports.add(subReportName);
	}

	/**
	 * Returns the subReports.
	 *
	 * @return 
	 *		the subReports to return
	 */
	public Collection<String> getSubReports() {
		return subReports;
	}
	
	/**
	 * Called after report instantiation to mark the report as to be exported to excel
	 */
	public void enableExportToExcel(){
		exportToExcel = true;
	}
	
	/**
	 * Indicates if this report is to be exported to excel
	 * @return true if this report is to be exported to excel
	 */
	public boolean isExportToExcel(){
		return exportToExcel;
	}
	
	/**
	 * Used in determining which template (jrxml) file should be used. This method
	 * is used when different templates exist for different export (pdf, excel, csv, HTML)
	 * versions of the report. 
	 * 7/18/2013: At present, only the pdf and excel exports are implemented. 
	 * Returns the report name with the correct suffix.
	 * For a normal report there is no suffix. For a report to be exported,
	 * the correct suffix for the export type is returned. 
	 * @param reportName
	 * @return
	 */
	public String getReportNameWithSuffix(String reportName) {
		if(isExportToExcel()) { 
			return reportName + REPORT_NAME_SUFFIX_EXPORT_EXCEL;
		} else {
			return reportName;
		}
	}

	public List<String> getExcelWorksheetNames() {
		return excelWorksheetNames;
	}

	public void setExcelWorksheetNames(List<String> excelWorksheetNames) {
		this.excelWorksheetNames = excelWorksheetNames;
	}
	
}
