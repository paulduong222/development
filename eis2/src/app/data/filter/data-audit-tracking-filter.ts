import { DateTransferVo } from "../date-transfer-vo";

export interface DataAuditTrackingFilter {
    startDate: object;
    endDate: object;
    auditEvent: string;
    homeUnit: string;
    primaryDispatchCenter: string;
    lastName: string;
    loginName: string;
    createdDate: object;
    crypticDateFilterCode: string;
    auditEventType: string[];
    modifiedBy: string;
    backupFilename: string;
    backupFilepath: string;
    backuptype: string;
    startDateVo: DateTransferVo;
    endDateVo: DateTransferVo;
}
