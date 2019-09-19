import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { IncidentGroupVo } from './incident-group-vo';
import { CountryCodeSubdivisionVo } from './country-code-subdivision-vo';

export interface JetPortVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
    countryCodeSubdivisionVo: CountryCodeSubdivisionVo;
    active: boolean;
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
}
