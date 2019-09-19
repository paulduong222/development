import { DialogueData } from './dialogue-data';
import { IapAreaLocationCapabilityVo } from '../iap-area-location-capability-vo';

export interface IapAreaLocationCapabilityData extends DialogueData {
    iapAreaLocationCapabilityVo: IapAreaLocationCapabilityVo;
    iapAreaLocationCapabilityVos: IapAreaLocationCapabilityVo[];
}
