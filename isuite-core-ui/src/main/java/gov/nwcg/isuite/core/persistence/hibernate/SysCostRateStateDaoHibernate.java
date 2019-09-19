package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SysCostRateState;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateImpl;
import gov.nwcg.isuite.core.filter.CostRateCategoryFilter;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.SysCostRateStateDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SysCostRateStateOvhdQuery;
import gov.nwcg.isuite.core.vo.SysCostRateStateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class SysCostRateStateDaoHibernate extends TransactionSupportImpl implements SysCostRateStateDao{

	private final CrudDao<SysCostRateState> crudDao;
	
	public SysCostRateStateDaoHibernate(final CrudDao<SysCostRateState> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRateState getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SysCostRateStateImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#save(gov.nwcg.isuite.core.domain.SysCostRateState)
	 */
	public void save(SysCostRateState persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRateState> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#delete(gov.nwcg.isuite.core.domain.SysCostRateState)
	 */
	public void delete(SysCostRateState persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#getSysCostRateState(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public SysCostRateState getSysCostRateState(CostRateFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SysCostRateStateImpl.class);

		if(filter != null) {
			if(LongUtility.hasValue(filter.getAgencyId())) {
				crit.add(Restrictions.eq("this.agencyId", filter.getAgencyId()));
			}
		}

		return (SysCostRateState)crit.uniqueResult();
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateStateDao#overwriteStateOvhdKindRates(gov.nwcg.isuite.core.vo.SysCostRateStateVo)
	 */
	public void overwriteStateOvhdKindRates(String rateType,SysCostRateStateVo vo) throws PersistenceException {

		if(rateType.equals("DIRECT")){
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateStateOvhdQuery.getUpdateDirectKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}
		
		if(rateType.equals("INDIRECT")){
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateStateOvhdQuery.getUpdateInDirectKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getInDirectRate()));	
			
			query.executeUpdate();
		}

		// update non subordinate (ie --> single rate  (12/7/2012 directRate ) )
		if(rateType.equals("NONSUBORDINATE")){
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateStateOvhdQuery.getUpdateNonSubordinateStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}
		
		// update subordinate
		if(rateType.equals("SUBORDINATE2")){
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateStateOvhdQuery.getUpdateSubordinateStateKindRates(
						super.isOracleDialect() 
						,vo.getId() 
						,vo.getInDirectRate()));	
			
			query.executeUpdate();
		}	
		
		if(rateType.equals("SUBORDINATE")){
			SQLQuery query = 
				getHibernateSession().createSQLQuery(SysCostRateStateOvhdQuery.getUpdateSubordinateStateKindRates(
						super.isOracleDialect() 
						,vo.getId() 
						,vo.getSubordinateRate()));	
			
			query.executeUpdate();
		}	
	}

}