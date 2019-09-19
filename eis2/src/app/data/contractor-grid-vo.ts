import { AbstractVo } from './abstract-vo';

export interface ContractorGridVo extends AbstractVo {
    name: string;
    duns: string;
    phone: string;
    addressLine1: string;
    city: string;
    countrySubAbbreviation: string;
    postalCode: string;
    enabled: boolean;
    deletable: boolean;
    incidentId: number;
}
