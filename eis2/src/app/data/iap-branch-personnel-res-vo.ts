import { AbstractVo } from './abstract-vo';

export interface IapBranchPersonnelResVo extends AbstractVo {
    iapBranchPersonnelId: number;
    name: string;
    positionNum: number;
    isTrainee: boolean;
}
