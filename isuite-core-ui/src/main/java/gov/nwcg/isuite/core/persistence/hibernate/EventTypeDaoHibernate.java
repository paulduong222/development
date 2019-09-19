package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.EventType;
import gov.nwcg.isuite.core.domain.impl.EventTypeImpl;
import gov.nwcg.isuite.core.filter.EventTypeFilter;
import gov.nwcg.isuite.core.persistence.EventTypeDao;
import gov.nwcg.isuite.core.vo.EventTypeVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

public class EventTypeDaoHibernate extends TransactionSupportImpl implements EventTypeDao {

   static final Logger LOG = Logger.getLogger(EventTypeDaoHibernate.class);
   private final CrudDao<EventType> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    * @param transferableDao can't be null
    */
   public EventTypeDaoHibernate(final CrudDao<EventType> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
      
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#delete(gov.nwcg.isuite.domain.Persistable)
    */
   public void delete(EventType persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getAll(gov.nwcg.isuite.domain.Filter)
    */
   @SuppressWarnings("unchecked")
   @Deprecated
   public Collection<EventType> getAll(Filter filter) throws PersistenceException {
//      LOG.debug("*** Entering: EventTypeDaoHibernate.getAll(filter)");
//      assert(filter instanceof EventTypeFilter);
//      EventTypeFilter f = (EventTypeFilter) filter;
//      Criteria crit = getHibernateSession().createCriteria(EventTypeImpl.class);
//      if (f != null) {
//         if (f.getEventType() != null && !"".equals(f.getEventType()))
//         {
//            crit.add(Expression.ilike("eventType", f.getEventType(), MatchMode.START));
//         }
//         if (f.getEventTypeCode() != null && !"".equals(f.getEventTypeCode()))
//         {
//            crit.add(Expression.ilike("eventTypeCode", f.getEventTypeCode(), MatchMode.START));
//         }
//      }
//      crit.addOrder(Order.asc("eventTypeCode"));
//      return crit.list();
      throw new UnsupportedOperationException("The getPicklist(Filter) should be called.");   
   }
   
   @SuppressWarnings("unchecked")
   public Collection<EventTypeVo> getPicklist(EventTypeFilter filter) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(EventTypeImpl.class);
      crit.setProjection(Projections.projectionList()
               .add(Projections.property("id"), "id")
               .add(Projections.property("eventType"), "type")
               .add(Projections.property("eventTypeCode"), "eventTypeCd")
               );
      if (filter != null) {
         if (filter.getEventType() != null && !"".equals(filter.getEventType()))
         {
            crit.add(Expression.ilike("eventType", filter.getEventType(), MatchMode.START));
         }
         if (filter.getEventTypeCode() != null && !"".equals(filter.getEventTypeCode()))
         {
            crit.add(Expression.ilike("eventTypeCode", filter.getEventTypeCode(), MatchMode.START));
         }
      }
      crit.addOrder(Order.asc("eventTypeCode"));
      crit.setResultTransformer(Transformers.aliasToBean(EventTypeVo.class));
      Collection<EventTypeVo> vos = crit.list();
      return vos;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public EventType getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, EventTypeImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
    */
   public void save(EventType persistable) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<EventType> persistables) throws PersistenceException {
      throw new UnsupportedOperationException();
   }

}
