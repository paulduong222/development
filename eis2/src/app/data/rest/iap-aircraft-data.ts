import { DialogueData } from './dialogue-data';
import { IapAircraftVo } from '../iap-aircraft-vo';

export interface IapAircraftData extends DialogueData {
    iapAircraftVo: IapAircraftVo;
    iapAircraftVos: IapAircraftVo[];
}
