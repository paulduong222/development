package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.customreports.enumerators.CriteriaTermEnum;

public class CustomReportCriteriaTermVo {
	
	private String displayName;
	private String termType;
	
	public CustomReportCriteriaTermVo() {
		super();
	}
	
	/**
	 * @param criteriaTermEnum
	 * @return
	 */
	public static CustomReportCriteriaTermVo getInstance(CriteriaTermEnum criteriaTermEnum) {
		CustomReportCriteriaTermVo vo = new CustomReportCriteriaTermVo();
		
		vo.setDisplayName(criteriaTermEnum.getDisplayName());
		vo.setTermType(criteriaTermEnum.getTermType());
		
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
	 * @param termType the termType to set
	 */
	public void setTermType(String termType) {
		this.termType = termType;
	}

	/**
	 * @return the termType
	 */
	public String getTermType() {
		return termType;
	}

}
