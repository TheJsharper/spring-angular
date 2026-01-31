import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { PeriodicElement, ProductsService } from '@services';
import { mergeMap, Observable } from 'rxjs';
import { DialogProductComponent } from './dialog/dialog-product.component';



@Component({
  selector: 'lib-products',
  imports: [MatTableModule, MatIconModule, MatButtonModule, RouterModule],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products {


  private productService: ProductsService = inject(ProductsService);

  readonly dialog = inject(MatDialog);


  periodictElement: Observable<Array<PeriodicElement>> = this.productService.getAllPeriodictElement();

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol', 'actions'];



  openDialog(row: PeriodicElement): void {
    const dialogRef = this.dialog.open(DialogProductComponent, {
      data: row,
      width: '40%',
      height: '60%'

    });


    this.periodictElement = dialogRef.afterClosed().pipe(
      mergeMap((result: PeriodicElement) => {
        if (result && row.id !== '') {
          return this.productService.patchPeriodictElement(row.id, result)
        } else if (result && row.id === '') {
          return this.productService.createPeriodictElement(result)
        } else {
          return this.productService.getAllPeriodictElement();
        }
      })
    );

  }

  onDelete(id: string): void {
    this.periodictElement = this.productService.deletePeriodictElement(id);
  }


}
