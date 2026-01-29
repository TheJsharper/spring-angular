import { Route } from '@angular/router';
import { ProductsService } from '@services';

export const appRoutes: Route[] = [
    {
        path: 'main-shell',
        // loadComponent: () => import('products').then(c => c.Products),
        loadChildren: () => import('main-shell').then(c => c.mainShellRoutes),

        providers: [ProductsService],

    },
    {
        path: '',
        pathMatch: 'full',
        redirectTo: '/main-shell',
    }
];
