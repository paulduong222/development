import { AbstractVo } from './abstract-vo';

export interface IapPlanPrintOrderVo extends AbstractVo {
    iapPlanId: number;
    formType: string;
    formId: number;
    position: number;
}
