import { AsyncPipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { PeriodicElement, ProductsService } from '@services';
import { mergeMap, Observable } from 'rxjs';
import { DialogProductComponent } from './dialog/dialog-product.component';



@Component({
  selector: 'lib-products',
  imports: [MatTableModule, AsyncPipe],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products implements OnInit {


  private productService: ProductsService = inject(ProductsService);

  readonly dialog = inject(MatDialog);

  clickedRows = new Set<PeriodicElement>();

  periodictElement!: Observable<Array<PeriodicElement>>;

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];



  ngOnInit() {
    this.periodictElement = this.productService.getAllPeriodictElement();
  }
  openDialog(row: PeriodicElement): void {
    const dialogRef = this.dialog.open(DialogProductComponent, {
      data: row,
      width: '40%',
      height: '60%',
    });


    this.periodictElement = dialogRef.afterClosed().pipe(
      mergeMap((result: PeriodicElement) =>
        this.productService.patchPeriodictElement(row.id, result)
      )
    );

  }


}
