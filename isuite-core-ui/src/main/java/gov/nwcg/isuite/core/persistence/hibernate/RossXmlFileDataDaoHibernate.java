package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileDataImpl;
import gov.nwcg.isuite.core.persistence.RossXmlFileDataDao;
import gov.nwcg.isuite.core.persistence.hibernate.query.RossResourceQuery;
import gov.nwcg.isuite.core.vo.RossXmlFileDataVo;
import gov.nwcg.isuite.core.vo.rossimport2.RossResourceVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.ShortUtil;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class RossXmlFileDataDaoHibernate extends TransactionSupportImpl implements RossXmlFileDataDao{
	private final CrudDao<RossXmlFileData> crudDao;

	public RossXmlFileDataDaoHibernate(final CrudDao<RossXmlFileData> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDataDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RossXmlFileData getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,clazz);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDataDao#save(gov.nwcg.isuite.core.domain.RossXmlFileData)
	 */
	public void save(RossXmlFileData persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDataDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RossXmlFileData> persistables) throws PersistenceException{
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDataDao#delete(gov.nwcg.isuite.core.domain.RossXmlFileData)
	 */
	public void delete(RossXmlFileData persistable) throws PersistenceException{
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDataDao#getAllByRossXmlFileId(java.lang.Long)
	 */
	public Collection<RossXmlFileDataVo> getAllByRossXmlFileId(Long rossXmlFileId) throws PersistenceException{
		
		return null;
	}

	public Collection<RossXmlFileData> getAllByRossXmlFileId(Long rossXmlFileId, Boolean rossAssignment) throws PersistenceException{
		Criteria crit = getHibernateSession().createCriteria(RossXmlFileDataImpl.class);

		crit.createAlias("this.rossXmlFile", "rxf");
		
		crit.add(Restrictions.eq("rxf.id", rossXmlFileId));
		crit.add(Restrictions.eq("this.rossAssignment", ShortUtil.toShort(rossAssignment)));
		crit.add(Restrictions.eq("this.importStatus", ""));
		
		return crit.list();
	}

	public Collection<RossResourceVo> getNewResourcesByRossXmlFileId(Long rossXmlFileId, Long incidentId, Boolean rossAssignment) throws PersistenceException{
		String sql = "select id as rossXmlFileDataId "+
					   ", res_id as rossResId " +
				       ", req_id as rossResReqId " +
				       ", sortRequestNumber(req_number) as sortRequestNumberField " +
				       ", req_number as requestNumber " +
				       ", res_name as resourceName " +
				       ", res_prov_unit_code as unitCode " +
				       ", res_prov_agency_abbrev as agencyCode " +
				       ", jet_port as jetPort " +
				       ", filled_catalog_item_code as itemCode " +
				       ", filled_catalog_item_name as itemName " +
				       ", mob_etd as mobDate " +
				       ", vendor_name as vendorName " +
				       ", vendor_owned_flag as vendorOwnedFlag " +
				       ", req_catalog_name as requestCatalogName " +
				       ", req_category_name as requestCategoryName " +
				       ", last_name as lastName " +
				       ", first_name as firstName " +
				       ", middle_name as middleName " +
				       ", assignment_name as assignmentName " +
					   "from isw_ross_xml_file_data " +
					   "where ross_xml_file_id = " + rossXmlFileId + " " +
					   "and (import_status is null or import_status = '') " +
					   "and is_ross_assignment = " + ShortUtil.toShort(rossAssignment) + " ";

		if(LongUtility.hasValue(incidentId)){
			sql=sql+"and req_id not in "+
					"("+
					"   select case when ross_res_req_id is null then -1 else ross_res_req_id end as inc_res_ross_req_id from isw_incident_resource "+
					"   where incident_id = " + incidentId + " " +
					") ";
			sql=sql+"and req_id not in "+
					"("+
					"   select ross_res_req_id from isw_ross_inc_data_blacklist "+
					"   where ross_inc_id = " +
					"      (select ross_inc_id from isw_ross_xml_file where id = " + rossXmlFileId +" ) " +
					") ";
		}

		sql=sql+"order by sortRequestNumberField ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(RossResourceVo.class);
 	    crt.addProjection("id", "rossXmlFileDataId");
 	    crt.addProjection("rossResId", "rossResId");
 	    crt.addProjection("rossResReqId", "rossResReqId");
 	    crt.addProjection("sortRequestNumberField", "sortRequestNumber");
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("resourceName", "resourceName");
 	    crt.addProjection("agencyCode", "agencyCode");
 	    crt.addProjection("unitCode", "unitCode");
 	    crt.addProjection("jetPort", "jetPort");
 	    crt.addProjection("itemCode", "itemCode");
 	    crt.addProjection("itemName", "itemName");
 	    crt.addProjection("mobDate", "mobDate");
 	    crt.addProjection("vendorName", "vendorName");
 	    crt.addProjection("vendorOwnedFlag", "vendorOwnedFlag");
 	    crt.addProjection("requestCatalogName", "requestCatalogName");
 	    crt.addProjection("requestCategoryName", "requestCategoryName");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("middleName", "middleName");
 	    crt.addProjection("assignmentName", "assignmentName");
 	    
        crt.addScalar("rossXmlFileDataId", Long.class.getName());
        crt.addScalar("rossResId", Long.class.getName());
        crt.addScalar("rossResReqId", Long.class.getName());
        crt.addScalar("mobDate", Date.class.getName());
        
		query.setResultTransformer(crt);
		
		return query.list();
	}

	public RossResourceVo getExcludedResourceByRossXmlFileId(Long rossResReqId, Long rossXmlFileId, Long incidentId, Boolean rossAssignment) throws PersistenceException{
		String sql = "select id as rossXmlFileDataId "+
					   ", res_id as rossResId " +
				       ", req_id as rossResReqId " +
				       ", sortRequestNumber(req_number) as sortRequestNumberField " +
				       ", req_number as requestNumber " +
				       ", res_name as resourceName " +
				       ", res_prov_unit_code as unitCode " +
				       ", res_prov_agency_abbrev as agencyCode " +
				       ", jet_port as jetPort " +
				       ", filled_catalog_item_code as itemCode " +
				       ", filled_catalog_item_name as itemName " +
				       ", mob_etd as mobDate " +
				       ", vendor_name as vendorName " +
				       ", vendor_owned_flag as vendorOwnedFlag " +
				       ", req_catalog_name as requestCatalogName " +
				       ", req_category_name as requestCategoryName " +
				       ", last_name as lastName " +
				       ", first_name as firstName " +
				       ", middle_name as middleName " +
				       ", assignment_name as assignmentName " +
					   "from isw_ross_xml_file_data r " +
					   "where ross_xml_file_id = " + rossXmlFileId + " " +
					   "and import_status = 'EXCLUDED' " +
					   "and is_ross_assignment = " + ShortUtil.toShort(rossAssignment) + " " +
					   "and id = (select max(id) from isw_ross_xml_file_data where res_id = r.res_id and req_id = r.req_id) ";

		sql=sql+"and req_id = " + rossResReqId + " " ;

		sql=sql+"order by sortRequestNumberField ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
        CustomResultTransformer crt = new CustomResultTransformer(RossResourceVo.class);
 	    crt.addProjection("id", "rossXmlFileDataId");
 	    crt.addProjection("rossResId", "rossResId");
 	    crt.addProjection("rossResReqId", "rossResReqId");
 	    crt.addProjection("sortRequestNumberField", "sortRequestNumber");
 	    crt.addProjection("requestNumber", "requestNumber");
 	    crt.addProjection("resourceName", "resourceName");
 	    crt.addProjection("agencyCode", "agencyCode");
 	    crt.addProjection("unitCode", "unitCode");
 	    crt.addProjection("jetPort", "jetPort");
 	    crt.addProjection("itemCode", "itemCode");
 	    crt.addProjection("itemName", "itemName");
 	    crt.addProjection("mobDate", "mobDate");
 	    crt.addProjection("vendorName", "vendorName");
 	    crt.addProjection("vendorOwnedFlag", "vendorOwnedFlag");
 	    crt.addProjection("requestCatalogName", "requestCatalogName");
 	    crt.addProjection("requestCategoryName", "requestCategoryName");
 	    crt.addProjection("lastName", "lastName");
 	    crt.addProjection("firstName", "firstName");
 	    crt.addProjection("middleName", "middleName");
 	    crt.addProjection("assignmentName", "assignmentName");
 	    
        crt.addScalar("rossXmlFileDataId", Long.class.getName());
        crt.addScalar("rossResId", Long.class.getName());
        crt.addScalar("rossResReqId", Long.class.getName());
        crt.addScalar("mobDate", Date.class.getName());
        
		query.setResultTransformer(crt);
		
		return (RossResourceVo)query.uniqueResult();
	}
	
	public void updateStatuses(Collection<Long> rossResourceIds, String rossIncId, String status) throws PersistenceException {


		if(null != rossResourceIds && rossResourceIds.size()>999){
			/*
			 * Split out rossResourceIds in chunks of 999
			 */
			Hashtable table = CollectionUtility.splitCollection(rossResourceIds, 999);
			
			for(int i=1;i<(table.size()+1);i++){
				Collection<Long> ids = (Collection<Long>)table.get(i);

				String sql = RossResourceQuery.getUpdateStatuses(super.isOracleDialect(), rossIncId, status);
				SQLQuery query = getHibernateSession().createSQLQuery(sql);
				
				query.setParameterList("ids", ids);
				
				query.executeUpdate();
				
			}
		}else{
			String sql = RossResourceQuery.getUpdateStatuses(super.isOracleDialect(), rossIncId, status);
			SQLQuery query = getHibernateSession().createSQLQuery(sql);
			
			query.setParameterList("ids", rossResourceIds);
			
			query.executeUpdate();
		}
	
	}

	public void updateResourceImportStatus(Long rossResReqId, String rossIncId, String status) throws PersistenceException {

		String sql = RossResourceQuery.getUpdateResourceImportStatus(super.isOracleDialect(), rossIncId, status, rossResReqId);
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		
		query.executeUpdate();

	}
	
}
