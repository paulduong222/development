package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.EmergencyEquipmentDandAReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;

public class EmergencyEquipmentDandAReport extends BaseReport implements IReport{
	/*
	 * for now hardcode the report name.
	 * we may want to discuss having a isw_report table
	 * that defines the supported reports.
	 */
	private static final String REPORT_NAME="EmergencyEquipmentDandAReport";
	private static final String SUB_REPORT_NAME="EmergencyEquipmentDandAReport_subreport1";

	public EmergencyEquipmentDandAReport(Collection<EmergencyEquipmentDandAReportData> data){
		super();
		super.setHeaderTitle("Emergency Equipment Deductions and Additions");
		super.setReportData(data);
		super.addSubReportName(SUB_REPORT_NAME);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.report.IReport#getReportName()
	 */
	public String getReportName(){
		return EmergencyEquipmentDandAReport.REPORT_NAME;
	}
	
	
}
