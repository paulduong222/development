import { AbstractVo } from './abstract-vo';
export interface AgencyGroupVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
    active: boolean;
    // incidentVo: any;
    // incidentGroupVo: any;
 }
