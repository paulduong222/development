package gov.nwcg.isuite.framework.customreports.enumerators;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaOperatorVo;
import gov.nwcg.isuite.framework.customreports.clauses.AbstractClause;

import java.util.ArrayList;
import java.util.Collection;

public enum ComparisonOperatorEnum {
	
	IS_EQUAL_TO("is equal to", "IS_EQUAL_TO", "STRING, STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", "=", "CommonClause")
	,NOT_EQUAL_TO("not equal to", "NOT_EQUAL_TO", "STRING, STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", "!=", "CommonClause")
	,IS_EMPTY("is empty", "IS_EMPTY", "STRING", "", "IsEmptyClause")
	,IS_NOT_EMPTY("is not empty", "IS_NOT_EMPTY", "STRING", "", "IsNotEmptyClause")
	,STARTS_WITH("starts with", "STARTS_WITH", "STRING", "", "StartsWithClause")
	,ENDS_WITH("ends with", "ENDS_WITH", "STRING", "", "EndsWithClause")
	,CONTAINS("contains", "CONTAINS", "STRING", "", "ContainsClause")
	,NOT_STARTS_WITH("not starts with", "NOT_STARTS_WITH", "STRING", "", "NotStartsWithClause")
	,NOT_ENDS_WITH("not ends with", "NOT_ENDS_WITH", "STRING", "", "NotEndsWithClause")
	,NOT_CONTAINS("not contains", "NOT_CONTAINS", "STRING", "", "NotContainsClause")
	,IN("in", "IN", "STRING", "", "InClause")
	,NOT_IN("not in", "NOT_IN", "STRING", "", "NotInClause")
	,GREATER_THAN("greater than", "GREATER_THAN", "STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", ">", "CommonClause")
	,GREATER_THAN_EQUAL_TO("greater than equal to", "GREATER_THAN_EQUAL_TO", "STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", ">=", "CommonClause")
	,LESS_THAN("less than", "LESS_THAN", "STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", "<", "CommonClause")
	,LESS_THAN_EQUAL_TO("less than equal to", "LESS_THAN_EQUAL_TO", "STR_LENGTH, DATE, TIME, NUMBER, CURRENCY", "<=", "CommonClause")
	,IS_TRUE("is true", "IS_TRUE", "BOOLEAN", "", "IsTrueClause")
	,IS_FALSE("is false", "IS_FALSE", "BOOLEAN", "", "IsFalseClause")
	,IS_NULL("is null", "IS_NULL", "DATE, TIME, NUMBER, CURRENCY", "", "IsNullClause")
	,IS_NOT_NULL("is not null", "IS_NOT_NULL", "DATE, TIME, NUMBER, CURRENCY", "", "IsNotNullClause")
	,BETWEEN("between", "BETWEEN", "DATE, TIME, NUMBER, CURRENCY", "", "BetweenClause")
	;
	
	private String displayName;
	private String operatorType;
	private String dataType;
	private String symbol;
	private String className;
	
	
	private static final String PACKAGE_PREFIX = "gov.nwcg.isuite.framework.customreports.clauses.";
	
	ComparisonOperatorEnum(String displayName, String operatorType, String dataType, String symbol, String className) {
		this.displayName = displayName;
		this.operatorType = operatorType;
		this.dataType = dataType;
		this.symbol = symbol;
		this.className = className;
	}
	
	/**
	 * @param operatorType
	 * @param dataType
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public static AbstractClause getAbstractClause(CustomReportCriteriaOperatorVo operatorVo, String mode) throws Exception {
		for( ComparisonOperatorEnum coEnum :  ComparisonOperatorEnum.values()) {
			if(coEnum.getOperatorType().equals(operatorVo.getOperatorType())) {
				Class targetClass = Class.forName(PACKAGE_PREFIX + coEnum.className);
				AbstractClause clauseClass=(AbstractClause)targetClass.newInstance();
				
				return clauseClass;
			}
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public static Collection<CustomReportCriteriaOperatorVo> getCriteriaOperatorVos() {
		Collection<CustomReportCriteriaOperatorVo> vos = new ArrayList<CustomReportCriteriaOperatorVo>();
		
		for(ComparisonOperatorEnum comparisonOperator : ComparisonOperatorEnum.values()) {
			vos.add(CustomReportCriteriaOperatorVo.getInstance(comparisonOperator));
		}
		
		return vos;
	}
	
	/**
	 * @param operatorType
	 * @return
	 */
	public static CustomReportCriteriaOperatorVo getCriteriaOperatorVo(String operatorType) {
		ComparisonOperatorEnum comparisonOperatorEnum = ComparisonOperatorEnum.valueOf(operatorType);
		CustomReportCriteriaOperatorVo vo = CustomReportCriteriaOperatorVo.getInstance(comparisonOperatorEnum);
		
		return vo;
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the operatorType
	 */
	public String getOperatorType() {
		return operatorType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	
	
}
