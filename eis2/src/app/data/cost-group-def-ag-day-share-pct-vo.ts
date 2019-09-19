import { AbstractVo } from './abstract-vo';
import { AgencyVo } from './agency-vo';
import { CostGroupVo } from './cost-group-vo';

export interface CostGroupDefAgDaySharePctVo extends AbstractVo {
    costGroupVo: CostGroupVo;
    agencyVo: AgencyVo;
    percentage: number;

}
