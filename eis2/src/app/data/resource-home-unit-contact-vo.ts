import { AbstractVo } from './abstract-vo';
import { IncidentResourceVo } from './incident-resource-vo';
import { OrganizationVo } from './organization-vo';
import { AddressVo } from './address-vo';

export interface ResourceHomeUnitContactVo extends AbstractVo {  
    incidentResourceVo: IncidentResourceVo;
    contactName: string;
    unitVo: OrganizationVo;
    addressVo: AddressVo;
    phone: string;
    email: string;
}