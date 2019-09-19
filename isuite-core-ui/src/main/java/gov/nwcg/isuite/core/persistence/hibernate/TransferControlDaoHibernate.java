package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.TransferControl;
import gov.nwcg.isuite.core.persistence.TransferControlDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.SQLQuery;

public class TransferControlDaoHibernate extends TransactionSupportImpl implements TransferControlDao {

	private final CrudDao<TransferControl> crudDao;

	/**
	 * Constructor.
	 * @param crudDao can't be null
	 * @param transferableDao can't be null
	 */
	public TransferControlDaoHibernate(final CrudDao<TransferControl> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(TransferControl persistable) throws PersistenceException {
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public TransferControl getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(TransferControl persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<TransferControl> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public int getStatusEmptyCount() throws PersistenceException {
		int cnt=0;
		
		String sql="select count(*) from isw_transfer_control where status is null or status=''";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=q.uniqueResult();
		if(null != rslt){
			try{
				cnt=TypeConverter.convertToInt(rslt);
			}catch(Exception e){}
		}
		
		return cnt;
	}
	
	public void createRecord(Boolean isGroupTransfer, String groupTi, String incTi,Long dtUserId) throws PersistenceException {
		String sql="insert into isw_transfer_control (id,incident_group_ti,incident_ti,ds_user_id,is_incident_group) values " +
					"("+(super.isOracleDialect() ? "SEQ_TRANSFER_CONTROL.nextVal" : "nextVal('SEQ_TRANSFER_CONTROL')" )+" "+
					","+(isGroupTransfer==true ? "'"+groupTi+"'" : "''" ) + " " +
					",'"+incTi + "' " +
					","+dtUserId + " " +
					",'"+(super.isOracleDialect() ? (isGroupTransfer==true ? "Y": "N" ) : (isGroupTransfer==true ? "Y" : "N" ) )+"' )";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		q.executeUpdate();
	}
	
	public void generateTI() throws PersistenceException {
		String sql="update inc_exp_tables set t_name=lower(t_name)";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
		
		String sql2="select gen_ti()";
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
		query2.list();
	}

	public String getGroupTi(Long id) throws PersistenceException {
		String sql="select transferable_identity from isw_incident_group where id = " + id + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				String ti=TypeConverter.convertToString(rslt);
				return ti;
			}catch(Exception e){}
		}
		return "";
	}
	
	public String getIncidentTi(Long id) throws PersistenceException {
		String sql="select transferable_identity from isw_incident where id = " + id + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				String ti=TypeConverter.convertToString(rslt);
				return ti;
			}catch(Exception e){}
		}
		return "";
	}

}
