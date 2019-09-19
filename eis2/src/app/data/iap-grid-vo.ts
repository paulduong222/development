import { AbstractVo } from './abstract-vo';
import { IapAttachmentVo } from './iap-attachment-vo';
import { IapPlanPrintOrderVo } from './iap-plan-print-order-vo';

export interface IapGridVo extends AbstractVo {
    hierachalGroupField: any[];
    recordType: string;
    uniqueKey: string;
    name: string;
    formType: string;
    sortValue: string;
    displayName: string;
    fromDateString: string;
    fromTimeString: string;
    toDateString: string;
    toTimeString: string;
    isLocked: boolean;
    parentGridVoId: number;
    operationalPeriod: string;
    fromDate: Date;
    toDate: Date;
    printDocument: boolean;
    printPosition: number;
    isAttachment: boolean;
    attachmentId: number;
    iapAttachmentVos: IapAttachmentVo[];
    iapPlanPrintOrderVos: IapPlanPrintOrderVo[];
    formSequence: number;
    hasMultiple: boolean;
    children: IapGridVo[];
}
