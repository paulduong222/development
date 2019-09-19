package gov.nwcg.isuite.core.reports;

import gov.nwcg.isuite.core.reports.data.CustomReportData;
import gov.nwcg.isuite.framework.report.BaseReport;
import gov.nwcg.isuite.framework.report.IReport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomReportsReport extends BaseReport implements IReport{

	private static final String REPORT_NAME="CustomReport";

	public CustomReportsReport(String headerTitle, String subTitle, Collection<CustomReportData> data, String incidents){
		super();
		super.setHeaderTitle(headerTitle);
		super.setHeaderSubTitle(subTitle);
		super.setReportData(data);
		
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("incidents", incidents);
		super.setCustomFields(pMap);
	}	
	
	public String getReportName(){
		return REPORT_NAME;
	}
}
