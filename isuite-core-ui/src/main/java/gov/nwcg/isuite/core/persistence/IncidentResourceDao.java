package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.filter.IncidentResourceFilter;
import gov.nwcg.isuite.core.reports.data.AllIncidentResourcesReportData;
import gov.nwcg.isuite.core.reports.filter.AllIncidentResourcesReportFilter;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.EarliestDateVo;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCheckInDemobVo;
import gov.nwcg.isuite.core.vo.IncidentResourceCommonVo;
import gov.nwcg.isuite.core.vo.IncidentResourceGridVo;
import gov.nwcg.isuite.core.vo.IncidentResourceTimeDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceVo;
import gov.nwcg.isuite.core.vo.ResourceAssignmentVo;
import gov.nwcg.isuite.core.vo.ResourceCostVo;
import gov.nwcg.isuite.core.vo.ResourceDataForAccrualVo;
import gov.nwcg.isuite.core.vo.ResourceInventoryGridVo;
import gov.nwcg.isuite.core.vo.ResourceTimeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 * Data Access for Incident_Resources.
 *  
 * @author bsteiner
 */
public interface IncidentResourceDao extends TransactionSupport, CrudDao<IncidentResource>{
   

	/**
	 * Returns collection of IncidentResourceGridVo's for the filter criteria supplied.
	 * 
	 * @param filter
	 * 			the filter criteria to search by
	 * @return
	 * 			collection of incidentresourcegridvo's to return
	 * @throws PersistenceException
	 */
	public Collection<IncidentResourceGridVo> getGrid(IncidentResourceFilter filter, Collection<String> sortFields) throws PersistenceException;

	public Collection<IncidentResourceGridVo> getGrid2(IncidentResourceFilter filter, Collection<String> sortFields) throws PersistenceException;
	

   /**
    * Retrieves the IncidentResourceCommonVo for a specific Resource.
    * 
    * @param resourceId 
    * 		the Resource id
    * @param assignmentId
    * 		the Assignment Id
    * @return 
    * 		IncidentResourceCommonVo
    * @throws PersistenceException
    */
   public IncidentResourceCommonVo getIncidentResourceCommonData(Long resourceId,Long assignmentId) throws PersistenceException;

   /**
    * Retrieves the CheckinDemobData for a specific Resource Assignment
    * 
    * @param incidentResourceId 
    * 			the incident resource id
    * @return 
    * 		IncidentResourceCheckinDemobVo
    * @throws PersistenceException
    */
   public IncidentResourceCheckInDemobVo getIncidentResourceCheckInDemobData(Long incidentResourceId) throws PersistenceException;

   /**
    * Disables the resources by setting the is_enabled flag to false.
    * 
    * Optionally removes the parent associations by setting the parent_id to null.
    * 
    * @param resourceIds
    * 			collection of resource.id's to disable
    * @param removeParentAssociations
    * 			flag to indicate whether or not the parent associations should be removed
    * @return
    * 		the number of resources disabled
    * @throws PersistenceException
    */
   public int disableResources(Collection<Long> resourceIds, boolean removeParentAssociations) throws PersistenceException;
   
   /**
    * Enables the resources by setting the is_enabled flag to true.
    * 
    * @param resourceIds
    * 			collection of resource.id's to enable
    * @return
    * 		the number of resources enabled
    * @throws PersistenceException
    */
   public int enableResources(Collection<Long> resourceIds) throws PersistenceException;
   
   /**
    * Retrieves the ResourceTimeVo for a specific Resource Assignment
    * @param id Resource Assignment id
    * @return ResourceTimeVo
    * @throws PersistenceException
    */
   public ResourceTimeVo getResourceTimeData(Long id) throws PersistenceException;

   /**
    * Retrieves the ResourceCostVo for a specific Resource Assignment
    * @param id Resource Assignment id
    * @return ResourceCostVo
    * @throws PersistenceException
    */
   public ResourceCostVo getResourceCostData(Long id) throws PersistenceException;

