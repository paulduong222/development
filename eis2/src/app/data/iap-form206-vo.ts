import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IapMedicalAidVo } from './iap-medical-aid-vo';
import { IapHospitalVo } from './iap-hospital-vo';
import { IapAreaLocationCapabilityVo } from './iap-area-location-capability-vo';
import { IapRemoteCampLocationsVo } from './iap-remote-camp-locations-vo';

export interface IapForm206Vo extends AbstractVo {
    iapPlanId: number;
    iapAmbulanceVos: IapMedicalAidVo[];
    iapAirAmbulanceVos: IapMedicalAidVo[];
    iapHospitalVos: IapHospitalVo[];
    iapAreaLocationCapabilityVos: IapAreaLocationCapabilityVo[];
    iapRemoteCampLocationsVos: IapRemoteCampLocationsVo[];
    preparedBy: string;
    preparedDateVo: DateTransferVo;
    preparedTime: string;
    reviewedBy: string;
    reviewedDateVo: DateTransferVo;
    reviewedTime: string;
    isFormLocked: boolean;
}
