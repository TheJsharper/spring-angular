export interface Person {
    id: string;
    firstName: string;
    lastName: string;
    age: number;
    phone: string;
    street: string;
    houseNumber: string;
    state: string;
    city: string;
    country: string;
}

export const keyOfPerson: Array<keyof Omit<Person, 'id'>> = [
    'firstName',
    'lastName',
    'age',
    'phone',    
    'street',
    'houseNumber',
    'state',
    'city',
    'country'
];
export const getKeyOfPerson = (person: Person): Array<keyof Person> => {
    return Object.keys(person) as Array<keyof Person>;
}