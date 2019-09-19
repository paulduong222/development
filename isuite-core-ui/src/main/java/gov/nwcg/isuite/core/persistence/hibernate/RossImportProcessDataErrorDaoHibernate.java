package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessDataErrorImpl;
import gov.nwcg.isuite.core.persistence.RossImportProcessDataErrorDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossDataErrorQuery;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossResourceErrorQuery;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.TypeConverter;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class RossImportProcessDataErrorDaoHibernate extends TransactionSupportImpl implements RossImportProcessDataErrorDao {
	private final CrudDao<RossImportProcessDataError> crudDao;

	/**
	 * @param crudDao
	 */
	public RossImportProcessDataErrorDaoHibernate(final CrudDao<RossImportProcessDataError> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}

	public void delete(RossImportProcessDataError persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	public RossImportProcessDataError getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,RossImportProcessDataErrorImpl.class);
	}

	public void save(RossImportProcessDataError persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	public void saveAll(Collection<RossImportProcessDataError> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	public void checkForIncidentPdcConflict(Long rossXmlFileId, String unitCode) throws PersistenceException,Exception {
		SQLQuery query = null;
		int cnt = 0;
		
		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncPdcQueryCount(rossXmlFileId, unitCode,super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt == 0 ){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncPdcQueryCount2(rossXmlFileId, unitCode,super.isOracleDialect()));
			cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
			if(cnt == 0 || cnt > 1){
				query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncPdcQuery(rossXmlFileId, unitCode, super.isOracleDialect()));
				query.executeUpdate();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDataErrorDao#checkForIncidentErrors(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void checkForIncidentErrors(Long rossXmlFileId, String unitCode, String agencyCode, String eventType) throws PersistenceException,Exception {
		SQLQuery query = null;
		int cnt = 0;
		
		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncUnitCodeQueryCount(rossXmlFileId, unitCode,super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncUnitCodeQuery(rossXmlFileId, unitCode, super.isOracleDialect()));
			query.executeUpdate();
		}else{
			this.checkForIncidentPdcConflict(rossXmlFileId, unitCode);
		}
		
		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncAgencyCodeQueryCount(rossXmlFileId, agencyCode,super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncAgencyCodeQuery(rossXmlFileId, agencyCode, super.isOracleDialect()));
			query.executeUpdate();
		}

		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncEventTypeQueryCount(rossXmlFileId, eventType,super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownIncEventTypeQuery(rossXmlFileId, eventType, super.isOracleDialect()));
			query.executeUpdate();
		}

	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDataErrorDao#checkForResourceErrors(java.lang.Long)
	 */
	public void checkForResourceErrors(Long rossXmlFileId) throws PersistenceException,Exception {
		SQLQuery query = null; 
		int cnt=0;
		
		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResAgencyCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.updateUnknownResAgencyCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResAgencyCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}

		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResAssignmentItemCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.updateUnknownResAssignmentItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResAssignmentItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}

		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResQualItemCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.updateUnknownResQualItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResQualItemCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
		
		/*
		query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResJetPortCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInt(query.uniqueResult());
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResJetPortCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossDataErrorQuery.getUnknownResJetPortCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
		*/
		
		query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResUnitCodeQueryCount(rossXmlFileId, super.isOracleDialect()));
		cnt = TypeConverter.convertToInteger(query.uniqueResult()).intValue();
		if(cnt > 0){
			query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.updateUnknownResUnitCodeQuery(rossXmlFileId, super.isOracleDialect()));
			//query = getHibernateSession().createSQLQuery(RossResourceErrorQuery.getUnknownResUnitCodeQuery(rossXmlFileId, super.isOracleDialect()));
			query.executeUpdate();
		}
	}

	public Collection<RossImportProcessDataError> getByRossXmlFileId(Long rxfId) throws PersistenceException {
		
		Criteria crit = getHibernateSession().createCriteria(RossImportProcessDataErrorImpl.class);
		

		crit.createAlias("rossXmlFile", "rxf");
		
		crit.add(Restrictions.eq("rxf.id", rxfId));
		
				
		crit.add(Restrictions
				.sqlRestriction("(" +
						"(this_.ross_xml_file_data_id not in " +
						"      (" +
						"		select id from isw_ross_xml_file_data where ross_xml_file_id = " + rxfId + " " +
						"       and res_id in (select res_id from isw_ross_inc_data_blacklist)" +
						"       ) " +
						")" +
						")"));
		
		return crit.list();
	}
	
}
