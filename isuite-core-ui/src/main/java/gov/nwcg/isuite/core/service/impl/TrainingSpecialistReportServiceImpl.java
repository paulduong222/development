package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.ResourceTrainingDao;
import gov.nwcg.isuite.core.persistence.hibernate.ResourceHomeUnitContactDao;
import gov.nwcg.isuite.core.reports.Tnsp225Report;
import gov.nwcg.isuite.core.reports.Tnsp2Report;
import gov.nwcg.isuite.core.reports.Tnsp3Report;
import gov.nwcg.isuite.core.reports.Tnsp4Report;
import gov.nwcg.isuite.core.reports.Tnsp5Report;
import gov.nwcg.isuite.core.reports.TnspEvalRecordReport;
import gov.nwcg.isuite.core.reports.TnspHUAvery5160Report;
import gov.nwcg.isuite.core.reports.TnspIncidentTraineeReport;
import gov.nwcg.isuite.core.reports.data.Tnsp225ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2SubReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp2SubReportTnspData;
import gov.nwcg.isuite.core.reports.data.Tnsp3ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportPPData;
import gov.nwcg.isuite.core.reports.data.Tnsp3SubReportTnspData;
import gov.nwcg.isuite.core.reports.data.Tnsp4ReportData;
import gov.nwcg.isuite.core.reports.data.Tnsp5ReportData;
import gov.nwcg.isuite.core.reports.data.TnspEvalRecordReportData;
import gov.nwcg.isuite.core.reports.data.TnspHUAvery5160ReportData;
import gov.nwcg.isuite.core.reports.data.TnspIncidentTraineeReportData;
import gov.nwcg.isuite.core.reports.data.TnspIncidentTraineeSubReportData;
import gov.nwcg.isuite.core.reports.filter.HomeUnitContactLabelsReportFilter;
import gov.nwcg.isuite.core.reports.filter.IncidentTrainingSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.TrainingAssignmentsListReportFilter;
import gov.nwcg.isuite.core.service.TrainingSpecialistReportService;
import gov.nwcg.isuite.core.vo.ResourceHomeUnitContactVo;
import gov.nwcg.isuite.core.vo.TrainingSpecialistVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class TrainingSpecialistReportServiceImpl extends BaseReportService implements TrainingSpecialistReportService {
	private ReportBuilder2 reportBuilder = null;
	
	public TrainingSpecialistReportServiceImpl() {
		super();
	}
	
	public void initialization(){
		reportBuilder = new ReportBuilder2(servletContext);
		reportBuilder.applicationContext=super.context;
	}
	
	private String getDestinationFileName(String prefix) throws Exception {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return prefix + timestamp + ".pdf";
	}

	public DialogueVo getEarliestStartDate(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");

		try{
			String dte = dao.getEarliestStartDate(incidentId, incidentGroupId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_EARLIEST_START_DATE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(dte);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getTrainingSpecialistList(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId, Long rtId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");

		try{
			Collection<TrainingSpecialistVo> list = dao.getTrainingSpecialistList(incidentId, incidentGroupId,rtId);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GET_TRAINING_SPECIALIST_LIST");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(list);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateTrainingAssignmentsListReport(DialogueVo dialogueVo, TrainingAssignmentsListReportFilter filter) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<Tnsp2ReportData> reportData = new ArrayList<Tnsp2ReportData>();
			Collection<Tnsp2ReportData> reportDataTmp =  dao.getTrainingAssnListReportData(filter);

			if(CollectionUtility.hasValue(reportDataTmp)){
				for(Tnsp2ReportData d : reportDataTmp){
					if(!CollectionUtility.hasValue(d.getSubReportData())){
						Tnsp2SubReportData d3 = new Tnsp2SubReportData();
						d.getSubReportData().add(d3);
					}
					if(!CollectionUtility.hasValue(d.getSubReportTnspData())){
						Tnsp2SubReportTnspData tnspData = new Tnsp2SubReportTnspData();
						d.getSubReportTnspData().add(tnspData);
					}
					
					if(LongUtility.hasValue(filter.getIncidentGroupId())){
						IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
						IncidentGroup ig=igDao.getById(filter.getIncidentGroupId());
						if(null != ig){
							d.setIncidentName(ig.getGroupName());
							igDao.flushAndEvict(ig);
						}
					}else if(LongUtility.hasValue(filter.getIncidentId())){
						IncidentDao iDao=(IncidentDao)context.getBean("incidentDao");
						Incident i=iDao.getById(filter.getIncidentId());
						if(null != i){
							d.setIncidentName(i.getIncidentName());
							d.setIncidentNumber(i.getHomeUnit().getUnitCode()+"-"+i.getNbr());
							iDao.flushAndEvict(i.getHomeUnit());
							iDao.flushAndEvict(i);
						}
						
					}
					
					reportData.add(d);
				}
			}
			
			// init new tnsp2 report object
			IReport report = new Tnsp2Report(reportData);
			report.addCustomField("SUBREPORT_DIR", super.getSourcePath());

			String destFile=getDestinationFileName("tnsp2_");
			String outputFile=super.getOutputFile(destFile);
			String filetype="pdf";
			
			if(null != filter && BooleanUtility.isTrue(filter.getExportToExcel())){
				report.enableExportToExcel();
				destFile=destFile.replaceAll(".pdf", ".xls");
				outputFile=outputFile.replaceAll(".pdf", ".xls");
				filetype="xls";
			}			
			
			// generate pdf
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			dialogueVo.setResultObjectAlternate(filetype);

		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateIncidentTrainingSummaryReport(DialogueVo dialogueVo, IncidentTrainingSummaryReportFilter filter) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");

			// get the data for report
			Collection<Tnsp3ReportData> reportDataTmp = dao.getTrainingSummaryReportData(filter);
			Collection<Tnsp3ReportData> reportData = new ArrayList<Tnsp3ReportData>();
			
			if(CollectionUtility.hasValue(reportDataTmp)){
				for(Tnsp3ReportData d : reportDataTmp){
					if(!CollectionUtility.hasValue(d.getSubReportData())){
						// create empty record
						Tnsp3SubReportData d3 = new Tnsp3SubReportData();
						d.getSubReportData().add(d3);
					}else{
						// get total rows
						Tnsp3SubReportData totalRow = Tnsp3SubReportData.createTotalRow(d.getSubReportData());
						d.getSubReportData().add(totalRow);
					}
					if(!CollectionUtility.hasValue(d.getSubReportTnspData())){
						Tnsp3SubReportTnspData tnspData = new Tnsp3SubReportTnspData();
						d.getSubReportTnspData().add(tnspData);
					}
					
					if(!CollectionUtility.hasValue(d.getSubReportPPData())){
						Tnsp3SubReportPPData ppData = new Tnsp3SubReportPPData();
						d.getSubReportPPData().add(ppData);
					}
					String dateRange="";
					if(StringUtility.hasValue(filter.getStartDateVo().dateString))
						dateRange=filter.getStartDateVo().dateString;
					if(StringUtility.hasValue(filter.getStartDateVo().dateString)
							&& StringUtility.hasValue(filter.getEndDateVo().dateString)){
						dateRange=dateRange+"-"+filter.getEndDateVo().dateString;
					}
					d.setDateRange(dateRange);

					if(LongUtility.hasValue(filter.getIncidentGroupId())){
						IncidentGroupDao igDao=(IncidentGroupDao)context.getBean("incidentGroupDao");
						IncidentGroup ig=igDao.getById(filter.getIncidentGroupId());
						if(null != ig){
							d.setIncidentName(ig.getGroupName());
							igDao.flushAndEvict(ig);
						}
					}else if(LongUtility.hasValue(filter.getIncidentId())){
						IncidentDao iDao=(IncidentDao)context.getBean("incidentDao");
						Incident i=iDao.getById(filter.getIncidentId());
						if(null != i){
							d.setIncidentName(i.getIncidentName());
							d.setIncidentNumber(i.getHomeUnit().getUnitCode()+"-"+i.getNbr());
							iDao.flushAndEvict(i.getHomeUnit());
							iDao.flushAndEvict(i);
						}
						
					}
					
					reportData.add(d);
				}
			}
			// init new tnsp1 report object
			IReport report = new Tnsp3Report(reportData);
			report.addCustomField("SUBREPORT_DIR", super.getSourcePath());
			
			// generate pdf
			String destFile=getDestinationFileName("tnsp3_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	public DialogueVo generateDataFormReport(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<TnspIncidentTraineeReportData> reportData = new ArrayList<TnspIncidentTraineeReportData>();
			
			TrainingSpecialistVo tnspVo=null;
			Long trainerId=0L;
			Long tnspId=0L;
			
			if(null != dialogueVo.getResultObject()){
				tnspVo=(TrainingSpecialistVo)dialogueVo.getResultObject();
				tnspId=tnspVo.getId();
			}
			if(null != dialogueVo.getResultObjectAlternate2()){
				trainerId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate2());
			}
			
			if(null != dialogueVo.getResultObjectAlternate4() && ((String)dialogueVo.getResultObjectAlternate4()).equals("no")){
				// get the data for report
				Long rtId=0L;
				if(null != dialogueVo.getResultObjectAlternate())
					rtId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate());

				Collection<TnspIncidentTraineeReportData> rd = new ArrayList<TnspIncidentTraineeReportData>();
				rd = dao.getTraineeDataFormReportData(rtId,trainerId, tnspId);
				for(TnspIncidentTraineeReportData d : rd){
					if(!CollectionUtility.hasValue(d.getSubReportData())){
						Collection<TnspIncidentTraineeSubReportData> subReportData = new ArrayList<TnspIncidentTraineeSubReportData>();
						for(int x=0;x<7;x++){
							subReportData.add(new TnspIncidentTraineeSubReportData());
						}
						d.setSubReportData(subReportData);
						reportData.add(d);
					}else{
						reportData.add(d);
					}
				}
			}else{
				TnspIncidentTraineeReportData d= TnspIncidentTraineeReportData.getInstanceBlankForm();
				
				Collection<TnspIncidentTraineeSubReportData> subReportData = new ArrayList<TnspIncidentTraineeSubReportData>();
				for(int x=0;x<7;x++){
					subReportData.add(new TnspIncidentTraineeSubReportData());
				}
				d.setSubReportData(subReportData);
				reportData.add(d);
			}

			// init new tnsp1 report object
			IReport report = new TnspIncidentTraineeReport(reportData);
			report.addCustomField("SUBREPORT_DIR", super.getSourcePath());
			
			// generate pdf
			String destFile=getDestinationFileName("tnsp1_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateEvaluatorFormReport(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<TnspEvalRecordReportData> reportData = new ArrayList<TnspEvalRecordReportData>();

			Long trainerId=0L;
			
			if(null != dialogueVo.getResultObjectAlternate2()){
				trainerId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate2());
			}
			
			String evalRecNum="";
			if(null != dialogueVo.getResultObjectAlternate3()){
				evalRecNum=(String)dialogueVo.getResultObjectAlternate3();
			}
			
			if(null != dialogueVo.getResultObjectAlternate4() && ((String)dialogueVo.getResultObjectAlternate4()).equals("no")){
				// get the data for report
				Long rtId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate());
				
				Collection<TnspEvalRecordReportData> reportData2 = new ArrayList<TnspEvalRecordReportData>();
				reportData2 = dao.getTnspEvalRecordReportData(rtId, trainerId);
				if(CollectionUtility.hasValue(reportData2)){
					for(TnspEvalRecordReportData d : reportData2){
						d.setEvalRecordNumber(evalRecNum);
						if(StringUtility.hasValue(d.getField1())){
							d.setField1(d.getField1().trim());
						}else
							d.setField1("");
						//d.setField1(d.getField1().trim());
						reportData.add(d);
					}
				}
			}else{
				reportData.add(TnspEvalRecordReportData.getInstanceBlankForm());
			}

			// init new tnspEvalRecord report object
			IReport report = new TnspEvalRecordReport(reportData);
			
			// generate pdf
			String destFile=getDestinationFileName("tnspEval_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generatePerformanceEvaluationReport(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<Tnsp225ReportData> reportData = new ArrayList<Tnsp225ReportData>();
			Long trainerId=0L;
			
			if(null != dialogueVo.getResultObjectAlternate2()){
				trainerId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate2());
			}
			
			if(null != dialogueVo.getResultObjectAlternate4() && ((String)dialogueVo.getResultObjectAlternate4()).equals("no")){
				// get the data for report
				Long rtId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate());
				reportData = dao.getPerformanceEvalReportData(rtId,trainerId);
			}else{
				reportData.add(Tnsp225ReportData.getInstanceBlankForm());
			}

			// init new tnsp225 report object
			IReport report = new Tnsp225Report(reportData);
			
			// generate pdf
			String destFile=getDestinationFileName("tnsp225_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo generateHomeUnitLetterReport(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<Tnsp5ReportData> reportData = new ArrayList<Tnsp5ReportData>();
			
			TrainingSpecialistVo tnspVo=null;
			Long trainerId=0L;
			Long tnspId=0L;
			
			if(null != dialogueVo.getResultObject()){
				tnspVo=(TrainingSpecialistVo)dialogueVo.getResultObject();
				tnspId=tnspVo.getId();
			}
			if(null != dialogueVo.getResultObjectAlternate2()){
				trainerId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate2());
			}
			
			if(null != dialogueVo.getResultObjectAlternate4() && ((String)dialogueVo.getResultObjectAlternate4()).equals("no")){
				// get the data for report
				Long rtId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate());
				Collection<Tnsp5ReportData> reportData2 = new ArrayList<Tnsp5ReportData>();
				reportData2 = dao.getTnspHomeUnitLetterReportData(rtId, tnspId);
				
				String sDiff=((UserSessionVo)this.context.getBean("userSessionVo")).getClientToServerHourDifference();
				Calendar cal=Calendar.getInstance();
				cal.add(Calendar.HOUR, Integer.parseInt(sDiff));
				Date sysDate=cal.getTime();
				String strVal=DateUtil.toDateString(sysDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
				
				for(Tnsp5ReportData d : reportData2){
					d.setToDate(strVal);
					reportData.add(d);
				}
			}else{
				reportData.add(Tnsp5ReportData.getInstanceBlankForm());
			}

			// init new tnsp5 report object
			IReport report = new Tnsp5Report(reportData);
			
			// generate pdf
			String destFile=getDestinationFileName("tnsp5_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		return dialogueVo;
	}
	
	public DialogueVo generateExitInterviewReport(DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();

		try{
			ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
			Collection<Tnsp4ReportData> reportData = new ArrayList<Tnsp4ReportData>();
			
			TrainingSpecialistVo tnspVo=null;
			Long trainerId=0L;
			Long tnspId=0L;
			
			if(null != dialogueVo.getResultObject()){
				tnspVo=(TrainingSpecialistVo)dialogueVo.getResultObject();
				tnspId=tnspVo.getId();
			}
			if(null != dialogueVo.getResultObjectAlternate2()){
				trainerId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate2());
			}
			
			if(null != dialogueVo.getResultObjectAlternate4() && ((String)dialogueVo.getResultObjectAlternate4()).equals("no")){
				// get the data for report
				Long rtId=0L;
				if(null != dialogueVo.getResultObjectAlternate())
					rtId=TypeConverter.convertToLong(dialogueVo.getResultObjectAlternate());
				reportData = dao.getExitInterviewReportData(rtId,trainerId, tnspId); 
			}else{
				Tnsp4ReportData d=Tnsp4ReportData.getInstanceBlankForm();
				if(null != dialogueVo.getResultObjectAlternate3()){
					String s=(String)dialogueVo.getResultObjectAlternate3();
					StringTokenizer st = new StringTokenizer(s,"|");
					int x=0;
					while(st.hasMoreTokens()){
						String val=(String)st.nextToken();
						if(x==0)
							d.setIncidentName(val);
						else
							d.setIncidentNumber(val);
						x++;
					}
				}
				reportData.add(d);
			}

			// init new tnsp4 report object
			IReport report = new Tnsp4Report(reportData);
			
			// generate pdf
			String destFile=getDestinationFileName("tnsp4_");
			String outputFile=super.getOutputFile(destFile);
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}

		
		return dialogueVo;
	}
	
	public DialogueVo generateHomeUnitContactLabelsReport(DialogueVo dialogueVo, HomeUnitContactLabelsReportFilter filter) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		ResourceTrainingDao dao = (ResourceTrainingDao)context.getBean("resourceTrainingDao");
		
		try{
			Collection<TnspHUAvery5160ReportData> reportData = dao.getHomeUnitContactLabelData(filter.getHomeUnitContactIds());
			
			// init new TnspHUAvery5160Report report object
			IReport report = new TnspHUAvery5160Report(reportData);

			String destFile=getDestinationFileName("tnspHULabel_");
			String outputFile=super.getOutputFile(destFile);
			String filetype="pdf";
			
			if(null != filter && BooleanUtility.isTrue(filter.getExportToExcel())){
				report.enableExportToExcel();
				destFile=destFile.replaceAll(".pdf", ".xls");
				outputFile=outputFile.replaceAll(".pdf", ".xls");
				filetype="xls";
			}			
			
			// generate pdf
			reportBuilder.applicationContext=super.context;
			String reportFile=this.reportBuilder.createPdfReport(report, outputFile);

			String pdfUrl=getOutputUrl(destFile);
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("GENERATE_FORM_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(pdfUrl);
			dialogueVo.setResultObjectAlternate(filetype);
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	public DialogueVo getHomeUnitContactGrid(DialogueVo dialogueVo, Long incidentId, Long incidentGroupId) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			ResourceHomeUnitContactDao hucDao = (ResourceHomeUnitContactDao)super.context.getBean("resourceHomeUnitContactDao");
			
			Collection<ResourceHomeUnitContactVo> vos = new ArrayList<ResourceHomeUnitContactVo>();
			
			vos = hucDao.getGrid2(incidentId,incidentGroupId);
			
			CourseOfActionVo courseOfActionVo = new CourseOfActionVo();
			courseOfActionVo.setCoaName("GET_HOME_UNIT_CONTACT_GRID");
			courseOfActionVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			courseOfActionVo.setIsDialogueEnding(Boolean.TRUE);
			
			dialogueVo.setCourseOfActionVo(courseOfActionVo);
			dialogueVo.setRecordset(vos);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

}
