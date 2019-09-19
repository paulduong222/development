import { AbstractVo } from './abstract-vo';
import { CostGroupVo } from './cost-group-vo';
import { CostGroupAgDaySharePctVo } from './cost-group-ag-day-share-pct-vo';

export interface CostGroupAgencyDayShareVo extends AbstractVo {
    costGroupVo: CostGroupVo;
    agencyShareDate: Date;
    costGroupAgencyDaySharePercentageVos: CostGroupAgDaySharePctVo[];

}
