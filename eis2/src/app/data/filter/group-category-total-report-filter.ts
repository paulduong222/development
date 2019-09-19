import { ReportFilter } from "./report-filter";

export interface GroupCategoryTotalReportFilter extends ReportFilter {
    additionalFilters: any[];
	selectedReportGroup: any;
	selectedGraphGroup: any;
	additionalFilterType: any;
	
	isReportOnly: boolean;
	isGraphOnly: boolean;
	isReportAndGraph: boolean;
	isDateRangeSelected: boolean;
	isIncidentGroup: boolean;
	isOverhead: boolean;
	isNonOverhead: boolean;

	includeAllAccountingCode: boolean;
	selectiveAccountingCode: boolean;

	includeAllAgency: boolean;
	selectiveAgency: boolean;

	includeAllPaymentAgency: boolean;
	resourcesWherePaymentAgencyIsBlank: boolean;
	resourcesWherePaymentAgencyasEntry: boolean;
	selectivePaymentAgencies: boolean;

	includeAllCostGroup: boolean;
	resourcesWhereCostGroupIsBlank: boolean;
	resourcesWhereCostGroupHasEntry: boolean;
	selectiveCostGroups: boolean;

	includeAllUnitID: boolean;
	selectiveHomeUnits: boolean;

	includeAllItemCode: boolean;
	selectiveItemCode: boolean;

	groupingName: any;
	reportGroupForQuery: any;
		
	startDateVo: any;
	endDateVo: any;
}
