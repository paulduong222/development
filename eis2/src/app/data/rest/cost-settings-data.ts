import { DialogueData } from './dialogue-data';
import { CostSettingsVo } from '../cost-settings-vo';

export interface CostSettingsData extends DialogueData {
    costSettingsVo: CostSettingsVo;
}
