import { AbstractVo } from './abstract-vo';

export interface DepartmentVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
}
