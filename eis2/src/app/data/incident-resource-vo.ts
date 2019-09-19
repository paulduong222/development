import { AbstractVo } from './abstract-vo';
import { ResourceVo } from './resource-vo';
import { IncidentVo } from './incident-vo';
import { WorkPeriodVo } from './work-period-vo';
import { CostDataVo } from './cost-data-vo';
import { IncidentPrefsOtherFieldsVo } from './incident-prefs-other-fields-vo';
import { IncidentAccountCodeVo } from './incident-account-code-vo';

export interface IncidentResourceVo extends AbstractVo {
    incidentVo: IncidentVo;
    resourceVo: ResourceVo;
    resNumId: number;
    nameAtIncident: string;
    dailyCostException: string;
    workPeriodVo: WorkPeriodVo;
    incStartDate: Date;
    hasTimeVo: boolean;
    costDataVo: CostDataVo;
    incidentPrefsOtherFieldsVo: IncidentPrefsOtherFieldsVo;
    incidentAccountCodeVos: IncidentAccountCodeVo[];
    // the default for the incident
    defaultIncidentAccountCodeVo: IncidentAccountCodeVo;
}
