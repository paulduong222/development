import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';
import { AssignmentStatusVo } from './assignment-status-vo';
import { WorkPeriodVo } from './work-period-vo';
import { AssignmentTimeVo } from './assignment-time-vo';
import { TimeAssignAdjustVo } from './time-assign-adjust-vo';
import { AssignmentStatusTypeEnum } from './enums/assignment-status-type-enum.enum';

export interface AssignmentVo extends AbstractVo {
    kindVo: KindVo;
    requestNumber: string;
    startDate: Date;
    endDate: Date;
    training: boolean;
    assignmentStatusVo: AssignmentStatusVo;
    assignmentStatus: AssignmentStatusTypeEnum;
    workPeriodVos: WorkPeriodVo[];
    assignmentTimeVo: AssignmentTimeVo;
    timeAssignAdjustVos: any[];
    origAssignmentStatusVo: AssignmentStatusVo;
    reassignIncidentName: string;
    reassignIncidentNumber: string;
}
