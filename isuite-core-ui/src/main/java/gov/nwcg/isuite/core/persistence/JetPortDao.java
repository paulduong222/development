package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.filter.JetPortFilter;
import gov.nwcg.isuite.core.vo.AgencyVo;
import gov.nwcg.isuite.core.vo.JetPortVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface JetPortDao extends TransactionSupport, CrudDao<JetPort> {

   /**
    * Retrieve a set of JetPort VOs based on a set of search criterion
    * 
    * @param filter {@link JetPortFilter} with selected criterion
    * @return {@link Collection} of {@link JetPortVo} objects.
    * @throws PersistenceException
    */
   public Collection<JetPortVo> getGrid(JetPortFilter filter) throws PersistenceException;

   /**
    * Get a list of JetPort Vo's based on the provided criteria.
    * @return {@link Collection} of {@link JetPortVo}'s
    */
   public Collection<JetPortVo> getPicklist() throws PersistenceException;

   /**
    * Tests to see if the newly entered code is unique
    * @param code the newly added code
    * @return true if unique, false if otherwise
    * @throws PersistenceException
    */
   public Boolean isCodeUnique(String code) throws PersistenceException;

   /**
    * @param vo
    * @return
    * @throws PersistenceException
    */
   public int getDuplicateCodeCount(JetPortVo vo) throws PersistenceException;

	public Collection<JetPortVo> getIncidentGroupJetPortDuplicates(Long incidentGroupId, Collection<Long> incidentIds) throws PersistenceException;

	public Collection<JetPortVo> getNonStdJetports(Long incidentGroupId, Long incidentId) throws PersistenceException;

	public Collection<JetPortVo> getStandardAndNonStandard(Collection<Long> incidentIds, Long incidentGroupId) throws PersistenceException;
}
