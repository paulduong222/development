import { AbstractVo } from './abstract-vo';
import { ContractorAgreementVo } from './contractor-agreement-vo';
import { AddressVo } from './address-vo';
import { IncidentVo } from './incident-vo';

export interface ContractorVo extends AbstractVo {
    name: string;
    tin: string;
    verifyTin: string;
    duns: string;
    phone: string;
    fax: string;
    addressVo: AddressVo;
    deletedDate: Date;
    enabled: boolean;
    incidentVos: IncidentVo[];
    contractorAgreementVos: ContractorAgreementVo[];
    actualTin: string;
    incidentVo: IncidentVo;
}
