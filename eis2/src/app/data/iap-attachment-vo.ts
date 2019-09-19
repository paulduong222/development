import { AbstractVo } from './abstract-vo';

export interface IapAttachmentVo extends AbstractVo {
    iapPlanId: number;
    attachmentName: string;
    attached: boolean;
    filename: string;
}
