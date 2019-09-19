import { IncidentResourceVo } from 'src/app/data/incident-resource-vo';
import { IncidentVo } from 'src/app/data/incident-vo';
import { WorkPeriodVo } from 'src/app/data/work-period-vo';
import { OrganizationVo } from 'src/app/data/organization-vo';
import { AgencyVo } from 'src/app/data/agency-vo';
import { JetPortVo } from 'src/app/data/jet-port-vo';
import { AssignmentVo } from 'src/app/data/assignment-vo';
import { ResourceVo } from 'src/app/data/resource-vo';
import { IncidentAccountCodeVo } from 'src/app/data/incident-account-code-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { ResourceMobilizationVo } from 'src/app/data/resource-mobilization-vo';
import { TravelMethodVo } from 'src/app/data/travel-method-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { CostDataVo } from 'src/app/data/cost-data-vo';
import { IncidentPrefsOtherFieldsVo } from 'src/app/data/incident-prefs-other-fields-vo';
import { ResourceKindVo } from 'src/app/data/resource-kind-vo';
import { ResourceClassificationVo } from 'src/app/data/resource-classification-vo';
import { ContractorVo } from 'src/app/data/contractor-vo';
import { ContractorAgreementVo } from 'src/app/data/contractor-agreement-vo';
import { KindVo } from 'src/app/data/kind-vo';
import { AirTravelVo } from 'src/app/data/air-travel-vo';
import { AccrualCodeVo } from 'src/app/data/accrual-code-vo';
import { AssignmentTimeVo } from 'src/app/data/assignment-time-vo';
import { WorkPeriodOvernightStayInfoVo } from 'src/app/data/work-period-overnight-stay-info-vo';
import { AdPaymentInfoVo } from 'src/app/data/ad--payment-info-vo';
import { RateClassRateVo } from 'src/app/data/rate-class-rate-vo';
import { ContractorPaymentInfoVo } from 'src/app/data/contractor-payment-info-vo';
import { ContractorRateVo } from 'src/app/data/contractor-rate-vo';

export class IncidentResourceHelper {

