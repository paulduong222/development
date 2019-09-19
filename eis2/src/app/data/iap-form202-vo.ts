import { AbstractVo } from './abstract-vo';

export interface IapForm202Vo extends AbstractVo {
    iapPlanId: number;
    preparedBy: string;
    preparedByPosition: string;
    approvedBy: string;
    approvedDate: Date;
    approvedDateTime: string;
    approvedDateString: string; // helper
    objectives: string;
    commandEmphasis: string;
    generalSituationalAwareness: string;
    siteSafetyPlanRqrd: boolean;
    siteSafetyPlanLoc: string;
    isForm202Attached: boolean;
    isForm203Attached: boolean;
    isForm204Attached: boolean;
    isForm205Attached: boolean;
    isForm205aAttached: boolean;
    isForm206Attached: boolean;
    isForm207Attached: boolean;
    isForm208Attached: boolean;
    isForm220Attached: boolean;
    isIncidentMapAttached: boolean;
    isWeatherForecastAttached: boolean;
    isOtherFormAttached1: boolean;
    isOtherFormAttached2: boolean;
    isOtherFormAttached3: boolean;
    isOtherFormAttached4: boolean;
    otherFormName1: string;
    otherFormName2: string;
    otherFormName3: string;
    otherFormName4: string;
    isFormLocked: boolean;
}
