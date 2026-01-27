import { Component, inject, OnInit } from "@angular/core";
import { Form, FormControl, FormGroup, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogActions, MatDialogClose, MatDialogContent, MatDialogRef, MatDialogTitle } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { PeriodicElement } from "@services";

@Component({
    selector: 'dialog-product',
    imports: [MatButtonModule,
        MatDialogTitle,
        MatDialogContent,
        MatDialogActions,
        MatInputModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatDialogClose],
    templateUrl: './dialog-product.component.html'
})
export class DialogProductComponent implements OnInit {
    readonly dialogRef = inject(MatDialogRef<DialogProductComponent>);

    readonly data: PeriodicElement = inject(MAT_DIALOG_DATA);

    form = new FormGroup({
        id: new FormControl(this.data.id),
        
        position: new FormControl(this.data.position),
        name: new FormControl(this.data.name),
        weight: new FormControl(this.data.weight),
        symbol: new FormControl(this.data.symbol),
    });


    ngOnInit(): void {
        console.log('Dialog data received:', this.data);
    }

    onNoClick(): void {
        this.dialogRef.close();
    }
}  