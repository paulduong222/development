package gov.nwcg.isuite.framework.report;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IReport {

	/**
	 * Returns the name of the report file.
	 * 
	 * @return
	 * 		the report name
	 */
	public String getReportName();
	
	/**
	 * Returns the reportData.
	 * 
	 * @return
	 * 		the reportData to return
	 */
	public Collection<? extends Object> getReportData();
	

	/**
	 * Creates and returns the report parameter map.
	 * 
	 * @return
	 * 		the map of parameters
	 */
	public Map<String,Object> toParameterMap();
	
	/**
	 * Sets the customFields.
	 *
	 * @param customFields 
	 *			the customFields to set
	 */
	public void setCustomFields(Map<String, Object> customFields) ;
	
	/**
	 * Add custom fields (report parameters) at runtime.
	 * 
	 * @param fieldName 
	 * 				name of the parameter
	 * @param value
	 * 				value of the parameter
	 */
	public void addCustomField(String fieldName, Object value);

	/**
	 * Add a subReportName to the report.  
	 * The ReportBuilder will need to compile the sub reports at runtime
	 * so the subreport.jasper files avaiable.
	 * 
	 * @param subReportName
	 * 				name of subReportName to add
	 */
	public void addSubReportName(String subReportName);


	/**
	 * Returns the collection of subReport names needed for the report.
	 * 
	 * @return
	 * 		collection of sub report names
	 */
	public Collection<String> getSubReports();
	
	/**
	 * Called after report instantiation to mark the report as to be exported to excel
	 */
	public void enableExportToExcel();
	
	/**
	 * Indicates if this report is to be exported to excel
	 * @return true if this report is to be exported to excel
	 */
	public boolean isExportToExcel();
	
	/**
	 * Sets values for the worksheet names that will be used in the excel export. 
	 * @param worksheetNameList
	 */
	public void setExcelWorksheetNames(List<String> worksheetNameList);
	
	/**
	 * Returns names of worksheets that will be used in the excel export. 
	 * @return
	 */
	public List<String> getExcelWorksheetNames();
}


	
	
