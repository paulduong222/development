import { AbstractVo } from './abstract-vo';
import { DateTransferVo } from './date-transfer-vo';

export interface DbAvailVo extends AbstractVo {
    databaseName: string;
    databaseNameActual: string;
    password: string;
    confirmPassword: string;
    isAutoBackup: boolean;
    backupInterval: number;
    passwordHash: string;
    backupDestination: string;
    additionalBackupDestination: string;
    isActive: boolean;
    masterDb: string;
    datasource: string;
    initialVersion: number;
    lastRecoverCode: string;

    createdDateAsString: string;
    createdTime: string;

    // change password fields
    isChangePassword: boolean;
    currentPassword: string;
    changePassword: string;
    confirmChangePassword: string;

    lastAutoBackupDateVo: DateTransferVo;
}
