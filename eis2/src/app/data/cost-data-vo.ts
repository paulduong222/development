import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { AgencyVo } from './agency-vo';
import { AccrualCodeVo } from './accrual-code-vo';
import { CostGroupVo } from './cost-group-vo';
import { IncidentShiftVo } from './incident-shift-vo';

export interface CostDataVo extends AbstractVo {
    accrualCodeVo: AccrualCodeVo;
    paymentAgencyVo: AgencyVo;
    assignDateVo: DateTransferVo;
    accrualLocked: boolean;
    useAccrualsOnly: boolean;
    generateCosts: boolean;
    generateCostsSys: boolean;
    costRemarks: string;
    costOther1: string;
    costOther2: string;
    costOther3: string;
    defaultCostGroupVo: CostGroupVo;
    defaultIncidentShiftVo: IncidentShiftVo;

}
