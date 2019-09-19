import { DialogueData } from './dialogue-data';
import { DropdownData } from '../dropdowndata';

export interface KindSubData extends DialogueData {
    requestCategoryTypeData: DropdownData[];
    dailyFormTypeData: DropdownData[];
    sit209TypeData: DropdownData[];
    subGroupCategoryTypeData: DropdownData[];
    groupCategoryTypeData: DropdownData[];
    departmentTypeData: DropdownData[];
}
