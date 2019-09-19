package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AgencyGroup;
import gov.nwcg.isuite.core.filter.AgencyGroupFilter;
import gov.nwcg.isuite.core.vo.AgencyGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface AgencyGroupDao extends TransactionSupport, CrudDao<AgencyGroup> {
   
   /**
    * Retrieve a set of AgencyGroup VOs based on a set of search criterion
    * 
    * @param filter {@link AgencyGroupFilter} with selected criterion
    * @return {@link Collection} of {@link AgencyGroupVo} objects.
    * @throws PersistenceException
    */
   public Collection<AgencyGroupVo> getGrid(AgencyGroupFilter filter) throws PersistenceException;

   /**
    * Get a list of AgencyGroup Vo's based on the provided criteria.
    * @return {@link Collection} of {@link AgencyGroupVo}'s
    */
   public Collection<AgencyGroupVo> getPicklist() throws PersistenceException;

   /**
    * Tests to see if the newly entered code is unique
    * @param code the newly added code
    * @return true if unique, false if otherwise
    * @throws PersistenceException
    */
   public Boolean isCodeUnique(String code) throws PersistenceException;
   
   /**
    * @param code
    * @param excludeId
    * @return
    * @throws PersistenceException
    */
   public int getDuplicateCodeCount(String code, Long excludeId) throws PersistenceException;
}
