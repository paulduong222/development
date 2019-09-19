import { TimeReportFilter } from "./time-report-filter";

export interface AnalysisReportFilter extends TimeReportFilter {
    incidentId: any;
	incidentGroupId: any;	
	isIncidentGroup: boolean;
	analysisReport: any; //exception, byCost, byOverhead
	itemCodeOrResource: any; //resource
	analysisReportFilter: any; //exceeds10000, threeOrMoreDays, noAgencyAssigned, noCostRecords
	exceeds10000: any;
	threeOrMoreDays: any;
	startDateChar: any;
	endDateChar: any;
	startDate: Date;
	endDate: Date;
	dateRangeIncluded: boolean;
	noDailyCostSelections: any[];
	
	allExceptionsIncluded: boolean;
	missingAgencyIncluded: boolean;
	missingDatesIncluded: boolean;
	statusIsFIncluded: boolean;
	
	startDateVo: any;
	endDateVo: any;
}
