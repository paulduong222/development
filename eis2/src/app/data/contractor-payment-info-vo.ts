import { AbstractVo } from './abstract-vo';
import { ContractorVo } from './contractor-vo';
import { DateTransferVo } from './date-transfer-vo';
import { ContractorAgreementVo } from './contractor-agreement-vo';
import { ContractorRateVo } from './contractor-rate-vo';

export interface ContractorPaymentInfoVo extends AbstractVo {
    contractorVo: ContractorVo;
    vinName: string;
    desc1: string;
    desc2: string;
    contractorAgreementVo: ContractorAgreementVo;
    hiredDateVo: DateTransferVo;
    operation: boolean;
    supplies: boolean;
    withdrawn: boolean;
    deletedDate: Date;
    pointOfHire: string;
    contractorRateVos: ContractorRateVo[];
}
