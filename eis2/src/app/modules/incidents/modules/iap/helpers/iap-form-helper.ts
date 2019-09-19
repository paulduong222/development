import { IapForm202Vo } from 'src/app/data/iap-form202-vo';
import { IapPrintJobVo } from 'src/app/data/iap-print-job-vo';
import { IapPrintFormVo } from 'src/app/data/iap-print-form-vo';
import { IapPlanPrintOrderVo } from 'src/app/data/iap-plan-print-order-vo';
import { IapForm205Vo } from 'src/app/data/iap-form205-vo';
import { IapFrequencyVo } from 'src/app/data/iap-frequency-vo';
import { DateTransferVo } from 'src/app/data/date-transfer-vo';
import { IapAircraftVo } from 'src/app/data/iap-aircraft-vo';
import { IapAircraftTaskVo } from 'src/app/data/iap-aircraft-task-vo';
import { IapForm220Vo } from 'src/app/data/iap-form220-vo';
import { IapAircraftFrequencyVo } from 'src/app/data/iap-aircraft-frequency-vo';
import { IapPersonnelVo } from 'src/app/data/iap-personnel-vo';
import { IapForm206Vo } from 'src/app/data/iap-form206-vo';
import { IapMedicalAidVo } from 'src/app/data/iap-medical-aid-vo';
import { IapHospitalVo } from 'src/app/data/iap-hospital-vo';
import { IapAreaLocationCapabilityVo } from 'src/app/data/iap-area-location-capability-vo';
import { IapRemoteCampLocationsVo } from 'src/app/data/iap-remote-camp-locations-vo';
import { AddressVo } from 'src/app/data/address-vo';
import { CountryCodeSubdivisionVo } from 'src/app/data/country-code-subdivision-vo';
import { IapForm203Vo } from 'src/app/data/iap-form203-vo';

export class IapFormHelper {

    initPrintJobVo(planId) {
        const vo = <IapPrintJobVo> {
            id: 0
            , iapPlanId: planId
            , formsToPrint: [] as IapPrintFormVo[]
            , ordersToPrint: [] as IapPlanPrintOrderVo[]
            , lockPlan: false
        };

        return vo;
    }

    initIapPrintFormVo() {
        const vo = <IapPrintFormVo> {
            id: 0
            , formType: ''
            , formId: 0
            , position: 0
            , attachmentId: 0
            , isAttachment: false
        };

        return vo;
    }

    initIapPlanPrintOrderVo(planId) {
        const vo = <IapPlanPrintOrderVo> {
            id: 0
            , iapPlanId: planId
            , formType: ''
            , formId: 0
            , position: 0
        };

        return vo;
    }

    initIcs202Vo(planId) {
        const vo = <IapForm202Vo> {
            id: 0,
            iapPlanId: planId,
            preparedBy: '',
            preparedByPosition: '',
            approvedBy: '',
            approvedDate: null,
            approvedDateTime: '',
            approvedDateString: '', // helper
            objectives: '',
            commandEmphasis: '',
            generalSituationalAwareness: '',
            siteSafetyPlanRqrd: false,
            siteSafetyPlanLoc: '',
            isForm202Attached: false,
            isForm203Attached: false,
            isForm204Attached: false,
            isForm205Attached: false,
            isForm205aAttached: false,
            isForm206Attached: false,
            isForm207Attached: false,
            isForm208Attached: false,
            isForm220Attached: false,
            isIncidentMapAttached: false,
            isWeatherForecastAttached: false,
            isOtherFormAttached1: false,
            isOtherFormAttached2: false,
            isOtherFormAttached3: false,
            isOtherFormAttached4: false,
            otherFormName1: '',
            otherFormName2: '',
            otherFormName3: '',
            otherFormName4: '',
            isFormLocked: false,
        };

        return vo;
    }

    initIcs203Vo(planId) {
        const vo = <IapForm203Vo> {
            id: 0
            , iapPlanId: planId
            , preparedBy: ''
            , preparedByPosition: ''
            , preparedDateVo: <DateTransferVo>{dateString: '', timeString: ''}
            , preparedTime: ''
            , isFormLocked: false
            , isNoBranch: false
            , iapPersonnelVos: [] as IapPersonnelVo[]
            , iapCommanderVos: [] as IapPersonnelVo[]
            , iapLogisticsVos: [] as IapPersonnelVo[]
            , iapPlanningVos: [] as IapPersonnelVo[]
            , iapAirOpsVos: [] as IapPersonnelVo[]
            , iapFinanceVos: [] as IapPersonnelVo[]
            , iapOperationsVos: [] as IapPersonnelVo[]
            , iapBranchVos: [] as IapPersonnelVo[]
            , iapAgencyVos: [] as IapPersonnelVo[]
        };

        return vo;
    }

    initIcs205Vo(planId) {
        const vo = <IapForm205Vo> {
            id: 0,
            iapPlanId: planId,
            specialInstruction: '',
            preparedBy: '',
            preparedByPosition: '',
            preparedTime: '',
            preparedDateVo: <DateTransferVo>{},
            isFormLocked: false,
            iapFrequencieVos: [] as IapFrequencyVo[]
        };

        return vo;
    }

