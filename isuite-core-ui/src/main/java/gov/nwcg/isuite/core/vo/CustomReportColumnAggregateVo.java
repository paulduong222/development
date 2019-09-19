package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldAggregatorTypeEnum;

public class CustomReportColumnAggregateVo extends AbstractVo {
	
	private String displayName;
	private String aggregateType;
	private String dataType;
	
	public CustomReportColumnAggregateVo() {
		super();
	}
	
	public CustomReportColumnAggregateVo(String aggregateType, String displayName, String dataType) {
		super();
		this.displayName = displayName;
		this.aggregateType = aggregateType;
		this.dataType = dataType;
	}
	
	public static CustomReportColumnAggregateVo getInstance(FieldAggregatorTypeEnum fieldAggregatorTypeEnum) {
		CustomReportColumnAggregateVo vo = new CustomReportColumnAggregateVo();
		
		vo.setAggregateType(fieldAggregatorTypeEnum.getAggregateType());
		vo.setDisplayName(fieldAggregatorTypeEnum.getDisplayName());
		vo.setDataType(fieldAggregatorTypeEnum.getDataType());
		
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
	 * @return the aggregateType
	 */
	public String getAggregateType() {
		return aggregateType;
	}

	/**
	 * @param aggregateType the aggregateType to set
	 */
	public void setAggregateType(String aggregateType) {
		this.aggregateType = aggregateType;
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
