import { ChangeDetectionStrategy, Component, effect, inject, signal, WritableSignal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { getState } from '@ngrx/signals';
import { keyOfPerson, Person } from '@services';
import { Subscription, tap } from 'rxjs';
import { DialogPersonComponent } from './dialog/dialog-person.component';
import { PersonStore } from './store/persons.store';
@Component({
  selector: 'lib-persons-table',
  imports: [MatFormFieldModule, MatInputModule, MatIconModule, MatTableModule, MatButton],
  templateUrl: './persons-table.html',
  styleUrls: ['./persons-table.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush

})
export class PersonsTable {


  private readonly dialog = inject(MatDialog);

  displayedColumns: string[] = [...keyOfPerson, 'actions'];

  private store = inject(PersonStore);

  private persons$$: WritableSignal<Array<Person>>;

  dataSource = new MatTableDataSource<Person>(getState(this.store).persons);

  subscriptions = new Subscription();

  constructor() {
    this.persons$$ = signal(getState(this.store).persons);

    effect(() => {
      this.persons$$.set([...getState(this.store).persons]);
      
      this.dataSource.data = [...getState(this.store).persons];
    });
  }


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;

    this.dataSource.filter = filterValue.trim().toLowerCase();
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
        } else if (result && row.id === '') {
          this.store.createPerson(result);
        }
      })
    ).subscribe());
  }

  onDelete(id: string): void {
    this.store.deletePerson(id);
  }

}
