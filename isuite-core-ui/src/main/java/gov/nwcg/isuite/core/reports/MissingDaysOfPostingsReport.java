package gov.nwcg.isuite.core.reports;

import java.util.Collection;

import gov.nwcg.isuite.core.reports.data.MissingDaysOfPostingsReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class MissingDaysOfPostingsReport extends BaseReport implements IReport {
	
	private static final String SUB_REPORT="MissingDaysOfPostingsSubReport";
	private static final String REPORT_NAME="MissingDaysOfPostingsReport";
	
	public MissingDaysOfPostingsReport(String subTitle,Collection<MissingDaysOfPostingsReportData> data){
		super();
		super.setReportData(data);
		setSubreport();
	}
		
	public String getReportName() {
		return MissingDaysOfPostingsReport.REPORT_NAME;
	}
	
	public void setSubreport() {
		super.addSubReportName(SUB_REPORT);
	}	
}