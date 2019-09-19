import { AbstractVo } from './abstract-vo';
import { CountryCodeVo } from './country-code-vo';

export interface CountryCodeSubdivisionVo extends AbstractVo {
    standard: boolean;
    countryCodeVo: CountryCodeVo;
    countryCdId: number;
    countrySubAbbreviation: string;
    countrySubName: string;
 }
