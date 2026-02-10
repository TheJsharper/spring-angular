import { AsyncPipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { Person, PersonsService, keyOfPerson } from '@services';
import { map, Observable, tap } from 'rxjs';
import { DialogPersonComponent } from './dialog/dialog-person.component';
import { MatButton } from '@angular/material/button';
@Component({
  selector: 'lib-persons-table',
  imports: [MatFormFieldModule, MatInputModule, MatIconModule, MatTableModule, AsyncPipe, MatButton],
  templateUrl: './persons-table.html',
  styleUrls: ['./persons-table.scss'],
})
export class PersonsTable {


  private readonly dialog = inject(MatDialog);

  displayedColumns: string[] = [...keyOfPerson, 'actions'];

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

  openDialog(row: Person): void {
    const dialogRef = this.dialog.open(DialogPersonComponent, {
      data: row,
      width: '40%',
      height: '60%'
    });

    /*this.subscriptions.add(dialogRef.afterClosed().pipe(
      tap((result: Person) => {
        if (result && row.id !== '') {
          this.store.dispatch(updatePerson({ personId: row.id, changes: result }));
        } else if (result && row.id === '') {
          this.store.dispatch(createPerson({ person: result }));
        }
      })
    ).subscribe());*/
  }

  onDelete(id: string): void {
    //this.store.dispatch(deleteProduct({ productId: id }));
  }

}
