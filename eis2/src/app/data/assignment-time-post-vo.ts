import { AbstractVo } from './abstract-vo';
import { RateClassRateVo } from './rate-class-rate-vo';
import { IncidentAccountCodeVo } from './incident-account-code-vo';
import { KindVo } from './kind-vo';
import { EmploymentTypeEnum } from './enums/employment-type-enum.enum';
import { ContractorRateVo } from './contractor-rate-vo';
import { DateTransferVo } from './date-transfer-vo';
import { SpecialPayVo } from './special-pay-vo';

export interface AssignmentTimePostVo extends AbstractVo {
rateClassRateVo: RateClassRateVo;
employmentType: EmploymentTypeEnum;
incidentAccountCodeVo: IncidentAccountCodeVo;
kindVo: KindVo;
refContractorRateVo: ContractorRateVo;
specialPayVo: SpecialPayVo;
postStartDateVo: DateTransferVo;
postStartTime: string;
postStopDateVo: DateTransferVo;
postStopTime: string;
otherRate: number;
rateType: string;
unitOfMeasure: string;
rateAmount: number;
guaranteeAmount: number;
description: string;
assignmentTimeId: number;
isHalfRate: boolean;
quantity: number;
training: boolean;
returnTravelStartOnly: boolean;
primaryPosting: boolean;
specialPosting: boolean;
guaranteePosting: boolean;
invoicedAmount: number;
timeInvoiceVos: any[];
lastInvoiceDate: Date;
contractorPostType: string;
postStartDateString: string;
postStopDateString: string;

}
