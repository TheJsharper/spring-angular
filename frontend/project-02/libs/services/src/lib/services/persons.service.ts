import { Injectable } from "@angular/core";
import { Person } from "../types/persons.types";
import { personDbs } from "../../dbs/persons.database";

@Injectable()
export class PersonsService {
    private persons: Array<Person> = personDbs;


    getPersons(): Array<Person> {
        return this.persons;
    }
}