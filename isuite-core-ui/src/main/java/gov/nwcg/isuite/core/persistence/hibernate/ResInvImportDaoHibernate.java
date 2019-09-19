package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ResInvImport;
import gov.nwcg.isuite.core.domain.impl.ResInvImportImpl;
import gov.nwcg.isuite.core.persistence.ResInvImportDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.ResourceInventoryQuery2;
import gov.nwcg.isuite.core.vo.ResInvImportVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

public class ResInvImportDaoHibernate extends TransactionSupportImpl implements ResInvImportDao {

   /**
    * 
    */
   private final CrudDao<ResInvImport> crudDao;
   
   public ResInvImportDaoHibernate(final CrudDao<ResInvImport> crudDao) {
      if ( crudDao == null ) {
         throw new IllegalArgumentException("crudDao can not be null");
      }
      this.crudDao = crudDao;
   }
   
   public void delete(ResInvImport persistable) throws PersistenceException {
      crudDao.delete(persistable);
   }

   public ResInvImport getById(Long id, Class clazz) throws PersistenceException {
      return crudDao.getById(id, ResInvImportImpl.class);
   }

   public void save(ResInvImport persistable) throws PersistenceException {
      crudDao.save(persistable);
   }

   public void saveAll(Collection<ResInvImport> persistables) throws PersistenceException {
      crudDao.saveAll(persistables);
   }

   public Collection<ResInvImportVo> getByDates(Date fromDate, Date toDate) throws PersistenceException {
	   Criteria crit = getHibernateSession().createCriteria(ResInvImportImpl.class);
	   
	   if(DateUtil.hasValue(fromDate))
		   crit.add(Expression.gt("processedDate", fromDate));
	   if(DateUtil.hasValue(toDate))
		   crit.add(Expression.lt("processedDate", toDate));

	   crit.addOrder(Order.desc("processedDate"));
	   
	   Collection<ResInvImport> entities = crit.list();
	   if(CollectionUtility.hasValue(entities))
		   try{
			   return ResInvImportVo.getInstances(entities, true);
		   }catch(Exception e){
			   throw new PersistenceException(e);
		   }
	   else
		   return new ArrayList<ResInvImportVo>();
   }

   public void createResInvImportRecord(String type) throws PersistenceException {
	   String sql=ResourceInventoryQuery2.getResInvImportInsertQuery2(type);
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   q.executeUpdate();
	   
	   if(type.equalsIgnoreCase("OH")){
		   String sql2=ResourceInventoryQuery2.getResInvImportInsertCountQuery2();
		   SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		   q2.executeUpdate();

		   String sql3=ResourceInventoryQuery2.getResInvImportUpdateCountQuery2();
		   SQLQuery q3 = getHibernateSession().createSQLQuery(sql3);
		   q3.executeUpdate();
	   }else{
		   String sql4=ResourceInventoryQuery2.getResInvImportInsertNonOHCountQuery2();
		   SQLQuery q4 = getHibernateSession().createSQLQuery(sql4);
		   q4.executeUpdate();

		   String sql5=ResourceInventoryQuery2.getResInvImportUpdateNonOHCountQuery2();
		   SQLQuery q5 = getHibernateSession().createSQLQuery(sql5);
		   q5.executeUpdate();
		   
	   }
   }
   
   public void doUpdatesOH() throws PersistenceException {
	   int cnt=0;
	   String sql=ResourceInventoryQuery2.getUpdateOHCountQuery();
	   
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   Object rslt=q.uniqueResult();
	   try{
		   if(null != rslt)
			   cnt=TypeConverter.convertToInt(rslt);
	   }catch(Exception e){}

	   if(cnt>0){
		   String sql2=ResourceInventoryQuery2.getUpdateOHQuery();
		   SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		   q2.executeUpdate();
	   }
   }

   public void doUpdatesNonOH() throws PersistenceException {
	   int cnt=0;
	   String sql=ResourceInventoryQuery2.getUpdateNonOHCountQuery();
	   
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   Object rslt=q.uniqueResult();
	   try{
		   if(null != rslt)
			   cnt=TypeConverter.convertToInt(rslt);
	   }catch(Exception e){}

	   if(cnt>0){
		   String sql2=ResourceInventoryQuery2.getUpdateNonOHQuery();
		   SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		   q2.executeUpdate();
	   }
   }
   
   public void doInsertsOH() throws PersistenceException {
	   int cnt=0;
	   String sql=ResourceInventoryQuery2.getInsertOHCountQuery();
	   
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   Object rslt=q.uniqueResult();
	   try{
		   if(null != rslt)
			   cnt=TypeConverter.convertToInt(rslt);
	   }catch(Exception e){}

	   if(cnt>0){
		   String sql2=ResourceInventoryQuery2.getInsertOHQuery();
		   SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		   q2.executeUpdate();
	   }
   }

   public void doInsertsNonOH() throws PersistenceException {
	   int cnt=0;
	   String sql=ResourceInventoryQuery2.getInsertNonOHCountQuery();
	   
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   Object rslt=q.uniqueResult();
	   try{
		   if(null != rslt)
			   cnt=TypeConverter.convertToInt(rslt);
	   }catch(Exception e){}

	   if(cnt>0){
		   String sql2=ResourceInventoryQuery2.getInsertNonOHQuery();
		   SQLQuery q2 = getHibernateSession().createSQLQuery(sql2);
		   q2.executeUpdate();
		   
		   cnt=0;
		   String sql3=ResourceInventoryQuery2.getInsertNonOHKindCountQuery();
		   SQLQuery q3 = getHibernateSession().createSQLQuery(sql3);
		   rslt=q.uniqueResult();
		   try{
			   if(null != rslt)
				   cnt=TypeConverter.convertToInt(rslt);
		   }catch(Exception e){}
		   
		   if(cnt>0){
			   String sql4=ResourceInventoryQuery2.getInsertNonOHKindQuery();
			   SQLQuery q4 = getHibernateSession().createSQLQuery(sql4);
			   q4.executeUpdate();
		   }
	   }
   }

   public void markCompleted() throws PersistenceException {
	   String sql="update isw_resinv_import set status='COMPLETED' where status='INWORK'";
	   SQLQuery q = getHibernateSession().createSQLQuery(sql);
	   q.executeUpdate();
   }
}
