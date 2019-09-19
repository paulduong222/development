import { AbstractVo } from './abstract-vo';
import { AddressVo } from './address-vo';

export interface TrainingContactVo extends AbstractVo{
    phone: string;
    email: string;
    addressVo: AddressVo;
    incidentResourceId: number;
	requestNumber: string;
	resourceName: string;
	itemCode: string;
	itemDescription: string;
	status: string;
	active: boolean;
	unitId: string;
	unitDescription: string;
}
