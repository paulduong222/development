interface Button {
    text: string;
    click: Function;
    class: string;
}
interface Column {
    field?: string;
    title: string;
    cellContentClick?: Function;
    showAsLink?: boolean;
    unsortable?: boolean;
    button?: Button;
    style?: any;
}
export interface TableDefinition {
    columns: Column[];
}
