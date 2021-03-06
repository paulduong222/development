import { DateTransferVo } from './date-transfer-vo';

export interface IncidentResourceGridVo {
    hierachalGroupField: any[];
    groupTag: string;
    resourceId: number;
    parentResourceId: number;
    incidentId: number;
    incidentResourceId: number;
    workPeriodId: number;
    assignmentId: number;
    workAreaId: number;
    workAreaIncidentId: number;
    isPerson: boolean;
    requestNumber: string;
    requestNumberSortValue: string;
    resourceName: string;
    lastName: string;
    firstName: string;
    itemCode: string;
    itemName: string;
    trainee: boolean;
    assignmentStatus: string;
    agency: string;
    unitId: string;
    actualReleaseDate: Date;
    actualReleaseTime: string;
    actualReleaseDateVo: DateTransferVo;
    incidentNumber: string;
    incidentName: string;
    ciCheckInDate: Date;
    ciCheckInTime: string;
    ciCheckInDateVo: DateTransferVo;
    numberOfPersonnel: number;
    resourceClassification: string;
    dmTentativeDemobCity: string;
    dmTentativeDemobState: string;
    ciArrivalJetPort: string;
    dmTravelMethod: string;
    ciTravelMethod: string;
    mobilizationStartDate: Date;
    ciFirstWorkDate: Date;
    ciFirstWorkDateVo: DateTransferVo;
    plansRemarks: string;
    leader: boolean;
    leaderName: string;
    leaderLastName: string;
    leaderFirstName: string;
    leaderIsTrainee: boolean;
    other1: string;
    other2: string;
    other3: string;
    requestCategory: string;
    ciLengthAtAssignment: number;
    sectionDescription: string;
    subSectionDescription: string;
    vehicleId: string;
    dmTentativeDemobDate: Date;
    dmTentativeDemobDateVo: DateTransferVo;
    accountingCode: string;
    cellPhoneNumber: string;
    strikeTeamTaskForce: string;
    dmReassignable: boolean;
    dmCheckoutFormPrinted: boolean;
    dmPlanningDispatchNotified: boolean;
    dmReleaseDispatchNotified: boolean;
    dmRestOvernight: boolean;
    dmReleaseRemarks: string;
    dmEstimatedArrivalDate: Date;
    dmEstimatedArrivalTime: string;
    dmEstimatedArrivalDateVo: DateTransferVo;
    dmAirDispatchNotified: boolean;
    airRemarks: string;
    hoursToAirport: number;
    minutesToAirport: number;
    itineraryReceived: boolean;
    nameOnPictureId: string;
    departFromJetport: string;
    overnightRemarks: string;
    departmentCode: string;
    departmentSubCode: string;
    departmentDesc: string;
    departmentSubDesc: string;
    assignmentTimeId: number;
    leaderType: string;
    permanent: boolean;
    enabled: boolean;
    contracted: boolean;
    resourceStatus: string;
    resourceAgencyName: string;
    resourceAgencyCode: string;
    resourceDeletedDate: Date;
    resourceOrganizationName: string;
    resourceUnitCode: string;
    nameAtIncident: string;
    ciPrePlanningRemarks: string;
    dmTentativeReleaseDate: Date;
    dmTentativeReleaseTime: string;
    dmTentativeReleaseDateVo: DateTransferVo;
    kindCode: string;
    kindDescription: string;
    strikeTeam: boolean;
    deletable: boolean;
    dmReturnTravelMethod: string;
    dmTentativeReleaseRemarks: string;
    specialInstructions: string;
    supplies: boolean;
    employmentType: string;
    vinName: string;
    contractorName: string;
    contractorAgreementNumber: string;
    paymentAgency: string;
    accrualCode: string;
    assignDate: Date;
    assignDateVo: DateTransferVo;
    children: any[];
    isLineOverhead: boolean;
    subGroupCategoryCode: string;
    costRemarks: string;
    hasTnspRecord: boolean;
}
