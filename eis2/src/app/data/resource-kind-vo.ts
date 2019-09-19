import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';
import { ResourceVo } from './resource-vo';

export interface ResourceKindVo extends AbstractVo {
    resourceVo: ResourceVo;
    resourceId: number;
    kindVo: KindVo;
    kindId: number;
    training: boolean;
    primary: boolean;
}
