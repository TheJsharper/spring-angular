import { Route } from '@angular/router';
import { provideState } from '@ngrx/store';
import { ProductsService } from '@services';
import { counterReducer } from './components/counter/store/counter.reducers';

export const appRoutes: Route[] = [

    { path: 'account', loadComponent: () => import('./components/account/account.component').then(m => m.AccountComponent) },

    { path: 'home', loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent) },

    { path: 'settings', loadComponent: () => import('./components/settings/settings.component').then(m => m.SettingsComponent) },

    { path: 'products', loadChildren: () => import('products').then(m => m.productsRoutes), providers: [ProductsService] },

    {
        path: 'counter',
        loadComponent: () => import('./components/counter/counter.component').then(m => m.CounterComponent),
        providers: [
            provideState('count', counterReducer)
        ]
    },

    { path: '**', redirectTo: '/home' }
];
