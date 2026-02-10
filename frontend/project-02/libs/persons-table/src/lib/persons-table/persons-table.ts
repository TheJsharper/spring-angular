import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { keyOfPerson, Person, PersonsService } from '@services';
import { map, Observable, Subscription, tap } from 'rxjs';
import { DialogPersonComponent } from './dialog/dialog-person.component';
import { PersonStore } from './store/persons.store';
@Component({
  selector: 'lib-persons-table',
  imports: [MatFormFieldModule, MatInputModule, MatIconModule, MatTableModule, MatButton],
  templateUrl: './persons-table.html',
  styleUrls: ['./persons-table.scss'],
})
export class PersonsTable {


  private readonly dialog = inject(MatDialog);

  displayedColumns: string[] = [...keyOfPerson, 'actions'];

   persons: Observable<Array<Person>> = inject(PersonsService).getPersons();

   private store = inject(PersonStore);

   persons$: Array<Person> = this.store.getPersons();

  dataSource: Observable<MatTableDataSource<Person>>;

  dataSource$: MatTableDataSource<Person> = new MatTableDataSource<Person>(this.persons$);
  subscriptions = new Subscription();


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

   // if (this.dataSourceInstance) {
      this.dataSource$.filter = filterValue.trim().toLowerCase();
   // }
  }

  openDialog(row: Person): void {
    const dialogRef = this.dialog.open(DialogPersonComponent, {
      data: row,
      width: '40%',
      height: '60%'
    });

    this.subscriptions.add(dialogRef.afterClosed().pipe(
      tap((result: Person) => {
        if (result && row.id !== '') {
          this.store.updatePerson(result.id, result);
          //this.store.dispatch(updatePerson({ personId: row.id, changes: result }));
        } else if (result && row.id === '') {
            this.store.createPerson(result);
          //this.store.dispatch(createPerson({ person: result }));
        }
      })
    ).subscribe());
  }

  onDelete(id: string): void {
    //this.store.dispatch(deleteProduct({ productId: id }));
  }

}
