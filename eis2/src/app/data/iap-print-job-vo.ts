import { AbstractVo } from './abstract-vo';
import { IapPrintFormVo } from './iap-print-form-vo';
import { IapPlanPrintOrderVo } from './iap-plan-print-order-vo';

export interface IapPrintJobVo extends AbstractVo {
    iapPlanId: number;
    formsToPrint: IapPrintFormVo[];
    ordersToPrint: IapPlanPrintOrderVo[];
    lockPlan: boolean;
}
