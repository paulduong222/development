import { AbstractVo } from './abstract-vo';
import { ResourceVo } from './resource-vo';
import { DateTransferVo } from './date-transfer-vo';

export interface ResourceMobilizationVo extends AbstractVo {
    resourceVo: ResourceVo;
    startDate: Date;
    startDateVo: DateTransferVo;
}
