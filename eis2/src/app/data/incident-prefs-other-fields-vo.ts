import { AbstractVo } from './abstract-vo';

export interface IncidentPrefsOtherFieldsVo extends AbstractVo {
    incidentId: number;
    incidentGroupId: number;
    other1Label: string;
    other2Label: string;
    other3Label: string;
}
