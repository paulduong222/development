import { DialogueData } from './dialogue-data';
import { TrainingContactVo } from '../training-contact-vo';

export interface TrainingContactData extends DialogueData {
    trainingContactVo: TrainingContactVo;
}
