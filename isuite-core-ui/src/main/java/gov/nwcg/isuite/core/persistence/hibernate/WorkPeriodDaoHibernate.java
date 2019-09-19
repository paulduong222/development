package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.impl.WorkPeriodImpl;
import gov.nwcg.isuite.core.persistence.WorkPeriodDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author mpoll
 *
 */
public class WorkPeriodDaoHibernate extends TransactionSupportImpl implements WorkPeriodDao {

   /**
    * 
    */
   private final CrudDao<WorkPeriod> crudDao;
   @SuppressWarnings("unused")
   private static final Logger LOG = Logger.getLogger(WorkPeriodDaoHibernate.class);
   
   public WorkPeriodDaoHibernate(final CrudDao<WorkPeriod> crudDao) {
      if ( crudDao == null ) {
         LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }
   
   public void delete(WorkPeriod persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   public WorkPeriod getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, WorkPeriodImpl.class);
   }

   public void save(WorkPeriod persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   public void saveAll(Collection<WorkPeriod> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   public WorkPeriod getByUniqueIdentity(String uniqueIdentity, Class clazz) throws PersistenceException, DuplicateItemException {
      throw new UnsupportedOperationException();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.WorkPeriodDao#getByIncidentResourceId(java.lang.Long)
    */
   public WorkPeriod getByIncidentResourceId(Long incidentResourceId) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(WorkPeriodImpl.class);
	   crit.add(Restrictions.eq("incidentResourceId", incidentResourceId));
	   return (WorkPeriod)crit.uniqueResult();
   }

}
