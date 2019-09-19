package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.impl.IncidentAccountCodeImpl;
import gov.nwcg.isuite.core.filter.IncidentAccountCodeFilter;
import gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentAccountCodeQuery;
import gov.nwcg.isuite.core.vo.IncidentAccountCodeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransferableDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.DuplicateItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

@SuppressWarnings("unchecked")
public class IncidentAccountCodeDaoHibernate extends TransactionSupportImpl implements IncidentAccountCodeDao {

	private final CrudDao<IncidentAccountCode> crudDao;

	/**
	 * Constructor.
	 * 
	 * @param crudDao
	 *           can't be null
	 */
	public IncidentAccountCodeDaoHibernate(final CrudDao<IncidentAccountCode> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#delete(java.lang.Long)
	 */
	public void delete(Long id) throws PersistenceException {
		IncidentAccountCode iac = getById(id);
		if (iac== null) {
			throw new PersistenceException("unable to locate object with id: " + id);
		}
		this.delete(iac);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#delete(gov.nwcg.isuite.domain.incident.Incident)
	 */
	public void delete(IncidentAccountCode iac) throws PersistenceException {
		crudDao.delete(iac);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#getById(java.lang.Long)
	 */
	public IncidentAccountCode getById(Long id) throws PersistenceException {
		return this.getById(id, IncidentAccountCodeImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentAccountCode getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentAccountCodeImpl.class);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.CrudDao#save(gov.nwcg.isuite.domain.Persistable)
	 */
	public void save(IncidentAccountCode iac) throws PersistenceException {
		crudDao.save(iac);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentAccountCode> iacs) throws PersistenceException {
		crudDao.saveAll(iacs);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIncidentAccountCodesByIncidentId(java.lang.Long)
	 */
	public Collection<IncidentAccountCodeVo> getIncidentAccountCodesByIncidentId(Long incidentId, IncidentAccountCodeFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);

		crit.createAlias("accountCode", "ac",CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("ac.agency", "ag",CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("ac.regionUnit", "o",CriteriaSpecification.LEFT_JOIN);

		if (null != filter) {
			if(StringUtility.hasValue(filter.getAccountCode())){
				crit.add(Restrictions.ilike("ac.accountCode", filter.getAccountCode(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getAgencyCode())){
				crit.add(Restrictions.ilike("ag.agencyCode", filter.getAgencyCode(), MatchMode.START));
			}
			if(StringUtility.hasValue(filter.getRegionUnitCode())){
				crit.add(Restrictions.ilike("o.code", filter.getRegionUnitCode(), MatchMode.START));
			}
			if(filter.getDeletable() != null){
			   if(filter.getDeletable() == true) {
			      crit.add(Restrictions.eq("this.defaultFlag", Boolean.FALSE));
			   } else {
			      crit.add(Restrictions.eq("this.defaultFlag", Boolean.TRUE));
			   }
			}
		}

		crit.add(Restrictions.eq("this.incidentId", incidentId));
		crit.addOrder(Order.asc("ac.accountCode"));

		crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Collection<IncidentAccountCode> entities = crit.list();

		try{
			return IncidentAccountCodeVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIncidentAccountCodesByAccountCodeId(java.lang.Long)
	 */
	public Collection<IncidentAccountCode> getIncidentAccountCodesByAccountCodeId(Long accountCodeId) throws PersistenceException {
		Query query = getHibernateSession().createQuery("from IncidentAccountCodeImpl where accountCodeId = :accountCodeId");
		query.setLong("accountCodeId", accountCodeId);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIncidentAccountCodesByOverrideAccountCodeId(java.lang.Long)
	 */
	public Collection<IncidentAccountCode> getIncidentAccountCodesByAccountCodeOrOverrideAccountCodeId(Long accountCodeId) throws PersistenceException {
		Query query = getHibernateSession().createQuery("from IncidentAccountCodeImpl i where i.accountCodeId = :accountCodeId or i.overrideAccountCodeId = :overrideAccountCodeId");
		query.setLong("accountCodeId", accountCodeId);
		Long overrideAccountCodeId = accountCodeId;
		query.setLong("overrideAccountCodeId", overrideAccountCodeId);
		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getDefaultIncidentAccountCodeByIncidentId(java.lang.Long)
	 */
	public IncidentAccountCode getDefaultIncidentAccountCodeByIncidentId(Long incidentId) throws PersistenceException {
		Query query = getHibernateSession().createQuery("from IncidentAccountCodeImpl i where i.incidentId = :incidentId and i.defaultFlag = :defaultFlag");
		query.setLong("incidentId", incidentId);
		query.setBoolean("defaultFlag", true);
		return (IncidentAccountCode)query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getEventTypeIdByIncidentId(java.lang.Long)
	 */
	public Long getEventTypeIdByIncidentId(Long incidentId) throws PersistenceException {
		Query query = getHibernateSession().createQuery("select eventTypeId from IncidentImpl i where i.id = :incidentId");
		query.setLong("incidentId", incidentId);
		return (Long)query.uniqueResult();
	}

	@Override
	public Collection<IncidentAccountCode> getGrid(Long incidentId, IncidentAccountCodeFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		crit.createAlias("accountCode", "ac");
		crit.createAlias("incident", "i");
		crit.createAlias("ac.regionUnit", "ru", CriteriaSpecification.LEFT_JOIN);
		crit.createAlias("ac.agency", "a");
//		crit.setProjection(Projections.projectionList()
//				.add( Projections.property("id"), "id")
//				.add( Projections.property("ac.accountCode"), "accountCodeVo.accountCode")
//				.add( Projections.property("a.agencyCode"), "agencyCd")
//				.add( Projections.property("defaultFlag"), "defaultFlag")
//				.add( Projections.property("ru.code"), "code") 
//				.add( Projections.property("i.incidentName"), "incidentName")
//		);
		crit.add(Restrictions.eq("this.incidentId", incidentId));
//		crit.setResultTransformer(Transformers.aliasToBean(IncidentAccountCodeGridVo.class));
		if (filter != null) {
			if (filter.getIncidentName() != null && filter.getIncidentName().trim().length() > 0) {
				crit.add(Restrictions.ilike("i.incidentName", filter.getIncidentName(), MatchMode.START));
			}
			if (filter.getAccountCode() != null && filter.getAccountCode().trim().length() > 0) {
				crit.add(Restrictions.ilike("ac.accountCode", filter.getAccountCode().trim(), MatchMode.START));
			}
			if (filter.getRegionUnitCode() != null && filter.getRegionUnitCode().trim().length() > 0) {
				crit.add(Restrictions.ilike("ru.code", filter.getRegionUnitCode().trim(), MatchMode.START));
			}
			if (filter.getAgencyCode() != null && filter.getAgencyCode().trim().length() > 0) {
				crit.add(Restrictions.ilike("a.agencyCode", filter.getAgencyCode().trim(), MatchMode.START));
			}
//			if (filter.getDefaultFlag() != null) {
//				crit.add(Restrictions.eq("this.defaultFlag", filter.getDefaultFlag()));
//			}
		}
		crit.setMaxResults(super.getMaxResultSize());
		crit.addOrder(Order.asc("ac.accountCode"));
		return crit.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIncidentAccountCodeByAccountCodeIdAndIncidentId(java.lang.Long, java.lang.Long)
	 */
	public IncidentAccountCode getIncidentAccountCodeByAccountCodeIdAndIncidentId(Long accountCodeId, Long incidentId) throws PersistenceException {
		Query query = getHibernateSession().createQuery("from IncidentAccountCodeImpl where accountCodeId = :accountCodeId and incidentId = :incidentId");
		query.setLong("accountCodeId", accountCodeId);
		query.setLong("incidentId", incidentId);
		query.setMaxResults(1);
		return (IncidentAccountCode)query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIacVoById(java.lang.Long)
	 */
	public IncidentAccountCodeVo getIacVoById(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class, "iac");
		crit.createAlias("incident", "i");
		crit.createAlias("ac.agency", "a");
		crit.createAlias("accountCode", "ac");
		crit.createAlias("ac.regionUnit", "ru", CriteriaSpecification.LEFT_JOIN);
		crit.setProjection(Projections.projectionList()
				.add(Projections.property("iac.id"), "id")
				.add(Projections.property("i.incidentName"), "incidentName")
				.add(Projections.property("a.agencyCode"), "agencyCode")
				.add(Projections.property("ac.accountCode"), "accountFireCode")
				.add(Projections.property("ru.unitCode"), "unitCode")
				.add(Projections.property("defaultFlag"), "defaultValue")
				.add(Projections.property("ac.id"), "accountCodeId")
				.add(Projections.property("i.id"), "incidentId")
				.add(Projections.property("i.eventTypeId"), "eventTypeId")
				.add(Projections.property("a.id"), "agencyId"));
		crit.setResultTransformer(Transformers.aliasToBean(IncidentAccountCodeVo.class));
		crit.add(Expression.eq("iac.id", id));
		crit.setMaxResults(1);
		return (IncidentAccountCodeVo)crit.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.persistence.incident.IncidentAccountCodeDao#getIACByAccountCode(java.lang.String)
	 */
	public IncidentAccountCode getIACByAccountCode(String accountCode) throws PersistenceException {
		Query query = getHibernateSession().createQuery("from IncidentAccountCodeImpl where accountCode.accountCode = :accountCode");
		query.setString("accountCode", accountCode);
		query.setMaxResults(1);
		return (IncidentAccountCode)query.uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao#getIACByAccountCodeAndIncidentId(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	public IncidentAccountCode getIACByAccountCodeAndIncidentId(String accountCode, Long incidentId, Long iacId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		crit.createAlias("accountCode", "ac");
		crit.add(Restrictions.eq("ac.accountCode", accountCode));
		crit.add(Restrictions.eq("incidentId", incidentId));
		if(LongUtility.hasValue(iacId)) {
			crit.add(Restrictions.ne("id", iacId));
		}
		crit.setMaxResults(1);
		return (IncidentAccountCode)crit.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao#removeOtherDefault(java.lang.Long, java.lang.Long)
	 */
	public void removeOtherDefault(Long incidentId, Long incidentAccountCodeId) throws PersistenceException {

		Query query = getHibernateSession().createSQLQuery(IncidentAccountCodeQuery.getRemoveDefaultQuery(incidentId, incidentAccountCodeId, super.isOracleDialect()));
		
		query.executeUpdate();
	}

	public void deleteBySql(Long incidentAccountCodeId) throws PersistenceException {
		String sql = "DELETE FROM ISW_INCIDENT_ACCOUNT_CODE WHERE ID = " + incidentAccountCodeId;
		
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		q.executeUpdate();
		
	}

	public Collection<IncidentAccountCode> getByAgencyAndCode(Long agencyId, String code, Long excludeIncidentId) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		crit.createAlias("accountCode", "ac");
		crit.add(Restrictions.eq("ac.agencyId", agencyId));
		crit.add(Restrictions.eq("ac.accountCode", code.toUpperCase()));
		crit.add(Restrictions.ne("incidentId", excludeIncidentId));
		
		return crit.list();
	}
	
	@Override
	public Collection<IncidentAccountCode> getByAgencyAndIncident(Long agencyId, Long incidentId, Long excludeIacId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		crit.createAlias("accountCode", "ac");
		crit.add(Restrictions.eq("ac.agencyId", agencyId));
		crit.add(Restrictions.eq("this.incidentId", incidentId));
		if(LongUtility.hasValue(excludeIacId))
			crit.add(Restrictions.ne("id", excludeIacId));
		
		return crit.list();
	}
	
	public Collection<IncidentAccountCode> getByIncidentId(Long incidentId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);
		
		crit.add(Restrictions.eq("incidentId", incidentId));
		
		return crit.list();
	}

	public Collection<IncidentAccountCode> getByIncidentGroupId(Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentAccountCodeImpl.class);

		String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+incidentGroupId+")";
		crit.add(Restrictions.sqlRestriction(sqlFilter));
		
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao#getTimePostingCount(java.lang.Long, java.lang.Long)
	 */
	public int getTimePostingCount(Long id, Long incidentId) throws PersistenceException {
	
		String sql = IncidentAccountCodeQuery.getTimePostingCount(id, incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		try{
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

	public int getTimeAdjustmentsCount(Long id, Long incidentId) throws PersistenceException {
		
		String sql = IncidentAccountCodeQuery.getTimeAdjustmentCount(id, incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		try{
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentAccountCodeDao#getResourceCount(java.lang.Long, java.lang.Long)
	 */
	public int getResourceCount(Long id, Long incidentId) throws PersistenceException {
		
		String sql = IncidentAccountCodeQuery.getResourceCount(id, incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		try{
			Object rslt = query.uniqueResult();
			if(null != rslt)
				return TypeConverter.convertToInteger(rslt).intValue();
			else
				return 0;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}	
	
	public void updateNonDefaults(Long incidentId,Long excludeIacId) throws PersistenceException {
		String sql = IncidentAccountCodeQuery.getUpdateNonDefaultsQuery(incidentId, excludeIacId,super.isOracleDialect() );
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}
	
}
