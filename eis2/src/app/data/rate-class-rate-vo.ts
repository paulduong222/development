import { AbstractVo } from './abstract-vo';

export interface RateClassRateVo extends AbstractVo {
    rateClassId: number;
    area: string;
    rate: number;
    rateYear: number;
    standard: boolean;
    rateClassName: string;
    rateClassCode: string;
    trainingRateClassRateVo: RateClassRateVo;
    active: boolean;
}
