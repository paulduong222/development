package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Url;
import gov.nwcg.isuite.core.domain.impl.UrlImpl;
import gov.nwcg.isuite.core.persistence.UrlDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;

/**
 * Dao for url (pages).
 * @author bsteiner
 * 
 */
public class UrlDaoHibernate extends TransactionSupportImpl implements UrlDao {
   // private static final Log LOG =
   // LogFactory.getLog(UrlDaoHibernate.class);

	/**
	 * Get all ISW_URL_ROLE records
	 * @return <code>Collection</code> of <code>Url</code> objects
	 */
	@SuppressWarnings("unchecked")
   public Collection<Url> getAll() throws PersistenceException {
		Criteria criteria = (Criteria) getHibernateSession().createCriteria(
				UrlImpl.class);
		return criteria.list();
	}
}
