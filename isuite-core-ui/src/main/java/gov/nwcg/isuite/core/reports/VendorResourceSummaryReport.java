package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.VendorResourceSummaryReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class VendorResourceSummaryReport extends BaseReport implements IReport{

	private static final String REPORT_NAME="VendorResourceSummaryReport";

	public VendorResourceSummaryReport(String subTitle,Collection<VendorResourceSummaryReportData> data){
		super();
		super.setHeaderTitle("HEADER_TITLE_FROM_Class:VendorResourceSummaryReport");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
	}
	
	public String getReportName(){
		return REPORT_NAME;
	}
}
