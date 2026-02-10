import { Route } from '@angular/router';
import {  } from '@ngrx/signals';
import { ProductsService } from '@services';
import { counterReducer } from './components/counter/store/counter.reducers';
import { CounterStoreSignal } from './components/signal-store-counter/store/store-signal';
import { provideState } from '@ngrx/store';

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
    {
        path: 'signal-store-counter',
        loadComponent: () => import('./components/signal-store-counter/signal-store-counter.component').then(m => m.SignalStoreCounterComponent),
        providers: [ CounterStoreSignal]
    },

    { path: '**', redirectTo: '/home' }
];
