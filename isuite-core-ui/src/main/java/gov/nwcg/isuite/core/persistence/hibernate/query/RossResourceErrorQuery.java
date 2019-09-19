package gov.nwcg.isuite.core.persistence.hibernate.query;

public class RossResourceErrorQuery {

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

	public static String getUnknownIncItemCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (FILLED_CATALOG_ITEM_CODE IS NULL OR FILLED_CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the item code of ' || FILLED_CATALOG_ITEM_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (FILLED_CATALOG_ITEM_CODE IS NULL OR FILLED_CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
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
	
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResUnitCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
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
	public static String getUnknownResUnitCodeQuery(Long rossXmlFileId, Boolean isOracle) {
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

	public static String updateUnknownResUnitCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET RES_PROV_UNIT_CODE = 'UNK' ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}

	public static String getUnknownResItemCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (FILLED_CATALOG_ITEM_CODE IS NULL OR FILLED_CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the item code of ' || FILLED_CATALOG_ITEM_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (FILLED_CATALOG_ITEM_CODE IS NULL OR FILLED_CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	

	public static String updateUnknownResItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET FILLED_CATALOG_ITEM_CODE = 'UNK' ")
		   .append(" WHERE (FILLED_CATALOG_ITEM_CODE IS NULL OR FILLED_CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResAgencyCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
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
	public static String getUnknownResAgencyCodeQuery(Long rossXmlFileId, Boolean isOracle) {
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
	
	public static String updateUnknownResAgencyCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET RES_PROV_AGENCY_ABBREV = 'UNK' ")
		   .append(" WHERE RES_PROV_AGENCY_ABBREV NOT IN (SELECT AGENCY_CODE FROM ISWL_AGENCY ) " )
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResJetPortCodeQueryCount(Long rossXmlFileId, Boolean isOracle){
        StringBuffer sql = new StringBuffer();
        
        sql.append("SELECT count(ID) ")
           .append("FROM ISW_ROSS_XML_FILE_DATA ")
           .append("WHERE JET_PORT IS NOT NULL ")
           .append("AND ( select count(id) from iswl_jet_port where code = substr(jet_port,regexp_instr(jet_port,'[(]') + 1,3)) = 0  " )
           .append("AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_JETPORT_CODE' )) ")
           .append("AND ROSS_XML_FILE_ID = "+rossXmlFileId + " ");        
		
           return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResJetPortCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_RES_ERROR (ID, ROSS_XML_FILE_DATA_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_RES_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_RES_ERROR')" ) + ", ID, 'Unable to resolve the jet port code of ' || JET_PORT || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_JETPORT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE JET_PORT IS NOT NULL " )
		   .append(" AND ( select count(id) from iswl_jet_port where code = substr(jet_port,regexp_instr(jet_port,'[(]') + 1,3)) = 0 " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_res_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_JETPORT_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	

}
