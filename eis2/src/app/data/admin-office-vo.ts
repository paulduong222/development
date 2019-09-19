import { AbstractVo } from './abstract-vo';
import { AddressVo } from './address-vo';

export interface AdminOfficeVo extends AbstractVo {
    officeName: string;
    phone: string;
    deletedDate: object;
    addressVo: AddressVo;
    standard: boolean;
    contractorAgreementVos: any[];

}
