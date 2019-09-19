package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateKindImpl;
import gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class IncidentCostRateKindDaoHibernate extends TransactionSupportImpl implements IncidentCostRateKindDao{

	private final CrudDao<IncidentCostRateKind> crudDao;
	
	public IncidentCostRateKindDaoHibernate(final CrudDao<IncidentCostRateKind> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateKind getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentCostRateKindImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao#save(gov.nwcg.isuite.core.domain.IncidentCostRateKind)
	 */
	public void save(IncidentCostRateKind persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateKind> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateKindDao#delete(gov.nwcg.isuite.core.domain.IncidentCostRateKind)
	 */
	public void delete(IncidentCostRateKind persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}
	
	public IncidentCostRateKind getByKind(String type, Long incidentId, Long kindId) throws PersistenceException{
		IncidentCostRateKind entity = null;
		
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateKindImpl.class);

		String s="this_.inccost_rate_id in ("+
				"  select id from isw_inccost_rate where cost_rate_category='"+type+"' " +
				"   and incident_id = " + incidentId + ")";
		crit.add(Restrictions.sqlRestriction(s));

		s="this_.kind_id = "+kindId+"";
		crit.add(Restrictions.sqlRestriction(s));
		
		entity=(IncidentCostRateKind)crit.uniqueResult();
		
		return entity;
	}

	public Long getIncidentId(Long icrkId) throws PersistenceException {
		String sql="select incident_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_kind " +
				   " where id = " + icrkId + " " +
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
	
	public Long getIncidentGroupId(Long icrkId) throws PersistenceException {
		String sql="select incident_group_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_kind " +
				   " where id = " + icrkId + " " +
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
	
	
	public void updateIncidents(IncidentCostRateKind irck, Collection<Long> incidentIds) throws PersistenceException {
		for(Long id : incidentIds){
			String sql1="select id " + 
						"from isw_inccost_rate_kind "+
						   "where inccost_rate_id = (" +
						   "  select id "+
						   "  from isw_inccost_rate "+
						   "  where incident_id = " + id + " " +
						   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
						   ") " +
						   "and kind_id = " + irck.getKindId() + " ";
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
			Object rslt1=query1.uniqueResult();
			Long updateId=0L;
			try{
				updateId=TypeConverter.convertToLong(rslt1);
			}catch(Exception ee){
				
			}
			
			if(LongUtility.hasValue(updateId)){
				String sql="update isw_inccost_rate_kind " +
				   "set rate_type='"+irck.getRateType()+"' "+
				   ", rate_amount="+irck.getRateAmount()+" " +
				   "where inccost_rate_id = (" +
				   "  select id "+
				   "  from isw_inccost_rate "+
				   "  where incident_id = " + id + " " +
				   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
				   ") " +
				   "and kind_id = " + irck.getKindId() + " ";

				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}
	}

	public void updateGroupAndIncidents(IncidentCostRateKind irck, Collection<Long> incidentIds, Long groupId) throws PersistenceException {
		String sql2="select id " + 
					"from isw_inccost_rate_kind "+
					   "where inccost_rate_id = (" +
					   "  select id "+
					   "  from isw_inccost_rate "+
					   "  where incident_group_id = " + groupId + " " +
					   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
					   ") " +
					   "and kind_id = " + irck.getKindId() + " ";
		SQLQuery query2 = getHibernateSession().createSQLQuery(sql2);
		Object rslt2=query2.uniqueResult();
		Long updateId=0L;
		try{
			updateId=TypeConverter.convertToLong(rslt2);
			if(LongUtility.hasValue(updateId)){
				String sql="update isw_inccost_rate_kind " +
					   "set rate_type='"+irck.getRateType()+"' "+
					   ", rate_amount="+irck.getRateAmount()+" " +
					   "where inccost_rate_id = (" +
					   "  select id "+
					   "  from isw_inccost_rate "+
					   "  where incident_group_id = " + updateId + " " +
					   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
					   ") " +
					   "and kind_id = " + irck.getKindId() + " ";

				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}catch(Exception ee){
		}

		for(Long id : incidentIds){
			String sql1="select id " + 
						"from isw_inccost_rate_kind "+
						   "where inccost_rate_id = (" +
						   "  select id "+
						   "  from isw_inccost_rate "+
						   "  where incident_id = " + id + " " +
						   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
						   ") " +
						   "and kind_id = " + irck.getKindId() + " ";
			SQLQuery query1 = getHibernateSession().createSQLQuery(sql1);
			Object rslt1=query1.uniqueResult();
			updateId=0L;
			try{
				updateId=TypeConverter.convertToLong(rslt1);
			}catch(Exception ee){
				
			}
			
			if(LongUtility.hasValue(updateId)){
				String sql="update isw_inccost_rate_kind " +
				   "set rate_type='"+irck.getRateType()+"' "+
				   ", rate_amount="+irck.getRateAmount()+" " +
				   "where inccost_rate_id = (" +
				   "  select id "+
				   "  from isw_inccost_rate "+
				   "  where incident_id = " + id + " " +
				   "  and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + irck.getIncidentCostRateId()+" ) " +
				   ") " +
				   "and kind_id = " + irck.getKindId() + " ";

				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				query.executeUpdate();
			}
		}
	}

}