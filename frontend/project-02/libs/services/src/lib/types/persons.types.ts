export interface Person {
    firstName:   string;
    lastName:    string;
    age:         number;
    phone:       string;
    street:      string;
    houseNumber: string;
    state:       string;
    city:        string;
    country:     string;
}

export enum City {
    Bronx = "Bronx",
    Brooklyn = "Brooklyn",
    NewYork = "New York",
    Queens = "Queens",
    StatenIsland = "Staten Island",
}

export enum Country {
    Usa = "USA",
}

export enum State {
    Ny = "NY",
}
