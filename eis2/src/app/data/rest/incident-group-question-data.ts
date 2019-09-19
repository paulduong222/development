import { DialogueData } from './dialogue-data';
import { IncidentGroupQuestionVo } from '../incident-group-question-vo';

export interface IncidentGroupQuestionData extends DialogueData {
    questionType: string;
    incidentGroupQuestionVo: IncidentGroupQuestionVo;
    incidentGroupQuestionVos: IncidentGroupQuestionVo[];
}
