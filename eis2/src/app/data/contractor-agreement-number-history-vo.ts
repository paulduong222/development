import { AbstractVo } from './abstract-vo';
import { ContractorAgreementVo } from './contractor-agreement-vo';

export interface ContractorAgreementNumberHistoryVo extends AbstractVo {
    contractorAgreementVo: ContractorAgreementVo;
    contractorAgreementId: number;
    reasonText: string;
    newAgreementNumber: string;
    oldAgreementNumber: string;
}
