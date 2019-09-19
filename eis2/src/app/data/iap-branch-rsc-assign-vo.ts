import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';

export interface IapBranchRscAssignVo extends AbstractVo {
    iapBranchId: number;
    resourceName: string;
    lastName: string;
    firstName: string;
    leaderName: string;
    nbrOfPersonnel;
    nbrOfPersonnelString: string;
    transportation;
    dropOffPoint: string;
    dropOffTime: string;
    pickUpPoint: string;
    pickUpTime: string;
    contactInfo: string;
    additionalInfo: string;
    lastDayToWorkDateVo: DateTransferVo;
    isBlankLine: boolean;
    positionNum: number;
    requestNumber: string;
    itemCode: string;
    resourceId: number;

    trainee: boolean;
    leaderLastName: string;
    leaderFirstName: string;
    leaderIsTrainee: boolean;
}
