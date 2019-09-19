import { AbstractVo } from './abstract-vo';
import { AddressVo } from './address-vo';

export interface IapMedicalAidVo extends AbstractVo {
    iapForm206Id: number;
    type: string;
    name: string;
    location: string;
    phone: string;
    paramedics: boolean;
    serviceLevel: string;
    addressVo: AddressVo;
    airType: string;
    capability: string;
    emsFrequency: string;
    lifeSupport: boolean;
    isBlankLine: boolean;
    positionNum: number;
}
