package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.customreports.enumerators.FieldFormatterTypeEnum;

public class CustomReportColumnFormatVo extends AbstractVo {
	
	private String displayName;
	private String formatType;
	private String dataType;
	
	public CustomReportColumnFormatVo() {
		super();
	}
	
	public CustomReportColumnFormatVo(String formatType, String displayName, String dataType) {
		super();
		this.displayName = displayName;
		this.formatType = formatType;
		this.dataType = dataType;
	}
	
	/**
	 * @param fieldFormatterTypeEnum
	 * @return
	 */
	public static CustomReportColumnFormatVo getInstance(FieldFormatterTypeEnum fieldFormatterTypeEnum) {
		CustomReportColumnFormatVo vo = new CustomReportColumnFormatVo();
		
		vo.setFormatType(fieldFormatterTypeEnum.getFormatType());
		vo.setDisplayName(fieldFormatterTypeEnum.getDisplayName());
		vo.setDataType(fieldFormatterTypeEnum.getDataType());
		
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
	 * @param formatType the formatType to set
	 */
	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	/**
	 * @return the formatType
	 */
	public String getFormatType() {
		return formatType;
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
