package gov.nwcg.isuite.core.reports.generator;


import edu.emory.mathcs.backport.java.util.Collections;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.TimeInvoiceImpl;
import gov.nwcg.isuite.core.financial.calculator.DailyTimeCalculatorFactory;
import gov.nwcg.isuite.core.financial.posts.DailyTimePostImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.TimeAssignAdjustDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.reports.EmergencyFirefighterCommissaryReport;
import gov.nwcg.isuite.core.reports.OF288TimeInvoiceReportV2a;
import gov.nwcg.isuite.core.reports.data.EmergencyFirefighterCommissaryReportData;
import gov.nwcg.isuite.core.reports.data.EmergencyFirefighterCommissarySubReportData;
import gov.nwcg.isuite.core.reports.data.OF288TimeDetail;
import gov.nwcg.isuite.core.reports.data.OF288TimeInvoice;
import gov.nwcg.isuite.core.reports.data.PersonDetail;
import gov.nwcg.isuite.core.reports.generator.of288.OF288ReportDataGenerator;
import gov.nwcg.isuite.core.rules.OF288InvoiceGeneratorRulesHandler;
import gov.nwcg.isuite.core.rules.OF288InvoiceGeneratorRulesHandler2;
import gov.nwcg.isuite.core.vo.AddressVo;
import gov.nwcg.isuite.core.vo.AssignmentTimePostVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.GenericVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeAdustDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimePostDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.financial.calculator.DailyTimeCalculator;
import gov.nwcg.isuite.framework.financial.posts.DailyTimePost;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.AdjustmentTypeEnum;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.DecimalUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.PdfUtil;
import gov.nwcg.isuite.framework.util.PhoneNumberUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
 
public class OF288ReportGeneratorImpl extends ReportTimeInvoiceGeneratorImpl {

	@SuppressWarnings("unchecked")
	@Override
	public DialogueVo generateReport(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null)  dialogueVo = new DialogueVo();
		
		Collection<Date> dates;
		Collection<IncidentResourceVo> incidentResourceVos = new ArrayList<IncidentResourceVo>();
		Boolean isCrew=false;
		
		DailyTimePost dtp = (DailyTimePostImpl) context.getBean("dailyTimePost");
		DailyTimeCalculator dtc;

		Collection<OF288TimeInvoice> timeInvoiceData;
		Collection<String> reportUrls = new ArrayList<String>();
		Collection<String> crewResourceReportFiles = new ArrayList<String>();
		Collection<String> crewResourceAdjReportFiles= new ArrayList<String>();

		
		try {
			IncidentResourceDao irdao = (IncidentResourceDao) context.getBean("incidentResourceDao");
			IncidentResource ir = irdao.getById(filter.getIncidentResourceId(), IncidentResourceImpl.class);
			if(CollectionUtility.hasValue(ir.getResource().getChildResources())){
				filter.setIsCrew(true);
				isCrew=true;
			}
			
			// check if resource is person
			if(BooleanUtility.isTrue(ir.getResource().isPerson())){
				filter.setIsPerson(true);
			}
			irdao.flushAndEvict(ir);

			Collection<IncidentResourceTimeDataVo> irTimeDataVos = new ArrayList<IncidentResourceTimeDataVo>();

			TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");
			TimeAssignAdjustDao taaDao2 = (TimeAssignAdjustDao)context.getBean("timeAssignAdjustDao");
			
			Collection<IncidentResourceTimePostDataVo> timePostDataVos = new ArrayList<IncidentResourceTimePostDataVo>();
			Collection<IncidentResourceTimeAdustDataVo> timeAdjustDataVos = new ArrayList<IncidentResourceTimeAdustDataVo>();
			
			
			if(isCrew==true && BooleanUtility.isFalse(filter.getIsPerson())){
				irTimeDataVos=irdao.getIncidentResourceTimeDataVos(filter.getIncidentResourceId(), true,filter.getLastDateToIncludeOnReport());
				if(CollectionUtility.hasValue(irTimeDataVos)){
					Collection<Long> assignmentTimeIds=new ArrayList<Long>();
					for(IncidentResourceTimeDataVo v : irTimeDataVos){
						assignmentTimeIds.add(v.getAssignmentTimeId());
						v.setResNumId(irdao.updateResNumId(v.getIncidentResourceId()));
					}
					// fix stop times
					tpDao2.fixStopTimes2(assignmentTimeIds);
				}
				
				// get all time postings for the subordinates
				timePostDataVos = tpDao2.getTimePostingData(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport(), true);
				
				// get all time adjustments for the subordinates
				timeAdjustDataVos = taaDao2.getTimeAdjustData(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport(), true);
				
			}else{
				//irdao.updateResNumId(filter.getIncidentResourceId());
				
				irTimeDataVos=irdao.getIncidentResourceTimeDataVos(filter.getIncidentResourceId(), false,filter.getLastDateToIncludeOnReport());
				if(CollectionUtility.hasValue(irTimeDataVos)){
					Collection<Long> assignmentTimeIds=new ArrayList<Long>();
					for(IncidentResourceTimeDataVo v : irTimeDataVos){
						assignmentTimeIds.add(v.getAssignmentTimeId());
						v.setResNumId(irdao.updateResNumId(v.getIncidentResourceId()));
					}
					// fix stop times
					tpDao2.fixStopTimes2(assignmentTimeIds);
				}
				
				// get all time postings for the resource
				timePostDataVos = tpDao2.getTimePostingData(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport(), false);
				
				// get all time adjustments for the resource
				timeAdjustDataVos = taaDao2.getTimeAdjustData(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport(), false);

			}
			
			// apply rules
			String reportFilename="";
			String reportAdjFilename="";
			
			OF288InvoiceGeneratorRulesHandler2 rulesHandler = new OF288InvoiceGeneratorRulesHandler2(context);
			if (rulesHandler.execute(irTimeDataVos, null, filter, timePostDataVos, timeAdjustDataVos, dialogueVo) == OF288InvoiceGeneratorRulesHandler._OK) {
				OF288ReportDataGenerator gen = new OF288ReportDataGenerator();
				gen.context=super.context;
				
				for(IncidentResourceTimeDataVo irTimeDataVo : irTimeDataVos) {
					//System.out.println(irTimeDataVo.getRequestNumber());
					// fix stop times
					//tpDao2.fixStopTimes(irTimeDataVo.getIncidentResourceId(),null);
					
					Collection<IncidentResourceTimePostDataVo> tpDataVos 
						= new ArrayList<IncidentResourceTimePostDataVo>();
					Collection<IncidentResourceTimeAdustDataVo> adjDataVos 
						= new ArrayList<IncidentResourceTimeAdustDataVo>();
					
					// get only this resources time posting data
					for(IncidentResourceTimePostDataVo tp : timePostDataVos){
						if(irTimeDataVo.getIncidentResourceId().compareTo(tp.getIncidentResourceId())==0)
							tpDataVos.add(tp);
					}
					// get only this resources time adjust data
					for(IncidentResourceTimeAdustDataVo a : timeAdjustDataVos){
						if(irTimeDataVo.getIncidentResourceId().compareTo(a.getIncidentResourceId())==0)
							adjDataVos.add(a);
					}
					
					gen.irTimeDataVo=irTimeDataVo;
					gen.irTimeAdjustDataVos=adjDataVos;
					gen.irTimePostDataVos=tpDataVos;
					gen.filter=filter;
					//todo: dan stop times have changed, reload timedatavos
					
					//if(CollectionUtility.hasValue(tpDataVos) || CollectionUtility.hasValue(adjDataVos)){
						// create Report data objects
						timeInvoiceData = gen.generateReportData();
						//this.generateReportData(irVo, filter);
						
						if(CollectionUtility.hasValue(timeInvoiceData)){
							//IncidentResourceVo irvo2 = null;
							//Collection<IncidentResourceVo> irvos = irdao.getNonInvoicedIncidentResourcesById(irTimeDataVo.getIncidentResourceId(), filter.getLastDateToIncludeOnReport());
							//if(CollectionUtility.hasValue(irvos))
							//	irvo2=irvos.iterator().next();
							TimeInvoice timeInvoice = generateInvoiceReports2(timeInvoiceData, filter, irTimeDataVo);
							
							if(null != timeInvoice){
								if(null != timeInvoice.getInvoiceReport()){
									reportFilename=timeInvoice.getInvoiceReport().getFileName();
									crewResourceReportFiles.add(this.getOutputFile(reportFilename));
								}
								
								if(null != timeInvoice.getAdjustmentReport()){
									reportAdjFilename=timeInvoice.getAdjustmentReport().getFileName();
									crewResourceAdjReportFiles.add(this.getOutputFile(reportAdjFilename));
								}

								// mark invoiced
								if (filter.getPrintOriginalInvoice()) {
									dtc = (new DailyTimeCalculatorFactory()).getCalculatorInstance(null);

									// calculate Totals
									//dtc.calculateTimePostInvoicedAmount(irTimeDataVo);

									// save invoiced amount
									//dtp.saveInvoicedAmounts(irvo2, filter);

									for(IncidentResourceTimePostDataVo voObj : tpDataVos){
										BigDecimal qty = voObj.getQuantity() != null ? voObj.getQuantity() : new BigDecimal(0.0);
										BigDecimal rt = voObj.getRateAmount() != null ? voObj.getRateAmount() : new BigDecimal(0.0);
										BigDecimal invoiced = qty.multiply(rt);
										if(CollectionUtility.hasValue(adjDataVos)){
											for(IncidentResourceTimeAdustDataVo adjvo : adjDataVos){
												if(DateUtil.isSameDate(adjvo.getActivityDate(),voObj.getPostStartDate())){
													if(StringUtility.hasValue(adjvo.getAdjustmentType())){
														if(adjvo.getAdjustmentType().equals("ADDITION")){
															invoiced=invoiced.add(adjvo.getAdjustmentAmount());
														}else{
															invoiced=invoiced.subtract(adjvo.getAdjustmentAmount());
														}
													}else{
														invoiced=invoiced.add(adjvo.getAdjustmentAmount());
													}
												}
											}
										}
										voObj.setInvoicedAmount(invoiced);
										
									}
									
									// mark atp's invoiced
									//if(CollectionUtility.hasValue(tpDataVos) || CollectionUtility.hasValue(adjDataVos)){
										dtp.markInvoiced2(filter.getLastDateToIncludeOnReport()
															,irTimeDataVo
															,tpDataVos
															,adjDataVos
															, timeInvoice, filter.getPrintOriginalInvoice());
									//}
								}
							}
						}
						
					//}
				}
				
				
				if(isCrew==true && BooleanUtility.isFalse(filter.getIsPerson())){
					String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
					String singlePdf="rpt" + timestamp + ".pdf";
					String outputFile = this.getOutputFile(singlePdf);
					Collection<String> files = new ArrayList<String>();
					
					// concatenate files into single pdf for crew parent
					if(CollectionUtility.hasValue(crewResourceReportFiles)){
					
						files.addAll(crewResourceReportFiles);
						
						//String crewReportUrl=this.getOutputUrl(singlePdf);
						
						//reportUrls.add(crewReportUrl);
					}
					
					if(CollectionUtility.hasValue(crewResourceAdjReportFiles)){
						files.addAll(crewResourceAdjReportFiles);
						/*
						String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
						String singlePdf="rpt" + timestamp + ".pdf";
						String outputFile = this.getOutputFile(singlePdf);
						
						PdfUtil.concatenatePdfs(crewResourceAdjReportFiles, outputFile);
						String crewReportAdjUrl=this.getOutputUrl(singlePdf);
						reportUrls.add(crewReportAdjUrl);
						*/
					}
					
					PdfUtil.concatenatePdfs(files, outputFile);
					String fileUrl=this.getOutputUrl(singlePdf);
					reportUrls.add(fileUrl);
					
				}else{

					String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
					String singlePdf="rpt" + timestamp + ".pdf";
					String outputFile = this.getOutputFile(singlePdf);
					Collection<String> reportFiles=new ArrayList<String>();
					
					if(StringUtility.hasValue(reportFilename)){
						reportFiles.add(this.getOutputFile(reportFilename));
						//reportUrls.add(this.getOutputUrl(reportFilename));
					}
					
					if(StringUtility.hasValue(reportAdjFilename)){
						reportFiles.add(this.getOutputFile(reportAdjFilename));
						//reportUrls.add(this.getOutputUrl(reportAdjFilename));
					}
					
					PdfUtil.concatenatePdfs(reportFiles, outputFile);
					String singleReportUrl=this.getOutputUrl(singlePdf);
					reportUrls.add(singleReportUrl);
				}
				
				// do post process actions and message additions
				//rulesHandler.executeProcessedActions(incidentResources, dates, filter, dialogueVo);
				//rulesHandler.addAdditionalMessages(dialogueVo);
			}else
				return dialogueVo;
			
		} catch (Exception e) {
			super.handleException(e);
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("Generate_Invoice");
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportUrls);
		dialogueVo.setCourseOfActionVo(coa);
		
