import { AbstractVo } from './abstract-vo';

export interface ContractorRateVo extends AbstractVo {
    supercededByVo: ContractorRateVo;
    rateType: string;
    unitOfMeasure: string;
    rateAmount: number;
    guaranteeAmount: number;
    description: string;
    displayName: string;
}
