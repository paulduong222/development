package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.ReportImpl;
import gov.nwcg.isuite.core.domain.impl.UserImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ReportDao;
import gov.nwcg.isuite.core.persistence.ReportDemobDao;
import gov.nwcg.isuite.core.persistence.ReportIncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.UserDao;
import gov.nwcg.isuite.core.reports.ActualDemobReport;
import gov.nwcg.isuite.core.reports.AirTravelRequestReport;
import gov.nwcg.isuite.core.reports.AllIncidentResourcesReport;
import gov.nwcg.isuite.core.reports.AvailableForReleaseReport;
import gov.nwcg.isuite.core.reports.CheckoutReport;
import gov.nwcg.isuite.core.reports.DemobPlanningReport;
import gov.nwcg.isuite.core.reports.GroundSupportReport;
import gov.nwcg.isuite.core.reports.ICS209Report;
import gov.nwcg.isuite.core.reports.LastWorkDayReport;
import gov.nwcg.isuite.core.reports.PasswordExpiredReport;
import gov.nwcg.isuite.core.reports.PersonnelTimeReport;
import gov.nwcg.isuite.core.reports.QualificationsReport;
import gov.nwcg.isuite.core.reports.StrikeTeamTaskForceReport;
import gov.nwcg.isuite.core.reports.TentativeReleasePosterReport;
import gov.nwcg.isuite.core.reports.data.ActualDemobReportData;
import gov.nwcg.isuite.core.reports.data.AirTravelRequestReportData;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.data.AvailableForReleaseReportData;
import gov.nwcg.isuite.core.reports.data.CheckoutReportData;
import gov.nwcg.isuite.core.reports.data.DemobPlanningReportData;
import gov.nwcg.isuite.core.reports.data.GroundSupportReportData;
import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.core.reports.data.LastWorkDayReportData;
import gov.nwcg.isuite.core.reports.data.PasswordExpiredReportData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.core.reports.data.QualificationsReportData;
import gov.nwcg.isuite.core.reports.data.StrikeTeamTaskForceReportData;
import gov.nwcg.isuite.core.reports.data.TentativeReleasePosterReportData;
import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.core.service.ReportService;
import gov.nwcg.isuite.core.vo.ReportAgencySelect;
import gov.nwcg.isuite.core.vo.ReportAgencySelectVo;
import gov.nwcg.isuite.core.vo.ReportSelectVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.service.BaseService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ReportException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

@SuppressWarnings("unchecked")
public class ReportServiceImpl extends BaseService implements ReportService {
	private static final String PARAMETER_REPORT_OUTPUT_FOLDER = "REPORT_OUTPUT_FOLDER";
	private static final String PARAMETER_REPORT_OUTPUT_URL = "REPORT_OUTPUT_URL";
	private static final String PARAMETER_REPORT_SOURCE_PATH = "REPORT_SOURCE_PATH";

	public ReportServiceImpl(){

	}
	
