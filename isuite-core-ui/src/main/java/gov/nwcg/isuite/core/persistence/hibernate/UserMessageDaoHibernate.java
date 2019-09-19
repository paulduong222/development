package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.UserMessage;
import gov.nwcg.isuite.core.domain.impl.UserMessageImpl;
import gov.nwcg.isuite.core.filter.UserMessageFilter;
import gov.nwcg.isuite.core.filter.impl.UserMessageFilterImpl;
import gov.nwcg.isuite.core.persistence.UserMessageDao;
import gov.nwcg.isuite.core.vo.UserMessageVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 * @author mgreen
 *
 */
public class UserMessageDaoHibernate extends TransactionSupportImpl implements UserMessageDao {

   private static final Logger LOG = Logger.getLogger(UserMessageDaoHibernate.class);
//   private final CrudDao<UserMessage> crudDao;
   
   /**
    * Constructor.
    * @param crudDao can't be null
    */
   public UserMessageDaoHibernate() {
      
//      if ( crudDao == null ) {
//         throw new IllegalArgumentException("crudDao can not be null");
//      }
//      
//      this.crudDao = crudDao;
   }
   
   public void delete(UserMessage persistable) throws PersistenceException {
      LOG.error("Attempted to delete user message");
      throw new PersistenceException("Cannot delete User Messages.");
   }

   @SuppressWarnings("unchecked")
   public Collection<UserMessage> getAll(UserMessageFilter userMessageFilter) throws PersistenceException {

      Criteria criteria = (Criteria) getHibernateSession().createCriteria(UserMessage.class);
      if(userMessageFilter != null) {

         //user id = filter
         if(userMessageFilter.getUserId() != null)
            criteria.add(Expression.eq("userId", userMessageFilter.getUserId()));

         //message cause = filter
         if(userMessageFilter.getCause() != null) {
            criteria.createAlias("message", "m");
            criteria.add(Expression.eq("m.cause", userMessageFilter.getCause()));
         }
         
         //message severity = filter
         if(userMessageFilter.getSeverity() != null) {
            criteria.createAlias("message", "m"); //TODO: what if this was already added above?
            criteria.add(Expression.eq("m.severity", userMessageFilter.getSeverity()));
         }
         
         // >= cleared date filter
         if(userMessageFilter.getClearedDate() != null)
            criteria.add(Expression.le("clearedDate", userMessageFilter.getClearedDate()));
         else criteria.add(Expression.isNull("clearedDate"));
         
         //active
         if(userMessageFilter.isActive() != null) {
            criteria.createAlias("message", "m"); //TODO: what if this was already added above?
            criteria.add(Restrictions.eq("m.active", userMessageFilter.isActive()));
         }
      }
      
      criteria.setMaxResults(super.getMaxResultSize());
      return criteria.list();
   }
   
   @SuppressWarnings("unchecked")
   public Collection<UserMessageVo> getAllUserMessageVos(UserMessageFilter filter) 
      throws PersistenceException {
      
      Criteria crit = getHibernateSession().createCriteria(UserMessageImpl.class);
      crit.createAlias("message", "msg");

      //user id = filter
      if(filter.getUserId() != null)
         crit.add(Restrictions.eq("userId", filter.getUserId()));
      
      //>= cleared date filter
      if(filter.getClearedDate() != null)
         crit.add(Restrictions.ge("clearedDate", filter.getClearedDate()));
      
      if ( filter.getTitle() != null ) 
         crit.add(Expression.ilike("msg.title", filter.getTitle(), MatchMode.ANYWHERE));

      if ( filter.getCause() != null ) 
         crit.add(Expression.eq("msg.cause", filter.getCause()));
      
      if ( filter.getSeverity() != null ) 
         crit.add(Expression.eq("msg.severity", filter.getSeverity()));

      if ( filter.getStartDate() != null ) 
         crit.add(Expression.ge("msg.occurranceDate", filter.getStartDate()));      
      
      if ( filter.getEndDate() != null ) 
         crit.add(Expression.le("msg.occurranceDate", filter.getEndDate()));
      
      if(filter.isActive() != null)
         crit.add(Restrictions.eq("msg.active", filter.isActive()));
      
      if(filter.isGlobal() != null)
         crit.add(Restrictions.eq("msg.global", filter.isGlobal()));
      
      crit.setResultTransformer(Transformers.aliasToBean(UserMessageVo.class));
//      List<UserMessageVo> list = crit.list();
//      return list;
      return crit.list();
   }
   
   @SuppressWarnings("unchecked")
   public Collection<UserMessage> getAllUserMessages(UserMessageFilter filter) 
      throws PersistenceException {
   
   Criteria crit = getHibernateSession().createCriteria(UserMessageImpl.class);
   crit.createAlias("message", "msg");
//   crit.createAlias("user", "u");

   //user id = filter
   if(filter.getUserId() != null)
      crit.add(Restrictions.eq("userId", filter.getUserId()));
   
   //>= cleared date filter
   if(filter.getClearedDate() != null)
      crit.add(Restrictions.ge("clearedDate", filter.getClearedDate()));
   
   if ( filter.getTitle() != null ) 
      crit.add(Expression.ilike("msg.title", filter.getTitle(), MatchMode.ANYWHERE));

   if ( filter.getCause() != null ) 
      crit.add(Expression.eq("msg.cause", filter.getCause()));
   
   if ( filter.getSeverity() != null ) 
      crit.add(Expression.eq("msg.severity", filter.getSeverity()));

   if ( filter.getStartDate() != null ) 
      crit.add(Expression.ge("msg.occurranceDate", filter.getStartDate()));      
   
   if ( filter.getEndDate() != null ) 
      crit.add(Expression.le("msg.occurranceDate", filter.getEndDate()));
   
   if(filter.isActive() != null)
      crit.add(Restrictions.eq("msg.isActive", filter.isActive()));
   
   if(filter.isGlobal() != null)
      crit.add(Restrictions.eq("msg.global", filter.isGlobal()));
   
   return crit.list();
}   

   public UserMessage getById(Long id) throws PersistenceException {
      return this.getById(id, UserMessageImpl.class);
   }
   
   public UserMessage getById(Long id, Class clazz) throws PersistenceException {
//      return crudDao.getById(id, UserMessageImpl.class);
	   return null;
   }

   public void save(UserMessage persistable) throws PersistenceException {
//      crudDao.save(persistable);
   }

   public void saveAll(Collection<UserMessage> persistables) throws PersistenceException {
      
      if(persistables == null) throw new PersistenceException("Incoming collection cannot be null.");
//      crudDao.saveAll(persistables);
   }
   
//   public Collection<UserMessageVo> getUserMessagesByUserId(Long userId) throws Exception {
//      UserMessageFilterImpl filter = new UserMessageFilterImpl(); 
//      filter.setUserId(userId);
//      return this.getAllUserMessageVos(filter);
//   }
   
   public Collection<UserMessage> getUserMessagesByUserId(Long userId) throws Exception {
      UserMessageFilterImpl filter = new UserMessageFilterImpl(); 
      filter.setUserId(userId);
      return this.getAllUserMessages(filter);
   }   

}
