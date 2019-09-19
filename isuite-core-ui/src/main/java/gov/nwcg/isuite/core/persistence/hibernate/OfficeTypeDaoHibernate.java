package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.OfficeType;
import gov.nwcg.isuite.core.domain.impl.OfficeTypeImpl;
import gov.nwcg.isuite.core.persistence.OfficeTypeDao;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * Dao for office type.
 * @author doug
 *
 */
public class OfficeTypeDaoHibernate extends TransactionSupportImpl implements OfficeTypeDao {
//	private static final Log LOG = LogFactory.getLog(OfficeTypeDaoHibernate.class);
   
   private final CrudDao<OfficeType> crudDao;

   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public OfficeTypeDaoHibernate(final CrudDao<OfficeType> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(OfficeType persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   public Collection<OfficeType> getAll(Filter filter) throws PersistenceException {
      throw new UnsupportedOperationException("Currently Unsupported method.");
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public OfficeType getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, OfficeTypeImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(OfficeType persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<OfficeType> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }
}
