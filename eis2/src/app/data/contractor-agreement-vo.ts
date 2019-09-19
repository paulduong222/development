import { AbstractVo } from './abstract-vo';
import { AdminOfficeVo } from './admin-office-vo';
import { DateTransferVo } from './date-transfer-vo';
import { ContractorVo } from './contractor-vo';
import { ContractorAgreementNumberHistoryVo } from './contractor-agreement-number-history-vo';

export interface ContractorAgreementVo extends AbstractVo {
    adminOfficeVo: AdminOfficeVo;
    adminOfficeId: number;
    agreementNumber: string;
    startDateVo: DateTransferVo;
    endDateVo: DateTransferVo;
    pointOfHire: string;
    contractorVo: ContractorVo;
    enabled: boolean;
    deletedDate: Date;
    contractorAgreementNumberHistoryVo: ContractorAgreementNumberHistoryVo;
}
