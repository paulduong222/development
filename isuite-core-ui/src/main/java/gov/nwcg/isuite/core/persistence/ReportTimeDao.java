package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.ScratchReportTime;
import gov.nwcg.isuite.core.filter.impl.TimeReport2FilterImpl;
import gov.nwcg.isuite.core.reports.data.CrewRosterReportData;
import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsSubReportData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportAgencyFaxResourceData;
import gov.nwcg.isuite.core.reports.data.PersonnelTimeReportData;
import gov.nwcg.isuite.core.reports.data.ResourceDateData;
import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.core.reports.data.VendorResourceSummaryReportData;
import gov.nwcg.isuite.core.reports.filter.MissingDaysOfPostingsReportFilter;
import gov.nwcg.isuite.core.reports.filter.PersonnelTimeReportFilter;
import gov.nwcg.isuite.core.reports.filter.ShiftsInExcessOfStandardHoursReportFilter;
import gov.nwcg.isuite.core.reports.filter.SummaryHoursWorkedReportFilter;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.reports.filter.WorkRestRatioReportFilter;
import gov.nwcg.isuite.core.vo.ReportAgencySelect;
import gov.nwcg.isuite.core.vo.ReportSelectVo;
import gov.nwcg.isuite.core.vo.TimeReportData2Vo;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;

public interface ReportTimeDao extends TransactionSupport {

   public void setSessionFactory(SessionFactory sf);
   
   /**
    * 
    * @param incidentId
    * @param incidentGroupId
    * @return
    * @throws PersistenceException
    */
   public Collection<ReportAgencySelect> getAgencyResourcesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo Crew names for the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getCrewNamesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
  
   
   /**
    * 
    * @param incidentId
    * @param incidentGroupId
    * @return {@link Collection} of ReportSelectVo Crew names for the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getCrewRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo Person Resource names for
    *         the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of contracted ReportSelectVo Person Resource names for
    *         the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of non-contracted ReportSelectVo Person Resource names for
    *         the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getNonContractedPersonResourceNamesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo with Request Numbers for the selected {@link Incident} Person resources
    * @throws PersistenceException 
    */
   public Collection<ReportSelectVo> getPersonRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo with Request Numbers for the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo with non-contracted Request Numbers for the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getNonContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of ReportSelectVo with contracted Request Numbers for the selected {@link Incident}
    * @throws PersistenceException
    */
   public Collection<ReportSelectVo> getContractedRequestNumbersForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;
      
   /**
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<VendorResourceSummaryReportData> getVendorResourceSummaryReportData(VendorResourceSummaryReportFilter filter) throws PersistenceException;
   
   /**
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<MissingDaysOfPostingsSubReportData> getDaysOfPostingsReportData(MissingDaysOfPostingsReportFilter filter, Long incidentId) throws PersistenceException;
   
   /**
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<MissingDaysOfPostingsSubReportData> getUniqueDaysOfPostingsReportData(MissingDaysOfPostingsReportFilter filter, Long incidentId) throws PersistenceException;
   
   
   /**
    * 
    * @param filter
    * @param incidentId
    * @return
    * @throws PersistenceException
    */
   public Collection<PersonnelTimeReportData> getPersonnelTimeReportData(PersonnelTimeReportFilter filter, Long incidentId) throws PersistenceException;
   
   /**
    * return a list of invoices named like the submitted value
    * 
    * @param invoiceIdSub
    * @return
    * @throws PersistenceException
    */
   public  Collection<String> getPreviousTimeInvoicesNames(String invoiceIdSub) throws PersistenceException;
   
   /**
    * 
    * @param filter {@link TimeReportFilter}
    * @return
    * @throws PersistenceException
    */
   public BigDecimal getResourceId(TimeReportFilter filter) throws PersistenceException;


   /**
    * Return the number of times this invoice_number has been printed
    * 
    * @param invoiceId
    * @return
    * @throws PersistenceException
    */
   public String getTimeInvoiceNameCount(String invoiceId) throws PersistenceException;

   /**
    * Return the number of times this invoice_number like invoiceId has been printed, plus 1
    * 
    * @param invoiceId
    * @return
   * @throws PersistenceException 
    * @throws PersistenceException
    */
  public Integer getTimeInvoiceLikeNameCount(String invoiceId) throws PersistenceException;

  /**
   * Return Collection<ReportSelectVo> with reportId and print date as label
   * 
   * @param incidentId
   * @param incidentGroupId
   * @return
 * @throws PersistenceException 
   */
  public Collection<ReportSelectVo> getAvailableForReleaseReprintListForIncident(Long incidentId, Long incidentGroupId) throws PersistenceException;

  /**
   * Return a list of Time Invoices for the specified resource id
   * 
   * @param resourceId
   * @return Collection<TimeInvoice>
   * @throws PersistenceException 
   * @throws HibernateException 
   */
  //public Collection<TimeInvoice> getResourceInvoiceList(Long resourceId) throws HibernateException, PersistenceException;
  
  /**
   * Method used to retrieve ScratchReportTime records for WorkRestRatio report 
   * using a transaction Id
   */
  public Collection<ScratchReportTime> getWorkRestRatioReportData(Long transactionId, WorkRestRatioReportFilter filter) 
	throws PersistenceException;
 
  /**
   * Method used to remove Report Time related scratch records.
   * @param transactionId
   * @return
   * @throws PersistenceException
   */
  public boolean deleteReportTimeScratchData(Long transactionId) throws PersistenceException;
  
  /**
   * 
   * @param filter
   * @return
   * @throws PersistenceException
   */
  public List<ResourceDateData> getSummaryHoursWorkedReportData(SummaryHoursWorkedReportFilter filter) 
  				throws PersistenceException;
  
  /**
   * 
   * @param filter
   * @throws PersistenceException
   */
  public void insertSummaryHoursWorkedReportDataToScratchTable(SummaryHoursWorkedReportFilter filter) 
  				throws PersistenceException;

  /**
   * 
   * @param filter
   * @return
   * @throws PersistenceException
   */
  public Long insertWorkRestRatioReportDataToScratchTable(WorkRestRatioReportFilter filter) 
  				throws PersistenceException;
  
   
  public Collection<ReportSelectVo> getContractedResourcesForSelectedIncident(Long incidentId, Long incidentGroupId) throws PersistenceException ;
  
  /**
   * Method to return crew roster report data based on input values in TimeReportFilter
   * @param filter
   * @return
   * @throws PersistenceException
   */
  public Collection<CrewRosterReportData> getCrewRosterReportData(TimeReportFilter filter) 
	throws PersistenceException;
  
  public Collection<ShiftsInExcessOfStandardHoursReportData> getShiftsInExcessOfStandardHoursReportData2(ShiftsInExcessOfStandardHoursReportFilter filter) throws PersistenceException;
  
  public Long insertShiftsInExcessReportDataToScratchTable(ShiftsInExcessOfStandardHoursReportFilter filter) 
  		throws PersistenceException;
  
  public Collection<ScratchReportTime> getShiftsInExcessOfStandardHoursReportDetails2(
			Long incidentId,
			Long transactionId,
			ShiftsInExcessOfStandardHoursReportFilter filter) 
		throws PersistenceException;

  public Collection<PersonnelTimeReportAgencyFaxResourceData> getAgencyTreeDataForPersonnelTimeRep(
			Long incidentOrGroupId, boolean isIncidentGroup) throws PersistenceException;
  
 public Collection<TimeReportData2Vo> getTimeReportData(TimeReport2FilterImpl filter) throws PersistenceException;
}
