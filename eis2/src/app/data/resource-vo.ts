import { AbstractVo } from './abstract-vo';
import { IncidentResourceVo } from './incident-resource-vo';
import { OrganizationVo } from './organization-vo';
import { IncidentVo } from './incident-vo';
import { AgencyVo } from './agency-vo';
import { KindVo } from './kind-vo';
import { ContractorVo } from './contractor-vo';
import { ContractorAgreementVo } from './contractor-agreement-vo';
import { ResourceMobilizationVo } from './resource-mobilization-vo';
import { ResourceKindVo } from './resource-kind-vo';
import { ResourceClassificationVo } from './resource-classification-vo';

export interface ResourceVo extends AbstractVo {
    resourceName: string;
    firstName: string;
    lastName: string;
    parentResourceVo: ResourceVo;
    permanentResourceVo: ResourceVo;
    parentResourceId: number;
    permanentResourceId: number;
    childResourceVos: ResourceVo[];
    incidentResourceVos: IncidentResourceVo[];
    organizationVo: OrganizationVo;
    organizationId: number;
    primaryDispatchCenterVo: OrganizationVo;
    primaryDispatchCenterId: number;
    resourceMobilizationVos: ResourceMobilizationVo[];
    incidentVos: IncidentVo[];
    resourceClassification: string;
    resourceStatus: string;
    agencyVo: AgencyVo;
    agencyId: number;
    person: boolean;
    contracted: boolean;
    leader: boolean;
    nameOnPictureId: string;
    contactName: string;
    phone: string;
    email: string;
    other1: string;
    other2: string;
    other3: string;
    enabled: boolean;
    permanent: boolean;
    deletedDate: Date;
    numberOfPersonnel: number;
    resourceKindVos: ResourceKindVo[];
    resourceClassificationVo: ResourceClassificationVo;
    leaderType: number;
    component: boolean;
    otherQuals: ResourceKindVo[];
    primaryQual: ResourceKindVo;
    employmentType: {code: string};
    contractorVo: ContractorVo;
    contractorAgreementVo: ContractorAgreementVo;
    rossGroupAssignment: string;
    rossResId: number;
	// the kindVo property is only for convenience access to the primary item
	// code
	// for the resource. all item codes are normally stored in the
	// resourceKindVos
    kindVo: KindVo;
}
