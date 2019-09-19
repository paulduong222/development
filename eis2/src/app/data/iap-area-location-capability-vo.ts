import { AbstractVo } from './abstract-vo';

export interface IapAreaLocationCapabilityVo extends AbstractVo {
    iapForm206Id: number;
    branchName: string;
    divisionName: string;
    groupName: string;
    emsResponders: string;
    capability: string;
    ambAirEta: string;
    ambGroundEta: string;
    approvedHelispot: string;
    latitude: string;
    longitude: string;
    emergencyChannel: string;
    availEquipment: string;
    isBlankLine: boolean;
    positionNum: number;
}
