package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.AssignmentTimePost;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeAssignAdjust;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.impl.AssignmentTimePostImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.core.domain.impl.TimeAssignAdjustImpl;
import gov.nwcg.isuite.core.domain.impl.TimeInvoiceImpl;
import gov.nwcg.isuite.core.financial.posts.DailyTimePostImpl;
import gov.nwcg.isuite.core.persistence.IncidentDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.reports.EmergencyEquipmentDandAReport;
import gov.nwcg.isuite.core.reports.OF286TimeInvoiceReport;
import gov.nwcg.isuite.core.reports.data.EmergencyEquipmentDandAReportData;
import gov.nwcg.isuite.core.reports.data.EmergencyEquipmentDandASubReportData;
import gov.nwcg.isuite.core.reports.data.OF286EquipmentDetail;
import gov.nwcg.isuite.core.reports.data.OF286TimeInvoiceReportData;
import gov.nwcg.isuite.core.reports.data.OF286TimeInvoiceSubReportData;
import gov.nwcg.isuite.core.rules.OF286InvoiceGeneratorRulesHandler;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.core.vo.AssignmentTimeVo;
import gov.nwcg.isuite.core.vo.AssignmentVo;
import gov.nwcg.isuite.core.vo.ContractorVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.GenericVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceVo;
import gov.nwcg.isuite.core.vo.TimeAssignAdjustVo;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.financial.posts.DailyTimePost;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.PdfUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

public class OF286ReportGeneratorImpl extends ReportTimeInvoiceGeneratorImpl {
	private Date reportStartDate;
	private Date reportEndDate;
	private Collection<Long> processedTimePostIds=new ArrayList<Long>();
	private Collection<Long> processedAdjustmentIds=new ArrayList<Long>();
	
