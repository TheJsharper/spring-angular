import { effect, inject, Signal, signal, WritableSignal } from "@angular/core";
import { Person, PersonsSignalsService } from "@services";
import { getState, patchState, signalStore, withHooks, withMethods, withProps, withState } from '@ngrx/signals';

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

        getPersons(): PersonState {
            patchState(store, {
                persons: service.getPersons()(),
            });
            return getState(store);
        },

        createPerson(person: Person): void {
            service.createPerson(person);
            patchState(store, {
                persons: [...store.persons(), person],

            });
        },
        updatePerson(personId: string, changes: Partial<Person>): void {
            service.updatePerson(personId, changes);
            patchState(store, {
                persons: service.getPersons()(),
            });
        },
        deletePerson(personId: string): void {
            service.deletePerson(personId);
            patchState(store, {
                persons: service.getPersons()(),
            });
        },





    })),
    withHooks({
        onInit(store) {
            effect(() => {
                console.log('PersonStore initialized with state:', getState(store));
            });
        }
    })
);