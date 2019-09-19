import { KindVo } from "../kind-vo";

export interface AllIncidentResourcesReportFilter {
    incidentId: any;
	incidentGroupId: any;
	resourceTypes: String[];
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
	
	strikeTeamTaskForce: boolean;
	subTotalsFirstSort: boolean;
	groupSubsection: boolean;
    allResourcesSort: String[];
}
