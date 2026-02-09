import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Person, PersonsService, keyOfPerson } from '@services';
import { map, Observable, tap } from 'rxjs';
@Component({
  selector: 'lib-persons-table',
  imports: [MatFormFieldModule, MatInputModule, MatTableModule, AsyncPipe],
  templateUrl: './persons-table.html',
  styleUrls: ['./persons-table.scss'],
})
export class PersonsTable {

  
   
  displayedColumns: string[] = keyOfPerson;

  persons: Observable<Array<Person>> = inject(PersonsService).getPersons();

  dataSource: Observable<MatTableDataSource<Person>>;


  private dataSourceInstance?: MatTableDataSource<Person>;

  constructor() {
   

    this.dataSource = this.persons.pipe(
      map((data) => new MatTableDataSource(data)),
      tap((dataSource) =>
        this.dataSourceInstance = dataSource
      )
    )
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    if (this.dataSourceInstance) {
      this.dataSourceInstance.filter = filterValue.trim().toLowerCase();
    }
  }

}
