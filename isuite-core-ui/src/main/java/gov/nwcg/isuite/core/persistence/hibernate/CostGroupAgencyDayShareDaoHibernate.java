package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDayShare;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDayShareImpl;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

public class CostGroupAgencyDayShareDaoHibernate extends TransactionSupportImpl implements CostGroupAgencyDayShareDao {
	private final CrudDao<CostGroupAgencyDayShare> crudDao;
	
	public CostGroupAgencyDayShareDaoHibernate(final CrudDao<CostGroupAgencyDayShare> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao#getGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostGroupAgencyDayShare> getGrid(Long costGroupId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(CostGroupAgencyDayShareImpl.class);
		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		crit.add(Expression.eq("costGroup.id", costGroupId));
		crit.add(Expression.isNull("deletedDate"));
		
		return crit.list();
	}
	
	public void delete(CostGroupAgencyDayShare persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public CostGroupAgencyDayShare getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	public void save(CostGroupAgencyDayShare persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	public void saveAll(Collection<CostGroupAgencyDayShare> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupAgencyDayShareDao#getByShareDate(java.util.Date)
	 */
	public CostGroupAgencyDayShare getByShareDate(Date shareDate, Long costGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostGroupAgencyDayShareImpl.class);

		crit.add(Restrictions.eq("this.costGroupId", costGroupId));
		crit.add(Restrictions.isNull("this.deletedDate"));
		
		String sql="";
		sql="" +
			"to_date(to_char(this_.agency_share_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
			" = " +
			"to_date('"+DateUtil.toDateString(shareDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " ;

		crit.add(Restrictions.sqlRestriction(sql));
		
		return (CostGroupAgencyDayShare)crit.uniqueResult();
	}

	public CostGroupAgencyDayShare getLastByCostGroup(Long costGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostGroupAgencyDayShareImpl.class);

		crit.add(Restrictions.eq("costGroupId", costGroupId));
		crit.add(Restrictions.isNull("deletedDate"));
		
		String sql="";
		sql="" +
			"this_.id=(select max(id) from isw_cost_group_ag_ds where cost_group_id = " + costGroupId+") ";

		crit.add(Restrictions.sqlRestriction(sql));

		CostGroupAgencyDayShare cgads=(CostGroupAgencyDayShare)crit.uniqueResult();
		
		return cgads;
		//return (CostGroupAgencyDayShare)crit.uniqueResult();
	}

	public void deleteAllBeforeDate(Long costGroupId, Date startDate) throws PersistenceException {
		String sql="DELETE FROM isw_cost_group_ag_ds " +
			"WHERE cost_group_id = " + costGroupId + " " +
			"AND " +
			"to_date(to_char(agency_share_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
			" < " +
			"to_date('"+DateUtil.toDateString(startDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	public void deleteAllAfterDate(Date cutoffDate) throws PersistenceException {
		String sql="DELETE FROM isw_cost_group_ag_ds " +
			"WHERE to_timestamp(to_char(agency_share_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" > " +
			"to_timestamp('"+DateUtil.toDateString(cutoffDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
}
