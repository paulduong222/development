import { AbstractVo } from './abstract-vo';

export interface CostAccrualsVo extends AbstractVo {
    id: number;
    changeAmount: string;
    extractDate: number;
    extractDatevo: {};
    finalized: boolean;
    finalizedDate: number;
    incidentGroupId: number;
    incidentId: number;
    preparedBy: string;
    preparedPhone: string;
    totalAmount: number;

}