import { AbstractVo } from './abstract-vo';
import { AddressVo } from './address-vo';

export interface IapHospitalVo extends AbstractVo {
    iapForm206Id: number;
    name: string;
    addressVo: AddressVo;
    latitude: string;
    longitude: string;
    vhf: string;
    airTravelTime: string;
    groundTravelTime: string;
    phone: string;
    levelOfCare: string;
    trauma: boolean;
    helipad: boolean;
    burnCenter: boolean;
    isBlankLine: boolean;
    positionNum: number;
}
