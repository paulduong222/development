import { AbstractVo } from './abstract-vo';
import { AgencyVo } from './agency-vo';
import { CostGroupAgencyDayShareVo } from './cost-group-agency-day-share-vo';

export interface CostGroupAgDaySharePctVo extends AbstractVo {
    costGroupAgencyDayShareVo: CostGroupAgencyDayShareVo;
    agencyVo: AgencyVo;
    percentage: number;

}
