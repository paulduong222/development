package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapBranch;
import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapForm203;
import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.core.domain.impl.IapAttachmentImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm202Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm203Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm205Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.core.persistence.IapAttachmentDao;
import gov.nwcg.isuite.core.persistence.IapBranchDao;
import gov.nwcg.isuite.core.persistence.IapForm202Dao;
import gov.nwcg.isuite.core.persistence.IapForm203Dao;
import gov.nwcg.isuite.core.persistence.IapForm205Dao;
import gov.nwcg.isuite.core.persistence.IapForm206Dao;
import gov.nwcg.isuite.core.persistence.IapForm220Dao;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.persistence.IapPlanPrintOrderDao;
import gov.nwcg.isuite.core.reports.IapForm202Report;
import gov.nwcg.isuite.core.reports.IapForm203Report;
import gov.nwcg.isuite.core.reports.IapForm204Report1Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report2Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report3Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report4Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report5Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report6Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report7Comm;
import gov.nwcg.isuite.core.reports.IapForm204Report8Comm;
import gov.nwcg.isuite.core.reports.IapForm205Report;
import gov.nwcg.isuite.core.reports.IapForm205_16F_1LReport;
import gov.nwcg.isuite.core.reports.IapForm206Pg3Report;
import gov.nwcg.isuite.core.reports.IapForm206Report;
import gov.nwcg.isuite.core.reports.IapForm220Pg2Report;
import gov.nwcg.isuite.core.reports.IapForm220Report;
import gov.nwcg.isuite.core.reports.data.IapForm202ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm203ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm204ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm205ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm206Pg3ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm206ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm220Pg2ReportData;
import gov.nwcg.isuite.core.reports.data.IapForm220ReportData;
import gov.nwcg.isuite.core.service.IapPlanService;
import gov.nwcg.isuite.core.service.IapReportService;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.core.vo.IapPrintFormVo;
import gov.nwcg.isuite.core.vo.IapPrintJobVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.ReportBuilder2;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.PdfUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.regex.Pattern;

import org.hsqldb.lib.FileUtil;

public class IapReportServiceImpl extends BaseReportService implements IapReportService {
	private IapPlanDao iapPlanDao=null;
	private IapPlanPrintOrderDao iapPlanPrintOrderDao=null;
	private IapForm202Dao iapForm202Dao=null;
	private IapForm203Dao iapForm203Dao=null;
	private IapBranchDao iapBranchDao=null;
	private IapForm205Dao iapForm205Dao=null;
	private IapForm206Dao iapForm206Dao=null;
	private IapForm220Dao iapForm220Dao=null;
	private ReportBuilder2 reportBuilder = null;
	private IapAttachmentDao iapAttachmentDao=null;
	
	private IapPlanService iapPlanService = null;

	public IapReportServiceImpl() {
		super();
	}
	
	public void initialization() {
		iapPlanDao = (IapPlanDao)context.getBean("iapPlanDao");
		iapPlanPrintOrderDao = (IapPlanPrintOrderDao)context.getBean("iapPlanPrintOrderDao");
		iapForm202Dao = (IapForm202Dao)context.getBean("iapForm202Dao");
		iapForm203Dao = (IapForm203Dao)context.getBean("iapForm203Dao");
		iapBranchDao = (IapBranchDao)context.getBean("iapBranchDao");
		iapForm205Dao = (IapForm205Dao)context.getBean("iapForm205Dao");
		iapForm206Dao = (IapForm206Dao)context.getBean("iapForm206Dao");
		iapForm220Dao = (IapForm220Dao)context.getBean("iapForm220Dao");
		iapAttachmentDao = (IapAttachmentDao)context.getBean("iapAttachmentDao");
		
		iapPlanService = (IapPlanService)context.getBean("iapPlanService");
	}

