package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.domain.impl.SystemParameterImpl;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SystemParameterQuery;
import gov.nwcg.isuite.core.vo.SystemParameterVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;

public class SystemParameterDaoHibernate extends TransactionSupportImpl implements SystemParameterDao {

	private final CrudDao<SystemParameter> crudDao;

	/**
	 * Constructor.
	 * @param crudDao can't be null
	 */
	public SystemParameterDaoHibernate(final CrudDao<SystemParameter> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void delete(SystemParameter persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@Override
	public SystemParameter getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SystemParameterImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@Override
	public void save(SystemParameter persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	@Override
	public void saveAll(Collection<SystemParameter> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemParameterDao#getByParameterName(java.lang.String)
	 */
	@Override
	public SystemParameter getByParameterName(String nm) throws PersistenceException {

		Query q = getHibernateSession().createQuery(SystemParameterQuery.FIND_BY_PARAMETER_NAME_QUERY);

		q.setParameter("nm", nm);

		Collection<SystemParameter> entities = q.list();

		if( (null != entities) && (entities.size()>0) ){
			return (SystemParameter)entities.iterator().next();
		}
		
		return null;
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SystemParameterDao#getGrid()
	 */
	public Collection<SystemParameter> getGrid() throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SystemParameterImpl.class);
		
		crit.addOrder(Order.asc("id"));
		
		return crit.list();
	}
	
	public void updateParameter(SystemParameterVo vo) throws PersistenceException {
		String sql = "UPDATE ISW_SYSTEM_PARAMETER " +
					 "SET PARAMETER_VALUE='"+vo.getParameterValue() + "' " +
					 ",PARAMETER_NAME='" + vo.getParameterName()+ "' "+
					 "WHERE ID = " + vo.getId() + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void updateByName(String name, String value) throws PersistenceException {
		String sql = "UPDATE ISW_SYSTEM_PARAMETER " +
					 "SET PARAMETER_VALUE='"+value + "' " +
					 "WHERE PARAMETER_NAME='" + name+ "' ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public Collection<SystemParameterVo> getParametersForSync() throws PersistenceException {
		Collection<SystemParameterVo> vos = new ArrayList<SystemParameterVo>();
		
		String sql = "select id as id, parameter_name as parameterName, parameter_value as parameterValue from isw_system_parameter order by id";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
        CustomResultTransformer crt = new CustomResultTransformer(SystemParameterVo.class);
        crt.addScalar("id", Long.class.getName());
		query.setResultTransformer(crt);
		
		vos=query.list();
        
		return vos;
	}
	
	public void persistSqls(Collection<String> sqls) throws PersistenceException {
		for(String sql : sqls){	
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}
}
