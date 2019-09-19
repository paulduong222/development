package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.PriorityProgram;
import gov.nwcg.isuite.core.domain.impl.PriorityProgramImpl;
import gov.nwcg.isuite.core.persistence.PriorityProgramDao;
import gov.nwcg.isuite.core.vo.PriorityProgramVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class PriorityProgramDaoHibernate extends TransactionSupportImpl
		implements PriorityProgramDao {
	
	 private final CrudDao<PriorityProgram> crudDao;
	 
	 public PriorityProgramDaoHibernate(final CrudDao<PriorityProgram> crudDao) {

	      if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao cannot be null");
	      }
	      this.crudDao = crudDao;
      
	 }

	 @Override
	 public void delete(PriorityProgram persistable) throws PersistenceException {
		 crudDao.delete(persistable);
	 }

	 @Override
	 public PriorityProgram getById(Long id, Class<?> clazz)
			throws PersistenceException {
		
		 return crudDao.getById(id, PriorityProgramImpl.class);
	 }

	 @Override
	 public void save(PriorityProgram persistable) throws PersistenceException {
		crudDao.save(persistable);
	 }

	 @Override
	 public void saveAll(Collection<PriorityProgram> persistables)
			throws PersistenceException {
		 crudDao.saveAll(persistables);
	 }
	 
	 public int getDuplicateCodeCount(PriorityProgramVo vo) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(PriorityProgramImpl.class);
		 
		 crit.add(Expression.eq("code", vo.getCode().toUpperCase()));
		 crit.add(Expression.ne("id", vo.getId()));
		 crit.add(Expression.eq("incidentId", vo.getIncidentId()));
		 
		 return crit.list().size();
	 }
	 
	 @SuppressWarnings("unchecked")
	 public Collection<PriorityProgramVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		 Criteria crit = getHibernateSession().createCriteria(PriorityProgramImpl.class);
		 
		 if(LongUtility.hasValue(incidentId)){
			 crit.add(Restrictions.eq("incidentId", incidentId));
		 }
		 
//		 if(LongUtility.hasValue(incidentGroupId)){
//			 String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId + ")";
//			 crit.add(Restrictions.sqlRestriction(sqlFilter));
//		 }
		 
		 if(LongUtility.hasValue(incidentGroupId)){
			 crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		 }
		 
		 crit.addOrder(Order.desc("code"));
		 
		 Collection<PriorityProgram> entities = crit.list();
		 
		 try{
			 return PriorityProgramVo.getInstances(entities, true);
		 }catch (Exception e) {
			 throw new PersistenceException(e);
		 }
	 }
	 
	 public void syncNewWithGroup(String ppCode, Long groupId) throws PersistenceException {
		 // add new ppCode to all incidents in group
		 String sql="select igi.incident_id "+
		 			"from isw_incident_group_incident igi " +
		 		    "where igi.incident_group_id = " + groupId + " " +
		 		    "and (select count(id) from iswl_priority_program where incident_id = igi.incident_id and code='"+ppCode+"') = 0";
		 SQLQuery q = getHibernateSession().createSQLQuery(sql);
		 Collection<Object> list=q.list();
		 if(CollectionUtility.hasValue(list)){
			 for(Object obj : list){
				 try{
					 Long incidentId = TypeConverter.convertToLong(obj);
					 if(LongUtility.hasValue(incidentId)){
						 sql="insert into iswl_priority_program (id, code, incident_id) values (";
						 if(super.isOracleDialect())
							 sql=sql+"seq_priority_program.nextVal";
						 else
							 sql=sql+"nextVal('seq_priority_program')";
						 sql=sql+",'"+ppCode.toUpperCase()+"'";
						 sql=sql+","+incidentId+"";
						 sql=sql+")";
						 q=getHibernateSession().createSQLQuery(sql);
						 q.executeUpdate();
					 }
				 }catch(Exception e){}
			 }
		 }
	 }

	 public void syncNewFromIncident(String ppCode, Long groupId) throws PersistenceException {
		 // add ppCode from incident to group if not there
		 String sql="select id "+
		 			"from iswl_priority_program " +
		 		    "where incident_group_id = " + groupId + " " +
		 		    "and code='"+ppCode+"'";
		 SQLQuery q = getHibernateSession().createSQLQuery(sql);
		 Object ppId=q.uniqueResult();
		 if(null == ppId){
			 sql="insert into iswl_priority_program (id, code, incident_group_id) values (";
			 if(super.isOracleDialect())
				 sql=sql+"seq_priority_program.nextVal";
			 else
				 sql=sql+"nextVal('seq_priority_program')";
			 sql=sql+",'"+ppCode.toUpperCase()+"'";
			 sql=sql+","+groupId+"";
			 sql=sql+")";
			 q=getHibernateSession().createSQLQuery(sql);
			 q.executeUpdate();
		 }
	 }

	 public void syncUpdateWithGroup(String originalCode,String ppCode, Long groupId) throws PersistenceException {
		 // add new ppCode to all incidents in group
		 String sql="select igi.incident_id "+
		 			"from isw_incident_group_incident igi " +
		 		    "where igi.incident_group_id = " + groupId + " " +
		 		    "and (select count(id) from iswl_priority_program where incident_id = igi.incident_id and code='"+originalCode+"')=1";
		 SQLQuery q = getHibernateSession().createSQLQuery(sql);
		 Collection<Object> list=q.list();
		 if(CollectionUtility.hasValue(list)){
			 for(Object obj : list){
				 try{
					 Long incidentId = TypeConverter.convertToLong(obj);
					 if(LongUtility.hasValue(incidentId)){
						 sql="update iswl_priority_program set code='"+ppCode+"' where incident_id="+incidentId+" and code='"+originalCode+"'";
						 q=getHibernateSession().createSQLQuery(sql);
						 q.executeUpdate();
					 }
				 }catch(Exception e){}
			 }
		 }
	 }

	 public int getIncidentInUseCount(String ppCode, Long groupId) throws PersistenceException {
		 String sql=""+
		 "select count(rt.id) " +
		 "from isw_resource_training rt " +
		 "	, iswl_priority_program pp " +
		 "where rt.priority_program_id = pp.id " +
		 "and pp.code = '"+ppCode+"' " +
		 "and pp.incident_id in ( " +
		 " 	select incident_id " +
		 " 	from isw_incident_group_incident " +
		 " 	where incident_group_id = " + groupId + " " +
		 ")";		 
		 SQLQuery q = getHibernateSession().createSQLQuery(sql);
		 Object result=q.uniqueResult();
		 if(null != result){
			 try{
				 int cnt=TypeConverter.convertToInt(result);
				 return cnt;
			 }catch(Exception e){}
		 }
		 
		 return 0;
	 }
	 
	 public void deleteCodeForGroupIncidents(String ppCode, Long groupId) throws PersistenceException {
		 String sql=""+
		 "delete from iswl_priority_program " +
		 "where code = '"+ppCode+"' " +
		 "and incident_id in ( " +
		 " 	select incident_id " +
		 " 	from isw_incident_group_incident " +
		 " 	where incident_group_id = " + groupId + " " +
		 ")";		 
		 SQLQuery q = getHibernateSession().createSQLQuery(sql);
		 q.executeUpdate();
	 }

	public Long getIdByTransferableIdentity(String ti) throws PersistenceException {
		String sql="select id from iswl_priority_program where transferable_identity='"+ti+"'";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object result=q.uniqueResult();
		if(null != result){
			try{
				Long id=TypeConverter.convertToLong(result);
				return id;
			}catch(Exception e){}
		}
		
		return 0L;
	}
	 
}
