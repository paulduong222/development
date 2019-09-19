import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IapAircraftVo } from './iap-aircraft-vo';
import { IapAircraftFrequencyVo } from './iap-aircraft-frequency-vo';
import { IapAircraftTaskVo } from './iap-aircraft-task-vo';
import { IapPersonnelVo } from './iap-personnel-vo';

export interface IapForm220Vo extends AbstractVo {
    iapPlanId: number;
    sunrise: string;
    sunset: string;
    readyAlertAircraft: string;
    medivac: string;
    newIncident: string;
    altitude: string;
    centralPoint: string;
    remarks: string;
    preparedBy: string;
    preparedByPosition: string;
    preparedByDateVo: DateTransferVo;
    isFormLocked: boolean;
    iapAircraftVos: IapAircraftVo[];
    iapFixedWingVos: IapAircraftVo[];
    iapHelicopterVos: IapAircraftVo[];
    iapAircraftFrequencyVos: IapAircraftFrequencyVo[];
    iapAircraftTaskVos: IapAircraftTaskVo[];
    iapPersonnelVos: IapPersonnelVo[];
    // placeholders for 8 Personnel
    iapPersonnel1: IapPersonnelVo;
    iapPersonnel2: IapPersonnelVo;
    iapPersonnel3: IapPersonnelVo;
    iapPersonnel4: IapPersonnelVo;
    iapPersonnel5: IapPersonnelVo;
    iapPersonnel6: IapPersonnelVo;
    iapPersonnel7: IapPersonnelVo;
    iapPersonnel8: IapPersonnelVo;
    iapPersonnel9: IapPersonnelVo;
    iapPersonnel10: IapPersonnelVo;
    iapPersonnel11: IapPersonnelVo;
    // placeholders for 9 Aircraft Frequencies
    iapAircraftFrequency1: IapAircraftFrequencyVo;
    iapAircraftFrequency2: IapAircraftFrequencyVo;
    iapAircraftFrequency3: IapAircraftFrequencyVo;
    iapAircraftFrequency4: IapAircraftFrequencyVo;
    iapAircraftFrequency5: IapAircraftFrequencyVo;
    iapAircraftFrequency6: IapAircraftFrequencyVo;
    iapAircraftFrequency7: IapAircraftFrequencyVo;
    iapAircraftFrequency8: IapAircraftFrequencyVo;
    iapAircraftFrequency9: IapAircraftFrequencyVo;
    iapAircraftFrequency10: IapAircraftFrequencyVo;
    iapAircraftFrequency11: IapAircraftFrequencyVo;
    iapAircraftFrequency12: IapAircraftFrequencyVo;
    // placeholders for 8 Fixed Wing
    iapFixedWing1: IapAircraftVo;
    iapFixedWing2: IapAircraftVo;
    iapFixedWing3: IapAircraftVo;
    iapFixedWing4: IapAircraftVo;
    iapFixedWing5: IapAircraftVo;
    iapFixedWing6: IapAircraftVo;
    iapFixedWing7: IapAircraftVo;
    iapFixedWing8: IapAircraftVo;
    iapFixedWing9: IapAircraftVo;
    iapFixedWing10: IapAircraftVo;
    iapFixedWing11: IapAircraftVo;
    iapFixedWing12: IapAircraftVo;
}
