import { AbstractVo } from './abstract-vo';
import { AgencyVo } from './agency-vo';
import { IncidentVo } from './incident-vo';
import { IncidentGroupVo } from './incident-group-vo';

export interface OrganizationVo extends AbstractVo {
    organizationType: string;
    name: string;
    unitCode: string;
    dispatchCenter: boolean;
    supplyCache: boolean;
    agencyId: number;
    agencyVo: AgencyVo;
    countrySubAbbreviation: string;
    dispatchCenters: any[];
    standard: boolean;
    active: boolean;
    local: boolean;
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
}
