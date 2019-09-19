package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.TnspEvalRecordReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class TnspEvalRecordReport extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="TNSP_Eval_Record";
	
	public TnspEvalRecordReport(Collection<TnspEvalRecordReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return TnspEvalRecordReport.REPORT_NAME;
	}

}
