/**
 * 
 */
package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.OfficeType;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author doug
 */
public interface OfficeTypeDao extends TransactionSupport, CrudDao<OfficeType>{
   public Collection<OfficeType> getAll(Filter filter) throws PersistenceException;
}
