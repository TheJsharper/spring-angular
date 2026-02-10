import { inject, Signal, signal } from "@angular/core";
import { Person, PersonsSignalsService } from "@services";
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

    withMethods((store, service = inject(PersonsSignalsService)) => ({

        getPersons(): Array<Person> {
            return service.getPersons();
        }

        , createPerson(person: Person): void {
            service.createPerson(person);
            patchState(store, {
                persons: [...store.persons(), person],

            });
        },
        updatePerson(personId: string, changes: Partial<Person>): void {
            service.updatePerson(personId, changes);
            patchState(store, {
                persons: store.persons().map((person) =>
                    person.id === personId ? { ...person, ...changes } : person
                ),
            });
        },
        deletePerson(personId: string): void {
            service.deletePerson(personId);
            patchState(store, {
                persons: store.persons().filter((person) => person.id !== personId),
            });
        },





    }))
);