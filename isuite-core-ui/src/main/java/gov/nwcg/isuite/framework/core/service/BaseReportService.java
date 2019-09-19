package gov.nwcg.isuite.framework.core.service;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.design.JasperDesign;

public class BaseReportService extends BaseService {
	private static final String PARAMETER_REPORT_OUTPUT_FOLDER = "REPORT_OUTPUT_FOLDER";
	private static final String PARAMETER_REPORT_OUTPUT_URL = "REPORT_OUTPUT_URL";
	private static final String PARAMETER_REPORT_SOURCE_PATH = "REPORT_SOURCE_PATH";
 
	public static final long MILLISECS_PER_MINUTE = 60*1000;
    public static final long MILLISECS_PER_HOUR   = 60*MILLISECS_PER_MINUTE;
    protected static final long MILLISECS_PER_DAY = 24*MILLISECS_PER_HOUR;

    private static final String REPORT_EXTENSION_PDF = "pdf";
	private static final String REPORT_EXTENSION_EXCEL = "xls";
	
	private String getDestinationFileName(IReport iReport) throws Exception {
		String fileExtension = REPORT_EXTENSION_PDF;
		
		if(iReport.isExportToExcel()) {
			fileExtension = REPORT_EXTENSION_EXCEL;
		}
		
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return "rpt" + timestamp + "." + fileExtension;
	}

	protected CourseOfActionVo buildCompleteCoa(){
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("REPORT COMPLETE");
		coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		return coaVo;
	}
	
	
	
	protected CourseOfActionVo buildNoDataCoa(String rptName, String detailMessageProperty) {
		String detailMessage="No data available for "+rptName+" Report that meets the following criteria:\n";
		detailMessage=detailMessage+super.getIsuiteProperty(detailMessageProperty);
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("NO DATA");
		coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coaVo.setMessageVo(new MessageVo("text.reports"
										, "info.generic"
										, new String[]{detailMessage}
										, MessageTypeEnum.CRITICAL));
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		return coaVo;
	}
	
	protected CourseOfActionVo buildNoDataCoa(String rptName) {
		
		CourseOfActionVo coaVo = new CourseOfActionVo();
		coaVo.setCoaName("NO DATA");
		coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coaVo.setMessageVo(new MessageVo("text.reports"
										, "info.generic"
										, new String[]{"No data available for " + rptName + " Report."}
										, MessageTypeEnum.CRITICAL));
		coaVo.setIsDialogueEnding(Boolean.TRUE);
		return coaVo;
	}
	
