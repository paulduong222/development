import { DialogueData } from './dialogue-data';
import { IapHospitalVo } from '../iap-hospital-vo';

export interface IapHospitalData extends DialogueData {
    iapHospitalVo: IapHospitalVo;
    iapHospitalVos: IapHospitalVo[];

}