	private String getDestinationFileName(String prefix) throws Exception {
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return prefix + timestamp + ".pdf";
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.IapReportService#printPlan(gov.nwcg.isuite.core.vo.IapPrintJobVo, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo printPlan(IapPrintJobVo printJobVo,  DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			reportBuilder = new ReportBuilder2(servletContext);
			reportBuilder.applicationContext=super.context;
			
			IapPlan iapPlan = null; 
			if(LongUtility.hasValue(printJobVo.getIapPlanId()))
				iapPlan=iapPlanDao.getById(printJobVo.getIapPlanId(), IapPlanImpl.class);
			
			// Lock the plan if:
			// A. The user chose to lock it.
			// B. The plan isn't already locked.
			if(!iapPlan.getIsPlanLocked().getValue()) { // Not already locked
				if(printJobVo.getLockPlan()){			// User chose to lock the plan
					this.iapPlanService.lockUnlockIapPlan(iapPlan.getId(), "LOCK", null);
				}
			}
			
			iapPlanDao.flushAndEvict(iapPlan);
			
			String singlePdfUrl="";

			if(CollectionUtility.hasValue(printJobVo.getFormsToPrint())){
				
				Collection<String> reportFiles = new ArrayList<String>();
			 
				// for each form, generate jasper report
				for(IapPrintFormVo printFormVo : printJobVo.getFormsToPrint()){
					if(BooleanUtility.isTrue(printFormVo.getIsAttachment())){
						IapAttachment attachment=this.iapAttachmentDao.getById(printFormVo.getAttachmentId(), IapAttachmentImpl.class);
						iapAttachmentDao.flushAndEvict(attachment);
						String attachFile=this.getOutputFile(attachment.getFilename());
						reportFiles.add(attachFile);
					}else{
						String reportFile=this.generateFormReport(iapPlan, printFormVo);
						reportFiles.add(reportFile);
					} 
				}
				
/////////////////////////////////////////////
				
				Collection<IapPlanPrintOrderVo> vos2 = iapPlanPrintOrderDao.getPicklist(printJobVo.getIapPlanId());
				if (null!=vos2)
				{
					for(IapPlanPrintOrderVo vo : vos2){
						
						IapPlanPrintOrder deleteEntity = null;
						
						deleteEntity=iapPlanPrintOrderDao.getByKey(vo.getIapPlanId(), vo.getFormType(), vo.getFormId());
						
						if(null!=deleteEntity)
						{
							iapPlanPrintOrderDao.delete(deleteEntity);
						}
					}
				}
			
				for(IapPlanPrintOrderVo printOrderVo : printJobVo.getOrdersToPrint()){
				 	IapPlanPrintOrderVo vo3 = new IapPlanPrintOrderVo();
					vo3.setIapPlanId(printJobVo.getIapPlanId());
					vo3.setFormId(printOrderVo.getFormId());
					vo3.setFormType(printOrderVo.getFormType());			 
					vo3.setPosition(printOrderVo.getPosition());		 
					this.savePlanPrintOrder(vo3, dialogueVo);
				}
				
////////////////////////////////////////////			
				// Assemble the reportFiles into single pdf document
				String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
				String singlePdf="rpt" + timestamp + ".pdf";
				String singlePdfFile = getOutputFile(singlePdf);
				PdfUtil.concatenatePdfs(reportFiles, singlePdfFile);
				singlePdfUrl=getOutputUrl(singlePdf);			
			}
			
			CourseOfActionVo coaVo = new CourseOfActionVo();
			coaVo.setCoaName("PRINT_PLAN_COMPLETE");
			coaVo.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coaVo.setIsDialogueEnding(Boolean.TRUE);
			dialogueVo.setCourseOfActionVo(coaVo);
			dialogueVo.setResultObject(singlePdfUrl);
		 
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	 
	public DialogueVo savePlanPrintOrder(IapPlanPrintOrderVo vo, DialogueVo dialogueVo) throws ServiceException {
		if(null==dialogueVo) dialogueVo = new DialogueVo();
		
		try{
 
			IapPlanPrintOrder entity = null;
			
			entity = IapPlanPrintOrderVo.toEntity(entity, vo, true);
			
			iapPlanPrintOrderDao.save(entity);
			iapPlanPrintOrderDao.flushAndEvict(entity);

			vo=IapPlanPrintOrderVo.getInstance(entity, true);
		
			CourseOfActionVo coaVo = new CourseOfActionVo();
				coaVo.setCoaName("SAVE_IAP_PLANPRINTORDER");
				coaVo.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			 	coaVo.setIsDialogueEnding(Boolean.TRUE);
				dialogueVo.setResultObject(vo);
				
				dialogueVo.setCourseOfActionVo(coaVo);				
			 
		}catch(ServiceException se){
			throw se;	
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
	
	
	private String generateFormReport(IapPlan iapPlan, IapPrintFormVo printFormVo ) throws Exception {
		String reportFile = "";
		
		int formType = TypeConverter.convertToInt(printFormVo.getFormType());
		switch(formType)
		{
			case 202:
				reportFile = generateForm202Report(iapPlan, printFormVo);
				break;
			case 203:
				reportFile = generateForm203Report(iapPlan, printFormVo);
				break;
			case 204:
				reportFile = generateForm204Report(iapPlan, printFormVo);
				break;
			case 205:
				reportFile = generateForm205Report(iapPlan, printFormVo);
				break;
			case 206:
				reportFile = generateForm206Report(iapPlan, printFormVo);
				break;
			case 220:
				reportFile = generateForm220Report(iapPlan, printFormVo);
				break;
		}
		
		return reportFile;
	}
	
	/**
	 * Generate a DHS form 202 report.
	 * 
	 * @param iapPlan
	 * @param printFormVo
	 * @return
	 * @throws Exception
	 */
	private String generateForm202Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapForm202 entity = this.iapForm202Dao.getById(printFormVo.getFormId(), IapForm202Impl.class);

		// load the 202 report data object list
		Collection<IapForm202ReportData> reportData = new ArrayList<IapForm202ReportData>();
		IapForm202ReportData data = IapForm202ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		this.iapForm202Dao.flushAndEvict(entity);
		
		// init new form 202 report object
		IReport report = new IapForm202Report(reportData);

		// generate pdf
		String outputFile=super.getOutputFile(getDestinationFileName("form202_"));
		reportBuilder.applicationContext=super.context;
		reportFile=this.reportBuilder.createPdfReport(report, outputFile);
		
		return reportFile;
	}

	private String generateForm203Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapForm203 entity = this.iapForm203Dao.getById(printFormVo.getFormId(), IapForm203Impl.class);

		// load the 203 report data object list
		Collection<IapForm203ReportData> reportData = new ArrayList<IapForm203ReportData>();
		IapForm203ReportData data = IapForm203ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		this.iapBranchDao.flushAndEvict(entity);
		
		// init new form 203 report object
		IReport report = new IapForm203Report(reportData);
		report.addCustomField("SUBREPORT_DIR", super.getSourcePath());

		// generate pdf
		String outputFile=super.getOutputFile(getDestinationFileName("form203_"));
		reportBuilder.applicationContext=super.context;
		reportFile=this.reportBuilder.createPdfReport(report, outputFile);
		
		return reportFile;
	}
	
	/**
	 * @param iapPlan
	 * @param printFormVo
	 * @return
	 * @throws Exception
	 */
	private String generateForm204Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapBranch entity = this.iapBranchDao.getById(printFormVo.getFormId(), IapBranchImpl.class);

		// load the 204 report data object list
		Collection<IapForm204ReportData> reportData = new ArrayList<IapForm204ReportData>();
		IapForm204ReportData data = IapForm204ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		this.iapBranchDao.flushAndEvict(entity);
		
		// init new form 204 report object
		IReport report = null;
		switch(entity.getIapBranchCommSummaries().size()){
			case 1:
				report = new IapForm204Report1Comm(reportData);
				break;
			case 2:
				report = new IapForm204Report2Comm(reportData);
				break;
			case 3:
				report = new IapForm204Report3Comm(reportData);
				break;
			case 0:
			case 4:
				report = new IapForm204Report4Comm(reportData);
				break;
			case 5:
				report = new IapForm204Report5Comm(reportData);
				break;
			case 6:
				report = new IapForm204Report6Comm(reportData);
				break;
			case 7:
				report = new IapForm204Report7Comm(reportData);
				break;
			case 8:
				report = new IapForm204Report8Comm(reportData);
				break;
		}
		
		//IReport report = new IapForm204Report(reportData);
		report.addCustomField("SUBREPORT_DIR", super.getSourcePath());

		// generate pdf
		String outputFile=super.getOutputFile(getDestinationFileName("form204_"));
		reportBuilder.applicationContext=super.context;
		reportFile=this.reportBuilder.createPdfReport(report, outputFile);
		
		return reportFile;
	}
	
