import { TimeReportFilter } from './time-report-filter';

export interface SummaryHoursWorkedReportFilter extends TimeReportFilter {
  sections: string[];
  incidentIds: number[];
  transactionId: number;

	isNonGroupBy: boolean;
	isSection: boolean;
	isAllResources: boolean;
	isSpecificResources: boolean;
	isSortByShifStartDate: boolean;
	isSortByRequestNum: boolean;
	isSortByResourceName: boolean;
  isExcludeDemob: boolean;
}
