package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gov.nwcg.isuite.framework.report.data.BaseReportData;

public class Tnsp1ReportData {
	
	public Tnsp1ReportData() {
	}

	public static Tnsp1ReportData getInstanceBlankForm() throws Exception {
		Tnsp1ReportData reportData = new Tnsp1ReportData();
		return reportData;
	}

	public static Tnsp1ReportData getInstance() throws Exception {
		Tnsp1ReportData reportData = new Tnsp1ReportData();
		
		return reportData;
	}

}
