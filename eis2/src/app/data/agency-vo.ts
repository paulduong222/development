import { AbstractVo } from './abstract-vo';
import { AgencyGroupVo } from './agency-group-vo';
import { RateGroupVo } from './rate-group-vo';
import { IncidentVo } from './incident-vo';
import { IncidentGroupVo } from './incident-group-vo';

export interface AgencyVo extends AbstractVo {
    agencyCd: string;
    agencyNm: string;
    theAgencyType: string;
    deletedDate: Date;
    standard: boolean;
    state: boolean;
    agencyGroupVo: AgencyGroupVo;
    rateGroupVo: RateGroupVo;
    active: boolean;
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
 }
