import { TimeReportFilter } from "./time-report-filter";

export interface ShiftsInExcessOfStandardHoursReportFilter extends TimeReportFilter {
    standardHours: any;
}
