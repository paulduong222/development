import { DateTransferVo } from "../date-transfer-vo";

export interface IncidentTrainingSummaryReportFilter {
    incidentId: number;
    incidentGroupId: number;
    startDateVo: DateTransferVo;
    endDateVo: DateTransferVo;
}