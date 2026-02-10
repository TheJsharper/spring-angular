import { Component, inject, signal, WritableSignal } from "@angular/core";
import { form, FormField } from "@angular/forms/signals";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { MatFormField, MatLabel } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { Person } from "@services";
import { PersonStore } from "../store/persons.store";

@Component({
    selector: 'lib-dialog-person',
    imports: [
        MatFormField,
        MatLabel,
        FormField,
        MatDialogTitle,
        MatDialogContent,
        MatDialogActions,
        MatDialogClose,
        MatInputModule,
        MatDialogActions,
        MatButtonModule 
    ],
    templateUrl: './dialog-person.component.html',
})
export class DialogPersonComponent {
    readonly data: Person = inject(MAT_DIALOG_DATA);
    //&private store = inject(PersonStore);

    readonly dialogRef = inject(MatDialogRef<DialogPersonComponent>);

    model: WritableSignal<Person> = signal({
        id: this.data.id,
        firstName: this.data.firstName,
        lastName: this.data.lastName,
        age: this.data.age,
        phone: this.data.phone,
        street: this.data.street,
        houseNumber: this.data.houseNumber,
        state: this.data.state,
        city: this.data.city,
        country: this.data.country
    });
    form = form(this.model);

    constructor() { }

    onSave(): void {
        const person: Person = this.model();
     //   this.store.updatePerson(person.id, person);
        this.dialogRef.close(person);
    }

    onCancel(): void {
        this.dialogRef.close();
    }
}