	/**
	 * @param iapPlan
	 * @param printFormVo
	 * @return
	 * @throws Exception
	 */
	private String generateForm205Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapForm205 entity = this.iapForm205Dao.getById(printFormVo.getFormId(), IapForm205Impl.class);

		// load the 205 report data object list
		Collection<IapForm205ReportData> reportData = new ArrayList<IapForm205ReportData>();
		IapForm205ReportData data = IapForm205ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		this.iapForm205Dao.flushAndEvict(entity);
		
		IReport report = null;
		
		// init new form 205 report object
		String specialInstrustion = entity.getSpecialInstruction();
		
		String p = "</P>";
		if (specialInstrustion == null) {
			//0 line
			report = new IapForm205Report(reportData);
		}
		else {
			if ((specialInstrustion.split(Pattern.quote(p), -1).length - 1) == 1) {
				//exactly 1 line
				report = new IapForm205_16F_1LReport(reportData);	
			}
			else {
				//blank line or lines >= 2
				report = new IapForm205Report(reportData);
			}	
		}
		
		
		report.addCustomField("SUBREPORT_DIR", super.getSourcePath());

		// generate pdf
		String outputFile=super.getOutputFile(getDestinationFileName("form205_"));
		reportBuilder.applicationContext=super.context;
		reportFile=this.reportBuilder.createPdfReport(report, outputFile);
		
