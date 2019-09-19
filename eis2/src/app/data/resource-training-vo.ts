import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IncidentResourceVo } from './incident-resource-vo';
import { RscTrainingTrainerVo } from './rsc-training-trainer-vo';
import { RscTrainingObjectiveVo } from './rsc-training-objective-vo';
import { DropdownData } from './dropdowndata';
import { PriorityProgramVo } from './priority-program-vo';


export interface ResourceTrainingVo extends AbstractVo {
    kindVo: KindVo;
    incidentResourceVo: IncidentResourceVo;
    initialAssignment: boolean;
    startDateTransferVo: DateTransferVo;
    endDateTransferVo: DateTransferVo;
    fsPriorityTrainee: boolean;
    validRedCard: boolean;
    objectiveIssuer: string;
    rscTrainingTrainerVos: RscTrainingTrainerVo[];
    rscTrainingObjectiveVos: RscTrainingObjectiveVo[];
    ptbPercentage: number;
    numberOfAcres: number;
    tnspComments: string;
    incidentTaskBook: boolean;
    complexityVo: DropdownData;
    priorityProgramVo: PriorityProgramVo;
    recommendationVo: DropdownData;
    fuelTypeVos: DropdownData[];
    lengthAssignment: string;
}