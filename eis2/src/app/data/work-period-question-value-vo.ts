import { AbstractVo } from './abstract-vo';
import { WorkPeriodVo } from './work-period-vo';
import { IncidentQuestionVo } from './incident-question-vo';

export interface WorkPeriodQuestionValueVo extends AbstractVo {
    workPeriodVo: WorkPeriodVo;
    incidentQuestionVo: IncidentQuestionVo;
    questionValue: boolean;
    readOnlyPosition: number;
}
