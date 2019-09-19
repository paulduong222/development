package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.impl.IsuiteAuthorityImpl;
import gov.nwcg.isuite.core.persistence.IsuiteAuthorityDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.IsuiteAuthorityNameEnum;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.springframework.security.GrantedAuthority;

/**
 * @author bsteiner
 *
 */
public class IsuiteAuthorityDaoHibernate extends TransactionSupportImpl implements IsuiteAuthorityDao {
   
   private static final Logger LOG = Logger.getLogger(IsuiteAuthorityDaoHibernate.class);

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.access.IsuiteAuthorityDao#getBy(gov.nwcg.isuite.domain.access.AuthorityNameEnum)
    */
   public GrantedAuthority getBy(IsuiteAuthorityNameEnum authorityName) throws PersistenceException {
      LOG.debug("**Entering IsuiteAuthorityDaoHibernate.getBy(authorityName)");
      
      if (authorityName == null) {
         throw new IllegalArgumentException("authorityName cannot be null.");
      }
      
      Criteria criteria = (Criteria) getHibernateSession().createCriteria(
               IsuiteAuthorityImpl.class);
      LOG.debug("adding to criteria.. authority: " + authorityName.getName());
      criteria.add(Expression.eq("authority", authorityName.getName()));
      
      return (GrantedAuthority) criteria.uniqueResult();
   }

}
