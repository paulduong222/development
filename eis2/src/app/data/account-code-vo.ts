import { AbstractVo } from './abstract-vo';
import { AgencyVo } from './agency-vo';
import { RegionCodeVo } from './region-code-vo';

export interface AccountCodeVo extends AbstractVo {
    accountCode: string;
    agencyVo: AgencyVo;
    regionUnitVo: RegionCodeVo;
}
