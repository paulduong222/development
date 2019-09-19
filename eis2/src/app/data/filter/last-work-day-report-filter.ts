import { KindVo } from "../kind-vo";

export interface LastWorkDayReportFilter {
  incidentId: any;
	incidentGroupId: any;
	includeSTTFComponents: boolean;
  includeAllDates: boolean;
	reportStartDate: Date;
	reportEndDate: Date;
	sectionsToInclude: String[];
	sortBy: String[];
	grouping: any;

	groupByDateResourceCategory: boolean;
	groupBySectionDate: boolean;
	groupBySectionResourceCategoryDate: boolean;

	allSections: boolean;
	command: boolean;
	externalResources: boolean;
	finance: boolean;
	logistics: boolean;
	operations: boolean;
	plans: boolean;
}
