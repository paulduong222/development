export class dbDropdownData {
    id?: number;
    name?: string;
    description?: string;
    reftype?: string;
    constructor(_id: number, _name: string, _desc: string) {
        this.id = _id;
        this.name = _name;
        this.description = _desc;
    }
}