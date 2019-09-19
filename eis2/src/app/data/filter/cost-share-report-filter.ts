import { ReportFilter } from "./report-filter";

export interface CostShareReportFilter extends ReportFilter {
	isSummaryReport: boolean;
	isDetailReport: boolean;
	isShiftKindReport: boolean;
	isWorkSheetReport: boolean;
	isIncidentGroup: boolean;
}
