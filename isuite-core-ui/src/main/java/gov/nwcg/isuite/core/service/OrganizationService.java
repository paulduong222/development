/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.service.PersistableService;
import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Collection;

/**
 * @author bsteiner
 *
 */
public interface OrganizationService extends  TransactionService {
   
   /**
    * Temporary stub in for Organizations.  This is needed for user use case for now.
    * @param filter
    * @return <code>Collection</code> of <code>Organization</code> objects.
    */
   public Collection<OrganizationVo> getOrganizations(OrganizationFilter filter) throws ServiceException;
   
//   /**
//    * This method translates a value object to a domain object.
//    * @param theVo
//    * @return
//    * @throws ServiceException
//    */
//   public Organization voToDo(OrganizationVo theVo) throws ServiceException;

   /**
    * Retrieve a set of Agencies based on filter criteria.  The AgencyFilter 
    * contains an eventTypeId.  If this value is set, the other filters are 
    * ignored and the DAO produces a Collection of Agencies that are specifically 
    * selected based on whether or not the eventTypeId is associated to a 
    * Wild Land fire or not.
    * 
    * @param filter
    * @return {@link Collection} of {@link AgencyVo} objects.
    * @throws ServiceException
    */
   public Collection<AgencyVo> getAgencies(AgencyFilter filter) throws ServiceException;
   
   /**
    * Retrieves a collection of organizations to use for picklists.  It only 
    * contains {@link OrganizationPicklistVo}s which just have id, name and unit code.
    * 
    * @param unitCode the first part of a unit code.
    * @param name the Organization Name.  e.g. "Newcastle Fire Protection District"
    * @return
    * @throws ServiceException
    */
   public Collection<OrganizationPicklistVo> getOrganizationPickList(String unitCode, String name) throws ServiceException;
   
   /**
    * Get a single {@link Organization} object
    * @param id Id of the {@link Organization} we want.
    * @return {@link OrganizationVo}
    * @throws ServiceException
    */
   public OrganizationVo getById(Long id) throws ServiceException;
   
//   /**
//    * @return A {@link Collection} of {@link OrganizationVo} objects that represent Dispatch Centers.
//    * @throws ServiceException
//    */
//   public Collection<OrganizationVo> getDispatchCenters() throws ServiceException;
}
