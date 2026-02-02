import { createReducer, on } from "@ngrx/store";
import { PeriodicElement } from "@services";
import { clearProducts, createProductSuccess, deleteProductSuccess, loadProducts, loadProductsFailure, loadProductsSuccess, updateProductSuccess } from "./products.actions";


export interface ProductsState {
    products: PeriodicElement[];
    loading: boolean;
    error: any;
}
export const initialProductsState: ProductsState = {
    products: [],
    loading: false,
    error: null,
};

export const productsReducer = createReducer(
    initialProductsState,
    on(loadProducts, (state, actions) => ({
        ...state,
        loading: true,
        error: null,
    })),
    on(loadProductsSuccess, (state, { products }) => ({
        ...state,
        products: products,
        loading: false,
        error: null,
    })),
    on(loadProductsFailure, (state, { error }) => ({
        ...state,
        loading: false,
        error: error,
    })),
    on(deleteProductSuccess, (state, { productId }) => ({
        ...state,
        products: state.products.filter(product => product.id !== productId),
        loading: false,
        error: null,
    })),
    on(updateProductSuccess, (state, { product }) => ({
        ...state,
        products: state.products.map(p => p.id === product.id ? product : p),
        loading: false,
        error: null,
    })),
    on(createProductSuccess, (state, action) =>
    ({
        ...state, products: [...state.products, action.product],
        loading: true,
        error: null,
    })
    ),
    on(clearProducts, (state) => ({
        ...state,
        products: [],
        loading: false,
        error: null,
    })),



); 