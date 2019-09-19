import { AbstractVo } from './abstract-vo';
import { CountryCodeSubdivisionVo } from './country-code-subdivision-vo';

export interface WorkPeriodOvernightStayInfoVo extends AbstractVo {
    countrySubdivisionVo: CountryCodeSubdivisionVo;
    city: string;
    estimatedArrivalDate: Date;
    lengthOfStay: number;
    remarks: string;
}