	@Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null) dialogueVo = new DialogueVo();

		Collection<Date> dates;
		Collection<IncidentResourceVo> incidentResourceVos = null;
		
		DailyTimePost dtp = (DailyTimePostImpl) context.getBean("dailyTimePost"); 
		//DailyTimeCalculator dtc;

		Collection<OF286TimeInvoiceReportData> timeInvoiceData;
		Collection<String> reportUrls = new ArrayList<String>();
		Collection<String> crewResourceReportFiles = new ArrayList<String>();
		Collection<String> crewResourceAdjReportFiles= new ArrayList<String>();
		Collection<String> crewResourceFiles = new ArrayList<String>();

		try {
			incidentResourceVos = getIncidentResources(filter);

			// get unique dates
			//dates = (new UniqueDatesUtil()).getUniqueTimePostDates(incidentResourceVos);

			// apply rules
			OF286InvoiceGeneratorRulesHandler rulesHandler = new OF286InvoiceGeneratorRulesHandler(context);
			if (rulesHandler.execute(incidentResourceVos, null, filter, dialogueVo) == OF286InvoiceGeneratorRulesHandler._OK) {
				
				processedTimePostIds = new ArrayList<Long>();
				processedAdjustmentIds = new ArrayList<Long>();

				TimePostDao tpDao2 = (TimePostDao)context.getBean("timePostDao");
				
				for(IncidentResourceVo irVo : incidentResourceVos){
					// fix stop times
					tpDao2.fixStopTimes(irVo.getId(),null);
					
					// get list of distinct accounting codes from postings
					TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
					Collection<AssignmentTimePost> atps = tpDao.getTimePostings(filter.getLastDateToIncludeOnReport(),irVo.getId(),0L);
					Collection<Long> iacs=new ArrayList<Long>();
					
					for(AssignmentTimePost atp : atps){
						Long iac = atp.getIncidentAccountCodeId();
						if(!LongUtility.containsLong(iacs, iac)){
							iacs.add(iac);
						}
						tpDao.flushAndEvict(atp);
					}

					IncidentDao incDao = (IncidentDao)context.getBean("incidentDao");
					
					for(Long iac : iacs){
						//processedTimePostIds = new ArrayList<Long>();
						//processedAdjustmentIds = new ArrayList<Long>();
						GenericVo genericVo=incDao.getDataByIncAcctCodeId(iac);
						
						// create Report data objects
						timeInvoiceData = this.generateReportData(irVo, filter, iac, genericVo);

						String reportFilename="";
						String reportAdjFilename="";

						TimeInvoice timeInvoice = generateInvoiceReports(timeInvoiceData, filter, irVo);

						if(null != timeInvoice.getInvoiceReport()){
							reportFilename=timeInvoice.getInvoiceReport().getFileName();
							if(StringUtility.hasValue(reportFilename)){
								crewResourceFiles.add(this.getOutputFile(reportFilename));
								crewResourceReportFiles.add(this.getOutputFile(reportFilename));
							}
						}

						if(null != timeInvoice.getAdjustmentReport()){
							reportAdjFilename=timeInvoice.getAdjustmentReport().getFileName();
							if(StringUtility.hasValue(reportAdjFilename)){
								crewResourceFiles.add(this.getOutputFile(reportAdjFilename));
								crewResourceAdjReportFiles.add(this.getOutputFile(reportAdjFilename));
							}
						}
						
						// mark invoiced
						if (filter.getPrintOriginalInvoice()) {

							// save invoiced amount
							dtp.saveInvoicedAmounts(irVo, filter);

							// mark invoiced begin
							if (timeInvoice.getAssignmentTimePosts() == null) 
								timeInvoice.setAssignmentTimePosts(new ArrayList<AssignmentTimePost>());
							if (timeInvoice.getTimeAssignmentAdjusts() == null) 
								timeInvoice.setTimeAssignmentAdjusts(new ArrayList<TimeAssignAdjust>());
							
							for(Long atpid : this.processedTimePostIds){
								AssignmentTimePost atp2 = new AssignmentTimePostImpl();
								atp2.setId(atpid);
								timeInvoice.getAssignmentTimePosts().add(atp2);
							}

							for(Long adjid : this.processedAdjustmentIds){
								TimeAssignAdjust adj2 = new TimeAssignAdjustImpl();
								adj2.setId(adjid);
								timeInvoice.getTimeAssignmentAdjusts().add(adj2);
							}
							
							TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
							
							tid.save(timeInvoice);
							tid.flushAndEvict(timeInvoice);
							//dtp.markInvoiced(filter.getLastDateToIncludeOnReport(),irVo, timeInvoice, filter.getPrintOriginalInvoice());

							// mark invoiced end
						}
						
					}


				}

				String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());
				String singlePdf="rpt" + timestamp + ".pdf";
				String outputFile = this.getOutputFile(singlePdf);

				if(CollectionUtility.hasValue(crewResourceFiles)){
					PdfUtil.concatenatePdfs(crewResourceFiles, outputFile);
				}
				String singleReportUrl=this.getOutputUrl(singlePdf);
				reportUrls.add(singleReportUrl);
				
			}else{
				return dialogueVo;
			}
			
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

	protected Collection<OF286TimeInvoiceReportData> generateReportData(IncidentResourceVo irVo, TimeReportFilter filter, Long iacId,GenericVo genVo) throws ServiceException {

		Collection<OF286TimeInvoiceReportData> timeInvoices = new ArrayList<OF286TimeInvoiceReportData>();
		OF286TimeInvoiceReportData ti;
		OF286EquipmentDetail ed;
		try {
			ti = new OF286TimeInvoiceReportData();
			ed = new OF286EquipmentDetail();

			ti.setDraftInvoice(filter.getPrintDraftInvoice());
			ti.setDuplicateOriginalInvoice(filter.getPrintDuplicateOriginalInvoice());
			ti.setFinalInvoice(filter.getFinalInvoice());
			ti.setReportPrintedDate(new Date());

			ti.setResourceId(irVo.getResourceVo().getId());

			for (AssignmentVo a : irVo.getWorkPeriodVo().getAssignmentVos()) {
				AssignmentTimeVo at = a.getAssignmentTimeVo();

				ti.setRemarks(at.getOfRemarks() != null ? at.getOfRemarks() : "");

				ContractorVo contractor = at.getContractorPaymentInfoVo()!=null ?
						at.getContractorPaymentInfoVo().getContractorVo()!=null ?
								at.getContractorPaymentInfoVo().getContractorVo() : null : null;
				if (contractor != null) {
					ti.setContractorName(contractor.getName());
					ti.setDuns(contractor.getDuns());
					if (contractor.getAddressVo() != null) {
						ti.setContractorCity(contractor.getAddressVo().getCity());
						ti.setContractorStreet((contractor.getAddressVo().getAddressLine1() != null ? 
								contractor.getAddressVo().getAddressLine1() : "")
								+ " "
								+ (contractor.getAddressVo().getAddressLine2() != null ? 
										contractor.getAddressVo().getAddressLine2() : ""));
						ti.setContractorState(contractor.getAddressVo().getCountrySubdivisionVo() != null ? 
								contractor.getAddressVo().getCountrySubdivisionVo().getCountrySubAbbreviation(): "");
						ti.setContractorZip(contractor.getAddressVo().getPostalCode());
					}

					//if(StringUtility.hasValue(contractor.getActualTin())){
					//	ti.setContractorTINOrSSN(contractor.getActualTin());

					//}else
					ti.setContractorTINOrSSN("");
				}

				ti.setPointOfHire("");
				if(null != at.getContractorPaymentInfoVo()){
					if(StringUtility.hasValue(at.getContractorPaymentInfoVo().getPointOfHire())){
						ti.setPointOfHire(at.getContractorPaymentInfoVo().getPointOfHire());
					}
				}

				
				String resName="";
				if(BooleanUtility.isTrue(irVo.getResourceVo().getPerson())){
					resName=irVo.getResourceVo().getFirstName() + " " + irVo.getResourceVo().getLastName();
				}else{
					resName=irVo.getResourceVo().getResourceName();
				}
				//String x = irVo.getResourceVo().getResourceName() != null ?
				//		irVo.getResourceVo().getResourceName() :
				//			irVo.getResourceVo().getFirstName() + " " + irVo.getResourceVo().getLastName();
				String x = resName;
						ti.setResourceItemName(x + "-" + (a.getKindVo() != null ? a.getKindVo().getDescription() : ""));
						if (at.getContractorPaymentInfoVo() != null) {
							ti.setResourceUniqueID(at.getContractorPaymentInfoVo().getVinName());
							ti.setMake(at.getContractorPaymentInfoVo().getDesc1());
							ti.setModel(at.getContractorPaymentInfoVo().getDesc2());
						}
						ti.setRequestNumber(a.getRequestNumber());

						AdminOfficeVo ao = (at.getContractorPaymentInfoVo() != null ? 
								at.getContractorPaymentInfoVo().getContractorAgreementVo() != null ?
										at.getContractorPaymentInfoVo().getContractorAgreementVo().getAdminOfficeVo() : null : null);			  
						if (ao != null) {
							ti.setAdminOfficeForPaymentName(ao.getOfficeName());
							if (ao.getAddressVo() != null) {
								ti.setAdminOfficeForPaymentStreet((ao.getAddressVo().getAddressLine1() != null ? 
										ao.getAddressVo().getAddressLine1() : "")
										+ " "
										+ (ao.getAddressVo().getAddressLine2() != null ? 
												ao.getAddressVo().getAddressLine2() : ""));
								ti.setAdminOfficeForPaymentCity(ao.getAddressVo().getCity());
								ti.setAdminOfficeForPaymentState(ao.getAddressVo().getCountrySubdivisionVo()
										.getCountrySubAbbreviation());
								ti.setAdminOfficeForPaymentZip(ao.getAddressVo().getPostalCode());
							}
						}

						String resnumid="0000";
						if(LongUtility.hasValue(irVo.getResNumId()))
							resnumid=String.valueOf(irVo.getResNumId());
						String invId=StringUtility.leftPad(resnumid, "0", 4);
						
						String invNumber="F-"+
						irVo.getIncidentVo().getIncidentYear() + "-" +
						irVo.getIncidentVo().getHomeUnitVo().getUnitCode()+"-" +
						irVo.getIncidentVo().getIncidentNumber() + "-" +
						invId;
						//irVo.getResourceVo().getId() + "-";
						ti.setIdentificationNumber(invNumber);
						//ti.setIdentificationNumber("F-" + ir.getIncidentVo().getIncidentYear()
						//		+ "-" + ir.getIncidentVo().getIncidentNumber() + "-" + ir.getResourceVo().getId()
						//		+ "-");
						ti.setIdentificationNumber(super.generateInvoiceIdNumber(ti));

						ti.setOfficialNumber(super.getTimeInvoiceNameCount(ti.getIdentificationNumber()));

						// get like named previous invoices
						String invoiceId = ti.getIdentificationNumber();
						int index = invoiceId.lastIndexOf('-');
						invoiceId = invoiceId.substring(0, index + 1);
						//defect 4899 - turn this off
						//ti.setPreviousInvoices(super.getPreviousTimeInvoicesNames(invoiceId));
						ti.setPreviousInvoices(new ArrayList<String>());
						
						String incidentTag = "";
						incidentTag = irVo.getIncidentVo().getHomeUnitVo().getUnitCode() 
						+ "-" + irVo.getIncidentVo().getIncidentNumber();

						TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");

						Collection<AssignmentTimePost> atps = tpDao.getTimePostings(filter.getLastDateToIncludeOnReport(),irVo.getId(),iacId);
						
						Date firstDate=null;
						Date initialDate=null;
						Date lastDate=filter.getLastDateToIncludeOnReport();
						lastDate=DateUtil.addMilitaryTimeToDate(lastDate, "2359");

						int cnt=0;
						for(AssignmentTimePost atp : atps){
							if(cnt==0){
								firstDate=atp.getPostStartDate();
								initialDate=atp.getPostStartDate();
							}
							firstDate=DateUtil.addMilitaryTimeToDate(firstDate, "2359");
							//tpDao.flushAndEvict(atp);
							cnt++;
						}

						this.reportStartDate=initialDate;
						this.reportEndDate=lastDate;
						ti.setStartDate(initialDate);
						ti.setEndDate(lastDate);


						OF286LineItemBuilder lineItemBuilder = new OF286LineItemBuilder(context);

						while(!firstDate.after(lastDate)){
							Collection<OF286TimeInvoiceSubReportData> subReportData =
								lineItemBuilder.buildSubReportDataForDate(firstDate,irVo,atps);

							if(CollectionUtility.hasValue(subReportData)){
								int i=0;

								subReportData = reSortThisDayData(subReportData);
								
								for(OF286TimeInvoiceSubReportData data : subReportData){

									OF286TimeInvoiceSubReportData tmpdata = (OF286TimeInvoiceSubReportData)data.clone();

									//tmpdata.setIncidentNumber(incidentTag);
									tmpdata.setIncidentNumber(genVo.getStr1()+"-"+genVo.getStr2());
									
									if(i>0){
										if(previousLineItemSame(i,(i-1),subReportData)==true){
											// determine the subtotal amount
											Double subTotal = data.getTotalAmount();
											subTotal=determineSubTotalAmount(i,(i-1),subReportData,subTotal);
											tmpdata.setTotalAmount(subTotal);
											tmpdata.setFinalAmount(subTotal);

											if(null != tmpdata.getGuaranteeAmount()){
												if(tmpdata.getGuaranteeAmount() > subTotal)
													tmpdata.setFinalAmount(tmpdata.getGuaranteeAmount());
											}
										}
									}

									// check for setting next line and totals
									if(nextLineItemSame(i,(i+1),subReportData)==true){
										tmpdata.setFinalAmount(0.0);
										tmpdata.setGuaranteeAmount(0.0);
										//tmpdata.setTotalAmount(0.0);
										tmpdata.setNoLineTotal("See Next Line");
									}else{
										if(null != data.getGuaranteeAmount()){
											if(data.getGuaranteeAmount() > tmpdata.getTotalAmount()){
												tmpdata.setNoLineTotal("");
												tmpdata.setGuaranteeAmount(data.getGuaranteeAmount());
												tmpdata.setFinalAmount(data.getGuaranteeAmount());
											}
										}
										
									}


									ti.getOF286InvoiceDetails().add(tmpdata);							
									i++;
								}
							}

							firstDate=DateUtil.addDaysToDate(firstDate, 1);
						}

						timeInvoices.add(ti);

						for(AssignmentTimePost atp : atps){
							processedTimePostIds.add(atp.getId());
							tpDao.flushAndEvict(atp);
						}

						/* Add adjustment Details */
						EmergencyEquipmentDandAReportData adj = new EmergencyEquipmentDandAReportData();
						adj.setEquipmentDetail(ti.getEquipmentDetails());
						adj.setDraftInvoice(filter.getPrintDraftInvoice());
						adj.setIdentificationNumber(ti.getIdentificationNumber());
						adj.setOfficialNumber(ti.getOfficialNumber());
						//adj.setPostStartDate(ti.getPostStartDate());
						//adj.setPostStopDate(ti.getPostStopDate());
						adj.setPostStartDate(initialDate);
						adj.setPostStopDate(lastDate);
						adj.setReportPrintedDate(ti.getReportPrintedDate());

						for (TimeAssignAdjustVo taa : a.getTimeAssignAdjustVos()) {
							Boolean bcontinue=true;
							
							if(null != taa.getTimeInvoiceVos() && CollectionUtility.hasValue(taa.getTimeInvoiceVos())){
								for(TimeInvoiceVo tiv : taa.getTimeInvoiceVos()){
									if(BooleanUtility.isFalse(tiv.getIsDraft())
											&& !DateUtil.hasValue(tiv.getDeletedDate())
											&& BooleanUtility.isTrue(tiv.getIsInvoiceAdjust())){
										bcontinue=false;
									}
								}
							}

							if(DateUtil.hasValue(taa.getDeletedDate())){
								bcontinue=false;
							}

							if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
								Date actDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
								if(actDate.after(lastDate)){
									bcontinue=false;
								}
							}
							
							if(bcontinue==true){
								if(taa.getIncidentAccountCodeVo().getId().compareTo(iacId)==0){
									processedAdjustmentIds.add(taa.getId());
									
									EmergencyEquipmentDandASubReportData sub = new EmergencyEquipmentDandASubReportData();
									sub.setAccountingCode(taa.getIncidentAccountCodeVo() != null ? 
											taa.getIncidentAccountCodeVo().getAccountCodeVo() != null ? 
													taa.getIncidentAccountCodeVo().getAccountCodeVo().getAccountCode() : "" : "");
									
									if(DateTransferVo.hasDateString(taa.getActivityDateVo())){
										Date chkDate=DateTransferVo.getTransferDate(taa.getActivityDateVo());
										sub.setActivityDate(chkDate);
									}
									//sub.setActivityDate(taa.getActivityDate());
									sub.setAdjustmentType(taa.getAdjustmentType().toString());
									sub.setAmount(taa.getAdjustmentAmount() != null ? taa.getAdjustmentAmount().doubleValue(): 0.0);
									sub.setAgreementNumber(at.getContractorPaymentInfoVo() != null ? 
											at.getContractorPaymentInfoVo().getContractorAgreementVo() != null ? 
													at.getContractorPaymentInfoVo().getContractorAgreementVo().getAgreementNumber(): "" : "");
									sub.setDescription(taa.getCommodity());
									sub.setIncidentName(irVo.getIncidentVo().getIncidentName());

									adj.getReportData().add(sub);
								}
								
							}
						}
						
						if (adj.getReportData().size() > 0) {
							Collections.sort((List)adj.getReportData(), new Comparator<EmergencyEquipmentDandASubReportData>(){public int compare(EmergencyEquipmentDandASubReportData ob1, EmergencyEquipmentDandASubReportData ob2){return ob1.getActivityDate().compareTo(ob2.getActivityDate());}});			
							
							ti.setAdjustmentData(adj);
						}
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return timeInvoices;
	}

	private Boolean nextLineItemSame(int sourceIdx, int targetIdx,Collection<OF286TimeInvoiceSubReportData> subReportData) {
		Boolean rtn=false;

		int x=0;
		OF286TimeInvoiceSubReportData sourceData=null;

		for(OF286TimeInvoiceSubReportData d : subReportData){
			if(x==sourceIdx){
				sourceData=d;
			}
			if(x==targetIdx){
				if(sourceData.getDevPostingType().equals(d.getDevPostingType())){
					if(DateUtil.isSameDate(sourceData.getDevDate(), d.getDevDate())){
						if(StringUtility.hasValue(sourceData.getDevContractorRateId()) && StringUtility.hasValue(d.getDevContractorRateId())){
							if(sourceData.getDevContractorRateId().equals(d.getDevContractorRateId())){
								if(StringUtility.hasValue(sourceData.getDevUom()) && StringUtility.hasValue(d.getDevUom())){
									if(sourceData.getDevUom().equals(d.getDevUom())){
										return true;
									}
								}
							}else{
								// is same posting uom same?
								if(StringUtility.hasValue(sourceData.getDevUom()) && StringUtility.hasValue(d.getDevUom())){
									if(sourceData.getDevUom().equals(d.getDevUom()))
										return true;
								}
							}
						}
					}
				}
				/*
				if(DateUtil.isSameDate(sourceData.getDevDate(), d.getDevDate())){
					if(sourceData.getDevContractorRateId().equals(d.getDevContractorRateId())
							&& sourceData.getDevUom().equals(d.getDevUom())){
						return true;
					}else{
						// is same posting uom same?
						if(sourceData.getDevUom().equals(d.getDevUom()))
							return true;
					}
				}
				*/
			}

			x++;
		}

		return false;
	}

	private Boolean previousLineItemSame(int sourceIdx, int targetIdx,Collection<OF286TimeInvoiceSubReportData> subReportData) {
		Boolean rtn=false;

		int x=0;
		OF286TimeInvoiceSubReportData targetData=null;

		for(OF286TimeInvoiceSubReportData d : subReportData){
			if(x==targetIdx){
				targetData=d;
			}
			if(x==sourceIdx){
				if(targetData.getDevPostingType().equals(d.getDevPostingType())){
					if(DateUtil.isSameDate(targetData.getDevDate(), d.getDevDate())){
						if(StringUtility.hasValue(targetData.getDevContractorRateId()) && StringUtility.hasValue(d.getDevContractorRateId())){
							if(targetData.getDevContractorRateId().equals(d.getDevContractorRateId())){
								if(StringUtility.hasValue(targetData.getDevUom()) && StringUtility.hasValue(d.getDevUom())){
									if(targetData.getDevUom().equals(d.getDevUom()))
										return true;
								}
							}else{
								if(StringUtility.hasValue(targetData.getDevUom()) && StringUtility.hasValue(d.getDevUom())){
									if(targetData.getDevUom().equals(d.getDevUom()))
										return true;
								}
							}
						}
					}
				}
				/*
				if(DateUtil.isSameDate(targetData.getDevDate(), d.getDevDate())){
					if(targetData.getDevContractorRateId().equals(d.getDevContractorRateId())
							&& targetData.getDevUom().equals(d.getDevUom())){
						return true;
					}else{
						// is same posting uom same?
						if(targetData.getDevUom().equals(d.getDevUom()))
							return true;
					}
				}
				*/
			}

			x++;
		}

		return false;
	}

	private Collection<OF286TimeInvoiceSubReportData> reSortThisDayData(Collection<OF286TimeInvoiceSubReportData> data) {
		Collection<OF286TimeInvoiceSubReportData> rtnData= new ArrayList<OF286TimeInvoiceSubReportData>();
		HashMap<Long,OF286TimeInvoiceSubReportData> map = new HashMap<Long,OF286TimeInvoiceSubReportData>();
		
		// sort through list and put primary's first, then the rest
		for(OF286TimeInvoiceSubReportData d : data){
			if(d.getDevPostingType().equals("PRIMARY") )
				rtnData.add(d);
		}
		for(OF286TimeInvoiceSubReportData d : data){
			if(d.getDevPostingType().equals("GUARANTEE"))
				rtnData.add(d);
		}
		for(OF286TimeInvoiceSubReportData d : data){
			if(d.getDevPostingType().equals("SPECIAL"))
				rtnData.add(d);
		}
		
		return rtnData;
	}
	
	private Double determineSubTotalAmount(int sourceIdx, int targetIdx,Collection<OF286TimeInvoiceSubReportData> subReportData, Double subTotal) {

		int x=0;
		OF286TimeInvoiceSubReportData targetData=null;

		for(OF286TimeInvoiceSubReportData d : subReportData){
			if(x==targetIdx){
				targetData=d;
			}
			if(x==sourceIdx){
				if(DateUtil.isSameDate(targetData.getDevDate(), d.getDevDate())){
					if(targetData.getDevContractorRateId().equals(d.getDevContractorRateId())
							|| 	(StringUtility.hasValue(targetData.getDevUom()) && StringUtility.hasValue(d.getDevUom()))
							){
						subTotal=subTotal + targetData.getTotalAmount();

						if( (targetIdx-1) >= 0){
							if(previousLineItemSame(sourceIdx,(targetIdx-1),subReportData)==true){
								// determine the subtotal amount
								subTotal=determineSubTotalAmount(sourceIdx,(targetIdx-1),subReportData,subTotal);
							}
						}
					}
				}
			}

			x++;
		}

		return subTotal;

	}

	protected TimeInvoice generateInvoiceReports(Collection<OF286TimeInvoiceReportData> timeInvoices, TimeReportFilter filter, IncidentResourceVo irVo) throws ServiceException, PersistenceException {

		Collection<EmergencyEquipmentDandAReportData> adjustmentReports = new ArrayList<EmergencyEquipmentDandAReportData>();

		TimeInvoice invoice = new TimeInvoiceImpl();
		invoice.setIsFinal(filter.getFinalInvoice());
		invoice.setIsExported(StringBooleanEnum.N);
		invoice.setIsDraft(filter.getPrintDraftInvoice());
		invoice.setIsDuplicate(filter.getPrintDuplicateOriginalInvoice());
		invoice.setIsInvoiceAdjust(filter.getPrintDeductionsAndInvoice());
		invoice.setIsInvoiceOnly(filter.getPrintInvoiceOnly());
		invoice.setIsAdjustOnly(filter.getPrintItemizedDeductionsOnly());
		//invoice.setFirstIncludeDate(timeInvoices.iterator().next().getPostStartDate());
		//invoice.setLastIncludeDate(timeInvoices.iterator().next().getPostStopDate());
		invoice.setFirstIncludeDate(this.reportStartDate);
		invoice.setLastIncludeDate(this.reportEndDate);
		if(CollectionUtility.hasValue(timeInvoices))
			invoice.setInvoiceNumber(timeInvoices.iterator().next().getIdentificationNumber());
		else
			invoice.setInvoiceNumber("");

		Collection<Resource> resources = new ArrayList<Resource>();
		try {
			resources.add(ResourceVo.toEntity(new ResourceImpl(), irVo.getResourceVo(), true));
		} catch (Exception e) {
			super.handleException(e);
		}

		invoice.setResources(resources);

		// generate Reports
		if (filter.getPrintInvoiceOnly() || filter.getPrintDeductionsAndInvoice()) {
			try {
				IReport report = new OF286TimeInvoiceReport(timeInvoices);
				report.addCustomField("SUBREPORT_DIR", getSourcePath());

				this.generateTimeInvoice(report, invoice);
			} catch (Exception e) {
				super.handleException(e);
			}
		}

		if (filter.getPrintDeductionsAndInvoice() || filter.getPrintItemizedDeductionsOnly()) {
			for (OF286TimeInvoiceReportData trd : timeInvoices) {
				if (trd.getAdjustmentReports() != null)
					adjustmentReports.add(trd.getAdjustmentReports());
			}
			if (adjustmentReports != null && adjustmentReports.size() > 0) {
				try {
					IReport report = new EmergencyEquipmentDandAReport(adjustmentReports);
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

	@Override
	public DialogueVo reprintTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException,PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		TimeInvoice invoice = prepareDuplicateInvoice(filter);
		String orgInvReport = invoice.getInvoiceReport() != null ? invoice.getInvoiceReport().getFileName() : null;
		invoice.setInvoiceReport(null);

		Collection<String> invoices = new ArrayList<String>();
		Collection<String> adjustments = new ArrayList<String>();

		String orgAdjReport = invoice.getAdjustmentReport() != null ? 
				invoice.getAdjustmentReport().getFileName(): null;
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

				if (orgInvReport != null) {
					Report invRpt = createAndSaveReport();
					invoice.setInvoiceReport(invRpt);

					try {
						PdfReader pdfReader = new PdfReader(filePath + orgInvReport);
						PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath + invRpt.getFileName()));

						invoices.add(filePath+invRpt.getFileName());
						
						// alter the heading and official number
						for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
							PdfContentByte content = pdfStamper.getOverContent(i);
							content.setColorFill(new CMYKColor(0, 0, 0, 0));
							content.setColorStroke(new CMYKColor(0, 0, 0, 0));

							content.moveTo(240, 753);
							content.lineTo(388, 753);
							content.lineTo(388, 766);
							content.lineTo(240, 766);
							content.closePathFillStroke();

							content.moveTo(385, 718);
							content.lineTo(405, 718);
							content.lineTo(405, 729);
							content.lineTo(385, 729);
							content.closePathFillStroke();

							content.setColorFill(new CMYKColor(255, 255, 255, 255));

							ColumnText ct = new ColumnText(content);
							ct.setAlignment(Element.ALIGN_CENTER);
							ct.setSimpleColumn(240, 737, 388, 773); // ? 72 = 1 inch ?
							ct.setText(new Phrase(duplicateText, new Font(bfHB)));
							ct.go();

							ColumnText ct2 = new ColumnText(content);
							ct2.setSimpleColumn(387, 714, 500, 734);
							ct2.setText(new Phrase(officialNumber, new Font(bfHR, 8.0f)));
							ct2.go();

							ColumnText ct3 = new ColumnText(content);
							ct3.setAlignment(Element.ALIGN_CENTER);
							ct3.setSimpleColumn(25, 65, 165, 85);
							ct3.setText(new Phrase(duplicateText, new Font(bfHB, 10.0f)));
							ct3.go();

							ColumnText ct4 = new ColumnText(content);
							ct4.setAlignment(Element.ALIGN_CENTER);
							ct4.setSimpleColumn(315, 65, 455, 85);
							ct4.setText(new Phrase(duplicateText, new Font(bfHB, 10.0f)));
							ct4.go();

							content.beginText();
						}
						pdfReader.close();
						pdfStamper.close();
					} catch (IOException e) {
						super.handleException(e);
					} catch (DocumentException de) {
						super.handleException(de);
					}
				}

				boolean adjReportExists=false;
				try{
					adjReportExists=FileUtil.fileExists(filePath + orgAdjReport);
				}catch(Exception eee){}
				
				if (orgAdjReport != null  && BooleanUtility.isTrue(adjReportExists)) {
					Report adjRpt = createAndSaveReport();
					invoice.setAdjustmentReport(adjRpt);

					try {
						PdfReader pdfReader = new PdfReader(filePath + orgAdjReport);
						PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath + adjRpt.getFileName()));

						adjustments.add(filePath+adjRpt.getFileName());
						
						// alter the heading and official number
						for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
							PdfContentByte content = pdfStamper.getOverContent(i);
							content.setColorFill(new CMYKColor(0, 0, 0, 0));
							content.setColorStroke(new CMYKColor(0, 0, 0, 0));

							content.moveTo(240, 754);
							content.lineTo(388, 754);
							content.lineTo(388, 769);
							content.lineTo(240, 769);
							content.closePathFillStroke();

							content.moveTo(413, 715);
							content.lineTo(428, 715);
							content.lineTo(428, 728);
							content.lineTo(413, 728);
							content.closePathFillStroke();

							content.setColorFill(new CMYKColor(255, 255, 255, 255));

							ColumnText ct = new ColumnText(content);
							ct.setAlignment(Element.ALIGN_CENTER);
							ct.setSimpleColumn(231, 738, 398, 775);
							ct.setText(new Phrase(duplicateText, new Font(bfHB)));
							ct.go();

							ColumnText ct2 = new ColumnText(content);
							ct2.setSimpleColumn(413, 714, 480, 734);
							ct2.setText(new Phrase(officialNumber, new Font(bfHR, 10.0f)));
							ct2.go();

							content.beginText();
						}
						pdfReader.close();
						pdfStamper.close();
					} catch (IOException e) {
						super.handleException(e);
					} catch (DocumentException de) {
						super.handleException(de);
					}
				}

				saveTimeInvoice(invoice);

				//Collection<String> reportUrls=getReportList(invoice, dialogueVo, "Reprint_Invoice");
				Collection<String> reportUrls=new ArrayList<String>();
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
				coa.setCoaName("Reprint_Invoice");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coa.setIsDialogueEnding(true);
				dialogueVo.setResultObject(reportUrls);
				dialogueVo.setCourseOfActionVo(coa);
				
				return dialogueVo;
	}
}
