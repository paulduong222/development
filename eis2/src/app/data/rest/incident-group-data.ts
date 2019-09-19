import { DbAvailVo } from 'src/app/data/db-avail-vo';
import { IncidentGroupVo } from '../incident-group-vo';
import { DialogueData } from './dialogue-data';

export interface IncidentGroupData extends DialogueData {
    incidentGroupVo: IncidentGroupVo;
}
