import { TimeReportFilter } from "./time-report-filter";

export interface OF286TimeInvoiceReportFilter extends TimeReportFilter {
    selectByRequestNumber: boolean;
    selectByResourceName: boolean;
}
