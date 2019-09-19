package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentResourceDailyCost;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceDailyCostImpl;
import gov.nwcg.isuite.core.filter.DailyCostFilter;
import gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentResourceDailyCostQuery;
import gov.nwcg.isuite.core.vo.ContractorRateVo;
import gov.nwcg.isuite.core.vo.DailyCostVo;
import gov.nwcg.isuite.core.vo.IacVo;
import gov.nwcg.isuite.core.vo.IncidentResourceDailyCostVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

@SuppressWarnings("unchecked")
public class IncidentResourceDailyCostDaoHibernate extends TransactionSupportImpl implements IncidentResourceDailyCostDao {

	private final CrudDao<IncidentResourceDailyCost> crudDao;

	public IncidentResourceDailyCostDaoHibernate(final CrudDao<IncidentResourceDailyCost> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IncidentResourceDailyCost persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentResourceDailyCost getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	public Collection<IncidentResourceDailyCost> getByIncidentResourceId(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);
		
		crit.add(Restrictions.eq("this.incidentResourceId",id));
		crit.addOrder(Order.asc("activityDate"));
		
		return (Collection<IncidentResourceDailyCost>)crit.list();
	}

	public Collection<IncidentResourceDailyCost> getByIncidentResourceId(Long id,Date start,Date end) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);
		
		crit.add(Restrictions.eq("this.incidentResourceId",id));
		crit.add(Restrictions.conjunction()
				.add(Restrictions.ge("activityDate", start))
				.add(Restrictions.le("activityDate", end))
		);
		
		crit.addOrder(Order.asc("activityDate"));
		
		return (Collection<IncidentResourceDailyCost>)crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getByIncidentResourceOtherId(java.lang.Long)
	 */
	public Collection<IncidentResourceDailyCost> getByIncidentResourceOtherId(Long id) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);
		
		crit.add(Restrictions.eq("this.incidentResourceOtherId",id));
		
		return (Collection<IncidentResourceDailyCost>)crit.list();
	}

	public Collection<IncidentResourceDailyCost> getByFilter(DailyCostFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);

		if(LongUtility.hasValue(filter.getIncidentResourceId()))
			crit.add(Restrictions.eq("this.incidentResourceId",filter.getIncidentResourceId()));

		if(LongUtility.hasValue(filter.getIncidentResourceOtherId()))
			crit.add(Restrictions.eq("this.incidentResourceOtherId",filter.getIncidentResourceOtherId()));
			
		if(DateUtil.hasValue(filter.getFromDate())){
			String s= "(" +
				"to_date(to_char(this_.activity_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
				" " + filter.getFromDateComparator() +" " +
				"to_date('"+DateUtil.toDateString(filter.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
				") ";
			crit.add(Restrictions.sqlRestriction(s));
		}
		
		return (Collection<IncidentResourceDailyCost>)crit.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IncidentResourceDailyCost persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentResourceDailyCost> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getResourceCosts(java.lang.Long)
	 */
	public Collection<IncidentResourceDailyCostVo> getResourceCosts(Long id) throws PersistenceException, Exception {
		
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);
		
		crit.add(Restrictions.eq("this.incidentResourceId",id));
		
		Collection<IncidentResourceDailyCost> entities = (Collection<IncidentResourceDailyCost>)crit.list();

		return IncidentResourceDailyCostVo.getInstances(entities, true);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getResourceOtherCosts(java.lang.Long)
	 */
	public Collection<IncidentResourceDailyCostVo> getResourceOtherCosts(Long id) throws PersistenceException, Exception {
		
		Criteria crit = getHibernateSession().createCriteria(IncidentResourceDailyCostImpl.class);
		
		crit.add(Restrictions.eq("this.incidentResourceOtherId",id));
		
		Collection<IncidentResourceDailyCost> entities = (Collection<IncidentResourceDailyCost>)crit.list();

		return IncidentResourceDailyCostVo.getInstances(entities, true);
	}

	public BigDecimal getParentRollupAmount(Long incidentResourceId, Long incAcctCodeId, Date activityDate) throws PersistenceException {
		
		String sql = IncidentResourceDailyCostQuery.getParentRollupQuery(incidentResourceId, incAcctCodeId, activityDate);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				BigDecimal bd = TypeConverter.convertToBigDecimal(rslt);
				if(null != bd)
					return bd;
			}catch(Exception e){
				// smother
			}
		}
		
		return BigDecimal.valueOf(0.0);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getChildUniqueAcctCodeIdsByDate(java.lang.Long, java.lang.Long, java.util.Date)
	 */
	public Collection<Long> getChildUniqueAcctCodeIdsByDate(Long incResId, Long excludeAcctCodeId, Date dt) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getChildUniqueAcctCodeIdsByDate(incResId, excludeAcctCodeId, dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Collection<Object> olist = (Collection<Object>)query.list();
		Collection<Long> list = new ArrayList<Long>();
		
		try{
			for(Object o : olist){
				Long lng=TypeConverter.convertToLong(o);
				list.add(lng);
			}
		}catch(Exception e){
			
		}
		
		return list;
	}

	public Collection<IacVo> getChildUniqueCostAcctCodeIds(Long incResId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getChildUniqueCostAcctCodeIds(incResId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(IacVo.class);
 	    
        crt.addScalar("activityDate", Date.class.getName());
        crt.addScalar("incidentAccountCodeId", Long.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getIncidentTotalCostAmount(java.lang.Long)
	 */
	public BigDecimal getIncidentTotalCostAmount(Long incidentId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getIncidentTotalCostQuery(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				BigDecimal val = TypeConverter.convertToBigDecimal(rslt);
				return val;
			}catch(Exception e){
				// smother
			}
		}
		
		return BigDecimal.valueOf(0.0);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getIncidentGroupTotalCostAmount(java.lang.Long)
	 */
	public BigDecimal getIncidentGroupTotalCostAmount(Long incidentGroupId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getIncidentGroupTotalCostQuery(incidentGroupId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				BigDecimal val = TypeConverter.convertToBigDecimal(rslt);
				return val;
			}catch(Exception e){
				// smother
			}
		}
		
		return BigDecimal.valueOf(0.0);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#getManualCostCount(java.lang.Long, java.util.Date)
	 */
	public int getManualCostCount(Long incidentResourceId, Long incidentResourceOtherId,Date dt) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getManualCostCountQuery(incidentResourceId, incidentResourceOtherId, dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				
				return cnt;
			}catch(Exception e){
				//smother
			}
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#deleteManualCost(java.lang.Long, java.util.Date)
	 */
	public void deleteManualCost(Long incidentResourceId,Long incidentResourceOtherId, Date dt) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getDeleteManualCostQuery(incidentResourceId, incidentResourceOtherId,dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}
	
	public void setCostGroupId(Long incidentResourceId, Long costGroupId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getSetCostGroupId(incidentResourceId, costGroupId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#setShiftId(java.lang.Long, java.lang.Long)
	 */
	public void setShiftId(Long incidentResourceId, Long shiftId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getSetShiftId(incidentResourceId, shiftId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#deleteCostBeforeDate(java.lang.String, java.lang.Long, java.util.Date)
	 */
	public void deleteCostBeforeDate(String resourceType,Long incidentResourceId, Date dt) throws PersistenceException {
		String sql = "";
		
		if(resourceType.equals("RESOURCE"))
			sql=IncidentResourceDailyCostQuery.getDeleteResourceCostsBeforeDateQuery(incidentResourceId, dt);
		else
			sql=IncidentResourceDailyCostQuery.getDeleteResourceOtherCostsBeforeDateQuery(incidentResourceId, dt);
				
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#deleteCostAfterDate(java.lang.String, java.lang.Long, java.util.Date)
	 */
	public void deleteCostAfterDate(String resourceType,Long incidentResourceId, Date dt) throws PersistenceException {
		String sql = "";
		
		if(resourceType.equals("RESOURCE"))
			sql=IncidentResourceDailyCostQuery.getDeleteResourceCostsAfterDateQuery(incidentResourceId, dt);
		else
			sql=IncidentResourceDailyCostQuery.getDeleteResourceOtherCostsAfterDateQuery(incidentResourceId, dt);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentResourceDailyCostDao#deleteResEstCoststByAcctCodeId(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public void deleteResEstCoststByAcctCodeId(Long incidentResourceId, Long incidentResourceOtherId, Long acctCodeId) throws PersistenceException{
		String sql = "";
		
		sql=IncidentResourceDailyCostQuery.getDeleteResEstCostsByAcctCodeIdQuery(incidentResourceId, incidentResourceOtherId,acctCodeId);
				
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
		
	}

	public void deleteResEstCosts(Long incidentResourceId, Long incidentResourceOtherId) throws PersistenceException {
		String sql = "";
		
		sql=IncidentResourceDailyCostQuery.getDeleteResEstCostsQuery(incidentResourceId, incidentResourceOtherId);
				
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public int getResourceActualsCount(Long incidentId, Long resourceId) throws PersistenceException {
		String sql=IncidentResourceDailyCostQuery.getResourceActualsCountQuery(incidentId, resourceId);

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}else
			return 0;
	}
	
	public int getSubordinateActualsCount(Long incidentId, Long resourceId) throws PersistenceException {
		String sql="";
		if(super.isOracleDialect()){
			sql=IncidentResourceDailyCostQuery.getSubordinateActualsCountQueryOra(incidentId, resourceId);
		}else{
			sql=IncidentResourceDailyCostQuery.getSubordinateActualsCountQueryPg(incidentId, resourceId);
		}
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}else
			return 0;
	}

	public String getResourceCostException(Long incidentId) throws PersistenceException {
		String sql=IncidentResourceDailyCostQuery.getResourceCostExceptionQuery(incidentId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		
		if(null != rslt){
			try{
				String val=TypeConverter.convertToString(rslt);
				return val;
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}else
			return "";
		
	}

	public Collection<DailyCostVo> getDailyCosts(Long incidentResourceId,Long incidentResourceOtherId, Date startDate) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getDailyCostQuery(incidentResourceId,incidentResourceOtherId, startDate);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(DailyCostVo.class);
 	    
        crt.addScalar("id", Long.class.getName());
        crt.addScalar("incidentResourceId", Long.class.getName());
        crt.addScalar("incidentResourceOtherId", Long.class.getName());
        crt.addScalar("accrualCodeId", Long.class.getName());
        crt.addScalar("incidentShiftId", Long.class.getName());
        crt.addScalar("costGroupId", Long.class.getName());
        crt.addScalar("incidentAccountCodeId", Long.class.getName());
        crt.addScalar("activityDate", Date.class.getName());
        crt.addScalar("units", BigDecimal.class.getName());
        crt.addScalar("unitCostAmount", BigDecimal.class.getName());
        crt.addScalar("adjustmentAmount", BigDecimal.class.getName());
        crt.addScalar("isLocked", Boolean.class.getName());
        crt.addScalar("isFlowdown", Boolean.class.getName());
        crt.addScalar("primaryTotalAmount", BigDecimal.class.getName());
        crt.addScalar("subordinateTotalAmount", BigDecimal.class.getName());
        crt.addScalar("totalCostAmount", BigDecimal.class.getName());
        crt.addScalar("aircraftCostAmount", BigDecimal.class.getName());
        crt.addScalar("flightHours", BigDecimal.class.getName());
        crt.addScalar("waterGallons", BigDecimal.class.getName());
        crt.addScalar("retardantGallons", BigDecimal.class.getName());
        crt.addScalar("cargoPounds", BigDecimal.class.getName());
        crt.addScalar("numberOfLoads", Integer.class.getName());
        crt.addScalar("numberOfTrips", Integer.class.getName());
        crt.addScalar("numberOfPassengers", Integer.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
		
	}
	
	public void persistSqls(Collection<String> sqls) throws PersistenceException {
		for(String sql : sqls){	
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
	}
	
	public Collection<ContractorRateVo> getContractorRateVos(Long cpiId) throws PersistenceException {
		String sql = IncidentResourceDailyCostQuery.getContractorRatesQuery(cpiId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(ContractorRateVo.class);
 	    
        crt.addScalar("id", Long.class.getName());
        crt.addScalar("rateAmount", BigDecimal.class.getName());
        crt.addScalar("guaranteeAmount", BigDecimal.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
		
	}
	
	public void deleteAllAfterDate(Date cutoffDate) throws PersistenceException {
		String sql="DELETE FROM isw_inc_res_daily_cost " +
			"WHERE to_timestamp(to_char(activity_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
			" > " +
			"to_timestamp('"+DateUtil.toDateString(cutoffDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " ;

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();
	}
	
}
