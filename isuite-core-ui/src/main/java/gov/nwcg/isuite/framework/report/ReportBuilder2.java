package gov.nwcg.isuite.framework.report;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.springframework.context.ApplicationContext;

public class ReportBuilder2 {
	private ServletContext servletContext=null;
	public ApplicationContext applicationContext;
	private String urlContext="isuite";
	private Boolean isRunningInDevEnv=false;
	public String reportSourcePathVal="";
	
	// Property prefix for dynamically naming worksheets in an excel export
	private static final String EXCEL_SHEET_NAME_PROPERTY_PREFIX = "net.sf.jasperreports.export.xls.sheet.names.";
	
	public ReportBuilder2() {
	}

	public ReportBuilder2(ServletContext sc) {
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
	public String createPdfReport(IReport iReport, String destinationFile) throws ReportException {
		JasperPrint jp=null;
		
		try{
			
			String templatePath = getTemplateSourcePath(iReport.getReportName());
			
			Collection<String> subReports = new ArrayList<String>();
			if(iReport.getSubReports().size()>0){
				for(String subReport : iReport.getSubReports()){
					subReports.add(getTemplateSourcePath(subReport));
				}
			}
			
			// compile the report
			int v=this.compileReport(templatePath,subReports.toArray());
			
			JRBeanCollectionDataSource beanDs =
				new JRBeanCollectionDataSource(iReport.getReportData());		

			String jasperpath=templatePath.replaceAll(".jrxml", ".jasper");
			
			jp=JasperFillManager.fillReport(jasperpath,iReport.toParameterMap() ,beanDs);
			if(iReport.isExportToExcel()==true){
	            JRXlsExporter exporterXLS = new JRXlsExporter();
	            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	            FileOutputStream fout = new FileOutputStream(new File(destinationFile));
	            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jp);
	            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outStream);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);	            
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
	            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
	            exporterXLS.exportReport();
	            byte arr[] = outStream.toByteArray();

	            fout.write(arr);
	            fout.flush();
	            fout.close();
	            outStream.close(); 	            
			}else{
				JasperExportManager.exportReportToPdfFile(jp, destinationFile);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new ReportException(e.getMessage());
		}finally{
			if(null != jp)
				jp=null;
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
		templateName = templateName+".jrxml";
		
        String basePath2= servletContext.getRealPath(servletContext.getContextPath());
        if(basePath2.indexOf("Webroot")>0){
        	isRunningInDevEnv=true;        	
        }

        String path="";
        
		/*
		 * Check if the webroot path is valid,
		 * if its valid, the context is probably running inside eclipse,
		 * otherwise probably running inside tomcat
		 */
        String reportSourcePath=reportSourcePathVal;
         if(!StringUtility.hasValue(reportSourcePathVal)){
 			 reportSourcePath=this.getSystemParamValue(SystemParameterTypeEnum.REPORT_SOURCE_PATH);
         }
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

	/**
	 * Private method that compiles a jasper jrmxl report file along with any subreports.
	 * @param report the jrxml report file to compile
	 * @param subReports optional array of subreport names
	 * @return the compiled jasper report
	 * @throws Exception
	 */
	private int compileReport(String report,Object... subReports) throws Exception {
		JasperReport jasperReport = null;
		
		/*
		 * Try to compile the report
		 */
		String jasperfile=report.replace(".jrxml", ".jasper");
		if(isRunningInDevEnv==true || !FileUtil.fileExists(jasperfile)){
			JasperCompileManager.compileReportToFile(report);
		}

		/*
		 * Try to compile all subreports needed for the report
		 */
		if( (null != subReports ) && (subReports.length>0) ){
			for(int i=0;i<subReports.length;i++){
				String subReport = (String)subReports[i];
				jasperfile=subReport.replaceAll(".jrxml", ".jasper");
				if(isRunningInDevEnv==true || !FileUtil.fileExists(jasperfile)){
					JasperCompileManager.compileReportToFile(subReport);
				}
			}
		}
		
		
		return 1;
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
