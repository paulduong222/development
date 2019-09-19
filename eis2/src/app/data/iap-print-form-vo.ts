import { AbstractVo } from './abstract-vo';

export interface IapPrintFormVo extends AbstractVo {
    formType: string;
    formId: number;
    position: number;
    attachmentId: number;
    isAttachment: false;
}
