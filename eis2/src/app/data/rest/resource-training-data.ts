import { DialogueData } from './dialogue-data';
import { ResourceTrainingVo } from '../resource-training-vo';

export interface ResourceTrainingData extends DialogueData {
    resourceTrainingVo: ResourceTrainingVo;
}