package gov.nwcg.isuite.core.persistence.hibernate.query;

import gov.nwcg.isuite.core.filter.CostGroupResourceFilter;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Date;

public class CostGroupQuery {
	
	public static String getCostGroupAvailableOtherResourceGridQuery(Long costGroupId) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT RO.ID, ")
		.append("RO.REQUEST_NUMBER AS requestNumber, ")
		//.append("RO.RESOURCE_NAME AS resourceName, ")
		.append("RO.COST_DESCRIPTION AS resourceName, ")
		.append("K.CODE AS itemCode, ")
		.append("A.AGENCY_CODE AS agencyCode ")
		.append("FROM ISW_RESOURCE_OTHER RO ")
		.append("JOIN ISW_INCIDENT_RESOURCE_OTHER IRO ON RO.ID = IRO.RESOURCE_OTHER_ID ")
		.append("LEFT JOIN ISWL_KIND K ON RO.KIND_ID = K.ID ")
		.append("LEFT JOIN ISWL_AGENCY A ON RO.AGENCY_ID = A.ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RQ ON K.REQUEST_CATEGORY_ID = RQ.ID ")
		.append("WHERE RO.ACTUAL_RELEASE_DATE IS NULL ")
		.append("AND NOT RO.ID IN (SELECT CGRO.RESOURCE_OTHER_ID FROM ISW_COST_GROUP_RESOURCE_OTHER CGRO WHERE CGRO.COST_GROUP_ID = " + costGroupId + ") ")
		.append("AND IRO.INCIDENT_ID IN (SELECT CG.INCIDENT_ID FROM ISW_COST_GROUP CG WHERE CG.ID = " + costGroupId + ") ");
		
		//b.append(getCostGroupResourceOtherFilter(costGroupResourceFilter));
			
		//add order by
		b.append("ORDER BY SORTREQUESTNUMBER(RO.REQUEST_NUMBER)");
		
