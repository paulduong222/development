package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RateClass;
import gov.nwcg.isuite.core.domain.impl.RateClassImpl;
import gov.nwcg.isuite.core.persistence.RateClassDao;
import gov.nwcg.isuite.core.vo.RateClassVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;

@SuppressWarnings("unchecked")
public class RateClassDaoHibernate extends TransactionSupportImpl implements RateClassDao {

   private final CrudDao<RateClass> crudDao;
   
   public RateClassDaoHibernate(final CrudDao<RateClass> crudDao) {
      if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
      this.crudDao = crudDao;
   }
   
   public RateClass getById(Long id, Class clazz) throws PersistenceException {
	   return crudDao.getById(id, clazz);
   } 
   
   public RateClass getById(Long id) throws PersistenceException {
	   return crudDao.getById(id, RateClassImpl.class);
   } 

   public Collection<RateClassVo> getByRateClassId(Long id) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(RateClassImpl.class);

	   crit.add(Expression.eq("id", id));
	   
	   Collection<RateClass> entities = crit.list();

	   if(null != entities){
		   try{
			   return RateClassVo.getInstances(entities,true);
		   }catch(Exception e){
			   throw new PersistenceException(e);
		   }
	   }

	   return null;
   }
   
   @SuppressWarnings("unchecked")
   public Collection<RateClassVo> getPicklist() throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(RateClassImpl.class);

	   Collection<RateClass> entities = crit.list();

	   if(null != entities){
		   try{
			   return RateClassVo.getInstances(entities,true);
		   }catch(Exception e){
			   throw new PersistenceException(e);
		   }
	   }

	   return null;
   }

   @Override
   public void delete(RateClass persistable) throws PersistenceException {
   }

   @Override
   public void save(RateClass persistable) throws PersistenceException {

   }

   @Override
   public void saveAll(Collection<RateClass> persistables) throws PersistenceException {

   }

   public Collection<Integer> getRateYears() throws PersistenceException {
	   String sql = "SELECT DISTINCT(RATE_YEAR) FROM ISWL_RATE_CLASS ORDER BY RATE_YEAR ";
	   
	   SQLQuery query = getHibernateSession().createSQLQuery(sql);
	   
	   return (Collection<Integer>)query.list();
	   
   }
}
