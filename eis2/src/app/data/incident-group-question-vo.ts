import { AbstractVo } from './abstract-vo';
import { IncidentGroupVo } from './incident-group-vo';
import { WorkPeriodQuestionValueVo } from './work-period-question-value-vo';
import { QuestionVo } from './question-vo';

export interface IncidentGroupQuestionVo extends AbstractVo {
    questionVo: QuestionVo;
    incidentGroupId: number;
    position: number;
    visible: boolean;
}
