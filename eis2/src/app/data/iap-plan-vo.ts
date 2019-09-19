import { AbstractVo } from './abstract-vo';

export interface IapPlanVo extends AbstractVo {
    incidentId: number;
    incidentGroupId: number;
    incidentName: string;
    operationalPeriod: string;
    fromDate: Date;
    fromDateTime: string;
    fromDateString: string;
    toDate: Date;
    toDateTime: string;
    toDateString: string;
    isPlanLocked: boolean;
}
