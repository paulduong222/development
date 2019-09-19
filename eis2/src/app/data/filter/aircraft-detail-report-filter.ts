import { ReportFilter } from "./report-filter";

export interface AircraftDetailReportFilter extends ReportFilter {
	aircraftTypes: any[];
    isReportOnly: boolean;
	isGraphOnly: boolean;
	isReportAndGraph: boolean;
	isDateRangeSelected: boolean;
	isIncludeAllAircraftType: boolean;
	isSelectiveAircraftTypes: boolean;
	isIncidentGroup: boolean;
	startDateVo: any;
	endDateVo: any;
}
