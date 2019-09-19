package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.filter.ResourceFilter;
import gov.nwcg.isuite.core.filter.WorkAreaFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentSelectorVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.core.vo.ResourceGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaGridVo;
import gov.nwcg.isuite.core.vo.WorkAreaPicklistVo;
import gov.nwcg.isuite.core.vo.WorkAreaResourceGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * DAO for Work Areas
 * 
 * @author bsteiner
 */
public interface WorkAreaDao extends TransactionSupport, CrudDao<WorkArea> {
   
   /**
    * Retrieve a set of Grid VOs based on a set of search criterion
    * 
    * @param filter {@link WorkAreaFilter} with selected criterion
    * @return {@link Collection} of {@link WorkAreaGridVo} objects.
    * @throws PersistenceException
    */
   public Collection<WorkAreaGridVo> getGrid(WorkAreaFilter filter) throws PersistenceException;

   public Collection<WorkAreaResourceGridVo> getResourceGrid(WorkAreaResourceFilter filter) throws PersistenceException;
   
   /**
    * Retrieve a set of Picklist VOs based on a set of search criterion
    * 
    * @param filter {@link WorkAreaFilter} with selected criterion
    * @return {@link Collection} of {@link WorkAreaPicklistVo} objects.
    * @throws PersistenceException
    */
   public Collection<WorkAreaPicklistVo> getPicklist(WorkAreaFilter filter) throws PersistenceException;

   /**
    * Retrieve all resources associated to the provided work area id.
    * 
    * @param workAreaId the work area's id
    * @return a {@link Collection} of {@link Resource}s.
    * 
    * @throws PersistenceException
    */
   public Collection<ResourceGridVo> getResourcesByWorkAreaId(Long workAreaId) throws PersistenceException;
   
   /**
    * 
    * @param filter {@link IncidentFilter}
    * @param userId
    * @return {@link Collection} of {@link IncidentGridVo} objects.
    * @throws PersistenceException
    */
   public Collection<IncidentGridVo> getWorkAreaIncidents(IncidentFilter filter, Long userId) throws PersistenceException;
   
   /**
    * Return only the needed data for the WorkAreaIncidentSelector.incidentSelector component.
    * @param workAreaId
    * @return {@link Collection} of {@link IncidentSelectorVo} objects.
    * @throws PersistenceException
    */
   public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(Long workAreaId) throws PersistenceException;

   public Collection<IncidentSelectorVo> getWorkAreaIncidentSelectorData(IncidentFilter filter) throws PersistenceException,Exception ;
   
   /**
    * Retrieves all resources associated to the provided work area id.
    * 
    * @param filter the {@link ResourceFilter} populated with criteria
    * @return a {@link Collection} of {@link ResourceGridVo}s.
    * @throws PersistenceException
    */
   public Collection<WorkAreaResourceGridVo> getWorkAreaResources(WorkAreaResourcesFilter filter) throws PersistenceException;
   
   /**
    * Retrieve all incidents NOT associated to the provided work area id.
    * 
    * @param filter the {@link IncidentFilter} populated with criteria.
    * @param privilegedUser
    * @return a {@link Collection} of {@link IncidentGridVo}s.
    * 
    * @throws PersistenceException
    */
   public Collection<IncidentGridVo> getAvailableWorkAreaIncidents(IncidentFilter filter, Boolean privilegedUser) throws PersistenceException;
   
   /**
    * Retrieve all resources NOT associated to the provided work area id.
    * 
    * @param filter the {@link ResourceFilter} populated with criteria.
    * @return a {@link Collection} of {@link ResourceGridVo}s.
    * 
    * @throws PersistenceException
    */
   public Collection<WorkAreaResourceGridVo> getAvailableWorkAreaResources(WorkAreaResourcesFilter filter) throws PersistenceException;
   
