package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Date;

public class TentativeReleasePosterReportData extends BaseReportData {
	private Date tentativeReleaseDate = null;
	private String tentativeReleaseDateString="";
	private String requestNumber = "";
	private String kindCode = "";
	private String resourceCategory = "";
	private Long resourceId;
	
	public TentativeReleasePosterReportData() {
	}

	/**
	 * @return the tentativeReleaseDate
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	/**
	 * @param tentativeReleaseDate the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the kindCode
	 */
	public String getKindCode() {
		return kindCode;
	}

	/**
	 * @param kindCode the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * @return the resourceCategory
	 */
	public String getResourceCategory() {
		return resourceCategory;
	}

	/**
	 * @param resourceCategory the resourceCategory to set
	 */
	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}
	
	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getTentativeReleaseDateString() {
		if(null != this.tentativeReleaseDate){
			String dt = DateUtil.toDateString(this.tentativeReleaseDate);
			String tm = DateUtil.toMilitaryTime(this.tentativeReleaseDate);
			
			return dt + " " + tm;
		}else
			return "";
	}

	public void setTentativeReleaseDateString(String val) {
		this.tentativeReleaseDateString = val;
	}

}
