package gov.nwcg.isuite.framework.report;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.jasperreports.JasperReportsUtils;

public class ReportBuilder {
	private ServletContext servletContext=null;
	public ApplicationContext applicationContext;
	private String urlContext="isuite";
	
	// Property prefix for dynamically naming worksheets in an excel export
	private static final String EXCEL_SHEET_NAME_PROPERTY_PREFIX = "net.sf.jasperreports.export.xls.sheet.names.";
	
	public ReportBuilder() {
	}

	public ReportBuilder(ServletContext sc) {
		servletContext=sc;
	}
	
	/**
	 * Builds a pdf report.
	 * 
	 * @param reportObject
	 * 			the IReport implementation object
	 * @param destinationFile
	 * 			the destination file 
	 * @return
	 * 		the created pdf report file
	 * @throws ReportException
	 */
	//TODO: Manu - Refactor this method to createReport
	public synchronized String createPdfReport(IReport iReport, String destinationFile) throws ReportException {
		try{
			JasperReport jasperReport = this.createJasperReportFromPath(iReport, destinationFile);
			
			this.printReport(jasperReport, iReport, destinationFile);
		}catch(Exception e){
			e.printStackTrace();
			throw new ReportException(e.getMessage());
		}
		return destinationFile;
	}
	
	// TODO: Manu - Delete this method after createPdfReport has been renamed to createReport
	public String createExcelReport(IReport iReport, String destinationFile) throws ReportException {
		return createPdfReport(iReport, destinationFile);
	}
	
	/**
	 * Compiles from a JasperDesign object and prints the report as a file on to the file system. 
	 * @param iReport
	 * @param jasperDesign
	 * @param destinationFile
	 * @return
	 * @throws JRException
	 * @throws ReportException
	 */
	public String createReportFromJasperDesign(IReport iReport, JasperDesign jasperDesign, String destinationFile) throws JRException, ReportException {
		try{
			JasperReport jasperReport = this.createJasperReportFromDesign(iReport, jasperDesign);
			this.printReport(jasperReport, iReport, destinationFile);
		}catch(Exception e){
			e.printStackTrace();
			throw new ReportException(e.getMessage());
		}
		return destinationFile;
	}
	
	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {
		if(applicationContext!=null){
			SystemParameterDao spDao = (SystemParameterDao)applicationContext.getBean("systemParameterDao");
			
			SystemParameter entity = spDao.getByParameterName(paramName.name());
			
			return entity.getParameterValue();
		}
		
		return "";
	}
	
	/**
	 * Returns the source path to the report jrxml file.
	 * 
	 * @param templateName
	 * 		the name of the jrxml template file
	 * @return
	 * 		the template source path
	 * @throws Exception
	 */
	private String getTemplateSourcePath(String templateName) throws Exception{
		
		//Append template name with the '.jrxml' suffix, if required.
		templateName = getTemplateNameWithExtension(templateName);
		
		String path = "Webroot" +
		   System.getProperty("file.separator") +
		   "reports" +
		   System.getProperty("file.separator") +
		   templateName; 

		/*
		 * Check if the webroot path is valid,
		 * if its valid, the context is probably running inside eclipse,
		 * otherwise probably running inside tomcat
		 */
		if(FileUtil.fileExists(path)){
			return path;
		}else{
			String reportSourcePath=this.getSystemParamValue(SystemParameterTypeEnum.REPORT_SOURCE_PATH);
			if(StringUtility.hasValue(reportSourcePath)){
				return reportSourcePath+templateName;
			}
	        String basePath= servletContext.getRealPath(servletContext.getContextPath());
	        int idx = basePath.lastIndexOf(urlContext);

	        if(idx > 0){
	        	path = basePath.substring(0, idx) +
				   "reports" +
				   System.getProperty("file.separator") +
				   templateName;
	        }else{
	        	path= basePath + 
				   System.getProperty("file.separator") +
				   "reports" +
				   System.getProperty("file.separator") +
				   templateName;
	        }
	        
	        return path;
		}
	}

	/**
	 * Returns the report name with the .jrxml suffix.
	 * 
	 * @param name
	 * 			the name of the template
	 * @return
	 * 		the template name with .jrxml extension
	 * @throws Exception
	 */
	private String getTemplateNameWithExtension(String templateName) throws Exception{
		String templateNameWithExtension = templateName;
		
		if((null != templateName) && (!templateName.isEmpty())){
			if(!templateName.endsWith(".jrxml"))
				templateNameWithExtension = templateName + ".jrxml";
		} else {
			throw new Exception("Unable to determine JRXML template name with null or empty name parameter.");
		}
		return templateNameWithExtension;
	}
	
