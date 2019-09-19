import { AbstractVo } from './abstract-vo';
import { CountryCodeSubdivisionVo } from './country-code-subdivision-vo';

export interface AddressVo extends AbstractVo {
    addressLine1: string;
    addressLine2: string;
    city: string;
    postalCode: string;
    countrySubdivisionVo: CountryCodeSubdivisionVo;
}
