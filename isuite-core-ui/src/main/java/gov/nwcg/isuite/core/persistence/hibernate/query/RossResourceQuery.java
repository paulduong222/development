package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.framework.util.LongUtility;

public class RossResourceQuery {

	/**
	 * @param rossXmlFileId
	 * @param isOracle
	 * @return
	 */
	public static String getValidRossXmlFileResources(Long rossXmlFileId, Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("");
		sql.append("SELECT ID as rossXmlFileDataId ")
           .append(", RES_ID as rossResId ")
           .append(", REQ_ID as rossResReqId ")
           .append(", RES_NAME as resourceName ")
           .append(", ASSIGNMENT_NAME as assignmentName ")
           .append(", LAST_NAME as lastName ")
           .append(", FIRST_NAME as firstName ")
           .append(", MIDDLE_NAME as middleName ")
           .append(", REQ_CATALOG_NAME as requestCatalogName ")
           .append(", REQ_CATEGORY_NAME as requestCategoryName ")
           .append(",REQ_NUMBER as requestNumber ")
           .append(",MOB_ETD as mobEtd ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' )) IS NULL ")
           .append("   THEN ")
           .append("     RXFD.RES_PROV_AGENCY_ABBREV ")
           .append("   ELSE ")
           .append("    (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' ) ) ")
           .append("   END ) as agencyCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' )) IS NULL ")
           .append("   THEN " )
           .append("    (SELECT ID FROM ISWL_AGENCY WHERE AGENCY_CODE = RXFD.RES_PROV_AGENCY_ABBREV) ")
           .append("   ELSE ")
           .append("    (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' ) ) ")
           .append("   END ) as agencyId ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' )) IS NULL ")
           .append("   THEN " )
           .append("    RXFD.FILLED_CATALOG_ITEM_CODE ")
           .append("   ELSE ")
           .append("    (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' ) ) ")
           .append("   END ) as itemCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' )) IS NULL ")
           .append("   THEN ")
           .append("    (SELECT ID FROM ISWL_KIND WHERE (CODE = RXFD.FILLED_CATALOG_ITEM_CODE) OR (UPPER(DESCRIPTION) = UPPER(RXFD.FILLED_CATALOG_ITEM_NAME)) ) ")
           .append("   ELSE ")
           .append("    (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' ) )  ")
           .append("   END ) as itemId ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' )) IS NULL ")
           .append("    THEN ")
           .append("      RXFD.RES_PROV_UNIT_CODE ")
           .append("    ELSE ")
           .append("      (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' ) ) ")
           .append("    END ) as unitCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' )) IS NULL ")
           .append("    THEN ")
           .append("     (SELECT ID FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false) + " AND UNIT_CODE = RXFD.RES_PROV_UNIT_CODE) ")
           .append("    ELSE ")
           .append("      (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' ) ) ") 
           .append("    END ) as unitId ")
           .append("FROM ISW_ROSS_XML_FILE_DATA RXFD ")
           .append("WHERE RXFD.ROSS_XML_FILE_ID = "+rossXmlFileId + " ")
           .append("AND RXFD.IS_ROSS_ASSIGNMENT = 1 ")
           //.append("AND RXFD.IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ")
           .append("AND RXFD.ID NOT IN (SELECT ROSS_XML_FILE_DATA_ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE EXCLUDE_FROM_IMPORT = 1 ) ")
           .append("AND RXFD.RES_ID NOT IN (SELECT RES_ID FROM ISW_ROSS_INC_DATA_BLACKLIST WHERE ROSS_INC_BLACKLIST_ID NOT IN (SELECT ID FROM ISW_ROSS_INC_BLACKLIST WHERE " + (isOracle ? "ROSS_INC_ID" : "CAST (ROSS_INC_ID as varchar)") + " IN (SELECT ROSS_INC_ID FROM ISW_ROSS_XML_FILE WHERE ID = " + rossXmlFileId + " )  )) ")
           .append("AND RXFD.DEMOB_ETD IS NULL ")
           .append("");
		
		if(LongUtility.hasValue(incidentId)){
			sql.append("AND REQ_ID NOT IN (")
			   .append("SELECT ROSS_RES_REQ_ID FROM ISW_INCIDENT_RESOURCE WHERE INCIDENT_ID = " + incidentId + " AND ROSS_RES_REQ_ID > 0 ")
			   .append(") ");
		}
		
		return sql.toString();
	}