    initNewIncidentResourceVo(): IncidentResourceVo {
        const irVo: IncidentResourceVo = <IncidentResourceVo>{
            id: 0
            , incidentVo: <IncidentVo>{id: 0}
            , resourceVo: <ResourceVo>{
                id: 0
                , resourceName: ''
                , firstName: ''
                , lastName: ''
                , parentResourceVo: <ResourceVo>{}
                , parentResourceId: 0
                , childResourceVos: [] as ResourceVo[]
                , incidentResourceVos: [] as IncidentResourceVo[]
                , organizationVo: <OrganizationVo>{}
                , organizationId: 0
                , resourceMobilizationVos: [] as ResourceMobilizationVo[]
                , incidentVos: [] as IncidentVo[]
                , agencyVo: <AgencyVo> {}
                , agencyId: 0
                , person: false
                , contracted: false
                , leader: false
                , nameOnPictureId: ''
                , contactName: ''
                , phone: ''
                , email: ''
                , other1: ''
                , other2: ''
                , other3: ''
                , enabled: true
                , permanent: false
                , deletedDate: null
                , numberOfPersonnel: 0
                , resourceKindVos: [] as ResourceKindVo[]
                , resourceClassificationVo: <ResourceClassificationVo>{}
                , leaderType: 0
                , component: false
                , otherQuals: [] as ResourceKindVo[]
                , primaryQual: <ResourceKindVo>{}
                // , employmentType: {code: ''}
                , contractorVo: <ContractorVo>{}
                , contractorAgreementVo: <ContractorAgreementVo>{}
                , rossResId: 0
                , kindVo: <KindVo>{}
            }
            , workPeriodVo: <WorkPeriodVo>{
                id: 0
                , assignmentVos: [] as AssignmentVo[]
                , currentAssignmentVo: <AssignmentVo>{
                    id: 0
                    , requestNumber: ''
                    , assignmentStatusVo: {id: 0, code: ''}
                    , kindVo: {id: 0, code: '', description: ''}
                    , assignmentTimeVo: <AssignmentTimeVo> {
                        id: 0
                        , employmentType: null
                        , otherDefaultRate: 0
                        , hiringUnitName: ''
                        , hiringUnitPhone: ''
                        , hiringUnitFax: ''
                        , ofRemarks: ''
                        , contractorPaymentInfoVo: <ContractorPaymentInfoVo>{
                            id: 0
                            , contractorVo: <ContractorVo>{
                                id: 0
                            }
                            , vinName: ''
                            , desc1: ''
                            , desc2: ''
                            , contractorAgreementVo: <ContractorAgreementVo>{
                                id: 0
                            }
                            , hiredDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                            , operation: false
                            , supplies: false
                            , withdrawn: false
                            , deletedDate: null
                            , pointOfHire: ''
                            , contractorRateVos: [] as ContractorRateVo[]
                        }
                        , adPaymentInfoVo: <AdPaymentInfoVo>{
                            id: 0
                            , eci: ''
                            , rateClassRateVo: <RateClassRateVo>{
                                id: 0
                                , area: ''
                                , rate: 0
                                , rateYear: 0
                                , rateClassName: ''
                                , rateClassCode: ''
                            }
                            , initialEmp: false
                            , returnTravel: false
                            , pointOfHire: ''
                            , pointOfHireOrgVo: <OrganizationVo>{
                                id: 0
                            }
                        }
                    }
                }
                , ciArrivalJetPortVo: <JetPortVo> {}
                , ciResourceMobilizationVo: <ResourceMobilizationVo>{
                    resourceVo: <ResourceVo>{id: 0}
                    , startDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                }
                , ciRentalLocation: ''
                , ciCheckInDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , ciFirstWorkDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , ciPrePlanningRemarks: ''
                , ciLengthAtAssignment: 0
                , ciTentativeArrivalDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , ciTravelMethodVo: <TravelMethodVo> {id: 0}
                , dmTentativeDemobCity: ''
                , dmTentativeDemobStateVo: <CountryCodeSubdivisionVo> {id: 0}
                , dmReleaseDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , dmTentativeArrivalDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , dmTentativeReleaseDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , dmReAssignable: false
                , dmRestOvernight: false
                , dmReleaseDispatchNotified: false
                , dmPlanningDispatchNotified: false
                , dmCheckoutFormPrinted: false
                , dmReleaseRemarks: ''
                , dmPlanningRemarks: ''
                , dmAirTravelVo: <AirTravelVo>{
                    id: 0
                    , jetPortVo: <JetPortVo>{id: 0}
                    , airline: ''
                    , dispatchNotified: false
                    , hoursToAirport: 0
                    , minutesToAirport: 0
                    , leaveTime: ''
                    , flightNumber: ''
                    , flightTime: ''
                    , remarks: ''
                    , itineraryReceived: false
                }
                , dmTravelMethodVo: <TravelMethodVo> {id: 0}
                , checkInQuestions: []
                , airTravelQuestions: []
                , workPeriodQuestionValueVos: []
                , workPeriodOvernightStayInfoVos: [] as WorkPeriodOvernightStayInfoVo[]
                , wpDefaultIncidentAccountCodeVo: <IncidentAccountCodeVo> {}
            }
            , resNumId: 0
            , nameAtIncident: ''
            , dailyCostException: ''
            , incStartDate: null
            , hasTimeVo: false
            , costDataVo: <CostDataVo>{
                id: 0
                , accrualCodeVo: <AccrualCodeVo>{id: 0}
                , accrualLocked: false
                , useAccrualsOnly: false
                , generateCosts: false
                , paymentAgencyVo: <AgencyVo>{id: 0, agencyCd: ''}
                , assignDateVo: <DateTransferVo>{dateString: '', timeString: ''}
                , costOther1: ''
                , costOther2: ''
                , costOther3: ''
                , costRemarks: ''
            }
            , incidentPrefsOtherFieldsVo: <IncidentPrefsOtherFieldsVo>{}
            , defaultIncidentAccountCodeVo: <IncidentAccountCodeVo>{}
        };

        return irVo;
    }

}
