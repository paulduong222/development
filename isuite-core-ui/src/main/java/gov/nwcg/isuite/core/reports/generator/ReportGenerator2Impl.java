package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.reports.data.CostReportChartReportData;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.ReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.report.generator.ReportGenerator2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public abstract class ReportGenerator2Impl extends BaseService implements ReportGenerator2 {

	private static final String PARAMETER_REPORT_OUTPUT_FOLDER = "REPORT_OUTPUT_FOLDER";
	private static final String PARAMETER_REPORT_OUTPUT_URL = "REPORT_OUTPUT_URL";
	private static final String PARAMETER_REPORT_SOURCE_PATH = "REPORT_SOURCE_PATH";
	private static final String PARAMETER_REPORT_IMAGE_PATH = "REPORT_IMAGE_PATH";

	public abstract <E extends ReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException;

	protected  String getDestinationFileName() throws Exception {

		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

		return "rpt" + timestamp + ".pdf";
	}

	protected String getOutputFile(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");

		try {
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_FOLDER);

			if ((null != spEntity) && (null != spEntity.getParameterValue())
					&& (!spEntity.getParameterValue().isEmpty())) {
				return spEntity.getParameterValue() + fileName;
				
			} else {
				return fileName;
			}

		} catch (Exception e) {
			throw e;
		}
	}

	protected String getOutputUrl(String fileName) throws Exception {
		SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");

		try {
			UserSessionVo usvo = (UserSessionVo)super.context.getBean("userSessionVo");
			
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_OUTPUT_URL);

			if( (null != spEntity) && ( null != spEntity.getParameterValue()) && (!spEntity.getParameterValue().isEmpty()) ){

				// use explicit localhost value
				if(null != usvo && BooleanUtility.isTrue(usvo.getLocalHostLogin())){
					return "http://localhost:59123/isuite/reportsoutput/"+fileName;
				}
				
				// use from db
				if(spEntity.getParameterValue().contains("localhost"))
					return spEntity.getParameterValue() + fileName;


				// use from db
				return spEntity.getParameterValue() + fileName;
				
			}else{
				return fileName;
			}

		} catch (Exception e) {
			throw e;
		}
	}

	protected String getSourcePath() throws Exception {
		SystemParameterDao spDao = (SystemParameterDao) context.getBean("systemParameterDao");

		try {
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_SOURCE_PATH);

			if ((null != spEntity) && (null != spEntity.getParameterValue())
					&& (!spEntity.getParameterValue().isEmpty())) {
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

		try {
			SystemParameter spEntity = spDao.getByParameterName(PARAMETER_REPORT_IMAGE_PATH);

			if ((null != spEntity) && (null != spEntity.getParameterValue())
					&& (!spEntity.getParameterValue().isEmpty())) {
				return spEntity.getParameterValue();
			} else {
				return "";
			}

		} catch (Exception e) {
			throw e;
		}
	}

	protected Report prepareReport() throws Exception {
		String fileName = getDestinationFileName();
		
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
			rpt = prepareReport();
		} catch (Exception e) {
			super.handleException(e);
		}
		return createAndSaveReport(rpt, report);
	}
	
	protected String createAndSaveReport(Report rpt, IReport report) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder2 reportBuilder = new ReportBuilder2(super.servletContext);
		reportBuilder.applicationContext=this.context;
		ReportDao reportDao = (ReportDao) context.getBean("reportDao");

		try {
			
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if (null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
  
			rpt.setReportName(report.getReportName());
			
			String outputFile = this.getOutputFile(rpt.getFileName());

			// Create the pdf report. Set the name of the output file.
			reportBuilder.createPdfReport(report, outputFile);
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
	
	protected String buildNoDataMessage(String rptName) {
		String detailMessage="No data available for "+rptName;
		
		return detailMessage;
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
	
	protected IncidentDao getIncidentDao() throws ServiceException, PersistenceException {
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
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		
		return incidents;
	}

	protected Incident getIncidentByIncidentId(Long id) throws ServiceException {
		Incident incident = null;
	
		try {
			incident = getIncidentDao().getById(id);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		
		return incident;
	}
	
	protected List<Long> getIncidentIdsByIncidentGroupId(Long id) throws ServiceException {
		List<Long> ids = null;
		try {
			ids = (List<Long>)getIncidentGroupDao().getIncidentIdsInGroup(id);
			Collections.sort(ids);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		
		return ids;
	}
	
	protected IncidentGroup getIncidentGroupById(Long id) throws ServiceException {
		IncidentGroup incidentGroup = null; 
		try {
			incidentGroup = getIncidentGroupDao().getById(id, IncidentGroupImpl.class);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		
		return incidentGroup;
	}
	
	protected void FormatTotalAmount(Collection<CostReportChartReportData> list) {
		if(list == null || list.isEmpty())
			return;
		
		Double total = 0.0;
		
		for(CostReportChartReportData data: list)
			total += data.getTotalAmount();
		
		for(CostReportChartReportData data: list) {
			int percentageTotal = 0;
			
			if(total != 0.0) {
				percentageTotal = (int)Math.round((data.getTotalAmount()*100)/total);
				data.setTotalAmounInPercentage( String.valueOf(percentageTotal)+"%");
			} else {
				percentageTotal = 0;
				data.setTotalAmounInPercentage("");
			}
		}
	}
}
