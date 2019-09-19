package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateOvhdImpl;
import gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentCostRateOvhdQuery;
import gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.Column;

import org.hibernate.SQLQuery;

@SuppressWarnings("unchecked")
public class IncidentCostRateOvhdDaoHibernate extends TransactionSupportImpl implements IncidentCostRateOvhdDao{

	private final CrudDao<IncidentCostRateOvhd> crudDao;
	
	public IncidentCostRateOvhdDaoHibernate(final CrudDao<IncidentCostRateOvhd> crudDao)  {
		
		super();
		
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRateOvhd getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentCostRateOvhdImpl.class);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao#save(gov.nwcg.isuite.core.domain.IncidentCostRateOvhd)
	 */
	public void save(IncidentCostRateOvhd persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRateOvhd> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao#delete(gov.nwcg.isuite.core.domain.IncidentCostRateOvhd)
	 */
	public void delete(IncidentCostRateOvhd persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateOvhdDao#overwriteOvhdKindRates(gov.nwcg.isuite.core.vo.IncidentCostRateOvhdVo)
	 */
	public void overwriteOvhdKindRates(String rateType,IncidentCostRateOvhdVo vo) throws PersistenceException {

		if(rateType.equals("DIRECT")){
			// update direct
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateDirectKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}

		if(rateType.equals("INDIRECT")){
			// update indirect
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateInDirectKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
		}

		if(rateType.equals("NONSUBORDINATE")){
			// update non subordinate (single rate)
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateNonSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
		}

		if(rateType.equals("SUBORDINATE2")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
		}
		
		if(rateType.equals("SUBORDINATE")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getSubordinateRate()));	
			
			query.executeUpdate();
		}
	
	}

	public void overwriteOvhdKindRatesGroup(String rateType,IncidentCostRateOvhdVo vo,Long groupId, Collection<Long> incidentIds) throws PersistenceException {

		if(rateType.equals("DIRECT")){
			// update direct
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateDirectKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();

			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateDirectKindRatesGroup(
								super.isOracleDialect() 
								,vo.getIncidentCostRateId() 
								,vo.getDirectRate()
								,groupId));	
				
				query.executeUpdate();
			}

			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateDirectKindRatesIncId(
									super.isOracleDialect() 
									,vo.getIncidentCostRateId() 
									,vo.getDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}

		if(rateType.equals("INDIRECT")){
			// update indirect
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateInDirectKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateInDirectKindRatesGroup(
								super.isOracleDialect() 
								,vo.getIncidentCostRateId() 
								,vo.getIndirectRate()
								,groupId));	
				
				query.executeUpdate();
			}

			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateInDirectKindRatesIncId(
									super.isOracleDialect() 
									,vo.getIncidentCostRateId() 
									,vo.getIndirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
			
		}

		if(rateType.equals("NONSUBORDINATE")){
			// update non subordinate (single rate)
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateNonSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getDirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateNonSubordinateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getIncidentCostRateId() 
								,vo.getDirectRate()
								,groupId));	
				
				query.executeUpdate();
			}

			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateNonSubordinateKindRatesIncId(
									super.isOracleDialect() 
									,vo.getIncidentCostRateId() 
									,vo.getDirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
			
		}

		if(rateType.equals("SUBORDINATE2")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getIndirectRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getIncidentCostRateId() 
								,vo.getIndirectRate()
								,groupId));	
				
				query.executeUpdate();
			}

			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRatesIncId(
									super.isOracleDialect() 
									,vo.getIncidentCostRateId() 
									,vo.getIndirectRate()
									,id));	
					
					query.executeUpdate();
				}
			}
			
		}
		
		if(rateType.equals("SUBORDINATE")){
			// update subordinate
			SQLQuery query = 
				getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRates(
							super.isOracleDialect() 
							,vo.getIncidentCostRateId() 
							,vo.getSubordinateRate()));	
			
			query.executeUpdate();
			
			if(LongUtility.hasValue(groupId)){
				query = 
					getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRatesGroup(
								super.isOracleDialect() 
								,vo.getIncidentCostRateId() 
								,vo.getSubordinateRate()
								,groupId));	
				
				query.executeUpdate();
			}

			if(CollectionUtility.hasValue(incidentIds)){
				for(Long id : incidentIds){
					query = 
						getHibernateSession().createSQLQuery(IncidentCostRateOvhdQuery.getUpdateSubordinateKindRatesIncId(
									super.isOracleDialect() 
									,vo.getIncidentCostRateId() 
									,vo.getSubordinateRate()
									,id));	
					
					query.executeUpdate();
				}
			}
		}
	
	}

	public Long getIncidentId(Long icrOvhdId) throws PersistenceException {
		String sql="select incident_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_ovhd " +
				   " where id = " + icrOvhdId + " " +
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
	
	public Long getIncidentGroupId(Long icrOvhdId) throws PersistenceException {
		String sql="select incident_group_id "+
				   "from isw_inccost_rate " +
				   "where id = ("+
				   " select inccost_rate_id "+
				   " from isw_inccost_rate_ovhd " +
				   " where id = " + icrOvhdId + " " +
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

	public void saveGroupRecord(IncidentCostRateOvhd source, Long groupId) throws PersistenceException {
		String sql = "update isw_inccost_rate_ovhd " +
					 "set direct_rate = " + source.getDirectRate() + " " +
					 ", indirect_rate = " + source.getIndirectRate() + " " +
					 ", single_rate = " + source.getSingleRate() + " " +
					 ", subordinate_rate = " + source.getSubordinateRate() + " " +
					 "where inccost_rate_id = " +
					 " ( select id from isw_inccost_rate " +
					 "   where incident_group_id = " + groupId + " " +
					 "   and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + source.getIncidentCostRateId() + ") " + 
					 " ) ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
	public void saveGroupIncRecord(IncidentCostRateOvhd source, Long incId) throws PersistenceException {
		String sql = "update isw_inccost_rate_ovhd " +
					 "set direct_rate = " + source.getDirectRate() + " " +
					 ", indirect_rate = " + source.getIndirectRate() + " " +
					 ", single_rate = " + source.getSingleRate() + " " +
					 ", subordinate_rate = " + source.getSubordinateRate() + " " +
					 "where inccost_rate_id = " +
					 " ( select id from isw_inccost_rate " +
					 "   where incident_id = " + incId + " " +
					 "   and cost_rate_category = (select cost_rate_category from isw_inccost_rate where id = " + source.getIncidentCostRateId() + ") " + 
					 " ) ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}	
}