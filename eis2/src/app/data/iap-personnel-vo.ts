import { AbstractVo } from './abstract-vo';
import { IncidentResourceVo } from './incident-resource-vo';
import { BranchSettingVo } from './branch-setting-vo';
import { IapPersonnelResVo } from './iap-personnel-res-vo';

export interface IapPersonnelVo extends AbstractVo {
iapForm203Id: number;
iapForm220Id: number;
agencyName: string;
role: string;
roleType: string;
name: string;
phone: string;
form: string;
section: string;
isBlankLine: boolean;
isBlankBranch: boolean;
divisionGroupName: string;
positionNum: number;
isTrainee: boolean;
incidentResourceVo: IncidentResourceVo;
branchSettingVo: BranchSettingVo;
isBranchName: boolean;
iapBranchPersonnelParentId: number;
branchPersonnelVos: IapPersonnelVo[];
iapPersonnelResVo1: IapPersonnelResVo;
iapPersonnelResVo2: IapPersonnelResVo;

}
