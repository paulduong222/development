/**
 * Persistence of Organizations
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.core.vo.OrganizationPicklistVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * Persists organizations.
 * <p>
 * Provides for the basic CRUD operations by specifying the type in the
 * <code>CurdDao<T></code>. Also adds more navigation methods.
 * </p>
 * 
 * @author doug
 * 
 */
public interface OrganizationDao extends TransactionSupport, CrudDao<Organization> {

	/**
	 * Return all organizations.
	 * <p>
	 * This method returns all organizations in a preorder depth-first
	 * traversal. For example given the following: <code>
	 *        J
	 *       /  \
	 *      /    \
	 *     K      L
	 *    / \    / \
	 *   N   O  P   Q
	 * </code>
	 * The following would be returned: <code> J, K, N, O, L, P, Q </code>
	 * </p>
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Organization> getAllDepthFirst()
			throws PersistenceException;

	/**
	 * Return all organizations.
	 * <p>
	 * This method returns all organizations in a breadth-first order. For
	 * example given the following: <code>
	 *        J
	 *       /  \
	 *      /    \
	 *     K      L
	 *    / \    / \
	 *   N   O  P   Q
	 * </code>
	 * The following would be returned: <code> J, K, L, N, O, P, Q </code>
	 * </p>
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<Organization> getAllBreadthFirst()
			throws PersistenceException;
   
   public Collection<OrganizationPicklistVo> getOrganizationPickList(String unitcode, String name) throws PersistenceException;

   /**
    * Retrieves Organizations by the filter provided.
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<Organization> getAll(OrganizationFilter filter) throws PersistenceException;
   
   /**
    * Retrieve all organizations by their id's.
    * 
    * @param ids 
    * @return
    * @throws PersistenceException
    */
   public Collection<Organization> getAllByIds(Collection<Long> ids) throws PersistenceException;
   
   /**
    * This method returns an organization from the 
    * database based on it's abbreviated organization code.
    * @param organizationCode
    * @return Organization
    * @throws PersistenceException
    */
   public Organization getByOrganizationCode(String organizationCode) throws PersistenceException;
   
   
   /**
    * This method returns a list of organizations from the 
    * database based on the provided workAreaId.
    * @param workAreaId
    * @return Collection<Organization>
    * @throws PersistenceException
    */
   public Collection<Organization> getByWorkAreaId(Long workAreaId) throws PersistenceException;
   
   
   
   /**
    * This method returns the primary dispatch center organization based
    * on the organization id that is passed in.
    * 
    * @param organizationId
    * @return {@link OrganizationImpl}
    * @throws PersistenceException
    */
   public Organization getPrimaryDispatchCenter(Long organizationId) throws PersistenceException;
   
   /**
    * 
    * @param userId
    * @return
    * @throws PersistenceException
    */
   public Collection<OrganizationPicklistVo> getAllOrganizationsByUserId(Long userId) throws PersistenceException;
  
   /**
    * @return A {@link Collection} of {@link OrganizationVo} objects that represent Dispatch Centers.
    * @throws PersistenceException
    */
   public Collection<OrganizationVo> getDispatchCenters() throws PersistenceException;
   
   /**
    * @param vo
    * @return
    * @throws PersistenceException
    */
   public int getDuplicateCodeCount(OrganizationVo vo) throws PersistenceException;

  public Collection<OrganizationVo> getIncidentGroupOrganizationDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException;

	public String getUnitCodeByTi(String ti) throws PersistenceException;  

	public Long getOrgIdByTi(String ti) throws PersistenceException;

	public Collection<OrganizationVo> getByIncidentId(Long incidentId) throws PersistenceException;
	
	public Collection<OrganizationVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException;
}
