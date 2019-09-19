package gov.nwcg.isuite.core.reports.data;


/**
 * Report data object for IncidentGroupConflictReport.jrxml.
 */
public class IncGroupConflictReportData {
	private String groupName;
	private String textline;
	
	public IncGroupConflictReportData() {
	}

	/**
	 * @return the textline
	 */
	public String getTextline() {
		return textline;
	}

	/**
	 * @param textline the textline to set
	 */
	public void setTextline(String textline) {
		this.textline = textline;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
