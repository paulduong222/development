/**
 * 
 */
package gov.nwcg.isuite.framework.core.persistence;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import org.hibernate.Session;

/**
 * Marker interface to identify which dao's need to participate in transactions.
 * <p>
 * This is done to make the wiring up of the spring config proxies easier
 * </p>
 * 
 * @author doug
 * 
 */
public interface TransactionSupport {
   
   /**
    * Return the maximum number of items in a result set.
    * @return the maximum number of items in a result set.
    */
   public int getMaxResultSize();

   public Session getHibernateSession() throws PersistenceException;

   public void flushAndEvict(Persistable entity) throws Exception;
   
   public void setLoggingInterceptor(LoggingInterceptor logger);
   
   public Boolean isOracleDialect();
}
