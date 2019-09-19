import { DbAvailVo } from 'src/app/data/db-avail-vo';

export interface DbBackupData {
    vo: DbAvailVo;
    destFileName: string;
    altDestination: string;
}
