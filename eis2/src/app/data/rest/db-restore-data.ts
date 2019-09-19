import { DialogueData } from './dialogue-data';

export interface DbRestoreData extends DialogueData {
    filename: string;
    targetDbName: object;
    pwd: string;
}
