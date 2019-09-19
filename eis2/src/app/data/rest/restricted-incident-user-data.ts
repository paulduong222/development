import { DialogueData } from './dialogue-data';
import { RestrictedIncidentUserVo } from '../restricted-incident-user-vo';

export interface RestrictedIncidentUserData extends DialogueData {
    restrictedIncidentUserVo: RestrictedIncidentUserVo;
    restrictedIncidentUserVos: RestrictedIncidentUserVo[];
}
