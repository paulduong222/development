import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';
import { JetPortVo } from './jet-port-vo';
import { ResourceMobilizationVo } from './resource-mobilization-vo';
import { AssignmentVo } from './assignment-vo';
import { IncidentAccountCodeVo } from './incident-account-code-vo';
import { CountryCodeSubdivisionVo } from './country-code-subdivision-vo';
import { TravelMethodVo } from './travel-method-vo';
import { AirTravelVo } from './air-travel-vo';
import { WorkPeriodOvernightStayInfoVo } from './work-period-overnight-stay-info-vo';
import { WorkPeriodQuestionValueVo } from './work-period-question-value-vo';

export interface WorkPeriodVo extends AbstractVo {
    assignmentVos: AssignmentVo[];
    currentAssignmentVo: AssignmentVo;
    ciArrivalJetPortVo: JetPortVo;
    ciResourceMobilizationVo: ResourceMobilizationVo;
    ciRentalLocation: string;
    ciCheckInDateVo: DateTransferVo;
    ciFirstWorkDateVo: DateTransferVo;
    ciPrePlanningRemarks: string;
    ciLengthAtAssignment: number;
   ciTentativeArrivalDateVo: DateTransferVo;
    ciTravelMethodVo: TravelMethodVo;
    dmTentativeDemobCity: string;
    dmTentativeDemobStateVo: CountryCodeSubdivisionVo;
    dmReleaseDateVo: DateTransferVo;
    dmTentativeArrivalDateVo: DateTransferVo;
    dmTentativeReleaseDateVo: DateTransferVo;
    dmReAssignable: boolean;
    dmRestOvernight: boolean;
    dmReleaseDispatchNotified: boolean;
    dmPlanningDispatchNotified: boolean;
    dmCheckoutFormPrinted: boolean;
    dmReleaseRemarks: string;
    dmPlanningRemarks: string;
    dmAirTravelVo: AirTravelVo;
    dmTravelMethodVo: TravelMethodVo;
    checkInQuestions: any; // WorkPeriodQuestionValueVo;
    airTravelQuestions: WorkPeriodQuestionValueVo[]; // WorkPeriodQuestionValueVo;
    workPeriodQuestionValueVos: any; // WorkPeriodQuestionValueVo;
    workPeriodOvernightStayInfoVos: WorkPeriodOvernightStayInfoVo[];
    wpDefaultIncidentAccountCodeVo: IncidentAccountCodeVo;

}
