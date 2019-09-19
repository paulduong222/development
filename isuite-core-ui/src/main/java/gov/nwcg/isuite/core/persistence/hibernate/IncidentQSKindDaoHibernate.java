package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentQSKind;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.impl.IncidentQSKindImpl;
import gov.nwcg.isuite.core.domain.impl.KindImpl;
import gov.nwcg.isuite.core.filter.KindFilter;
import gov.nwcg.isuite.core.persistence.IncidentQSKindDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentQSKindQuery;
import gov.nwcg.isuite.core.vo.KindVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

/**
 * @author mpoll
 *
 */
public class IncidentQSKindDaoHibernate extends TransactionSupportImpl implements IncidentQSKindDao {
   private final CrudDao<IncidentQSKind> crudDao;

   /**
    * Constructor
    */
   public IncidentQSKindDaoHibernate(final CrudDao<IncidentQSKind> crudDao) {
      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(IncidentQSKind persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public IncidentQSKind getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, IncidentQSKindImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(IncidentQSKind persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<IncidentQSKind> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQSKindDao#getAvailablePrefKindCodes(java.lang.Long, java.lang.Boolean, gov.nwcg.isuite.core.filter.KindFilter)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<KindVo> getAvailablePrefKindCodes(Long incidentOrGroupId, Boolean isGroup, KindFilter itemFilter) throws PersistenceException {
      /*
       * Return Collection<KindVo> from ISWL_Kind table where it doesn't exist in the ISW_Incident_QS_Kind Table
       * and isn't a kind field that is located in the default QuickStats list
       */
      Criteria crit = getHibernateSession().createCriteria(KindImpl.class);
      crit.createAlias("requestCategory", "rc");
      
      DetachedCriteria detachedCrit = DetachedCriteria.forClass(IncidentQSKindImpl.class);
      detachedCrit.createAlias("kind", "k");
      if(isGroup)
    	  detachedCrit.add(Restrictions.eq("incidentGroupId", incidentOrGroupId));
      else
    	  detachedCrit.add(Restrictions.eq("incidentId", incidentOrGroupId));
    	  
      detachedCrit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
      detachedCrit.setProjection(Property.forName("k.id")); 

      if(itemFilter != null) {
         if(itemFilter.getCode() != null && !itemFilter.getCode().isEmpty()) {
            crit.add(Restrictions.ilike("this.code", itemFilter.getCode(), MatchMode.START));
         }
         if(itemFilter.getDescription() != null && !itemFilter.getDescription().isEmpty()) {
            crit.add(Restrictions.ilike("this.description", itemFilter.getDescription(), MatchMode.START));
         }
         if(itemFilter.getRcCode() != null && !itemFilter.getRcCode().isEmpty()) {
            //Even though we are checking only a single character here, I used ilike so that I can reap the benefits of case insensitivity. -dbudge
            crit.add(Restrictions.ilike("rc.code", itemFilter.getRcCode(), MatchMode.START));
         }
      }
	   
      crit.add(Subqueries.propertyNotIn("id", detachedCrit));

      crit.addOrder(Order.asc("this.code"));
      Collection<Kind> entities = crit.list();

      try{
    	  return KindVo.getInstances(entities, true);
      }catch(Exception e){
		  throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQSKindDao#getSelectedPrefKindCodes(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<KindVo> getSelectedPrefKindCodes(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(IncidentQSKindImpl.class);
	   crit.createAlias("kind", "k");
	   crit.createAlias("k.requestCategory", "rc");
	   if (isGroup) {
		   crit.add(Expression.eq("this.incidentGroupId", incidentOrGroupId));
	   } else {
		   crit.add(Expression.eq("this.incidentId", incidentOrGroupId));
	   }

	   crit.addOrder(Order.asc("this.kindId"));
	   Collection<IncidentQSKindImpl> results = crit.list();
	   Collection<Kind> items = new ArrayList<Kind>();
	   for(IncidentQSKindImpl entity : results) {
		   items.add(entity.getKind());
	   }

	   try {
		   return KindVo.getInstances(items, true);
	   }
	   catch ( Exception e ) {
		   throw new PersistenceException(e);
	   }
   }

   @Override
   public void removeAllQSKindsWithId(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      if (isGroup) {
         //Query uguaQuery = getHibernateSession().createQuery(IncidentQSKindQuery.DELETE_ALL_WITH_INCIDENT_GROUP_ID_QUERY);
         //uguaQuery.setParameter("incidentGroupId", incidentOrGroupId);
         //uguaQuery.executeUpdate();
      } else {
         Query uguaQuery = getHibernateSession().createQuery(IncidentQSKindQuery.DELETE_ALL_WITH_INCIDENT_ID_QUERY);
         uguaQuery.setParameter("incidentId", incidentOrGroupId);
         uguaQuery.executeUpdate();
      }
   }

}
