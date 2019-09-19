package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.impl.DbAvailImpl;
import gov.nwcg.isuite.core.persistence.DbAvailDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.OrderBySql;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class DbAvailDaoHibernate extends TransactionSupportImpl implements DbAvailDao {

	private final CrudDao<DbAvail> crudDao;

	/**
	 * Constructor.
	 * @param crudDao can't be null
	 */
	public DbAvailDaoHibernate(final CrudDao<DbAvail> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;

	}

	public void closeSession() throws Exception {
		getHibernateSession().close();
	}

	public void openSession() throws Exception {
		getHibernateSession().getSessionFactory().openSession();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void delete(DbAvail persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@Override
	public DbAvail getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, DbAvailImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void save(DbAvail persistable) throws PersistenceException {
		crudDao.setSkipFixCasing(true);
		crudDao.save(persistable);
		crudDao.setSkipFixCasing(false);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	@Override
	public void saveAll(Collection<DbAvail> persistables) throws PersistenceException {

	}

	public Collection<String> getDbNames() throws PersistenceException {
		Collection<String> names = new ArrayList<String>();

		Criteria crit = getHibernateSession().createCriteria(DbAvailImpl.class);
		
		Collection<DbAvail> entities = (Collection<DbAvail>) crit.list();
		for(DbAvail entity : entities){
			names.add(entity.getName());
		}
		
		return names;
	}
	
	public Collection<String> getDbNames(Long excludeId) throws PersistenceException {
		Collection<String> names = new ArrayList<String>();

		String sql = "select name from isw_db_avail ";
		if(LongUtility.hasValue(excludeId))
			sql=sql+"where id != " + excludeId;
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Collection<Object> list = query.list();
		for(Object o : list ){
			String name=(String)o;
			names.add(name);
		}
		/*
		Criteria crit = getHibernateSession().createCriteria(DbAvailImpl.class);
		
		Collection<DbAvail> entities = (Collection<DbAvail>) crit.list();
		for(DbAvail entity : entities){
			names.add(entity.getName());
		}
		*/
		
		return names;
	}
	
	public Collection<DbAvail> getAll() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(DbAvailImpl.class);
		
		String sqlOrder="this_.name asc ";
		crit.addOrder(OrderBySql.sql(sqlOrder));

		Collection<DbAvail> entities = (Collection<DbAvail>) crit.list();
		
		return entities;
		
	}
	
	public void updateDatabaseName(String origName, String newName) throws PersistenceException {
		String sql="ALTER database " + origName + " rename to "+newName+" ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void dropDatabase(String name ) throws PersistenceException {
		String sql="DROP database IF EXISTS " + name +" ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public DbAvail getByDatabaseName(String name) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(DbAvailImpl.class);
		crit.add(Restrictions.eq("name", name));
		
		DbAvail entity = (DbAvail)crit.uniqueResult();
		
		return entity;
		
	}

	public void updateLastAutoBackupDate(Long id, Date dt) throws PersistenceException {
		String sql="update isw_db_avail set last_auto_backup_date = " +
					"to_timestamp('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
					"where id = " + id + " " ;
		
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		q.executeUpdate();
	}
}