	/**
	 * Writes the JasperReport object on to the file system as a PDF or excel 
	 * @param jasperReport
	 * @param iReport
	 * @param destinationFile
	 * @throws Exception
	 */
	private void printReport(JasperReport jasperReport, IReport iReport, String destinationFile) throws Exception {
		// init outputstream holder
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		if(iReport.isExportToExcel()) {
			
//			Map<Object, Object> finalParams = new HashMap<Object, Object>();
//			finalParams.put(JRXlsAbstractExporterParameter.SHEET_NAMES, new String[] {"first","second","third"});
//			finalParams.putAll(iReport.toParameterMap());
			
			JasperReportsUtils.renderAsXls(
					jasperReport
					//, finalParams
					, iReport.toParameterMap()
					, iReport.getReportData()
					, os);
		} else {
			JasperReportsUtils.renderAsPdf(jasperReport
					, iReport.toParameterMap()
					, iReport.getReportData()
					, os);
		}

		FileUtil.writeFile(os,destinationFile);
	}
	/**
	 * Creates a JasperReport object from a JRXML file from a given path. Also compiles any
	 * sub-report associated with this JRXML.
	 * @param iReport
	 * @param destinationFile
	 * @return
	 * @throws ReportException
	 */
	private JasperReport createJasperReportFromPath(IReport iReport, String destinationFile) throws ReportException {
		try{
			/*
			 * check the reportObject
			 */
			if( null == iReport)
				throw new ReportException("Unable to create Jasper report with null reportObject.");

			/*
			 * build the source report file path
			 */
			String templatePath = getTemplateSourcePath(iReport.getReportName());


			Collection<String> subReports = new ArrayList<String>();
			
			if(iReport.getSubReports().size()>0){
				for(String subReport : iReport.getSubReports()){
					subReports.add(getTemplateSourcePath(subReport));
				}
			}
			
			// compile the report
			JasperReport jasperReport=this.compileReport(templatePath,subReports.toArray());
			
			// Set worksheet names, if this is an excel export
			if(iReport.isExportToExcel()) {
				setExcelWorksheetNameProperties(iReport, jasperReport);
			}

			return jasperReport;

		}catch(Exception e){
			throw new ReportException(e.getMessage());
		}
		
	}
	
	/**
	 * Complies a JasperDesign object to a JasperReport object. 
	 * @param iReport
	 * @param jasperDesign
	 * @return
	 * @throws ReportException
	 */
	//TODO: Might need to add compilation logic for possible subreports in custom reports
	private JasperReport createJasperReportFromDesign(IReport iReport, JasperDesign jasperDesign) throws ReportException {
		JasperReport jasperReport = null;
		try {
			jasperReport = JasperCompileManager.compileReport(jasperDesign);

			// Set worksheet names, if this is an excel export
			if(iReport.isExportToExcel()) {
				setExcelWorksheetNameProperties(iReport, jasperReport);
			}
			
		} catch (JRException e) {
			e.printStackTrace();
			throw new ReportException("Unable to compile the report: " + iReport.getReportName());
		}
		return jasperReport;
	}
	
	// Private method to set worksheet names in a compiled JasperReport object.
	// Although direct property setting in possible in a JRXML, that method does not work
	// consistently in a scenario where number of worksheets are dynamic and/or are created
	// by using sub-reports. Adding worksheet names directly as a property on the 
	// compiled JasperReport object eliminates that variation. 
	// Please ensure that if this method is being used, the underlying JRXML(s) do NOT have
	// these same properties set. Those propertie values will likely override the ones being 
	// set here.
	//
	// Example:
	//jasperReport.setProperty("net.sf.jasperreports.export.xls.sheet.names.1", "SheetNameA");
	//jasperReport.setProperty("net.sf.jasperreports.export.xls.sheet.names.2", "SheetNameB");
	//jasperReport.setProperty("net.sf.jasperreports.export.xls.sheet.names.3", "SheetNameC");
	//jasperReport.setProperty("net.sf.jasperreports.export.xls.sheet.names.4", "SheetNameD");
	
	
	private static JasperReport setExcelWorksheetNameProperties(IReport iReport, JasperReport jasperReport) {
		// Do nothing if this isn't to be exported to Excel
		if(iReport.isExportToExcel()) {
			List<String> worksheetNames = iReport.getExcelWorksheetNames();
			if(worksheetNames!=null && worksheetNames.size()>0) {
				for(int i=0; i<worksheetNames.size(); i++){
					jasperReport.setProperty(EXCEL_SHEET_NAME_PROPERTY_PREFIX + i, worksheetNames.get(i));
				}
			}
		}
		return jasperReport;
	}

	/**
	 * Private method that compiles a jasper jrmxl report file along with any subreports.
	 * @param report the jrxml report file to compile
	 * @param subReports optional array of subreport names
	 * @return the compiled jasper report
	 * @throws Exception
	 */
	private synchronized JasperReport compileReport(String report,Object... subReports) throws Exception {
		JasperReport jasperReport = null;
		
		/*
		 * Try to compile the report
		 */
		jasperReport = JasperCompileManager.compileReport(report);

		/*
		 * Try to compile all subreports needed for the report
		 */
		if( (null != subReports ) && (subReports.length>0) ){
			for(int i=0;i<subReports.length;i++){
				String subReport = (String)subReports[i];
				
				//JasperReport jasperSubReport = JasperCompileManager.compileReport(subReport);
				JasperCompileManager.compileReportToFile(subReport);
				
				//TODO:  Generate and read the .jasper data in memory.
				//       Writing to the file system is ok for now.
				//       However, there is really no need to write the .jasper file to the file system
				//        and then load it from the file system again later.  Just generate the data in memory and leave it there.
			}
		}
		
		if(null==jasperReport)
			throw new ReportException("Unable to compile the report: " + report);
		
		return jasperReport;
	}
	
	/**
	 * Returns a JasperDesign object from a JRXML template on the file system. 
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	public JasperDesign getJasperDesignFromTemplate(String templateName) throws Exception {
		String templatePath = getTemplateSourcePath(templateName);
		File templateFile=new File(templatePath);
		JasperDesign jasperDesign= JRXmlLoader.load(templateFile);
		
		return jasperDesign;
	}

	/**
	 * Returns the servletContext.
	 *
	 * @return 
	 *		the servletContext to return
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * Sets the servletContext.
	 *
	 * @param servletContext 
	 *			the servletContext to set
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * Returns the urlContext.
	 *
	 * @return 
	 *		the urlContext to return
	 */
	public String getUrlContext() {
		return urlContext;
	}

	/**
	 * Sets the urlContext.
	 *
	 * @param urlContext 
	 *			the urlContext to set
	 */
	public void setUrlContext(String urlContext) {
		this.urlContext = urlContext;
	}

}
