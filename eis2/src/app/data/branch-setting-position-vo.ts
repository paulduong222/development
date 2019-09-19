import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';

export interface BranchSettingPositionVo extends AbstractVo {
    branchSettingId: number;
    kindVo: KindVo;
    position: string;
}
