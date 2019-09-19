import { AbstractVo } from "./abstract-vo";
import { ResourceTrainingVo } from "./resource-training-vo";
import { AddressVo } from "./address-vo";
import { KindVo } from "./kind-vo";
import { OrganizationVo } from "./organization-vo";
import { IncidentResourceVo } from "./incident-resource-vo";

export interface RscTrainingTrainerVo extends AbstractVo {
    resourceTrainingVo: ResourceTrainingVo; 
	addressVo: AddressVo;
	kindVo: KindVo;
	requestNumber: string;
	unitVo: OrganizationVo;
	email: string;
	phone: string;
	resourceName: string;
	status: string;
	incidentResourceVo: IncidentResourceVo;
}