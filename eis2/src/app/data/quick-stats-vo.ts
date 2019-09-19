import { QuickStatsResourceVo } from './quick-stats-resource-vo';
import { QuickStatsResourceTypeVo } from './quick-stats-resource-type-vo';
import { QuickStatsTotalsVo } from './quick-stats-totals-vo';

export interface QuickStatsVo {
    incidentId: number;
    incidentGroupId: number;
    quickStatsResourceVos: QuickStatsResourceVo[];
    quickStatsResourceTypeVos: QuickStatsResourceTypeVo[];
    qsTotalsVo: QuickStatsTotalsVo;

}