		return b.toString();
	}
	
	public static String getCostGroupSelectedOtherResourceGridQuery(Long costGroupId) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT RO.ID, ")
		.append("RO.REQUEST_NUMBER AS requestNumber, ")
		//.append("RO.RESOURCE_NAME AS resourceName, ")
		.append("RO.COST_DESCRIPTION AS resourceName, ")
		.append("K.CODE AS itemCode, ")
		.append("A.AGENCY_CODE AS agencyCode ")
		.append("FROM ISW_RESOURCE_OTHER RO ")
		.append("JOIN ISW_COST_GROUP_RESOURCE_OTHER CGRO ON RO.ID = CGRO.RESOURCE_OTHER_ID ")
		.append("LEFT JOIN ISWL_KIND K ON RO.KIND_ID = K.ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RQ ON K.REQUEST_CATEGORY_ID = RQ.ID ")
		.append("LEFT JOIN ISWL_AGENCY A ON RO.AGENCY_ID = A.ID ")
		.append("WHERE RO.ACTUAL_RELEASE_DATE IS NULL ")
		.append("AND CGRO.COST_GROUP_ID = " + costGroupId + " ");
		
		//b.append(getCostGroupResourceOtherFilter(costGroupResourceFilter));
			
		//add order by
		b.append("ORDER BY SORTREQUESTNUMBER(RO.REQUEST_NUMBER)");
		
		return b.toString();
	}
	
	public static String getCostGroupResourceOtherFilter(CostGroupResourceFilter filter) {
		StringBuffer b = new StringBuffer();
		
		if(null != filter) {
			if(StringUtility.hasValue(filter.getRequestNumber())) {
				b.append("AND RO.REQUEST_NUMBER LIKE '" + filter.getRequestNumber().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getResourceName())) {
				//b.append("AND RO.RESOURCE_NAME LIKE '" + filter.getResourceName().toUpperCase() + "%' ");
				b.append("AND RO.COST_DESCRIPTION LIKE '" + filter.getResourceName().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getItemCode())) {
				b.append("AND K.CODE LIKE '" + filter.getItemCode().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				b.append("AND A.AGENCY_CODE LIKE '" + filter.getAgency().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getUnitId())) {
				b.append("AND O.UNIT_CODE LIKE '" + filter.getUnitId().toUpperCase() + "%' ");
			}
			
			if(null != filter.getCheckboxFilter()) {
				if(filter.getCheckboxFilter().getReqCatList(filter.getCheckboxFilter()).size() > 0) {
					b.append("AND RQ.CODE IN ( :requestCategories ) " );	
				}
			}
		}
		
		return b.toString();
	}
	
	public static String getCostGroupAvailableResourceGridQuery(Long costGroupId, Boolean isOracle) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT R.ID, ")
		.append("A.REQUEST_NUMBER AS requestNumber, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
		.append("K.CODE AS itemCode, ")
		.append("AG.AGENCY_CODE AS agencyCode, ")
		.append("O.UNIT_CODE AS unitId ")
		.append("FROM ISW_RESOURCE R ")
		.append("JOIN ISW_INCIDENT_RESOURCE IR ON R.ID = IR.RESOURCE_ID ")
		.append("JOIN ISW_WORK_PERIOD WP ON IR.ID = WP.INCIDENT_RESOURCE_ID ")
		.append("JOIN ISW_WORK_PERIOD_ASSIGNMENT WPA ON WP.ID = WPA.WORK_PERIOD_ID ")
		.append("JOIN ISW_ASSIGNMENT A ON WPA.ASSIGNMENT_ID = A.ID ")
		.append("JOIN ISWL_KIND K ON A.KIND_ID = K.ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RQ ON K.REQUEST_CATEGORY_ID = RQ.ID ")
		.append("LEFT JOIN ISWL_AGENCY AG ON R.AGENCY_ID = AG.ID ")
		.append("JOIN ISW_ORGANIZATION O ON R.ORGANIZATION_ID = O.ID ")
		.append("WHERE WP.DM_RELEASE_DATE IS NULL ")
		//.append("AND WP.DM_ESTIMATED_ARRIVAL_DATE IS NULL ")
		.append("AND WP.DM_TENTATIVE_ARRIVAL_DATE IS NULL ")
		.append("AND A.ASSIGNMENT_STATUS != 'D' ")
		.append("AND NOT R.ID IN (SELECT CGR.RESOURCE_ID FROM ISW_COST_GROUP_RESOURCE CGR WHERE CGR.COST_GROUP_ID = " + costGroupId + ") ")
		.append("AND IR.INCIDENT_ID IN (SELECT CG.INCIDENT_ID FROM ISW_COST_GROUP CG WHERE CG.ID = " + costGroupId + ") ");
		
		//b.append(getCostGroupResourceFilter(costGroupResourceFilter, isOracle));
			
		//add order by
		b.append("ORDER BY SORTREQUESTNUMBER(A.REQUEST_NUMBER)");
		
		return b.toString();
	}
	
	public static String getCostGroupSelectedResourceGridQuery(Long costGroupId, Boolean isOracle) {
		StringBuffer b = new StringBuffer();
		
		b.append("SELECT R.ID, ")
		.append("A.REQUEST_NUMBER AS requestNumber, ")
		.append("CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
				" THEN R.LAST_NAME || ', ' || R.FIRST_NAME ELSE R.RESOURCE_NAME END AS resourceName, ")
		.append("K.CODE AS itemCode, ")
		.append("AG.AGENCY_CODE AS agencyCode, ")
		.append("O.UNIT_CODE AS unitId ")
		.append("FROM ISW_RESOURCE R ")
		.append("JOIN ISW_INCIDENT_RESOURCE IR ON R.ID = IR.RESOURCE_ID ")
		.append("JOIN ISW_WORK_PERIOD WP ON IR.ID = WP.INCIDENT_RESOURCE_ID ")
		.append("JOIN ISW_WORK_PERIOD_ASSIGNMENT WPA ON WP.ID = WPA.WORK_PERIOD_ID ")
		.append("JOIN ISW_ASSIGNMENT A ON WPA.ASSIGNMENT_ID = A.ID ")
		.append("JOIN ISWL_KIND K ON A.KIND_ID = K.ID ")
		.append("LEFT JOIN ISWL_REQUEST_CATEGORY RQ ON K.REQUEST_CATEGORY_ID = RQ.ID ")
		.append("LEFT JOIN ISWL_AGENCY AG ON R.AGENCY_ID = AG.ID ")
		.append("JOIN ISW_ORGANIZATION O ON R.ORGANIZATION_ID = O.ID ")
		.append("JOIN ISW_COST_GROUP_RESOURCE CGR ON R.ID = CGR.RESOURCE_ID ")
		.append("WHERE WP.DM_RELEASE_DATE IS NULL ")
		.append("AND WP.DM_ESTIMATED_ARRIVAL_DATE IS NULL ")
		.append("AND CGR.COST_GROUP_ID = " + costGroupId + " ");
		
		//b.append(getCostGroupResourceFilter(costGroupResourceFilter, isOracle));
			
		//add order by
		b.append("ORDER BY SORTREQUESTNUMBER(A.REQUEST_NUMBER)");
		
		return b.toString();
	}
	
	public static String getCostGroupResourceFilter(CostGroupResourceFilter filter, Boolean isOracle) {
		StringBuffer b = new StringBuffer();
		
		if(null != filter) {
			if(StringUtility.hasValue(filter.getRequestNumber())) {
				b.append("AND A.REQUEST_NUMBER LIKE '" + filter.getRequestNumber().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getResourceName())) {
				b.append("AND RO.RESOURCE_NAME LIKE '" + filter.getResourceName().toUpperCase() + "%' ");
				b.append("AND CASE WHEN R.IS_PERSON = " + (isOracle ? 1 : Boolean.TRUE) + 
						" THEN R.LAST_NAME || ', ' || R.FIRST_NAME LIKE '" + 
						filter.getResourceName().toUpperCase() + "%' ELSE R.RESOURCE_NAME LIKE '" + 
						filter.getResourceName().toUpperCase() + "%' END ");
			}
			if(StringUtility.hasValue(filter.getItemCode())) {
				b.append("AND K.CODE LIKE '" + filter.getItemCode().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getAgency())) {
				b.append("AND AG.AGENCY_CODE LIKE '" + filter.getAgency().toUpperCase() + "%' ");
			}
			if(StringUtility.hasValue(filter.getUnitId())) {
				b.append("AND O.UNIT_CODE LIKE '" + filter.getUnitId().toUpperCase() + "%' ");
			}
			
			if(null != filter.getCheckboxFilter()) {
				if(filter.getCheckboxFilter().getReqCatList(filter.getCheckboxFilter()).size() > 0) {
					b.append("AND RQ.CODE IN ( :requestCategories ) ");	
				}
				if(filter.getCheckboxFilter().isExcludeFilled() || filter.getCheckboxFilter().isExcludeDemob()) {
					b.append("AND NOT A.ASSIGNMENT_STATUS IN ( :assignmentStatuses ) ");
				}
			}
		}
		
		return b.toString();
	}
	
	public static String getActiveAssignedResourcesCountQuery(Long costGroupId) {
		String sql = 
			"SELECT COUNT(*) FROM " + 
			"(SELECT R.ID FROM ISW_RESOURCE R " +
			"JOIN ISW_INCIDENT_RESOURCE IR ON IR.RESOURCE_ID = R.ID " +
			"JOIN ISW_WORK_PERIOD WP ON IR.ID = WP.INCIDENT_RESOURCE_ID " +
			"JOIN ISW_COST_GROUP_RESOURCE CGR ON R.ID = CGR.RESOURCE_ID " +
			"JOIN ISW_COST_GROUP CG ON CGR.COST_GROUP_ID = CG.ID " +
			"WHERE WP.DM_RELEASE_DATE IS NULL " + 
			"AND WP.DM_ESTIMATED_ARRIVAL_DATE IS NULL " + 
			"AND CG.ID = " + costGroupId + ") tb1 ";
		
		return sql;
	}

	public static String getDailyCostAssignedCountQuery(Long costGroupId) {
		String sql = 
			"SELECT COUNT(*) FROM " + 
			"ISW_INC_RES_DAILY_COST " +
			"WHERE COST_GROUP_ID = " + costGroupId + " ";
		
		return sql;
	}

	public static String getActiveAssignedOtherResourcesCountQuery(Long costGroupId) {
		String sql = 
			"SELECT COUNT(*) FROM " + 
			"(SELECT RO.ID FROM ISW_RESOURCE_OTHER RO " +
			"JOIN ISW_COST_GROUP_RESOURCE_OTHER CGRO ON RO.ID = CGRO.RESOURCE_OTHER_ID " +
			"JOIN ISW_COST_GROUP CG ON CGRO.COST_GROUP_ID = CG.ID " +
			"WHERE RO.ACTUAL_RELEASE_DATE IS NULL " +
			"AND CG.ID = " + costGroupId + ") tbl1 ";
			
		return sql;
	}
	
	public static String REMOVE_EXCLUDED_RESOURCES_QUERY =
		"DELETE FROM ISW_COST_GROUP_RESOURCE WHERE COST_GROUP_ID = :costGroupId AND RESOURCE_ID NOT IN ( :resourceIds )";
	
	public static String REMOVE_EXCLUDED_OTHER_RESOURCES_QUERY = 
		"DELETE FROM ISW_COST_GROUP_RESOURCE_OTHER WHERE COST_GROUP_ID = :costGroupId AND RESOURCE_OTHER_ID NOT IN ( :resourceOtherIds )";
	
	public static String REMOVE_RESOURCES_FROM_PRIOR_COST_GROUPS_QUERY = 
		"DELETE FROM ISW_COST_GROUP_RESOURCE WHERE COST_GROUP_ID != :costGroupId AND RESOURCE_ID IN ( :resourceIds )";
	
	public static String REMOVE_OTHER_RESOURCES_FROM_PRIOR_COST_GROUPS_QUERY = 
		"DELETE FROM ISW_COST_GROUP_RESOURCE_OTHER WHERE COST_GROUP_ID != :costGroupId AND RESOURCE_OTHER_ID IN ( :resourceOtherIds )";

	public static String getCostGroupDayShareDateCountQuery(Long costGroupId, Date dt){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT COUNT(ID) FROM ISW_COST_GROUP_AG_DS ")
			.append("WHERE COST_GROUP_ID = " + costGroupId + " ")
			.append("AND ")
		    .append("   to_date(to_char(agency_share_date, 'MM/DD/YYYY'),'MM/DD/YYYY') " )
		    .append("   = ")
		    .append("   to_date('"+DateUtil.toDateString(dt, DateUtil.MM_SLASH_DD_SLASH_YYYY)+"','MM/DD/YYYY') "); 
		
		return sql.toString();
	}
}
