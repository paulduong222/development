import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { CostDataVo } from './cost-data-vo';
import { ResourceOtherVo } from './resource-other-vo';
import { AssignmentStatusVo } from './assignment-status-vo';

export interface IncidentResourceOtherVo extends AbstractVo {
    costDataVo: CostDataVo;
    resourceOtherVo: ResourceOtherVo;
    incidentVo: IncidentVo;
    assignmentStatusVo: AssignmentStatusVo;
}
