import { AbstractVo } from './abstract-vo';

export interface RequestCategoryVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
}
