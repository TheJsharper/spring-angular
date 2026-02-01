import { createFeatureSelector, createSelector } from "@ngrx/store";
import { ProductsState } from "./products.reducer";

export const featureSelectorName = 'products';

export const selectProductsState = createFeatureSelector<ProductsState>(featureSelectorName);
    
export const selectAllProducts = createSelector(
    selectProductsState,
    (state: ProductsState) => state.products
);

export const selectProductsLoading = createSelector(
    selectProductsState,
    (state: ProductsState) => state.loading
);

export const selectProductsError = createSelector(
    selectProductsState,
    (state: ProductsState) => state.error
);