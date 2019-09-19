package gov.nwcg.isuite.framework.customreports.enumerators;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaEvaluatorVo;

import java.util.ArrayList;
import java.util.Collection;

public enum CriteriaEvaluatorEnum {
	
	AS_IS("text value", "AS_IS", "STRING", "STRING")
	,AS_TEXT_LENGTH("text length", "AS_TEXT_LENGTH", "STRING", "STR_LENGTH")
	,MM_DD_YYYY("mm/dd/yyyy", "MM_DD_YYYY", "DATE", "DATE")
	,MONTH("month", "MONTH", "DATE", "DATE")
	,DAY("day", "DAY", "DATE", "DATE")
	,YEAR("year", "YEAR", "DATE", "DATE")
	,PLUS_N_DAYS("+n days", "PLUS_N_DAYS", "DATE", "DATE")
	,MINUS_N_DAYS("-n days", "MINUS_N_DAYS", "DATE", "DATE")
	,PLUS_N_MONTHS("+n months", "PLUS_N_MONTHS", "DATE", "DATE")
	,MINUS_N_MONTHS("-n months", "MINUS_N_MONTHS", "DATE", "DATE")
	,MILITARY_HOUR_MINUTE("hour/minute", "MILITARY_HOUR_MINUTE", "TIME", "TIME")
	,MILITARY_HOUR("hour", "MILITARY_HOUR", "TIME", "TIME")
	,MINUTE("minute", "MINUTE", "TIME", "TIME")
	,SECOND("second", "SECOND", "TIME", "TIME")
	;
	 
	private String displayName;
	private String evaluatorType;
	private String dataType;
	private String operatorType;
	
	CriteriaEvaluatorEnum(String displayName, String evaluatorType, String dataType, String operatorType) {
		this.displayName = displayName;
		this.evaluatorType = evaluatorType;
		this.dataType = dataType;
		this.operatorType = operatorType;
	}
	
	/**
	 * @return
	 */
	public static Collection<CustomReportCriteriaEvaluatorVo> getCriteriaEvaluatorVos() {
		Collection<CustomReportCriteriaEvaluatorVo> vos = new ArrayList<CustomReportCriteriaEvaluatorVo>();
		
		for(CriteriaEvaluatorEnum criteriaEvaluator : CriteriaEvaluatorEnum.values()) {
			vos.add(CustomReportCriteriaEvaluatorVo.getInstance(criteriaEvaluator));
		}
		
		return vos;
	}
	
	/**
	 * @param evaluatorType
	 * @return
	 */
	public static CustomReportCriteriaEvaluatorVo getCriteriaEvaluatorVo(String evaluatorType) {
		
		CriteriaEvaluatorEnum criteriaEvaluatorEnum = CriteriaEvaluatorEnum.valueOf(evaluatorType);
		CustomReportCriteriaEvaluatorVo vo = CustomReportCriteriaEvaluatorVo.getInstance(criteriaEvaluatorEnum);
		
		return vo;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @return the evaluatorType
	 */
	public String getEvaluatorType() {
		return evaluatorType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @return the operatorType
	 */
	public String getOperatorType() {
		return operatorType;
	}
	
}
