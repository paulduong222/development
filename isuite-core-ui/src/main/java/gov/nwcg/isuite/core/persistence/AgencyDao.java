package gov.nwcg.isuite.core.persistence;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.filter.AgencyFilter;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface AgencyDao extends TransactionSupport, CrudDao<Agency>{
   
   /**
    * Get a {@link Collection} of {@link Agency} objects based on the data in the {@link AgencyFilter}
    * @param filter
    * @return A {@link Collection} of {@link Agency} objects
    * @throws PersistenceException
    */
	public Collection<AgencyVo> getGrid(AgencyFilter filter) throws PersistenceException;
   
   /**
    * Get a {@link Collection} of {@link Agency} objects based on the data in the {@link AgencyFilter}
    * @param filter
    * @return A {@link Collection} of {@link Agency} objects
    * @throws PersistenceException
    */
	public Collection<AgencyVo> getAgencies(AgencyFilter filter) throws PersistenceException;
   
	
   /**
    * Get an {@link Agency} object based on it's agencyCode
    * @param agencyCode
    * @return A single {@link Agency} object
    * @throws PersistenceException
    */
   public Agency getByAgencyCode(String agencyCode) throws PersistenceException;
   
   /**
    * @param vo
    * @return
    * @throws PersistenceException
    */
   public int getDuplicateCodeCount(AgencyVo vo) throws PersistenceException;

   public Collection<AgencyVo> getIncidentGroupAgencyDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException;
 
   public Collection<AgencyVo> getByIncidentId(Long incidentId) throws PersistenceException;

	public Collection<AgencyVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException;

}
