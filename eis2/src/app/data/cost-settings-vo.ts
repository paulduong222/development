import { AbstractVo } from './abstract-vo';

export interface CostSettingsVo extends AbstractVo {
    incidentId: number;
    incidentGroupId: number;
    costDefaultHours: number;
    costDefaultHoursString: string;
    costAutoRun: boolean;
    costAutoRunString: string;
}
