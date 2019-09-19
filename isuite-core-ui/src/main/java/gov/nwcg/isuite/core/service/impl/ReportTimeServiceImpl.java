package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.persistence.IncidentGroupDao;
import gov.nwcg.isuite.core.persistence.IncidentResourceDao;
import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.persistence.TimeInvoiceDao;
import gov.nwcg.isuite.core.persistence.TimePostDao;
import gov.nwcg.isuite.core.persistence.WorkPeriodDao;
import gov.nwcg.isuite.core.reports.MissingDaysOfPostingsReport;
import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsReportData;
import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsSubReportData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportAgencyFaxResourceData;
import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.OF286TimeInvoiceReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.rules.IsLockedRulesHandler;
import gov.nwcg.isuite.core.service.ReportTimeService;
import gov.nwcg.isuite.core.vo.CheckboxTreeDataVo;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.PersonnelTimeRepCTDVo;
import gov.nwcg.isuite.core.vo.ReportAgencySelect;
import gov.nwcg.isuite.core.vo.ReportAgencySelectVo;
import gov.nwcg.isuite.core.vo.ReportSelectVo;
import gov.nwcg.isuite.core.vo.TimeInvoiceVo;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.core.vo.dialogue.MessageVo;
import gov.nwcg.isuite.framework.core.rules.AbstractRule;
import gov.nwcg.isuite.framework.core.service.BaseReportService;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.report.generator.ReportGenerator;
import gov.nwcg.isuite.framework.report.generator.ReportTimeInvoiceGenerator;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.MessageTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateTimeValidator;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportTimeServiceImpl extends BaseReportService implements ReportTimeService {

	public enum ResourceListEnum {
		PERSON_RESOURCE_NAMES
		,CONTRACTED_PERSON_RESOURCE_NAMES
		,NON_CONTRACTED_PERSON_RESOURCE_NAMES
		,REQUEST_NUMBERS
		,CONTRACTED_REQUEST_NUMBERS
		,NON_CONTRACTED_REQUEST_NUMBERS
		,PERSON_REQUEST_NUMBERS
		,CREW_NAMES
		,CREW_REQUEST_NUMBERS
		,AGENCY_RESOURCES
		,CONTRACTED_RESOURCES
	}
	
	public ReportTimeServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.core.service.ReportTimeService#generateOF288TimeInvoiceReport
	 * (gov.nwcg.isuite.core.reports.filter.OF288TimeInvoiceReportFilter,
	 * java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo generateOF288TimeInvoiceReport(TimeReportFilter filter, DialogueVo dialogueVo)
			throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		if(BooleanUtility.isTrue(filter.getFinalInvoice())){
			try{
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(filter.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
				
			}catch(Exception e){
				super.dialogueException(dialogueVo, e);
				return dialogueVo;
			}
			
		}
		
		ReportTimeInvoiceGenerator rtg = (ReportTimeInvoiceGenerator) context.getBean("of288ReportGenerator");
		if(DateTransferVo.hasDateString(filter.getLastDateToIncludeOnReportVo())){
			Date dt=DateTransferVo.getDate(filter.getLastDateToIncludeOnReportVo());
			try{
				dt=DateUtil.addMilitaryTimeToDate(dt, "2359");
			}catch(Exception e){}
			filter.setLastDateToIncludeOnReport(dt);
		}
		return rtg.generateReport(filter, dialogueVo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.nwcg.isuite.core.service.ReportTimeService#generateOF286TimeInvoiceReport
	 * (gov.nwcg.isuite.core.reports.filter.OF286TimeInvoiceReportFilter,
	 * java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	@Override
	public DialogueVo generateOF286TimeInvoiceReport(OF286TimeInvoiceReportFilter filter, DialogueVo dialogueVo)
			throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		if(BooleanUtility.isTrue(filter.getFinalInvoice())){
			try{
				IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
				if(lockedRuleHandler.execute(filter.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
					return dialogueVo;
				}
				
			}catch(Exception e){
				super.dialogueException(dialogueVo, e);
				return dialogueVo;
			}
			
		}
		
		ReportTimeInvoiceGenerator rtg = (ReportTimeInvoiceGenerator) context.getBean("of286ReportGenerator");
		if(DateTransferVo.hasDateString(filter.getLastDateToIncludeOnReportVo())){
			Date dt=DateTransferVo.getDate(filter.getLastDateToIncludeOnReportVo());
			try{
				dt=DateUtil.addMilitaryTimeToDate(dt, "2359");
			}catch(Exception e){}
			filter.setLastDateToIncludeOnReport(dt);
		}
		return rtg.generateReport(filter, dialogueVo);
	}
	
	public DialogueVo getResourceInvoiceList(Long resourceId, DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		TimeInvoiceDao tiDao = (TimeInvoiceDao) super.context.getBean("timeInvoiceDao");

		TimePostDao tpDao = (TimePostDao)context.getBean("timePostDao");
		
		try {
			Collection<TimeInvoiceVo> invoices = tiDao.getForResource(resourceId);
			
			Date maxDate=tpDao.getLatestTimePostingDateByResourceId(resourceId);
			
			if (invoices != null && invoices.size() > 0) {
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("ResourceInvoiceList");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coa.setIsDialogueEnding(true);
				dialogueVo.setResultObject(invoices);
				if(null != maxDate)
					dialogueVo.setResultObjectAlternate(DateUtil.toDateString(maxDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));
				else
					dialogueVo.setResultObjectAlternate(null);
					
				dialogueVo.setCourseOfActionVo(coa);
			}else{
				CourseOfActionVo coa = new CourseOfActionVo();
				coa.setCoaName("ResourceInvoiceList");
				coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
				coa.setIsDialogueEnding(true);
				dialogueVo.setCourseOfActionVo(coa);
				dialogueVo.setResultObject(invoices);
				if(null != maxDate)
					dialogueVo.setResultObjectAlternate(DateUtil.toDateString(maxDate,DateUtil.MM_SLASH_DD_SLASH_YYYY));
				else
					dialogueVo.setResultObjectAlternate(null);
			}
		} catch (Exception e) {
			super.handleException(e);
		}

		return dialogueVo;
	}

	public DialogueVo deleteLastTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		try{
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(filter.getTimeInvoiceId(), "TIMEINVOICE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
			return dialogueVo;
		}
		
		ReportTimeInvoiceGenerator rtg = (ReportTimeInvoiceGenerator) context.getBean("of286ReportGenerator");
		return rtg.deleteLastTimeInvoice(filter, dialogueVo);
	}

	public DialogueVo reprintOF286TimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		ReportTimeInvoiceGenerator rtg = (ReportTimeInvoiceGenerator) context.getBean("of286ReportGenerator");
		return rtg.reprintTimeInvoice(filter, dialogueVo);		
	}
	
	public DialogueVo reprintOF288TimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		ReportTimeInvoiceGenerator rtg = (ReportTimeInvoiceGenerator) context.getBean("of288ReportGenerator");
		return rtg.reprintTimeInvoice(filter, dialogueVo);		
	}
	
	public DialogueVo getResourceListForSelectedIncident(String listName, Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		Collection<ReportSelectVo> data = new ArrayList<ReportSelectVo>();

		try {
			ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			
			if(listName.equalsIgnoreCase(ResourceListEnum.PERSON_RESOURCE_NAMES.name())) {
				data = dao.getPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CONTRACTED_PERSON_RESOURCE_NAMES.name())) {
				data = dao.getContractedPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.NON_CONTRACTED_PERSON_RESOURCE_NAMES.name())) {
				data = dao.getNonContractedPersonResourceNamesForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.REQUEST_NUMBERS.name())) {
				data = dao.getRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CONTRACTED_REQUEST_NUMBERS.name())) {
				data = dao.getContractedRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.NON_CONTRACTED_REQUEST_NUMBERS.name())) {
				data = dao.getNonContractedRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.PERSON_REQUEST_NUMBERS.name())) {
				data = dao.getPersonRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CREW_NAMES.name())) {
				data = dao.getCrewNamesForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CREW_REQUEST_NUMBERS.name())) {
				data = dao.getCrewRequestNumbersForSelectedIncident(incidentId, incidentGroupId);
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CONTRACTED_RESOURCES.name())) {
				data = dao.getContractedResourcesForSelectedIncident(incidentId, incidentGroupId);
			} 
		} catch (Exception e) {
			if(listName.equalsIgnoreCase(ResourceListEnum.PERSON_RESOURCE_NAMES.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CONTRACTED_PERSON_RESOURCE_NAMES.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.NON_CONTRACTED_PERSON_RESOURCE_NAMES.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_PERSON_RESOURCE_NAMES_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.REQUEST_NUMBERS.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CONTRACTED_REQUEST_NUMBERS.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.NON_CONTRACTED_REQUEST_NUMBERS.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.PERSON_REQUEST_NUMBERS.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CREW_NAMES.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_CREW_NAMES_FOR_SELECTED_INCIDENT));
			} else if(listName.equalsIgnoreCase(ResourceListEnum.CREW_REQUEST_NUMBERS.name())) {
				throw new ServiceException(new ErrorObject(
						ErrorEnum.UNABLE_TO_RETRIEVE_CREW_REQUEST_NUMBERS_FOR_SELECTED_INCIDENT));
			} 
		}

		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName(listName);
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportTimeService#getAgencyResourcesForSelectedIncident(java.lang.Long, java.lang.Long, gov.nwcg.isuite.core.vo.dialogue.DialogueVo)
	 */
	public DialogueVo getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo)
			throws ServiceException {
		
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		Collection<ReportAgencySelect> data = new ArrayList<ReportAgencySelect>();
		List<ReportAgencySelectVo> dataVo = new ArrayList<ReportAgencySelectVo>();

		try {
			ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
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
		coa.setCoaName(ResourceListEnum.AGENCY_RESOURCES.name());
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(dataVo);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.service.ReportTimeService#saveActualReleaseDateAndTime(java.util.Date, gov.nwcg.isuite.framework.filter.TimeReportFilter, java.lang.Long)
	 */
	public DialogueVo saveActualReleaseDateAndTime(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		WorkPeriodDao workPeriodDao = (WorkPeriodDao)super.context.getBean("workPeriodDao");

		try {
			IsLockedRulesHandler lockedRuleHandler = new IsLockedRulesHandler(super.context);
			if(lockedRuleHandler.execute(filter.getIncidentResourceId(), "INCIDENTRESOURCE", dialogueVo)!=AbstractRule._OK){
				return dialogueVo;
			}
			
			
			WorkPeriod wp = workPeriodDao.getByIncidentResourceId(filter.getIncidentResourceId());
			
			// defect 4044 - validate release date not before checkin date
			if(DateUtil.hasValue(wp.getCICheckInDate())){
				Date ciDate=wp.getCICheckInDate();
				try{
					ciDate=DateUtil.addMilitaryTimeToDate(ciDate, "2359");
					Date dmReleaseDate=DateTransferVo.getDate(filter.getActualReleaseDateTransferVo());
					String dmTime="";
					if(StringUtility.hasValue(filter.getActualReleaseDateTransferVo().timeString)){
						dmTime=filter.getActualReleaseDateTransferVo().timeString;
					}
					
					if(DateUtil.hasValue(dmReleaseDate)){
						dmReleaseDate=DateUtil.addMilitaryTimeToDate(dmReleaseDate, "2359");
						ErrorObject error=DateTimeValidator.validateDate1NotBeforeDate2(
								dmReleaseDate
								,ciDate
								, "Actual Release Date", "Check-In Date");

						if(error!=null){
							CourseOfActionVo coaVo = new CourseOfActionVo();
							
							Collection<ErrorObject> validationErrors = new ArrayList<ErrorObject>();
							validationErrors.add(error);
							coaVo.setCoaName("ValidationError");
							coaVo.setCoaType(CourseOfActionTypeEnum.ERROR);
							coaVo.setIsDialogueEnding(Boolean.TRUE);
							coaVo.setErrorObjectVos(validationErrors);
							dialogueVo.setCourseOfActionVo(coaVo);
							return dialogueVo;
						}
					}

					if(StringUtility.hasValue(dmTime)){
						dmReleaseDate=DateUtil.addMilitaryTimeToDate(dmReleaseDate, dmTime);
					}
					
					wp.setDMReleaseDate(dmReleaseDate);
					workPeriodDao.save(wp);
					
				}catch(Exception e){}
			}else{
				Date dmReleaseDate=DateTransferVo.getDate(filter.getActualReleaseDateTransferVo());
				String dmTime=DateTransferVo.getTime(filter.getActualReleaseDateTransferVo());
				
				if(DateUtil.hasValue(dmReleaseDate)){
					if(StringUtility.hasValue(dmTime)){
						try{
							dmReleaseDate=DateUtil.addMilitaryTimeToDate(dmReleaseDate, dmTime);
						}catch(Exception ee){}
					}
					wp.setDMReleaseDate(dmReleaseDate);
					workPeriodDao.save(wp);
				}
				
			}

			//wp.setDMReleaseDate(filter.getActualReleaseDate());
			//workPeriodDao.save(wp);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject(ErrorEnum._90000_ERROR, e.getMessage()));
		} catch(Exception ee){
			super.dialogueException(dialogueVo, ee);
			return dialogueVo;
		}
		
		MessageVo mvo = new MessageVo();
		mvo.setMessageType(MessageTypeEnum.INFO);
		mvo.setMessageProperty("info.0030");
		mvo.setTitleProperty("text.timeReports");
		
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaName("SAVE_ACTUAL_RELEASE_DATE/TIME");
		coa.setCoaType(CourseOfActionTypeEnum.SHOWMESSAGE);
		coa.setMessageVo(mvo);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
	
	/*
	 *  NON-INVOICE TIME REPORTS
	 */
	
	public DialogueVo generatePersonnelTimeReport(PersonnelTimeReportFilter filter, DialogueVo dialogueVo)
			throws Exception {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		ReportGenerator rg = (ReportGenerator) context.getBean("personnelTimeReportGenerator");
		filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
		filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
		return rg.generateReport(filter, dialogueVo);
	}
	
	public DialogueVo generateShiftsInExcessOfStandardHoursReport2(ShiftsInExcessOfStandardHoursReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
 
		//ReportGenerator rg = (ReportGenerator) context.getBean("shiftsInExcessReportGenerator2");
		ReportGenerator rg = (ReportGenerator) context.getBean("shiftsInExcessReportGenerator2Alt");
		filter.setFirstDateToIncludeOnReport(filter.getFirstDateToIncludeOnReportVo().getDate(filter.getFirstDateToIncludeOnReportVo()));
		filter.setLastDateToIncludeOnReport(filter.getLastDateToIncludeOnReportVo().getDate(filter.getLastDateToIncludeOnReportVo()));
		return rg.generateReport(filter, dialogueVo);
	}

	public DialogueVo generateShiftsInExcessOfStandardHoursReport(ShiftsInExcessOfStandardHoursReportFilter filter, DialogueVo dialogueVo)  throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		//ReportGenerator rg = (ReportGenerator) context.getBean("shiftsInExcessReportGenerator2");
		ReportGenerator rg = (ReportGenerator) context.getBean("shiftsInExcessReportGenerator2Alt");
		filter.setFirstDateToIncludeOnReport(filter.getFirstDateToIncludeOnReportVo().getDate(filter.getFirstDateToIncludeOnReportVo()));
		filter.setLastDateToIncludeOnReport(filter.getLastDateToIncludeOnReportVo().getDate(filter.getLastDateToIncludeOnReportVo()));
		return rg.generateReport(filter, dialogueVo);
	}

	public DialogueVo generateVendorResourceSummaryReport(VendorResourceSummaryReportFilter filter, DialogueVo dialogueVo)  
			throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		ReportGenerator rg = (ReportGenerator) context.getBean("vendorResourceSummaryReportGenerator");
		return rg.generateReport(filter, dialogueVo);
	}
 
	public DialogueVo generateSummaryHoursWorkedReport(SummaryHoursWorkedReportFilter filter, DialogueVo dialogueVo) 
			throws ServiceException, PersistenceException {
		
		ReportGenerator rg = (ReportGenerator) context.getBean("summaryHoursWorkedReportGenerator2");
		filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
		filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
		return rg.generateReport(filter, dialogueVo);
	}
	
	private boolean isDateFound(MissingDaysOfPostingsSubReportData data, Date date, Collection<MissingDaysOfPostingsSubReportData> rpt) {
		boolean found = false;
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String missingDate = df.format(date);

		for(MissingDaysOfPostingsSubReportData d : rpt) {
			if (data.getRequestNumber() == null) {
				if (data.getResourceName().equals(d.getResourceName()) && missingDate.equals(d.getPostingDateChar()))
					found = true;
			}
			else {
				if (data.getRequestNumber().equals(d.getRequestNumber()) && data.getResourceName().equals(d.getResourceName()) && missingDate.equals(d.getPostingDateChar()))
					found = true;
			}
		}
		
		return found;
	}
	
	private Collection<MissingDaysOfPostingsSubReportData> generatetMissingDaysOfPostingsReportData(Collection<MissingDaysOfPostingsSubReportData> rptS_E, 
			Collection<MissingDaysOfPostingsSubReportData> rptUni, MissingDaysOfPostingsReportFilter filter) throws ServiceException,
			PersistenceException
	{
				
		Collection<MissingDaysOfPostingsSubReportData> rptMissing = new ArrayList<MissingDaysOfPostingsSubReportData>();
				
		Calendar start = new GregorianCalendar(Integer.parseInt(filter.getStartDateChar().substring(6, 10)),Integer.parseInt(filter.getStartDateChar().substring(0, 2))-1,Integer.parseInt(filter.getStartDateChar().substring(3, 5))); // year , month, day
		Calendar end = new  GregorianCalendar(Integer.parseInt(filter.getEndDateChar().substring(6, 10)),Integer.parseInt(filter.getEndDateChar().substring(0, 2))-1,Integer.parseInt(filter.getEndDateChar().substring(3, 5))); // year , month, day
		
		Calendar cldr = (Calendar) start.clone();
		cldr.add(Calendar.DAY_OF_MONTH, -1);
		 
		Date[] dates = getDiff(cldr,end);
		
		
		MissingDaysOfPostingsSubReportData newData;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String missingDate;
		
	    for(MissingDaysOfPostingsSubReportData data : rptUni) {
			
	    	for(Date date : dates) {
				
	    		if (!isDateFound(data, date, rptS_E)) {
	    			newData = new MissingDaysOfPostingsSubReportData();
	    			missingDate = df.format(date);
	    			newData.setRequestNumber(data.getRequestNumber());
	    			newData.setResourceName(data.getResourceName());
	    			newData.setCheckInDateChar(data.getCheckInDateChar());
	    			newData.setFirstPostingDateChar(data.getFirstPostingDateChar());
	    			newData.setPostingDateChar(missingDate);
	    			rptMissing.add(newData);
	    		}
			}
		}
	    
	    Collection<MissingDaysOfPostingsSubReportData> rptMissingFinal = new ArrayList<MissingDaysOfPostingsSubReportData>();
	            
	    for(MissingDaysOfPostingsSubReportData data : rptMissing) {
	    	if (data.getCheckInDateChar() != null) {
	    		try {
	    		 //delete dates before checked-in        			
	    		 Date checkin = df.parse(data.getCheckInDateChar());
	    		 Date posting = df.parse(data.getPostingDateChar());
	    		 if (checkin.equals(posting) || checkin.before(posting)) {
	    			 newData = new MissingDaysOfPostingsSubReportData();
	     			 newData.setRequestNumber(data.getRequestNumber());
	     			 newData.setResourceName(data.getResourceName());
	     			 newData.setPostingDateChar(data.getPostingDateChar());
	     			 rptMissingFinal.add(newData);
	    		 }
	    		}
	    		catch (Exception e) {}
	    	}
	    	else {
	    		try {
	    		 //delete dates before firstPostingDate
	       		 Date firstPosting = df.parse(data.getFirstPostingDateChar());
	       		 Date posting = df.parse(data.getPostingDateChar());
	       		 if (firstPosting.before(posting)) {
	       			 newData = new MissingDaysOfPostingsSubReportData();
	        			 newData.setRequestNumber(data.getRequestNumber());
	        			 newData.setResourceName(data.getResourceName());
	        			 newData.setPostingDateChar(data.getPostingDateChar());
	        			 rptMissingFinal.add(newData);
	       		 }
	       		}
	       		catch (Exception e) {}   
	    	}
	    }
	    
		return rptMissingFinal;
	}

	public static Date[] getDiff(Calendar start, Calendar end) throws IllegalArgumentException{
		if(start.after(end)) throw new IllegalArgumentException(start.toString()  + " is after "  + end.toString());
		long diffDays = diffDayPeriods(start,end);
		int size = new Long(diffDays).intValue();
		Date[] diff = new Date[size];
		for(int i = 0 ; i < size; i++) {
		  start.add(Calendar.DAY_OF_YEAR, 1);
			diff[i] = start.getTime();
		}
		return diff;
	}
	
	private static long diffDayPeriods(Calendar start, Calendar end) {
        long endL   =  end.getTimeInMillis() +  end.getTimeZone().getOffset(  end.getTimeInMillis() );
        long startL = start.getTimeInMillis() + start.getTimeZone().getOffset( start.getTimeInMillis() );
        return (endL - startL) / MILLISECS_PER_DAY;
    }
	
	public DialogueVo generateMissingDaysOfPostingsReport(MissingDaysOfPostingsReportFilter filter, DialogueVo dialogueVo) 
	throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		Collection<Incident> incidents = new ArrayList<Incident>();
		
		filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
		filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
						
		if(filter.getIncidentGroupId() > 0)
			incidents.addAll(getIncidentsByIncidentIds(getIncidentIdsByIncidentGroupId(filter.getIncidentGroupId()))); 
		else 
			incidents.add(getIncidentByIncidentId(filter.getIncidentId())); 
		
						
		ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		
		Collection<MissingDaysOfPostingsReportData> missingDaysOfPostingsReportDataList = new ArrayList<MissingDaysOfPostingsReportData>();
		
		for(Incident incident : incidents) {
			
			Collection<MissingDaysOfPostingsSubReportData> reportDataWithStartAndEnd;
			reportDataWithStartAndEnd = reportTimeDao.getDaysOfPostingsReportData((MissingDaysOfPostingsReportFilter) filter, incident.getId());
										
			Collection<MissingDaysOfPostingsSubReportData> reportDataUnique;
			reportDataUnique = reportTimeDao.getUniqueDaysOfPostingsReportData((MissingDaysOfPostingsReportFilter) filter, incident.getId());
				
										
			Collection<MissingDaysOfPostingsSubReportData> reportDataMissing;
			reportDataMissing = generatetMissingDaysOfPostingsReportData(reportDataWithStartAndEnd, reportDataUnique, (MissingDaysOfPostingsReportFilter) filter);
						
			MissingDaysOfPostingsReportData missingDaysOfPostingsReportData = new MissingDaysOfPostingsReportData();
			
			if(null != reportDataMissing && reportDataMissing.size() > 0) 
				missingDaysOfPostingsReportData.setSubReportData(reportDataMissing);
			
			else
				continue;
			
			missingDaysOfPostingsReportData.setIncidentId(incident.getId());
			missingDaysOfPostingsReportData.setIncidentName(incident.getIncidentName());
			missingDaysOfPostingsReportData.setIncidentNumber(incident.getIncidentNumber());
			missingDaysOfPostingsReportDataList.add(missingDaysOfPostingsReportData);

		}
										
		if(missingDaysOfPostingsReportDataList.size() < 1){
			dialogueVo.setCourseOfActionVo(super.buildNoDataCoa("Missing Days Of Postings Report","text.missingDaysOfPostingsReportInstructions"));
			return dialogueVo;
		}
						
		String pdfURL = null;

		try {
			IReport report = new MissingDaysOfPostingsReport("", missingDaysOfPostingsReportDataList);
			if (((MissingDaysOfPostingsReportFilter) filter).getPersonnelOrVendor().equals("Personnel")) 
			    report.addCustomField("reportTitle", "Missing Days of Postings by Personnel Report");
			else
				report.addCustomField("reportTitle", "Missing Days of Postings by Vendor Report");
			
			if(filter.getIncidentGroupId() > 0) {
				IncidentGroupDao igDao = this.getIncidentGroupDao();
				IncidentGroup ig = igDao.getById(filter.getIncidentGroupId(), IncidentGroup.class);
				report.addCustomField("reportSubTitle", "Incident Group: " + ig.getGroupName());
			}	
			else {
				Incident inc = getIncidentByIncidentId(filter.getIncidentId());
				report.addCustomField("reportSubTitle", "Incident: " + inc.getIncidentName() + " (" + inc.getIncidentNumber() + ")");
			}
			
			report.addCustomField("startDateChar", ((MissingDaysOfPostingsReportFilter) filter).getStartDateChar());
			report.addCustomField("endDateChar", ((MissingDaysOfPostingsReportFilter) filter).getEndDateChar());
			
			report.addCustomField("SUBREPORT_DIR", this.getSourcePath());
				
			pdfURL = generateReport(report, filter.getIncidentId(), filter.getIncidentGroupId());			
			dialogueVo.setCourseOfActionVo(super.buildCompleteCoa());
			dialogueVo.setResultObject(pdfURL);
				
		} catch (Exception e) {
			super.handleException(e);
		}
		
		return dialogueVo;	
		
	}

	public DialogueVo generateWorkRestRatioReport(WorkRestRatioReportFilter filter, DialogueVo dialogueVo)  
			throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		//ReportGenerator rg = (ReportGenerator) context.getBean("workRestRatioReportGenerator");
		ReportGenerator rg = (ReportGenerator) context.getBean("workRestRatioReportGenerator2Alt");
		filter.setStartDate(filter.getStartDateVo().getDate(filter.getStartDateVo()));
		filter.setEndDate(filter.getEndDateVo().getDate(filter.getEndDateVo()));
		return rg.generateReport(filter, dialogueVo);
	}

	@Override
	public DialogueVo generateCrewRosterReport(TimeReportFilter filter, DialogueVo dialogueVo) 
			throws ServiceException, PersistenceException {
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}

		ReportGenerator rg = (ReportGenerator) context.getBean("crewRosterReportGenerator");
		return rg.generateReport(filter, dialogueVo);
	}
	
	/**
	 * Returns list of PersonnelTimeRepCTDVo objects that will be used as a datasource for the checkbox tree component
	 * on the personnel time report UI
	 */
	public DialogueVo getAgencyTreeDataForPersonnelTimeRep(Long incidentOrGroupId, Boolean isIncidentGroup, DialogueVo dialogueVo) throws ServiceException {
		if(incidentOrGroupId == null || isIncidentGroup == null) {
			throw new ServiceException(new ErrorObject("Cannot determine if ID is for incident or incident group."));
		}
		
		ReportTimeDao dao = (ReportTimeDao) super.context.getBean("reportTimeDao");
		if (dialogueVo == null) {
			dialogueVo = new DialogueVo();
		}
		
		Collection<PersonnelTimeReportAgencyFaxResourceData> retrievedList = new ArrayList<PersonnelTimeReportAgencyFaxResourceData>();
		try {
			retrievedList = dao.getAgencyTreeDataForPersonnelTimeRep(incidentOrGroupId, isIncidentGroup);
		} catch (PersistenceException e) {
			throw new ServiceException(new ErrorObject("Unable to retrieve list of agencies, resources, and fax numbers."));
		}
		
		// Convert retrievedData to the format needed by the Flex UI to render the checkbox tree
		/*
		 * Structure of the collection: 
		 * Agency 
		 * |___ Fax Numbers
		 * 			|___ Resources
		 * 
		 */
		Map<String, PersonnelTimeRepCTDVo> agencyMap = new LinkedHashMap<String, PersonnelTimeRepCTDVo>(); //new ArrayList<PersonnelTimeRepCTDVo>();
		if(retrievedList!=null && retrievedList.size()>0){
			// Level 1: Create set of objects with agencies defined
			for (PersonnelTimeReportAgencyFaxResourceData retrievedObject : retrievedList){
				String agencyCode = retrievedObject.getAgencyCode();
				if(agencyMap.get(agencyCode)==null){
					PersonnelTimeRepCTDVo agencyCTDVo = new PersonnelTimeRepCTDVo(PersonnelTimeRepCTDVo.PERSONNEL_TIME_REPORT_AGENCY);
					agencyCTDVo.setItemLabel(retrievedObject.getAgencyCode());
					agencyMap.put(agencyCode, agencyCTDVo);
				}
			}
			
			// Level 2: Add fax numbers to children created in Step 1
			for (PersonnelTimeReportAgencyFaxResourceData retrievedObject : retrievedList){
				PersonnelTimeRepCTDVo agencyCTDVo = agencyMap.get(retrievedObject.getAgencyCode()); // We know the object is not null because we added in Level 1
				Collection<CheckboxTreeDataVo> childrenFax = agencyCTDVo.getChildren();
				if(childrenFax == null) {
					childrenFax = new ArrayList<CheckboxTreeDataVo>();
					agencyCTDVo.setChildren(childrenFax);
				}
				boolean found = false;
				for (CheckboxTreeDataVo childFax : childrenFax){
					if(childFax.getItemLabel().equals(retrievedObject.getFaxNumber())) {
						found = true;
						break;
					}
				}
				if(found==false){ // Fax number not found in this agency. Add it. 
					PersonnelTimeRepCTDVo faxCTDVo = new PersonnelTimeRepCTDVo(PersonnelTimeRepCTDVo.PERSONNEL_TIME_REPORT_FAX);
					faxCTDVo.setItemLabel(retrievedObject.getFaxNumber());
					childrenFax.add(faxCTDVo);
				}
			}
			
			// Level 3: Add resources to children created in Step 2
			for (PersonnelTimeReportAgencyFaxResourceData retrievedObject : retrievedList){
				PersonnelTimeRepCTDVo agencyCTDVo = agencyMap.get(retrievedObject.getAgencyCode()); // Object is not null because we added in Level 1
				Collection<CheckboxTreeDataVo> childrenFax = agencyCTDVo.getChildren(); // Children will not be null because we added in Level 2
				for (CheckboxTreeDataVo childFax : childrenFax){
					// If the fax number of the retrieved object doesn't match the fax number of this child,
					// continue over the loop and check the next childFax in this agency.
					if(!childFax.getItemLabel().equals(retrievedObject.getFaxNumber())) {
						continue;
					}
					
					Collection<CheckboxTreeDataVo> childrenResource = childFax.getChildren(); 
					if(childrenResource == null) {
						childrenResource = new ArrayList<CheckboxTreeDataVo>();
						childFax.setChildren(childrenResource);
					}
					
					boolean found = false;
					for (CheckboxTreeDataVo childResource : childrenResource){
						// Comparing using itemValue instead of itemLabel(as above)
						if(childResource.getItemValue().equals(retrievedObject.getResourceId())) {
							found = true;
							break;
						}
					}
					if(found==false){ // Resource ID not found in this agency. Add it. 
						PersonnelTimeRepCTDVo resourceCTDVo = new PersonnelTimeRepCTDVo(PersonnelTimeRepCTDVo.PERSONNEL_TIME_REPORT_RESOURCE);
						resourceCTDVo.setItemLabel(retrievedObject.getResourceName());
						resourceCTDVo.setItemValue(retrievedObject.getResourceId().toString());
						childrenResource.add(resourceCTDVo);
					}
				}
			}
		}
		
		//Convert agencyMap to Collection that can be returned via dialogue
		Collection<PersonnelTimeRepCTDVo> data = new ArrayList<PersonnelTimeRepCTDVo>(agencyMap.values());
		
		CourseOfActionVo coa = new CourseOfActionVo();
		coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
		dialogueVo.setResultObject(data);
		dialogueVo.setCourseOfActionVo(coa);
		return dialogueVo;
	}
	
	public DialogueVo getResourceData(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException {
		if(null == dialogueVo) dialogueVo = new DialogueVo();
		
		try{
			IncidentResourceDao irDao = (IncidentResourceDao)context.getBean("incidentResourceDao");
			Collection<IncidentResourceGridVo> vos = irDao.getReportResourceData(incidentId, incidentGroupId);

			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("GET_RESOURCE_DATA");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			dialogueVo.setRecordset(vos);
			dialogueVo.setCourseOfActionVo(coa);
			
		}catch(Exception e){
			super.dialogueException(dialogueVo, e);
		}
		
		return dialogueVo;
	}
}	
