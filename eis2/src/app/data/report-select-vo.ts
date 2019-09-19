import { AbstractVo } from './abstract-vo';

export interface RequestCategoryVo extends AbstractVo {
    label: string;
    resourceId: any;
    incidentResourceId: any;
    releaseDate: Date;
    isPerson: boolean;
    isContractor: boolean;
}
