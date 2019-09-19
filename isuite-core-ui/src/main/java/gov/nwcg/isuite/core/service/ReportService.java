package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

public interface ReportService {

	public String generateAllIncidentResourcesReport(AllIncidentResourcesReportFilter filter) throws ServiceException;
	
	public String generateCheckoutReport(CheckoutReportFilter filter) throws ServiceException;
	
	public String generateDemobPlanningReport(DemobPlanningReportFilter filter) throws ServiceException;
	
	public String generateTentativeReleasePosterReport(TentativeReleasePosterReportFilter filter) throws ServiceException;
	
	public String generateAvailableForReleaseReport(AvailableForReleaseReportFilter filter) throws ServiceException;
	
	public String generateAirTravelRequestReport(AirTravelRequestReportFilter filter) throws ServiceException;
	
	public String generateLastWorkDayReport(LastWorkDayReportFilter filter) throws ServiceException; 
	
	public String generateActualDemobReport(ActualDemobReportFilter filter) throws ServiceException;
	
	public String generateGroundSupportReport(GroundSupportReportFilter filter) throws ServiceException;
	
	public String generateICS209Report(ICS209ReportFilter filter) throws ServiceException;
	
	public String generatePasswordExpiredReport() throws ServiceException;
	
	/**
	 * 
	 * @param incidentId
	 * @return
	 * @throws ServiceException
	 */
	public String generateStrikeTeamTaskForceReport(Long incidentId,Collection<Integer> resourceIds, Long incidentGroupId) throws ServiceException;
			
	public String generateQualificationsReport(QualificationsReportFilter filter) throws ServiceException ;
	
//	/**
//	 * 
//	 * @param actualReleaseDateTime
//	 * @param filter
//	 * @param incidentId
//	 * @throws ServiceException
//	 */
//	public void saveActualReleaseDateAndTime(Date actualReleaseDateTime, TimeReportFilter filter, Long incidentId) throws ServiceException;

	public DialogueVo getRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getNonContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getPersonRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getNonContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getCrewNamesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;

	public DialogueVo generatePersonnelTimeReport(PersonnelTimeReportFilter filter, DialogueVo dialogueVo) throws ServiceException;
	
	public DialogueVo getAvailableForReleaseReprintList(Long incidentId, Long incidentGroupId, DialogueVo dialogueVo) throws ServiceException;
	
}