    initIcs206Vo(planId) {
        const vo = <IapForm206Vo> {
            id: 0,
            iapPlanId: planId,
            iapAmbulanceVos: [] as IapMedicalAidVo[],
            iapAirAmbulanceVos: [] as IapMedicalAidVo[],
            iapHospitalVos: [] as IapHospitalVo[],
            iapAreaLocationCapabilityVos: [] as IapAreaLocationCapabilityVo[],
            iapRemoteCampLocationsVos: [] as IapRemoteCampLocationsVo[],
            preparedBy: '',
            preparedDateVo: <DateTransferVo>{dateString: '', timeString: ''},
            preparedTime: '',
            reviewedBy: '',
            reviewedDateVo: <DateTransferVo>{dateString: '', timeString: ''},
            reviewedTime: '',
            isFormLocked: false,
        };

        return vo;
    }

    initIcs220Vo(planId) {
        const vo = <IapForm220Vo> {
            id: 0,
            iapPlanId: planId,
            sunrise: '',
            sunset: '',
            readyAlertAircraft: '',
            medivac: '',
            newIncident: '',
            altitude: '',
            centralPoint: '',
            remarks: '',
            preparedBy: '',
            preparedByPosition: '',
            preparedByDateVo: <DateTransferVo>{dateString: '', timeString: ''},
            isFormLocked: false,
            iapAircraftVos: [] as IapAircraftVo[],
            iapFixedWingVos: [] as IapAircraftVo[],
            iapHelicopterVos: [] as IapAircraftVo[],
            iapAircraftFrequencyVos: [] as IapAircraftFrequencyVo[],
            iapAircraftTaskVos: [] as IapAircraftTaskVo[],
            iapPersonnelVos: [] as IapPersonnelVo[]
        };

        return vo;
    }

    initNew205FrequencyVo(form205Id) {
        const vo = <IapFrequencyVo> {
            id: 0,
            iapForm205Id: form205Id,
            radioType: '',
            channel: '',
            sfunction: '',
            frequencyRx: '',
            toneRx: '',
            frequencyTx: '',
            toneTx: '',
            assignment: '',
            remarks: '',
            preparedDate: null,
            zoneGroup: '',
            channelName: '',
            modeType: '',
            masterFreqId: 0,
            isBlankLine: false,
            positionNum: 1,
        };

        return vo;
    }

    initNewIapAircraftVo(form205Id, wingType) {
        const vo = <IapAircraftVo> {
            id: 0,
            iapForm220Id: form205Id,
            wingType: wingType,
            aircraft: '',
            nbrAvailable: 0,
            type: '',
            makeModel: '',
            faaNbr: '',
            base: '',
            baseFax: '',
            available: '',
            availableDateVo: <DateTransferVo>{dateString: '', timeString: ''},
            startTime: '',
            remarks: '',
            isBlankLine: false,
            positionNum: 0,
        };

        return vo;
    }

    initNewIapAircraftTaskVo(form220Id) {
        const vo = <IapAircraftTaskVo> {
            id: 0,
            iapForm220Id: form220Id,
            type: '',
            name: '',
            startTime: '',
            flyFrom: '',
            flyTo: '',
            isBlankLine: false,
            positionNum: 0
        };

        return vo;
    }

    initNewIapMedicalAidVo(form206Id) {
        const vo = <IapMedicalAidVo>{
            id: 0
            , iapForm206Id: form206Id
            , type: ''
            , name: ''
            , location: ''
            , phone: ''
            , paramedics: false
            , serviceLevel: ''
            , addressVo: <AddressVo> {
              id: 0
              , addressLine1: ''
              , addressLine2: ''
              , city: ''
              , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
                id: 0
              }
              , postalCode: ''
            }
            , airType: ''
            , capability: ''
            , emsFrequency: ''
            , lifeSupport: false
            , isBlankLine: false
            , positionNum: 1
         };

         return vo;
    }

    initNewIapHospitalVo(form206Id) {
        const vo = <IapHospitalVo>{
            id: 0
            , iapForm206Id: form206Id
            , name: ''
            , phone: ''
            , addressVo: <AddressVo> {
              id: 0
              , addressLine1: ''
              , addressLine2: ''
              , city: ''
              , countrySubdivisionVo: <CountryCodeSubdivisionVo>{
                id: 0
              }
              , postalCode: ''
            }
            , latitude: ''
            , longitude: ''
            , vhf: ''
            , airTravelTime: ''
            , groundTravelTime: ''
            , levelOfCare: ''
            , trauma: false
            , helipad: false
            , burnCenter: false
            , isBlankLine: false
            , positionNum: 1
         };

         return vo;
    }

    initNewIapAreaLocationCapabilityVo(form206Id) {
        const vo = <IapAreaLocationCapabilityVo>{
            id: 0
            , iapForm206Id: form206Id
            , branchName: ''
            , divisionName: ''
            , groupName: ''
            , emsResponders: ''
            , capability: ''
            , emergencyChannel: ''
            , availEquipment: ''
            , approvedHelispot: ''
            , ambAirEta: ''
            , ambGroundEta: ''
            , latitude: ''
            , longitude: ''
            , isBlankLine: false
            , positionNum: 1
         };

         return vo;
    }

    initNewIapRemoteCampLocationsVo(form206Id) {
        const vo = <IapRemoteCampLocationsVo>{
            id: 0
            , iapForm206Id: form206Id
            , name: ''
            , location: ''
            , pointOfContact: ''
            , emsResponders: ''
            , capability: ''
            , emergencyChannel: ''
            , availableEquipment: ''
            , approvedHelispot: ''
            , ambAirEta: ''
            , ambGroundEta: ''
            , latitude: ''
            , longitude: ''
            , isBlankLine: false
            , positionNum: 1
         };

         return vo;
    }

}
