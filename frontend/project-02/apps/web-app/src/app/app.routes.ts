import { provideRouter, Route } from '@angular/router';
import { ProductsService } from '@services';
import { MainShell, mainShellRoutes } from 'main-shell';

export const appRoutes: Route[] = [
   /* {
        path: 'main-shell',
        // loadComponent: () => import('products').then(c => c.Products),
         loadChildren: () => import('main-shell').then(c => c.mainShellRoutes),
       // loadComponent: () => import('main-shell').then(c => c.MainShell),

        providers: [ProductsService],

    },
    {
        path: '',
        pathMatch: 'full',
        redirectTo: '/main-shell',
    }*/

       
    {
        path: 'products',
        //loadComponent: () => import('products').then(m => m.Products),
         loadChildren: () => import('products').then(m => m.productsRoutes),
         providers: [ProductsService],
       // component: Products

    },
];
