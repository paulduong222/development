import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IapBranchCommSummaryVo } from './iap-branch-comm-summary-vo';
import { IapBranchPersonnelVo } from './iap-branch-personnel-vo';
import { IapBranchRscAssignVo } from './iap-branch-rsc-assign-vo';

export interface IapForm204Vo extends AbstractVo {
    iapPlanId: number;
    branchName: string;
    divisionName: string;
    groupName: string;
    controlOperations: string;
    specialInstructions: string;
    stagingArea: string;
    workAssignment: string;
    preparedBy: string;
    approvedBy: string;
    isForm204Locked: boolean;
    reparedDateVo: DateTransferVo;
    preparedTime: string;
    preparedByPosition: string;
    iapBranchCommSummaryVos: IapBranchCommSummaryVo[];
    iapBranchPersonnelVos: IapBranchPersonnelVo[];
    iapBranchRscAssignVos: IapBranchRscAssignVo[];
}
