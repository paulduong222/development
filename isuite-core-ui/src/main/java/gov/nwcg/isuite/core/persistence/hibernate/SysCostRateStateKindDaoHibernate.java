package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.SysCostRateStateKind;
import gov.nwcg.isuite.core.domain.impl.SysCostRateStateKindImpl;
import gov.nwcg.isuite.core.filter.CostRateCategoryFilter;
import gov.nwcg.isuite.core.filter.CostRateFilter;
import gov.nwcg.isuite.core.persistence.SysCostRateStateKindDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class SysCostRateStateKindDaoHibernate extends TransactionSupportImpl implements SysCostRateStateKindDao {

	private final CrudDao<SysCostRateStateKind> crudDao;
	
	public SysCostRateStateKindDaoHibernate(final CrudDao<SysCostRateStateKind> crudDao)  {

		super();

		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	@Override
	public void save(SysCostRateStateKind persistable) throws PersistenceException {
		this.crudDao.save(persistable);
	}

	@Override
	public void delete(SysCostRateStateKind persistable)
			throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public SysCostRateStateKind getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, SysCostRateStateKindImpl.class);
	}

	@Override
	public void saveAll(Collection<SysCostRateStateKind> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<SysCostRateStateKind> getSysCostRateStateKinds(CostRateFilter filter) throws PersistenceException {
		CostRateCategoryFilter subFilter = null;
		if(null != filter) {
			subFilter = (CostRateCategoryFilter)filter.getSubFilter();
		}
		
		Criteria crit = getHibernateSession().createCriteria(SysCostRateStateKindImpl.class);
		
		crit.createAlias("kind", "k");
		crit.createAlias("k.requestCategory", "rc");
		crit.createAlias("sysCostRateState", "costRateState");
		crit.add(Restrictions.eq("costRateState.agencyId", filter.getAgencyId()));
		
		if(null != subFilter) {
			if(StringUtility.hasValue(filter.getRequestCategory()) && filter.getRequestCategory().equals("OTHER")) {
				crit.add(Restrictions.not(Restrictions.in("rc.code", new String[]{"O", "E", "C"})));
			} else if(StringUtility.hasValue(filter.getRequestCategory())) {
				crit.add(Restrictions.eq("rc.code", filter.getRequestCategory()));
			}

			if(StringUtility.hasValue(subFilter.getItemCode())) {
				crit.add(Restrictions.like("k.code", subFilter.getItemCode().toUpperCase(), MatchMode.START));
			}
			if(StringUtility.hasValue(subFilter.getItemDescription())) {
				crit.add(Restrictions.like("k.description", subFilter.getItemDescription().toUpperCase(), MatchMode.START));
			}
			if(StringUtility.hasValue(subFilter.getRequestCategory())) {
				crit.add(Restrictions.like("rc.code", subFilter.getRequestCategory().toUpperCase(), MatchMode.START));
			}
			if(subFilter.getUnitOfMeasureVo()!=null && StringUtility.hasValue(subFilter.getUnitOfMeasureVo().getCode())) {
				crit.add(Expression.ilike("this.rateType", subFilter.getUnitOfMeasureVo().getCode().toUpperCase(),MatchMode.START));
			}
			if(null != subFilter.getRate()) {
				crit.add(Restrictions.eq("this.rateAmount", subFilter.getRate())); 
			}
		}
		
		return crit.list();
	}

}
