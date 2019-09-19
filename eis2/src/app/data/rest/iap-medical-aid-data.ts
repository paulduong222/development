import { DialogueData } from './dialogue-data';
import { IapMedicalAidVo } from '../iap-medical-aid-vo';

export interface IapMedicalAidData extends DialogueData {
    iapMedicalAidVo: IapMedicalAidVo;
    iapMedicalAidVos: IapMedicalAidVo[];
}
