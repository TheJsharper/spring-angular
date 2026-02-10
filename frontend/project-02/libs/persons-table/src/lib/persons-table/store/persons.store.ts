import { Signal, signal } from "@angular/core";
import { Person } from "@services";
import { patchState, signalStore, withMethods, withProps, withState } from '@ngrx/signals';

export const PERSON_FEATURE_KEY = 'persons';

export interface PersonState {
    // Define the state properties here
    persons: Array<Person>;
    //isLoading: boolean;
}

export const initialPersonState: PersonState = {
    persons: [],
    //isLoading: false,
};
/**/
export const PersonStore = signalStore(
    withState<PersonState>(initialPersonState),
    withMethods((store) => ({
        createPerson(person: Person): void {
            patchState(store, {
                persons: [...store.persons(), person],

            });
        },
        updatePerson(personId: string, changes: Partial<Person>): void {
            patchState(store, {
                persons: store.persons().map((person) =>
                    person.id === personId ? { ...person, ...changes } : person
                ),
            });
        },
        deletePerson(personId: string): void {
            patchState(store, {
                persons: store.persons().filter((person) => person.id !== personId),
            });
        },





    }))
);