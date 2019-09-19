import { DialogueData } from './dialogue-data';
import { IapRemoteCampLocationsVo } from '../iap-remote-camp-locations-vo';

export interface IapRemoteCampLocationData extends DialogueData {
    iapRemoteCampLocationsVo: IapRemoteCampLocationsVo;
    iapRemoteCampLocationsVos: IapRemoteCampLocationsVo[];
}