   /**
    * Retrieves the (complete) ResourceAssignmentVo for a specific Resource Assignment
    * @param id Resource Assignment id
    * @return ResourceAssignmentVo
    * @throws PersistenceException
    */
   public ResourceAssignmentVo getResourceAssignmentData(Long id) throws PersistenceException;
   
   /**
    * Removes all Kind Codes from the qualifications list given specified ids.
    * @param ids The list of Kind Code Ids marked for deletion.
    * @throws PersistenceException
    */
   public void deleteQualifications(Collection<Long> ids) throws PersistenceException;

   /**
    * Saves a list of qualifications (Kind Codes) based upon list of ids
    * @param ids
    * @throws PersistenceException
    */
   public void saveQualifications(Collection<Long> ids) throws PersistenceException;
   
   /**
    * Returns a collection of AllIncidentResourcesReportData objects.
    * 
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<AllIncidentResourcesReportData> getAllIncidentResourcesReportData(AllIncidentResourcesReportFilter filter ) throws PersistenceException;

   public Collection<IncidentResourceGridVo> getAvailableResourcesForRoster(IncidentResourceFilter filter) throws PersistenceException;

   public Collection<IncidentResourceGridVo> getAvailableWorkAreaResourcesForRoster(IncidentResourceFilter filter) throws PersistenceException;

   public Collection<IncidentResourceGridVo> getRosterResourceGrid(IncidentResourceFilter filter) throws PersistenceException;

	public Collection<IncidentQuestionVo> getIncidentQuestions(Long incidentId) throws PersistenceException;

	public void unrosterResources(Collection<Long> ids) throws PersistenceException;

	public Collection<IncidentResourceGridVo> checkRequestNumber(Long workAreaId, IncidentResourceVo vo) throws PersistenceException;

	public Collection<IncidentResourceGridVo> getStrikeTeams(Long incidentId,Collection<Long> incidentIds) throws PersistenceException;
   
   /**
    * 
    * @param incidentId
    * @return {@link Collection} of {@link IncidentResourceVo} objects.
    * @throws PersistenceException
    */
	public Collection<IncidentResourceVo> getByIncidentId(Long incidentId) throws PersistenceException;
   
	public void updateCheckOutFormPrinted(Collection<Long> ids) throws PersistenceException;

	public void updateTentReleaseDispathNotified(Collection<Long> ids) throws PersistenceException;
	
	public void updateActualReleaseDispathNotified(Collection<Long> ids) throws PersistenceException;
	
   /**
    * 
    * @param incidentId
    * @param resourceId
    * @return
    * @throws PersistenceException
    */
	public Long getIncidentResourceIdByIncidentIdAndResourceId(Long incidentId, Long resourceId) throws PersistenceException;

   /**
    * @param parentResourceId
    * @throws PersistenceException
    */
	public Long assignmentTimeRecordCount(Long parentResourceId) throws PersistenceException ;

   /**
    * @param parentResourceId
    * @throws PersistenceException
    */
	public void insertAssignmentTime(Long parentResourceId) throws PersistenceException ;

   /**
    * @param type
    * @param parentResourceId
    * @throws PersistenceException
    */
	public void updateCrewEmploymentType(String type, Long parentResourceId) throws PersistenceException ;

   /**
    * @param parentResourceId
    * @throws PersistenceException
    */
	public void updateCrewAddresses(Long incidentResourceId, Long parentResourceId) throws PersistenceException;

   /**
    * @param parentResourceId
    * @throws PersistenceException
    */
	public void clearCrewAddresses(Long parentResourceId) throws PersistenceException;   

	public void clearNonContractorEmploymentType(Long id) throws PersistenceException;

	public void updateAirTravelDispatchNotified(Collection<Long> ids) throws PersistenceException;

	public Collection<IncidentResourceVo> getNonInvoicedIncidentResourcesById(Long incidentResourceId, Date startDate) throws PersistenceException, ServiceException;

	public Collection<ResourceInventoryGridVo> getUnassignedInventoryResources(String dispatchCenter,Long userId) throws PersistenceException ;

