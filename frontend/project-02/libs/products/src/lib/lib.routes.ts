import { Route } from '@angular/router';
import { Products } from './products/products';
import { provideState } from '@ngrx/store';
import { productsReducer } from './products/store/products.reducer';
import { provideEffects } from '@ngrx/effects';
import { ProductsEffects } from './products/store/products.effects';
import { featureSelectorName } from './products/store/products.selectors';

export const productsRoutes: Route[] = [{ path: '', component: Products, providers: [
    provideState(featureSelectorName, productsReducer),
    provideEffects([ProductsEffects])
] }];
