import { ContractorVo } from '../contractor-vo';
import { DialogueData } from './dialogue-data';

export interface ContractorData extends DialogueData {
    contractorVo: ContractorVo;
}
