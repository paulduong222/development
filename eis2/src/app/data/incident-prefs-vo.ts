import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';

export interface IncidentPrefsVo extends AbstractVo {
    incidentVo: IncidentVo;
    sectionName: string;
    fieldLabel: string;
    position: number;
    selected: boolean;
 }
