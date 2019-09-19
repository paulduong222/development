import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IapFrequencyVo } from './iap-frequency-vo';

export interface IapForm205Vo extends AbstractVo {
    iapPlanId: number;
    specialInstruction: string;
    preparedBy: string;
    preparedByPosition: string;
    preparedTime: string;
    preparedDateVo: DateTransferVo;
    isFormLocked: boolean;
    iapFrequencieVos: IapFrequencyVo[];
}
