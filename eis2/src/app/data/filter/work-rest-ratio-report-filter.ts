import { TimeReportFilter } from "./time-report-filter";

export interface WorkRestRatioReportFilter extends TimeReportFilter {
    allResources: boolean;
	specificResources: boolean;

	groupByNone: boolean;
	groupBySection: boolean;
	groupByDate: boolean;

	sectionTypeAll: boolean;
	sectionTypeCommand: boolean;
	sectionTypeOperations: boolean;
	sectionTypeFinance: boolean;
	sectionTypePlanning: boolean;
	sectionTypeLogistics: boolean;
	sectionTypeExternal: boolean;

	sectionSortByShiftStartDate: boolean;
	sectionSortByRequestNumber: boolean;
	sectionSortByName: boolean;

	dateTypeAscending: boolean;
	dateTypeDescending: boolean;

	dateSortByRequestNumber: boolean;
	dateSortByName: boolean;
}
