import { AsyncPipe } from '@angular/common';
import { AfterViewInit, Component, inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogClose } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { PeriodicElement, ProductsService } from '@services';
import { mergeMap, Observable } from 'rxjs';
import { DialogProductComponent } from './dialog/dialog-product.component';



@Component({
  selector: 'lib-products',
  imports: [MatTableModule],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products {


  private productService: ProductsService = inject(ProductsService);

  readonly dialog = inject(MatDialog);


  periodictElement: Observable<Array<PeriodicElement>> = this.productService.getAllPeriodictElement();

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];



 
  openDialog(row: PeriodicElement): void {
    const dialogRef = this.dialog.open(DialogProductComponent, {
      data: row,
      width: '40%',
      height: '60%',
      hasBackdrop: false,
      
    });


    this.periodictElement = dialogRef.afterClosed().pipe(
      mergeMap((result: PeriodicElement) =>{
        if (result) {
          return this.productService.patchPeriodictElement(row.id, result)
        }
        return this.productService.getAllPeriodictElement();
      })
    );

  }


}