   /**
    * Removes the association of Incidents from a Work Area.
    * 
    * @param incidentIds Collection of Incident Ids.
    * @param workAreaId Work Area Id
    * @throws PersistenceException
    */
   public void removeIncidentsFromWorkArea(Collection<Long> incidentIds, Long workAreaId) throws PersistenceException;
   
   /**
    * Removes the association of Resources from a work area.
    * 
    * @param resourceIds
    * @param workAreaId
    * @throws Persistenceexception
    */
   public void removeResourcesFromWorkArea(Collection<Long> resourceIds, Long workAreaId) throws PersistenceException;
   
   /**
    * This method is used to fetch the list of workAreas the user can
    * switch to operating in when logged into the system.
    * @param userId
    * @throws PersistenceException
    */
   public Collection<WorkAreaPicklistVo> getWorkAreaPickListByUserId(Long userId) throws PersistenceException;
   
   /**
    * Determine if The Work Area Name is Unique for the given User.
    * 
    * @param waId
    * @param waName
    * @param uName
    * @return true if the work area name is unique.
    * @throws PersistenceException
    */
   public boolean isWorkAreaNameUniqueToUser(Long waId, String waName, Long userId) throws PersistenceException;

   public boolean isWorkAreaNameUniqueStandard(String waName) throws PersistenceException;

   public void addResourceToWorkArea(Long workAreaId, Long resourceId) throws PersistenceException;

   public Collection<Resource> getAvailableRosterResources(Long workAreaId, Long resourceId) throws PersistenceException;

   public Collection<WorkAreaResourceGridVo> getUnassignedResources(WorkAreaResourceFilter filter) throws PersistenceException;

   public Collection<OrganizationVo> getAssociatedUserOrganizations(Long userId) throws PersistenceException;

   public Collection<WorkArea> getWorkAreasForUser(Long id) throws PersistenceException;

   public Collection<WorkArea> getWorkAreasForUser(WorkAreaFilter filter) throws PersistenceException;
   
   public WorkAreaGridVo getGridItem(Long userId, Long workAreaId) throws PersistenceException;
   
   /**
    * 
    * @param standardOrganizationId
    * @return
    * @throws PersistenceException
    */
   public Long getWorkAreaIdByStandardOrganizationId(Long standardOrganizationId) throws PersistenceException;
   
   /**
    * 
    * @param standardOrganizationId
    * @return
    * @throws PersistenceException
    */
   public WorkArea getByStandardOrganizationId(Long standardOrganizationId) throws PersistenceException;
   
   public Long getResourceIdInWorkArea(Long workAreaId, Long resourceId) throws PersistenceException;
 
   public Long getPermanentResourceIdInWorkArea(Long workAreaId, Long resourceId) throws PersistenceException;
 
   public void updateRosterChildrenLeaderType(Long parentResourceId, Long excludeChildId, Integer fromType, Integer toType) throws PersistenceException ;
 
   public void syncUserRestrictedWorkArea(Long userId) throws PersistenceException;
   
   public void updateSharedOutFlag() throws PersistenceException;
   
   /**
    * Deletes all work area organizations not in the user's organizations.
    * 
    * @param userId
    * @throws PersistenceException
    */
   public void removeWorkAreaOrgsNotInUserOrgs(Long userId) throws PersistenceException;
   
   /**
    * Deletes all work area incidents not in the user's organizations.
    * 
    * @param userId
    * @throws PersistenceException
    */
   public void removeWorkAreaIncidentsNotInUserOrgs(Long userId) throws PersistenceException;
   
   /**
    * Deletes all work area resources not in the user's organizations.
    * 
    * @param userId
    * @throws PersistenceException
    */
   public void removeWorkAreaResourcesNotInUserOrgs(Long userId) throws PersistenceException;
 
   public void removeWorkAreaIncidentAssociation(Long pdcId, Long incidentId) throws PersistenceException;

   public void addWorkAreaIncidentAssociation(Long pdcId, Long incidentId) throws PersistenceException;
   
}