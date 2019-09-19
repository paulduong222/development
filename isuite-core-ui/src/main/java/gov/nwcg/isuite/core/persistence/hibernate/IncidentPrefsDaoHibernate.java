package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentGroupPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefs;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupPrefsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentPrefsOtherFieldsImpl;
import gov.nwcg.isuite.core.persistence.IncidentPrefsDao;
import gov.nwcg.isuite.core.vo.IncidentPrefsVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.IncidentPrefsSectionNameEnum;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author mpoll
 *
 */
public class IncidentPrefsDaoHibernate extends TransactionSupportImpl implements IncidentPrefsDao {

   private final CrudDao<IncidentPrefs> crudDao;

   /**
    * Default Constructor
    */
   public IncidentPrefsDaoHibernate(final CrudDao<IncidentPrefs> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(IncidentPrefs persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public IncidentPrefs getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, IncidentPrefsImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(IncidentPrefs persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<IncidentPrefs> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getFinanceFields(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getFinanceFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.FINANCE));
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
    	  return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
    	  throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getICS221Fields(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getICS221Fields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
        return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getICS221OtherFields(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getICS221OtherFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.ICS221_OTHER));
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
        return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getLogisticsFields(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getLogisticsFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.LOGISTICS));
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
        return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getOtherLabels(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getOtherLabels(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.OTHER_LABEL));
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
        return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getPlanningFields(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefsVo> getPlanningFields(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.PLANNING));
      
      crit.addOrder(Order.asc("position"));
      Collection<IncidentPrefs> entities = crit.list();
      
      try{
        return IncidentPrefsVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

//   // NOTE: Although incident groups use IncidentGroupPrefs, this method is still using IncidentPrefs.
//   // This method is being called from IncidentPrefsServiceImpl.saveCheckOutPrefsForAllIncidentsInGroup( ) method.
//   /*
//    * (non-Javadoc)
//    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getByIncidentGroupId(java.lang.Long)
//    */
//   @SuppressWarnings("unchecked")
//   @Override
//   public Collection<IncidentPrefs> getByIncidentGroupId(Long incidentGroupId) throws PersistenceException {
//      try {
////    	  IncidentGroupPrefsImpl.class
//         Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
//         crit.add(Restrictions.eq("this.incidentGroupId", incidentGroupId));
//         crit.addOrder(Order.asc("this.fieldLabel"));
//         crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
//         return crit.list();
//      } catch(Exception e) {
//         throw new PersistenceException(e);
//      }
//   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getByIncidentId(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   @Override
   public List<IncidentPrefs> getByIncidentId(Long incidentId) throws PersistenceException {
      try {
         Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
         crit.add(Restrictions.eq("this.incidentId", incidentId));
         crit.addOrder(Order.asc("this.fieldLabel"));
         crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
         return crit.list();
      } catch(Exception e) {
         throw new PersistenceException(e);
      }
   }

   public IncidentPrefsOtherFields getPrefsOtherFields(Long incidentId) throws PersistenceException {
	   try {
		   Criteria crit = getHibernateSession().createCriteria(IncidentPrefsOtherFieldsImpl.class);
		   crit.add(Restrictions.eq("this.incidentId", incidentId));
		   crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		   return (IncidentPrefsOtherFields)crit.uniqueResult();
	   } catch(Exception e) {
		   throw new PersistenceException(e);
	   }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getICS204Block5FieldsForIncident(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentPrefs> getICS204Block5FieldsForIncident(Long incidentId, Boolean selected) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentPrefsImpl.class);
      crit.add(Expression.eq("incidentId", incidentId));
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.IAP_204_BLOCK6)); //Update to Block 5 when code change is made
      if(selected!=null) {
		   crit.add(Expression.eq("selected", selected));  
	   } 
      crit.addOrder(Order.asc("position"));
      return crit.list();
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentPrefsDao#getICS204Block5FieldsForIncidentGroup(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentGroupPrefs> getICS204Block5FieldsForIncidentGroup(Long incidentGroupId, Boolean selected) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentGroupPrefsImpl.class);
      crit.add(Expression.eq("incidentGroupId", incidentGroupId));
      crit.add(Expression.eq("sectionName", IncidentPrefsSectionNameEnum.IAP_204_BLOCK6)); //Update to Block 5 when code change is made
      if(selected!=null) {
		   crit.add(Expression.eq("selected", selected));  
	   } 
      crit.addOrder(Order.asc("position"));
      return crit.list();
   }
   
}
