import { DialogueData } from './dialogue-data';
import { IapPrintJobVo } from '../iap-print-job-vo';

export interface IapPrintJobData extends DialogueData {
    iapPrintJobVo: IapPrintJobVo;
}
