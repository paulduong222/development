package gov.nwcg.isuite.core.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.core.domain.impl.ProjectionItemWorksheetImpl;
import gov.nwcg.isuite.core.persistence.CostProjectionItemWorksheetDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.CostProjectionQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class CostProjectionItemWorksheetDaoHibernate extends TransactionSupportImpl implements CostProjectionItemWorksheetDao {
	
	private final CrudDao<ProjectionItemWorksheet> crudDao;
	
	public CostProjectionItemWorksheetDaoHibernate(final CrudDao<ProjectionItemWorksheet> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionItemWorksheetDao#getProjectionWorksheetGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetGrid(Long projectionId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionItemWorksheetImpl.class);
		crit.createAlias("projectionItem", "pi");
		
		crit.add(Expression.eq("pi.projectionId", projectionId));
		crit.add(Expression.eq("pi.isSupportCost", StringBooleanEnum.N));
		crit.add(Expression.eq("pi.isItemCodeActive", StringBooleanEnum.Y));
		
		crit.addOrder(Order.asc("pi.itemCodeGroup"));
		crit.addOrder(Order.asc("projectionDate"));
		
		Collection<ProjectionItemWorksheet> entities = crit.list();
		
		return entities;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionItemWorksheetDao#getProjectionWorksheetGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProjectionItemWorksheet> getSupportingCostWorksheetGrid(Long projectionId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionItemWorksheetImpl.class);
		crit.createAlias("projectionItem", "pi");
		
		crit.add(Expression.eq("pi.projectionId", projectionId));
		crit.add(Expression.eq("pi.isSupportCost", StringBooleanEnum.Y));
		
		Collection<ProjectionItemWorksheet> entities = crit.list();
		
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetPersonnelTotal(Long projectionId) throws PersistenceException {
		Collection<ProjectionItemWorksheet> entities = new ArrayList<ProjectionItemWorksheet>();
		
		SQLQuery query = getHibernateSession().createSQLQuery(CostProjectionQuery.getProjectionWorksheetPersonnelTotal(projectionId));
		
		CustomResultTransformer crt = new CustomResultTransformer(ProjectionItemWorksheetImpl.class);
		//crt.addScalar("quantity", Integer.class.getName());
		crt.addScalar("numberOfPersonnel", Integer.class.getName());
	    crt.addScalar("projectionDate",Date.class.getName());
		query.setResultTransformer(crt);
		
		try {
			entities = query.list();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}

		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public Date getProjectionWorksheetStartDay(Long projectionId) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionItemWorksheetImpl.class);
		crit.setProjection(Projections.min("projectionDate"));
		crit.createAlias("projectionItem", "pi");
		crit.add(Expression.eq("pi.projectionId", projectionId));
		List<Date> ds = crit.list();
		
		return ds.get(0);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CostProjectionItemWorksheetDao#getProjectionWorksheetGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<ProjectionItemWorksheet> getProjectionWorksheetByItemCode(Long projectionId, String itemCode) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(ProjectionItemWorksheetImpl.class);
		crit.createAlias("projectionItem", "pi");
		
		crit.add(Expression.eq("pi.projectionId", projectionId));
		//crit.add(Expression.or(Expression.eq("pi.itemCodeGroup", itemCode),Expression.eq("pi.isSupportCost", StringBooleanEnum.Y)));
		crit.add(Expression.eq("pi.itemCodeGroup", itemCode));
		crit.add(Expression.eq("pi.isSupportCost", StringBooleanEnum.N));
		crit.addOrder(Order.asc("projectionDate"));
		
		Collection<ProjectionItemWorksheet> entities = crit.list();
		
		return entities;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ProjectionItemWorksheet persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ProjectionItemWorksheet getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ProjectionItemWorksheet persistable) throws PersistenceException {
		crudDao.save(persistable);

	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ProjectionItemWorksheet> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

}
