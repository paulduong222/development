package gov.nwcg.isuite.core.persistence.hibernate.query;

public class RossDataErrorQuery {

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncUnitCodeQueryCount(Long rossXmlFileId, String code,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_UNIT_CODE' )) ") 
		   .append(" AND (SELECT  COUNT(ID) FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+code+"') = 0 ")
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUpdateUnknownIncUnitCodeQuery(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE ")
		   .append("SET UNIT_CODE = 'ZZ-ZZZ' ")
		   .append("WHERE ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}


	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncUnitCodeQuery(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, 'Unable to resolve the unit code of "+code+" for incident', 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_INCIDENT_UNIT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_UNIT_CODE' )) ") 
		   .append(" AND (SELECT  COUNT(ID) FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+code+"') = 0 ")
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}

	public static String getUnknownIncPdcQueryCount(Long rossXmlFileId, String code,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_PDC' )) ") 
		   //.append(" AND (SELECT COUNT(ORGANIZATION_ID) FROM ISW_ORGANIZATION_PDC WHERE ORGANIZATION_ID IN (SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+code+"')) = 0 ")
		   .append(" AND ID = " + rossXmlFileId + " ");		

		//sql.append("SELECT  COUNT(ORGANIZATION_ID) FROM ISW_ORGANIZATION_PDC WHERE ORGANIZATION_ID IN (SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+code+"') ");
		
		return sql.toString();
	}

	public static String getUnknownIncPdcQueryCount2(Long rossXmlFileId, String code,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT  COUNT(ORGANIZATION_ID) FROM ISW_ORGANIZATION_PDC WHERE ORGANIZATION_ID IN (SELECT ID FROM ISW_ORGANIZATION WHERE UNIT_CODE = '"+code+"') ");
		
		return sql.toString();
	}
	
	public static String getUnknownIncPdcQuery(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, 'Unable to resolve the primary dispatch center for incident', 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_INCIDENT_PDC' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncEventTypeQueryCount(Long rossXmlFileId, String code,Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_EVTTYPE_CODE' )) ") 
		   .append(" AND (SELECT  COUNT(ID) FROM ISWL_EVENT_TYPE WHERE UPPER(EVENT_TYPE) = '"+code.toUpperCase()+"') = 0 ")
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncEventTypeQuery(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, 'Unable to resolve the incident event type', 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_INCIDENT_EVTTYPE_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_EVTTYPE_CODE' )) ") 
		   .append(" AND (SELECT  COUNT(ID) FROM ISWL_EVENT_TYPE WHERE UPPER(EVENT_TYPE) = '"+code.toUpperCase()+"') = 0 ")
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncAgencyCodeQueryCount(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT COUNT(ID) ")
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_AGENCY_CODE' )) ")		
		   .append(" AND (SELECT COUNT(ID) FROM ISWL_AGENCY WHERE AGENCY_CODE = '"+code+"')=0 " )
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
		return sql.toString();
	}	
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownIncAgencyCodeQuery(Long rossXmlFileId, String code, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, 'Unable to resolve the incident agency code', 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_INCIDENT_AGENCY_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE ")
		   .append(" WHERE ID NOT IN (select ross_xml_file_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_INCIDENT_AGENCY_CODE' )) ")		
		   .append(" AND (SELECT COUNT(ID) FROM ISWL_AGENCY WHERE AGENCY_CODE = '"+code+"')=0 " )
		   .append(" AND ID = " + rossXmlFileId + " ");		
		
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
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_UNIT_CODE' )) ") 
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}
	
	public static String updateUnknownResUnitCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET RES_PROV_UNIT_CODE = 'UNK' ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResUnitCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_DATA_ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, ROSS_XML_FILE_ID, 'Unable to resolve the unit code of ' || RES_PROV_UNIT_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_UNIT_CODE NOT IN (SELECT UNIT_CODE FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false ) + ") " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_UNIT_CODE' )) ") 
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}

	public static String getUnknownResAssignmentItemCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append("where ( ")
		   .append("(filled_catalog_item_code is null and ( filled_catalog_item_name is not null and upper(filled_catalog_item_name) not in (select upper(description) from iswl_kind))) ")
		   .append("or ")
		   .append("(filled_catalog_item_name is null and (filled_catalog_item_code is not null and filled_catalog_item_code not in (select code from iswl_kind) ) ) ")
		   .append(") ")
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND FILLED_CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}

	public static String updateUnknownResAssignmentItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET FILLED_CATALOG_ITEM_CODE = 'UNK' ")
		   .append("where ( ")
		   .append("(filled_catalog_item_code is null and ( filled_catalog_item_name is not null and upper(filled_catalog_item_name) not in (select upper(description) from iswl_kind))) ")
		   .append("or ")
		   .append("(filled_catalog_item_name is null and (filled_catalog_item_code is not null and filled_catalog_item_code not in (select code from iswl_kind) ) ) ")
		   .append(") ")
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND FILLED_CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResAssignmentItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_DATA_ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, ROSS_XML_FILE_ID, 'Unable to resolve the Assignment item code ' || FILLED_CATALOG_ITEM_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append("where ( ")
		   .append("(filled_catalog_item_code is null and ( filled_catalog_item_name is not null and upper(filled_catalog_item_name) not in (select upper(description) from iswl_kind))) ")
		   .append("or ")
		   .append("(filled_catalog_item_name is null and (filled_catalog_item_code is not null and filled_catalog_item_code not in (select code from iswl_kind) ) ) ")
		   .append(") ")
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND FILLED_CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	

	public static String getUnknownResQualItemCodeQueryCount(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("SELECT count(id) ")
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}

	public static String updateUnknownResQualItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET CATALOG_ITEM_CODE = 'UNK' ")
		   .append(" WHERE (CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResQualItemCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_DATA_ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, ROSS_XML_FILE_ID,'Unable to resolve the Qualification item code ' || CATALOG_ITEM_CODE || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE (CATALOG_ITEM_CODE NOT IN (SELECT CODE FROM ISWL_KIND ) ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_ITEM_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND CATALOG_ITEM_CODE IS NOT NULL ")
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
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
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_AGENCY_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 " );		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	
	
	public static String updateUnknownResAgencyCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("UPDATE ISW_ROSS_XML_FILE_DATA SET RES_PROV_AGENCY_ABBREV = 'UNK' ")
		   .append(" WHERE RES_PROV_AGENCY_ABBREV NOT IN (SELECT AGENCY_CODE FROM ISWL_AGENCY ) " )
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")			   
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 " );		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResAgencyCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_DATA_ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, ROSS_XML_FILE_ID, 'Unable to resolve the agency code of ' || RES_PROV_AGENCY_ABBREV || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE RES_PROV_AGENCY_ABBREV NOT IN (SELECT AGENCY_CODE FROM ISWL_AGENCY ) " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_AGENCY_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 " );		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
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
           .append("AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_JETPORT_CODE' )) ")
           .append("AND ROSS_XML_FILE_ID = "+rossXmlFileId + " ")        
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 ");		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
           return sql.toString();
	}
	
	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getUnknownResJetPortCodeQuery(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();

		sql.append("INSERT INTO ISW_ROSS_IMP_PROC_DATA_ERROR (ID, ROSS_XML_FILE_DATA_ID, ROSS_XML_FILE_ID, ERROR_DESC, NEW_VALUE_TYPE, ROSS_RES_ERROR_ID) ")
		   .append(" SELECT " + (isOracle ? "SEQ_ROSS_IMP_PROC_DATA_ERROR.nextVal" : "nextVal('SEQ_ROSS_IMP_PROC_DATA_ERROR')" ) + ", ID, ROSS_XML_FILE_ID, 'Unable to resolve the jet port code of ' || JET_PORT || ' for resource ' || RES_NAME, 'STRING' " )
		   .append(" ,(SELECT ID from ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_JETPORT_CODE' ) " ) 
		   .append(" FROM ISW_ROSS_XML_FILE_DATA ")
		   .append(" WHERE JET_PORT IS NOT NULL " )
		   .append(" AND ( select count(id) from iswl_jet_port where code = substr(jet_port,regexp_instr(jet_port,'[(]') + 1,3)) = 0 " )
		   .append(" AND ID NOT IN (select ross_xml_file_data_id from isw_ross_imp_proc_data_error where ross_res_error_id = (select id from iswl_ross_res_error where code = 'UNKNOWN_RES_JETPORT_CODE' )) ")		
		   .append(" AND ROSS_XML_FILE_ID = " + rossXmlFileId + " ")		
		   .append(" AND DEMOB_ETD IS NULL ")
		   .append(" AND IS_ROSS_ASSIGNMENT = 1 " );		
		   //.append(" AND IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ");		
		
		return sql.toString();
	}	

}
