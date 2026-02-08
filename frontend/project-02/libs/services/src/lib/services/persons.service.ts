import { Injectable } from "@angular/core";
import { Person } from "../types/persons.types";
import { personDbs } from "../../dbs/persons.database";
import { map, Observable, of } from "rxjs";

@Injectable()
export class PersonsService {
    private persons: Observable<Array<Person>> = of(personDbs);


    getPersons(): Observable<Array<Person>> {
        return this.persons;
    }

    getPersonById(id: string): Observable<Person | undefined> {
        return this.persons.pipe(
            map((persons) => persons.find((person) => person.id === id))
        );
    }

    createPerson(person: Omit<Person, "id">): void {
        const newPerson: Person = { ...person, id: this.generateId() };
        this.persons = of([...personDbs, newPerson]);
    }

    private generateId(): string {
        return Math.random().toString(36).substr(2, 9);
    }
}