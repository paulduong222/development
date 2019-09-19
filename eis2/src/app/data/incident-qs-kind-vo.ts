import { AbstractVo } from './abstract-vo';
import { KindVo } from './kind-vo';

export interface IncidentQsKindVo extends AbstractVo {
    incidentId: number;
    kindVo: KindVo;
}
