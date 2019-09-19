import { DialogueData } from './dialogue-data';
import { IncidentPrefsVo } from '../incident-prefs-vo';

export interface IncidentPrefsData extends DialogueData {
    incidentPrefsVo: IncidentPrefsVo;
    incidentPrefsVos: IncidentPrefsVo[];
}
