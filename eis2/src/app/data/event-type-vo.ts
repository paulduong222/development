import { AbstractVo } from './abstract-vo';

export interface EventTypeVo extends AbstractVo {
    type: string;
    eventTypeCd: string;
}
