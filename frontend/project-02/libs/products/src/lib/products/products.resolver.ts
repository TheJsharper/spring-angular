import { ActivatedRoute, ActivatedRouteSnapshot, ResolveFn, RouterStateSnapshot } from "@angular/router";
import { Store } from "@ngrx/store";
import { PeriodicElement } from "@services";
import { map, Observable } from "rxjs";
import { ProductsState } from "./store/products.reducer";
import { inject } from "@angular/core";
import { loadProducts } from "./store/products.actions";
import { selectAllProducts } from "./store/products.selectors";

export const productsResolver: ResolveFn<Observable<Array<PeriodicElement>>> =
    (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {


        const store: Store<ProductsState> = inject(Store<ProductsState>);

        store.dispatch(loadProducts());
        
        return store.select(selectAllProducts)
    }
