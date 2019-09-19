import { AbstractVo } from './abstract-vo';

export interface CountryCodeVo extends AbstractVo {
    countryAbbreviation: string;
    countryName: string;
 }
