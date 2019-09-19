package gov.nwcg.isuite.core.controllers;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GlidePathReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.StrikeTeamTaskForceReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.core.service.GlidePathReportService;
import gov.nwcg.isuite.core.service.ReportService2;
import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

@Controller
@RequestMapping("/reports/plans")
public class PlanReportController extends BaseRestController{
	@Autowired
	private ReportService2 service;
	@Autowired
	private GlidePathReportService glidePathReportService;
	
	@RequestMapping(value = "/ics209", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateICS209Report(@RequestBody ICS209ReportFilter ics209ReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateICS209Report(ics209ReportFilter, null));
	}
	
	@RequestMapping(value = "/all-incident-resources", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateAllIncidentResourcesReport(@RequestBody AllIncidentResourcesReportFilter allIncidentResourcesReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateAllIncidentResourcesReport(allIncidentResourcesReportFilter, null));
	}
	
	@RequestMapping(value = "/qualifications", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateQualificationsReport(@RequestBody QualificationsReportFilter qualificationsReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateQualificationsReport(qualificationsReportFilter, null));
	}
	
	@RequestMapping(value = "/strike-team-task-force", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateStrikeTeamTaskForceReport(@RequestBody StrikeTeamTaskForceReportFilter strikeTeamTaskForceReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateStrikeTeamTaskForceReport(strikeTeamTaskForceReportFilter, null));
	}
	
	@RequestMapping(value = "/checkout", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateCheckoutReport(@RequestBody CheckoutReportFilter checkoutReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateCheckoutReport(checkoutReportFilter, null));
	}
	
	@RequestMapping(value = "/last-work-day", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateLastWorkDayReport(@RequestBody LastWorkDayReportFilter lastWorkDayReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateLastWorkDayReport(lastWorkDayReportFilter, null));
	}
	
	@RequestMapping(value = "/demob", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateDemobPlanningReport(@RequestBody DemobPlanningReportFilter demobPlanningReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateDemobPlanningReport(demobPlanningReportFilter, null));
	}
	
	@RequestMapping(value = "/tentative", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateTentativeReleasePosterReport(@RequestBody TentativeReleasePosterReportFilter tentativeReleasePosterReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateTentativeReleasePosterReport(tentativeReleasePosterReportFilter, null));
	}
	
	@RequestMapping(value = "/available", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateAvailableForReleaseReport(@RequestBody AvailableForReleaseReportFilter availableForReleaseReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateAvailableForReleaseReport(availableForReleaseReportFilter, null));
	}
	
	@RequestMapping(value = "/air-travel", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateAirTravelRequestReport(@RequestBody AirTravelRequestReportFilter airTravelRequestReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateAirTravelRequestReport(airTravelRequestReportFilter, null));
	}
	
	@RequestMapping(value = "/actual-demob", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateActualDemobReport(@RequestBody ActualDemobReportFilter actualDemobReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateActualDemobReport(actualDemobReportFilter, null));
	}
	
	@RequestMapping(value = "/ground-support", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateGroundSupportReport(@RequestBody GroundSupportReportFilter groundSupportReportFilter) throws ServiceException, ValidationException {
		return super.resolveMessaging(service.generateGroundSupportReport(groundSupportReportFilter, null));
	}
	
	@RequestMapping(value = "/glide-path", method = { RequestMethod.POST})
	public @ResponseBody DialogueVo generateGlidePathReport(@RequestBody GlidePathReportFilter glidePathReportFilter) throws ServiceException, PersistenceException {
		return super.resolveMessaging(glidePathReportService.generateGlidePathReport(glidePathReportFilter, null));
	}
}
