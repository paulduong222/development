package gov.nwcg.isuite.core.persistence.hibernate.query;

import java.math.BigDecimal;

public class SysCostRateOvhdQuery {

	/**
	 * SQL Query to update the rateAmount for all isw_syscost_rate_kind records
	 * having the sysCostRateId and having the kind reference with is_direct = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateDirectKindRates(Boolean isOracle, Long sysCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_ID = " + sysCostRateId + " ")
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
	 * SQL Query to update the rateAmount for all isw_syscost_rate_kind records
	 * having the sysCostRateId and having the kind reference with is_indirect = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateInDirectKindRates(Boolean isOracle, Long sysCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_ID = " + sysCostRateId + " ")
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
	 * SQL Query to update the rateAmount for all isw_syscost_rate_kind records
	 * having the sysCostRateId and having the kind reference with is_subordinate = true.
	 * 
	 * @param isOracle
	 * @param sysCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateSubordinateKindRates(Boolean isOracle, Long sysCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_ID = " + sysCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 1 : true ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}

	/**
	 * SQL Query to update the rateAmount for all isw_syscost_rate_kind records
	 * having the sysCostRateId and having the kind reference with is_subordinate = false.
	 * 
	 * @param isOracle
	 * @param sysCostRateId
	 * @param rateAmount
	 * @return
	 */
	public static String getUpdateNonSubordinateKindRates(Boolean isOracle, Long sysCostRateId, BigDecimal rateAmount) {
		StringBuffer sql = new StringBuffer()
			.append("UPDATE ISW_SYSCOST_RATE_KIND ")
			.append("SET RATE_AMOUNT = " + rateAmount + " ")
			.append("WHERE SYSCOST_RATE_ID = " + sysCostRateId + " ")
			.append("AND KIND_ID IN ")
			.append("( ")
			.append("     SELECT ID FROM ISWL_KIND WHERE REQUEST_CATEGORY_ID = ")
			.append("     (SELECT ID FROM ISWL_REQUEST_CATEGORY WHERE CODE = 'O' ) ")
			.append("     AND IS_SUBORDINATE = " + (isOracle ? 0 : false ) + " ")
			.append(") ")
			.append("");
		
		
		return sql.toString();
	}
	
}
