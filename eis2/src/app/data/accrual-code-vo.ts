import { AbstractVo } from './abstract-vo';

export interface AccrualCodeVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
}
