import { AbstractVo } from './abstract-vo';
import { RateClassRateVo } from './rate-class-rate-vo';

export interface RateClassVo extends AbstractVo {
    rateClassName: string;
    rateYear: number;
    standard: boolean;
    rateClassRateVos: RateClassRateVo[];
}
