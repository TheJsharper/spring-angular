export interface EChartsPackageSize {
    name:     string;
    size:     number;
    children: Child[];
    value:    number;
}

export interface Child {
    name:      string;
    size?:     number;
    children?: Child[];
    value?:    number;
}
