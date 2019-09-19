package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateStateKindImpl;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.IncidentCostRateStateKindDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class IncidentCostRateStateKindDaoHibernate extends TransactionSupportImpl implements IncidentCostRateStateKindDao {

	private final CrudDao<IncidentCostRateStateKind> crudDao;
	
	public IncidentCostRateStateKindDaoHibernate(final CrudDao<IncidentCostRateStateKind> crudDao)  {

		super();

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<IncidentCostRateStateKind> getIncidentCostRateStateKinds(CostRateFilter filter) throws PersistenceException {
		if(!LongUtility.hasValue(filter.getIncidentId()) && !LongUtility.hasValue(filter.getIncidentGroupId())) {
			throw new PersistenceException("IncidentId or IncidentGroupId is required");
		}
		
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateStateKindImpl.class);

		crit.createAlias("kind", "k");
		crit.createAlias("k.requestCategory", "rc");
		crit.createAlias("incidentCostRateState", "costRateState");
		crit.createAlias("costRateState.incidentCostRate", "costRate");

		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.createAlias("costRate.incident", "inc");

		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.createAlias("costRate.incidentGroup", "incGroup");
		
		if(StringUtility.hasValue(filter.getRequestCategory()) && filter.getRequestCategory().equals("OTHER")) {
			crit.add(Restrictions.not(Restrictions.in("rc.code", new String[]{"O", "E", "C"})));
		} else if(StringUtility.hasValue(filter.getRequestCategory())) {
			crit.add(Restrictions.eq("rc.code", filter.getRequestCategory().toUpperCase()));
		}
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.add(Restrictions.eq("inc.id", filter.getIncidentId()));

		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.add(Restrictions.eq("incGroup.id", filter.getIncidentGroupId()));
		
		crit.add(Restrictions.eq("costRateState.agencyId", filter.getAgencyId()));

		return crit.list();
	}

	@Override
	public void save(IncidentCostRateStateKind persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void delete(IncidentCostRateStateKind persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public IncidentCostRateStateKind getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentCostRateStateKindImpl.class);
	}

	@Override
	public void saveAll(Collection<IncidentCostRateStateKind> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public IncidentCostRateStateKind getByKind(String type, Long incidentId, Long kindId, Long agencyId) throws PersistenceException{
		IncidentCostRateStateKind entity = null;
		
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateStateKindImpl.class);

		String s="this_.inccost_rate_state_id in ("+
	    			"select id from isw_inccost_rate_state " +
	    			"where inccost_rate_id in " +
	    			"  ( " +
	    			"		select id from isw_inccost_rate " +
	    			"   	where cost_rate_category='STATE_COOP_CUSTOM'" +
	    			"		and incident_id = " + incidentId + " " +
	    			"   ) " +
	    			"   and agency_id = " + agencyId + " " +
	    			") ";
	    			
		crit.add(Restrictions.sqlRestriction(s));

		s="this_.kind_id = "+kindId+"";
		crit.add(Restrictions.sqlRestriction(s));
		
		entity=(IncidentCostRateStateKind)crit.uniqueResult();
		
		return entity;
	}

	public Long getIncidentId(Long icrStateKindId) throws PersistenceException {
		String sql="select incident_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_state " +
				   " where id = (select inccost_rate_state_id from isw_inccost_rate_state_kind where id = " + icrStateKindId + ") " +
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
	
	public Long getIncidentGroupId(Long icrStateKindId) throws PersistenceException {
		String sql="select incident_group_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_state " +
				   " where id = (select inccost_rate_state_id from isw_inccost_rate_state_kind where id = " + icrStateKindId + ") " +
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

	public void saveGroupRecord(IncidentCostRateStateKind source, Long groupId) throws PersistenceException {
		String sql = "update isw_inccost_rate_state_kind " +
				 "set rate_type = '" + source.getRateType() + "' " +
				 ", rate_amount = " + source.getRateAmount() + " " +
				 "where kind_id = " + source.getKindId() + " " +
				 "and id in (" + 
				 "   select id "+
				 "   from isw_inccost_rate_state_kind "+
				 "   where inccost_rate_state_id = " +
				 "     (select id from isw_inccost_rate_state " +
				 "       where inccost_rate_id = " + 
				 "         (select id from isw_inccost_rate "+
				 "          where cost_rate_category = 'STATE_COOP_CUSTOM' " +
				 "          and incident_group_id = " + groupId + " " +
				 "          ) " +
				 "       and agency_id = (select agency_id from isw_inccost_rate_state where id = " + source.getIncidentCostRateStateId() +") " + 
				 "      ) " +
				 ") "; 
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	public void saveGroupIncRecord(IncidentCostRateStateKind source, Long incId) throws PersistenceException {
		String sql = "update isw_inccost_rate_state_kind " +
					 "set rate_type = '" + source.getRateType() + "' " +
					 ", rate_amount = " + source.getRateAmount() + " " +
					 "where kind_id = " + source.getKindId() + " " +
					 "and id in (" + 
					 "   select id "+
					 "   from isw_inccost_rate_state_kind "+
					 "   where inccost_rate_state_id = " +
					 "     (select id from isw_inccost_rate_state " +
					 "       where inccost_rate_id = " + 
					 "         (select id from isw_inccost_rate "+
					 "          where cost_rate_category = 'STATE_COOP_CUSTOM' " +
					 "          and incident_id = " + incId + " " +
					 "          ) " +
					 "       and agency_id = (select agency_id from isw_inccost_rate_state where id = " + source.getIncidentCostRateStateId() +") " + 
					 "      ) " +
					 ") "; 
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}	
	
}
