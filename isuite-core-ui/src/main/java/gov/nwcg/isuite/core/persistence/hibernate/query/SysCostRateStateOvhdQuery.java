package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.math.BigDecimal;

public class SysCostRateStateOvhdQuery {

	public static String getUpdateDirectKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
		.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
		.append("SET RATE_AMOUNT = " + rateAmount + " ")
		.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
		.append("AND KIND_ID IN ")
		.append("( ")
		.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
		.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
		.append("     AND IS_DIRECT = " + (isOracle ? 1 : true ) + " ")
		.append(") ")
		.append("");
		
		
		return sql.toString();
	}
	
	public static String getUpdateInDirectKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
		.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
		.append("SET RATE_AMOUNT = " + rateAmount + " ")
		.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
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
	 * SQL Query to update the rateAmount for all isw_syscost_rate_state_kind records
	 * having the sysCostRateStateId and having the kind reference with is_subordinate = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSubordinateStateKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			//.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
	/**
	 * SQL Query to update the rateAmount for all isw_syscost_rate_state_kind records
	 * having the sysCostRateId and having the kind reference with is_single = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSingleStateKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SINGLE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_syscost_rate_state_kind records
	 * having the sysCostRateStateId and having the kind reference with is_subordinate = false.
	 * 
	 * @param isOracle
	 * @param sysCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateNonSubordinateStateKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false ) + " ")
			//.append("     AND IS_DIRECT = " + (isOracle ? 0 : false ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	

	/**
	 * SQL Query to update the rateAmount for all isw_syscost_rate_state_kind records
	 * having the sysCostRateId and having the kind reference with is_direct = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateDirectStateKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
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
	 * SQL Query to update the rateAmount for all isw_syscost_rate_state_kind records
	 * having the sysCostRateId and having the kind reference with is_indirect = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateStateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateIndirectStateKindRates(Boolean isOracle, Long sysCostRateStateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_STATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_STATE_ID = " + sysCostRateStateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_INDIRECT = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
}
