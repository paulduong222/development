package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.KindGroup;
import gov.nwcg.isuite.core.filter.KindGroupFilter;
import gov.nwcg.isuite.core.vo.KindGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface KindGroupDao extends TransactionSupport, CrudDao<KindGroup> {
   
   /**
    * Retrieve a set of KindGroup VOs based on a set of search criterion
    * 
    * @param filter {@link KindGroupFilter} with selected criterion
    * @return {@link Collection} of {@link KindGroupVo} objects.
    * @throws PersistenceException
    */
   public Collection<KindGroupVo> getGrid(KindGroupFilter filter) throws PersistenceException;

   /**
    * Get a list of KindGroup Vo's based on the provided criteria.
    * @return {@link Collection} of {@link KindGroupVo}'s
    */
   public Collection<KindGroupVo> getPicklist() throws PersistenceException;

   /**
    * Tests to see if the newly entered code is unique
    * @param code the newly added code
    * @return true if unique, false if otherwise
    * @throws PersistenceException
    */
   public Boolean isCodeUnique(String code) throws PersistenceException;
}
