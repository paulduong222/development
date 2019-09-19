import { KindVo } from '../kind-vo';
import { ReportSectionCategorySelectVo } from '../report-section-category-select-vo';
import { RequestCategoryVo } from '../report-select-vo';

export interface TimeReportFilter {
  actualReleaseDate: Date;
  actualReleaseTime: Date;
  actualReleaseTimeAsString: string;
  actualReleaseDateTransferVo: any;

  endDate: Date;
  startDate: Date;
  firstDateToIncludeOnReport: Date;
  lastDateToIncludeOnReport: Date;

  finalInvoice: boolean;

  printDraftInvoice: boolean;
  printOriginalInvoice: boolean;
  printDuplicateOriginalInvoice: boolean;

  printInvoiceOnly: boolean;
  printDeductionsAndInvoice: boolean;
  printItemizedDeductionsOnly: boolean;

  print: boolean;

  incidentResourceId: any;
  resourceId: any;
  requestNumber: string;
  resourceName: string;
  crewName: string;
  personName: string;

  sortBy: string;

  incidentId: any;
  incidentGroupId: any;
  incidentTag: string;
  incidentNumber: string;
  incidentName: string;

  timeInvoiceId: any;
  reasonForDelete: string;
  reasonForReprint: string;

  resourceReportSelectVo: RequestCategoryVo;
  requestNumberReportSelectVo: RequestCategoryVo;

  excludeDemob: boolean;
  isCrew: boolean;
  isPerson: boolean;

  exportToExcel: boolean;

  startDateVo: any;
  endDateVo: any;

  firstDateToIncludeOnReportVo: any;
  lastDateToIncludeOnReportVo: any;
}
