import { Injectable, inject } from '@angular/core';
import { Actions, createEffect, ofType } from '@ngrx/effects';
import { ProductsService } from '@services';
import { catchError, exhaustMap, map, of } from 'rxjs';
import { createProduct, createProductFailure, createProductSuccess, deleteProduct, deleteProductFailure, deleteProductSuccess, loadProducts, loadProductsFailure, loadProductsSuccess, updateProduct, updateProductFailure, updateProductSuccess } from './products.actions';

@Injectable()
export class ProductsEffects {
    private actions$ = inject(Actions);
    private productsService = inject(ProductsService);



    loadProducts$ = createEffect(() => {
        return this.actions$.pipe(
            // Effect implementation goes here
            ofType(loadProducts
            ),
            exhaustMap(() => this.productsService.getAllPeriodictElement()
                .pipe(
                    map(products => (loadProductsSuccess({ products }))),
                    catchError(error => of(loadProductsFailure({ error })))
                )
            )
        );
    });

    createProduct$ = createEffect(() =>
        this.actions$.pipe(
            ofType(createProduct),
            exhaustMap((action) => this.productsService.createPeriodictElement(action.product)
                .pipe(
                    map((products) => createProductSuccess({ product: products[products.length - 1] })),
                    catchError((error) => of(createProductFailure({ error })))
                )
            )
        )
    );


    updateProduct$ = createEffect(() =>
        this.actions$.pipe(
            ofType(updateProduct),
            exhaustMap((action) => this.productsService.patchPeriodictElement(action.productId, action.changes)
                .pipe(
                    map((products) => {
                        const updatedProduct = products.find(p => p.id === action.productId);
                        return updatedProduct ? updateProductSuccess
                            ({ product: updatedProduct }) : updateProductFailure({ error: 'Product not found' });
                    }),
                    catchError((error) => of(updateProductFailure({ error })))
                )
            )
        )
    )

    deleteProduct$ = createEffect(() =>
        this.actions$.pipe(
            ofType(deleteProduct),
            exhaustMap((action) => this.productsService.deletePeriodictElement(action.productId)
                .pipe(
                    map(() => deleteProductSuccess({ productId: action.productId })),
                    catchError((error) => of(deleteProductFailure({ error })))
                )
            )
        )
    );

}