import { TimeReportFilter } from "./time-report-filter";

export interface PersonnelTimeReportFilter extends TimeReportFilter {
    resourceIds: any;
    timeUnitLeaderName: any;
    timeUnitLeaderPhoneNumber: any;
    callsAreLongDistance: boolean;
    dialOutsideLine: boolean;
    dialOutsideLineNumber: any;
}