	public static String getRossXmlFileResourcesByResId(Long rossXmlFileId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("");
		sql.append("SELECT ID as rossXmlFileDataId ")
           .append(", RES_ID as rossResId ")
           .append(", REQ_ID as rossResReqId ")
           .append(", RES_NAME as resourceName ")
           .append(", LAST_NAME as lastName ")
           .append(", FIRST_NAME as firstName ")
           .append(", MIDDLE_NAME as middleName ")
           .append(", REQ_CATALOG_NAME as requestCatalogName ")
           .append(", REQ_CATEGORY_NAME as requestCategoryName ")
           .append(",REQ_NUMBER as requestNumber ")
           .append(",MOB_ETD as mobEtd ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' )) IS NULL ")
           .append("   THEN ")
           .append("     RXFD.RES_PROV_AGENCY_ABBREV ")
           .append("   ELSE ")
           .append("    (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' ) ) ")
           .append("   END ) as agencyCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' )) IS NULL ")
           .append("   THEN " )
           .append("    (SELECT ID FROM ISWL_AGENCY WHERE AGENCY_CODE = RXFD.RES_PROV_AGENCY_ABBREV) ")
           .append("   ELSE ")
           .append("    (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_AGENCY_CODE ' ) ) ")
           .append("   END ) as agencyId ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' )) IS NULL ")
           .append("   THEN " )
           .append("    RXFD.FILLED_CATALOG_ITEM_CODE ")
           .append("   ELSE ")
           .append("    (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' ) ) ")
           .append("   END ) as itemCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' )) IS NULL ")
           .append("   THEN ")
           .append("    (SELECT ID FROM ISWL_KIND WHERE (CODE = RXFD.FILLED_CATALOG_ITEM_CODE) OR (UPPER(DESCRIPTION) = UPPER(RXFD.FILLED_CATALOG_ITEM_NAME)) ) ")
           .append("   ELSE ")
           .append("    (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_ITEM_CODE ' ) )  ")
           .append("   END ) as itemId ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' )) IS NULL ")
           .append("    THEN ")
           .append("      RXFD.RES_PROV_UNIT_CODE ")
           .append("    ELSE ")
           .append("      (SELECT NEW_VALUE FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' ) ) ")
           .append("    END ) as unitCode ")
           .append(", (CASE WHEN (SELECT ID FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID =RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' )) IS NULL ")
           .append("    THEN ")
           .append("     (SELECT ID FROM ISW_ORGANIZATION WHERE IS_DISPATCH_CENTER = " + (isOracle ? 0 : false) + " AND UNIT_CODE = RXFD.RES_PROV_UNIT_CODE) ")
           .append("    ELSE ")
           .append("      (SELECT " + (isOracle ? "TO_NUMBER(NEW_VALUE)" : "CAST (NEW_VALUE as BIGINT)") + " FROM ISW_ROSS_IMP_PROC_RES_ERROR WHERE ROSS_XML_FILE_DATA_ID = RXFD.ID AND ROSS_RES_ERROR_ID=(SELECT ID FROM ISWL_ROSS_RES_ERROR WHERE CODE = 'UNKNOWN_RES_UNIT_CODE ' ) ) ") 
           .append("    END ) as unitId ")
           .append("FROM ISW_ROSS_XML_FILE_DATA RXFD ")
           .append("WHERE RXFD.ROSS_XML_FILE_ID = "+rossXmlFileId + " ")
           .append("AND RXFD.IS_ROSS_ASSIGNMENT = 1 ")
           //.append("AND RXFD.IS_ROSS_ASSIGNMENT = " + (isOracle ? 1 : Boolean.TRUE) + " ")
           .append("AND RXFD.RES_ID in ( :ids ) " )
           .append("AND RXFD.DEMOB_ETD IS NULL ")
           .append("");
		
		return sql.toString();
	}

	public static String getEISuiteResources(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
			.append("select r.ROSS_RES_ID as rossResId ")
			.append(",r.id as resourceId ")
			.append(",r.ross_group_assignment as rossGroupAssignment ")
			.append(",ir.id as incidentResourceId ")
			.append(",ir.ross_res_req_id as rossResReqId ")
			.append(",wp.id as workPeriodId ")
			.append(",a.id as assignmentId ")
			.append(",K.CODE as assignmentKindCode ")
			.append(",K.DESCRIPTION as assignmentKindDesc ")
			.append(",A.REQUEST_NUMBER as requestNumber ")
			.append(",AG.AGENCY_CODE as resourceAgencyCode ")
			.append(",AG.AGENCY_NAME as resourceAgencyName ")
			.append(",CASE WHEN r.is_person = "+ (isOracle ? 0 : false) + "  then r.resource_name ")
			.append("ELSE ")
			.append("r.last_name || ', ' || r.first_name ")
			.append("END as resourceName ")
			.append("from isw_incident_resource ir ")
			.append(",isw_resource r ")
			.append(" left join iswl_agency ag on ag.id = r.agency_id ")
			.append(",isw_work_period wp ")
			.append(",isw_work_period_assignment wpa ")
			.append(",isw_assignment a ")
			.append(",iswl_kind k ")
			.append("where r.id = ir.resource_id ")
			.append("and wp.incident_resource_id = ir.id ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ")
			.append("and k.id = a.kind_id ")
			.append("and A.END_DATE is null ")
			.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
			.append("and ir.incident_id = " + incidentId + " ");
		
		return sql.toString();
	}
	
