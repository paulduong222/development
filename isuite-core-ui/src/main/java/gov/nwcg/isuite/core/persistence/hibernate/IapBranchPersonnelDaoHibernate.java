package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.persistence.IapBranchPersonnelDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.SQLQuery;

public class IapBranchPersonnelDaoHibernate extends TransactionSupportImpl implements IapBranchPersonnelDao {
	private final CrudDao<IapBranchPersonnel> crudDao;
	
	public IapBranchPersonnelDaoHibernate(final CrudDao<IapBranchPersonnel> crudDao) {
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao = crudDao;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(IapBranchPersonnel persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public IapBranchPersonnel getById(Long id, Class<?> clazz) throws PersistenceException {
		return crudDao.getById(id, clazz);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(IapBranchPersonnel persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<IapBranchPersonnel> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public int get204PositionCount(Long branchId) throws PersistenceException{
		String sql="select count(id) from isw_iap_branch_personnel "+
				   "where iap_branch_id = " + branchId + " ";

		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		Object rslt=query.uniqueResult();
		
		if(null != rslt){
			try{
				int cnt=TypeConverter.convertToInt(rslt);
				return cnt;
			}catch(Exception e){
				
			}
		}
		
		return 0;
	}

}
