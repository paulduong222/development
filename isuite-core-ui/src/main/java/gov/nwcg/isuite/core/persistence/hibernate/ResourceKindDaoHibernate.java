package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.persistence.ResourceKindDao;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;

/**
 * 
 * @author 
 */
public class ResourceKindDaoHibernate extends TransactionSupportImpl implements ResourceKindDao {
	private final CrudDao<ResourceKind> crudDao;

	private static final Logger LOG = Logger.getLogger(ResourceKindDaoHibernate.class);

	/*
	 * Constructor
	 */
	public ResourceKindDaoHibernate(final CrudDao<ResourceKind> crudDao) {
		if ( crudDao == null ) {
			LOG.error("crudDao was null coming in to constructor.  Check the Spring config.");
			throw new IllegalArgumentException("crudDao can not be null");
		}
		this.crudDao = crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#delete(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void delete(ResourceKind persistable) throws PersistenceException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#getById(java.lang.Long, java.lang.Class)
	 */
	public ResourceKind getById(Long id, Class clazz) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#save(gov.nwcg.isuite.framework.core.domain.Persistable)
	 */
	public void save(ResourceKind persistable) throws PersistenceException {
	   if(null != persistable.getId() && persistable.getId()>0)
		   super.getHibernateSession().merge(persistable);
	   else
		   crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.persistence.CrudDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<ResourceKind> persistables) throws PersistenceException {
		// TODO Auto-generated method stub
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.ResourceKindDao#deleteQualifications(java.util.Collection)
	 */
	public void deleteQualifications(Collection<Long> resourceKindIds) throws PersistenceException {
	       
	   StringBuffer sql = new StringBuffer()
	   	.append("DELETE FROM ISW_RESOURCE_KIND WHERE ID in ( :ids )");
	   
	   SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());

	   query.setParameterList("ids", resourceKindIds);
	   
	   query.executeUpdate();
   }

}