	public Collection<Long> getCrewUpdateIds(Long parentResourceId) throws PersistenceException,Exception;

	public void updateCostDataUseActuals(Long incidentResourceId, Boolean val) throws PersistenceException;

	public IncidentResource getByAssignmentTimeId(Long atId) throws PersistenceException ;

	public IncidentResource getByAssignmentTimePostId(Long atpId) throws PersistenceException ;
	
	public Collection<IncidentResource> getTopLevelResources(Long incidentId) throws PersistenceException ;

	public Collection<IncidentResource> getTopLevelResourcesIG(Long incidentGroupId) throws PersistenceException;

	public void updateDefaultCostGroup(Long costDataId, Long cgId) throws PersistenceException;

	public void updateDefaultShift(Long costDataId, Long shiftId) throws PersistenceException;

	public Collection<IncidentResource> getAllByIncidentId(Long incidentId, Boolean excludeChildren) throws PersistenceException ;

	public Collection<Long> getIncidentResourceChildrenIds(Long incidentResourceId) throws PersistenceException;

	public Collection<IncidentResourceGridVo> getReportResourceData(Long incidentId, Long incidentGroupId) throws PersistenceException ;	

	public void deleteAdPaymentInfo(Long id) throws PersistenceException;

	public void updateOtherRate(Long assignmentTimeId, BigDecimal otherRate) throws PersistenceException ;

	public void clearContractorInfo(Long assignmentTimeId) throws PersistenceException ;	

	public Collection<Long> getTopLevelResourceIds(Long incidentId) throws PersistenceException ;

	public Date getParentAssignDate(Long parentResourceId,Long incidentId) throws PersistenceException ;

	public void updateCostAssignDate(Long resourceId, Long incidentId, Date assignDate) throws PersistenceException ;

	public IncidentResource getByResourceId(Long resourceId) throws PersistenceException;

	public Collection<IncidentResource> getValidResourcesIdsForCost(Long incidentId) throws PersistenceException;

	public Collection<IncidentResource> getAllByIncidentId(Long incidentId) throws PersistenceException;

	public Collection<CostResourceDataVo> getCostResourceData(Long incidentResourceId,Long incidentId,Long incidentGroupId) throws PersistenceException;

	public EarliestDateVo getEarliestDatesByIncResId(Long irId) throws PersistenceException ;
	
	public void updateCostAssignDate2(Long costDataId, Date assignDate) throws PersistenceException ;

	public void updateChildGenCostsTrue(Long parentResourceId) throws PersistenceException;
	
	public Collection<IncidentAccountCodeVo> getIncidentGroupAccountCodes(Long incidentGroupId) throws PersistenceException;

	public Collection<IncidentResourceGridVo> getIapResources(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public void persistSqls(Collection<String> sqls) throws PersistenceException;

	public Collection<ResourceDataForAccrualVo> getResourceDataForAccrual(Long incidentId) throws PersistenceException;
	
	public int getUnlockedCostRecordCount(Long irId) throws PersistenceException;

	public void updateDailyCostExceptions(Long incidentId, Long incidentGroupId) throws PersistenceException;
	
	public Object[] getResourceTimeCostRecordCount(Collection<Long> incidentResourceIds) throws PersistenceException;
	
	public void setResourceDefIAC(Long iacId, Long incidentId) throws PersistenceException;

	public Long getIdByResourceId(Long id ) throws PersistenceException;

	public Long getParentResourceId(Long irid ) throws PersistenceException;

	public Long updateResNumId(Long irId) throws PersistenceException;

	public Long updateResNumId2(Long irId) throws PersistenceException;
	
	public void resetResNumId(Long irId) throws PersistenceException;

	public void cleanUpDuplicateQuestionIssue(Long incidentResourceId) throws PersistenceException;

	public void updateCrewHireInfo(String name, String phone, String fax, Long parentResourceId) throws PersistenceException;
	
	public Collection<IncidentResourceTimeDataVo> getIncidentResourceTimeDataVos(Long parentIncidentResourceId, Boolean subsOnly,Date postDate) throws PersistenceException;
	
}

