import { AbstractVo } from './abstract-vo';

export interface AdminOfficeGridVo extends AbstractVo {
    officeName: string;
    addressLine1: string;
    addressLine2: string;
    city: string;
    countrySubdivision: string;
    postalCode: string;
    phone: string;
    deletedDate: object;
    deleteable: boolean;
}
