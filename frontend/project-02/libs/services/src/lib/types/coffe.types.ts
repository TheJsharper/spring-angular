export interface SunburstData {
    name:      string;
    itemStyle: ItemStyle;
    children:  Child[];
}

export interface Child {
    name:      string;
    value?:    number;
    itemStyle: ItemStyle;
    children?: Child[];
}

export interface ItemStyle {
    color: string;
}
