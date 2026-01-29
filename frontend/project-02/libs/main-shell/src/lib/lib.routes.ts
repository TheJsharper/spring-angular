import { Route } from '@angular/router';
import { MainShell } from './main-shell/main-shell';

export const mainShellRoutes: Route[] = [
    {
        path: '',
        component: MainShell,
        pathMatch: 'full',

        /*children: [
            {
                path: 'products',
                loadComponent: () => import('products').then(m => m.Products),

            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: '/products',
            }
        ]*/
    },
    {
        path: 'products',
        loadComponent: () => import('products').then(m => m.Products),

    },
];