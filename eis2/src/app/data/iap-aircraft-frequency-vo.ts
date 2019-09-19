import { AbstractVo } from './abstract-vo';

export interface IapAircraftFrequencyVo extends AbstractVo {
    iapForm220Id: number;
    frequency: string;
    amRxTx: string;
    fmRxTx: string;
    amTone: string;
    fmTone: string;
}
