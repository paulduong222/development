import { AbstractVo } from './abstract-vo';
import { OrganizationVo } from './organization-vo';
import { RateClassVo } from './rate-class-vo';
import { RateClassRateVo } from './rate-class-rate-vo';
import { RateAreaVo } from './rate-area-vo';
import { RateAreaEnum } from './enums/rate-area-enum.enum';

export interface AdPaymentInfoVo extends AbstractVo {
    rateAreaVo: RateAreaVo;
    rateClassRateVo: RateClassRateVo;
    initialEmp: boolean;
    returnTravel: boolean;
    pointOfHire: string;
    pointOfHireOrgVo: OrganizationVo;
    ssn: string;
    ssnVerify: string;
    encryptedSSN: string;
    eci: string;
    deletedDate: Date;
    rateAreaName: RateAreaEnum;
    rateYear: number;
    // placeholder for rateYear
    rateClassVo: RateClassVo;
}
