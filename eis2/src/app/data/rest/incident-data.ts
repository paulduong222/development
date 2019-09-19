import { IncidentVo } from '../incident-vo';
import { DialogueData } from './dialogue-data';
export interface IncidentData extends DialogueData {
    incidentVo: IncidentVo;
}
