package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemImpl;
import gov.nwcg.isuite.core.persistence.CostProjectionItemDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostProjectionQuery;
import gov.nwcg.isuite.core.vo.ProjectionItemVo;
import gov.nwcg.isuite.core.vo.ProjectionVo;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CostProjectionItemDaoHibernate extends TransactionSupportImpl implements CostProjectionItemDao {

	private final CrudDao<ProjectionItem> crudDao;
	
	/**
	 * Constructor.
	 * @param crudDao can't be null
	 * @param crudDaoRsc can't be null
	 */
	public CostProjectionItemDaoHibernate(final CrudDao<ProjectionItem> crudDao) {
		if ( crudDao == null ) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ProjectionItem persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public void deleteAll(Collection<ProjectionItem> persistables) throws PersistenceException {
		for(ProjectionItem pi : persistables)
			crudDao.delete(pi);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ProjectionItem getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public Collection<ProjectionItemVo> getIncidentSummaryProjectionItems(Long projectionId,String itemCode) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionItemImpl.class);
		
		if(LongUtility.hasValue(projectionId))
			crit.add(Restrictions.eq("projectionId", projectionId));
		
		crit.add(Restrictions.eq("isManuallyAdded", "N"));
		//crit.add(Restrictions.eq("isSupportCost", "N"));
		crit.add(Restrictions.eq("itemCode", itemCode));
		
		crit.addOrder(Order.asc("itemCode"));
		
		Collection<ProjectionItem> entities = crit.list();
		
		try{
			return ProjectionItemVo.getInstances(entities, true);
		}catch(Exception e){
			throw new PersistenceException(e);
		}	
	}
		
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ProjectionItem persistable) throws PersistenceException {
		Collection<ProjectionItemWorksheet> list = persistable.getProjectionItemWorksheets();
		for(Persistable p:list)
			setAuditableInfo(p);
        crudDao.save(persistable);
	}
	
	
	protected void setAuditableInfo1(Collection<ProjectionItemWorksheet> persistents) {
		      for (ProjectionItemWorksheet p : persistents) {
		         setAuditableInfo(p);
		      }
	}
	
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ProjectionItem> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
}
