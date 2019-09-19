package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapBranchRscAssign;
import gov.nwcg.isuite.core.domain.impl.IapBranchRscAssignImpl;
import gov.nwcg.isuite.core.persistence.IapBranchRscAssignDao;
import gov.nwcg.isuite.core.vo.IapBranchRscAssignVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class IapBranchRscAssignDaoHibernate extends TransactionSupportImpl implements IapBranchRscAssignDao {
	private final CrudDao<IapBranchRscAssign> crudDao;
	
	public IapBranchRscAssignDaoHibernate(final CrudDao<IapBranchRscAssign> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapBranchRscAssign persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapBranchRscAssign getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapBranchRscAssign persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapBranchRscAssign> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#autoFill(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@SuppressWarnings("unchecked")
	public void autoFill(IapBranchRscAssignVo vo) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		SQLQuery query;
		
		sql.append("update isw_iap_branch_rsc_assign ")
		.append(" set drop_off_point = '" + (StringUtility.hasValue(vo.getDropOffPoint()) ? vo.getDropOffPoint() : "") + "'")
		.append(" , drop_off_time = '" + (StringUtility.hasValue(vo.getDropOffTime()) ? vo.getDropOffTime() : "") + "'")
		.append(" , pick_up_point = '" + (StringUtility.hasValue(vo.getPickUpPoint()) ? vo.getPickUpPoint() : "")  + "'")
		.append(" , pick_up_time = '" + (StringUtility.hasValue(vo.getPickUpTime()) ? vo.getPickUpTime() : "") + "'")
		.append(" where is_blank_line = 'N' and iap_branch_id = " + vo.getIapBranchId());
				
		query = getHibernateSession().createSQLQuery(sql.toString());
		query.executeUpdate();
								  		
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getRscAssignGrid(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	@SuppressWarnings("unchecked")
	public Collection<IapBranchRscAssignVo> getRscAssignGrid(IapBranchRscAssignVo vo) throws PersistenceException {
        Criteria crit = getHibernateSession().createCriteria(IapBranchRscAssignImpl.class);
			
		crit.add(Restrictions.eq("iapBranchId", vo.getIapBranchId()));
		crit.addOrder(Order.asc("positionNum"));
		Collection<IapBranchRscAssign> entities = crit.list();
				
		try{
			return IapBranchRscAssignVo.getInstances(entities, true);
		}catch(Exception e){
	    	 throw new PersistenceException(e);
	    }								  		
	}
	
	@SuppressWarnings("unchecked")
	public List<Long> getResourcesAlreadyAssigned(Collection<IapBranchRscAssignVo> vos, Long iapPlanId) throws PersistenceException {
		if(vos==null || vos.size()<1) return null;
		
		List<Long> alreadyAssignedResourceIDs = new ArrayList<Long>();

		StringBuffer resourceListSql = null;
		for(IapBranchRscAssignVo vo: vos){
			if(vo.getResourceId()!=null){
				if(resourceListSql==null) {
					resourceListSql = new StringBuffer()
						.append(" rsc.resource_id = " + vo.getResourceId());
				} else {
					resourceListSql.append(" OR rsc.resource_id = " + vo.getResourceId());
				}
			}
		}
		
		if(resourceListSql==null) return null; 	// None of the resources had a resource Id; So the result
												// of matching resources will always be null
		
		StringBuffer sql = new StringBuffer()
			.append("SELECT distinct resource_id ")
			.append("FROM isw_iap_branch_rsc_assign rsc ")
			.append("LEFT JOIN isw_iap_branch branch ON branch.id = rsc.iap_branch_id ")
			.append("WHERE branch.iap_plan_id = " + iapPlanId)
			.append(" AND ( ")
			.append(resourceListSql)
			.append(" )");

		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());
		
		Collection<Object> tempList = query.list();
		try {
			for(Object o: tempList){
				alreadyAssignedResourceIDs.add(TypeConverter.convertToLong(o));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alreadyAssignedResourceIDs;
	}
}
