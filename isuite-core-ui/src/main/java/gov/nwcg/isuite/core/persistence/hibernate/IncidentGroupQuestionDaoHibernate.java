package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentGroupQuestion;
import gov.nwcg.isuite.core.domain.IncidentQuestion;
import gov.nwcg.isuite.core.domain.Question;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentQuestionImpl;
import gov.nwcg.isuite.core.domain.impl.QuestionImpl;
import gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class IncidentGroupQuestionDaoHibernate extends TransactionSupportImpl implements IncidentGroupQuestionDao {
   private final CrudDao<IncidentGroupQuestion> crudDao;

   /**
    * Constructor
    */
   public IncidentGroupQuestionDaoHibernate(final CrudDao<IncidentGroupQuestion> crudDao) {
      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   public void delete(IncidentGroupQuestion persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
    */
   public IncidentGroupQuestion getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, IncidentGroupQuestionImpl.class);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
    */
   public void save(IncidentGroupQuestion persistable) throws PersistenceException {
	  if( (null==persistable.getId()) || (persistable.getId()<1L)){
	      crudDao.save(persistable);
	  }else
		  getHibernateSession().merge(persistable);
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
    */
   public void saveAll(Collection<IncidentGroupQuestion> persistables) throws PersistenceException {
	  for(IncidentGroupQuestion entity : persistables){
		  if( (null==entity.getId()) || (entity.getId()<1L)){
		      crudDao.save(entity);
		  }else
			  getHibernateSession().merge(entity);
	  }
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.persistence.IncidentGroupQuestionDao#getByQuestion(java.lang.String, java.lang.Long, gov.nwcg.isuite.framework.types.QuestionTypeEnum)
    */
	@Override
	public IncidentGroupQuestion getByQuestion(String question, 
											   Long incidentGroupId, 
											   QuestionTypeEnum questionType) 
		throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentGroupQuestionImpl.class);
		   crit.createAlias("question", "q");
		   crit.add(Restrictions.eq("q.question", question).ignoreCase());
		   crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		   crit.add(Restrictions.eq("q.questionType", questionType));
		   return (IncidentGroupQuestion)crit.uniqueResult();
	}

	public void createDefaultGroupQuestions(Long incidentGroupId, Long primaryIncidentId) throws PersistenceException {
		// create group questions based on primary incident
		Criteria crit = getHibernateSession().createCriteria(IncidentQuestionImpl.class);
		crit.add(Restrictions.eq("incidentId", primaryIncidentId));
		Collection<IncidentQuestion> incidentQuestions = crit.list();
		IncidentGroup incidentGroup = new IncidentGroupImpl();
		incidentGroup.setId(incidentGroupId);
		
		if(CollectionUtility.hasValue(incidentQuestions)){
			for(IncidentQuestion iq : incidentQuestions){
				IncidentGroupQuestion igq = new IncidentGroupQuestionImpl();
				igq.setIncidentGroup(incidentGroup);
				igq.setVisible(iq.isVisible());
				igq.setPosition(iq.getPosition());
				if(BooleanUtility.isTrue(iq.getQuestion().isStandard())){
					igq.setQuestion(iq.getQuestion());
				}else{
					Question question = new QuestionImpl();
					question.setQuestion(iq.getQuestion().getQuestion());
					question.setQuestionType(iq.getQuestion().getQuestionType());
					question.setStandard(iq.getQuestion().isStandard());
					igq.setQuestion(question);
				}

				crudDao.save(igq);
				try{
					this.flushAndEvict(igq);
				}catch(Exception ee){}
			}
		}
	}


}
