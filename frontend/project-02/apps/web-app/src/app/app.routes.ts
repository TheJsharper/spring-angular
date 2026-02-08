import { Route } from '@angular/router';
import { PersonsService, ProductsService } from '@services';

export const appRoutes: Route[] = [


    {
        path: 'products',
        //loadComponent: () => import('products').then(m => m.Products),
        loadChildren: () => import('products').then(m => m.productsRoutes),
        providers: [ProductsService],
        // component: Products

    },
    {
        path: 'persons',
        loadChildren: () => import('persons-table').then(m => m.personsTableRoutes),
        providers: [PersonsService]
    },
    {
        path: '',
        redirectTo: 'products',
        pathMatch: 'full'
    }
];
