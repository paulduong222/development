package gov.nwcg.isuite.core.reports;

import java.util.Collection;
import gov.nwcg.isuite.core.reports.data.DetailByResourceReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;


public class DetailByResourceReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="DetailByResourceReport";
	private static final String SUB_REPORT="DetailByResourceSubReport";
		                                                     
	public DetailByResourceReport(Collection<DetailByResourceReportData> data) {
		super();
		super.setReportData(data);
		setSubreport();
	}
	
	public String getReportName() {
			return DetailByResourceReport.REPORT_NAME;
	}
	
	public void setSubreport() {
			super.addSubReportName(SUB_REPORT);
	}
}

