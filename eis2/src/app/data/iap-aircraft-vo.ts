import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';

export interface IapAircraftVo extends AbstractVo {
    iapForm220Id: number;
    wingType: string;
    aircraft: string;
    nbrAvailable: number;
    type: string;
    makeModel: string;
    faaNbr: string;
    base: string;
    baseFax: string;
    available: string;
    availableDateVo: DateTransferVo;
    startTime: string;
    remarks: string;
    isBlankLine: boolean;
    positionNum: number;
}
