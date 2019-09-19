package gov.nwcg.isuite.core.reports.generator;

import gov.nwcg.isuite.core.persistence.ReportTimeDao;
import gov.nwcg.isuite.core.reports.VendorResourceSummaryReport;
import gov.nwcg.isuite.core.reports.data.VendorResourceSummaryReportData;
import gov.nwcg.isuite.core.reports.filter.VendorResourceSummaryReportFilter;
import gov.nwcg.isuite.core.vo.dialogue.CourseOfActionVo;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;
import gov.nwcg.isuite.framework.report.IReport;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VendorResourceSummaryReportGeneratorImpl extends ReportGeneratorImpl {

	// Private static Strings that are expected by the JasperReport. 
	// Changing these string values will require a change in the JasperReport also.
	private static final String GROUPBYCLAUSE_NONE = "";
	private static final String GROUPBYCLAUSE_ITEMCODE = "itemCode";
	private static final String GROUPBYCLAUSE_VENDOR = "vendorAgreementNumber";
	private static final String GROUPBYCLAUSE_HIREDATE = "hireDate";
	
	private String getGroupByClause(VendorResourceSummaryReportFilter filter) {
		if(filter==null) return GROUPBYCLAUSE_NONE;
		
		if(filter.isGroupByItemCode())
			return GROUPBYCLAUSE_ITEMCODE;
		else if(filter.isGroupByVendor())
			return GROUPBYCLAUSE_VENDOR;
		else if(filter.isGroupByHireDate())
			return GROUPBYCLAUSE_HIREDATE;
		else return GROUPBYCLAUSE_NONE;
	}
		
    @Override
	public <E extends TimeReportFilter> DialogueVo generateReport(E filter, DialogueVo dialogueVo) throws ServiceException,
			PersistenceException {
		try {
			
			if(!(filter instanceof VendorResourceSummaryReportFilter)){
				throw new Exception("Invalid Filter Type. Expecting Filter to be of type VendorResourceSummaryReportFilter.");
			}
			
			ReportTimeDao reportTimeDao = (ReportTimeDao) super.context.getBean("reportTimeDao");
			Collection<VendorResourceSummaryReportData> reportData;
			reportData = reportTimeDao.getVendorResourceSummaryReportData((VendorResourceSummaryReportFilter) filter);
			
			if(reportData == null || reportData.size() == 0) {
				return noDataMessage("Vendor Resource Summary Report.", dialogueVo);
			}
			
			String groupByClause = getGroupByClause((VendorResourceSummaryReportFilter)filter);
			
			//List<String> reportsList = new ArrayList<String>();
			String pdfURL = null;
			try {
				IReport report = new VendorResourceSummaryReport("", reportData);
				report.addCustomField("incidentName", filter.getIncidentName());
				report.addCustomField("incidentTag", filter.getIncidentTag());
				report.addCustomField("groupByClause", groupByClause);
				
				//reportsList.add(createAndSaveReport(report));
				pdfURL = createAndSaveReport(report);
			} catch (Exception e) {
				super.handleException(e);
			}

			// add reportsList to dialogueVo
			//CourseOfActionVo coa = new CourseOfActionVo();
			//coa.setCoaName("GENERATE_VENDOR_RESOURCE_SUMMARY_REPORT");
			//coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RECORDSET);
			//coa.setIsDialogueEnding(true);
			CourseOfActionVo coa = new CourseOfActionVo();
			coa.setCoaName("REPORT COMPLETE");
			coa.setCoaType(CourseOfActionTypeEnum.HANDLE_RESULT_OBJECT);
			coa.setIsDialogueEnding(Boolean.TRUE);	 
			dialogueVo.setCourseOfActionVo(coa);
			dialogueVo.setResultObject(pdfURL);
		} catch (Exception e) {
			super.handleException(e);
		}

		return dialogueVo;	
	}
}
