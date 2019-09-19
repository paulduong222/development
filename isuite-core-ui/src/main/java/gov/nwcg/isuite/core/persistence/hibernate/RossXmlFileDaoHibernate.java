package gov.nwcg.isuite.core.persistence.hibernate;

import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.impl.RossXmlFileImpl;
import gov.nwcg.isuite.core.filter.RossIncidentFilter;
import gov.nwcg.isuite.core.persistence.RossXmlFileDao;
import gov.nwcg.isuite.core.vo.RossXmlFileGridVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.hibernate.TransactionSupportImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.transformer.CustomResultTransformer;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

public class RossXmlFileDaoHibernate extends TransactionSupportImpl implements RossXmlFileDao{
	private final CrudDao<RossXmlFile> crudDao;

	public RossXmlFileDaoHibernate(final CrudDao<RossXmlFile> crudDao){
		if ( crudDao == null ) {throw new IllegalArgumentException("crudDao can not be null");}
		this.crudDao=crudDao;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDao#getById(java.lang.Long, java.lang.Class)
	 */
	public RossXmlFile getById(Long id, Class clazz) throws PersistenceException {
		return crudDao.getById(id,RossXmlFileImpl.class);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDao#save(gov.nwcg.isuite.core.domain.RossXmlFile)
	 */
	public void save(RossXmlFile persistable) throws PersistenceException {
		crudDao.save(persistable);
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDao#saveAll(java.util.Collection)
	 */
	public void saveAll(Collection<RossXmlFile> persistables) throws PersistenceException{
		
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDao#delete(gov.nwcg.isuite.core.domain.RossXmlFile)
	 */
	public void delete(RossXmlFile persistable) throws PersistenceException{
		crudDao.delete(persistable);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.persistence.RossXmlFileDao#getGrid()
	 */
	public Collection<RossXmlFileGridVo> getGrid(RossIncidentFilter filter) throws PersistenceException{
		StringBuffer sql = new StringBuffer()
				.append("SELECT ID, INCIDENT_NAME as incidentName " +
									",INCIDENT_NUMBER as incidentNumber " + 
									",INCIDENT_EVENT_TYPE as incidentEventType " +
									",CREATED_DATE as createdDate " +
									",to_char(created_date,'HH:MI:SS') as creationTime " +
									",IMPORTED_DATE as importedDate " +
									",to_char(imported_date,'HH:MI:SS') as importedTime " +
									",ROSS_INC_ID as rossIncidentId " +
									",UNIT_CODE as unitCode " +
					 "FROM ISW_ROSS_XML_FILE rxf " +
					 "WHERE ID IN (" +
					 " SELECT MAX(ID) FROM ISW_ROSS_XML_FILE  " +
					 "WHERE incident_number = rxf.incident_number " +
					 "AND ROSS_INC_ID NOT IN (SELECT " + (super.isOracleDialect() ? "ROSS_INC_ID" : "CAST (ROSS_INC_ID as varchar)") + " FROM ISW_ROSS_INC_BLACKLIST ) " +
					 "");
				/*
                     "AND UNIT_CODE IN ( " + 
                     "	 SELECT UNIT_CODE FROM ISW_ORGANIZATION_PDC WHERE PDC_ID IN (  " +
                     "         SELECT ORGANIZATION_ID FROM ISW_USER_ORGANIZATION WHERE USER_ID = "+filter.getCurrentUserId() + " " +
               		 "   ) " + 
                     ") " 
				);
				*/
		if(null != filter){
			try{
				if(StringUtility.hasValue(filter.getIncidentName())){
					sql.append(" AND INCIDENT_NAME LIKE '" + filter.getIncidentName().toUpperCase() + "%' ");
				}
				if(StringUtility.hasValue(filter.getIncidentNumber())){
					sql.append(" AND INCIDENT_NUMBER LIKE '" + filter.getIncidentNumber().toUpperCase() + "%' ");
				}
				if(StringUtility.hasValue(filter.getIncidentEventType())){
					sql.append(" AND INCIDENT_EVENT_TYPE LIKE '" + filter.getIncidentEventType().toUpperCase() + "%' ");
				}
				//if(StringUtility.hasValue(filter.getIncidentEventType())){
				//	sql.append(" AND INCIDENT_EVENT_TYPE LIKE '" + filter.getIncidentEventType().toUpperCase() + "' ");
				//}
				if(filter.getImported()){
					sql.append(" AND IMPORT_STATUS = 'IMPORTED' ");
				}else{
					sql.append( "AND IMPORT_STATUS = 'NA' " );
				}
				
				if(StringUtility.hasValue(filter.getCreationDateString())) {
					String sqlCreationDateFilter = getCrypticDateFilterSql(filter.getCreationDateString(), "CREATED_DATE");
					if(StringUtility.hasValue(sqlCreationDateFilter))
						sql.append("AND " + sqlCreationDateFilter + " ");
				}
				
				if(StringUtility.hasValue(filter.getCreationTime())){
					sql.append("AND to_char(CREATED_DATE, 'HHMISS') like '" + filter.getCreationTime() + "%' ");
//					String sqlCreatedHourFilter = super.getTimeFilterHourSql(filter.getCreationTime(), "CREATED_DATE");
//					if(StringUtility.hasValue(sqlCreatedHourFilter))
//						sql.append("AND " + sqlCreatedHourFilter + " ");
//					String sqlCreatedMinuteFilter = super.getTimeFilterMinuteSql(filter.getCreationTime(), "CREATED_DATE");
//					if(StringUtility.hasValue(sqlCreatedMinuteFilter))
//						sql.append("AND " + sqlCreatedMinuteFilter + " ");
				}
				
				if(StringUtility.hasValue(filter.getImportedDateString())){
					String sqlFilter = getCrypticDateFilterSql(filter.getImportedDateString(), "IMPORTED_DATE");
					if(StringUtility.hasValue(sqlFilter))
						sql.append("AND " + sqlFilter + " ");
				}
				
				if(StringUtility.hasValue(filter.getImportedTime())){
					sql.append("AND to_char(IMPORTED_DATE, 'HHMISS') like '" + filter.getImportedTime() + "%' ");
//					String sqlHourFilter = super.getTimeFilterHourSql(filter.getImportedTime(), "IMPORTED_DATE");
//					if(StringUtility.hasValue(sqlHourFilter))
//						sql.append("AND " + sqlHourFilter + " ");
//					String sqlMinuteFilter = super.getTimeFilterMinuteSql(filter.getImportedTime(), "IMPORTED_DATE");
//					if(StringUtility.hasValue(sqlMinuteFilter))
//						sql.append("AND " + sqlMinuteFilter + " ");
				}

				if(BooleanUtility.isTrue(filter.getHasExcludedResources())){
					/*String rsql="AND (" +
						"SELECT COUNT(ID) FROM ISW_ROSS_INC_DATA_BLACKLIST "+
						"WHERE RES_ID IN (" +
							"SELECT RES_ID FROM ISW_ROSS_XML_FILE_DATA "+
							"WHERE  ross_xml_file_id = rxf.id " +
							  "AND IMPORT_STATUS = 'EXCLUDED' " +
						  ") " +
						") > 0 ";*/
					if(super.isOracleDialect()==true){
						String rsql="AND exists (" +
						"SELECT 1 FROM ISW_ROSS_INC_DATA_BLACKLIST "+
						"WHERE ROSS_RES_REQ_ID IN (" +
							"SELECT REQ_ID FROM ISW_ROSS_XML_FILE_DATA "+
							"WHERE  ross_xml_file_id = rxf.id " +
							  "AND IMPORT_STATUS = 'EXCLUDED' " +
						  ") " +
						") ";
						sql.append(rsql);
						
					}else{
						String rsql="AND (" +
						"SELECT COUNT(ID) FROM ISW_ROSS_INC_DATA_BLACKLIST "+
						"WHERE cast(ross_inc_id as varchar) = rxf.ross_inc_id  and ROSS_RES_REQ_ID IN (" +
							"SELECT REQ_ID FROM ISW_ROSS_XML_FILE_DATA "+
							//"WHERE  ross_xml_file_id = rxf.id " +
							  "WHERE IMPORT_STATUS = 'EXCLUDED' " +
							  "AND (select count(rxfd.req_id) from isw_ross_xml_file_data rxfd where rxfd.req_id = req_id and import_status='IMPORTED' ) < 1 " +
						  ") " +
						") > 0 ";
						sql.append(rsql);
					}

					String rsql2="AND (" +
					"rxf.ROSS_INC_ID in ("+
					" select ross_incident_id " +
					" from isw_incident " +
					" where id in ("+
					"   select incident_id " + 
					"   from isw_restricted_incident_user " +
					"   where user_id = " + filter.getCurrentUserId() + " " +
					" ) "+
					") ) ";
					sql.append(rsql2);
				}
			}catch(Exception e){
				throw new PersistenceException(e);
			}
		}

		sql.append(") ");

		sql.append(" ORDER BY INCIDENT_NUMBER ASC ");
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql.toString());

		CustomResultTransformer crt = new CustomResultTransformer(RossXmlFileGridVo.class);
	    crt.addScalar("id", Long.class.getName());
	    crt.addScalar("createdDate", Date.class.getName());
	    crt.addScalar("importedDate", Date.class.getName());
	    crt.addScalar("rossIncidentId", String.class.getName());
	    
	    crt.addProjection("incidentNumber", "incidentNumber");
	    crt.addProjection("incidentName", "incidentName");
	    crt.addProjection("incidentEventType", "incidentEventName");
	    crt.addProjection("createdDate", "creationDate");
	    crt.addProjection("creationTime", "creationTime");
	    crt.addProjection("rossIncidentId","rossIncidentId");
	    crt.addProjection("importedDate", "importedDate");
	    crt.addProjection("importedTime", "importedTime");
	    crt.addProjection("unitCode", "unitCode");
	    
	    query.setResultTransformer(crt);
	    
    	return query.list();
	}

	public Collection<RossXmlFile> getByIncidentNumber(String incNumber, Long excludeId) throws PersistenceException {

		Criteria crit = getHibernateSession().createCriteria(RossXmlFileImpl.class);
		crit.add(Restrictions.eq("incidentNumber",incNumber));
		crit.add(Restrictions.ne("id", excludeId));
		
		return crit.list();
	}
	
	public void deleteByRossIncidentId(String rossIncidentId) throws PersistenceException {
		String sql = "DELETE FROM ISW_ROSS_XML_FILE "+
				     "WHERE ROSS_INC_ID = '" + rossIncidentId + "' ";
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void purgeDeletedFileData(Long rxfId) throws PersistenceException {
		String sql="delete from isw_ross_xml_file_data "+
				   "where ross_xml_file_id = " + rxfId + " "; 
		
		SQLQuery query = getHibernateSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void purgeImportedResources(Long rxfId) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
			sql.append("delete ")
			   .append("from isw_ross_xml_file_data ")
			   .append("where id in ( ")
			   .append("  select rxfd2.id ")
			   .append("  from ")
			   .append("    isw_ross_xml_file_data rxfd2 ")
			   .append("    ,isw_ross_xml_file rxf ")
			   .append("	,isw_incident_resource ir ")
			   .append("	,isw_incident i ")
			   .append("  where rxfd2.ross_xml_file_id = rxf.id ")
			   .append("  and rxf.id = " + rxfId + " ")
			   .append("  and rxf.import_status = 'IMPORTED' ")
			   .append("  and i.ross_incident_id = rxf.ross_inc_id ")
			   .append("  and ir.incident_id = i.id ");
			if(super.isOracleDialect()==true){
				sql.append("and rxfd2.req_id = ir.ross_res_req_id ");
			}else{
				sql.append("and cast(rxfd2.req_id as bigint) = ir.ross_res_req_id ");
			}
			sql.append(") ");

		SQLQuery q = getHibernateSession().createSQLQuery(sql.toString());
		q.executeUpdate();
	}
	
	public void updateAllExcluded() throws PersistenceException {
		try{
			String sql="update isw_ross_xml_file_data " + 
					   "set import_status='EXCLUDED' " +
					   "where id in ( " +
					   "  select rxfd.id " +
					   "  from isw_ross_xml_file_data rxfd " + 
					   "      , isw_ross_inc_data_blacklist bdata " +
					   "  where rxfd.req_id = bdata.ross_res_req_id " +
					   "  and " +
					   "  ( " +
					   "    bdata.import_status is null " +
					   "    or " +
					   "    bdata.import_status not in ('IMPORTED','INPROCESS') " +
					   "  ) " +
					   "  and rxfd.id = (select max(id) from isw_ross_xml_file_data where req_id = bdata.ross_res_req_id) " +
					   ") ";
			SQLQuery q = getHibernateSession().createSQLQuery(sql);
			q.executeUpdate();
		}catch(Exception e){
			System.out.println("");
		}
	}

	public void updateIdByReqId(Long rxfId,Object reqId) throws PersistenceException {
		try{
			String sql="update isw_ross_xml_file_data " +
					   "set ross_xml_file_id = "+rxfId+" " +
					   "where import_status='EXCLUDED' " +
					   "and id = (select max(id) from isw_ross_xml_file_data where req_id = "+reqId+")"	;
			SQLQuery q = getHibernateSession().createSQLQuery(sql);
			q.executeUpdate();
		}catch(Exception e){
			System.out.println("");
		}
	}
	
}
