package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaEvaluatorEnum;

public class CustomReportCriteriaEvaluatorVo extends AbstractVo {
	
	private String displayName;
	private String evaluatorType;
	private String operatorType;
	private String dataType;
	
	public CustomReportCriteriaEvaluatorVo() {
		super();
	}
	
	public static CustomReportCriteriaEvaluatorVo getInstance(CriteriaEvaluatorEnum evalEnum) {
		CustomReportCriteriaEvaluatorVo vo = new CustomReportCriteriaEvaluatorVo();
		
		vo.setDisplayName(evalEnum.getDisplayName());
		vo.setEvaluatorType(evalEnum.getEvaluatorType());
		vo.setOperatorType(evalEnum.getOperatorType());
		vo.setDataType(evalEnum.getDataType());
		
		return vo;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param evaluatorType the evaluatorType to set
	 */
	public void setEvaluatorType(String evaluatorType) {
		this.evaluatorType = evaluatorType;
	}

	/**
	 * @return the evaluatorType
	 */
	public String getEvaluatorType() {
		return evaluatorType;
	}

	/**
	 * @param operatorType the operatorType to set
	 */
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	/**
	 * @return the operatorType
	 */
	public String getOperatorType() {
		return operatorType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	
	

}
