import { TimeReportFilter } from './time-report-filter';

  export interface VendorResourceSummaryReportFilter extends TimeReportFilter {
     groupByNone: boolean;
     groupByItemCode: boolean;
     groupByVendor: boolean;
     groupByHireDate: boolean;

     sortByRequestNumber: boolean;
     sortByItemCode: boolean;
     sortByVendor: boolean;
     sortByHireDate: boolean;
}
