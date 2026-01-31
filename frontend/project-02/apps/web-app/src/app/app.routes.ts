import { provideRouter, Route } from '@angular/router';
import { ProductsService } from '@services';
import { MainShell, mainShellRoutes } from 'main-shell';

export const appRoutes: Route[] = [
    {
        path: 'main-shell',
        // loadComponent: () => import('products').then(c => c.Products),
        loadChildren: () => import('main-shell').then(c => c.mainShellRoutes),
       // component: MainShell,

        providers: [ProductsService,  ],

    },
    {
        path: '',
        pathMatch: 'full',
        redirectTo: '/main-shell',
    }
];
