package gov.nwcg.isuite.framework.core.persistence.hibernate;

import gov.nwcg.isuite.framework.core.domain.Transferable;
import gov.nwcg.isuite.framework.core.persistence.TransferableDao;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;

/**
 * Dao for objects that can be transferred between systems.
 * @author doug
 */
public class TransferableDaoHibernate<T extends Transferable> extends TransactionSupportImpl implements TransferableDao<T> {

//	private static final Log LOG = LogFactory.getLog(TransferableDaoHibernate.class);
   

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.persistence.TransferableDao#getByUniqueIdentity(java.lang.String)
    */
   @SuppressWarnings("unchecked")
public T getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException, DuplicateItemException {
		Criteria criteria = (Criteria) getHibernateSession().createCriteria(
				clazz.getCanonicalName());
		criteria.add(Expression.eq("transferable.identity", uniqueIdentity));
		return (T) criteria.uniqueResult();
   }
}
