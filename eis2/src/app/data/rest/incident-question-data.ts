import { DialogueData } from './dialogue-data';
import { IncidentQuestionVo } from '../incident-question-vo';

export interface IncidentQuestionData extends DialogueData {
    questionType: string;
    incidentQuestionVo: IncidentQuestionVo;
    incidentQuestionVos: IncidentQuestionVo[];
}
