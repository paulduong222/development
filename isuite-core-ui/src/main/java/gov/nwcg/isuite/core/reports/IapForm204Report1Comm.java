package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.IapForm204ReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class IapForm204Report1Comm extends BaseReport implements IReport {
	
	private static final String REPORT_NAME="IAP_FORM_204_1Comm";
    private static final String SUB_REPORT_NAME_1="IAP_FORM_204_ResourcesAssigned";
    //private static final String SUB_REPORT_NAME_2="IAP_FORM_204_Communications";
    //private static final String SUB_REPORT_NAME_3="IAP_FORM_204_OperationsPersonnel";
    //private static final String SUB_REPORT_NAME_3="IAP_FORM_204_OpPersonnel2";
	
	public IapForm204Report1Comm(Collection<IapForm204ReportData> data) {
		super();
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME_1);
		//super.addSubReportName(SUB_REPORT_NAME_2);
		//super.addSubReportName(SUB_REPORT_NAME_3);
	}
	
	public String getReportName() {
		return IapForm204Report1Comm.REPORT_NAME;
	}

}
