import { createAction, props } from "@ngrx/store";
import { PeriodicElement } from "@services";



export const loadProducts = createAction(
    '[Products] Load Products'
);

export const loadProductsSuccess = createAction(
    '[Products] Load Products Success',

    props<{ products: PeriodicElement[] }>()
);

export const loadProductsFailure = createAction(
    '[Products] Load Products Failure',
    props<{ error: any }>()
);

export const createProduct = createAction(
    '[Products] Create Product',
    props<{ product: Omit<PeriodicElement, 'id'> }>()
);

export const createProductSuccess = createAction(
    '[Products] Create Product Success',
    props<{ product: PeriodicElement }>()
);


export const createProductFailure = createAction(
    '[Products] Create Product Failure',
    props<{ error: any }>()
);


export const deleteProduct = createAction(
    '[Products] Delete Product',
    props<{ productId: string }>()
);

export const deleteProductSuccess = createAction(
    '[Products] Delete Product Success',
    props<{ productId: string }>()
);
export const deleteProductFailure = createAction(
    '[Products] Delete Product Failure',
    props<{ error: any }>()
);
export const updateProduct = createAction(
    '[Products] Update Product',
    props<{ productId: string; changes: PeriodicElement }>()
);
export const updateProductSuccess = createAction(
    '[Products] Update Product Success',
    props<{ product: PeriodicElement }>()
);
export const updateProductFailure = createAction(
    '[Products] Update Product Failure',
    props<{ error: any }>()
);
export const clearProducts = createAction(
    '[Products] Clear Products'
);
