import { AbstractVo } from './abstract-vo';

export interface IapAircraftTaskVo extends AbstractVo {
    iapForm220Id: number;
    type: string;
    name: string;
    startTime: string;
    flyFrom: string;
    flyTo: string;
    isBlankLine: boolean;
    positionNum: number;
}
