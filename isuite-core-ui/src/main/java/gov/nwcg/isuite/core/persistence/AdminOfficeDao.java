/**
 * Persistence of AdminOffice
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AdminOffice;
import gov.nwcg.isuite.core.domain.ContractorAgreement;
import gov.nwcg.isuite.core.filter.AdminOfficeFilter;
import gov.nwcg.isuite.core.vo.AdminOfficeGridVo;
import gov.nwcg.isuite.core.vo.AdminOfficeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * Persists organizations.
 * <p>
 * Provides for the basic CRUD operations by specifying the type in the
 * <code>CurdDao<T></code>. Also adds more navigation methods.
 * </p>
 * 
 */
public interface AdminOfficeDao extends TransactionSupport, CrudDao<AdminOffice> {

	public void merge(AdminOffice adminOffice) throws PersistenceException;
	
	public void save(AdminOffice adminOffice) throws PersistenceException,DuplicateItemException;
	
	/**
	* Saves or updates a {@link Collection} of {@link AdminOffice} objects.
	* @param users {@link Collection} of {@link AdminOffice} objects to be saved or updated.
	* @throws PersistenceException
	*/
	public void saveAll(Collection<AdminOffice> adminOfficeList) throws PersistenceException;
	
	/**
	* Retrieve a {@link Collection} of {@link AdminOfficeGridVo} objects based on the
	* provided filter.
	* @param filter {@link AdminOfficeFilter}
	* @return A {@link Collection} of {@link AdminOfficeGridVo} objects
	* @throws PersistenceException
	*/
	public Collection<AdminOfficeGridVo> getGrid(AdminOfficeFilter filter) throws PersistenceException;
	   
	   /**
	* Retrieve a single {@link AdminOffice}
	* @param id
	* @return a single {@link AdminOffice}
	* @throws PersistenceException
	*/
	public AdminOffice getById(Long id) throws PersistenceException;

	/**
	 * @param officeName
	 * @return
	 * @throws PersistenceException
	 */
	public AdminOffice getByAdminOfficeName(String officeName) throws PersistenceException;
	
	/**
	 * @return A {@link Collection} of {@link AdminOfficeVo} objects
	 * @throws PersistenceException
	 */
	public Collection<AdminOfficeVo> getPicklist() throws PersistenceException;
	
	/**
	 * @param adminOfficeId
	 * @return
	 * @throws PersistenceException
	 */
	public Collection<ContractorAgreement> getIncludedInOriginalInvoice(Long adminOfficeId) throws PersistenceException;

 }
