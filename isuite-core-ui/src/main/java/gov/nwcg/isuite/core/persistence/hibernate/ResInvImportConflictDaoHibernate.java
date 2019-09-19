package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ResInvImportConflict;
import gov.nwcg.isuite.core.domain.impl.ResInvImportConflictImpl;
import gov.nwcg.isuite.core.persistence.ResInvImportConflictDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public class ResInvImportConflictDaoHibernate extends TransactionSupportImpl implements ResInvImportConflictDao {

   /**
    * 
    */
   private final CrudDao<ResInvImportConflict> crudDao;
   
   public ResInvImportConflictDaoHibernate(final CrudDao<ResInvImportConflict> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }
   
   public void delete(ResInvImportConflict persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   public ResInvImportConflict getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, ResInvImportConflictImpl.class);
   }

   public void save(ResInvImportConflict persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   public void saveAll(Collection<ResInvImportConflict> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

}
