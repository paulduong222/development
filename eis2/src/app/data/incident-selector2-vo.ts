export interface IncidentSelector2Vo {
    hierachalGroupField: any[];
    id:number;
    incidentId: number;
    incidentGroupId: number;
    parentGroupId: number;
    name: string;
    nameUnmodified: string;
    type: string;
    incidentNumber: string;
    unitCode: string;
    countryCode: string;
    eventType: string;
    startDate: string;
    agency: string;
    defaultAccountingCode: string;
    defaultAccountingCodeAgency: string;
    isSiteManaged: boolean;
    children: any[];

    other1Label: string;
    other2Label: string;
    other3Label: string;
}
