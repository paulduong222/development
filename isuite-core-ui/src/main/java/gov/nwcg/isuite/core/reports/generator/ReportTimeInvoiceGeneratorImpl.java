package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Report;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.TimeInvoice;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.TimeInvoiceImpl;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.rules.TimeInvoiceDeleteRulesHandler;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.data.TimeInvoiceData;
import gov.nwcg.isuite.framework.report.generator.ReportTimeInvoiceGenerator;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public abstract class ReportTimeInvoiceGeneratorImpl extends ReportGeneratorImpl implements ReportTimeInvoiceGenerator {

	abstract public DialogueVo reprintTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo)
			throws ServiceException, PersistenceException;

	protected void generateTimeInvoice(IReport report, TimeInvoice invoice) throws ServiceException {
		try {
			Report rpt = prepareReport();
			//rpt.setReportName(report.getReportName());
			invoice.setInvoiceReport(rpt);
			createAndSaveReport(rpt, report);
		} catch (Exception e) {
			ServiceException se = super.getServiceException(e);
			throw se;
		}
	}

	protected void generateAdjustmentInvoice(IReport report, TimeInvoice invoice) throws ServiceException {
		try {
			Report rpt = prepareReport();
			//rpt.setReportName(report.getReportName());
			invoice.setAdjustmentReport(rpt);
			createAndSaveReport(rpt, report);
		} catch (Exception e) {
			super.handleException(e);
		}
	}

	/**
	 * convert last part of IdNumber to a letter equal to count of previous invoices,
	 * 
	 * @param TimeInvoiceData
	 * @throws PersistenceException
	 */
	protected String generateInvoiceIdNumber(TimeInvoiceData ti) throws PersistenceException {
		String invoiceId = ti.getIdentificationNumber();

		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		int count = reportTimeDao.getTimeInvoiceLikeNameCount(invoiceId);

		String sufix = "";
		int wrapCounter = 0;
		while (count + 1 > 26) {
			wrapCounter++;
			count = count - 26;
		}
		if (wrapCounter > 0)
			sufix = sufix + (char) (wrapCounter + 64);
		sufix = sufix + (char) (count + 65);
		invoiceId = invoiceId + sufix;

		return invoiceId;
	}

	protected void saveTimeInvoice(TimeInvoice invoice) throws PersistenceException {
		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");

		tid.save(invoice);
	}

	protected Collection<String> getReportList(TimeInvoice ti, DialogueVo dialogueVo, String coaName) throws ServiceException {
		Collection<String> reportList = new ArrayList<String>();

		if (ti.getInvoiceReport() != null) {
			try {
				reportList.add(getOutputUrl(ti.getInvoiceReport().getFileName()));
			} catch (Exception e) {
				super.handleException(e);
			}
		}
		if (ti.getAdjustmentReport() != null) {
			try {
				reportList.add(getOutputUrl(ti.getAdjustmentReport().getFileName()));
			} catch (Exception e) {
				super.handleException(e);
			}
		}

		return reportList;
		// add reportsList to dialogueVo
		/*
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName(coaName);
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		coa.setIsDialogueEnding(true);
		dialogueVo.setResultObject(reportList);
		dialogueVo.setCourseOfActionVo(coa);
		*/
	}

	protected Collection<IncidentResourceVo> getIncidentResources(TimeReportFilter filter) throws ServiceException {
		// get Time Posts
		Collection<IncidentResourceVo> incidentResources = null;

		try {
			IncidentResourceDao ird = (IncidentResourceDao) context.getBean("incidentResourceDao");
			
			incidentResources = ird.getNonInvoicedIncidentResourcesById(filter.getIncidentResourceId(), filter.getLastDateToIncludeOnReport());
		
			//incidentResources = dtp.getTimePosts(filter.getIncidentResourceId(), 
			//		filter.getLastDateToIncludeOnReport());

		} catch (ServiceException e) {
			super.handleException(e);
		} catch(Exception ee){
			super.handleException(ee);
		}

		return incidentResources;
	}

	protected String getTimeInvoiceNameCount(String invoiceNumber) throws ServiceException {
		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		String count = "";
		try {
			count = reportTimeDao.getTimeInvoiceNameCount(invoiceNumber);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		return count;
	}

	protected Collection<String> getPreviousTimeInvoicesNames(String invoiceId) throws ServiceException {
		ReportTimeDao reportTimeDao = (ReportTimeDao) context.getBean("reportTimeDao");
		Collection<String> names = null;
		try {
			names = reportTimeDao.getPreviousTimeInvoicesNames(invoiceId);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		return names;
	}
	
	public DialogueVo deleteLastTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		if(BooleanUtility.isTrue(filter.getIsCrew())){
			try{
				return this.deleteLastTimeInvoiceCrew(filter, dialogueVo);
			}catch(Exception e){
				super.dialogueException(dialogueVo, e);
				return dialogueVo;
			}
		}
		
		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
		TimeInvoice invoice = null;
		try {
			invoice = tid.getById(filter.getTimeInvoiceId(), TimeInvoiceImpl.class);
			
			TimeInvoiceDeleteRulesHandler deleteRulesHandler = new TimeInvoiceDeleteRulesHandler(context);
			if(deleteRulesHandler.execute(invoice, dialogueVo)==TimeInvoiceDeleteRulesHandler._OK) {
				invoice.setDeletedDate(new Date());
				if (filter.getReasonForDelete() != null && filter.getReasonForDelete().length() > 300) {
					// shorten the reason to the max size allowed in the db
					String reason = filter.getReasonForDelete().substring(0, 299);
					filter.setReasonForDelete(reason);
				}
				invoice.setDeletedReason(filter.getReasonForDelete());

				tid.save(invoice);
				
				MessageVo mvo = new MessageVo();
				mvo.setTitleProperty("text.timeReports");
				mvo.setMessageProperty("text.timeInvoiceDeleted");

				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("Delete_Last_Invoice");
				coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
				coa.setIsDialogueEnding(true);
				coa.setMessageVo(mvo);

				dialogueVo.setCourseOfActionVo(coa);
			}
			
		} catch (Exception e) {
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}

	private DialogueVo deleteLastTimeInvoiceCrew(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException,Exception {
		if (dialogueVo == null) dialogueVo = new DialogueVo();

		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
		IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
		
		Long incidentResourceId = filter.getIncidentResourceId();
		IncidentResource parent = irDao.getById(incidentResourceId, IncidentResourceImpl.class);
		if(null != parent){
			if(CollectionUtility.hasValue(parent.getResource().getChildResources())){

				// check if crew has AD subordinates
				Collection<Long> adResourceIds = tid.getADSubordinateResourceIds(parent.getResourceId());
				
				for(Resource r : parent.getResource().getChildResources()){
					Collection<TimeInvoiceVo> invoices = tid.getForResource(r.getId());
					if(CollectionUtility.hasValue(invoices)){
						Long invoiceId = 0L;
						
						for(TimeInvoiceVo tivo : invoices){
							invoiceId=tivo.getId();
						}

						// check if resource is AD
						boolean isAdResource=false;
						for(Long adResourceId : adResourceIds){
							if(r.getId().compareTo(adResourceId)==0){
								isAdResource=true;
							}
						}
						
						if(LongUtility.hasValue(invoiceId)){
							TimeInvoice invoice = null;
							try {
								invoice = tid.getById(invoiceId, TimeInvoiceImpl.class);
							} catch (PersistenceException e) {
								super.handleException(e);
							}
							
							// if ad resource and invoice exported, do not delete
							boolean doNotDeleteAdInvoice=false;
							if(DateUtil.hasValue(invoice.getExportDate()) 
									&& isAdResource==true){
								doNotDeleteAdInvoice=true;
							}
							
							if(doNotDeleteAdInvoice==false){
								invoice.setDeletedDate(new Date());
								if (filter.getReasonForDelete() != null && filter.getReasonForDelete().length() > 300) {
									// shorten the reason to the max size allowed in the db
									String reason = filter.getReasonForDelete().substring(0, 299);
									filter.setReasonForDelete(reason);
								}
								invoice.setDeletedReason(filter.getReasonForDelete());

								try {
									tid.save(invoice);
								} catch (PersistenceException e) {
									super.handleException(e);
								}
							}
							
						}
					}
					
				}
			}
		}

		MessageVo mvo = new MessageVo();
		mvo.setTitleProperty("text.timeReports");
		mvo.setMessageProperty("text.timeInvoiceDeleted");

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("Delete_Last_Invoice");
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coa.setIsDialogueEnding(true);
		coa.setMessageVo(mvo);

		dialogueVo.setCourseOfActionVo(coa);

		return dialogueVo;
	}

	protected TimeInvoice prepareDuplicateInvoice(TimeReportFilter filter) throws ServiceException {
		TimeInvoiceDao tid = (TimeInvoiceDao) context.getBean("timeInvoiceDao");
		TimeInvoice org = null;
		try {
			org = tid.getById(filter.getTimeInvoiceId(), TimeInvoiceImpl.class);
		} catch (PersistenceException e) {
			super.handleException(e);
		}
		
		TimeInvoice invoice = new TimeInvoiceImpl();
		invoice.setIsDuplicate(true);
		
		invoice.setInvoiceNumber(org.getInvoiceNumber());
		invoice.setFirstIncludeDate(org.getFirstIncludeDate());
		invoice.setLastIncludeDate(org.getLastIncludeDate());
		invoice.setIsExported(StringBooleanEnum.N);
		invoice.setIsDraft(org.getIsDraft());
		invoice.setIsFinal(org.getIsFinal());
		
		invoice.setIsInvoiceAdjust(org.getIsAdjustOnly());
		invoice.setIsInvoiceOnly(org.getIsInvoiceOnly());
		invoice.setIsAdjustOnly(org.getIsAdjustOnly());
		
		invoice.setAdjustmentReport(org.getAdjustmentReport());
		invoice.setInvoiceReport(org.getInvoiceReport());
		
		return invoice;
	}

	
}
