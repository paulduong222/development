package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.math.BigDecimal;

public class IncidentCostRateOvhdQuery {

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_kind records
	 * having the incCostRateId and having the kind reference with is_direct = true.
	 * 
	 * @param isOracle
	 * @param incCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateDirectKindRates(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = " + incCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateDirectKindRatesGroup(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_group_id = " + groupId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateDirectKindRatesIncId(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID IN (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_id = " + incId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_kind records
	 * having the incCostRateId and having the kind reference with is_indirect = true.
	 * 
	 * @param isOracle
	 * @param incCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateInDirectKindRates(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = " + incCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateInDirectKindRatesGroup(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_group_id = " + groupId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateInDirectKindRatesIncId(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID IN (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_id = " + incId + ") ")
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
	 * SQL Query to update the rateAmount for all isw_inccost_rate_kind records
	 * having the incCostRateId and having the kind reference with is_subordinate = false.
	 * 
	 * @param isOracle
	 * @param incCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateNonSubordinateKindRates(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = " + incCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
	public static String getUpdateNonSubordinateKindRatesGroup(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_group_id = " + groupId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateNonSubordinateKindRatesIncId(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID IN (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_id = " + incId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_inccost_rate_kind records
	 * having the incCostRateId and having the kind reference with is_subordinate = true.
	 * 
	 * @param isOracle
	 * @param incCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSubordinateKindRates(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = " + incCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateSubordinateKindRatesGroup(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long groupId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID = (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_group_id = " + groupId + ") ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	public static String getUpdateSubordinateKindRatesIncId(Boolean isOracle, Long incCostRateId, BigDecimal rateAmount, Long incId) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_INCCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE INCCOST_RATE_ID IN (select id from isw_inccost_rate where cost_rate_category=(" +
					" select cost_rate_category from isw_inccost_rate where id = "+ incCostRateId + ") "+ 
					" and incident_id = " + incId + ") ")
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
