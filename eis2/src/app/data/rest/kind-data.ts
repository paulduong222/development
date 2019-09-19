import { KindVo } from '../kind-vo';
import { DialogueData } from './dialogue-data';

export interface KindData extends DialogueData {
    kindVo: KindVo;
    kindVos: KindVo[];
}
