package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;

import gov.nwcg.isuite.core.domain.CostGroupAgencyDaySharePercentage;
import gov.nwcg.isuite.core.domain.impl.CostGroupAgencyDaySharePercentageImpl;
import gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter;
import gov.nwcg.isuite.core.persistence.CostGroupAgencyDaySharePercentageDao;
import gov.nwcg.isuite.core.vo.CostGroupAgencyDaySharePercentageVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

public class CostGroupAgencyDaySharePercentageDaoHibernate extends TransactionSupportImpl implements CostGroupAgencyDaySharePercentageDao {
	private final CrudDao<CostGroupAgencyDaySharePercentage> crudDao;
	
	public CostGroupAgencyDaySharePercentageDaoHibernate(final CrudDao<CostGroupAgencyDaySharePercentage> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	public void delete(CostGroupAgencyDaySharePercentage persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public CostGroupAgencyDaySharePercentage getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	public void save(CostGroupAgencyDaySharePercentage persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	public void saveAll(Collection<CostGroupAgencyDaySharePercentage> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostGroupAgencyDaySharePercentageDao#getGrid(gov.nwcg.isuite.core.filter.CostGroupAgencyDayShareFilter)
	 */
	public Collection<CostGroupAgencyDaySharePercentageVo> getGrid(CostGroupAgencyDayShareFilter filter) throws PersistenceException {
		if(filter == null || (!LongUtility.hasValue(filter.getCostGroupId())) || (!DateUtil.hasValue(filter.getAgencyShareDate()))) {
			throw new PersistenceException("costGroupId and agencyShareDate are required.");
		}
		
		try {
			Criteria crit = getHibernateSession().createCriteria(CostGroupAgencyDaySharePercentageImpl.class);
			
			crit.createAlias("costGroupAgencyDayShare", "cgads");
			crit.createAlias("cgads.costGroup", "cg");
			
			Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();
			criteria.add( new FilterCriteria("this.cg.id",filter.getCostGroupId(),FilterCriteria.TYPE_EQUAL));
			criteria.add( new FilterCriteria("this.cgads.agencyShareDate",filter.getAgencyShareDate(),FilterCriteria.TYPE_EQUAL));
			
			CriteriaBuilder.addCriteria(crit, criteria);
			
			Collection<CostGroupAgencyDaySharePercentage> entities = crit.list();
			
			if(null != entities && entities.size()>0)
				return CostGroupAgencyDaySharePercentageVo.getInstances(entities, true);
			else
				return null;
		}catch(Exception e){
			throw new PersistenceException(e);
		}
	}

}
