import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';

export interface MessageBoardVo extends AbstractVo {
    type: string;
    effectiveDateTransferVo: DateTransferVo;
    expiredDateTransferVo: DateTransferVo;
    status: string;
    messageText: string;
    updatedBy: string;
    updatedDateTransferVo: DateTransferVo;
}
