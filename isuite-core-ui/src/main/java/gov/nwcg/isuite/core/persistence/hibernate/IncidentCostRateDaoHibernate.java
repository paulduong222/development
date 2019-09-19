package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IncidentCostRate;
import gov.nwcg.isuite.core.domain.IncidentCostRateKind;
import gov.nwcg.isuite.core.domain.IncidentCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateKindImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentCostRateOvhdImpl;
import gov.nwcg.isuite.core.filter.CostRateCategoryFilter;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.IncidentCostRateDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.IncidentCostRateQuery;
import gov.nwcg.isuite.core.vo.IncidentCostRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

@SuppressWarnings("unchecked")
public class IncidentCostRateDaoHibernate extends TransactionSupportImpl implements IncidentCostRateDao{

	private final CrudDao<IncidentCostRate> crudDao;

	public IncidentCostRateDaoHibernate(final CrudDao<IncidentCostRate> crudDao)  {

		super();

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IncidentCostRate getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, IncidentCostRateImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#save(gov.nwcg.isuite.core.domain.IncidentCostRate)
	 */
	public void save(IncidentCostRate persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IncidentCostRate> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#delete(gov.nwcg.isuite.core.domain.IncidentCostRate)
	 */
	public void delete(IncidentCostRate persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#getDefaultRatesGrid(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public Collection<IncidentCostRateGridVo> getDefaultRatesGrid(CostRateFilter filter) throws PersistenceException{
		if(!LongUtility.hasValue(filter.getIncidentId()) 
				&& !LongUtility.hasValue(filter.getIncidentGroupId())){
			throw new PersistenceException("IncidentId or IncidentGroupId is required");
		}

		///SQLQuery query = getHibernateSession().createSQLQuery(IncidentCostRateQuery.getGridQueryOld(super.isOracleDialect(), filter));

		SQLQuery query = null;
		
		if(super.isOracleDialect())
		{
			query=getHibernateSession().createSQLQuery(IncidentCostRateQuery.getGridQuery(filter));
		}	
		else{
			query=getHibernateSession().createSQLQuery(IncidentCostRateQuery.getGridQuery2(filter));		
		}
		CustomResultTransformer crt = new CustomResultTransformer(IncidentCostRateGridVo.class);
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("fedRate", BigDecimal.class.getName());
		crt.addScalar("stateRate", BigDecimal.class.getName());
		crt.addScalar("contrRate", BigDecimal.class.getName());
		crt.addScalar("customStateRates", String.class.getName());

		crt.addProjection("contrRate", "contractorRate");
		query.setResultTransformer(crt);

		Collection<IncidentCostRateGridVo> vos = query.list();
		
		try{
			vos=query.list();
		}catch(Exception e){
			throw new PersistenceException("IncidentCostRateDao.getDefaultRatesGrid() "
											+ e.getMessage() + " " );
		}
		
		return vos;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#getIncidentCostRate(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public IncidentCostRate getIncidentCostRate(CostRateFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateImpl.class);

		if(filter != null) {
			CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

			if(StringUtility.hasValue(filter.getCostRateCategory())) {
				crit.add(Expression.eq("costRateCategory", filter.getCostRateCategory()));
			}

		}

		return (IncidentCostRate)crit.uniqueResult();

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#getIncidentCostRateKinds(CostRateFilter)
	 */
	public Collection<IncidentCostRateKind> getIncidentCostRateKinds(CostRateFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateKindImpl.class);

		crit.setFetchMode("incidentCostRate",FetchMode.JOIN);
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.setFetchMode("incidentCostRate.incident",FetchMode.JOIN);
		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.setFetchMode("incidentCostRate.incidentGroup",FetchMode.JOIN);
		crit.setFetchMode("kind",FetchMode.JOIN);

		crit.createAlias("incidentCostRate", "incidentCostRate");
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.createAlias("incidentCostRate.incident", "incident");
		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.createAlias("incidentCostRate.incidentGroup", "incidentGroup");
		crit.createAlias("kind", "kind");
		crit.createAlias("kind.requestCategory", "rq");

		if(filter != null) {
			CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

			if(LongUtility.hasValue(filter.getIncidentId())) {
				crit.add(Expression.eq("incident.id", filter.getIncidentId()));
			}

			if(LongUtility.hasValue(filter.getIncidentGroupId())) {
				crit.add(Expression.eq("incidentGroup.id", filter.getIncidentGroupId()));
			}

			if(StringUtility.hasValue(filter.getRequestCategory())) {
				if(filter.getRequestCategory().equals("OTHER"))
					crit.add(Expression.not(Expression.in("rq.code", new String[]{"O","E","C"})));
				else
					crit.add(Expression.eq("rq.code", filter.getRequestCategory().toUpperCase()));
			}

			crit.add(Expression.eq("incidentCostRate.costRateCategory", filter.getCostRateCategory()));
			
			if(null != subFilter){
				if(StringUtility.hasValue(subFilter.getCostRateCategory())) {


					if(StringUtility.hasValue(subFilter.getItemCode())) {
						crit.add(Expression.ilike("kind.code", subFilter.getItemCode().toUpperCase(),MatchMode.START));
					}

					if(StringUtility.hasValue(subFilter.getItemDescription())) {
						crit.add(Expression.ilike("kind.description", subFilter.getItemDescription().toUpperCase(),MatchMode.START));
					}

					if(StringUtility.hasValue(subFilter.getRequestCategory())) {
						crit.add(Expression.ilike("rq.code", subFilter.getRequestCategory().toUpperCase(),MatchMode.START));
					}

					if(subFilter.getRate()!=null) {
						// query on a rateAmount value with a scale of 2
						if((subFilter.getRate().stripTrailingZeros()).scale() > 2 ) {
							crit.add(Expression.eq("rateAmount", subFilter.getRate().setScale(2, RoundingMode.DOWN)));
						} else if((subFilter.getRate().stripTrailingZeros()).scale() == 2 ) {
							crit.add(Expression.eq("rateAmount", subFilter.getRate()));
						} else { 
							// make the upper bound a scale of 2 value
							BigDecimal upper = subFilter.getRate().setScale(2); 
							if((subFilter.getRate().stripTrailingZeros()).scale() == 1 ) { //scale of 1, add .09
								upper = upper.add(new BigDecimal(.09));
							} else { // scale == 0, add .99
								upper = upper.add(new BigDecimal(.99));
							}
							crit.add(Expression.ge("rateAmount", subFilter.getRate()));
							crit.add(Expression.le("rateAmount", upper));
						}
					}

					if(subFilter.getUnitOfMeasureVo()!=null && StringUtility.hasValue(subFilter.getUnitOfMeasureVo().getCode())) {
						crit.add(Expression.ilike("rateType", subFilter.getUnitOfMeasureVo().getCode().toUpperCase(),MatchMode.START));
					}
				}
				
			}
		}

		crit.addOrder(Order.asc("kind.code"));

		return crit.list();

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.IncidentCostRateDao#getIncidentCostRateOvhd(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public IncidentCostRateOvhd getIncidentCostRateOvhd(CostRateFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(IncidentCostRateOvhdImpl.class);

		crit.setFetchMode("incidentCostRate",FetchMode.JOIN);
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.setFetchMode("incidentCostRate.incident",FetchMode.JOIN);
		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.setFetchMode("incidentCostRate.incidentGroup",FetchMode.JOIN);

		crit.createAlias("incidentCostRate", "incidentCostRate");
		
		if(LongUtility.hasValue(filter.getIncidentId()))
			crit.createAlias("incidentCostRate.incident", "incident");

		if(LongUtility.hasValue(filter.getIncidentGroupId()))
			crit.createAlias("incidentCostRate.incidentGroup", "incidentGroup");

		if(filter != null) {
			CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

			if(StringUtility.hasValue(filter.getCostRateCategory())) {
				crit.add(Expression.eq("incidentCostRate.costRateCategory", filter.getCostRateCategory()));
			}

			if(LongUtility.hasValue(filter.getIncidentId())) {
				crit.add(Expression.eq("incident.id", filter.getIncidentId()));
			}
			
			if(LongUtility.hasValue(filter.getIncidentGroupId())) {
				crit.add(Expression.eq("incidentGroup.id", filter.getIncidentGroupId()));
			}
		}

		return (IncidentCostRateOvhd)crit.uniqueResult();
	}
}
