import { AbstractVo } from './abstract-vo';
import { AgencyVo } from './agency-vo';
import { IncidentAccountCodeVo } from './incident-account-code-vo';
import { KindVo } from './kind-vo';
import { IncidentResourceOtherVo } from './incident-resource-other-vo';
import { CostGroupVo } from './cost-group-vo';

export interface ResourceOtherVo extends AbstractVo {
    indVo: KindVo;
    incidentAccountCodeVo: IncidentAccountCodeVo;
    agencyVo: AgencyVo;
    requestNumber: string;
    costDescription: string;
    actualReleaseDate: Date;
    actualReleaseTime: string;
    costGroupVos: CostGroupVo[];
    incidentResourceOtherVos: IncidentResourceOtherVo[];

}
