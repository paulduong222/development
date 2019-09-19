import { DialogueData } from './dialogue-data';
import { TrainingSettingsVo } from '../training-settings-vo';

export interface TrainingSettingsData extends DialogueData {
    trainingSettingsVo: TrainingSettingsVo;
}