	public static String getEISuiteResources2(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
			.append("select r.ROSS_RES_ID as rossResId ")
			.append(",r.id as resourceId ")
			.append(",r.ross_group_assignment as rossGroupAssignment ")
			.append(",ir.id as incidentResourceId ")
			.append(",ir.ross_res_req_id as rossResReqId ")
			.append(",wp.id as workPeriodId ")
			.append(",a.id as assignmentId ")
			.append(",K.CODE as assignmentKindCode ")
			.append(",K.DESCRIPTION as assignmentKindDesc ")
			.append(",A.REQUEST_NUMBER as requestNumber ")
			.append(",sortRequestNumber(A.REQUEST_NUMBER) as sortRequestNumberField ")
			.append(",AG.AGENCY_CODE as resourceAgencyCode ")
			.append(",AG.AGENCY_NAME as resourceAgencyName ")
			.append(",CASE WHEN r.is_person = "+ (isOracle ? 0 : false) + "  then r.resource_name ")
			.append("ELSE ")
			.append("r.last_name || ', ' || r.first_name ")
			.append("END as resourceName ")
			.append(",r.last_name as lastName ")
			.append(",r.first_name as firstName ");
			if(isOracle){
				sql.append(",case when r.is_person = 1 then 1 else 0 end as isPerson " );
			}else{
				sql.append(",case when r.is_person = true then true else false end as isPerson ");
			}
			sql.append("from isw_incident_resource ir ")
			.append(",isw_resource r ")
			.append(" left join iswl_agency ag on ag.id = r.agency_id ")
			.append(",isw_work_period wp ")
			.append(",isw_work_period_assignment wpa ")
			.append(",isw_assignment a ")
			.append(",iswl_kind k ")
			.append("where r.id = ir.resource_id ")
			.append("and wp.incident_resource_id = ir.id ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ")
			.append("and k.id = a.kind_id ")
			.append("and A.END_DATE is null ")
			.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
			.append("and ir.incident_id = " + incidentId + " ")
			.append("and ir.ross_res_req_id is null ")
			.append("order by sortRequestNumberField ");

		return sql.toString();
	}

	public static String getEISuiteResources3(Long incidentId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer()
			.append("select r.ROSS_RES_ID as rossResId ")
			.append(",r.id as resourceId ")
			.append(",r.ross_group_assignment as rossGroupAssignment ")
			.append(",ir.id as incidentResourceId ")
			.append(",ir.ross_res_req_id as rossResReqId ")
			.append(",wp.id as workPeriodId ")
			.append(",a.id as assignmentId ")
			.append(",K.CODE as assignmentKindCode ")
			.append(",K.DESCRIPTION as assignmentKindDesc ")
			.append(",A.REQUEST_NUMBER as requestNumber ")
			.append(",sortRequestNumber(A.REQUEST_NUMBER) as sortRequestNumberField ")
			.append(",AG.AGENCY_CODE as resourceAgencyCode ")
			.append(",AG.AGENCY_NAME as resourceAgencyName ")
			.append(",CASE WHEN r.is_person = "+ (isOracle ? 0 : false) + "  then r.resource_name ")
			.append("ELSE ")
			.append("r.last_name || ', ' || r.first_name ")
			.append("END as resourceName ")
			.append(",r.last_name as lastName ")
			.append(",r.first_name as firstName ");
			if(isOracle){
				sql.append(",case when r.is_person = 1 then 1 else 0 end as isPerson " );
			}else{
				sql.append(",case when r.is_person = true then true else false end as isPerson ");
			}
			sql.append("from isw_incident_resource ir ")
			.append(",isw_resource r ")
			.append(" left join iswl_agency ag on ag.id = r.agency_id ")
			.append(",isw_work_period wp ")
			.append(",isw_work_period_assignment wpa ")
			.append(",isw_assignment a ")
			.append(",iswl_kind k ")
			.append("where r.id = ir.resource_id ")
			.append("and wp.incident_resource_id = ir.id ")
			.append("and wpa.work_period_id = wp.id ")
			.append("and a.id = wpa.assignment_id ")
			.append("and k.id = a.kind_id ")
			.append("and A.END_DATE is null ")
			.append("and R.IS_ENABLED = " + (isOracle ? 1 : true) + " ")
			.append("and ir.incident_id = " + incidentId + " ")
			.append("order by sortRequestNumberField ");

		return sql.toString();
	}
	
	public static String getUpdateStatuses(Boolean isOracle, String rossIncId, String status) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("UPDATE ISW_ROSS_XML_FILE_DATA ")
			.append("SET IMPORT_STATUS = '" + status + "' ")
			.append("WHERE ID IN ( :ids ) " );
		
		return sql.toString();
	}

	public static String getUpdateResourceImportStatus(Boolean isOracle, String rossIncId, String status, Long rossResReqId) {
		StringBuffer sql = new StringBuffer()
			.append("")
			.append("UPDATE ISW_ROSS_XML_FILE_DATA ")
			.append("SET IMPORT_STATUS = '" + status + "' ")
			.append("WHERE req_id = " + rossResReqId + " ");
		
		return sql.toString();
	}
	
}
