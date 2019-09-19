import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { IncidentGroupVo } from './incident-group-vo';

export interface SubGroupCategoryVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
    active: boolean;
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
}