		return dialogueVo;
	}

	@SuppressWarnings("unchecked")
	//@Override
	//public DialogueVo generateReportOriginalDanModifyAbove(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
	public DialogueVo generateReportOrig(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null)  dialogueVo = new DialogueVo();
		
		Collection<Date> dates;
		Collection<IncidentResourceVo> incidentResourceVos = new ArrayList<IncidentResourceVo>();
		Boolean isCrew=false;
		
		DailyTimePost dtp = (DailyTimePostImpl) context.getBean("dailyTimePost");
		DailyTimeCalculator dtc;

		Collection<OF288TimeInvoice> timeInvoiceData;
		Collection<String> reportUrls = new ArrayList<String>();
		Collection<String> crewResourceReportFiles = new ArrayList<String>();
		Collection<String> crewResourceAdjReportFiles= new ArrayList<String>();

		
		try {
			IncidentResourceDao irdao = (IncidentResourceDao) context.getBean("incidentResourceDao");
			IncidentResource ir = irdao.getById(filter.getIncidentResourceId(), IncidentResourceImpl.class);
			if(CollectionUtility.hasValue(ir.getResource().getChildResources())){
				filter.setIsCrew(true);
				isCrew=true;
			}
			
			// check if resource is person
			if(BooleanUtility.isTrue(ir.getResource().isPerson())){
				filter.setIsPerson(true);
			}
			irdao.flushAndEvict(ir);
			
			if(isCrew==true && BooleanUtility.isFalse(filter.getIsPerson())){
				Collection<IncidentResourceVo> vos=new ArrayList<IncidentResourceVo>();
				Collection<Long> childrenIds = irdao.getIncidentResourceChildrenIds(filter.getIncidentResourceId());
				if(CollectionUtility.hasValue(childrenIds)){
					for(Long id : childrenIds){
						irdao.updateResNumId(id);
						Collection<IncidentResourceVo> irvos = irdao.getNonInvoicedIncidentResourcesById(id, filter.getLastDateToIncludeOnReport());
						if(CollectionUtility.hasValue(irvos))
							incidentResourceVos.add(irvos.iterator().next());
					}
				}
			}else{
				irdao.updateResNumId(filter.getIncidentResourceId());
				incidentResourceVos = irdao.getNonInvoicedIncidentResourcesById(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport());
			}
			
			// get unique dates
			//dates = (new UniqueDatesUtil()).getUniqueTimePostDates(incidentResources);

			// apply rules
			String reportFilename="";
			String reportAdjFilename="";
			
			OF288InvoiceGeneratorRulesHandler rulesHandler = new OF288InvoiceGeneratorRulesHandler(context);
			if (rulesHandler.execute(incidentResourceVos, null, filter, dialogueVo) == OF288InvoiceGeneratorRulesHandler._OK) {
				
				TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");
				
				for(IncidentResourceVo irVo : incidentResourceVos){
					if(irVo.getId().compareTo(14766L)==0){
						System.out.println("");
					}
					// fix stop times
					tpDao2.fixStopTimes(irVo.getId(),null);
					
					// create Report data objects
					timeInvoiceData = this.generateReportData(irVo, filter);
					
					if(CollectionUtility.hasValue(timeInvoiceData)){
						TimeInvoice timeInvoice = generateInvoiceReports(timeInvoiceData, filter, irVo);
						
						if(null != timeInvoice){
							if(null != timeInvoice.getInvoiceReport()){
								reportFilename=timeInvoice.getInvoiceReport().getFileName();
								crewResourceReportFiles.add(this.getOutputFile(reportFilename));
							}
							
							if(null != timeInvoice.getAdjustmentReport()){
								reportAdjFilename=timeInvoice.getAdjustmentReport().getFileName();
								crewResourceAdjReportFiles.add(this.getOutputFile(reportAdjFilename));
							}

							// mark invoiced
							if (filter.getPrintOriginalInvoice()) {
								dtc = (new DailyTimeCalculatorFactory()).getCalculatorInstance(null);

								// calculate Totals
								dtc.calculateTimePostInvoicedAmount(irVo);

								// save invoiced amount
								dtp.saveInvoicedAmounts(irVo, filter);

								// mark atp's invoiced
								dtp.markInvoiced(filter.getLastDateToIncludeOnReport(),irVo, timeInvoice, filter.getPrintOriginalInvoice());
							}
						}
					}
				}
				
				if(isCrew==true && BooleanUtility.isFalse(filter.getIsPerson())){
					String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
					String singlePdf="rpt" + timestamp + ".pdf";
					String outputFile = this.getOutputFile(singlePdf);
					Collection<String> files = new ArrayList<String>();
					
					// concatenate files into single pdf for crew parent
					if(CollectionUtility.hasValue(crewResourceReportFiles)){
					
						files.addAll(crewResourceReportFiles);
						
						//String crewReportUrl=this.getOutputUrl(singlePdf);
						
						//reportUrls.add(crewReportUrl);
					}
					
					if(CollectionUtility.hasValue(crewResourceAdjReportFiles)){
						files.addAll(crewResourceAdjReportFiles);
						/*
						String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
						String singlePdf="rpt" + timestamp + ".pdf";
						String outputFile = this.getOutputFile(singlePdf);
						
						PdfUtil.concatenatePdfs(crewResourceAdjReportFiles, outputFile);
						String crewReportAdjUrl=this.getOutputUrl(singlePdf);
						reportUrls.add(crewReportAdjUrl);
						*/
					}
					
					PdfUtil.concatenatePdfs(files, outputFile);
					String fileUrl=this.getOutputUrl(singlePdf);
					reportUrls.add(fileUrl);
					
				}else{

					String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
					String singlePdf="rpt" + timestamp + ".pdf";
					String outputFile = this.getOutputFile(singlePdf);
					Collection<String> reportFiles=new ArrayList<String>();
					
					if(StringUtility.hasValue(reportFilename)){
						reportFiles.add(this.getOutputFile(reportFilename));
						//reportUrls.add(this.getOutputUrl(reportFilename));
					}
					
					if(StringUtility.hasValue(reportAdjFilename)){
						reportFiles.add(this.getOutputFile(reportAdjFilename));
						//reportUrls.add(this.getOutputUrl(reportAdjFilename));
					}
					
					PdfUtil.concatenatePdfs(reportFiles, outputFile);
					String singleReportUrl=this.getOutputUrl(singlePdf);
					reportUrls.add(singleReportUrl);
				}
				
				// do post process actions and message additions
				//rulesHandler.executeProcessedActions(incidentResources, dates, filter, dialogueVo);
				//rulesHandler.addAdditionalMessages(dialogueVo);
			}else
				return dialogueVo;
			
		} catch (Exception e) {
			super.handleException(e);
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("Generate_Invoice");
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportUrls);
		dialogueVo.setCourseOfActionVo(coa);
		
		return dialogueVo;
	}

	private TimeInvoice generateInvoiceReports(Collection<OF288TimeInvoice> timeInvoiceData, 
												TimeReportFilter filter,
                                                IncidentResourceVo irVo)
												throws ServiceException, PersistenceException {
		Collection<EmergencyFirefighterCommissaryReportData> adjustmentReports = new ArrayList<EmergencyFirefighterCommissaryReportData>();

		TimeInvoice invoice = new TimeInvoiceImpl();
		invoice.setIsFinal(filter.getFinalInvoice());
		invoice.setIsExported(StringBooleanEnum.N);
		invoice.setIsDraft(filter.getPrintDraftInvoice());
		invoice.setIsDuplicate(filter.getPrintDuplicateOriginalInvoice());
		invoice.setIsInvoiceAdjust(filter.getPrintDeductionsAndInvoice());
		invoice.setIsInvoiceOnly(filter.getPrintInvoiceOnly());
		invoice.setIsAdjustOnly(filter.getPrintItemizedDeductionsOnly());
		invoice.setFirstIncludeDate(timeInvoiceData.iterator().next().getPostStartDate());
		
		if(!DateUtil.hasValue(invoice.getFirstIncludeDate())){
			return null;
		}
		
		invoice.setLastIncludeDate(timeInvoiceData.iterator().next().getPostStopDate());
		invoice.setInvoiceNumber(timeInvoiceData.iterator().next().getIdentificationNumber());

		Collection<Resource> resources = new ArrayList<Resource>();
	    	try {
				resources.add(ResourceVo.toEntity(null, irVo.getResourceVo(), true));
			} catch (Exception e) {
				super.handleException(e);
			}
	    invoice.setResources(resources);
	    
		if (filter.getPrintInvoiceOnly() || filter.getPrintDeductionsAndInvoice()) {
			try {
				//IReport report = new OF288TimeInvoiceReport(timeInvoiceData);
				IReport report = new OF288TimeInvoiceReportV2a(timeInvoiceData);
				report.addCustomField("SUBREPORT_DIR", getSourcePath());
				report.addCustomField("IMAGE_DIR", getImagePath());

				this.generateTimeInvoice(report, invoice);
			} catch (ServiceException se) {
				throw se;
			} catch(Exception e){
				ServiceException sve=super.getServiceException(e);
				throw sve;
			}
		}

		if (filter.getPrintDeductionsAndInvoice() || filter.getPrintItemizedDeductionsOnly()) {
			for (OF288TimeInvoice trd : timeInvoiceData) {
				if (trd.getCommissaryDetails() != null)
					adjustmentReports.add(trd.getCommissaryDetails());
			}
			if (adjustmentReports != null && adjustmentReports.size() > 0) {
				try {
					IReport report = new EmergencyFirefighterCommissaryReport(adjustmentReports);
					report.addCustomField("SUBREPORT_DIR", getSourcePath());

					this.generateAdjustmentInvoice(report, invoice);
				} catch (Exception e) {
					super.handleException(e);
				}
			}
		}

		saveTimeInvoice(invoice);

		return invoice;
	}

	private TimeInvoice generateInvoiceReports2(Collection<OF288TimeInvoice> timeInvoiceData, 
										TimeReportFilter filter,
										IncidentResourceTimeDataVo irTimeDataVo)
								throws ServiceException, PersistenceException {
		Collection<EmergencyFirefighterCommissaryReportData> adjustmentReports = new ArrayList<EmergencyFirefighterCommissaryReportData>();

		TimeInvoice invoice = new TimeInvoiceImpl();
		invoice.setIsFinal(filter.getFinalInvoice());
		invoice.setIsExported(StringBooleanEnum.N);
		invoice.setIsDraft(filter.getPrintDraftInvoice());
		invoice.setIsDuplicate(filter.getPrintDuplicateOriginalInvoice());
		invoice.setIsInvoiceAdjust(filter.getPrintDeductionsAndInvoice());
		invoice.setIsInvoiceOnly(filter.getPrintInvoiceOnly());
		invoice.setIsAdjustOnly(filter.getPrintItemizedDeductionsOnly());
		invoice.setFirstIncludeDate(timeInvoiceData.iterator().next().getPostStartDate());

		if(!DateUtil.hasValue(invoice.getFirstIncludeDate())){
			return null;
		}

		invoice.setLastIncludeDate(timeInvoiceData.iterator().next().getPostStopDate());
		invoice.setInvoiceNumber(timeInvoiceData.iterator().next().getIdentificationNumber());

		Collection<Resource> resources = new ArrayList<Resource>();
		try {
			Resource r = new ResourceImpl();
			r.setId(irTimeDataVo.getResourceId());
			resources.add(r);
		} catch (Exception e) {
			super.handleException(e);
		}
		invoice.setResources(resources);

		if (filter.getPrintInvoiceOnly() || filter.getPrintDeductionsAndInvoice()) {
			try {
				//IReport report = new OF288TimeInvoiceReport(timeInvoiceData);
				IReport report = new OF288TimeInvoiceReportV2a(timeInvoiceData);
				report.addCustomField("SUBREPORT_DIR", getSourcePath());
				report.addCustomField("IMAGE_DIR", getImagePath());

				this.generateTimeInvoice(report, invoice);
			} catch (ServiceException se) {
				throw se;
			} catch(Exception e){
				ServiceException sve=super.getServiceException(e);
				throw sve;
			}
		}

		if (filter.getPrintDeductionsAndInvoice() || filter.getPrintItemizedDeductionsOnly()) {
			for (OF288TimeInvoice trd : timeInvoiceData) {
				if (trd.getCommissaryDetails() != null)
					adjustmentReports.add(trd.getCommissaryDetails());
			}
			if (adjustmentReports != null && adjustmentReports.size() > 0) {
				try {
					IReport report = new EmergencyFirefighterCommissaryReport(adjustmentReports);
					report.addCustomField("SUBREPORT_DIR", getSourcePath());

					this.generateAdjustmentInvoice(report, invoice);
				} catch (Exception e) {
					super.handleException(e);
				}
			}
		}

		saveTimeInvoice(invoice);

		return invoice;
	}

	protected Collection<OF288TimeInvoice> generateReportDataNew(IncidentResourceVo irVo, TimeReportFilter filter) throws ServiceException {

		Collection<OF288TimeInvoice> timeInvoices = new ArrayList<OF288TimeInvoice>();
		OF288TimeInvoice ti;

		try {
			ti = new OF288TimeInvoice();

			ti.setDraftInvoice(filter.getPrintDraftInvoice());
			ti.setDuplicateOriginalInvoice(filter.getPrintDuplicateOriginalInvoice());
			ti.setFinalInvoice(filter.getFinalInvoice());
			ti.setReportPrintedDate(new Date());

			ti.setResourceId(irVo.getResourceVo().getId());

			IncidentResourceDao irdao = (IncidentResourceDao) context.getBean("incidentResourceDao");
			
			String empType="";
			String pointOfHire="";
			
			for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
				AssignmentTimeVo at = a.getAssignmentTimeVo();

				if(StringUtility.hasValue(at.getHiringUnitName())){
					ti.setHiringUnitName(at.getHiringUnitName().toUpperCase());
				}else{
					ti.setHiringUnitName("");
				}
				
				if(StringUtility.hasValue(at.getHiringUnitPhone())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitPhone());
					ti.setHiringPhone(ph);
				}else
					ti.setHiringPhone("");
				
				if(StringUtility.hasValue(at.getHiringUnitFax())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitFax());
					ti.setHiringFax(ph);
				}else
					ti.setHiringFax("");
				
				ti.setRemarks(at.getOfRemarks());

				String resnumid="0000";
				if(LongUtility.hasValue(irVo.getResNumId()))
					resnumid=String.valueOf(irVo.getResNumId());
				else{
					Long lngResNumId=irdao.updateResNumId(irVo.getId());
					if(LongUtility.hasValue(lngResNumId)){
						resnumid=String.valueOf(lngResNumId);
					}
				}
				String invId=StringUtility.leftPad(resnumid, "0", 4);

				String invNumber="F-"+
				irVo.getIncidentVo().getIncidentYear() + "-" +
				irVo.getIncidentVo().getHomeUnitVo().getUnitCode()+"-" +
				irVo.getIncidentVo().getIncidentNumber() + "-" +
				invId;
				//irVo.getResourceVo().getId(); // + "-";
				ti.setIdentificationNumber(invNumber);			 
				//ti.setIdentificationNumber("F-" + ir.getIncidentVo().getIncidentYear() + "-"
				//		+ ir.getIncidentVo().getIncidentNumber() + "-" + ir.getResourceVo().getId() + "-");
				ti.setIdentificationNumber(super.generateInvoiceIdNumber(ti));

				ti.setOfficialNumber(super.getTimeInvoiceNameCount(ti.getIdentificationNumber()));

				// get like named previous invoices
				String invoiceId = ti.getIdentificationNumber();
				if(StringUtility.hasValue(invoiceId)){
					int lngth=invoiceId.length();
					invoiceId=invoiceId.substring(0, lngth-1);
				}
				//int index = invoiceId.lastIndexOf('-');
				//invoiceId = invoiceId.substring(0, index + 1);
				ti.setPreviousInvoices(super.getPreviousTimeInvoicesNames(invoiceId));

				ti.setRequestNumber(a.getRequestNumber());

				PersonDetail pd = new PersonDetail();

				pd.setRequestNumber(a.getRequestNumber());

				if(a.getAssignmentTimeVo() != null) {
					ti.setEmploymentType(a.getAssignmentTimeVo().getEmploymentType() != null ? 
							a.getAssignmentTimeVo().getEmploymentType().name() : "");

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.AD && a.getAssignmentTimeVo().getAdPaymentInfoVo() != null) {
						empType="AD";
						//ti.setInitialEmployment(a.getAssignmentTimeVo().getAdPaymentInfoVo().getInitialEmp());
						ti.setPointOfHire(a.getAssignmentTimeVo().getAdPaymentInfoVo().getPointOfHireOrgVo().getUnitCode());
						pointOfHire=ti.getPointOfHire();
						//ti.setEntitledToReturnTravelTime(a.getAssignmentTimeVo().getAdPaymentInfoVo().getReturnTravel());
						if(StringUtility.hasValue(a.getAssignmentTimeVo().getAdPaymentInfoVo().getEci())){
							String eci = a.getAssignmentTimeVo().getAdPaymentInfoVo().getEci();
							if(eci.length()<10)
								eci=StringUtility.leftPad(eci, "0", 10);
							pd.setSSN(eci);
						}
					}

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER){
						empType="OTHER";
						ti.setPointOfHire(irVo.getResourceVo().getOrganizationVo().getUnitCode());	
						//ti.setPointOfHire("");
					}

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.FED){
						empType="FED";
						ti.setPointOfHire(irVo.getResourceVo().getOrganizationVo().getUnitCode());	
						//ti.setPointOfHire("");
					}

					if(a.getAssignmentTimeVo().getMailingAddressVo() != null) {
						AddressVo addr = a.getAssignmentTimeVo().getMailingAddressVo();
						pd.setStreetAddress1(addr.getAddressLine1() != null ? addr.getAddressLine1() : "");
						pd.setStreetAddress2(addr.getAddressLine2());
						pd.setCity(addr.getCity());
						pd.setState(addr.getCountrySubdivisionVo() != null ? addr.getCountrySubdivisionVo().getCountrySubAbbreviation() : "");
						pd.setZipCode(addr.getPostalCode());
					}
					pd.setPhoneNumber(a.getAssignmentTimeVo().getPhone());
					pd.setFaxNumber(a.getAssignmentTimeVo().getFax());
				}
				// ti.setTransferredFrom(transferredFrom);
				// ti.setEntitledToReturnTransportation(entitledToReturnTransportation);
				// ti.setEmployeeDischarged(employeeDischarged);
				// ti.setEmployeeQuit(employeeQuit);


				if(irVo.getResourceVo() != null) {
					if(BooleanUtility.isTrue(irVo.getResourceVo().getPerson())){
						ti.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName());
						pd.setFirstName(irVo.getResourceVo().getFirstName());
						pd.setLastName(irVo.getResourceVo().getLastName());
					}else{
						ti.setResourceName(irVo.getResourceVo().getResourceName());
						pd.setFirstName("");
						pd.setLastName(irVo.getResourceVo().getResourceName());
					}
					/*
					if (irVo.getResourceVo().getResourceName() != null) {
						ti.setResourceName(irVo.getResourceVo().getResourceName());
						pd.setFirstName("");
						pd.setLastName(irVo.getResourceVo().getResourceName());
					} else {
						ti.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName());
						pd.setFirstName(irVo.getResourceVo().getFirstName());
						pd.setLastName(irVo.getResourceVo().getLastName());
					}
					*/
					//pd.setAccountingCode(ir.getResourceVo().getOrganizationVo() != null ?
					//		ir.getResourceVo().getOrganizationVo().getUnitCode() : "");
					if(null != irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo() 
							&& null != irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getAccountCodeVo()){
						String iac = this.getLastPostingIACValue(at.getAssignmentTimePostVos());
						pd.setAccountingCode(iac);
						//pd.setAccountingCode(ir.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
					}else{
						pd.setAccountingCode("");
						//ir.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo() .getResourceVo().getOrganizationVo() != null ?
						//		ir.getResourceVo().getOrganizationVo().getUnitCode() : "");
					}
				}

				ti.setPersonDetail(pd);

				timeInvoices.add(ti);

				String incidentTag = "";
				incidentTag = irVo.getIncidentVo().getHomeUnitVo().getUnitCode() 
							+ "-" + irVo.getIncidentVo().getIncidentNumber();
				int groupCnt=0;
				String groupClassification="";
				String groupIndex="a";
				String newGroupClassification="";
				
				IncidentDao idao = (IncidentDao) context.getBean("incidentDao");
				
				for (AssignmentTimePostVo atp : at.getAssignmentTimePostVos()) {

					if(!DateUtil.hasValue(atp.getLastInvoiceDate())){
						
						GenericVo gvo = null;						
						Long incAcctCodeId=atp.getIncidentAccountCodeVo().getId();
						if(LongUtility.hasValue(incAcctCodeId)){
							gvo = idao.getDataByIncAcctCodeId(incAcctCodeId);
						}

						OF288TimeDetail td = new OF288TimeDetail();
						td.setRequestNumber(a.getRequestNumber());
						
						if(irVo.getIncidentVo() != null) {

							td.setIncidentName(atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentName());
							//td.setIncidentName(ir.getIncidentVo().getIncidentName());

							//td.setIncidentNumber(ir.getIncidentVo().getIncidentNumber());
							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								String incTag=gvo.getStr1()+"-"+gvo.getStr2();
								td.setIncidentNumber(incTag);
							}else
								td.setIncidentNumber(incidentTag);

							// CA-TNF would render TNF
							String unitcode=irVo.getIncidentVo().getHomeUnitVo().getUnitCode();
							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								unitcode=gvo.getStr1();
							}
							
							if(StringUtility.hasValue(unitcode)){
								String cd = unitcode.substring(3, (unitcode.length()));
								td.setIncidentLocation(cd);
							}else
								td.setIncidentLocation("");


							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								String state = unitcode.substring(0, 2);
								td.setIncidentState(state);
							}else{
								td.setIncidentState(irVo.getIncidentVo().getCountryCodeSubdivisionVo() != null ?
										irVo.getIncidentVo().getCountryCodeSubdivisionVo().getCountrySubAbbreviation() : "");
							}

							newGroupClassification=atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentName();
							newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCodeVo().getIncidentVo().getHomeUnitVo().getUnitCode()
							+"-"+atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentNumber();

							//td.setYear(ir.getIncidentVo().getIncidentYear());
						}

						Calendar cal = Calendar.getInstance();
						cal.setTime(atp.getPostStartDate());
						td.setPostStartDay(cal.get(Calendar.DAY_OF_MONTH));
						td.setPostStartMonth(cal.get(Calendar.MONTH) + 1);
						td.setPostStartYear(cal.get(Calendar.YEAR));
						td.setPostStartHour(cal.get(Calendar.HOUR_OF_DAY));
						td.setPostStartMinute(cal.get(Calendar.MINUTE));
						td.setHours(0.0);

						newGroupClassification=newGroupClassification+"-"+DateUtil.getYearAsInt(atp.getPostStartDate());

						td.setYear(DateUtil.getYearAsInt(atp.getPostStartDate()) );

						if(null != atp.getPostStopDate()){
							cal.setTime(atp.getPostStopDate());
							Integer hour=cal.get(Calendar.HOUR_OF_DAY);
							Integer minute=cal.get(Calendar.MINUTE);
							if(hour==00 && minute==0){
								hour=24;
							}else if(hour==23 && minute==59){
								hour=24;
								minute=0;
							}
							
							td.setPostStopHour(hour);
							td.setPostStopMinute(minute);

							//System.out.println(td.getPostStopHour());
						//	System.out.println(td.getPostStopMinute());
							
							if( (td.getPostStopHour().intValue()==23 && td.getPostStopMinute().intValue()==59 )){
								td.setPostStopHour(Integer.valueOf(24));
								td.setPostStopMinute(Integer.valueOf(00));
							}
									
							td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
						}
						if (td.getHours() <= 0.0) {
							ti.setHasPendingTotals(true);
						}
						td.setStartTimeOnly(atp.getReturnTravelStartOnly());

						td.setSpecialPayCode(atp.getSpecialPayVo() != null ? atp.getSpecialPayVo().getCode() : "");
						td.setSpecialPayId(atp.getSpecialPayVo() != null ? atp.getSpecialPayVo().getId() : null);
						td.setShowStartStop(true);
						td.setShowHoursSpecial(false);
						td.setShowHours(true);
						
						if(StringUtility.hasValue(td.getSpecialPayCode())){
							String code=td.getSpecialPayCode();
							if(code.equals("COP") || code.equals("DAY OFF") || code.equals("GUAR")){
								if(code.equals("GUAR"))
									td.setSpecialPayCode("GUARANTEE");
								
								td.setShowStartStop(false);
								td.setShowHoursSpecial(false);
								td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
							}else{
								td.setShowHoursSpecial(true);
								td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
							}
						}
						
						if(null != atp.getIncidentAccountCodeVo() 
								&& null != atp.getIncidentAccountCodeVo().getAccountCodeVo()){

							if(null != atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo()
									&& StringUtility.hasValue(atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode())){
								td.setAccountingCode(atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode()
										+ "/" + atp.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
								newGroupClassification=newGroupClassification+"-"+td.getAccountingCode();
							}else{
								td.setAccountingCode(atp.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
								newGroupClassification=newGroupClassification+"-"+"-"+td.getAccountingCode();;
							}
						}else
							td.setAccountingCode("UNKNOWN");

						td.setHalfRate(atp.getIsHalfRate());
						td.setKindCode("");
						td.setAdClass("");
						
						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER){
							td.setFireFighterClassification(atp.getKindVo().getCode());
							td.setKindCode(atp.getKindVo().getCode());
							//+"/"+ String.valueOf(atp.getOtherRate()));
							td.setRate(atp.getOtherRate() != null ? atp.getOtherRate().doubleValue() : 0.0);
							newGroupClassification=newGroupClassification+"-"+atp.getKindVo().getCode()+"-"+td.getRate();
						}

						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.AD){
							td.setFireFighterClassification((atp.getRateClassRateVo() != null ? atp.getRateClassRateVo()
									.getRateClassName()
									+ " " : "")
									+ atp.getKindVo().getCode());
							td.setRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
							td.setKindCode(atp.getKindVo().getCode());
							td.setAdClass(atp.getRateClassRateVo().getRateClassName());
							newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
						}
						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.FED){
							td.setFireFighterClassification(atp.getKindVo().getCode());
							td.setKindCode(atp.getKindVo().getCode());
							td.setRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
							newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
						}

						if(StringUtility.hasValue(groupClassification)){
							if(groupClassification.equals(newGroupClassification)){
								if( (groupCnt + 1) == 8){
									if(groupIndex.equals("a"))
										groupIndex="b";
									else
										groupIndex="a";

									groupCnt=1;
								}else{
									groupCnt+=1;
								}
							}else{
								groupCnt=1;
								if(groupIndex.equals("a"))
									groupIndex="b";
								else
									groupIndex="a";

								groupClassification=newGroupClassification;
							}
						}else{
							groupClassification=newGroupClassification;
							groupCnt=1;
							groupIndex="a";
						}

						td.setGroupIndex(groupIndex);

						if (td.getRate() <= 0.0) {
							ti.setHasPendingTotals(true);
						}

						// td.setFirstDateToIncludeOnReport(firstDateToIncludeOnReport);
						// td.setLastDateToIncludeOnReport(lastDateToIncludeOnReport);

						td.setEmployeeType(a.getAssignmentTimeVo() != null ? 
								a.getAssignmentTimeVo().getEmploymentType() != null ?
										a.getAssignmentTimeVo().getEmploymentType().name() : "" : "");

						ti.getOf288TimeDetails().add(td);
					}
				}

				if(BooleanUtility.isTrue(filter.getFinalInvoice())){
					groupCnt=0;
					// add an additional grouping per defect 3495
					OF288TimeDetail td = new OF288TimeDetail();
					
					if(groupIndex.equals("a"))
						groupIndex="b";
					else
						groupIndex="a";
						
					td.setFireFighterClassification("Blank");
					td.setStartTimeOnly(false);
					td.setShowStartStop(false);
					td.setShowHoursSpecial(false);
					td.setShowHours(false);
					ti.setHasPendingTotals(false);
					td.setIncidentName("");
					td.setIncidentNumber("");
					td.setIncidentLocation("");
					td.setEmployeeType("");

					td.setGroupIndex(groupIndex);

					ti.getOf288TimeDetails().add(td);
				}
				
				/* Add adjustment Details */
				EmergencyFirefighterCommissaryReportData adj = new EmergencyFirefighterCommissaryReportData();

				adj.setPersonDetail(ti.getPersonDetail());

				if(empType.equals("FED")){
					adj.setRegularGovEmployee("X");
				}else if(empType.equals("AD")){
					adj.setCasualEmployee("X");
				}else if(empType.equals("OTHER")){
					adj.setOtherEmployee("X");
				}
				
				if(StringUtility.hasValue(at.getHiringUnitName())){
					adj.setHiringUnitName(at.getHiringUnitName().toUpperCase());
				}else{
					adj.setHiringUnitName("");
				}
				if(StringUtility.hasValue(at.getHiringUnitPhone())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitPhone());
					adj.setHiringPhone(ph);
				}else
					adj.setHiringPhone("");
				
				if(StringUtility.hasValue(at.getHiringUnitFax())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitFax());
					adj.setHiringFax(ph);
				}else
					adj.setHiringFax("");

				adj.setPointOfHire(pointOfHire);
				
				adj.setRequestNumber(ti.getRequestNumber());
				adj.setIdentificationNumber(ti.getIdentificationNumber());
				adj.setOfficialNumber(ti.getOfficialNumber());
				adj.setReportType(ti.getReportType());
				adj.setReportTypeHeader(ti.getReportTypeHeader());
				if(null != ti.getPostStartDate())
					adj.setStartDate(ti.getPostStartDate());
				if(null != ti.getPostStopDate())
					adj.setEndDate(ti.getPostStopDate());

				if(irVo.getIncidentVo() != null) {
					// CA-TNF would render TNF
					String unitcode=irVo.getIncidentVo().getHomeUnitVo().getUnitCode();
					if(StringUtility.hasValue(unitcode)){
						String cd = unitcode.substring(3, (unitcode.length()));
						adj.setIncidentLocation(cd);
					}else
						adj.setIncidentLocation("");
					adj.setIncidentNumber(irVo.getIncidentVo().getHomeUnitVo().getUnitCode() + "-" +
							irVo.getIncidentVo().getIncidentNumber());

					adj.setIncidentName(irVo.getIncidentVo().getIncidentName());
					adj.setUnitCode(irVo.getIncidentVo().getHomeUnitVo() != null ? 
							irVo.getIncidentVo().getHomeUnitVo().getUnitCode() : "");
				}

				adj.setIncidentState(at.getMailingAddressVo() != null ?
						at.getMailingAddressVo().getCountrySubdivisionVo() != null ?
								at.getMailingAddressVo().getCountrySubdivisionVo().getCountrySubAbbreviation() : "" : "");

				Date firstAdjustmentDate=null;
				Collection<TimeAssignAdjustVo> taaVos = a.getTimeAssignAdjustVos();
				Collections.sort((List<TimeAssignAdjustVo>)taaVos, new ActivityDateComparator());
				
				for (TimeAssignAdjustVo taa : taaVos) {
					String lastIncludeDate=DateUtil.toDateString(filter.getLastDateToIncludeOnReport(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					
					String adjDate="";
					if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
						Date chkDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
						adjDate=DateUtil.toDateString(chkDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);
					}

					Date dteLastInclude=DateUtil.toDate(lastIncludeDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					dteLastInclude=DateUtil.addMilitaryTimeToDate(dteLastInclude, "2359");
					Date dteAdj=DateUtil.toDate(adjDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					if(null == firstAdjustmentDate)
						firstAdjustmentDate=dteAdj;
					else{
						if(dteAdj.before(firstAdjustmentDate))
							firstAdjustmentDate=dteAdj;
					}

					if (taa.getDeletedDate() == null 
							&& !DateUtil.hasValue(taa.getLastInvoiceDate())
							&& (!dteAdj.after(dteLastInclude) ) ){
						EmergencyFirefighterCommissarySubReportData sub = new EmergencyFirefighterCommissarySubReportData();

						if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
							Date chkDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
							sub.setPurchaseDate(chkDate);
						}
						sub.setAccountingCode(taa.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
						//sub.setPurchaseDate(taa.getActivityDate());
						sub.setCategoryName(taa.getAdjustCategoryVo() != null ? 
								taa.getAdjustCategoryVo().getDescription() : "");
						sub.setItem(taa.getCommodity());
						if (taa.getAdjustmentType() == AdjustmentTypeEnum.ADDITION) {
							sub.setAmount(taa.getAdjustmentAmount() != null ? 
									taa.getAdjustmentAmount().doubleValue() : 0.0);
						} else {
							sub.setAmount(-1 * (taa.getAdjustmentAmount() != null ? 
									taa.getAdjustmentAmount().doubleValue() : 0.0) );
						}

						adj.getSubReportData().add(sub);
					}
					if (adj.getSubReportData().size() > 0) {
						ti.setCommissaryDetails(adj);
						ti.setCommissaryReport(true);
					}
				}
				
				if(null == ti.getPostStartDate() && null != firstAdjustmentDate){
					ti.setPostStartDate(firstAdjustmentDate);
				}
				if(null == ti.getPostStopDate() && null != filter.getLastDateToIncludeOnReport()){
					ti.setPostStopDate(filter.getLastDateToIncludeOnReport());
				}
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return timeInvoices;
	}
	
	//protected Collection<OF288TimeInvoice> generateReportDataOrig(IncidentResourceVo irVo, TimeReportFilter filter) throws ServiceException {
	protected Collection<OF288TimeInvoice> generateReportData(IncidentResourceVo irVo, TimeReportFilter filter) throws ServiceException {

		Collection<OF288TimeInvoice> timeInvoices = new ArrayList<OF288TimeInvoice>();
		OF288TimeInvoice ti;

		try {
			ti = new OF288TimeInvoice();

			ti.setDraftInvoice(filter.getPrintDraftInvoice());
			ti.setDuplicateOriginalInvoice(filter.getPrintDuplicateOriginalInvoice());
			ti.setFinalInvoice(filter.getFinalInvoice());
			ti.setReportPrintedDate(new Date());

			ti.setResourceId(irVo.getResourceVo().getId());

			IncidentResourceDao irdao = (IncidentResourceDao) context.getBean("incidentResourceDao");
			
			String empType="";
			String pointOfHire="";
			
			for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
				AssignmentTimeVo at = a.getAssignmentTimeVo();

				if(StringUtility.hasValue(at.getHiringUnitName())){
					ti.setHiringUnitName(at.getHiringUnitName().toUpperCase());
				}else{
					ti.setHiringUnitName("");
				}
				
				if(StringUtility.hasValue(at.getHiringUnitPhone())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitPhone());
					ti.setHiringPhone(ph);
				}else
					ti.setHiringPhone("");
				
				if(StringUtility.hasValue(at.getHiringUnitFax())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitFax());
					ti.setHiringFax(ph);
				}else
					ti.setHiringFax("");
				
				ti.setRemarks(at.getOfRemarks());

				String resnumid="0000";
				if(LongUtility.hasValue(irVo.getResNumId()))
					resnumid=String.valueOf(irVo.getResNumId());
				else{
					Long lngResNumId=irdao.updateResNumId(irVo.getId());
					if(LongUtility.hasValue(lngResNumId)){
						resnumid=String.valueOf(lngResNumId);
					}
				}
				String invId=StringUtility.leftPad(resnumid, "0", 4);

				String invNumber="F-"+
				irVo.getIncidentVo().getIncidentYear() + "-" +
				irVo.getIncidentVo().getHomeUnitVo().getUnitCode()+"-" +
				irVo.getIncidentVo().getIncidentNumber() + "-" +
				invId;
				//irVo.getResourceVo().getId(); // + "-";
				ti.setIdentificationNumber(invNumber);			 
				//ti.setIdentificationNumber("F-" + ir.getIncidentVo().getIncidentYear() + "-"
				//		+ ir.getIncidentVo().getIncidentNumber() + "-" + ir.getResourceVo().getId() + "-");
				ti.setIdentificationNumber(super.generateInvoiceIdNumber(ti));

				ti.setOfficialNumber(super.getTimeInvoiceNameCount(ti.getIdentificationNumber()));

				// get like named previous invoices
				String invoiceId = ti.getIdentificationNumber();
				if(StringUtility.hasValue(invoiceId)){
					int lngth=invoiceId.length();
					invoiceId=invoiceId.substring(0, lngth-1);
				}
				//int index = invoiceId.lastIndexOf('-');
				//invoiceId = invoiceId.substring(0, index + 1);
				ti.setPreviousInvoices(super.getPreviousTimeInvoicesNames(invoiceId));

				ti.setRequestNumber(a.getRequestNumber());

				PersonDetail pd = new PersonDetail();

				pd.setRequestNumber(a.getRequestNumber());

				if(a.getAssignmentTimeVo() != null) {
					ti.setEmploymentType(a.getAssignmentTimeVo().getEmploymentType() != null ? 
							a.getAssignmentTimeVo().getEmploymentType().name() : "");

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.AD && a.getAssignmentTimeVo().getAdPaymentInfoVo() != null) {
						empType="AD";
						//ti.setInitialEmployment(a.getAssignmentTimeVo().getAdPaymentInfoVo().getInitialEmp());
						ti.setPointOfHire(a.getAssignmentTimeVo().getAdPaymentInfoVo().getPointOfHireOrgVo().getUnitCode());
						pointOfHire=ti.getPointOfHire();
						//ti.setEntitledToReturnTravelTime(a.getAssignmentTimeVo().getAdPaymentInfoVo().getReturnTravel());
						if(StringUtility.hasValue(a.getAssignmentTimeVo().getAdPaymentInfoVo().getEci())){
							String eci = a.getAssignmentTimeVo().getAdPaymentInfoVo().getEci();
							if(eci.length()<10)
								eci=StringUtility.leftPad(eci, "0", 10);
							pd.setSSN(eci);
						}
					}

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER){
						empType="OTHER";
						ti.setPointOfHire(irVo.getResourceVo().getOrganizationVo().getUnitCode());	
						//ti.setPointOfHire("");
					}

					if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.FED){
						empType="FED";
						ti.setPointOfHire(irVo.getResourceVo().getOrganizationVo().getUnitCode());	
						//ti.setPointOfHire("");
					}

					if(a.getAssignmentTimeVo().getMailingAddressVo() != null) {
						AddressVo addr = a.getAssignmentTimeVo().getMailingAddressVo();
						pd.setStreetAddress1(addr.getAddressLine1() != null ? addr.getAddressLine1() : "");
						pd.setStreetAddress2(addr.getAddressLine2());
						pd.setCity(addr.getCity());
						pd.setState(addr.getCountrySubdivisionVo() != null ? addr.getCountrySubdivisionVo().getCountrySubAbbreviation() : "");
						pd.setZipCode(addr.getPostalCode());
					}
					pd.setPhoneNumber(a.getAssignmentTimeVo().getPhone());
					pd.setFaxNumber(a.getAssignmentTimeVo().getFax());
				}
				// ti.setTransferredFrom(transferredFrom);
				// ti.setEntitledToReturnTransportation(entitledToReturnTransportation);
				// ti.setEmployeeDischarged(employeeDischarged);
				// ti.setEmployeeQuit(employeeQuit);


				if(irVo.getResourceVo() != null) {
					if(BooleanUtility.isTrue(irVo.getResourceVo().getPerson())){
						ti.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName());
						pd.setFirstName(irVo.getResourceVo().getFirstName());
						pd.setLastName(irVo.getResourceVo().getLastName());
					}else{
						ti.setResourceName(irVo.getResourceVo().getResourceName());
						pd.setFirstName("");
						pd.setLastName(irVo.getResourceVo().getResourceName());
					}
					/*
					if (irVo.getResourceVo().getResourceName() != null) {
						ti.setResourceName(irVo.getResourceVo().getResourceName());
						pd.setFirstName("");
						pd.setLastName(irVo.getResourceVo().getResourceName());
					} else {
						ti.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName());
						pd.setFirstName(irVo.getResourceVo().getFirstName());
						pd.setLastName(irVo.getResourceVo().getLastName());
					}
					*/
					//pd.setAccountingCode(ir.getResourceVo().getOrganizationVo() != null ?
					//		ir.getResourceVo().getOrganizationVo().getUnitCode() : "");
					if(null != irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo() 
							&& null != irVo.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getAccountCodeVo()){
						String iac = this.getLastPostingIACValue(at.getAssignmentTimePostVos());
						pd.setAccountingCode(iac);
						//pd.setAccountingCode(ir.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
					}else{
						pd.setAccountingCode("");
						//ir.getWorkPeriodVo().getWpDefaultIncidentAccountCodeVo() .getResourceVo().getOrganizationVo() != null ?
						//		ir.getResourceVo().getOrganizationVo().getUnitCode() : "");
					}
				}

				ti.setPersonDetail(pd);

				timeInvoices.add(ti);

				String incidentTag = "";
				incidentTag = irVo.getIncidentVo().getHomeUnitVo().getUnitCode() 
							+ "-" + irVo.getIncidentVo().getIncidentNumber();
				int groupCnt=0;
				String groupClassification="";
				String groupIndex="a";
				String newGroupClassification="";
				
				IncidentDao idao = (IncidentDao) context.getBean("incidentDao");
				
				for (AssignmentTimePostVo atp : at.getAssignmentTimePostVos()) {

					if(!DateUtil.hasValue(atp.getLastInvoiceDate())){
						
						GenericVo gvo = null;						
						Long incAcctCodeId=atp.getIncidentAccountCodeVo().getId();
						if(LongUtility.hasValue(incAcctCodeId)){
							gvo = idao.getDataByIncAcctCodeId(incAcctCodeId);
						}

						OF288TimeDetail td = new OF288TimeDetail();
						td.setRequestNumber(a.getRequestNumber());
						
						if(irVo.getIncidentVo() != null) {

							td.setIncidentName(atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentName());
							//td.setIncidentName(ir.getIncidentVo().getIncidentName());

							//td.setIncidentNumber(ir.getIncidentVo().getIncidentNumber());
							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								String incTag=gvo.getStr1()+"-"+gvo.getStr2();
								td.setIncidentNumber(incTag);
							}else
								td.setIncidentNumber(incidentTag);

							// CA-TNF would render TNF
							String unitcode=irVo.getIncidentVo().getHomeUnitVo().getUnitCode();
							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								unitcode=gvo.getStr1();
							}
							
							if(StringUtility.hasValue(unitcode)){
								String cd = unitcode.substring(3, (unitcode.length()));
								td.setIncidentLocation(cd);
							}else
								td.setIncidentLocation("");


							if(null != gvo && StringUtility.hasValue(gvo.getStr1())){
								String state = unitcode.substring(0, 2);
								td.setIncidentState(state);
							}else{
								td.setIncidentState(irVo.getIncidentVo().getCountryCodeSubdivisionVo() != null ?
										irVo.getIncidentVo().getCountryCodeSubdivisionVo().getCountrySubAbbreviation() : "");
							}

							newGroupClassification=atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentName();
							newGroupClassification=newGroupClassification+"-"+atp.getIncidentAccountCodeVo().getIncidentVo().getHomeUnitVo().getUnitCode()
							+"-"+atp.getIncidentAccountCodeVo().getIncidentVo().getIncidentNumber();

							//td.setYear(ir.getIncidentVo().getIncidentYear());
						}

						Calendar cal = Calendar.getInstance();
						cal.setTime(atp.getPostStartDate());
						td.setPostStartDay(cal.get(Calendar.DAY_OF_MONTH));
						td.setPostStartMonth(cal.get(Calendar.MONTH) + 1);
						td.setPostStartYear(cal.get(Calendar.YEAR));
						td.setPostStartHour(cal.get(Calendar.HOUR_OF_DAY));
						td.setPostStartMinute(cal.get(Calendar.MINUTE));
						td.setHours(0.0);

						newGroupClassification=newGroupClassification+"-"+DateUtil.getYearAsInt(atp.getPostStartDate());

						td.setYear(DateUtil.getYearAsInt(atp.getPostStartDate()) );

						if(null != atp.getPostStopDate()){
							cal.setTime(atp.getPostStopDate());
							Integer hour=cal.get(Calendar.HOUR_OF_DAY);
							Integer minute=cal.get(Calendar.MINUTE);
							if(hour==00 && minute==0){
								hour=24;
							}else if(hour==23 && minute==59){
								hour=24;
								minute=0;
							}
							
							td.setPostStopHour(hour);
							td.setPostStopMinute(minute);

							//System.out.println(td.getPostStopHour());
						//	System.out.println(td.getPostStopMinute());
							
							if( (td.getPostStopHour().intValue()==23 && td.getPostStopMinute().intValue()==59 )){
								td.setPostStopHour(Integer.valueOf(24));
								td.setPostStopMinute(Integer.valueOf(00));
							}
									
							td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
						}
						if (td.getHours() <= 0.0) {
							ti.setHasPendingTotals(true);
						}
						td.setStartTimeOnly(atp.getReturnTravelStartOnly());

						td.setSpecialPayCode(atp.getSpecialPayVo() != null ? atp.getSpecialPayVo().getCode() : "");
						td.setSpecialPayId(atp.getSpecialPayVo() != null ? atp.getSpecialPayVo().getId() : null);
						td.setShowStartStop(true);
						td.setShowHoursSpecial(false);
						td.setShowHours(true);
						
						if(StringUtility.hasValue(td.getSpecialPayCode())){
							String code=td.getSpecialPayCode();
							if(code.equals("COP") || code.equals("DAY OFF") || code.equals("GUAR")){
								if(code.equals("GUAR"))
									td.setSpecialPayCode("GUARANTEE");
								
								td.setShowStartStop(false);
								td.setShowHoursSpecial(false);
								td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
							}else{
								td.setShowHoursSpecial(true);
								td.setHours(atp.getQuantity() != null ? atp.getQuantity().doubleValue() : 0.0);
							}
						}
						
						if(null != atp.getIncidentAccountCodeVo() 
								&& null != atp.getIncidentAccountCodeVo().getAccountCodeVo()){

							if(null != atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo()
									&& StringUtility.hasValue(atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode())){
								td.setAccountingCode(atp.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode()
										+ "/" + atp.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
								newGroupClassification=newGroupClassification+"-"+td.getAccountingCode();
							}else{
								td.setAccountingCode(atp.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
								newGroupClassification=newGroupClassification+"-"+"-"+td.getAccountingCode();;
							}
						}else
							td.setAccountingCode("UNKNOWN");

						td.setHalfRate(atp.getIsHalfRate());
						td.setKindCode("");
						td.setAdClass("");
						
						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.OTHER){
							td.setFireFighterClassification(atp.getKindVo().getCode());
							td.setKindCode(atp.getKindVo().getCode());
							//+"/"+ String.valueOf(atp.getOtherRate()));
							td.setRate(atp.getOtherRate() != null ? atp.getOtherRate().doubleValue() : 0.0);
							newGroupClassification=newGroupClassification+"-"+atp.getKindVo().getCode()+"-"+td.getRate();
						}

						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.AD){
							td.setFireFighterClassification((atp.getRateClassRateVo() != null ? atp.getRateClassRateVo()
									.getRateClassName()
									+ " " : "")
									+ atp.getKindVo().getCode());
							td.setRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
							td.setKindCode(atp.getKindVo().getCode());
							td.setAdClass(atp.getRateClassRateVo().getRateClassName());
							newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
						}
						if(a.getAssignmentTimeVo().getEmploymentType()==EmploymentTypeEnum.FED){
							td.setFireFighterClassification(atp.getKindVo().getCode());
							td.setKindCode(atp.getKindVo().getCode());
							td.setRate(atp.getRateAmount() != null ? atp.getRateAmount().doubleValue() : 0.0);
							newGroupClassification=newGroupClassification+"-"+td.getFireFighterClassification()+"-"+td.getRate();
						}

						if(StringUtility.hasValue(groupClassification)){
							if(groupClassification.equals(newGroupClassification)){
								if( (groupCnt + 1) == 8){
									if(groupIndex.equals("a"))
										groupIndex="b";
									else
										groupIndex="a";

									groupCnt=1;
								}else{
									groupCnt+=1;
								}
							}else{
								groupCnt=1;
								if(groupIndex.equals("a"))
									groupIndex="b";
								else
									groupIndex="a";

								groupClassification=newGroupClassification;
							}
						}else{
							groupClassification=newGroupClassification;
							groupCnt=1;
							groupIndex="a";
						}

						td.setGroupIndex(groupIndex);

						if(DecimalUtil.hasDoubleValue(td.getRate())){
							if (td.getRate() <= 0.0) {
								ti.setHasPendingTotals(true);
							}
						}else
							ti.setHasPendingTotals(true);
						
						// td.setFirstDateToIncludeOnReport(firstDateToIncludeOnReport);
						// td.setLastDateToIncludeOnReport(lastDateToIncludeOnReport);

						td.setEmployeeType(a.getAssignmentTimeVo() != null ? 
								a.getAssignmentTimeVo().getEmploymentType() != null ?
										a.getAssignmentTimeVo().getEmploymentType().name() : "" : "");

						ti.getOf288TimeDetails().add(td);
					}
				}

				if(BooleanUtility.isTrue(filter.getFinalInvoice())){
					groupCnt=0;
					// add an additional grouping per defect 3495
					OF288TimeDetail td = new OF288TimeDetail();
					
					if(groupIndex.equals("a"))
						groupIndex="b";
					else
						groupIndex="a";
						
					td.setFireFighterClassification("Blank");
					td.setStartTimeOnly(false);
					td.setShowStartStop(false);
					td.setShowHoursSpecial(false);
					td.setShowHours(false);
					ti.setHasPendingTotals(false);
					td.setIncidentName("");
					td.setIncidentNumber("");
					td.setIncidentLocation("");
					td.setEmployeeType("");

					td.setGroupIndex(groupIndex);

					ti.getOf288TimeDetails().add(td);
				}
				
				/* Add adjustment Details */
				EmergencyFirefighterCommissaryReportData adj = new EmergencyFirefighterCommissaryReportData();

				adj.setPersonDetail(ti.getPersonDetail());

				if(empType.equals("FED")){
					adj.setRegularGovEmployee("X");
				}else if(empType.equals("AD")){
					adj.setCasualEmployee("X");
				}else if(empType.equals("OTHER")){
					adj.setOtherEmployee("X");
				}
				
				if(StringUtility.hasValue(at.getHiringUnitName())){
					adj.setHiringUnitName(at.getHiringUnitName().toUpperCase());
				}else{
					adj.setHiringUnitName("");
				}
				if(StringUtility.hasValue(at.getHiringUnitPhone())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitPhone());
					adj.setHiringPhone(ph);
				}else
					adj.setHiringPhone("");
				
				if(StringUtility.hasValue(at.getHiringUnitFax())){
					String ph = PhoneNumberUtil.formatNumber(at.getHiringUnitFax());
					adj.setHiringFax(ph);
				}else
					adj.setHiringFax("");

				adj.setPointOfHire(pointOfHire);
				
				adj.setRequestNumber(ti.getRequestNumber());
				adj.setIdentificationNumber(ti.getIdentificationNumber());
				adj.setOfficialNumber(ti.getOfficialNumber());
				adj.setReportType(ti.getReportType());
				adj.setReportTypeHeader(ti.getReportTypeHeader());
				if(DateUtil.hasValue(ti.getPostStartDate())){
					adj.setStartDate(ti.getPostStartDate());
				}
				if(DateUtil.hasValue(ti.getPostStopDate())){
					adj.setEndDate(ti.getPostStopDate());
				}

				if(irVo.getIncidentVo() != null) {
					// CA-TNF would render TNF
					String unitcode=irVo.getIncidentVo().getHomeUnitVo().getUnitCode();
					if(StringUtility.hasValue(unitcode)){
						String cd = unitcode.substring(3, (unitcode.length()));
						adj.setIncidentLocation(cd);
					}else
						adj.setIncidentLocation("");
					adj.setIncidentNumber(irVo.getIncidentVo().getHomeUnitVo().getUnitCode() + "-" +
							irVo.getIncidentVo().getIncidentNumber());

					adj.setIncidentName(irVo.getIncidentVo().getIncidentName());
					adj.setUnitCode(irVo.getIncidentVo().getHomeUnitVo() != null ? 
							irVo.getIncidentVo().getHomeUnitVo().getUnitCode() : "");
				}

				adj.setIncidentState(at.getMailingAddressVo() != null ?
						at.getMailingAddressVo().getCountrySubdivisionVo() != null ?
								at.getMailingAddressVo().getCountrySubdivisionVo().getCountrySubAbbreviation() : "" : "");

				Date firstAdjustmentDate=null;
				Collection<TimeAssignAdjustVo> taaVos = a.getTimeAssignAdjustVos();
				Collections.sort((List<TimeAssignAdjustVo>)taaVos, new ActivityDateComparator());
				
				for (TimeAssignAdjustVo taa : taaVos) {
					String lastIncludeDate=DateUtil.toDateString(filter.getLastDateToIncludeOnReport(),DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					
					String adjDate="";
					if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
						Date chkDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
						adjDate=DateUtil.toDateString(chkDate,DateUtil.MM_SLASH_DD_SLASH_YYYY);
					}

					Date dteLastInclude=DateUtil.toDate(lastIncludeDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					dteLastInclude=DateUtil.addMilitaryTimeToDate(dteLastInclude, "2359");
					Date dteAdj=DateUtil.toDate(adjDate, DateUtil.MM_SLASH_DD_SLASH_YYYY);
					
					if(null == firstAdjustmentDate)
						firstAdjustmentDate=dteAdj;
					else{
						if(dteAdj.before(firstAdjustmentDate))
							firstAdjustmentDate=dteAdj;
					}

					if (taa.getDeletedDate() == null 
							&& !DateUtil.hasValue(taa.getLastInvoiceDate())
							&& (!dteAdj.after(dteLastInclude) ) ){
						EmergencyFirefighterCommissarySubReportData sub = new EmergencyFirefighterCommissarySubReportData();

						if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
							Date chkDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
							sub.setPurchaseDate(chkDate);
						}
						sub.setAccountingCode(taa.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode());
						//sub.setPurchaseDate(taa.getActivityDate());
						sub.setCategoryName(taa.getAdjustCategoryVo() != null ? 
								taa.getAdjustCategoryVo().getDescription() : "");
						sub.setItem(taa.getCommodity());
						if (taa.getAdjustmentType() == AdjustmentTypeEnum.ADDITION) {
							sub.setAmount(taa.getAdjustmentAmount() != null ? 
									taa.getAdjustmentAmount().doubleValue() : 0.0);
						} else {
							sub.setAmount(-1 * (taa.getAdjustmentAmount() != null ? 
									taa.getAdjustmentAmount().doubleValue() : 0.0) );
						}

						adj.getSubReportData().add(sub);
					}
					if (adj.getSubReportData().size() > 0) {
						ti.setCommissaryDetails(adj);
						ti.setCommissaryReport(true);
					}
				}
				
				if(null == ti.getPostStartDate() && null != firstAdjustmentDate){
					ti.setPostStartDate(firstAdjustmentDate);
				}
				if(null == ti.getPostStopDate() && null != filter.getLastDateToIncludeOnReport()){
					ti.setPostStopDate(filter.getLastDateToIncludeOnReport());
				}
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return timeInvoices;
	}

	@Override
	public DialogueVo reprintTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null) dialogueVo = new DialogueVo();

		Boolean isCrew=false;
		Collection<TimeReportFilter> filters = new ArrayList<TimeReportFilter>();
		Collection<IncidentResourceVo> incidentResourceVos = new ArrayList<IncidentResourceVo>();
		Collection<String> reportUrls = new ArrayList<String>();
		Collection<String> invoices = new ArrayList<String>();
		Collection<String> adjustments = new ArrayList<String>();

		Long irId=filter.getIncidentResourceId();
		try {
			IncidentResourceDao irdao = (IncidentResourceDao) context.getBean("incidentResourceDao");
			IncidentResource ir = irdao.getById(filter.getIncidentResourceId(), IncidentResourceImpl.class);
			if(CollectionUtility.hasValue(ir.getResource().getChildResources())){
				filter.setIsCrew(true);
				isCrew=true;
			}
			irdao.flushAndEvict(ir);
			
			if(isCrew==true){
				Collection<IncidentResourceVo> vos=new ArrayList<IncidentResourceVo>();
				Collection<Long> childrenIds = irdao.getIncidentResourceChildrenIds(filter.getIncidentResourceId());
				if(CollectionUtility.hasValue(childrenIds)){
					for(Long id : childrenIds){
						Collection<IncidentResourceVo> irvos = irdao.getNonInvoicedIncidentResourcesById(id, Calendar.getInstance().getTime());
						if(CollectionUtility.hasValue(irvos))
							incidentResourceVos.add(irvos.iterator().next());
					}
				}
			}
		}catch(Exception e){
			
		}
		
		if(isCrew==true){
			TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
			
			// get last invoice for all children
			for(IncidentResourceVo irvo : incidentResourceVos){
				Collection<TimeInvoiceVo> timeInvoices = tid.getForResource(irvo.getResourceVo().getId());
				if(CollectionUtility.hasValue(timeInvoices)){
					Long invoiceId = 0L;
					
					for(TimeInvoiceVo tivo : timeInvoices){
						invoiceId=tivo.getId();
					}
					
					if(LongUtility.hasValue(invoiceId)){
						TimeReportFilter f = new TimeReportFilter();
						f.setTimeInvoiceId(invoiceId);
						filters.add(f);
					}
				}
			}
				
		}else{
			filters.add(filter);
		}

		if(!CollectionUtility.hasValue(filters)){
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("NO_INVOICES_AVAIL");
			coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
			coa.setMessageVo(new MessageVo("text.invoices"
					, "info.generic"
					, new String[]{"There are no invoices to reprint."}
					, MessageTypeEnum.CRITICAL));
			
			coa.setIsDialogueEnding(true);
			dialogueVo.setCourseOfActionVo(coa);
			return dialogueVo;
		}
		
		for(TimeReportFilter f : filters){
			TimeInvoice invoice = prepareDuplicateInvoice(f);
			String orgInvReport = invoice.getInvoiceReport() != null ?
					invoice.getInvoiceReport().getFileName() : null;
			invoice.setInvoiceReport(null);
			
			String orgAdjReport = invoice.getAdjustmentReport() != null ? 
					invoice.getAdjustmentReport().getFileName() : null;
			invoice.setAdjustmentReport(null);

			String filePath = "";
			try {
				filePath = getOutputFile("");
			} catch (Exception e) {
				super.handleException(e);
			}
			
			String duplicateText = "Duplicate Original Invoice"; 
			String officialNumber = super.getTimeInvoiceNameCount(invoice.getInvoiceNumber());
			if(StringUtility.hasValue(filter.getReasonForReprint())){
				officialNumber=officialNumber+" " +filter.getReasonForReprint();
			}
			
			BaseFont bfHB = null;
			BaseFont bfHR = null;
			try {
				bfHB = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "Cp1252", false);
				bfHR = BaseFont.createFont(BaseFont.HELVETICA, "Cp1252", false);
			} catch (Exception e) {
				super.handleException(e);
			}
			
			if(orgInvReport != null){
				Report invRpt = createAndSaveReport();
				invoice.setInvoiceReport(invRpt);
				
				try {
					PdfReader pdfReader = new PdfReader(filePath + orgInvReport);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, 
							new FileOutputStream(filePath + invRpt.getFileName()));
					
					invoices.add(filePath+invRpt.getFileName());
					
					// alter the heading and official number
					for(int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
						PdfContentByte content = pdfStamper.getOverContent(i);
						content.setColorFill(new CMYKColor(0, 0, 0, 0));
						content.setColorStroke(new CMYKColor(0, 0, 0, 0));
						
						/*
						content.moveTo(214, 739);
						content.lineTo(300, 739);
						content.lineTo(300, 750);
						content.lineTo(214, 750);
						content.closePathFillStroke();
						
						content.moveTo(481, 736);
						content.lineTo(490, 736);
						content.lineTo(490, 746);
						content.lineTo(481, 746);
						content.closePathFillStroke();
						*/
						
						content.moveTo(20, 20);
						content.lineTo(30, 20);
						content.lineTo(30, 30);
						content.lineTo(20, 30);
						content.closePathFillStroke();
						
						content.setColorFill(new CMYKColor(255, 255, 255, 255));
						
						ColumnText ct = new ColumnText(content);
						ct.setSimpleColumn(184, 10, 374, 36); //? 72 = 1 inch ?
						ct.setText(new Phrase(duplicateText, new Font(bfHB)));
						ct.go();

						/* Dan - 12/21/2015 commenting out below 2 items
						ColumnText ct2 = new ColumnText(content);
						ct2.setSimpleColumn(482, 739, 600, 756); 
						ct2.setText(new Phrase(officialNumber, new Font(bfHR, 8.0f)));
						ct2.go();
						
						ColumnText ct3 = new ColumnText(content);
						ct3.setSimpleColumn(28, 31, 168, 56); 
						ct3.setText(new Phrase(duplicateText, new Font(bfHB, 10.0f)));
						ct3.go();
						*/
						
						/*
						ColumnText ct4 = new ColumnText(content);
						ct4.setSimpleColumn(210, 31, 335, 56); 
						ct4.setText(new Phrase(duplicateText, new Font(bfHB, 10.0f)));
						ct4.go();
						*/
						content.beginText();
					}
					pdfReader.close();
					pdfStamper.close();
				} catch (IOException e) {
					super.handleException(e);
				} catch (DocumentException de ) {
					super.handleException(de);
				}
			}

			boolean adjReportExists=false;
			try{
				adjReportExists=FileUtil.fileExists(filePath + orgAdjReport);
			}catch(Exception eee){}
			
			if(orgAdjReport != null && BooleanUtility.isTrue(adjReportExists)) {
				Report adjRpt = createAndSaveReport();
				invoice.setAdjustmentReport(adjRpt);
				
				try {
					PdfReader pdfReader = new PdfReader(filePath + orgAdjReport);
					PdfStamper pdfStamper = new PdfStamper(pdfReader, 
							new FileOutputStream(filePath + adjRpt.getFileName()));

					adjustments.add(filePath+adjRpt.getFileName());
					
					// alter the heading and official number
					for(int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
						PdfContentByte content = pdfStamper.getOverContent(i);
						content.setColorFill(new CMYKColor(0, 0, 0, 0));
						content.setColorStroke(new CMYKColor(0, 0, 0, 0));
						
						content.moveTo(240, 726);
						content.lineTo(387, 726);
						content.lineTo(387, 739);
						content.lineTo(240, 739);
						content.closePathFillStroke();
						
						content.moveTo(455, 727);
						content.lineTo(465, 727);
						content.lineTo(465, 737);
						content.lineTo(455, 737);
						content.closePathFillStroke();
						
						content.setColorFill(new CMYKColor(255, 255, 255, 255));
						
						ColumnText ct = new ColumnText(content);
						ct.setAlignment(Element.ALIGN_CENTER);
						ct.setSimpleColumn(227, 725, 396, 745);
						ct.setText(new Phrase(duplicateText, new Font(bfHB)));
						ct.go();
						
						ColumnText ct2 = new ColumnText(content);
						ct2.setSimpleColumn(450, 726, 580, 746);
						ct2.setText(new Phrase(officialNumber, new Font(bfHR, 8.0f)));
						ct2.go();
						
						content.beginText();
					}
					pdfReader.close();
					pdfStamper.close();
				} catch (IOException e) {
					super.handleException(e);
				} catch (DocumentException de ) {
					super.handleException(de);
				}
			}
			
			saveTimeInvoice(invoice);
			
			//Collection<String> reportUrls2=getReportList(invoice, dialogueVo, "Reprint_Invoice");
		}

		String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
		String singlePdf="rpt" + timestamp + ".pdf";
		String outputFileInvoice=null;
		Collection<String> rptFiles= new ArrayList<String>();
		
		try{
			outputFileInvoice = this.getOutputFile(singlePdf);
		}catch(Exception e){
			
		}
		if(CollectionUtility.hasValue(invoices))
			rptFiles.addAll(invoices);
		
		if(CollectionUtility.hasValue(adjustments))
			rptFiles.addAll(adjustments);

		try{
			PdfUtil.concatenatePdfs(rptFiles, outputFileInvoice);
			String concatUrl=this.getOutputUrl(singlePdf);
			reportUrls.add(concatUrl);
		}catch(Exception e){
			
		}
		
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("Generate_Invoice");
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportUrls);
		dialogueVo.setCourseOfActionVo(coa);
		
		return dialogueVo;
	}
	
	private String getLastPostingIACValue(Collection<AssignmentTimePostVo> vos ){
		
		AssignmentTimePostVo vo = null;
		
		if(CollectionUtility.hasValue(vos)){
			for(AssignmentTimePostVo v : vos){
				vo=v;
			}
			
			if(null!=vo){
				if(null != vo.getIncidentAccountCodeVo()
					&& null != vo.getIncidentAccountCodeVo().getAccountCodeVo()
					&& null != vo.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo()
					&& StringUtility.hasValue(vo.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode())){
					return vo.getIncidentAccountCodeVo().getAccountCodeVo().getRegionUnitVo().getCode()
							+ "/" + vo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode();
				}else{
					return vo.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode();
				}
			}else
				return "";
		}else{
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	static class ActivityDateComparator implements Comparator {
		
		public int compare(Object vo1, Object vo2){
			Date activityDate1=null;
			Date activityDate2=null;

			try{
				if(DateTransferVo.hasDateString(((TimeAssignAdjustVo)vo1).getActivityDateVo()))
					activityDate1=DateTransferVo.getTransferDate(((TimeAssignAdjustVo)vo1).getActivityDateVo());
				if(DateTransferVo.hasDateString(((TimeAssignAdjustVo)vo2).getActivityDateVo()))
					activityDate2=DateTransferVo.getTransferDate(((TimeAssignAdjustVo)vo2).getActivityDateVo());
			}catch(Exception e){}
			//Date activityDate1 = ((TimeAssignAdjustVo) vo1).getActivityDate();
			//Date activityDate2 = ((TimeAssignAdjustVo) vo2).getActivityDate();
			
			return activityDate1.compareTo(activityDate2);
		}
		
	}
	
}
