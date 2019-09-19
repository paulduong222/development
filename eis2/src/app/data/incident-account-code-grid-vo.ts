import { AbstractVo } from './abstract-vo';

// NOTE: the data fields are differenct than the java version
export interface IncidentAccountCodeGridVo extends AbstractVo {
    defaultFlag: boolean;
    accountCode: string;
    agencyCode: string;
    region: string;
    accrualCode: string;
}
