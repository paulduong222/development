import { ContractorVo } from '../contractor-vo';

export interface ContractorRateFilter {
    supercededByVo: ContractorVo;
    rateType: '';
    unitOfMeasure: '';
    rateAmount: number;
    guaranteeAmount: number;
    description: '';
}
