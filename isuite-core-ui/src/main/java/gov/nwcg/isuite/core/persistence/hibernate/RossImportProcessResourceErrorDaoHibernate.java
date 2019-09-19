package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossImportProcessResourceError;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessResourceErrorImpl;
import gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossResourceErrorQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.math.BigDecimal;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class RossImportProcessResourceErrorDaoHibernate extends TransactionSupportImpl implements RossImportProcessResourceErrorDao {
	private final CrudDao<RossImportProcessResourceError> crudDao;

	/**
	 * @param crudDao
	 */
	public RossImportProcessResourceErrorDaoHibernate(final CrudDao<RossImportProcessResourceError> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#delete(gov.nwcg.isuite.core.domain.RossImportProcessResourceError)
	 */
	public void delete(RossImportProcessResourceError persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RossImportProcessResourceError getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,RossImportProcessResourceErrorImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#save(gov.nwcg.isuite.core.domain.RossImportProcessResourceError)
	 */
	public void save(RossImportProcessResourceError persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RossImportProcessResourceError> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#checkForErrors(java.lang.Long)
	 */
	public void checkForErrors(Long rossXmlFileId) throws PersistenceException,Exception {
		SQLQuery query = null; 
		query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResAgencyCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		int cnt = TypeConverter.convertToInt(query.uniqueResult());
		if(cnt > 0){
			
			query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.updateUnknownResAgencyCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResAgencyCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}

		query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResItemCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInt(query.uniqueResult());
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.updateUnknownResItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
		
		/* skip for now
		query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResJetPortCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInt(query.uniqueResult());
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResJetPortCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
		*/
		
		query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResUnitCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInt(query.uniqueResult());
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.updateUnknownResUnitCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResUnitCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessResourceErrorDao#getByRossXmlFileId(java.lang.Long)
	 */
	public Collection<RossImportProcessResourceError> getByRossXmlFileId(Long rxfId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RossImportProcessResourceErrorImpl.class);
		
		crit.createAlias("this.rossXmlFileData", "rxfd");
		crit.createAlias("rxfd.rossXmlFile", "rxf");
		
		crit.add(Restrictions.eq("rxf.id", rxfId));
		
		return crit.list();
	}
	
}
