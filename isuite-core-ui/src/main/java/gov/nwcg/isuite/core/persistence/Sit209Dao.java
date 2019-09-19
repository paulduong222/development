package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.filter.Sit209Filter;
import gov.nwcg.isuite.core.vo.Sit209Vo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface Sit209Dao extends TransactionSupport, CrudDao<Sit209> {
   
   /**
    * Retrieve a set of Sit209 VOs based on a set of search criterion
    * 
    * @param filter {@link Sit209Filter} with selected criterion
    * @return {@link Collection} of {@link Sit209Vo} objects.
    * @throws PersistenceException
    */
   public Collection<Sit209Vo> getGrid(Sit209Filter filter) throws PersistenceException;

   /**
    * Get a list of Sit209 Vo's based on the provided criteria.
    * @return {@link Collection} of {@link Sit209Vo}'s
    */
   public Collection<Sit209Vo> getPicklist() throws PersistenceException;

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
