package gov.nwcg.isuite.core.persistence.hibernate.query;

public class RossIncidentErrorQuery {

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncEventTypeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE UPPER(INCIDENT_EVENT_TYPE) NOT IN (SELECT UPPER(EVENT_TYPE) FROM ISWL_EVENT_TYPE) " )
		   .append(" AND ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_EVTTYPE_CODE' )) ") 
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncEventTypeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the unit code of ' || RES_PROV_UNIT_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_UNIT_CODE' )) ") 
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncUnitCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_UNIT_CODE' )) ") 
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncUnitCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the unit code of ' || RES_PROV_UNIT_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_UNIT_CODE' )) ") 
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncAgencyCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT COUNT(ID) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_AGENCY_ABBREV NOT IN (SELECT AGENCY_CODE FROM ISWL_AGENCY ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_AGENCY_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncAgencyCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the agency code of ' || RES_PROV_AGENCY_ABBREV || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_AGENCY_ABBREV NOT IN (SELECT AGENCY_CODE FROM ISWL_AGENCY ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_AGENCY_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	
	
	
}
