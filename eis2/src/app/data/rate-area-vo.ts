import { AbstractVo } from './abstract-vo';

export interface RateAreaVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
 }
