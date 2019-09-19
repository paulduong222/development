package gov.nwcg.isuite.core.service;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.StrikeTeamTaskForceReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;


public interface ReportService2 {

	public DialogueVo generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo generateGroundSupportReport(GroundSupportReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo generateTentativeReleasePosterReport(TentativeReleasePosterReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo generateAirTravelRequestReport(AirTravelRequestReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo generateAvailableForReleaseReport(AvailableForReleaseReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;
	
	public DialogueVo generateActualDemobReport(ActualDemobReportFilter filter, DialogueVo dialogueVo) throws ServiceException ;	

	public DialogueVo generateCheckoutReport(CheckoutReportFilter filter, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo generateLastWorkDayReport(LastWorkDayReportFilter filter,DialogueVo dialogueVo) throws ServiceException ;

	public DialogueVo generateDemobPlanningReport(DemobPlanningReportFilter filter,DialogueVo dialogueVo) throws ServiceException ;	
	
	public DialogueVo generateICS209Report(ICS209ReportFilter filter,DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo generateStrikeTeamTaskForceReport(StrikeTeamTaskForceReportFilter filter,DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo generateQualificationsReport(QualificationsReportFilter filter,DialogueVo dialogueVo) throws ServiceException ;
	
}
