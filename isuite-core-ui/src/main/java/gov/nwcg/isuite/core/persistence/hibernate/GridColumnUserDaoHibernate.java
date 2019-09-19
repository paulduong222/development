package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.GridColumnUser;
import gov.nwcg.isuite.core.domain.impl.GridColumnUserImpl;
import gov.nwcg.isuite.core.filter.GridColumnUserFilter;
import gov.nwcg.isuite.core.filter.impl.GridColumnUserFilterImpl;
import gov.nwcg.isuite.core.persistence.GridColumnUserDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.CriteriaBuilder;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.GridNameEnum;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataIntegrityViolationException;

public class GridColumnUserDaoHibernate extends TransactionSupportImpl implements GridColumnUserDao {
	
	private final CrudDao<GridColumnUser> crudDao;

	public GridColumnUserDaoHibernate(final CrudDao<GridColumnUser> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.GridColumnUserDao#getGridColumns(gov.nwcg.isuite.core.filter.GridColumnUserFilter)
	 */
	@SuppressWarnings("unchecked")
   public Collection<GridColumnUser> getGridColumns(GridColumnUserFilter filter) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(GridColumnUserImpl.class);
		
        crit.createAlias("this.gridColumn", "gc");
		
		if(null != filter){
			if(null==filter.getUserId())
				throw new PersistenceException("filter.UserId is required to retrieve a user's gridColumns");

			try{
				/*
				 * Add the criteria
				 */
				Collection<FilterCriteria> filterCriteria = GridColumnUserFilterImpl.getFilterCriteria(filter);
				
				CriteriaBuilder.addCriteria(crit, filterCriteria);

			}catch(Exception e){
				throw new PersistenceException(e);
			}
			
		}else{
			throw new PersistenceException("GridColumnUserFilter cannot be null, userId is required to retrieve a user's gridColumns");
		}

		crit.setMaxResults(super.getMaxResultSize());

		crit.addOrder(Order.asc("userId"));
		crit.addOrder(Order.asc("gc.gridName"));
		crit.addOrder(Order.asc("position"));
		crit.addOrder(Order.asc("gc.columnName"));
		
		return crit.list();
	}

	public int getMaxResultSize() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(GridColumnUser persistable) throws PersistenceException {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
   public GridColumnUser getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(GridColumnUser persistable) throws PersistenceException {
		
		if (persistable == null) 
	          throw new PersistenceException("Unable to save Null GridColumnUser entity.");
		
		try {
			crudDao.save(persistable);
		} catch (DataIntegrityViolationException dive) {
			throw new PersistenceException(dive);
		}
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<GridColumnUser> persistables) throws PersistenceException {
		if (persistables == null) 
	          throw new PersistenceException("Unable to save Null GridColumnUser entity collection.");

		crudDao.saveAll(persistables);
		
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.GridColumnUserDao#restoreDefaults(gov.nwcg.isuite.framework.types.GridNameEnum, java.lang.Long)
	 */
	public void restoreDefaults(GridNameEnum gridName, Long userId) throws PersistenceException {
		//Clear all visible(default) flags for selected grid.
//		Query q = getHibernateSession().createQuery(
//				"update GridColumnImpl gci set gci.isDefault = :isdefault where gci.gridName = :gridname");
//		q.setBoolean("isdefault", false);
//		q.setParameter("gridname", gridName);
//		q.executeUpdate();

//		String sql = GridColumnUserQuery.restoreDefaultsSQLUpdate(gridName.name(), super.isOracleDialect());
//		SQLQuery sqlQuery = getHibernateSession().createSQLQuery(sql);
//		sqlQuery.executeUpdate();
		
		try{
		
		GridColumnUserFilter gcuFilter = new GridColumnUserFilterImpl();
		gcuFilter.setGridName(gridName);
		gcuFilter.setUserId(userId);
		Collection<GridColumnUser> gcUsers = this.getGridColumns(gcuFilter);
		
			for(GridColumnUser gcu : gcUsers) {

				StringBuffer sbsql = new StringBuffer();
				sbsql.append("update isw_grid_column_user gcu set ") 
				.append("position = ")
				.append(" ( ")
				.append("        case ")  
				.append("        when ") 
				.append("            ( ")
				.append("                select distinct(default_position) from isw_grid_column gc, isw_grid_column_user gcu ")
				.append("                where ")
				.append("                gc.id = gcu.grid_column_id ")
				.append("                and gc.grid_name = '" + gridName.name() + "' ")     
				.append("                and gcu.user_id = " + userId + " ");
				
				if(super.isOracleDialect()) {
					sbsql.append("                and gc.is_default = 1 ");
				} else {
					sbsql.append("                and gc.is_default = true ");
				}
				
				sbsql.append("                and gc.column_name = '" + gcu.getGridColumn().getColumnName() + "' ")
				.append("            ) is null ")
				.append("        then 99 ")
				.append("        else (select distinct(default_position) from isw_grid_column where grid_name = '" + gridName.name() + "' and column_name = '" + gcu.getGridColumn().getColumnName() + "') ")
				.append("        end ")
				.append("), ") 
				.append("is_visible = ") 
				.append("( ")
				.append("    case ")
				.append("    when ")   
				.append("        ( ")
				.append("            select distinct(is_default) from isw_grid_column gc, isw_grid_column_user gcu ") 
				.append("            where ")
				.append("            gc.id = gcu.grid_column_id ")     
				.append("            and gc.grid_name = '" + gridName.name() + "' ")     
				.append("            and gcu.user_id = " + userId + " ");
				
				if(super.isOracleDialect()) {
					sbsql.append("            and gc.is_default = 1 ");
				} else {
					sbsql.append("            and gc.is_default = true ");
				}
				
				sbsql.append("            and gc.column_name = '" + gcu.getGridColumn().getColumnName() + "' ")
				.append("        ) is null ");
				
				if(super.isOracleDialect()) {
					sbsql.append("    then 0 ")
					.append("    else 1 ");
				} else {
					sbsql.append("    then false ")
					.append("    else true ");
				}
				
				sbsql.append("    end ")
				.append(") where gcu.id = ")
				.append("( ")
				.append("    select distinct(gcu.id) from isw_grid_column_user gcu ")
				.append("    where gcu.grid_column_id = ") 
				.append("    ( ")
				.append("        select distinct(id) from isw_grid_column where grid_name = '" + gridName.name() + "' and column_name = '" + gcu.getGridColumn().getColumnName() + "' ")
				.append("    ) ")
				.append("    and gcu.user_id = " + userId + " ")
				.append(") ");
				
				SQLQuery sqlRestoreDefaultPositions = getHibernateSession().createSQLQuery(sbsql.toString());
				sqlRestoreDefaultPositions.executeUpdate();
			}
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

}
