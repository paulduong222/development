package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentResourceOther;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceOtherImpl;
import gov.nwcg.isuite.core.filter.OtherResourceCostFilter;
import gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostResourceDataQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentResourceOtherQuery;
import gov.nwcg.isuite.core.vo.CostResourceDataVo;
import gov.nwcg.isuite.core.vo.IncidentResourceOtherGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;


public class IncidentResourceOtherDaoHibernate extends TransactionSupportImpl implements IncidentResourceOtherDao {

	private final CrudDao<IncidentResourceOther> crudDao;

	public IncidentResourceOtherDaoHibernate(final CrudDao<IncidentResourceOther> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	public Collection<IncidentResourceOtherGridVo> getGrid(OtherResourceCostFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceOtherImpl.class);
		
		crit.setFetchMode("incident",FetchMode.JOIN);
		crit.createCriteria("incident", "inc");
		
		
		if(null != filter){
			if(LongUtility.hasValue(filter.getIncidentId())){
				crit.add(Restrictions.eq("inc.id", filter.getIncidentId()));
			}
				
			if(LongUtility.hasValue(filter.getIncidentGroupId())){
				String sqlFilter = "this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = "+filter.getIncidentGroupId()+")";
				crit.add(Restrictions.sqlRestriction(sqlFilter));
			}
			
			// Filter on ResourceOtherId
			if(LongUtility.hasValue(filter.getIncidentResourceOtherId())){
				crit.add(Restrictions.eq("id", filter.getIncidentResourceOtherId()));
			}
		}
		
		Collection<IncidentResourceOther> entities = crit.list();

		Collection<IncidentResourceOtherGridVo> vos = IncidentResourceOtherGridVo.getInstances(entities);
		
		return vos;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentResourceOther persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentResourceOther getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentResourceOtherImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentResourceOther persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentResourceOther> persistables) throws PersistenceException {

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao#getTopLevelResources(java.lang.Long)
	 */
	public Collection<IncidentResourceOther> getTopLevelResources(Long incidentId) throws PersistenceException {
		String sql = IncidentResourceOtherQuery.getTopLevelResources(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		Collection<IncidentResourceOther> entities = new ArrayList<IncidentResourceOther>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				entities.add(this.getById(id, IncidentResourceOtherImpl.class));
			}catch(Exception e){
				//smother
			}
		}
		
		return entities;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceOtherDao#getTopLevelResourcesIG(java.lang.Long)
	 */
	public Collection<IncidentResourceOther> getTopLevelResourcesIG(Long incidentGroupId) throws PersistenceException {
		String sql = IncidentResourceOtherQuery.getTopLevelResourcesIG(incidentGroupId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> list = (Collection<Object>)query.list();
		Collection<IncidentResourceOther> entities = new ArrayList<IncidentResourceOther>();
		
		for(Object l : list){
			try{
				Long id = TypeConverter.convertToLong(l);
				entities.add(this.getById(id, IncidentResourceOtherImpl.class));
			}catch(Exception e){
				//smother
			}
		}
		
		return entities;
	}

	public Object getCostDataValue(String field, Long id) throws PersistenceException {
		String sql = "";
		
		if(field.equals("ASSIGN_DATE")){
			sql = "SELECT "+field + " FROM ISW_COST_DATA " +
				  "WHERE ID = " + "(SELECT COST_DATA_ID FROM ISW_INCIDENT_RESOURCE_OTHER WHERE ID = " + id + ") ";
		}else{
			sql = "SELECT "+field + " FROM ISW_RESOURCE_OTHER " +
			  "WHERE ID = " + "(SELECT RESOURCE_OTHER_ID FROM ISW_INCIDENT_RESOURCE_OTHER WHERE ID = " + id + ") ";
		}
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=query.uniqueResult();
		
		return rslt;
	}

	public void updateDefaultCostGroup(Long costDataId, Long cgId) throws PersistenceException{
		String sql = "update isw_cost_data set default_cost_group_id = " + cgId + " where id = " + costDataId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}

	public Collection<CostResourceDataVo> getCostResourceData(Long incidentResourceOtherId,Long incidentId,Long incidentGroupId) throws PersistenceException {
		Collection<CostResourceDataVo> vos = new ArrayList<CostResourceDataVo>();
		
		String sql = CostResourceDataQuery.getResourceOtherCostDataQuery(incidentResourceOtherId,incidentId,incidentGroupId,super.isOracleDialect());

		SQLQuery query = getHibernateSession().createSQLQuery(sql);

        CustomResultTransformer crt = new CustomResultTransformer(CostResourceDataVo.class);

        crt.addScalar("incidentResourceOtherId", Long.class.getName());
        crt.addScalar("resourceOtherId", Long.class.getName());
        crt.addScalar("agencyId", Long.class.getName());
        crt.addScalar("kindId", Long.class.getName());
        crt.addScalar("defIncidentAccountCodeId", Long.class.getName());
        crt.addScalar("assignDate", Date.class.getName());
        crt.addScalar("releaseDate", Date.class.getName());
        crt.addScalar("generateCosts", Boolean.class.getName());
        crt.addScalar("defaultHours", Integer.class.getName());
        crt.addScalar("accrualCodeId", Long.class.getName());
        crt.addScalar("costDataId", Long.class.getName());
        //crt.addScalar("estimateRate", BigDecimal.class.getName());
        //crt.addScalar("estimateStateCustomRate", BigDecimal.class.getName());
        crt.addScalar("contractorRate", BigDecimal.class.getName());
        crt.addScalar("fedRate", BigDecimal.class.getName());
        crt.addScalar("stateRate", BigDecimal.class.getName());
        crt.addScalar("stateCustomRate", BigDecimal.class.getName());
        crt.addScalar("endDate", Date.class.getName());
        crt.addScalar("incidentStartDate", Date.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
		
	}
	
	public void persistSqls(Collection<String> sqls) throws PersistenceException {
		for(String sql : sqls){	
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}

	public int getUnlockedCostRecordCount(Long irId) throws PersistenceException {
		String sql = "SELECT COUNT(ID) FROM ISW_INC_RES_DAILY_COST " +
					 "WHERE INCIDENT_RESOURCE_OTHER_ID = " + irId + " " +
					 "AND (IS_LOCKED IS NULL OR IS_LOCKED = "+(super.isOracleDialect() ? 0 : false)+") ";

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object val = query.uniqueResult();
		
		int count=0;
		
		try{
			if(null != val)
				count=TypeConverter.convertToInt(val);
		}catch(Exception e){
			// smother
		}
		
		return count;
	}

	public Long getAccrualCodeId(Long iroId) throws PersistenceException {
		String sql="select accrual_code_id "+
				   "from isw_cost_data " +
				   "where id = ( " +
				   "   select cost_data_id " +
				   "   from isw_incident_resource_other " +
				   "   where id = " + iroId + ") " ;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object val=query.uniqueResult();
		if(null == val)
			return 0L;
		else{
			Long id = 0L;
			try{
				id=TypeConverter.convertToLong(val);
			}catch(Exception e){}
			
			return id;
		}
	}

	public Boolean getAccrualCodeLocked(Long iroId) throws PersistenceException {
		String sql="select is_accrual_locked "+
				   "from isw_cost_data " +
				   "where id = ( " +
				   "   select cost_data_id " +
				   "   from isw_incident_resource_other " +
				   "   where id = " + iroId + ") " ;
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object val=query.uniqueResult();
		
		if(null == val){
			return false;
		}else{
			Boolean islocked = false;
			try{
				islocked=TypeConverter.convertToBoolean(val);
			}catch(Exception e){}
			
			return islocked;
		}
	}

	public Long getIncidentId(Long resOtherId) throws PersistenceException {
		String sql="select incident_id from isw_incident_resource_other where resource_other_id = " + resOtherId + " ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt = query.uniqueResult();
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				return id;
			}catch(Exception smother){}
		}
		
		return null;
	}

}
