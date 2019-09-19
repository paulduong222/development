package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.reports.data.LastWorkDayReportData;
import gov.nwcg.isuite.core.reports.filter.LastWorkDayReportFilter;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

public class LastWorkDayReport extends BaseReport implements IReport {
	
	private static final String LAST_WORK_DAY_DATE_RESOURCE_CATEGORY_REPORT_NAME="LastWorkDayDateResourceReport";
	private static final String LAST_WORK_DAY_SECTION_DATE_REPORT_NAME="LastWorkDaySectionDateReport";
	private static final String LAST_WORK_DAY_SECTION_RESOURCE_CATEGORY_DATE_REPORT_NAME="LastWorkDaySectionResourceDateReport";
	private Date reportStartDate = null;
	private Date reportEndDate = null;
	private String reportName;

	public static final String REPORT_NAME_1="LastWorkDayDateResourceReport";
	public static final String REPORT_NAME_2="LastWorkDaySectionDateReport";
	public static final String REPORT_NAME_3="LastWorkDaySectionResourceDateReport";
	
	
	public LastWorkDayReport(String rname, Collection<LastWorkDayReportData> data) {
		super();
		this.reportName=rname;
		super.setReportData(data);
	}
	
	/**
	 * Returns the reportName
	 * 
	 * @return
	 * 		returns the reportName
	 */
	public String getReportName() {
		return reportName;
	}
	
	/**
	 * Sets the reportName
	 * 
	 * @param reportSta
	 * 		the reportStartDate to set
	 */
	public void setReportName(int report) {
		switch (report) {
			case LastWorkDayReportFilter.LAST_WORK_DAY_DATE_RESOURCE_CATEGORY_REPORT:
				this.reportName = LastWorkDayReport.LAST_WORK_DAY_DATE_RESOURCE_CATEGORY_REPORT_NAME;
				break;
			case LastWorkDayReportFilter.LAST_WORK_DAY_SECTION_DATE_REPORT:
				this.reportName = LastWorkDayReport.LAST_WORK_DAY_SECTION_DATE_REPORT_NAME;
				break;
			case LastWorkDayReportFilter.LAST_WORK_DAY_SECTION_RESOURCE_CATEGORY_DATE_REPORT:
				this.reportName = LastWorkDayReport.LAST_WORK_DAY_SECTION_RESOURCE_CATEGORY_DATE_REPORT_NAME;
				break;
		}
	}

	/**
	 * Sets the reportStartDate
	 * 
	 * @param reportStartDate
	 * 		the reportStartDate to set
	 */
	public void setReportStartDate(Date reportStartDate) {
		this.reportStartDate = reportStartDate;
	}

	/**
	 * Returns the reportStartDate
	 * 
	 * @return
	 * 		returns the reportStartDate
	 */
	public Date getReportStartDate() {
		return reportStartDate;
	}

	/**
	 * Sets the reportEndDate
	 * 
	 * @param reportEndDate
	 * 		the reportEndDate to set
	 */
	public void setReportEndDate(Date reportEndDate) {
		this.reportEndDate = reportEndDate;
	}

	/**
	 * Returns the reportEndDate
	 * 
	 * @return
	 * 		the reportEndDate to return
	 */
	public Date getReportEndDate() {
		return reportEndDate;
	}
}
