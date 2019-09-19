import { AbstractVo } from './abstract-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { IncidentGroupVo } from 'src/app/data/incident-group-vo';
import { RequestCategoryVo } from './request-category-vo';
import { DepartmentVo } from './department-vo';
import { DailyFormVo } from './daily-form-vo';
import { GroupCategoryVo } from './group-category-vo';
import { SubGroupCategoryVo } from './sub-group-category-vo';
import { Sit209Vo } from './sit-209-vo';

export interface KindVo extends AbstractVo{
    code: string;
    description: string;
    standard: boolean;
    requestCategoryId: number;
    requestCategoryVo: RequestCategoryVo;
    sectionCodeVo: DepartmentVo;
    dailyFormVo: DailyFormVo;
    groupCategoryVo: GroupCategoryVo;
    subGroupCategoryVo: SubGroupCategoryVo;
    _209CodeVo: Sit209Vo;
    direct: boolean;
    units: number;
    people: number;
    lineOverhead: boolean;
    subordinate: boolean;
    strikeTeam: boolean;
    aircraft: boolean;
    active: boolean;
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
    standardCost: number;
}
