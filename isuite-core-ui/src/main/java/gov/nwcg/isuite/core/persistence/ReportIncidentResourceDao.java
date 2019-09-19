package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.data.ICS209ReportData;
import gov.nwcg.isuite.core.reports.data.QualificationsReportData;
import gov.nwcg.isuite.core.reports.data.StrikeTeamTaskForceReportData;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.reports.filter.ICS209ReportFilter;
import gov.nwcg.isuite.core.reports.filter.QualificationsReportFilter;
import gov.nwcg.isuite.core.reports.filter.StrikeTeamTaskForceReportFilter;
import gov.nwcg.isuite.core.vo.ICS209ResourceData;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.SessionFactory;

/**
 * Data Access for Incident Resource Reports.
 *  
 * @author bsteiner
 */
public interface ReportIncidentResourceDao extends TransactionSupport{
   
	public void setSessionFactory(SessionFactory sf);
	
   /**
    * Returns a collection of AllIncidentResourcesReportData objects.
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<AllIncidentResourcesReportData> getAllIncidentResourcesReportData(AllIncidentResourcesReportFilter filter ) throws PersistenceException;

   
   /**
    * Returns a collection of ICS209ReportData objects for the ics209 report.
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<ICS209ReportData> getICS209ReportData(ICS209ReportFilter filter) throws PersistenceException;
   public Collection<ICS209ReportData> getICS209ReportData2(ICS209ReportFilter filter) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public Collection<StrikeTeamTaskForceReportData> getStrikeTeamTaskForceReportData(Long incidentId, Collection<Long> resourceIds) throws PersistenceException;

   public Collection<QualificationsReportData> getQualificationsReportData(QualificationsReportFilter filter) throws PersistenceException;

   public Collection<ICS209ResourceData> getICS209ResourceData(ICS209ReportFilter filter) throws PersistenceException;

}
