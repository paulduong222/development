import { AbstractVo } from './abstract-vo';
import { AddressVo } from './address-vo';
import { AdPaymentInfoVo } from './ad--payment-info-vo';
import { ContractorPaymentInfoVo } from './contractor-payment-info-vo';
import { EmploymentTypeEnum } from './enums/employment-type-enum.enum';
import { AssignmentTimePostVo } from './assignment-time-post-vo';

export interface AssignmentTimeVo extends AbstractVo {
    employmentType: EmploymentTypeEnum;
    mailingAddressVo: AddressVo;
    phone: string;
    fax: string;
    ofRemarks: string;
    otherDefaultRate: number;
    deletedDate: Date;
    adPaymentInfoVo: AdPaymentInfoVo;
    contractorPaymentInfoVo: ContractorPaymentInfoVo;
    assignmentTimePostVos: AssignmentTimePostVo[];
    hiringUnitName: string;
    hiringUnitPhone: string;
    hiringUnitFax: string;
}
