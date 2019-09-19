package gov.nwcg.isuite.core.controllers;

import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.OF286TimeInvoiceReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.service.ReportTimeService;
import gov.nwcg.isuite.core.service.impl.ReportServiceImpl;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

@Controller
@RequestMapping("/reports/time")
public class TimeReportController extends BaseRestController {
	@Autowired
	private ReportTimeService service;
	
	@Autowired
	private ReportServiceImpl reportService;

	@RequestMapping(value = "/get-resource-data", method = { RequestMethod.GET})
	public @ResponseBody DialogueVo getResourceData(@RequestParam("incidentId") Long incidentId, @RequestParam("incidentGroupId") Long incidentGroupId) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.getResourceData(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/get-resource-list-for-selected-incident", method = { RequestMethod.GET})
	public @ResponseBody DialogueVo getResourceListForSelectedIncident(@RequestParam("listName") String listName, @RequestParam("incidentId") Long incidentId, @RequestParam("incidentGroupId") Long incidentGroupId) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.getResourceListForSelectedIncident(listName, incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/get-request-numbers-for-selected-incident", method = { RequestMethod.GET})
	public @ResponseBody DialogueVo getRequestNumbersForSelectedIncident(@RequestParam("incidentId") Long incidentId, @RequestParam("incidentGroupId") Long incidentGroupId) throws ServiceException, PersistenceException {
		return super.resolveMessaging(reportService.getRequestNumbersForSelectedIncident(incidentId, incidentGroupId, null));
	}
	
	@RequestMapping(value = "/resource-invoice-list", method = { RequestMethod.GET})
	public @ResponseBody DialogueVo getResourceInvoiceList(@RequestParam("resourceId") Long resourceId) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.getResourceInvoiceList(resourceId, null));
	}
	
	@RequestMapping(value = "/delete-last-invoice", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo deleteLastTimeInvoice(@RequestBody TimeReportFilter timeReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.deleteLastTimeInvoice(timeReportFilter, null));
	}
	
	@RequestMapping(value = "/of-288", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateOF288TimeInvoiceReport(@RequestBody TimeReportFilter timeReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateOF288TimeInvoiceReport(timeReportFilter, null));
	}
	
	@RequestMapping(value = "/of-286", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateOF286TimeInvoiceReport(@RequestBody OF286TimeInvoiceReportFilter of286TimeInvoiceReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateOF286TimeInvoiceReport(of286TimeInvoiceReportFilter, null));
	}
	
	@RequestMapping(value = "/shifts-in-excess-of-standard-hours", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateShiftsInExcessOfStandardHoursReport(@RequestBody ShiftsInExcessOfStandardHoursReportFilter shiftsInExcessOfStandardHoursReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateShiftsInExcessOfStandardHoursReport(shiftsInExcessOfStandardHoursReportFilter, null));
	}
	
	@RequestMapping(value = "/personnel", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generatePersonnelTimeReport(@RequestBody PersonnelTimeReportFilter personnelTimeReportFilter) throws Exception {
		return super.resolveMessaging(service.generatePersonnelTimeReport(personnelTimeReportFilter, null));
	}
	
	@RequestMapping(value = "/work-rest-ratio", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateWorkRestRatioReport(@RequestBody WorkRestRatioReportFilter workRestRatioReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateWorkRestRatioReport(workRestRatioReportFilter, null));
	}
	
	@RequestMapping(value = "/get-agency-resources-for-selected-incident", method = { RequestMethod.GET})
	public @ResponseBody DialogueVo getAgencyResourcesForSelectedIncident(@RequestParam("incidentId") Long incidentId, @RequestParam("incidentGroupId") Long incidentGroupId) throws ServiceException, PersistenceException {
		return super.resolveMessaging(reportService.getAgencyResourcesForSelectedIncident(incidentId, incidentGroupId, null));
	}

	@RequestMapping(value = "/summary-hours-worked", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateSummaryHoursWorkedReport(@RequestBody SummaryHoursWorkedReportFilter summaryHoursWorkedReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateSummaryHoursWorkedReport(summaryHoursWorkedReportFilter, null));
	}
	
	@RequestMapping(value = "/missing-days-of-postings", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateMissingDaysOfPostingsReport(@RequestBody MissingDaysOfPostingsReportFilter missingDaysOfPostingsReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateMissingDaysOfPostingsReport(missingDaysOfPostingsReportFilter, null));
	}
	
	@RequestMapping(value = "/crew-roster", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateCrewRosterReport(@RequestBody TimeReportFilter timeReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateCrewRosterReport(timeReportFilter, null));
	}
	
	@RequestMapping(value = "/vendor-resource-summary", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateVendorResourceSummaryReport(@RequestBody VendorResourceSummaryReportFilter vendorResourceSummaryReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(service.generateVendorResourceSummaryReport(vendorResourceSummaryReportFilter, null));
	}
}
