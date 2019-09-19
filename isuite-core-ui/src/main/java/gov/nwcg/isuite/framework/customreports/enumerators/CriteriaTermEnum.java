package gov.nwcg.isuite.framework.customreports.enumerators;

import gov.nwcg.isuite.core.vo.CustomReportCriteriaTermVo;

public enum CriteriaTermEnum {
	
	TODAY("today", "TODAY")
	,YESTERDAY("yesterday", "YESTERDAY")
	,TOMORROW("tomorrow", "TOMORROW")
	;
	
	private String displayName;
	private String termType;
	
	CriteriaTermEnum(String displayName, String termType) {
		this.displayName = displayName;
		this.termType = termType;
	}
	
	public static CustomReportCriteriaTermVo getCriteriaTermVo(String termType) {
		CriteriaTermEnum criteriaTermEnum = CriteriaTermEnum.valueOf(termType);
		CustomReportCriteriaTermVo vo = CustomReportCriteriaTermVo.getInstance(criteriaTermEnum);
		
		return vo;
	}
	
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * @return the termType
	 */
	public String getTermType() {
		return termType;
	}
	

}
