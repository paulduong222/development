import { AbstractVo } from './abstract-vo';

export interface IapBranchCommSummaryVo extends AbstractVo {
    iapBranchId: number;
    sfunction: string;
    rx: string;
    tx: string;
    tone: string;
    rxTone: string;
    txTone: string;
    mode: string;
    system1: string;
    channel1: string;
    system2: string;
    channel2: string;
    primaryContact: string;
    cellNbr: string;
    pagerNbr: string;
    masterFreqId: number;
    isBlankLine: boolean;
    positionNum: number;
}
