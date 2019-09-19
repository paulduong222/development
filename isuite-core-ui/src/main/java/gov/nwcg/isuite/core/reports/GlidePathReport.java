package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.GlidePathReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class GlidePathReport extends BaseReport implements IReport{

	private static final String REPORT_NAME="GlidePathReport";

	public GlidePathReport(String subTitle,Collection<GlidePathReportData> data){
		super();
		super.setHeaderTitle("Glide Path Report");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		super.addSubReportName("GlidePathReport_resourceday_totals");
		super.addSubReportName("GlidePathReport_resourceday");
		super.addSubReportName("GlidePathReport_sectionsheet_header");
		super.addSubReportName("GlidePathReport_sectionsheet");
		super.addSubReportName("GlidePathReport_summarysheet_header");
		super.addSubReportName("GlidePathReport_summarysheet");
		
		// By default, all glidepath reports are to be exported to excel, not PDF or HTML.
		super.enableExportToExcel(); 
	}
	
	public String getReportName(){
		return REPORT_NAME;
	}
}
