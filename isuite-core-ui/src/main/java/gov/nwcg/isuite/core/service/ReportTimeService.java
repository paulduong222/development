package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.OF286TimeInvoiceReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.vo.PersonnelTimeRepCTDVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;

import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;


public interface ReportTimeService {

	/**
	 * 
	 * @param filter
	 * @param incidentId
	 * @param incidentResourceId
	 * @return
	 * @throws ServiceException
	 * @throws PersistenceException 
	 */
	public DialogueVo generateOF288TimeInvoiceReport(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	/**
	 * 
	 * @param filter
	 * @param incidentId
	 * @return
	 * @throws ServiceException
	 * @throws PersistenceException 
	 */
	public DialogueVo generateOF286TimeInvoiceReport(OF286TimeInvoiceReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;

	public DialogueVo reprintOF286TimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo reprintOF288TimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo generateWorkRestRatioReport(WorkRestRatioReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo generateSummaryHoursWorkedReport(SummaryHoursWorkedReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo generateMissingDaysOfPostingsReport(MissingDaysOfPostingsReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo generateCrewRosterReport(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	public DialogueVo generateVendorResourceSummaryReport(VendorResourceSummaryReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	
	 /**
	 * 
	 * @param filter
	 * @param incidentId
	 * @param dialogueVo
	 * @return
	 * @throws ServiceException
	 */
	public DialogueVo generateShiftsInExcessOfStandardHoursReport(ShiftsInExcessOfStandardHoursReportFilter filter, DialogueVo dialogueVo) throws ServiceException, PersistenceException;
	
	DialogueVo getResourceListForSelectedIncident(String listName, Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	DialogueVo getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
//	DialogueVo getRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getNonContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getPersonRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getNonContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
//	
//	DialogueVo getCrewNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	DialogueVo saveActualReleaseDateAndTime(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getAgencyTreeDataForPersonnelTimeRep( Long incidentOrGroupId, Boolean isIncidentGroup, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo getResourceData(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generatePersonnelTimeReport(PersonnelTimeReportFilter filter, DialogueVo dialogueVo) throws Exception;
	
	public DialogueVo getResourceInvoiceList(Long resourceId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo deleteLastTimeInvoice(TimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
}
