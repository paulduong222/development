import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { AccountCodeVo } from './account-code-vo';

export interface IncidentAccountCodeVo extends AbstractVo {
    accountCodeVo: AccountCodeVo;
    incidentId: number;
    incidentVo: IncidentVo;
    overrideAccountCodeVo: AccountCodeVo;
    defaultFlag: boolean;
    deletable: boolean;
    accrualAccountCode: string;
    accrualIncidentAccountCodeVo: IncidentAccountCodeVo;
}
