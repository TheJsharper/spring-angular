import { Route } from '@angular/router';
import {ProductsService} from '@services';

export const appRoutes: Route[] = [
    {
        path: '',
       // loadComponent: () => import('products').then(c => c.Products),
        loadComponent: () => import('main-shell').then(c => c.MainShell),
    
        providers:[ProductsService]
    }
];
