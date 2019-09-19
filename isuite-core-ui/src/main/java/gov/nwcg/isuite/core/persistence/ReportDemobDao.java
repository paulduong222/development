package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import org.hibernate.SessionFactory;

import gov.nwcg.isuite.core.reports.data.ActualDemobReportData;
import gov.nwcg.isuite.core.reports.data.AirTravelRequestReportData;
import gov.nwcg.isuite.core.reports.data.AvailableForReleaseReportData;
import gov.nwcg.isuite.core.reports.data.CheckoutReportData;
import gov.nwcg.isuite.core.reports.data.DemobPlanningReportData;
import gov.nwcg.isuite.core.reports.data.GroundSupportReportData;
import gov.nwcg.isuite.core.reports.data.LastWorkDayReportData;
import gov.nwcg.isuite.core.reports.data.TentativeReleasePosterReportData;
import gov.nwcg.isuite.core.reports.filter.ActualDemobReportFilter;
import gov.nwcg.isuite.core.reports.filter.AirTravelRequestReportFilter;
import gov.nwcg.isuite.core.reports.filter.AvailableForReleaseReportFilter;
import gov.nwcg.isuite.core.reports.filter.CheckoutReportFilter;
import gov.nwcg.isuite.core.reports.filter.DemobPlanningReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroundSupportReportFilter;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.core.reports.filter.TentativeReleasePosterReportFilter;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

/**
 * Data access for Demob Reports.
 * 
 * @author kvelasquez
 */
public interface ReportDemobDao extends TransactionSupport{
	
	public void setSessionFactory(SessionFactory sf);
	
	
	/**
	 * Returns a collection of CheckoutReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<CheckoutReportData> getCheckoutReportData(CheckoutReportFilter filter, Long useGroupId) throws PersistenceException;
	
	/**
	 * Returns a collection of DemobPlanningReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<DemobPlanningReportData> getDemobPlanningReportData(DemobPlanningReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of TentativeReleasePosterReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<TentativeReleasePosterReportData> getTentativeReleasePosterReportData(TentativeReleasePosterReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of AvailableForReleaseReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<AvailableForReleaseReportData> getAvailableForReleaseReportData(AvailableForReleaseReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of AirTravelRequestReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<AirTravelRequestReportData> getAirTravelRequestReportData(AirTravelRequestReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of LastWorkDayReportData objects
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<LastWorkDayReportData> getLastWorkDayReportData(LastWorkDayReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of ActualDemobReportData objects
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ActualDemobReportData> getActualDemobReportData(ActualDemobReportFilter filter) throws PersistenceException;
	
	/**
	 * Returns a collection of GroundSupportReportData objects.
	 * 
	 * @param filter
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<GroundSupportReportData> getGroundSupportReportData(GroundSupportReportFilter filter) throws PersistenceException;

}
