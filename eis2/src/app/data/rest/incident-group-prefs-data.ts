import { DialogueData } from './dialogue-data';
import { IncidentGroupPrefsVo } from '../incident-group-prefs-vo';

export interface IncidentGroupPrefsData extends DialogueData {
    incidentGroupPrefsVo: IncidentGroupPrefsVo;
    incidentGroupPrefsVos: IncidentGroupPrefsVo[];
}
