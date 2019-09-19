package gov.nwcg.isuite.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nwcg.isuite.core.controllers.restdata.AircraftDetailReportFilterData;
import gov.nwcg.isuite.core.controllers.restdata.AnalysisReportFilterData;
import gov.nwcg.isuite.core.controllers.restdata.CostShareReportFilterData;
import gov.nwcg.isuite.core.controllers.restdata.DialogueData;
import gov.nwcg.isuite.core.controllers.restdata.GroupCategoryTotalReportFilterData;
import gov.nwcg.isuite.core.reports.filter.AircraftDetailReportFilter;
import gov.nwcg.isuite.core.reports.filter.AnalysisReportFilter;
import gov.nwcg.isuite.core.reports.filter.CostShareReportFilter;
import gov.nwcg.isuite.core.reports.filter.GroupCategoryTotalReportFilter;
import gov.nwcg.isuite.core.service.ReportCostService;
import gov.nwcg.isuite.core.vo.dialogue.DialogueVo;
import gov.nwcg.isuite.framework.core.controllers.BaseRestController;

@Controller
@RequestMapping("/reports/costs")
public class ReportCostController extends BaseRestController {

	@Autowired
	private ReportCostService service;

	@RequestMapping(value = "/aircraft-detail", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateAircraftDetailReport(@RequestBody AircraftDetailReportFilter aircraftDetailReportFilter)
			throws Exception {
		return resolveMessaging(service.generateAircraftDetailReport(aircraftDetailReportFilter, null));
	}

	@RequestMapping(value = "/group-category-summary", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateGroupCategorySummaryReport(
			@RequestBody GroupCategoryTotalReportFilter groupCategoryTotalReportFilter) throws Exception {
		return resolveMessaging(service.generateGroupCategorySummaryReport(groupCategoryTotalReportFilter, null));
	}

	@RequestMapping(value = "/summary-by-resource", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateSummaryByResourceReport(
			@RequestBody GroupCategoryTotalReportFilter groupCategoryTotalReportFilter) throws Exception {
		return resolveMessaging(service.generateSummaryByResourceReport(groupCategoryTotalReportFilter, null));
	}

	@RequestMapping(value = "/summary-for-current-day", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateSummaryForCurrentDayReport(
			@RequestBody GroupCategoryTotalReportFilter groupCategoryTotalReportFilter) throws Exception {
		return resolveMessaging(service.generateSummaryForCurrentDayReport(groupCategoryTotalReportFilter, null));
	}

	@RequestMapping(value = "/details-by-resource", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateDetailByResourceReport(@RequestBody GroupCategoryTotalReportFilter groupCategoryTotalReportFilter)
			throws Exception {
		return resolveMessaging(service.generateDetailByResourceReport(groupCategoryTotalReportFilter, null));
	}

	@RequestMapping(value = "/analysis", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateAnalysisReport(@RequestBody AnalysisReportFilter analysisReportFilter)
			throws Exception {
		return resolveMessaging(service.generateAnalysisReport(analysisReportFilter, null));
	}

	@RequestMapping(value = "/daily-cost-comparison-ic", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateDailyCostComparisonICAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(
				service.generateDailyCostComparisonICAnalysisReport(data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/daily-cost-comparison-res", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateDailyCostComparisonRESRAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(
				service.generateDailyCostComparisonRESRAnalysisReport(data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/daily-cost-gt-10000", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourcesWithDailyCostExceeds10000AnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(service.generateResourcesWithDailyCostExceeds10000AnalysisReport(data.getFilter(),
				data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/time-postings-gt-3-days", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourcesWithActualTimePostingButThreeOrMoreDaysAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(service.generateResourcesWithActualTimePostingButThreeOrMoreDaysAnalysisReport(
				data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/no-posted-time", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourcesWithNoActualTimePostedAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(
				service.generateResourcesWithNoActualTimePostedAnalysisReport(data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/no-daily-cost-records", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourcesWithNoDailyCostRecordsAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(
				service.generateResourcesWithNoDailyCostRecordsAnalysisReport(data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/item-code-cost", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourceItemCodeByCostAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(
				service.generateResourceItemCodeByCostAnalysisReport(data.getFilter(), data.getDialogueVo()));
	}

	@RequestMapping(value = "/resources/item-code-by-cost/oh-personnel", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateResourceItemCodeByCostOHPersonnelAnalysisReport(
			@RequestBody AnalysisReportFilterData data) throws Exception {
		return resolveMessaging(service.generateResourceItemCodeByCostOHPersonnelAnalysisReport(data.getFilter(),
				data.getDialogueVo()));
	}

	@RequestMapping(value = "/group-category-total", method = RequestMethod.POST)
	public @ResponseBody DialogueVo getGroupCategoryTotalFilter(@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "filternName", required = true) String filterName,
			@RequestParam(value = "isIncidentGroup", required = true) boolean isIncidentGroup) throws Exception {
		return resolveMessaging(
				service.getGroupCategoryTotalFilter(id, filterName, isIncidentGroup, null));
	}
	
	@RequestMapping(value = "/group-category-total-by-filter", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateGroupCategoryTotalReport(@RequestBody GroupCategoryTotalReportFilter groupCategoryTotalReportFilter) throws Exception {
		return resolveMessaging(service.generateGroupCategoryTotalReport(groupCategoryTotalReportFilter, null));
	}
	
	@RequestMapping(value = "/cost-share", method = RequestMethod.POST)
	public @ResponseBody DialogueVo generateCostShareReport(@RequestBody CostShareReportFilter costShareReportFilter) throws Exception {
		return resolveMessaging(service.generateCostShareReport(costShareReportFilter, null));
	}
}