		return reportFile;
	}

	/**
	 * @param iapPlan
	 * @param printFormVo
	 * @return
	 * @throws Exception
	 */
	private String generateForm206Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapForm206 entity = this.iapForm206Dao.getById(printFormVo.getFormId(), IapForm206Impl.class);

		// load the 206 report data object list
		Collection<IapForm206ReportData> reportData = new ArrayList<IapForm206ReportData>();
		IapForm206ReportData data = IapForm206ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		// load the 206 pg3 report data object list
		Collection<IapForm206Pg3ReportData> reportDataPg3 = new ArrayList<IapForm206Pg3ReportData>();
		reportDataPg3.add(new IapForm206Pg3ReportData());
		
		this.iapForm206Dao.flushAndEvict(entity);

		Collection<String> reportFiles = new ArrayList<String>();
		
		// init new form 206 report object
		IReport report = new IapForm206Report(reportData);
		report.addCustomField("SUBREPORT_DIR", super.getSourcePath());
		String outputFile206=super.getOutputFile(getDestinationFileName("form206_"));
		String sourcePath=super.getSystemParamValue(SystemParameterTypeEnum.REPORT_SOURCE_PATH);
		reportBuilder.applicationContext=super.context;
		String reportFile206=this.reportBuilder.createPdfReport(report, outputFile206);
		reportFiles.add(reportFile206);
		
		// init new form 206Pg3 report object and generate report
		IReport reportPg3 = new IapForm206Pg3Report(reportDataPg3);
		reportPg3.addCustomField("SUBREPORT_DIR", super.getSourcePath());
		String outputFile206Pg3=super.getOutputFile(getDestinationFileName("form206_3_"));
		reportBuilder.applicationContext=super.context;
		String reportFile206Pg3=this.reportBuilder.createPdfReport(reportPg3, outputFile206Pg3);
		reportFiles.add(reportFile206Pg3);
		
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String singlePdf="rpt" + timestamp + ".pdf";
		String singlePdfFile = getOutputFile(singlePdf);
		PdfUtil.concatenatePdfs(reportFiles, singlePdfFile);

		try{
			FileUtil.delete(reportFile206);
			FileUtil.delete(reportFile206Pg3);
		}catch(Exception e){
		}
		
		return singlePdfFile;
	}

	/**
	 * Generate a DHS form 220 report.
	 * 
	 * @param iapPlan
	 * @param printFormVo
	 * @return
	 * @throws Exception
	 */
	private String generateForm220Report(IapPlan iapPlan, IapPrintFormVo printFormVo) throws Exception {
		String reportFile="";

		IapForm220 entity = this.iapForm220Dao.getById(printFormVo.getFormId(), IapForm220Impl.class);

		// load the 220 report data object list
		Collection<IapForm220ReportData> reportData = new ArrayList<IapForm220ReportData>();
		IapForm220ReportData data = IapForm220ReportData.getInstance(iapPlan, entity);
		reportData.add(data);

		// load the 220 pg2 report data object list
		Collection<IapForm220Pg2ReportData> reportDataPg2 = new ArrayList<IapForm220Pg2ReportData>();
		IapForm220Pg2ReportData dataPg2 = IapForm220Pg2ReportData.getInstance(iapPlan, entity);
		reportDataPg2.add(dataPg2);
		
		this.iapForm220Dao.flushAndEvict(entity);

		Collection<String> reportFiles = new ArrayList<String>();

		// FILE 2::::::::::::::::::::
		// init new form 220Pg2 report object and generate report
		IReport reportPg2 = new IapForm220Pg2Report(reportDataPg2);
		reportPg2.addCustomField("SUBREPORT_DIR", super.getSourcePath());
		String outputFile220Pg2=super.getOutputFile(getDestinationFileName("form220_2_"));
		reportBuilder.applicationContext=super.context;
		String reportFile220Pg2=this.reportBuilder.createPdfReport(reportPg2, outputFile220Pg2);
		
		//Determine # of pages for 220pg2 PDF file
		Integer numberOfPagesInFile2 = PdfUtil.numberOfPagesInPdfFile(reportFile220Pg2);

		// FILE 1::::::::::::::::::::
		// init new form 220 report object and generate report
		IReport report = new IapForm220Report(reportData);
		report.addCustomField("SUBREPORT_DIR", super.getSourcePath());
		
		// Save # of pages in File 2 as a param in File 1:
		report.addCustomField("file2PageCount", numberOfPagesInFile2);
		
		String outputFile220=super.getOutputFile(getDestinationFileName("form220_"));
		reportBuilder.applicationContext=super.context;
		String reportFile220=this.reportBuilder.createPdfReport(report, outputFile220);
		reportFiles.add(reportFile220);

		// Add File 2 after File 1
		reportFiles.add(reportFile220Pg2);
		
		///////////////////////////////////////////////////////
		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String singlePdf="rpt" + timestamp + ".pdf";
		String singlePdfFile = getOutputFile(singlePdf);
		PdfUtil.concatenatePdfs(reportFiles, singlePdfFile);

		try{
			FileUtil.delete(reportFile220);
			FileUtil.delete(reportFile220Pg2);
		}catch(Exception e){
			
		}
		return singlePdfFile;
	}
}
