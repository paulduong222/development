package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.QualificationsReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class QualificationsReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="QualificationsReport";

	public QualificationsReport(String subTitle,Collection<QualificationsReportData> data){
		super();
		super.setReportData(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return QualificationsReport.REPORT_NAME;
	}
	
	
}
