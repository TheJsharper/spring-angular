import { Component, inject } from '@angular/core';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { Person, PersonsService } from '@services';
@Component({
  selector: 'lib-persons-table',
  imports: [MatFormFieldModule, MatInputModule, MatTableModule],
  templateUrl: './persons-table.html',
  styleUrls: ['./persons-table.scss'],
})
export class PersonsTable {
  
  displayedColumns: string[] = ['firstName', 'lastName', 'age', 'phone', 'street', 'houseNumber', 'state', 'city', 'country'];

  persons: Array<Person> = inject(PersonsService).getPersons();

  dataSource = new MatTableDataSource(this.persons);

    applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

}
