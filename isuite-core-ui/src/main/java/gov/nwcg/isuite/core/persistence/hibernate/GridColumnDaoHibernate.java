package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.GridColumn;
import gov.nwcg.isuite.core.domain.impl.GridColumnImpl;
import gov.nwcg.isuite.core.filter.GridColumnFilter;
import gov.nwcg.isuite.core.filter.impl.GridColumnFilterImpl;
import gov.nwcg.isuite.core.persistence.GridColumnDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.hibernate.Criteria;

public class GridColumnDaoHibernate extends TransactionSupportImpl implements GridColumnDao {
	
	private final CrudDao<GridColumn> crudDao;

	public GridColumnDaoHibernate(final CrudDao<GridColumn> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
   public GridColumn getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.GridColumnDao#getGridColumns(gov.nwcg.isuite.core.filter.GridColumnFilter)
	 */
	@SuppressWarnings("unchecked")
   public Collection<GridColumn> getGridColumns(GridColumnFilter filter) throws PersistenceException{
		
		Criteria crit = getHibernateSession().createCriteria(GridColumnImpl.class);
		
		
		if(null != filter){

			try{
				/*
				 * Add the criteria
				 */
				Collection<FilterCriteria> filterCriteria = GridColumnFilterImpl.getFilterCriteria(filter);
				
				CriteriaBuilder.addCriteria(crit, filterCriteria);

			}catch(Exception e){
				throw new PersistenceException(e);
			}
			
		}else{
			/*
			 * If filter is null, get all gridColumn records
			 */
		}

		crit.setMaxResults(super.getMaxResultSize());

		return crit.list();
	}
	
	public int getMaxResultSize() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(GridColumn persistable) throws PersistenceException {
		throw new RuntimeException("Unsupported method");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(GridColumn persistable) throws PersistenceException {
		throw new RuntimeException("Unsupported method");
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<GridColumn> persistables) throws PersistenceException {
		throw new RuntimeException("Unsupported method");
	}

}
