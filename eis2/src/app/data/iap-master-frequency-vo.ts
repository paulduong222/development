import { AbstractVo } from './abstract-vo';
import { IncidentsViewComponent } from '../modules/incidents/incidents-view/incidents-view.component';
import { SystemTypeVo } from './system-type-vo';
import { IncidentVo } from './incident-vo';
import { IncidentGroupVo } from './incident-group-vo';

export interface IapMasterFrequencyVo extends AbstractVo {
    incidentVo: IncidentVo;
    incidentGroupVo: IncidentGroupVo;
    show: boolean;
    systemTypeVo: SystemTypeVo;
    group: string;
    channel: string;
    rfunction: string;
    rx: string;
    tx: string;
    tone: string;
    assignment: string;
    remarks: string;
    channelName: string;
    rxFreq: string;
    rxTone: string;
    txFreq: string;
    txTone: string;
    mode: string;
    positionNum: number;
}
