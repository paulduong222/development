import { AbstractVo } from './abstract-vo';
import { BranchSettingPositionVo } from './branch-setting-position-vo';
import { BranchPositionVo } from './branch-position-vo';

export interface BranchSettingVo extends AbstractVo {
    incidentId: number;
    incidentGroupId: number;
    branchName: string;
    positionNum: number;
    branchSettingPositionVos: BranchSettingPositionVo[];
    branchPositionVos: BranchPositionVo[];
}
