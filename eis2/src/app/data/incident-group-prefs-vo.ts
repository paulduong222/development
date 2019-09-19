import { AbstractVo } from './abstract-vo';
import { IncidentGroupVo } from './incident-group-vo';

export interface IncidentGroupPrefsVo extends AbstractVo {
    incidentGroupVo: IncidentGroupVo;
    sectionName: string;
    fieldLabel: string;
    position: number;
    selected: boolean;
 }
