import { AbstractVo } from './abstract-vo';

export interface IapFrequencyVo extends AbstractVo {
    iapForm205Id: number;
    radioType: string;
    channel: string;
    sfunction: string;
    frequencyRx: string;
    toneRx: string;
    frequencyTx: string;
    toneTx: string;
    assignment: string;
    remarks: string;
    preparedDate: Date;
    zoneGroup: string;
    channelName: string;
    modeType: string;
    masterFreqId: number;
    isBlankLine: boolean;
    positionNum: number;
}
