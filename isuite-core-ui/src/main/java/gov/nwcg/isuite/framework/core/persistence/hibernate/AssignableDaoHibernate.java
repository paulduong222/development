package gov.nwcg.isuite.framework.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.framework.core.domain.Assignable;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.AssignableDao;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * Dao for objects that can be assigned to an organization.
 * @author doug
 *
 */
public class AssignableDaoHibernate<A extends Assignable> extends TransactionSupportImpl implements AssignableDao<A> {
//	private static final Log log = LogFactory.getLog(AssignableDaoHibernate.class);
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.AssignableDao#getAvailable(java.util.Collection, gov.nwcg.isuite.domain.Filter)
    */
   public Collection<A> getAvailable(Collection<WorkArea> workareas, Filter filter) throws PersistenceException, UnsupportedOperationException {
      // TODO Auto-generated method stub
	   throw new UnsupportedOperationException("This method not yet implemented.");
//      return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.AssignableDao#getByWorkAreaId(java.lang.Long, java.lang.Class)
    */
   public Collection<A> getByWorkAreaId(Long id, Class clazz) throws PersistenceException, UnsupportedOperationException  {
      // TODO Auto-generated method stub
	   throw new UnsupportedOperationException("This method not yet implemented.");
//      return null;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.AssignableDao#getByWorkAreaId(java.lang.Long, gov.nwcg.isuite.domain.Filter)
    */
   public Collection<A> getByWorkAreaId(Long workAreaId, Filter filter) throws PersistenceException,UnsupportedOperationException  {
      // TODO Auto-generated method stub
	   throw new UnsupportedOperationException("This method not yet implemented.");
//      return null;
   }
   
}
