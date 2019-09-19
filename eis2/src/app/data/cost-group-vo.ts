import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { ResourceVo } from './resource-vo';
import { IncidentShiftVo } from './incident-shift-vo';
import { ResourceOtherVo } from './resource-other-vo';
import { CostGroupAgencyDayShareVo } from './cost-group-agency-day-share-vo';
import { CostGroupDefAgDaySharePctVo } from './cost-group-def-ag-day-share-pct-vo';

export interface CostGroupVo extends AbstractVo {
    incidentVo: IncidentVo;
    costGroupName: string;
    description: string;
    startDate: Date;
    incidentShiftVo: IncidentShiftVo;
    resourceVos: ResourceVo[];
    resourceOtherVos: ResourceOtherVo[];
    costGroupAgencyDayShareVos: CostGroupAgencyDayShareVo[];
    costGroupDefaultAgencyDaySharePercentageVos: CostGroupDefAgDaySharePctVo[];
}
