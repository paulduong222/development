import { KindVo } from "../kind-vo";

export interface QualificationsReportFilter {
    traineesOnly: boolean;
    excludeTrainees: boolean;
    incidentId: any;
    incidentGroupId: any;
    selectedKinds: KindVo[];
}
