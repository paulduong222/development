package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.customreports.enumerators.ComparisonOperatorEnum;

public class CustomReportCriteriaOperatorVo extends AbstractVo {
	
	private String displayName;
	private String operatorType;
	private String dataType;
	
	public CustomReportCriteriaOperatorVo() {
		super();
	}
	
	public static CustomReportCriteriaOperatorVo getInstance(ComparisonOperatorEnum opEnum) {
		CustomReportCriteriaOperatorVo vo = new CustomReportCriteriaOperatorVo();
		
		vo.setDisplayName(opEnum.getDisplayName());
		vo.setOperatorType(opEnum.getOperatorType());
		vo.setDataType(opEnum.getDataType());
		
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
