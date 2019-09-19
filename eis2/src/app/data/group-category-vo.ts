import { AbstractVo } from './abstract-vo';

export interface GroupCategoryVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
    active: boolean;
}
