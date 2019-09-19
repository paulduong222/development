import { AbstractVo } from './abstract-vo';
import { IncidentResourceVo } from './incident-resource-vo';
import { IapBranchPersonnelResVo } from './iap-branch-personnel-res-vo';

export interface IapBranchPersonnelVo extends AbstractVo {
    incidentResourceVo: IncidentResourceVo;
    iapBranchId: number;
    role: string;
    resourceName: string;
    phone1: string;
    phone2: string;
    roleType: string;
    isTrainee: boolean;
    positionNum: number;
    iapBranchPersonnelResVo1: IapBranchPersonnelResVo[];
    iapBranchPersonnelResVo2: IapBranchPersonnelResVo[];
    isBlankLine: boolean;
}
