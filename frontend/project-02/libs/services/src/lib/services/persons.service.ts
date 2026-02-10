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

    createPerson(person: Omit<Person, "id">): Observable<Array<Person>> {
        const newPerson: Person = { ...person, id: this.generateId() };
        this.persons = of([...personDbs, newPerson]);
        return this.persons;
    }

    deletePerson(id: string): Observable<Array<Person>> {
        this.persons = this.persons.pipe(
            map((persons) => persons.filter((person) => person.id !== id))
        );
        return this.persons;
    }
    
    updatePerson(id: string, updatedPerson: Omit<Person, "id">): Observable<Array<Person>> {
        this.persons = this.persons.pipe(
            map((persons) => persons.map((person) => person.id === id ? { ...person, ...updatedPerson } : person))
        );
        return this.persons;
    }
    
    private generateId(): string {
        return crypto.randomUUID();
    }
}