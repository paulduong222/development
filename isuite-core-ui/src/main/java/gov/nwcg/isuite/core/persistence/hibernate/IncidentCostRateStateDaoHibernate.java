package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentCostRateState;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateImpl;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentCostRateStateOvhdQuery;
import gov.nwcg.isuite.core.vo.IncidentCostRateStateVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class IncidentCostRateStateDaoHibernate extends TransactionSupportImpl implements IncidentCostRateStateDao{

	private final CrudDao<IncidentCostRateState> crudDao;
	
	public IncidentCostRateStateDaoHibernate(final CrudDao<IncidentCostRateState> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateState getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentCostRateStateImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#save(gov.nwcg.isuite.core.domain.IncidentCostRateState)
	 */
	public void save(IncidentCostRateState persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateState> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#delete(gov.nwcg.isuite.core.domain.IncidentCostRateState)
	 */
	public void delete(IncidentCostRateState persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#getIncidentCostRateState(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public IncidentCostRateState getIncidentCostRateState(CostRateFilter filter) throws PersistenceException {
		if(!LongUtility.hasValue(filter.getIncidentId()) && !LongUtility.hasValue(filter.getIncidentGroupId())) {
			throw new PersistenceException("IncidentId or IncidentGroupId is required");
		}
		
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateStateImpl.class);
		crit.createAlias("incidentCostRate", "incCostRate");

		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.createAlias("incCostRate.incident", "inc");

		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.createAlias("incCostRate.incidentGroup", "incGroup");
		
		//TODO: Add some projections and return only the fields we need.
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.add(Restrictions.eq("inc.id", filter.getIncidentId()));

		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.add(Restrictions.eq("incGroup.id", filter.getIncidentGroupId()));

		if(filter != null) {

			if(LongUtility.hasValue(filter.getAgencyId())) {
				crit.add(Restrictions.eq("this.agencyId", filter.getAgencyId()));
			}

		}

		return (IncidentCostRateState)crit.uniqueResult();
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateStateDao#overwriteStateOvhdKindRates(gov.nwcg.isuite.core.vo.IncidentCostRateStateVo)
	 */
	public void overwriteStateOvhdKindRates(String rateType,IncidentCostRateStateVo vo, Long groupId, Collection<Long> incidentIds) throws PersistenceException {
		
		// update non subordinate (ie --> single rate  (12/7/2012 directRate ) )
		if(rateType.equals("NONSUBORDINATE")) {
			// update non subordinate (single rate)
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateNonSubordinateStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateNonSubordinateStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getDirectRate()
								,groupId));	
				
				query.executeUpdate();
				
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateNonSubordinateStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}

		if(rateType.equals("DIRECT")) {
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getDirectRate()
								,groupId));	
				
				query.executeUpdate();
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getDirectRate()
									,id));	
					
					query.executeUpdate();
				}
				
			}
		}
	
		if(rateType.equals("INDIRECT")) {
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateIndirectStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getInDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateIndirectStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getInDirectRate()
								,groupId));	
				
				query.executeUpdate();
				
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateIndirectStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getInDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}

		if(rateType.equals("NONSUBORDINATE")) {
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getDirectRate()
								,groupId));	
				
				query.executeUpdate();
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateDirectStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}
		
		if(rateType.equals("SUBORDINATE2")) {
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getInDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getInDirectRate()
								,groupId));	
				
				query.executeUpdate();
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getInDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}
		
		if(rateType.equals("SUBORDINATE")) {
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRates(
							super.isOracleDialect() 
							,vo.getId() 
							,vo.getSubordinateRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getId() 
								,vo.getSubordinateRate()
								,groupId));	
				
				query.executeUpdate();
			}
			
			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateStateOvhdQuery.getUpdateSubordinateStateKindRatesInc(
									super.isOracleDialect() 
									,vo.getId() 
									,vo.getSubordinateRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}
	
	}

	public Long getIncidentId(Long icrStateId) throws PersistenceException {
		String sql="select incident_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_state " +
				   " where id = " + icrStateId + " " +
				   ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		try{
			Long incidentId=TypeConverter.convertToLong(rslt);
			return incidentId;
		}catch(Exception e){
			
		}
		return 0L;
	}
	
	public Long getIncidentGroupId(Long icrStateId) throws PersistenceException {
		String sql="select incident_group_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_state " +
				   " where id = " + icrStateId + " " +
				   ") ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		try{
			Long incidentGroupId=TypeConverter.convertToLong(rslt);
			return incidentGroupId;
		}catch(Exception e){
			
		}
		return 0L;
	}

	public void saveGroupRecord(IncidentCostRateState source, Long groupId) throws PersistenceException {
		String sql = "update isw_inccost_rate_state " +
					 "set direct_rate = " + source.getDirectRate() + " " +
					 ", indirect_rate = " + source.getIndirectRate() + " " +
					 ", single_rate = " + source.getSingleRate() + " " +
					 ", subordinate_rate = " + source.getSubordinateRate() + " " +
					 "where inccost_rate_id = " +
					 " ( select id from isw_inccost_rate " +
					 "   where incident_group_id = " + groupId + " " +
					 "   and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + source.getIncidentCostRateId() + ") " + 
					 " ) " +
					 " and agency_id = " + source.getAgencyId() + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	public void saveGroupIncRecord(IncidentCostRateState source, Long incId) throws PersistenceException {
		String sql = "update isw_inccost_rate_state " +
					 "set direct_rate = " + source.getDirectRate() + " " +
					 ", indirect_rate = " + source.getIndirectRate() + " " +
					 ", single_rate = " + source.getSingleRate() + " " +
					 ", subordinate_rate = " + source.getSubordinateRate() + " " +
					 "where inccost_rate_id = " +
					 " ( select id from isw_inccost_rate " +
					 "   where incident_id = " + incId + " " +
					 "   and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + source.getIncidentCostRateId() + ") " + 
					 " ) " +
					 " and agency_id = " + source.getAgencyId() + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}	
	
}