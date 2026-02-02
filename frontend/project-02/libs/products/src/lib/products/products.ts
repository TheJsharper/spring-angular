import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { Store } from '@ngrx/store';
import { PeriodicElement, ProductsService } from '@services';
import { Observable, Subscription, tap } from 'rxjs';
import { DialogProductComponent } from './dialog/dialog-product.component';
import { createProduct, deleteProduct, loadProducts, updateProduct } from './store/products.actions';
import { ProductsState } from './store/products.reducer';
import { selectAllProducts } from './store/products.selectors';



@Component({
  selector: 'lib-products',
  imports: [MatTableModule, MatIconModule, MatButtonModule, RouterModule],
  templateUrl: './products.html',
  styleUrl: './products.scss',
})
export class Products implements OnInit, OnDestroy {




  readonly dialog = inject(MatDialog);

  private store: Store<ProductsState> = inject(Store<ProductsState>);

  periodictElement: Observable<Array<PeriodicElement>> = this.store.select(selectAllProducts);

  displayedColumns: string[] = ['position', 'name', 'weight', 'symbol', 'actions'];

  private subscriptions = new Subscription

  constructor() {
    this.store.dispatch(loadProducts());
  }

  ngOnInit(): void {
    this.periodictElement = this.store.select(selectAllProducts)
  }


  openDialog(row: PeriodicElement): void {
    const dialogRef = this.dialog.open(DialogProductComponent, {
      data: row,
      width: '40%',
      height: '60%'

    });


    this.subscriptions.add(dialogRef.afterClosed().pipe(
      tap((result: PeriodicElement) => {
        if (result && row.id !== '') {
          this.store.dispatch(updateProduct({ productId: row.id, changes: result }));
        } else if (result && row.id === '') {
          this.store.dispatch(createProduct({ product: result }));
        }
      })
    ).subscribe());

  }

  onDelete(id: string): void {
    this.store.dispatch(deleteProduct({ productId: id }));
  }

  ngOnDestroy(): void {
    // Cleanup if necessary
    this.subscriptions.unsubscribe();
  }


}
