export interface Flare {
    name:     string;
    children: FlareChild[];
}

export interface FlareChild {
    name:     string;
    children: PurpleChild[];
}

export interface PurpleChild {
    name:      string;
    children?: FluffyChild[];
    value?:    number;
}

export interface FluffyChild {
    name:      string;
    value?:    number;
    children?: TentacledChild[];
}

export interface TentacledChild {
    name:  string;
    value: number;
}
