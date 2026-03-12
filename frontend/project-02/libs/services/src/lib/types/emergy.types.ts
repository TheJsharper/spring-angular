export interface Emergy {
    nodes: Node[];
    links: Link[];
}

export interface Link {
    source: string;
    target: string;
    value:  number;
}

export interface Node {
    name: string;
}
