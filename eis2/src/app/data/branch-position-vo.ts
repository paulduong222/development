import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';

// dan: this one does not extends AbstractVo ? check Java file
export interface BranchPositionVo {
    isNew: false;
    osition: string;
    kindVos: KindVo[];
}
