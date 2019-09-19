import { DialogueData } from './dialogue-data';
import { ContractorRateVo } from '../contractor-rate-vo';
import { WorkPeriodVo } from '../work-period-vo';
import { ContractorRateFilter } from '../filter/contractor-rate-filter';

export interface ContractorRateData extends DialogueData {
    contractorRateVo: ContractorRateVo;
    workPeriodVo: WorkPeriodVo;
    contractorRateFilter: ContractorRateFilter;
}
