import { AbstractVo } from './abstract-vo';

export interface ReportSectionCategorySelectVo extends AbstractVo {
    value: string;
    label: string;
    state: string;
    children: ReportSectionCategorySelectVo[];
}
