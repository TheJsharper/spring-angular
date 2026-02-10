import { Injectable, signal, WritableSignal } from "@angular/core";
import { personDbs } from "../../dbs/persons.database";
import { Person } from "../types/persons.types";

@Injectable()
export class PersonsSignalsService {
    private persons: WritableSignal<Array<Person>> = signal(personDbs);

    constructor() {
        console.log("PersonsSignalsService initialized with persons:", this.persons());
    }

    getPersons(): Array<Person> {
        return this.persons();
    }

    getPersonById(id: string): Person | undefined {
        return this.persons().find((person) => person.id === id);
    }

    createPerson(person: Omit<Person, "id">): void {
        const newPerson: Person = { ...person, id: this.generateId() };
        this.persons.update((prev: Person[]) => [...prev, newPerson]);
    }

    updatePerson(id: string, updatedPerson: Partial<Person>): void {
        this.persons.update((prev: Person[]) => prev.map((person) => person.id === id ? { ...person, ...updatedPerson } : person));
    }

    deletePerson(id: string): void {
        this.persons.update((prev: Person[]) => prev.filter((person) => person.id !== id));
    }

    private generateId(): string {
        return crypto.randomUUID();
    }
}