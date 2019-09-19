import { DialogueData } from './dialogue-data';
import { IncidentResourceVo } from '../incident-resource-vo';

export interface IncidentResourceData extends DialogueData {
    propagateToChildren: boolean;
    incidentResourceVo: IncidentResourceVo;
}
