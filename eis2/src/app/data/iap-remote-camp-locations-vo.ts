import { AbstractVo } from './abstract-vo';

export interface IapRemoteCampLocationsVo extends AbstractVo {
    iapForm206Id: number;
    name: string;
    location: string;
    pointOfContact: string;
    emsResponders: string;
    capability: string;
    ambAirEta: string;
    ambGroundEta: string;
    approvedHelispot: string;
    latitude: string;
    longitude: string;
    emergencyChannel: string;
    availableEquipment: string;
    isBlankLine: boolean;
    positionNum: number;
}
