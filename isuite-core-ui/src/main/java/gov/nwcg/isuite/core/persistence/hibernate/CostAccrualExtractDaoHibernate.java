package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CostAccrualExtract;
import gov.nwcg.isuite.core.domain.impl.CostAccrualExtractImpl;
import gov.nwcg.isuite.core.persistence.CostAccrualExtractDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostAccrualExtractQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostAccrualReportQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.FinancialExportQuery;
import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualAllDetailSubReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualDetailReportData;
import gov.nwcg.isuite.core.reports.data.CostAccrualSummaryReportData;
import gov.nwcg.isuite.core.vo.CostAccrualExtractVo;
import gov.nwcg.isuite.core.vo.CostAccrualGroupVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;
import gov.nwcg.isuite.xml.financialexport.AccountingCodeSummaryType;
import gov.nwcg.isuite.xml.financialexport.AccrualDetailType;
import gov.nwcg.isuite.xml.financialexport.RCLineSummaryType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CostAccrualExtractDaoHibernate extends TransactionSupportImpl implements CostAccrualExtractDao {

	private final CrudDao<CostAccrualExtract> crudDao;

	/**
	 * Constructor.
	 * @param crudDao can't be null
	 * @param crudDaoRsc can't be null
	 */
	public CostAccrualExtractDaoHibernate(final CrudDao<CostAccrualExtract> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(CostAccrualExtract persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public CostAccrualExtract getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(CostAccrualExtract persistable) throws PersistenceException {
        crudDao.save(persistable);
        
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<CostAccrualExtract> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getGrid(java.lang.Long, java.lang.Long)
	 */
	public Collection<CostAccrualExtractVo> getGrid(Long incidentId, Long incidentGroupId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);
		
		if(LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));
		
		if(LongUtility.hasValue(incidentGroupId)){
			//crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
			String sql = "( "+
					     "  this_.incident_group_id = " + incidentGroupId + " " +
			             "  or " +
			             "  this_.incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId+") and this_.is_from_single_incident = 'Y' " +
			             ") ";
			crit.add(Restrictions.sqlRestriction(sql));
		}
		
		crit.addOrder(Order.desc("extractDate"));
		
		Collection<CostAccrualExtract> entities = crit.list();
		
		try{
			return CostAccrualExtractVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}	
	}

	public CostAccrualExtract getExtractByDate(Long incidentId,Long incidentGroupId,Date date) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);

		if(LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));
		
		if(LongUtility.hasValue(incidentGroupId))
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
			
		String sql = "to_date(to_char(this_.extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') = to_date('" +
			(DateUtil.toDateString(date, DateUtil.MM_SLASH_DD_SLASH_YYYY)) + "','MM/DD/YYYY') ";
		
		crit.add(Restrictions.sqlRestriction(sql));

		return (CostAccrualExtract)crit.uniqueResult();
	}

	public int getExtractFinalizedCountByDate(Long incidentId,Long incidentGroupId,Date date) throws PersistenceException {
		String sql="select count(id) " +
				   "from isw_cost_accrual_extract " + 
				   "where to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') = to_date('" +
				   	(DateUtil.toDateString(date, DateUtil.MM_SLASH_DD_SLASH_YYYY)) + "','MM/DD/YYYY') " +
				   	"and finalized = 'Y' "
				   	;
		
		if(LongUtility.hasValue(incidentId) && LongUtility.hasValue(incidentGroupId)){
			sql=sql+"and (incident_id = " + incidentId + " " +
					"		or incident_group_id = " + incidentGroupId + ") ";
		}else if(LongUtility.hasValue(incidentId) && !LongUtility.hasValue(incidentGroupId)){
			sql=sql+"and incident_id = " + incidentId + " ";
		}else if(!LongUtility.hasValue(incidentId) && LongUtility.hasValue(incidentGroupId)){
			sql=sql+"and ( incident_group_id = " + incidentGroupId + " " +
					 "        or incident_id in (select incident_id from isw_incident_group_incident where incident_group_id = " + incidentGroupId +  ")) ";
		}

		SQLQuery q = getHibernateSession().createSQLQuery(sql);
		Object rslt = q.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception ee){}
		}
		
		return 0;
		
	}

	public int getExtractCountBeforeDate(Long incidentId,Long incidentGroupId,Date date) throws PersistenceException {
		
		String sql="SELECT COUNT(*) FROM ISW_COST_ACCRUAL_EXTRACT " +
				   "WHERE INCIDENT_ID = " + incidentId + " " + 
				   "AND to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('" +
				   		(DateUtil.toDateString(date, DateUtil.MM_SLASH_DD_SLASH_YYYY)) + "','MM/DD/YYYY') ";
				

		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object val=query.uniqueResult();
		
		int cnt=0;
		
		try{
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}
	
	public Collection<CostAccrualExtract> getExtractsBeforeDate(Long incidentId,Long incidentGroupId,Date date,String orderDirection) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);

		crit.add(Restrictions.eq("incidentId", incidentId));
		
		String sql = "to_date(to_char(this_.extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('" +
			(DateUtil.toDateString(date, DateUtil.MM_SLASH_DD_SLASH_YYYY)) + "','MM/DD/YYYY') ";
		
		crit.add(Restrictions.sqlRestriction(sql));

		if(orderDirection.equals("ASC"))
			crit.addOrder(Order.asc("extractDate"));
		else
			crit.addOrder(Order.desc("extractDate"));
	
		return crit.list();
	}
	
	public Collection<CostAccrualExtract> getUnFinalExtractsBeforeDate(Long incidentId,Long incidentGroupId,Date date,String orderDirection) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);

		if(LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));

		if(LongUtility.hasValue(incidentGroupId))
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		
		String sql = "to_date(to_char(this_.extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') < to_date('" +
			(DateUtil.toDateString(date, DateUtil.MM_SLASH_DD_SLASH_YYYY)) + "','MM/DD/YYYY') ";
		
		crit.add(Restrictions.sqlRestriction(sql));
		crit.add(Restrictions.sqlRestriction("this_.finalized = 'N'"));
		
		if(orderDirection.equals("ASC"))
			crit.addOrder(Order.asc("extractDate"));
		else
			crit.addOrder(Order.desc("extractDate"));
	
		return crit.list();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getLastFinalizedExtract(java.lang.Long, java.lang.Long)
	 */
	public CostAccrualExtractImpl getLastFinalizedExtract(Long incidentId, Long incidentGroupId) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);
		
		if(LongUtility.hasValue(incidentId))
			crit.add(Restrictions.eq("incidentId", incidentId));
		
		if(LongUtility.hasValue(incidentGroupId))
			crit.add(Restrictions.eq("incidentGroupId", incidentGroupId));
		
		crit.add(Restrictions.isNotNull("finalizedDate"));
		crit.addOrder(Order.desc("finalizedDate"));

		Collection<CostAccrualExtract> extracts = crit.list();
		
		try{
			if(CollectionUtility.hasValue(extracts)){
				return (CostAccrualExtractImpl)extracts.iterator().next();
			}
			//return CostAccrualExtractVo.getInstance(extract, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}
		
		return null;
	}

	public int getCostAccrualExtractResourcesPrevYearCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResCountPrevYearQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		
		try{
			Object val=query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}
	
	// resource other count
	public int getCostAccrualExtractOTResourcesPrevYearCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResOtCountPrevYearQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		
		try{
			Object val=query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}

	public int getCostAccrualExtractResourcesCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractResourcesCountQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		
		try{
			Object val=query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}
	
	//OTHER RESOURCES
	public int getCostAccrualExtractOTResourcesCount(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractOTResourcesCountQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		
		try{
			Object val=query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#createCostAccrualExtractResources(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	public void createCostAccrualExtractResources(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractResourcesQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	// OTHER RESOURCES
	public void createCostAccrualExtractResourcesOT(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractResourcesOTQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public void createCostAccrualExtResPrevYear(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResPrevYearQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public void createCostAccrualExtResOTPrevYear(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResOTPrevYearQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#createCostAccrualExtractResourcesIG(java.lang.Long, java.lang.Long, java.lang.String, java.lang.String)
	 */
	public void createCostAccrualExtractResourcesIG(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractResourcesIGQuery(extractId, incidentGroupId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}
	
	public void createCostAccrualExtResPrevYearIG(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResPrevYearIGQuery(extractId, incidentGroupId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public int getCostAccrualExtResPrevYearIGCount(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtResPrevYearIGCountQuery(extractId, incidentGroupId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		try{
			Object val = query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}
	
	public int getCostAccrualExtractResourcesIGCount(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateCostAccrualExtractResourcesIGCountQuery(extractId, incidentGroupId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		int cnt=0;
		try{
			Object val = query.uniqueResult();
			cnt=TypeConverter.convertToInt(val);
		}catch(Exception e){}
		
		return cnt;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#createADDrawDownResources(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public void createADDrawDownResources(Long extractId,Long incidentId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateADDrawDownQuery(extractId, incidentId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#createADDrawDownResourcesIG(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public void createADDrawDownResourcesIG(Long extractId,Long incidentGroupId, String extractDateString, String fiscalDate, String fiscalYear) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getCreateADDrawDownIGQuery(extractId, incidentGroupId, extractDateString, fiscalDate, fiscalYear, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#updateExtractResourceChangeAmount(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public void updateExtractResourceChangeAmount(Long extractId,Long previousId, String extractDateString,String fiscalDate) throws PersistenceException {
		String sql = "";
		SQLQuery query = null;
		
		if(LongUtility.hasValue(previousId)){
			//sql=CostAccrualExtractQuery.getUpdateChangeAmountQuery2(extractId, incidentId, extractDateString, fiscalDate,super.isOracleDialect());
			// regular resources
			sql=CostAccrualExtractQuery.getUpdateChangeAmountQuery3(extractId, previousId, extractDateString, fiscalDate,super.isOracleDialect());
			
			query = getHibernateSession().createSQLQuery(sql);

			query.executeUpdate();

			// other resources
			sql=CostAccrualExtractQuery.getUpdateChangeAmountQuery3OT(extractId, previousId, extractDateString, fiscalDate,super.isOracleDialect());
			
			query = getHibernateSession().createSQLQuery(sql);

			query.executeUpdate();
		}
		
		sql="UPDATE isw_cost_accrual_ext_rsc set change_amount = 0 where change_amount is null and cost_accrual_extract_id = "+extractId + " ";
		
		query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#updateExtractResourceChangeAmountIG(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public void updateExtractResourceChangeAmountIG(Long extractId,Long previousId, String extractDateString,String fiscalDate) throws PersistenceException {
		String sql = "";
		SQLQuery query = null;
		
		if(LongUtility.hasValue(previousId)){
			sql=CostAccrualExtractQuery.getUpdateChangeAmountIGQuery3(extractId, previousId, extractDateString, fiscalDate,super.isOracleDialect());
			
			query = getHibernateSession().createSQLQuery(sql);

			query.executeUpdate();
		}
		
		sql="UPDATE isw_cost_accrual_ext_rsc set change_amount = 0 where change_amount is null and cost_accrual_extract_id = "+extractId + " ";
		
		query = getHibernateSession().createSQLQuery(sql);

		query.executeUpdate();
	}

	public BigDecimal getTotalAmountByExtractId(Long extractId) throws PersistenceException {
		BigDecimal changeAmount = new BigDecimal(0.0);
		String sql = CostAccrualExtractQuery.getTotalAmountByExtractIdQuery(extractId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				changeAmount=TypeConverter.convertToBigDecimal(rslt);
			}catch(Exception e){
				
			}
		}
		
		return changeAmount;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getTotalChangeAmountByExtractId(java.lang.Long)
	 */
	public BigDecimal getTotalChangeAmountByExtractId(Long extractId) throws PersistenceException {
		BigDecimal changeAmount = new BigDecimal(0.0);
		String sql = CostAccrualExtractQuery.getTotalChangeAmountByExtractIdQuery(extractId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		Object rslt=query.uniqueResult();
		if(null != rslt){
			try{
				changeAmount=TypeConverter.convertToBigDecimal(rslt);
			}catch(Exception e){
				
			}
		}
		
		return changeAmount;
	}

	public Long getPreviousExtractId(Long extractId, Long incidentId,Long incidentGroupId) throws PersistenceException {
		Long rtnVal=null;
		
		String sql = "select max(id) "+
					 "from isw_cost_accrual_extract " +
					 "where extract_date < ( " +
					 "select extract_date " +
					 "from isw_cost_accrual_extract " +
					 "where id = " + extractId + " " +
					 ") "; 
		if(LongUtility.hasValue(incidentId))
			sql=sql+"and incident_id = " + incidentId + " ";	
		
		if(LongUtility.hasValue(incidentGroupId))
			sql=sql+"and incident_group_id = " + incidentGroupId + " ";	
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		Object rslt = query.uniqueResult();
		try{
			if(null != rslt)
				rtnVal=TypeConverter.convertToLong(rslt);
		}catch(Exception e){}
		
		return rtnVal;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getCostAccrualSummaryReportData(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostAccrualSummaryReportData> getCostAccrualSummaryReportData(Long extractId) throws PersistenceException {
		Collection<CostAccrualSummaryReportData> data = new ArrayList<CostAccrualSummaryReportData>();
	
		String sql = CostAccrualReportQuery.getCostAccrualSummaryReportQuery(extractId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(CostAccrualSummaryReportData.class);
	     crt.addScalar("totalAmount", BigDecimal.class.getName());
	     crt.addScalar("changeAmount", BigDecimal.class.getName());
	     crt.addScalar("previousAmount", BigDecimal.class.getName());
	     crt.addScalar("incidentResourceId", Long.class.getName());
	     crt.addScalar("incidentId", Long.class.getName());
	     crt.addProjection("totalAmount", "totalAmountBigDecimal");
	     crt.addProjection("changeAmount", "changeAmountBigDecimal");
	     crt.addProjection("previousAmount", "previousAmountBigDecimal");
		query.setResultTransformer(crt); 

		data = query.list();
		
		return data;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getCostAccrualDetailReportData(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostAccrualDetailReportData> getCostAccrualDetailReportData(Long extractId) throws PersistenceException {
		Collection<CostAccrualDetailReportData> data = new ArrayList<CostAccrualDetailReportData>();
	
		String sql = CostAccrualReportQuery.getCostAccrualDetailReportQuery(extractId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(CostAccrualDetailReportData.class);
	     crt.addScalar("totalAmount", BigDecimal.class.getName());
	     crt.addScalar("changeAmount", BigDecimal.class.getName());
	     crt.addScalar("previousAmount", BigDecimal.class.getName());
	     crt.addScalar("incidentResourceId", Long.class.getName());
	     //crt.addScalar("incidentResourceOtherId", Long.class.getName());
	     crt.addScalar("isPerson", Boolean.class.getName());
	     
	     crt.addProjection("totalAmount", "totalAmountBigDecimal");
	     crt.addProjection("changeAmount", "changeAmountBigDecimal");
	     crt.addProjection("previousAmount", "previousAmountBigDecimal");
	     crt.addScalar("incidentId", Long.class.getName());
		query.setResultTransformer(crt); 

		data = query.list();
		
		return data;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getCostAccrualAllDetailSubReportData(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostAccrualAllDetailSubReportData> getCostAccrualAllDetailSubReportData(Long extractId) throws PersistenceException {
		Collection<CostAccrualAllDetailSubReportData> data = new ArrayList<CostAccrualAllDetailSubReportData>();
	
		String sql = CostAccrualReportQuery.getCostAccrualDetailReportQuery(extractId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(CostAccrualAllDetailSubReportData.class);
	     crt.addScalar("totalAmount", BigDecimal.class.getName());
	     crt.addScalar("changeAmount", BigDecimal.class.getName());
	     crt.addScalar("previousAmount", BigDecimal.class.getName());
	     crt.addScalar("incidentResourceId", Long.class.getName());
	     crt.addScalar("isPerson", Boolean.class.getName());
	     
	     crt.addProjection("totalAmount", "totalAmountBigDecimal");
	     crt.addProjection("changeAmount", "changeAmountBigDecimal");
	     crt.addProjection("previousAmount", "previousAmountBigDecimal");
	     crt.addScalar("incidentId", Long.class.getName());
		query.setResultTransformer(crt); 

		data = query.list();
		
		return data;
	}

	public Collection<CostAccrualAllDetailReportData> getCostAccrualAllDetailReportData(Long extractId) throws PersistenceException {
		Collection<CostAccrualAllDetailReportData> data = new ArrayList<CostAccrualAllDetailReportData>();
	
		String sql = CostAccrualReportQuery.getCostAccrualDetailReportQuery(extractId,super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(CostAccrualAllDetailReportData.class);
	     crt.addScalar("totalAmount", BigDecimal.class.getName());
	     crt.addScalar("changeAmount", BigDecimal.class.getName());
	     crt.addScalar("previousAmount", BigDecimal.class.getName());
	     crt.addScalar("incidentResourceId", Long.class.getName());
	     crt.addScalar("isPerson", Boolean.class.getName());
	     
	     crt.addProjection("totalAmount", "totalAmountBigDecimal");
	     crt.addProjection("changeAmount", "changeAmountBigDecimal");
	     crt.addProjection("previousAmount", "previousAmountBigDecimal");
	     crt.addScalar("incidentId", Long.class.getName());
		 query.setResultTransformer(crt); 

 		 data = query.list();
		 return data;
	}

	public int getMissingAccrualsFromPreviousExtract(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) ")
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("where caer.incident_resource_id is not null ")
		   .append("and caer.cost_accrual_extract_id = " + previousExtractId + " ")
		   .append("and caer.total_amount > 0 ")
		   .append("and ( ")
		   .append("  select count(*) from isw_cost_accrual_ext_rsc ")
		   .append("  where cost_accrual_extract_id = " + currentExtractId + " ")
		   .append("  and incident_resource_other_id is null ")
		   .append("  and incident_resource_id = caer.incident_resource_id ")
		   .append("  and account_code = caer.account_code ")
		   .append("  and cost_accrual_code = caer.cost_accrual_code ")
		   .append(") < 1 ");

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		Object rslt=query.uniqueResult();
		
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}
		}catch(Exception e){
			
		}
		
		return 0;
	}

	// OTHER RESOURCES
	public int getMissingAccrualsFromPreviousExtractOT(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(id) ")
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("where caer.incident_resource_other_id is not null ")
		   .append("and caer.cost_accrual_extract_id = " + previousExtractId + " ")
		   .append("and caer.total_amount > 0 ")
		   .append("and ( ")
		   .append("  select count(*) from isw_cost_accrual_ext_rsc ")
		   .append("  where cost_accrual_extract_id = " + currentExtractId + " ")
		   .append("  and incident_resource_other_id = caer.incident_resource_other_id ")
		   .append("  and incident_resource_id is null ")
		   .append("  and account_code = caer.account_code ")
		   .append("  and cost_accrual_code = caer.cost_accrual_code ")
		   .append(") < 1 ");

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		Object rslt=query.uniqueResult();
		
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}
		}catch(Exception e){
			
		}
		
		return 0;
	}

	public int getUpdateMissingAccrualsFromPreviousExtract(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(caer.id) ") 
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("   , isw_inc_res_daily_cost irdc ")
		   .append("where caer.cost_accrual_extract_id = "+currentExtractId+" ")
		   .append("and caer.incident_resource_id = irdc.incident_resource_id ")
		   .append("and caer.cost_accrual_code != (select code from iswl_accrual_code where id = irdc.accrual_code_id) ")
		   .append("and caer.account_code = ( select account_code from isw_account_code where id = irdc.incident_account_code_id) ")
		   .append("and caer.change_amount >= 0.0 ")
		   .append("and to_date(to_char(irdc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		   .append("	> (select to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') from isw_cost_accrual_extract where id = " + previousExtractId + " ) ");
			
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		Object rslt=query.uniqueResult();
		
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}
		}catch(Exception e){
			
		}
		
		return 0;
	}

	//OTHER RESOURCES
	public int getUpdateMissingAccrualsFromPreviousExtractOT(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(caer.id) ") 
		   .append("from isw_cost_accrual_ext_rsc caer ")
		   .append("   , isw_inc_res_daily_cost irdc ")
		   .append("where caer.cost_accrual_extract_id = "+currentExtractId+" ")
		   .append("and caer.incident_resource_other_id = irdc.incident_resource_other_id ")
		   .append("and caer.cost_accrual_code != (select code from iswl_accrual_code where id = irdc.accrual_code_id) ")
		   .append("and caer.account_code = ( select account_code from isw_account_code where id = irdc.incident_account_code_id) ")
		   .append("and caer.change_amount >= 0.0 ")
		   .append("and to_date(to_char(irdc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		   .append("	> (select to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') from isw_cost_accrual_extract where id = " + previousExtractId + " ) ");
			
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		Object rslt=query.uniqueResult();
		
		try{
			if(null != rslt){
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}
		}catch(Exception e){
			
		}
		
		return 0;
	}

	public void createNegativeAccruals(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into isw_cost_accrual_ext_rsc(id, cost_accrual_extract_id, incident_resource_id, total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, account_code_id) ")
		   .append("select  ");
		if(super.isOracleDialect()){
			sql.append("SEQ_COST_ACCRUAL_EXT_RSC.nextVal  ");
		}else{
			sql.append("nextVal('SEQ_COST_ACCRUAL_EXT_RSC')  ");
		}
		sql.append("," + currentExtractId + " ")
		   .append(",incident_resource_id  ")
		   .append(",0  ")
		   .append(",-(total_amount)  ")
		   .append(",caer.cost_accrual_code  ")
		   .append(",caer.account_code  ")
		   .append(",caer.fiscal_year  ")
		   .append(",caer.account_code_id  ")
		   .append("from isw_cost_accrual_ext_rsc caer  ")
		   .append("where caer.cost_accrual_extract_id = " + previousExtractId + " ")
		   .append("and caer.incident_resource_id is not null ")
		   .append("and caer.total_amount > 0 ")
		   .append("and (  ")
		   .append("   select count(*) from isw_cost_accrual_ext_rsc  ")
		   .append("   where cost_accrual_extract_id = " + currentExtractId + " ")
		   .append("   and incident_resource_id = caer.incident_resource_id  ")
		   .append("   and incident_resource_other_id is null ")
		   .append("   and account_code = caer.account_code ")
		   .append("   and cost_accrual_code = caer.cost_accrual_code ")
		   .append(") < 1  ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		query.executeUpdate();
		
	}

	// RESOURCE OTHER
	public void createNegativeAccrualsOT(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into isw_cost_accrual_ext_rsc(id, cost_accrual_extract_id, incident_resource_other_id, total_amount, change_amount, cost_accrual_code, account_code, fiscal_year, account_code_id) ")
		   .append("select  ");
		if(super.isOracleDialect()){
			sql.append("SEQ_COST_ACCRUAL_EXT_RSC.nextVal  ");
		}else{
			sql.append("nextVal('SEQ_COST_ACCRUAL_EXT_RSC')  ");
		}
		sql.append("," + currentExtractId + " ")
		   .append(",incident_resource_other_id  ")
		   .append(",0  ")
		   .append(",-(total_amount)  ")
		   .append(",caer.cost_accrual_code  ")
		   .append(",caer.account_code  ")
		   .append(",caer.fiscal_year  ")
		   .append(",caer.account_code_id  ")
		   .append("from isw_cost_accrual_ext_rsc caer  ")
		   .append("where caer.cost_accrual_extract_id = " + previousExtractId + " ")
		   .append("and caer.incident_resource_other_id is not null ")
		   .append("and caer.total_amount > 0 ")
		   .append("and (  ")
		   .append("   select count(*) from isw_cost_accrual_ext_rsc  ")
		   .append("   where cost_accrual_extract_id = " + currentExtractId + " ")
		   .append("   and incident_resource_id is null ")
		   .append("   and incident_resource_other_id = caer.incident_resource_other_id  ")
		   .append("   and account_code = caer.account_code ")
		   .append("   and cost_accrual_code = caer.cost_accrual_code ")
		   .append(") < 1  ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		query.executeUpdate();
		
	}

	public void updateNegativeAccruals(Long previousExtractId, Long currentExtractId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		sql.append("update isw_cost_accrual_ext_rsc caer ")
		   .append("set change_amount=-(total_amount) ")
		   .append(", total_amount = 0.0 ")
		   .append("where id in ( ")
		   .append("   select caer2.id ")
		   .append("   from isw_cost_accrual_ext_rsc caer2 ")
		   .append("        , isw_inc_res_daily_cost irdc ")
		   .append("   where caer2.cost_accrual_extract_id = "+currentExtractId+" ")
		   .append("   and caer2.incident_resource_id = irdc.incident_resource_id ")
		   .append("   and caer2.cost_accrual_code != (select code from iswl_accrual_code where id = irdc.accrual_code_id) ")
		   .append("   and caer2.account_code = ( select account_code from isw_account_code where id = irdc.incident_account_code_id) ")
		   .append("   and caer2.change_amount >= 0.0 ")
		   .append("   and to_date(to_char(irdc.activity_date,'MM/DD/YYYY'),'MM/DD/YYYY') ")
		   .append("	> (select to_date(to_char(extract_date,'MM/DD/YYYY'),'MM/DD/YYYY') from isw_cost_accrual_extract where id = " + previousExtractId + " ) ")
		   .append(") ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		query.executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getAccountingCodeSummaryType(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AccountingCodeSummaryType> getAccountingCodeSummaryTypes(Long extractId) throws PersistenceException {
		Collection<AccountingCodeSummaryType> types = new ArrayList<AccountingCodeSummaryType>();
		
		String sql = FinancialExportQuery.getAccountCodeSummaryTypes(extractId);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(AccountingCodeSummaryType.class);
		crt.addScalar("subTotalAmount", BigDecimal.class.getName());
	    crt.addScalar("subChangeAmount", BigDecimal.class.getName());
	    crt.addScalar("subPrevAmount", BigDecimal.class.getName());
		
	    query.setResultTransformer(crt); 
	    
	    types = query.list();
	    
		return types;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getRCLineSummaryTypes(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Collection<RCLineSummaryType> getRCLineSummaryTypes(Long extractId, String accountCodeYr) throws PersistenceException {
		Collection<RCLineSummaryType> types = new ArrayList<RCLineSummaryType>();
		
		String sql = FinancialExportQuery.getRCLineSummaryTypes(extractId, accountCodeYr);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(RCLineSummaryType.class);
		crt.addScalar("rcLineTotalAmount", BigDecimal.class.getName());
	    crt.addScalar("rcLineChangeAmount", BigDecimal.class.getName());
	    crt.addScalar("rcLinePrevAmount", BigDecimal.class.getName());
		
	    query.setResultTransformer(crt); 
	    
	    types = query.list();
		
		return types;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getAccrualDetailTypes(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Collection<AccrualDetailType> getAccrualDetailTypes(Long extractId, String accountCodeYr, String accrualCode) throws PersistenceException {
		Collection<AccrualDetailType> types = new ArrayList<AccrualDetailType>();
		
		String sql = FinancialExportQuery.getAccrualDetailTypes(extractId, accountCodeYr, accrualCode, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(AccrualDetailType.class);
		crt.addScalar("totalAmount", BigDecimal.class.getName());
	    crt.addScalar("changeAmount", BigDecimal.class.getName());
	    crt.addScalar("prevAmount", BigDecimal.class.getName());
		
	    query.setResultTransformer(crt); 
	    
	    types = query.list();
		
		return types;
		
	}
	
	public void updateExportedAccruals(Collection<Long> incidentIds) throws PersistenceException {
		String sql = FinancialExportQuery.getUpdateExportedAccrualExtracts(incidentIds);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.setParameterList("incidentIds", incidentIds);
		
		query.executeUpdate();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostAccrualExtractDao#getUnexportedExtracts(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CostAccrualExtract> getUnexportedExtracts(Collection<Long> ids) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(CostAccrualExtractImpl.class);
		
		crit.add(Restrictions.isNotNull("finalizedDate"));
		crit.add(Restrictions.eq("exported", StringBooleanEnum.N));
		crit.add(Restrictions.in("incidentId", ids));
		
		return crit.list();
	}

	public void updateAllFinAccrualsAsSingle(Long incidentId) throws PersistenceException {
		String sql="select count(id) from isw_cost_accrual_extract "+
				   "where incident_id = " + incidentId + " " +
				   "and finalized = 'Y' ";
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		try{
			Object val=query.uniqueResult();
			if(null != val){
				Long cnt=TypeConverter.convertToLong(val);
				if(cnt>0){
					sql="update isw_cost_accrual_extract " +
					    "set is_from_single_incident = 'Y' " +
						"where incident_id = " + incidentId + " " +
					    "and finalized = 'Y' ";
					
					query = getHibernateSession().createSQLQuery(sql);
					query.executeUpdate();
				}
			}
		}catch(Exception e){
			
		}
	}
	
	public Collection<CostAccrualGroupVo> getGroupNumbers(Long incidentGroupId, Date extractDate) throws PersistenceException {
		String sql = CostAccrualExtractQuery.getIncidentGroupNumbersQuery(incidentGroupId, extractDate);
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(CostAccrualGroupVo.class);
		crt.addScalar("totalAmount", BigDecimal.class.getName());
		crt.addScalar("changeAmount", BigDecimal.class.getName());
		crt.addScalar("accountCodeId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());

 	    crt.addProjection("costAccrualCode", "accrualCode");
 	    crt.addProjection("drawDown", "drawDown");
 	    crt.addProjection("totalAmount", "totalAmount");
 	    crt.addProjection("changeAmount", "changeAmount");
 	    crt.addProjection("accountCodeId", "accountCodeId");
 	    crt.addProjection("incidentResourceId", "incidentResourceId");
 	    crt.addProjection("fiscalYear", "fiscalYear");
 	    
		query.setResultTransformer(crt); 

		Collection<CostAccrualGroupVo> vos = query.list();
		
		return vos;
	}

	public void createGroupExtractRecords(Long extractId, String fiscalYear, Collection<CostAccrualGroupVo> vos) throws PersistenceException {

		for(CostAccrualGroupVo vo : vos){
			String sql="insert into isw_cost_accrual_ext_rsc " +
					   " (id, cost_accrual_extract_id, incident_resource_id, cost_accrual_code, account_code_id " +
					   "   , account_code, total_amount, change_amount, fiscal_year, draw_down ) "+
					   " values "+
					   " ("+(super.isOracleDialect() ? "SEQ_COST_ACCRUAL_EXT_RSC.nextVal" : "nextVal('SEQ_COST_ACCRUAL_EXT_RSC')" ) + " " +
					   "  , "+extractId + " " +
					   "  , "+ vo.getIncidentResourceId()+" " +
					   "  , '"+vo.getAccrualCode()+"' " +
					   "  , "+vo.getAccountCodeId() + " " +
					   "  , '"+vo.getAccountCode() + "' " +
					   "  , " + vo.getTotalAmount() + " " +
					   "  , " + vo.getChangeAmount() + " " +
					   "  , '" + vo.getFiscalYear() + "' " +
					   "  , '" + vo.getDrawdown() + "' " + 
					   " ) " ;
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			query.executeUpdate();
		}
		
	}
	
	public void createGroupExtractRecords2(Long extractId, Long groupId, Date extractDate) throws PersistenceException {
		if(super.isOracleDialect()){
			StringBuffer sql=new StringBuffer()
			.append("insert into isw_cost_accrual_ext_rsc " +
					   " (id, cost_accrual_extract_id, cost_accrual_code, total_amount, change_amount, account_code " +
					   "   , account_code_id, incident_resource_id, incident_resource_other_id, draw_down, fiscal_year ) ");
			
			sql.append("select ");
			sql.append("SEQ_COST_ACCRUAL_EXT_RSC.nextVal ");
			sql.append(", "+extractId + " ")
			.append(", t.cost_accrual_code ")
			.append(", t.total_amount ")
			.append(", t.change_amount ")
			.append(", t.account_code ")
			.append(", t.account_code_id ")
			.append(", t.incident_resource_id ")
			.append(", t.incident_resource_other_id ")
			.append(", t.draw_down ")
			.append(", t.fiscal_year ")
			.append("FROM (")
			.append(" select " )
			.append("  cost_accrual_code as cost_accrual_code ")
			.append("  ,sum(total_amount) as total_amount ")
			.append("  ,sum(change_amount) as change_amount ")
			.append("  ,account_code as account_code ")
			.append("  ,account_code_id as account_code_id ")
			.append("  ,incident_resource_id as incident_resource_id ")
			.append("  ,incident_resource_other_id as incident_resource_other_id ")
			.append("  ,draw_down as draw_down ")
			.append("  ,fiscal_year as fiscal_year ")
			.append("from isw_cost_accrual_ext_rsc ")
			.append("where cost_accrual_extract_id in ( ")
			.append("	select id ")
			.append("	from isw_cost_accrual_extract ")
			.append("	where incident_id in ( ")
			.append("		select incident_id ")
			.append("		from isw_incident_group_incident ")
			.append("		where incident_group_id = " + groupId + " ")
			.append("	) ")
			.append("	and to_char(extract_date,'MM/DD/YYYY') = '"+DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"' ")
			.append(" ) " )
			.append("group by account_code, cost_accrual_code,account_code_id, incident_resource_id, incident_resource_other_id,draw_down, fiscal_year ")
			.append(") t ")
			;
			
			SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
			query.executeUpdate();
			
		}else{
			StringBuffer sql=new StringBuffer()
				.append("insert into isw_cost_accrual_ext_rsc " +
						   " (id, cost_accrual_extract_id, cost_accrual_code, total_amount, change_amount, account_code " +
						   "   , account_code_id, incident_resource_id, incident_resource_other_id, draw_down, fiscal_year ) ");
				
				sql.append("select ");
				if(super.isOracleDialect()){
					sql.append("SEQ_COST_ACCRUAL_EXT_RSC.nextVal ");
				}else{
					sql.append("nextVal('SEQ_COST_ACCRUAL_EXT_RSC') ");
				}
				sql.append(", "+extractId + " ")
				.append(", cost_accrual_code ")
				.append(", sum(total_amount) ")
				.append(", sum(change_amount) ")
				.append(", account_code ")
				.append(", account_code_id ")
				.append(", incident_resource_id ")
				.append(", incident_resource_other_id ")
				.append(", draw_down ")
				.append(", fiscal_year ")
				.append("from isw_cost_accrual_ext_rsc ")
				.append("where cost_accrual_extract_id in ( ")
				.append("	select id ")
				.append("	from isw_cost_accrual_extract ")
				.append("	where incident_id in ( ")
				.append("		select incident_id ")
				.append("		from isw_incident_group_incident ")
				.append("		where incident_group_id = " + groupId + " ")
				.append("	) ")
				.append("	and to_char(extract_date,'MM/DD/YYYY') = '"+DateUtil.toDateString(extractDate, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"' ")
				.append(") " )
				.append("group by account_code, cost_accrual_code,account_code_id, incident_resource_id, incident_resource_other_id,draw_down, fiscal_year ");
				
				SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
				query.executeUpdate();
		}
	}
}
