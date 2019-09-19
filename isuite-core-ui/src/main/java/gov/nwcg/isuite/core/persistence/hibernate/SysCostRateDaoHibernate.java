package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SysCostRate;
import gov.nwcg.isuite.core.domain.SysCostRateKind;
import gov.nwcg.isuite.core.domain.SysCostRateOvhd;
import gov.nwcg.isuite.core.domain.impl.SysCostRateImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateKindImpl;
import gov.nwcg.isuite.core.domain.impl.SysCostRateOvhdImpl;
import gov.nwcg.isuite.core.filter.CostRateCategoryFilter;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.SysCostRateDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.SysCostRateQuery;
import gov.nwcg.isuite.core.vo.SysCostRateGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
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
public class SysCostRateDaoHibernate extends TransactionSupportImpl implements SysCostRateDao{

	private final CrudDao<SysCostRate> crudDao;

	public SysCostRateDaoHibernate(final CrudDao<SysCostRate> crudDao)  {

		super();

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#getById(java.lang.Long, java.lang.Class)
	 */
	public SysCostRate getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, SysCostRateImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#save(gov.nwcg.isuite.core.domain.SysCostRate)
	 */
	public void save(SysCostRate persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<SysCostRate> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#delete(gov.nwcg.isuite.core.domain.SysCostRate)
	 */
	public void delete(SysCostRate persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#getDefaultRatesGrid(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public Collection<SysCostRateGridVo> getDefaultRatesGrid(CostRateFilter filter) throws PersistenceException{

		SQLQuery query = getHibernateSession().createSQLQuery(SysCostRateQuery.getGridQuery(super.isOracleDialect(), filter));

		CustomResultTransformer crt = new CustomResultTransformer(SysCostRateGridVo.class);
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("fedRate", BigDecimal.class.getName());
		crt.addScalar("stateRate", BigDecimal.class.getName());
		crt.addScalar("contrRate", BigDecimal.class.getName());

		crt.addProjection("contrRate", "contractorRate");
		query.setResultTransformer(crt);

		return query.list();
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#getSysCostRate(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public SysCostRate getSysCostRate(CostRateFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SysCostRateImpl.class);

		if(filter != null) {
			CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

			if(null != subFilter){
				if(StringUtility.hasValue(subFilter.getCostRateCategory())) {
					crit.add(Expression.eq("costRateCategory", subFilter.getCostRateCategory()));
				}
			}

			if(StringUtility.hasValue(filter.getCostRateCategory())) {
				crit.add(Expression.eq("costRateCategory", filter.getCostRateCategory()));
			}
		}

		return (SysCostRate)crit.uniqueResult();

	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#getSysCostRateKinds(CostRateFilter)
	 */
	public Collection<SysCostRateKind> getSysCostRateKinds(CostRateFilter filter) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(SysCostRateKindImpl.class);

		crit.setFetchMode("sysCostRate",FetchMode.JOIN);
		crit.setFetchMode("kind",FetchMode.JOIN);

		crit.createAlias("sysCostRate", "sysCostRate");
		crit.createAlias("kind", "kind");
		crit.createAlias("kind.requestCategory", "rq");

		if(filter != null) {
			if(StringUtility.hasValue(filter.getCostRateCategory())) {
				crit.add(Expression.eq("sysCostRate.costRateCategory", filter.getCostRateCategory()));
			}
			
			if(null != filter.getSubFilter()){
				CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

				if(StringUtility.hasValue(subFilter.getCostRateCategory())) {
					crit.add(Expression.eq("sysCostRate.costRateCategory", subFilter.getCostRateCategory()));

					if(StringUtility.hasValue(filter.getRequestCategory())) {
						if(filter.getRequestCategory().equals("OTHER"))
							crit.add(Expression.not(Expression.in("rq.code", new String[]{"O","E","C"})));
						else
							crit.add(Expression.eq("rq.code", filter.getRequestCategory().toUpperCase()));
					}

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
	 * @see gov.nwcg.isuite.core.persistence.SysCostRateDao#getSysCostRateOvhd(gov.nwcg.isuite.core.filter.CostRateFilter)
	 */
	public SysCostRateOvhd getSysCostRateOvhd(CostRateFilter filter) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(SysCostRateOvhdImpl.class);

		crit.setFetchMode("sysCostRate",FetchMode.JOIN);
		crit.createAlias("sysCostRate", "sysCostRate");

		if(filter != null) {
			
			//CostRateCategoryFilter subFilter = (CostRateCategoryFilter)filter.getSubFilter();

			if(StringUtility.hasValue(filter.getCostRateCategory())) {
				crit.add(Expression.eq("sysCostRate.costRateCategory", filter.getCostRateCategory()));
			}
		}

		return (SysCostRateOvhd)crit.uniqueResult();
	}
}
