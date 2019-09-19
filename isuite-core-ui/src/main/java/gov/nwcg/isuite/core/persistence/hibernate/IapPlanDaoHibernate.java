package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.core.filter.impl.IapPlanFilterImpl;
import gov.nwcg.isuite.core.persistence.IapPlanDao;
import gov.nwcg.isuite.core.vo.IapPlanVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class IapPlanDaoHibernate extends TransactionSupportImpl implements IapPlanDao {
	private final CrudDao<IapPlan> crudDao;
	
	public IapPlanDaoHibernate(final CrudDao<IapPlan> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapPlan persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapPlan getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapPlan persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapPlan> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapDao#getAllIapPlans(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapPlan> getAllIapPlans(Long incidentId,Long incidentGroupId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapPlanImpl.class);

		if(LongUtility.hasValue(incidentId)){
			crit.add(Expression.eq("incidentId", incidentId));
		}else{
			crit.add(Expression.eq("incidentGroupId", incidentGroupId));
		}

		crit.addOrder(Order.asc("fromDate"));
		
		return crit.list();
	}

	public Collection<IapPlan> getAllIapPlans(Long incidentId,Long incidentGroupId, IapPlanFilterImpl filter) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapPlanImpl.class);

		if(LongUtility.hasValue(incidentId)){
			crit.add(Expression.eq("incidentId", incidentId));
		}else{
			crit.add(Expression.eq("incidentGroupId", incidentGroupId));
		}

		if(filter != null){
			switch(filter.treeviewDisplay)
			{
				case 0: // show all
					break;
				case 1: // by date
					//String sqlFilter = "to_timestamp(to_char(this_.from_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
					//   " > " +
					//   "to_timestamp('"+DateUtil.toDateString(filter.byDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
					//   "";
					String sqlFilter = "to_timestamp(to_char(this_.from_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
									   " > " +
									   "to_timestamp('"+DateUtil.toDateString(filter.byDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
									   "";
					crit.add(Restrictions.sqlRestriction(sqlFilter));
					break;
				case 2: // by nbr days prior
					//String sqlFilter2 = "to_timestamp(to_char(this_.from_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') " +
					//   " > " +
					//   "to_timestamp('"+DateUtil.toDateString(filter.byDate, DateUtil.MM_SLASH_DD_SLASH_YYYY_HH_MM)+"','MM/DD/YYYY HH24:MI') " +
					//   "";
					String sqlFilter2 = "to_timestamp(to_char(this_.from_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " +
									   " > " +
									   "to_timestamp('"+DateUtil.toDateString(filter.byDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') " +
									   "";
					crit.add(Restrictions.sqlRestriction(sqlFilter2));
					break;
			}
		}
		
		crit.addOrder(Order.asc("fromDate"));
		
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IapDao#getAllIapPlans(java.lang.Long, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapPlan> getAllIapPlansByPlanName(IapPlanVo vo) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(IapPlanImpl.class);

		if(StringUtility.hasValue(vo.getIncidentName()))
			crit.add(Expression.eq("incidentName", vo.getIncidentName()));
		
		/*
		 * Commented out below for QC defect #80: There is a requirement that a plan must 
		 * be unique based on the Date From, Date To and Time From, Time To for the plan.
		 * Including "OperationPeriod in the query negates this rule.  This problem also 
		 * exists in the original E-ISuite.
		 */
//		if(StringUtility.hasValue(vo.getOperationalPeriod()))
//			crit.add(Expression.eq("operationPeriod", vo.getOperationalPeriod().toUpperCase()));
//		else 
//			crit.add(Expression.eq("operationPeriod", ""));
		
		if(DateUtil.hasValue(vo.getFromDate())){
			String date1=DateUtil.toDateString(vo.getFromDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			date1=date1+" "+DateUtil.toMilitaryTime(vo.getFromDate());
			String sql1="to_date(to_char(this_.from_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') = "+
						"to_date('"+date1+"','MM/DD/YYYY HH24:MI') ";
			crit.add(Expression.sql(sql1));
		}
		
		if(DateUtil.hasValue(vo.getToDate())){
			String date2=DateUtil.toDateString(vo.getToDate(), DateUtil.MM_SLASH_DD_SLASH_YYYY);
			date2=date2+" "+DateUtil.toMilitaryTime(vo.getToDate());
			String sql2="to_date(to_char(this_.to_date, 'MM/DD/YYYY HH24:MI'),'MM/DD/YYYY HH24:MI') = "+
						"to_date('"+date2+"','MM/DD/YYYY HH24:MI') ";
			crit.add(Expression.sql(sql2));
		}
		
		//crit.add(Expression.eq("fromDate", vo.getFromDate()));
		//crit.add(Expression.eq("toDate", vo.getToDate()));
		
		if(LongUtility.hasValue(vo.getId())){
			crit.add(Expression.ne("id", vo.getId()));
		}
			
		return crit.list();
	}
	
	public void lockUnlockForms(Long iapPlanId, Boolean isLocked) throws PersistenceException {
		String sql1 = "UPDATE ISW_IAP_FORM_202 SET IS_FORM_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		String sql2 = "UPDATE ISW_IAP_FORM_203 SET IS_FORM_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		String sql3 = "UPDATE ISW_IAP_FORM_205 SET IS_FORM_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		String sql4 = "UPDATE ISW_IAP_FORM_206 SET IS_FORM_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		String sql5 = "UPDATE ISW_IAP_FORM_220 SET IS_FORM_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		String sql6 = "UPDATE ISW_IAP_BRANCH SET IS_FORM_204_LOCKED = '" + (isLocked == true ? "Y" : "N") + "' WHERE IAP_PLAN_ID = " + iapPlanId + " ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql1);
		query.executeUpdate();
		query = getHibernateSession().createSQLQuery(sql2);
		query.executeUpdate();
		query = getHibernateSession().createSQLQuery(sql3);
		query.executeUpdate();
		query = getHibernateSession().createSQLQuery(sql4);
		query.executeUpdate();
		query = getHibernateSession().createSQLQuery(sql5);
		query.executeUpdate();
		query = getHibernateSession().createSQLQuery(sql6);
		query.executeUpdate();
	}
	
	public Long getIncidentId(Long planId) throws PersistenceException {
		String sql="select incident_id from isw_iap_plan where id = " + planId + " ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=q.uniqueResult();
		
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				return id;
			}catch(Exception smother){}
		}
		return null;
	}
	
	public Long getIncidentGroupId(Long planId) throws PersistenceException {
		String sql="select incident_group_id from isw_iap_plan where id = " + planId + " ";
		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		
		Object rslt=q.uniqueResult();
		
		if(null != rslt){
			try{
				Long id=TypeConverter.convertToLong(rslt);
				return id;
			}catch(Exception smother){}
		}
		return null;
	}
}
