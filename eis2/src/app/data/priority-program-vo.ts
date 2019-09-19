import { AbstractVo } from './abstract-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { ResourceTrainingVo } from './resource-training-vo';

export interface PriorityProgramVo extends AbstractVo{
    code: string;
    description: string;
    incidentId: number;
    incidentGroupId: number;
    incidentVo: IncidentVo;
    resourceTrainingVos: ResourceTrainingVo[];
}
