package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import gov.nwcg.isuite.core.domain.Recommendation;
import gov.nwcg.isuite.core.domain.impl.RecommendationImpl;
import gov.nwcg.isuite.core.persistence.RecommendationDao;
import gov.nwcg.isuite.core.vo.RecommendationVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class RecommendationDaoHibernate extends TransactionSupportImpl
		implements RecommendationDao {
	
	private final CrudDao<Recommendation> crudDao;
	
	public RecommendationDaoHibernate(final CrudDao<Recommendation> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao can not be null");
		}
	    this.crudDao = crudDao;
	}

	@Override
	public void delete(Recommendation persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public Recommendation getById(Long id, Class<?> clazz)
			throws PersistenceException {
		return crudDao.getById(id, RecommendationImpl.class);
	}

	@Override
	public void save(Recommendation persistable) throws PersistenceException {
		crudDao.save(persistable);

	}

	@Override
	public void saveAll(Collection<Recommendation> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<RecommendationVo> getPicklist()
			throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RecommendationImpl.class);
		crit.addOrder(Order.asc("code"));
		Collection<Recommendation> entities = crit.list();
		
		try{
			return RecommendationVo.getInstances(entities, true);
		}catch (Exception e) {
			 throw new PersistenceException(e);
		 }
	}

}
