package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.report.generator.ReportGenerator;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class ReportGeneratorImpl extends BaseService implements ReportGenerator {

	private static final String PARAMETER_REPORT_OUTPUT_FOLDER = "REPORT_OUTPUT_FOLDER";
	private static final String PARAMETER_REPORT_OUTPUT_URL = "REPORT_OUTPUT_URL";
	private static final String PARAMETER_REPORT_SOURCE_PATH = "REPORT_SOURCE_PATH";
	private static final String PARAMETER_REPORT_IMAGE_PATH = "REPORT_IMAGE_PATH";
	
	private static final String REPORT_EXTENSION_PDF = "pdf";
	private static final String REPORT_EXTENSION_EXCEL = "xls";

	private String sourcePath="";
	private String imagePath="";
	private String urlContext="";
	private String reportOutputUrl="";
	private String reportOutputFolder="";
	
	public abstract <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException;

	protected String getDestinationFileName(String fileExtension) throws Exception {
		if(fileExtension == null || "".equals(fileExtension)){
			throw new ServiceException("Invalid extension for the report name.");
		}
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

		return "rpt" + timestamp + "." + fileExtension;
	}

	protected String getOutputFile(String fileName) throws Exception {

		try {
			UserSessionVo usvo = (UserSessionVo)super.context.getBean("userSessionVo");
			if(!StringUtility.hasValue(this.reportOutputFolder)){
				SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");
				SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_FOLDER);
				this.reportOutputFolder=spEntity.getParameterValue();
			}

			if(StringUtility.hasValue(this.reportOutputFolder)){
				return this.reportOutputFolder+fileName;
			}else
				return fileName;

		} catch (Exception e) {
			throw e;
		}
	}

	protected String getOutputUrl(String fileName) throws Exception {
		

		try {
			UserSessionVo usvo = (UserSessionVo)super.context.getBean("userSessionVo");

			if(!StringUtility.hasValue(this.reportOutputUrl)){
				SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");
				SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_URL);
				reportOutputUrl=spEntity.getParameterValue();
			}

			// use explicit localhost value
			if(null != usvo && BooleanUtility.isTrue(usvo.getLocalHostLogin())){
				return "http://localhost:59123/isuite/reportsoutput/"+fileName;
			}
			
			// use from db
			if(reportOutputUrl.contains("localhost"))
				return reportOutputUrl + fileName;

			// use explicit localhost value
			if(null != usvo && BooleanUtility.isTrue(usvo.getLocalHostLogin())){
				return "http://localhost:59123/isuite/reportsoutput/"+fileName;
			}
			
			// use from db
			return reportOutputUrl + fileName;

		} catch (Exception e) {
			throw e;
		}
	}

	protected String getSourcePath() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");

		if(StringUtility.hasValue(sourcePath)) return sourcePath;
		
		try {
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_SOURCE_PATH);

			if ((null != spEntity) && (null != spEntity.getParameterValue())
					&& (!spEntity.getParameterValue().isEmpty())) {
				sourcePath=spEntity.getParameterValue();
				return spEntity.getParameterValue();
			} else {
				return "";
			}

		} catch (Exception e) {
			throw e;
		}
	}

	protected String getImagePath() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");

		if(StringUtility.hasValue(imagePath)) return imagePath;
		try {
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_IMAGE_PATH);

			if ((null != spEntity) && (null != spEntity.getParameterValue())
					&& (!spEntity.getParameterValue().isEmpty())) {
				imagePath=spEntity.getParameterValue();
				return spEntity.getParameterValue();
			} else {
				return "";
			}

		} catch (Exception e) {
			throw e;
		}
	}
	
	protected Report prepareReport() throws Exception {
		return prepareReport(REPORT_EXTENSION_PDF);
	}

	protected Report prepareReport(String fileExtension) throws Exception {
		String fileName = getDestinationFileName(fileExtension);
		
		Report rpt = new ReportImpl();
		rpt.setFileName(fileName);
		rpt.setDateRequested(new Date());
		rpt.setUserId(getUserVo().getId());
		if (rpt.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao) super.context.getBean("userDao");
			try {
				rpt.setUser(userDao.getById(rpt.getUserId(), UserImpl.class));
			} catch (PersistenceException pe) {
				throw new ServiceException(pe);
			}
		}
		return rpt;
	}

	protected Report createAndSaveReport() throws ServiceException {
		Report rpt = null;
		try {
			rpt = prepareReport();
			
			ReportDao reportDao = (ReportDao) super.context.getBean("reportDao");
			reportDao.save(rpt);
			
		} catch (PersistenceException pe) {
			super.handleException(pe);
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return rpt;
	}
	
	protected String createAndSaveReport(IReport report) throws ServiceException {
		Report rpt = null;
		try {
			if(report.isExportToExcel()) {
				rpt = prepareReport(REPORT_EXTENSION_EXCEL);
			} else {
				rpt = prepareReport(REPORT_EXTENSION_PDF);
			}
		} catch (Exception e) {
			super.handleException(e);
		}
		return createAndSaveReport(rpt, report);
	}
	
	protected String createAndSaveReport(Report rpt, IReport report) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder2 reportBuilder2 = new ReportBuilder2(servletContext);
		ReportBuilder reportBuilder = new ReportBuilder(servletContext);
		reportBuilder2.applicationContext=this.context;
		reportBuilder.applicationContext=this.context;
		ReportDao reportDao = (ReportDao) context.getBean("reportDao");

		try {
			if(!StringUtility.hasValue(this.urlContext)){
				urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			}
			if (null != urlContext && !urlContext.isEmpty()){
				reportBuilder.setUrlContext(urlContext);
				reportBuilder2.setUrlContext(urlContext);
			}
  
			rpt.setReportName(report.getReportName());
			
			String outputFile = this.getOutputFile(rpt.getFileName());

			reportBuilder.applicationContext=super.context;
			reportBuilder2.applicationContext=super.context;
			reportBuilder2.reportSourcePathVal=this.getSourcePath();
			
			// Create the report. Set the name of the output file.
			if(report.isExportToExcel()) {
				reportBuilder.createExcelReport(report, outputFile);
			} else {
				reportBuilder2.createPdfReport(report, outputFile);
			}
			
			rpt.setDateGenerated(new Date());

			return this.getOutputUrl(rpt.getFileName());
			
		} catch (ReportException re) {
			rpt.setErrorDesc(re.toString());
			throw new ServiceException(re);
		} catch (Exception e) {
			rpt.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(rpt);
			} catch (Exception e) {
				super.handleException(e);
			}
		}
	}

	protected DialogueVo noDataMessage(String parm, DialogueVo dialogueVo) {
    	{
			MessageVo messageVo = new MessageVo();
			messageVo.setMessageProperty("error.900010");
			messageVo.setParameters(new String[] { parm });
		    messageVo.setTitleProperty("text.timeReports");

		    CourseOfActionVo coa = new CourseOfActionVo();
		    coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		    coa.setMessageVo(messageVo);
		    coa.setIsDialogueEnding(true);
		    
		    dialogueVo.setCourseOfActionVo(coa);
		    return dialogueVo;
		}
    }
//	protected Collection<IncidentResourceVo> getIncidentResources(TimeReportFilter filter, DailyTimePost dtp)
//			throws ServiceException {
//		// get Time Posts
//		Collection<IncidentResourceVo> incidentResources = null;
//
//		try {
//			incidentResources = dtp.getTimePosts(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport());
//		} catch (ServiceException e) {
//			super.handleException(e);
//		}
//
//		return incidentResources;
//	}
	
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
	
	protected List<Long> getIncidentIdsByIncidentGroupId(Long id) throws ServiceException {
		List<Long> ids = null;
		try {
			ids = (List<Long>)getIncidentGroupDao().getIncidentIdsInGroup(id);
			Collections.sort(ids);
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return ids;
	}
}
