import { DialogueData } from './dialogue-data';
import { IapAircraftTaskVo } from '../iap-aircraft-task-vo';

export interface IapAircraftTaskData extends DialogueData {
    iapAircraftTaskVo: IapAircraftTaskVo;
    iapAircraftTaskVos: IapAircraftTaskVo[];
}
