import { AgencyVo } from '../agency-vo';
import { DialogueData } from './dialogue-data';

export interface AgencyData extends DialogueData {
    agencyVo: AgencyVo;
}
