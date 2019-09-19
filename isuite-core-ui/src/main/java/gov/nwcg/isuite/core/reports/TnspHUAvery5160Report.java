package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.TnspHUAvery5160ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class TnspHUAvery5160Report extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="Tnsp_HU_Avery5160";
	
	public TnspHUAvery5160Report(Collection<TnspHUAvery5160ReportData> data) {
		super();
		super.setReportData(data);
	}
	
	public String getReportName() {
		return TnspHUAvery5160Report.REPORT_NAME;
	}

}
