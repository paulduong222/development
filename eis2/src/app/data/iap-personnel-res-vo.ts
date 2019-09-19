import { AbstractVo } from './abstract-vo';

export interface IapPersonnelResVo extends AbstractVo {
    iapPersonnelId: number;
    name: string;
    positionNum: number;
    isTrainee: boolean;
}
