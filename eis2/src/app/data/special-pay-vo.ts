import { AbstractVo } from './abstract-vo';

export interface SpecialPayVo extends AbstractVo {
    code: string;
    description: string;
    standard: boolean;
    availableToAd: boolean;
    availableToFed: boolean;
    availableToOther: boolean;
    availableToFedAd: boolean;
    availableToFedAdOther: boolean;
    availableToFedOther: boolean;
    availableToAdOther: boolean;
    ordinalValue: number;
}
