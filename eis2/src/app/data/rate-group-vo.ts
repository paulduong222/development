import { AbstractVo } from './abstract-vo';

export interface RateGroupVo extends AbstractVo{
    code: string;
    description: string;
    standard: boolean;
}
