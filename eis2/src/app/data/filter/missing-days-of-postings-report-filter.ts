import { TimeReportFilter } from './time-report-filter';
import { DateTransferVo } from '../date-transfer-vo';

export interface MissingDaysOfPostingsReportFilter extends TimeReportFilter {
  incidentId: number;
  incidentGroupId: number;

  isIncidentGroup: boolean;

	startDateChar: string;
	endDateChar: string;
  endDateDbChar: string;

	startDate: Date;
  endDate: Date;

  personnelOrVendor: string;

  agencyName: string;

  employmentCode: string;

  noneOrAgency: string;

  reqNumOrResNameOrAgencyOrCode: string;

  reqNumOrResName: string;

	excludeDemobReassigned: boolean;

	startDateVo: DateTransferVo
	endDateVo: DateTransferVo
}
