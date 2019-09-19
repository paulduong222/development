package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.ShiftsInExcessOfStandardHoursReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class ShiftsInExcessOfStandardHoursReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="ShiftsInExcessOfStandardHoursReport";
	private static final String SUB_REPORT_NAME="ShiftsInExcessOfStandardHoursReport_subreport1";

	public ShiftsInExcessOfStandardHoursReport(String subTitle,Collection<ShiftsInExcessOfStandardHoursReportData> data){
		super();
		super.setHeaderTitle("SHIFTS IN EXCESS OF STANDARD HOURS");
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return ShiftsInExcessOfStandardHoursReport.REPORT_NAME;
	}
	
	
}
