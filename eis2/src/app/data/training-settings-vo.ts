import { AbstractVo } from './abstract-vo';
import { IncidentVo } from './incident-vo';
import { DropdownData } from 'src/app/data/dropdowndata';

export interface TrainingSettingsVo extends AbstractVo {
    numberOfAcres: number; 
    complexityVo: DropdownData;
    incidentId: number;
    incidentGroupId: number
    incidentVo: IncidentVo;
    fuelTypeVos: DropdownData[];    
}

