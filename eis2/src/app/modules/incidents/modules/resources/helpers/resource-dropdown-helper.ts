import { DropdownData } from 'src/app/data/dropdowndata';

export class ResourceDropdownHelper {
    statusTypeData: DropdownData[] = [
        {id: 0, code: 'F', desc: 'Filled'}
        , {id: 1, code: 'C', desc: 'Checked-In'}
        , {id: 2, code: 'P', desc: 'Pending Demob'}
        , {id: 3, code: 'D', desc: 'Demob'}
        , {id: 4, code: 'R', desc: 'Reassigned'}
    ];

}
