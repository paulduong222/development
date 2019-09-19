package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.math.BigDecimal;

public class IncidentCostRateStateOvhdQuery {

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_state_kind records
	 * having the incCostRateId and having the kind reference with is_single = true.
	 * 
	 * @param isOracle
	 * @param incCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSingleStateKindRates(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID = " + incCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SINGLE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateDirectStateKindRates(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID = " + incCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateDirectStateKindRatesGroup(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_group_id = " + groupId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateDirectStateKindRatesInc(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_id = " + incId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateIndirectStateKindRates(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID = " + incCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
	public static String getUpdateIndirectStateKindRatesGroup(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_group_id = " + groupId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateIndirectStateKindRatesInc(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_id = " + incId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_state_kind records
	 * having the incCostRateStateId and having the kind reference with is_subordinate = false.
	 * 
	 * @param isOracle
	 * @param incCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateNonSubordinateStateKindRates(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID = " + incCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
	public static String getUpdateNonSubordinateStateKindRatesGroup(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_group_id = " + groupId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateNonSubordinateStateKindRatesInc(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_id = " + incId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_state_kind records
	 * having the incCostRateStateId and having the kind reference with is_subordinate = true.
	 * 
	 * @param isOracle
	 * @param incCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSubordinateStateKindRates(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID = " + incCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateSubordinateStateKindRatesGroup(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_group_id = " + groupId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
	public static String getUpdateSubordinateStateKindRatesInc(Boolean isOracle, Long incCostRateStateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_STATE_ID IN ( " )
			.append("  select id from isw_inccost_rate_state " )
			.append("  where inccost_rate_id = (")
			.append("    select id from isw_inccost_rate ")
			.append("    where cost_rate_category='STATE_COOP_CUSTOM' " )
			.append("	 and incident_id = " + incId + " ")
			.append("  ) " )
			.append("  and agency_id = " + "(select agency_id from isw_inccost_rate_state where id = "+incCostRateStateId+" ) " )
			.append(") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

}
