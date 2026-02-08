import { Route } from '@angular/router';
import { PersonsTable } from './persons-table/persons-table';

export const personsTableRoutes: Route[] = [
  { path: '', component: PersonsTable },
];
