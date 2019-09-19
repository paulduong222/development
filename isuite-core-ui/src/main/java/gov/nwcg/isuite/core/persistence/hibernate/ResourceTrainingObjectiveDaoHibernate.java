package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import gov.nwcg.isuite.core.domain.RscTrainingObjective;
import gov.nwcg.isuite.core.domain.impl.RscTrainingObjectiveImpl;
import gov.nwcg.isuite.core.persistence.ResourceTrainingObjectiveDao;
import gov.nwcg.isuite.core.vo.RscTrainingObjectiveVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public class ResourceTrainingObjectiveDaoHibernate extends TransactionSupportImpl implements ResourceTrainingObjectiveDao {
	
	private final CrudDao<RscTrainingObjective> crudDao;
	
	public ResourceTrainingObjectiveDaoHibernate(final CrudDao<RscTrainingObjective> crudDao){
		if ( crudDao == null ) {
	         throw new IllegalArgumentException("crudDao cannot be null");
	      }
	      this.crudDao = crudDao;
	}

	@Override
	public void delete(RscTrainingObjective persistable)
			throws PersistenceException {
		crudDao.delete(persistable);
	}

	@Override
	public RscTrainingObjective getById(Long id, Class<?> clazz)
			throws PersistenceException {
		
		return crudDao.getById(id, RscTrainingObjectiveImpl.class);
	}

	@Override
	public void save(RscTrainingObjective persistable)
			throws PersistenceException {
		crudDao.save(persistable);
	}

	@Override
	public void saveAll(Collection<RscTrainingObjective> persistables)
			throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<RscTrainingObjectiveVo> getObjectives(Long resourceTrainingId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(RscTrainingObjectiveImpl.class);
		
		crit.add(Restrictions.eq("resourceTrainingId", resourceTrainingId));
		
		Collection<RscTrainingObjective> entities = crit.list();
		
		try{
			return RscTrainingObjectiveVo.getInstances(entities, true);
		}catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

}
