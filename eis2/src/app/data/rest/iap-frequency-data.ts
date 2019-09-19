import { DialogueData } from './dialogue-data';
import { IapFrequencyVo } from '../iap-frequency-vo';

export interface IapFrequencyData extends DialogueData {
    iapFrequencyVo: IapFrequencyVo;
    iapFrequencyVos: IapFrequencyVo[];

}
