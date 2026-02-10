import { Route } from '@angular/router';
import { PersonsSignalsService } from '@services';
import { PersonsTable } from './persons-table/persons-table';
import { PersonStore } from './persons-table/store/persons.store';

export const personsTableRoutes: Route[] = [
  { path: '', component: PersonsTable, providers: [PersonStore, PersonsSignalsService] },
];
