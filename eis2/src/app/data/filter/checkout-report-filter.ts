export interface CheckoutReportFilter {
    incidentId: any;
    incidentGroupId: any;
    resourceIds: any[];
    includeSTTFComponents: Boolean;
    sortBy: string;
    sorts: String[];
    markCheckOutFormPrinted: Boolean;
    markIncludeBox12: Boolean;
}