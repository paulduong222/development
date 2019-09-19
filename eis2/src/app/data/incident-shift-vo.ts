import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';

export interface IncidentShiftVo extends AbstractVo {
    shiftName: string;
    incidentVo: IncidentVo;
    startTime: string;
    endTime: string;
    fontVo: object;
}
