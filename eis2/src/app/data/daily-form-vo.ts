import { AbstractVo } from './abstract-vo';

export interface DailyFormVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
}
