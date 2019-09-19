import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { IapPersonnelVo } from './iap-personnel-vo';

export interface IapForm203Vo extends AbstractVo {
    iapPlanId: number;
    preparedBy: string;
    preparedByPosition: string;
    preparedDateVo: DateTransferVo;
    preparedTime: string;
    isFormLocked: boolean;
    isNoBranch: boolean;
    iapPersonnelVos: IapPersonnelVo[];
    iapCommanderVos: IapPersonnelVo[];
    iapLogisticsVos: IapPersonnelVo[];
    iapPlanningVos: IapPersonnelVo[];
    iapAirOpsVos: IapPersonnelVo[];
    iapFinanceVos: IapPersonnelVo[];
    iapOperationsVos: IapPersonnelVo[];
    iapBranchVos: IapPersonnelVo[];
    iapAgencyVos: IapPersonnelVo[];
}