	protected String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_FOLDER);

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() + fileName;
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

	protected String getOutputUrl(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

		try{
			UserSessionVo usvo = (UserSessionVo)super.context.getBean("userSessionVo");
			
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_URL);

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				// use from db
				if(spEntity.getParameterValue().contains("localhost"))
					return spEntity.getParameterValue() + fileName;

				// use explicit localhost value
				if(null != usvo && BooleanUtility.isTrue(usvo.getLocalHostLogin())){
					return "http://localhost:59123/isuite/reportsoutput/"+fileName;
				}

				// use from db
				return spEntity.getParameterValue() + fileName;
				
			}else{
				return fileName;
			}

		}catch(Exception e){
			throw e;
		}
	}

	protected String getSourcePath() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao)super.context.getBean("systemParameterDao");

		try{
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_SOURCE_PATH);

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){
				return spEntity.getParameterValue() ;
			}else{
				return "";
			}

		}catch(Exception e){
			throw e;
		}
	}
	
	protected JasperDesign getJasperDesignFromTemplate(String templateName) throws Exception {
		ReportBuilder builder = new ReportBuilder(super.servletContext);
		builder.applicationContext=this.context;
		return builder.getJasperDesignFromTemplate(templateName);
		//return new ReportBuilder(super.servletContext).getJasperDesignFromTemplate(templateName);
	}
	
	/**
	 * Compiles a JasperDesign object, and prints the result as a file on the file system
	 * @param iReport
	 * @param jasperDesign
	 * @return
	 * @throws ServiceException
	 */
	protected String generateReportFromJasperDesign(IReport iReport, JasperDesign jasperDesign) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder reportBuilder = new ReportBuilder(super.servletContext);
		reportBuilder.applicationContext=this.context;

		Report report = new ReportImpl();
		report.setReportName(iReport.getReportName());
		report.setDateRequested(new Date());
		report.setUserId(getUserVo().getId());
		if(report.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			try {
				report.setUser(userDao.getById(report.getUserId(), UserImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		ReportDao reportDao = (ReportDao)super.context.getBean("reportDao");
		
		try {
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
			
			String fileName = this.getDestinationFileName(iReport);

			report.setFileName(fileName);			
						
			String outputFile = this.getOutputFile(fileName);

			reportBuilder.createReportFromJasperDesign(iReport, jasperDesign, outputFile);
			report.setDateGenerated(new Date());
						
			return this.getOutputUrl(fileName);

		}catch(ReportException re){
			report.setErrorDesc(re.toString());
			throw new ServiceException(re);
		}catch(Exception e){
			report.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(report);
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
	}

	/**
	 * Generates a normal (non-Custom Report)
	 * @param report
	 * @return
	 * @throws ServiceException
	 */
	protected String generateReport(IReport iReport) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder reportBuilder = new ReportBuilder(super.servletContext);
		ReportBuilder2 reportBuilder2 = new ReportBuilder2(super.servletContext);
		reportBuilder.applicationContext=this.context;
		reportBuilder2.applicationContext=this.context;
		
		Report report = new ReportImpl();
		report.setReportName(iReport.getReportName());
		report.setDateRequested(new Date());
		report.setUserId(getUserVo().getId());
		if(report.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			try {
				report.setUser(userDao.getById(report.getUserId(), UserImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		ReportDao reportDao = (ReportDao)super.context.getBean("reportDao");
		
		try {
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty()){
				reportBuilder.setUrlContext(urlContext);
				reportBuilder2.setUrlContext(urlContext);
			}
			
			String fileName = this.getDestinationFileName(iReport);

			report.setFileName(fileName);			
						
			String outputFile = this.getOutputFile(fileName);

			if(BooleanUtility.isTrue(iReport.isExportToExcel())){
				// Create the pdf report. Set the name of the output file.
				reportBuilder.createPdfReport(iReport, outputFile);
				report.setDateGenerated(new Date());
			}else{
				// Create the pdf report. Set the name of the output file.
				reportBuilder2.createPdfReport(iReport, outputFile);
			}
			
			return this.getOutputUrl(fileName);

		}catch(ReportException re){
			report.setErrorDesc(re.toString());
			throw new ServiceException(re);
		}catch(Exception e){
			report.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(report);
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
	}
	
	/**
	 * Create a Report Object with the associated Incident Id or Incident Group Id.
	 * 
	 * @param report
	 * @param incidentId
	 * @param incidentGroupId
	 * @return String URL to the file for display
	 * @throws ServiceException
	 */
	protected String generateReport(IReport iReport, Long incidentId, Long incidentGroupId) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder2 reportBuilder = new ReportBuilder2(super.servletContext);
		reportBuilder.applicationContext=this.context;

		Report report = new ReportImpl();
		report.setReportName(iReport.getReportName());
		report.setDateRequested(new Date());
		report.setUserId(getUserVo().getId());
		if(report.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			try {
				report.setUser(userDao.getById(report.getUserId(), UserImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		
		if (incidentId != null && incidentId > 0) {
			report.setIncidentId(incidentId);
			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			try {
				report.setIncident(incidentDao.getById(incidentId, IncidentImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		} else if (incidentGroupId != null && incidentGroupId > 0) {
			report.setIncidentGroupId(incidentGroupId);
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
			try {
				report.setIncidentGroup(incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		
		ReportDao reportDao = (ReportDao)super.context.getBean("reportDao");
		
		try {
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
			
			String fileName = this.getDestinationFileName(iReport);

			report.setFileName(fileName);
						
			String outputFile = this.getOutputFile(fileName);

			// Create the pdf report. Set the name of the output file.
			reportBuilder.createPdfReport(iReport, outputFile);

			report.setDateGenerated(new Date());
						
			return this.getOutputUrl(fileName);

		}catch(ReportException re){
			report.setErrorDesc(re.toString());
			throw new ServiceException(re);
		}catch(Exception e){
			report.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(report);
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
	}

	protected IncidentDao getIncidentDao() {
		IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
		return incidentDao;
	}
	
	protected IncidentGroupDao getIncidentGroupDao() {
		IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
		return incidentGroupDao;
	}
	
	protected Collection<Incident> getIncidentsByIncidentIds(Collection<Long> ids) throws ServiceException {
		Collection<Incident> incidents = null;
		
		try {
			incidents = getIncidentDao().getAllByIds(ids);
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return incidents;
	}

	protected Incident getIncidentByIncidentId(Long id) throws ServiceException {
		Incident incident = null;
		
		try {
			incident = getIncidentDao().getById(id);
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return incident;
	}
		
	protected Collection<Long> getIncidentIdsByIncidentGroupId(Long id) throws ServiceException {
		Collection<Long> ids = null;
		try {
			ids = getIncidentGroupDao().getIncidentIdsInGroup(id);
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return ids;
	}
}
