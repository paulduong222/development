package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.persistence.IncidentQuestionDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentQuestionQuery;
import gov.nwcg.isuite.core.vo.IncidentQuestionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author mpoll
 *
 */
public class IncidentQuestionDaoHibernate extends TransactionSupportImpl implements IncidentQuestionDao {
   private final CrudDao<IncidentQuestion> crudDao;

   /**
    * Constructor
    */
   public IncidentQuestionDaoHibernate(final CrudDao<IncidentQuestion> crudDao) {
      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void delete(IncidentQuestion persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   @SuppressWarnings("unchecked")
   @Override
   public IncidentQuestion getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, IncidentQuestionImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   @Override
   public void save(IncidentQuestion persistable) throws PersistenceException {
	  if( (null==persistable.getId()) || (persistable.getId()<1L)){
	      crudDao.save(persistable);
	  }else
		  getHibernateSession().merge(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   @Override
   public void saveAll(Collection<IncidentQuestion> persistables) throws PersistenceException {
	  for(IncidentQuestion entity : persistables){
		  if( (null==entity.getId()) || (entity.getId()<1L)){
		      crudDao.save(entity);
		  }else
			  getHibernateSession().merge(entity);
	  }
      //crudDao.saveAll(persistables);
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#getAirTravelQuestions(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentQuestionVo> getAirTravelQuestions(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
      crit.createAlias("question", "q");
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("q.questionType", QuestionTypeEnum.AIRTRAVEL));
      crit.addOrder(Order.asc("position"));
      Collection<IncidentQuestion> entities = crit.list();
      
      try{
        return IncidentQuestionVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#getNonStandardQuestionEntities(java.lang.Long, java.lang.String)
    */
   @SuppressWarnings("unchecked")
   public Collection<IncidentQuestion> getNonStandardQuestionEntities(Long incidentId, String questionType) throws PersistenceException {
      try {
         Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
         crit.createAlias("question", "q");
         
         crit.add(Restrictions.eq("this.incidentId", incidentId));
         crit.add(Restrictions.eq("q.standard", false));
         if(questionType != null) {
            if(questionType.equals(QuestionTypeEnum.AIRTRAVEL.name())) {
               crit.add(Restrictions.eq("q.questionType", QuestionTypeEnum.AIRTRAVEL));
            }
            if(questionType.equals(QuestionTypeEnum.PREPLANNING.name())) {
               crit.add(Restrictions.eq("q.questionType", QuestionTypeEnum.PREPLANNING));
            }
         }
         
         crit.addOrder(Order.asc("this.position"));
         Collection<IncidentQuestion> entities = crit.list();
         return entities;

      } catch(Exception e) {
         throw new PersistenceException(e);
      }
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#getCheckInQuestions(java.lang.Long, java.lang.Boolean)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Collection<IncidentQuestionVo> getCheckInQuestions(Long incidentOrGroupId, Boolean isGroup) throws PersistenceException {
      Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
      crit.createAlias("question", "q");
      if (isGroup) {
         crit.add(Expression.eq("incidentGroupId", incidentOrGroupId));
      } else {
         crit.add(Expression.eq("incidentId", incidentOrGroupId));
      }
      crit.add(Expression.eq("q.questionType", QuestionTypeEnum.PREPLANNING));
      crit.addOrder(Order.asc("position"));
      Collection<IncidentQuestion> entities = crit.list();
      
      try{
        return IncidentQuestionVo.getInstances(entities, true);
      }catch(Exception e){
        throw new PersistenceException(e);
      }
   }

   public int getNextQuestionPosition(Long id, Boolean isGroup) throws PersistenceException {
	   Query q = null;
	   
	   if(isGroup){
		   q = getHibernateSession().createQuery(IncidentQuestionQuery.GET_MAX_INCIDENT_GRP_QUESTION_POSITION_QUERY);
	   }else {
		   q = getHibernateSession().createQuery(IncidentQuestionQuery.GET_MAX_INCIDENT_QUESTION_POSITION_QUERY);
	   }

	   q.setParameter("id", id);

	   Integer result = (Integer)q.uniqueResult();
	   
	   if(null==result)
		   result=new Integer(0);
	   
	   return result.intValue();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#checkForNonStandardTravelQuestions(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   @Override
   public Boolean checkForNonStandardTravelQuestions(Long incidentId) throws PersistenceException {
      try {
         Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
         crit.createAlias("question", "q");
         crit.add(Restrictions.eq("this.incidentId", incidentId));
         crit.add(Restrictions.eq("q.questionType", QuestionTypeEnum.AIRTRAVEL));
         crit.add(Restrictions.eq("q.standard", false));
         Collection<IncidentQuestion> incidentQuestions = crit.list();
         if(incidentQuestions == null || incidentQuestions.size() == 0) {
            return false;
         } else {
            return true;
         }
      } catch (Exception e) {
         throw new PersistenceException(e);
      }
   }
   
   //TODO:  Combine these two methods. -dbudge
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#checkForNonStandardCheckinQuestions(java.lang.Long)
    */
   @SuppressWarnings("unchecked")
   public Boolean checkForNonStandardCheckinQuestions(Long incidentId) throws PersistenceException {
      try {
         Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
         crit.createAlias("question", "q");
         crit.add(Restrictions.eq("this.incidentId", incidentId));
         crit.add(Restrictions.eq("q.questionType", QuestionTypeEnum.PREPLANNING));
         crit.add(Restrictions.eq("q.standard", false));
         Collection<IncidentQuestion> incidentQuestions = crit.list();
         if(incidentQuestions == null || incidentQuestions.size() == 0) {
            return false;
         } else {
            return true;
         }
      } catch (Exception e) {
         throw new PersistenceException(e);
      }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentQuestionDao#getByQuestionIdAndIncidentId(java.lang.Long, java.lang.Long)
    */
   @Override
   public IncidentQuestion getByQuestionIdAndIncidentId(Long questionId, Long incidentId) throws PersistenceException {
      try {
         Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);

         crit.add(Restrictions.eq("questionId", questionId));
         crit.add(Restrictions.eq("incidentId", incidentId));

         return (IncidentQuestion)crit.uniqueResult();
      } catch (Exception e) {
         throw new PersistenceException(e);
      }
   }

   @Override
   public IncidentQuestion getByQuestion(String question, Long incidentId, QuestionTypeEnum questionType) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
	   crit.createAlias("question", "q");
	   crit.add(Restrictions.eq("q.question", question).ignoreCase());
	   crit.add(Restrictions.eq("incidentId", incidentId));
	   crit.add(Restrictions.eq("q.questionType", questionType));
	   return (IncidentQuestion)crit.uniqueResult();
   }

}
