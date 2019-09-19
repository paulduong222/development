package gov.nwcg.isuite.core.persistence.hibernate.query;

public class ResourceInventoryQuery {
	
	public static final String GET_RESOURCES_INVENTORY_GRID_QUERY(Long dispatchCenterId, Long userId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT R.ID, ")
		.append("R.IS_PERSON AS person, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
		.append("K.DESCRIPTION AS itemName, ")
		.append("K.CODE as itemCode, ")
		.append("A.AGENCY_CODE AS agency, ")
		.append("O.UNIT_CODE As unitId, ")
		.append("R.PHONE as cellPhoneNumber, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.ROSS_LAST_NAME || ', ' || R.ROSS_FIRST_NAME ELSE R.ROSS_RESOURCE_NAME END AS rossResourceName, ")
		.append("R.LAST_ROSS_UPDATED_DATE AS rossUpdatedDate, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN 'O' ELSE RC.CODE END AS requestCategoryCode ")		
		.append("FROM ISW_RESOURCE R ")
		.append("LEFT JOIN ISWL_AGENCY A ON R.AGENCY_ID = A.ID ")
		.append("JOIN ISW_ORGANIZATION O ON R.ORGANIZATION_ID = O.ID ")
		.append("LEFT JOIN ISW_RESOURCE_KIND RK ON R.ID = RK.RESOURCE_ID ")
		.append("LEFT JOIN ISWL_KIND K ON RK.KIND_ID = K.ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RC ON K.REQUEST_CATEGORY_ID = RC.ID ")
		.append("WHERE ")
		.append(" R.ID NOT IN ")
		.append(" (SELECT RE.RESOURCE_ID FROM ISW_USER_RESINVVIEW_EXCLUDE RE WHERE RE.USER_ID = " + userId + ") ")
		.append(" AND R.IS_PERMANENT = " + (isOracle ? 1 : Boolean.TRUE))
		.append(" AND R.DELETED_DATE IS NULL ")
		.append("AND R.PRIMARY_DISP_CTR_ID = " + dispatchCenterId)
		.append(" ORDER BY resourceName");
		
		return sql.toString();
	}
	
//	public static final String GET_RESOURCES_INVENTORY_GRID="GET_RESOURCES_INVENTORY_GRID";
//	public static final String GET_RESOURCES_INVENTORY_GRID_QUERY(Long dispatchCenterId, Boolean isOracle) {
//		StringBuffer sql = new StringBuffer();
//		
//		sql.append("SELECT R.ID, ")
//		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
//				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
//		.append("CASE WHEN R.ORGANIZATION_ID IN (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID = " +
//				 dispatchCenterId + ") THEN " + (isOracle ? 0 : Boolean.FALSE) + " ELSE " + (isOracle ? 1 : Boolean.TRUE) 
//				 + " END AS nonStandard, ");
//		if (isOracle)  {
//			sql.append("(SELECT WM_CONCAT(K.DESCRIPTION) FROM ISWL_KIND K, " + 
//						"ISW_RESOURCE_KIND RK WHERE K.ID = RK.KIND_ID AND RK.RESOURCE_ID = R.ID) AS itemName, ");
//		} else {
//			sql.append("(SELECT ARRAY_TO_STRING(ARRAY(SELECT K.DESCRIPTION FROM ISWL_KIND K, " +
//						"ISW_RESOURCE_KIND RK WHERE K.ID = RK.KIND_ID AND RK.RESOURCE_ID = R.ID), ','::text) AS ARRAY_TO_STRING) AS itemName, ");
//		}
//		sql.append("A.AGENCY_CODE AS agency, ")
//		.append("O.UNIT_CODE As unitId ")
//		.append("FROM ISW_RESOURCE R ")
//		.append("LEFT JOIN ISWL_AGENCY A ON R.AGENCY_ID = A.ID ")
//		.append("JOIN ISW_ORGANIZATION O ON R.ORGANIZATION_ID = O.ID ")
//		.append("WHERE ")
//		.append("R.IS_PERMANENT = " + (isOracle ? 1 : Boolean.TRUE))
//		.append(" AND R.DELETED_DATE IS NULL ")
//		.append("AND (R.ORGANIZATION_ID IN (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID = " +
//				dispatchCenterId + ")")
//		.append("OR R.ID IN (SELECT RESOURCE_ID FROM ISW_ORGANIZATION_NONSTD_RES WHERE ORGANIZATION_ID = " + 
//				dispatchCenterId + "))")
//		.append("ORDER BY resourceName");
//		
//		return sql.toString();
//	}
	
	public static final String GET_NON_STD_RESOURCES_GRID_QUERY(Long dispatchCenterId, Long nonStdId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT R.ID, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
		.append("CASE WHEN R.ORGANIZATION_ID IN (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID = " +
				 dispatchCenterId + ") THEN " + (isOracle ? 0 : Boolean.FALSE) + " ELSE " + (isOracle ? 1 : Boolean.FALSE) 
				 + " END AS nonStandard, ");
		if (isOracle)  {
			sql.append("(SELECT WM_CONCAT(K.DESCRIPTION) FROM ISWL_KIND K, " + 
						"ISW_RESOURCE_KIND RK WHERE K.ID = RK.KIND_ID AND RK.RESOURCE_ID = R.ID) AS itemName, ");
		} else {
			sql.append("(SELECT ARRAY_TO_STRING(ARRAY(SELECT K.DESCRIPTION FROM ISWL_KIND K, " +
						"ISW_RESOURCE_KIND RK WHERE K.ID = RK.KIND_ID AND RK.RESOURCE_ID = R.ID), ','::text) AS ARRAY_TO_STRING) AS itemName, ");
		}
		sql.append("A.AGENCY_CODE AS agency, ")
		.append("O.UNIT_CODE As unitId ")
		.append("FROM ISW_RESOURCE R ")
		.append("LEFT JOIN ISWL_AGENCY A ON R.AGENCY_ID = A.ID ")
		.append("JOIN ISW_ORGANIZATION O ON R.ORGANIZATION_ID = O.ID ")
		.append("WHERE ")
		.append("R.IS_PERMANENT = " + (isOracle ? 1 : Boolean.TRUE))
		.append(" AND R.DELETED_DATE IS NULL ")
		.append("AND R.ORGANIZATION_ID IN (SELECT ORGANIZATION_ID FROM ISW_ORGANIZATION_PDC WHERE PDC_ID = " +
				dispatchCenterId + ")")
		.append("AND NOT R.ID IN (SELECT RESOURCE_ID FROM ISW_ORGANIZATION_NONSTD_RES WHERE ORGANIZATION_ID = " + 
				nonStdId + ")")
		.append("ORDER BY resourceName");
		
		return sql.toString();
	}
	
	public static final String REMOVE_NON_STANDARD_RESOURCE_QUERY = 
		
		"DELETE FROM ISW_ORGANIZATION_NONSTD_RES " + 
		"WHERE ORGANIZATION_ID = :organizationId " + 
		" AND RESOURCE_ID = :resourceId ";
	
	public static String REMOVE_EXCLUDED_RESOURCES_QUERY = 
		"DELETE FROM ISW_USER_RESINVVIEW_EXCLUDE WHERE USER_ID = :userId AND " +
		"RESOURCE_ID NOT IN ( :resourceIds ) ";
	
	public static String REMOVE_ALL_EXCLUDED_RESOURCES_QUERY = 
		"DELETE FROM ISW_USER_RESINVVIEW_EXCLUDE WHERE USER_ID = :userId ";
	
	public static String createExcludedResourceQuery(Long userId, Long resourceId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ISW_USER_RESINVVIEW_EXCLUDE (ID, USER_ID, RESOURCE_ID) ")
		   .append("VALUES (" + (isOracle ? "SEQ_USERRESINVVIEWEX.nextVal" : "nextVal('SEQ_USERRESINVVIEWEX')") + 
				   ", " + userId + ", "+ resourceId + ") ");
		
		return sql.toString();
	}
	
	public static String getUserResourceExcludedQuery(Long userId, Boolean isOracle) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT R.ID, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
		.append("K.DESCRIPTION AS itemName ")
		.append("FROM ISW_RESOURCE R ")
		.append("JOIN ISW_USER_RESINVVIEW_EXCLUDE RE ON RE.RESOURCE_ID = R.ID ")
		.append("LEFT JOIN ISW_RESOURCE_KIND RK ON R.ID = RK.RESOURCE_ID ")
		.append("LEFT JOIN ISWL_KIND K ON RK.KIND_ID = K.ID ")
		.append("WHERE ")
		.append("R.IS_PERMANENT = " + (isOracle ? 1 : Boolean.TRUE))
		.append(" AND R.DELETED_DATE IS NULL ")
		.append(" ORDER BY resourceName");
		
		return sql.toString();
	}
	
}
