import { KindVo } from "../kind-vo";

export interface DemobPlanningReportFilter {
  incidentId: any;
	incidentGroupId: any;
	allResourceCategories: boolean;
   aircraft: boolean;
	crews: boolean;
	overhead: boolean;
	equipment: boolean;

	allGroups: boolean;
	operations: boolean;
	command: boolean;
	logistics: boolean;
	plans: boolean;
	finance: boolean;
	external: boolean;

	allResourceStatuses: boolean;
	checkin: boolean;
	demobed: boolean;
	reassigned: boolean;
	pendingDemob: boolean;
	filled: boolean;

	includeSTTFComponents: boolean;
	subTotalsFirstSort: boolean;
	groupSubsection: boolean;
	sorts: string[];
}
