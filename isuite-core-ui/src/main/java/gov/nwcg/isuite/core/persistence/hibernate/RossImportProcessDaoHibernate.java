package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessImpl;
import gov.nwcg.isuite.core.persistence.RossImportProcessDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossResourceQuery;
import gov.nwcg.isuite.core.vo.RossImportProcessEISuiteResourceVo;
import gov.nwcg.isuite.core.vo.RossImportProcessResourceVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossEISuiteResourceVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;
import java.util.Date;

import org.hibernate.SQLQuery;

public class RossImportProcessDaoHibernate extends TransactionSupportImpl implements RossImportProcessDao {
	private final CrudDao<RossImportProcess> crudDao;

	public RossImportProcessDaoHibernate(final CrudDao<RossImportProcess> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDao#delete(gov.nwcg.isuite.core.domain.RossImportProcess)
	 */
	public void delete(RossImportProcess persistable) throws PersistenceException {
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RossImportProcess getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,RossImportProcessImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDao#save(gov.nwcg.isuite.core.domain.RossImportProcess)
	 */
	public void save(RossImportProcess persistable) throws PersistenceException {
		crudDao.save(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RossImportProcess> persistables) throws PersistenceException {
		crudDao.saveAll(persistables);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossImportProcessDao#getValidRossResources(java.lang.Long)
	 */
	public Collection<RossImportProcessResourceVo> getValidRossResources(Long rossXmlFileId, Long incidentId) throws PersistenceException {

		String sql = RossResourceQuery.getValidRossXmlFileResources(rossXmlFileId, incidentId,super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		CustomResultTransformer crt = new CustomResultTransformer(RossImportProcessResourceVo.class);
		crt.addScalar("rossXmlFileDataId", Long.class.getName());
		crt.addScalar("rossResId", Long.class.getName());
		crt.addScalar("rossResReqId", Long.class.getName());
		crt.addScalar("unitId", Long.class.getName());
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("agencyId", Long.class.getName());
		crt.addScalar("mobEtd", Date.class.getName());

		query.setResultTransformer(crt);

		try{
			return query.list();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	public Collection<RossImportProcessEISuiteResourceVo> getEISuiteResources(Long incidentId) throws PersistenceException {
		
		String sql = RossResourceQuery.getEISuiteResources(incidentId, super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(RossImportProcessEISuiteResourceVo.class);
		crt.addScalar("assignmentId", Long.class.getName());
		crt.addScalar("rossResId", Long.class.getName());
		crt.addScalar("rossResReqId", Long.class.getName());
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("workPeriodId", Long.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
	}

	public Collection<RossEISuiteResourceVo> getEISuiteResources2(Long incidentId) throws PersistenceException {
		
		String sql = RossResourceQuery.getEISuiteResources2(incidentId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(RossEISuiteResourceVo.class);
		
 	    crt.addProjection("sortRequestNumberField", "sortRequestNumber");
 	    
		crt.addScalar("assignmentId", Long.class.getName());
		crt.addScalar("rossResId", Long.class.getName());
		crt.addScalar("rossResReqId", Long.class.getName());
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("workPeriodId", Long.class.getName());
		crt.addScalar("isPerson", Boolean.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
	}

	public Collection<RossEISuiteResourceVo> getEISuiteResources3(Long incidentId) throws PersistenceException {
		
		String sql = RossResourceQuery.getEISuiteResources3(incidentId, super.isOracleDialect());
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		CustomResultTransformer crt = new CustomResultTransformer(RossEISuiteResourceVo.class);
		
 	    crt.addProjection("sortRequestNumberField", "sortRequestNumber");
 	    
		crt.addScalar("assignmentId", Long.class.getName());
		crt.addScalar("rossResId", Long.class.getName());
		crt.addScalar("rossResReqId", Long.class.getName());
		crt.addScalar("resourceId", Long.class.getName());
		crt.addScalar("incidentResourceId", Long.class.getName());
		crt.addScalar("workPeriodId", Long.class.getName());
		crt.addScalar("isPerson", Boolean.class.getName());

		query.setResultTransformer(crt);
		
		return query.list();
	}

	public Collection<RossImportProcessResourceVo> getRossResourcesByResIds(Long rossXmlFileId,Collection<Long> rossResourceIds) throws PersistenceException {

		String sql = RossResourceQuery.getRossXmlFileResourcesByResId(rossXmlFileId,super.isOracleDialect());
		SQLQuery query = getHibernateSession().createSQLQuery(sql);

		query.setParameterList("ids", rossResourceIds);
		CustomResultTransformer crt = new CustomResultTransformer(RossImportProcessResourceVo.class);
		crt.addScalar("rossXmlFileDataId", Long.class.getName());
		crt.addScalar("rossResId", Long.class.getName());
		crt.addScalar("rossResReqId", Long.class.getName());
		crt.addScalar("unitId", Long.class.getName());
		crt.addScalar("itemId", Long.class.getName());
		crt.addScalar("agencyId", Long.class.getName());
		crt.addScalar("mobEtd", Date.class.getName());

		query.setResultTransformer(crt);

		try{
			return query.list();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	
}
