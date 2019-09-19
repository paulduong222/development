package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.CustomReport;
import gov.nwcg.isuite.core.domain.CustomReportView;
import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.core.domain.impl.CustomReportImpl;
import gov.nwcg.isuite.core.domain.impl.CustomReportViewImpl;
import gov.nwcg.isuite.core.persistence.CustomReportDao;
import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.core.vo.CustomReportColumnVo;
import gov.nwcg.isuite.core.vo.CustomReportViewVo;
import gov.nwcg.isuite.core.vo.CustomReportVo;
import gov.nwcg.isuite.core.vo.SystemRoleVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CustomReportDaoHibernate extends TransactionSupportImpl implements
		CustomReportDao {
	
	private final CrudDao<CustomReport> crudDao;
	
	public CustomReportDaoHibernate(final CrudDao<CustomReport> crudDao) {
		if (crudDao == null) {
			throw new IllegalArgumentException("crudDao cannot be null");
		}
		this.crudDao = crudDao;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<CustomReportData> getReportResults(String sql, Collection<CustomReportColumnVo> columnVos) throws PersistenceException {
		 SQLQuery query;
		 ArrayList<CustomReportData> customReportDataList = null;
		try {
			query = super.getHibernateSession().createSQLQuery(sql);
			
			CustomResultTransformer crt = new CustomResultTransformer(CustomReportData.class);
			int i=1;
			
//			for(CustomReportColumnVo vo : columnVos){
//				if(vo.getCustomReportViewFieldVo().getDataType().equals("TIME")
//						&& null==vo.getFormatVo()){
//					// scalar
//					crt.addScalar("column"+i, Time.class.getName());
//				}else{
//					// scalar
//					crt.addScalar("column"+i, String.class.getName());
//				}
//				
//				
//				// projection
//				String field="column"+i;
//				crt.addProjection(field,field);
//				i++;
//			}
			
			query.setResultTransformer(crt); 
			customReportDataList = (ArrayList)query.list();
		} catch (HibernateException e) {
			throw e;
		} catch (PersistenceException e) {
			throw e;
		}
		return customReportDataList;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CustomReportDao#getCustomReportViewVos(java.util.Collection)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CustomReportViewVo> getCustomReportViewVos(Collection<SystemRoleVo> roles) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(CustomReportViewImpl.class);
		
		if(roles != null) {
			crit.createAlias("systemRoles", "role");
			
			crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			crit.addOrder(Order.asc("displayName"));
			
			if(CollectionUtility.hasValue(roles)) {
				Collection<String> roleNames = new ArrayList<String>();
				for(SystemRoleVo vo : roles){
					roleNames.add(vo.getRoleName());
				}
				
				crit.add(Restrictions.in("role.roleName", roleNames));
			}
		}
		
		Collection<CustomReportView> entities = crit.list();
		
		try {
			return CustomReportViewVo.getInstances(entities, true);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CustomReportDao#getGrid(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Collection<CustomReportVo> getGrid(Long userId, Collection<SystemRoleVo> roles) throws PersistenceException {
		Criteria crit = getHibernateSession().createCriteria(CustomReportImpl.class);
		
		 crit.add(Expression.disjunction()
				 .add(Expression.eq("userId", userId))
				 .add(Expression.eq("isPublic", StringBooleanEnum.Y))
				 );
		
		crit.addOrder(Order.asc("title"));
		
		Collection<CustomReport> entities = crit.list();
		
		List<String> userRoleNames = new ArrayList<String>();
		for(SystemRoleVo userRoleVo : roles){
			userRoleNames.add(userRoleVo.getRoleName());
		}
		
		Collection<CustomReport> newList = new ArrayList<CustomReport>();
		
		for(CustomReport cr : entities) {
			if(isViewAllowedForRoleNames(cr.getCustomReportView(), userRoleNames)){
				newList.add(cr);
			}
		}
		
		try {
			return CustomReportVo.getInstances(newList, true);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}
	
	/**
	 * Private method that returns true if the CustomReportView has a system role that matches one of user roles from a list
	 * of user role names
	 * @param view
	 * @param userRoleNames
	 * @return
	 */
	private boolean isViewAllowedForRoleNames(CustomReportView view, List<String> userRoleNames){
		if(null != view.getSystemRoles() && null != userRoleNames) {
			for(SystemRole sysRoleAllowed : view.getSystemRoles()){
				if(userRoleNames.contains(sysRoleAllowed.getRoleName())){
					return true;
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(CustomReport persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public CustomReport getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(CustomReport persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<CustomReport> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.CustomReportDao#getDuplicateReportTitleCount(gov.nwcg.isuite.core.vo.CustomReportVo, java.lang.Long)
	 */
	public int getDuplicateReportTitleCount(CustomReportVo vo, Long userId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(CustomReportImpl.class);
		
		crit.add(Expression.eq("title", vo.getReportTitle()));
		crit.add(Expression.ne("id", vo.getId()));
		
		if(vo.getIsPublic()) {
			crit.add(Expression.disjunction() 
					.add(Expression.eq("userId", userId)) 
					.add(Expression.eq("isPublic", StringBooleanEnum.Y))
					);
		}else {
			crit.add(Expression.disjunction() 
					.add(Expression.eq("userId", userId)) 
					.add(Expression.eq("isPublic", StringBooleanEnum.N))
					);
		}
		
		return crit.list().size();
	}

}