	protected String getDestinationFileName() throws Exception {

		String timestamp=String.valueOf(Calendar.getInstance().getTimeInMillis());

		return "rpt"+timestamp+".pdf";
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
					return "http://localhost/isuite/reportsoutput/"+fileName;
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

	private String generateReport(IReport report) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder2 reportBuilder = new ReportBuilder2(super.servletContext);

		Report rpt = new ReportImpl();
		rpt.setReportName(report.getReportName());
		rpt.setDateRequested(new Date());
		rpt.setUserId(getUserVo().getId());
		if(rpt.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			try {
				rpt.setUser(userDao.getById(rpt.getUserId(), UserImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		ReportDao reportDao = (ReportDao)super.context.getBean("reportDao");
		
		try {
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
			
			String fileName = this.getDestinationFileName();

			rpt.setFileName(fileName);			
						
			String outputFile = this.getOutputFile(fileName);

			// Create the pdf report. Set the name of the output file.
			reportBuilder.applicationContext=super.context;
			reportBuilder.createPdfReport(report, outputFile);

			rpt.setDateGenerated(new Date());
						
			return this.getOutputUrl(fileName);

		}catch(ReportException re){
			rpt.setErrorDesc(re.toString());
			throw new ServiceException(re);
		}catch(Exception e){
			rpt.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(rpt);
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
	}
	
	/*
	 * Create a Report Object with the associated Incident Id or Incident Group Id.
	 * 
	 * @param report
	 * @param incidentId
	 * @param incidentGroupId
	 * @return String URL to the file for display
	 * @throws ServiceException
	 */
	private String generateReport(IReport report, Long incidentId, Long incidentGroupId) throws ServiceException {
		// Instantiate a report builder
		ReportBuilder reportBuilder = new ReportBuilder(super.servletContext);

		Report rpt = new ReportImpl();
		rpt.setReportName(report.getReportName());
		rpt.setDateRequested(new Date());
		rpt.setUserId(getUserVo().getId());
		if(rpt.getUserId() != null) {
			// get the user object
			UserDao userDao = (UserDao)super.context.getBean("userDao");
			try {
				rpt.setUser(userDao.getById(rpt.getUserId(), UserImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		
		if (incidentId != null && incidentId > 0) {
			rpt.setIncidentId(incidentId);
			IncidentDao incidentDao = (IncidentDao)super.context.getBean("incidentDao");
			try {
				rpt.setIncident(incidentDao.getById(incidentId, IncidentImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		} else if (incidentGroupId != null && incidentGroupId > 0) {
			rpt.setIncidentGroupId(incidentGroupId);
			IncidentGroupDao incidentGroupDao = (IncidentGroupDao)super.context.getBean("incidentGroupDao");
			try {
				rpt.setIncidentGroup(incidentGroupDao.getById(incidentGroupId, IncidentGroupImpl.class));
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
		
		ReportDao reportDao = (ReportDao)super.context.getBean("reportDao");
		
		try {
			String urlContext = super.getSystemParamValue(SystemParameterTypeEnum.CONTEXT_ROOT);
			if(null != urlContext && !urlContext.isEmpty())
				reportBuilder.setUrlContext(urlContext);
			
			String fileName = this.getDestinationFileName();

			rpt.setFileName(fileName);
						
			String outputFile = this.getOutputFile(fileName);

			// Create the pdf report. Set the name of the output file.
			reportBuilder.applicationContext=super.context;
			reportBuilder.createPdfReport(report, outputFile);

			rpt.setDateGenerated(new Date());
						
			return this.getOutputUrl(fileName);

		}catch(ReportException re){
			rpt.setErrorDesc(re.toString());
			throw new ServiceException(re);
		}catch(Exception e){
			rpt.setErrorDesc(e.toString());
			throw new ServiceException(e);
		} finally {
			try {
				reportDao.save(rpt);
			} catch (PersistenceException pe){
				throw new ServiceException(pe);
			}
		}
	}
	
	public String generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter) throws ServiceException {
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try{
			/*
			 * Get the collection report data objects needed for the AllIncidentResourcesReport.
			 */
			Collection<AllIncidentResourcesReportData> reportData = dao.getAllIncidentResourcesReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"All Resources Report"));
			}
			
			/*
			 * get the incident name id key from the first object in the collection.
			 * the incident name id key will be used as the subtitle for the report
			 */
//			String subTitle="";
//			if( (null!=reportData) && (reportData.size()>0)){
//				AllIncidentResourcesReportData data = reportData.iterator().next();
//				subTitle=data.getIncidentName()+" " + data.getIncidentUnit() + "-" +data.getIncidentNumber();
//			}

			/*
			 * Instantiate the report controller object.
			 */
			IReport report = new AllIncidentResourcesReport(reportData);

			report.addCustomField("includeSubTotal", filter.getSubTotalsFirstSort());
			
			/*
			 * Create the pdf report. Set the name of the output file.
			 */
			return this.generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			super.handleException(new ErrorObject(ErrorEnum._90000_ERROR, e.getMessage()));
		}

		return null;
	}

	public String generateCheckoutReport(CheckoutReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the checkout report.
			Collection<CheckoutReportData> reportData = dao.getCheckoutReportData(filter,0L);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Checkout Report"));
			}else{
				if(filter.getMarkCheckOutFormPrinted()){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(CheckoutReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								irDao.updateCheckOutFormPrinted(ids);
							}
						}else{
							// update the check-out form printed field
							irDao.updateCheckOutFormPrinted(wpIds);
						}
					}
				}
			}
			
			// Instantiate the report controller object.
			IReport report = new CheckoutReport(reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public String generateQualificationsReport(QualificationsReportFilter filter) throws ServiceException {
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try {
			Collection<QualificationsReportData> reportData = dao.getQualificationsReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Qualifications Report"));
			}
			
			// Instantiate the report controller object.
			IReport report = new QualificationsReport("",reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public String generateDemobPlanningReport(DemobPlanningReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the demob planning report.
			Collection<DemobPlanningReportData> reportData = dao.getDemobPlanningReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Demob Planning Report"));
			}
			
			// Instantiate the report controller object.
			IReport report = new DemobPlanningReport(reportData);
			report.addCustomField("includeSubTotal", filter.getSubTotalsFirstSort());

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
		  throw new ServiceException(e);
		}
	}

	public String generateTentativeReleasePosterReport(TentativeReleasePosterReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the tentative release report.
			Collection<TentativeReleasePosterReportData> reportData = dao.getTentativeReleasePosterReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Tentative Release Report"));
			}
			
			// Instantiate the report controller object.
			IReport report = new TentativeReleasePosterReport(reportData);

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public String generateAirTravelRequestReport(AirTravelRequestReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the air travel request report.
			Collection<AirTravelRequestReportData> reportData2 = dao.getAirTravelRequestReportData(filter);

			if(null==reportData2 || reportData2.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Air Travel Request Report"));
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(AirTravelRequestReportData d : reportData2){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the air travel dispatch notified field
								irDao.updateAirTravelDispatchNotified(ids);
							}
						}else{
							// update the air travel dispatch notified field
							irDao.updateAirTravelDispatchNotified(wpIds);
						}
					}
				}
				
			}

			Collection<AirTravelRequestReportData> reportData = new ArrayList<AirTravelRequestReportData>();
			
			for(AirTravelRequestReportData data : reportData2){
				try{
					if( null != data.getLengthAtAssignment() && data.getLengthAtAssignment() > 0){
						if(null != data.getFirstWorkDate()){
							data.getCalcDate();
						}
					}
					reportData.add(data);
				}catch(Exception zz){
					System.out.println();
				}
			}
			
			// Instantiate the report controller object.
			IReport report = new AirTravelRequestReport(reportData);

			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public String generateLastWorkDayReport(LastWorkDayReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the last work day report.
			Collection<LastWorkDayReportData> reportData = dao.getLastWorkDayReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Last Work Day Report"));
			}
			
			// Instantiate the report controller object.
			IReport report = null;

			if(filter.getGroupByDateResourceCategory()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_1,reportData);
			}else if(filter.getGroupBySectionDate()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_2,reportData);
			}else if(filter.getGroupBySectionResourceCategoryDate()){
				report = new LastWorkDayReport(LastWorkDayReport.REPORT_NAME_3,reportData);
			}
			
			// Set custom parameters
			Map<String,Object> map = new HashMap<String,Object>();
			if(filter.getIncludeAllDates()){
				map.put("reportDateValue", "Includes All Dates");
			}else{
				map.put("reportDateValue", DateUtil.toDateString(filter.getReportStartDate()) + " - " + DateUtil.toDateString(filter.getReportEndDate()));
			}
			report.setCustomFields(map);

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public String generateActualDemobReport(ActualDemobReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the actual demob report.
			Collection<ActualDemobReportData> reportData = dao.getActualDemobReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Actual Demob Report"));
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(ActualDemobReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}

					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the tentative release dispatch notified field
								irDao.updateActualReleaseDispathNotified(ids);
							}
						}else{
							// update the tentative release dispatch notified field
							irDao.updateActualReleaseDispathNotified(wpIds);
						}
					}
					
				}
				
			}
			
			// Instantiate the report controller object.
			IReport report = new ActualDemobReport(reportData);
			
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	public String generateGroundSupportReport(GroundSupportReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the ground support report.
			Collection<GroundSupportReportData> reportData = dao.getGroundSupportReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Ground Support Report"));
			}

			// Instantiate the report controller object.
			IReport report = new GroundSupportReport(reportData);
			
			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService#generateICS209Report(gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter)
	 */
	public String generateICS209Report(ICS209ReportFilter filter) throws ServiceException {
		ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");

		try {
			if(!LongUtility.hasValue(filter.getIncidentId())){
				if(!LongUtility.hasValue(filter.getIncidentGroupId())){
					throw new ServiceException("IncidentId or IncidentGroupId must be set to run ICS209 report.");
				}
			}

			IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
			Incident incidentEntity = incidentDao.getById(filter.getIncidentId());
				
			// Gets the collection of report data objects needed for the ics209 report.
			Collection<ICS209ReportData> reportData = new ArrayList<ICS209ReportData>();
			if(BooleanUtility.isTrue(filter.getStateGrouping()))
				reportData=dao.getICS209ReportData(filter);
			else
				reportData=dao.getICS209ReportData2(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"ICS-209 Report"));
			}
			
			// Instantiate the report controller object
			String subTitle = "";
			if(null != incidentEntity){
				subTitle=incidentEntity.getIncidentName() + " " +
						incidentEntity.getHomeUnit().getUnitCode() +
						"-"+incidentEntity.getNbr() ;
			}
			IReport report = new ICS209Report(subTitle,reportData);

			return generateReport(report);

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch (ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService#generatePasswordExpiredReport()
	 */
	public String generatePasswordExpiredReport() throws ServiceException {
		// need to set the DAO where the report data will come from

		try {

			// Gets the collection of report data objects needed for the password expired report.
			Collection<PasswordExpiredReportData> reportData = null;

			// Instantiate the report controller object
			String subTitle = "TODO: FROG MT-LNF-00001";
			IReport report = new PasswordExpiredReport(subTitle,reportData);

			return generateReport(report);

		}catch(Exception e){
			throw new ServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportService#generateStrikeTeamTaskForceReport(java.lang.Long)
	 */
	@Override
	public String generateStrikeTeamTaskForceReport(Long incidentId,Collection<Integer> resourceIds, Long incidentGroupId) throws ServiceException{
		try {
			ReportIncidentResourceDao dao = (ReportIncidentResourceDao)super.context.getBean("reportIncidentResourceDao");
			Collection<Long> ids = IntegerUtility.convertToLongs(resourceIds);
			Collection<StrikeTeamTaskForceReportData> reportData = dao.getStrikeTeamTaskForceReportData(incidentId,ids);
			
			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Strike Team / Task Force Report"));
			}

			String subTitle="";
			
			if(LongUtility.hasValue(incidentId)){
				IncidentDao incidentDao = (IncidentDao)context.getBean("incidentDao");
				Incident incEntity = incidentDao.getById(incidentId);
				subTitle = incEntity.getIncidentName() + " " + (null != incEntity.getHomeUnit() ? incEntity.getHomeUnit().getUnitCode() : "") + "-" + incEntity.getNbr();
			}else if(LongUtility.hasValue(incidentGroupId)){
				IncidentGroupDao incidentGroupDao = (IncidentGroupDao)context.getBean("incidentGroupDao");
				IncidentGroup incGroupEntity = incidentGroupDao.getById(incidentGroupId,IncidentGroupImpl.class);
				
				if(null != incGroupEntity && incGroupEntity.getIncidents().size() > 0){
					for(Incident i : incGroupEntity.getIncidents()){
						if(subTitle.length() > 0)
							subTitle = subTitle + ", ";
						subTitle = subTitle + i.getIncidentName() + " " + (null != i.getHomeUnit() ? i.getHomeUnit().getUnitCode() : "") + "-" + i.getNbr();
					}
				}
			}
			
			IReport report = new StrikeTeamTaskForceReport(subTitle, reportData);
			return this.generateReport(report);
		} catch (ServiceException se){
			throw se;
		} catch (Exception e) {
			super.handleException(new ErrorObject(ErrorEnum._90000_ERROR, e.getMessage()));
		}

		return null;
	}

	 /*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getRequestNumbersForSelectedIncident(java.lang.Long)
	 */
	public DialogueVo getRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			data = dao.getRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
		} catch (Exception e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getContractedRequestNumbersForSelectedIncident(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			data = dao.getContractedRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
		} catch (Exception e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getNonContractedRequestNumbersForSelectedIncident(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getNonContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			data = dao.getNonContractedRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
		} catch (Exception e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getPersonRequestNumbersForSelectedIncident(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getPersonRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			data = dao.getPersonRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
		} catch (Exception e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getPersonResourceNamesForSelectedIncident(java.lang.Long)
	 */
	public DialogueVo getPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			data = dao.getPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getPersonResourceNamesForSelectedIncident(java.lang.Long)
	 */
	public DialogueVo getContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			data = dao.getContractedPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#getNonContractedPersonResourceNamesForSelectedIncident(java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getNonContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId,
			DialogueVo dialogueVo) throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			data = dao.getNonContractedPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.service.ReportService#getCrewNamesForSelectedIncident(java.lang.Long)
	 */
	public DialogueVo getCrewNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			data = dao.getCrewNamesForSelectedIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(ErrorEnum.UNABLE_TO_RETRIEVE_CREW_NAMES_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
  
	/*
	 * (non-Javadoc)
	 * 
	 * @seegov.nwcg.isuite.core.service.ReportService#
	 * getAgencyResoursesForSelectedIncident(java.lang.Long, java.lang.Long,
	 * gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		Collection<ReportAgencySelect> data = new ArrayList<ReportAgencySelect>();
		List<ReportAgencySelectVo> dataVo = new ArrayList<ReportAgencySelectVo>();

		try {
			data = dao.getAgencyResourcesForSelectedIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(
					ErrorEnum.UNABLE_TO_RETRIEVE_AGENCY_RESOURCES_FOR_SELECTED_INCIDENT));
		}

		if (data != null && data.size() > 0) {
			try {
				dataVo = ReportAgencySelectVo.getInstances(data, true);
			} catch (Exception e) {
				super.handleException(e);
			}
		} else {
			ReportAgencySelectVo rsv = new ReportAgencySelectVo();
			rsv.setLabel("No resources available");
			dataVo.add(rsv);
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(dataVo);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.service.ReportService#generatePersonnelTimeReport
	 * (gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter,
	 * java.lang.Long)
	 */
	public DialogueVo generatePersonnelTimeReport(PersonnelTimeReportFilter filter, DialogueVo dialogueVo)
			throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");

		Collection<PersonnelTimeReportData> reportData = new ArrayList<PersonnelTimeReportData>();

		try {
			reportData = reportTimeDao.getPersonnelTimeReportData(filter, filter.getIncidentId());
		} catch (Exception e) {
			if (e.getCause().getMessage().equals("Fail!")) {
				super
						.handleException(new ErrorObject(ErrorEnum.SELECTED_RESOURCE_HAS_NO_TIME_POSTINGS, e
								.getMessage()));
			}
			super.handleException(new ErrorObject(ErrorEnum._90000_ERROR, e.getMessage()));
		}

		if (null == reportData || reportData.size() < 1) {
			MessageVo messageVo = new MessageVo();
			messageVo.setMessageProperty("error.900010");
			messageVo.setParameters(new String[] { "Personnel Time Post" });
			messageVo.setTitleProperty("text.timeReports");

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setIsDialogueEnding(true);
			coa.setMessageVo(messageVo);
			dialogueVo.setCourseOfActionVo(coa);

			return dialogueVo;
		}

		List<String> reportsList = new ArrayList<String>();

		try {
			IReport report = new PersonnelTimeReport("", reportData);
			report.addCustomField("SUBREPORT_DIR", getSourcePath());

			reportsList.add(this.generateReport(report));
		} catch (Exception e) {
			super.handleException(e);
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportsList);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	public String generateAvailableForReleaseReport(AvailableForReleaseReportFilter filter) throws ServiceException {
		ReportDemobDao dao = (ReportDemobDao)super.context.getBean("reportDemobDao");

		try {
			// Gets the collection of report data objects needed for the availabe for release support report.
			Collection<AvailableForReleaseReportData> reportData = dao.getAvailableForReleaseReportData(filter);

			if(null==reportData || reportData.size() < 1){
				throw new ServiceException(new ErrorObject(ErrorEnum._900010_NO_REPORT_DATA_AVAILABLE,"Available For Release Report"));
			}else{
				if(filter.getMarkDispatchNotified() ){
					Collection<Long> wpIds = new ArrayList<Long>();
					for(AvailableForReleaseReportData d : reportData){
						if(null != d.getWorkPeriodId())
							wpIds.add(d.getWorkPeriodId());
					}
					
					if(CollectionUtility.hasValue(wpIds)){
						IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
						if(wpIds.size()>999){
							/*
							 * Split out wpIds in chunks of 999
							 * to avoid oracle exception
							 */
							Hashtable table = CollectionUtility.splitCollection(wpIds, 999);
							
							for(int i=1;i<(table.size()+1);i++){
								Collection<Long> ids = (Collection<Long>)table.get(i);

								// update the tentative release dispatch notified field
								irDao.updateTentReleaseDispathNotified(ids);
							}
						}else{
							// update the tentative release dispatch notified field
							irDao.updateTentReleaseDispathNotified(wpIds);
						}
					}
				}
				
			}
			
			// Instantiate the report controller object.
			IReport report = new AvailableForReleaseReport(reportData);

			return generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());

		}catch(PersistenceException pe){
			throw new ServiceException(pe);
		}catch(ServiceException se){
			throw se;
		}catch(Exception e){
			throw new ServiceException(e);
		}
	}
	
	public DialogueVo getAvailableForReleaseReprintList(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		Collection<ReportSelectVo> dataVo = new ArrayList<ReportSelectVo>();

		try {
			dataVo = dao.getAvailableForReleaseReprintListForIncident(incidentId, incidentGroupId);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(ErrorEnum.UNABLE_TO_RETRIEVE_REPRINT_LIST_FOR_SELECTED_INCIDENT));
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(dataVo);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	public DialogueVo reprintAvailableForReleaseReport(Long reportId, 
													   Boolean changePrintDate, 
													   Long incidentId, 
													   Long incidentGroupId, 
													   DialogueVo dialogueVo)
			throws ServiceException {

		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		// get report for reportId & prepare new reportImpl
		Report oldReport = getReport(reportId);
		Report report = prepareNewAvailableForReleaseReport(oldReport, changePrintDate, incidentId, incidentGroupId);
		String orgReport = oldReport.getFileName();

		String filePath = "";
		try {
			filePath = getOutputFile("");
		} catch (Exception e) {
			super.handleException(e);
		}

		// if true, update print date on report
		if (changePrintDate) {
			String fileName = "";
			try {
				fileName = getDestinationFileName();
			} catch (Exception e) {
				super.handleException(e);
			}
			report.setFileName(fileName);

			updateAvailableReportPrintDate(filePath, orgReport, fileName, report.getDateGenerated());
		} else {
			report.setFileName(orgReport);
		}

		// return file info through the dialogueVo for display.
		List<String> reportList = new ArrayList<String>();

		try {
			reportList.add(getOutputUrl(report.getFileName()));
		} catch (Exception e) {
			super.handleException(e);
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportList);
		dialogueVo.setCourseOfActionVo(coa);

		return dialogueVo;
	}

	private Report getReport(Long reportId) throws ServiceException {
		ReportDao rptDao = (ReportDao) context.getBean("reportDao");
		Report org = null;

		try {
			org = rptDao.getById(reportId, ReportImpl.class);
		} catch (PersistenceException e) {
			super.handleException(e);
		}

		return org;
	}

	private Report prepareNewAvailableForReleaseReport(Report oldReport, 
													   Boolean changePrintDate, 
													   Long incidentId, 
													   Long incidentGroupId) 
			throws ServiceException {
		Report report = new ReportImpl();
		report.setReportName(oldReport.getReportName());
		report.setDateRequested(new Date());
		report.setDateGenerated(new Date());
		report.setOriginalReportId(oldReport.getId());
		report.setOriginalReport(oldReport);
		try {
			report.setUser(UserVo.toEntity(null, super.getUserVo(), false, null));
		} catch (Exception e) {
			super.handleException(e);
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

		ReportDao reportDao = (ReportDao) super.context.getBean("reportDao");
		try {
			reportDao.save(report);
		} catch (PersistenceException pe) {
			super.handleException(pe);
		}

		return report;
	}

	private void updateAvailableReportPrintDate(String filePath, String orgReport, String newReport, Date date)
			throws ServiceException {
		String dateString = DateUtil.toDateString(date, "MM/dd/yyyy");
		String timeString = DateUtil.toDateString(date, "HH:mm");

		BaseFont bfHR = null;
		try {
			bfHR = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
		} catch (Exception e) {
			super.handleException(e);
		}

		try {
			PdfReader pdfReader = new PdfReader(filePath + orgReport);
			PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath + newReport));

			// alter the heading and official number
			for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
				PdfContentByte content = pdfStamper.getOverContent(i);
				content.setColorFill(new CMYKColor(0, 0, 0, 0));
				content.setColorStroke(new CMYKColor(0, 0, 0, 0));

				content.moveTo(72, 539);
				content.lineTo(140, 539);
				content.lineTo(140, 554);
				content.lineTo(72, 554);
				content.closePathFillStroke();

				content.moveTo(184, 539);
				content.lineTo(215, 539);
				content.lineTo(215, 554);
				content.lineTo(184, 554);
				content.closePathFillStroke();

				content.setColorFill(new CMYKColor(255, 255, 255, 255));

				ColumnText ct = new ColumnText(content);
				ct.setAlignment(Element.ALIGN_LEFT);
				ct.setSimpleColumn(72, 527, 150, 558); // ? 72 = 1 inch ?
				ct.setText(new Phrase(dateString, new Font(bfHR)));
				ct.go();

				ColumnText ct2 = new ColumnText(content);
				ct2.setAlignment(Element.ALIGN_LEFT);
				ct2.setSimpleColumn(185, 527, 230, 558);
				ct2.setText(new Phrase(timeString, new Font(bfHR)));
				ct2.go();

				content.beginText();
			}
			pdfStamper.close();
			pdfReader.close();
		} catch (IOException e) {
			super.handleException(e);
		} catch (DocumentException de) {
			super.handleException(de);
		}
	}
}
