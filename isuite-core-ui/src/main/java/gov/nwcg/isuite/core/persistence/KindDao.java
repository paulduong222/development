package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.core.vo.OrganizationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author mgreen
 */

public interface KindDao extends TransactionSupport, CrudDao<Kind> {

   /**
    * Retrieve a {@link Collection} of {@link KindVo}s populated from filter.
    *  
    * @param filter
    * @return
    * @throws PersistenceException
    */
   public Collection<KindVo> getPicklist(KindFilter filter) throws PersistenceException;

   /**
    * @return
    * @throws PersistenceException
    */
   public Collection<Kind> getAll() throws PersistenceException;
   
   /**
    * @param vo
    * @return
    * @throws PersistenceException
    */
   public int getDuplicateCodeCount(KindVo vo) throws PersistenceException;

   public Collection<KindVo> getIncidentGroupKindDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException;
   
   public void executeInsertIncidentCostRateKind(Long kindId, String reqCatCode, Long incidentId) throws PersistenceException;
   
   public void executeInsertIncidentCostRateStateKind(Long kindId, String reqCatCode, Long incidentId) throws PersistenceException;
   
   public Collection<KindVo> getByIncidentId(Long incidentId) throws PersistenceException;

	public Collection<KindVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException;
}
