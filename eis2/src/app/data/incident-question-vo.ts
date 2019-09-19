import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { WorkPeriodQuestionValueVo } from './work-period-question-value-vo';
import { QuestionVo } from './question-vo';

export interface IncidentQuestionVo extends AbstractVo {
    questionVo: QuestionVo;
    incidentVo: IncidentVo;
    position: number;
    visible: boolean;
//    workPeriodQuestionValueVo: WorkPeriodQuestionValueVo;
}
