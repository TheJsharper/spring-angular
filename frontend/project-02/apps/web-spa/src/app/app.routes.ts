import { Route } from '@angular/router';
import { ProductsService } from '@services';

export const appRoutes: Route[] = [
    //{ path: 'account', component: AccountComponent },
    { path: 'account', loadComponent: () => import('./components/account/account.component').then(m => m.AccountComponent) },
    //{ path: 'home', component: HomeComponent },
    { path: 'home', loadComponent: () => import('./components/home/home.component').then(m => m.HomeComponent) },
    // { path: 'settings', component: SettingsComponent },
    { path: 'settings', loadComponent: () => import('./components/settings/settings.component').then(m => m.SettingsComponent) },
    { path: 'products', loadChildren: () => import('products').then(m => m.productsRoutes), providers: [ProductsService] },
    { path: '**', redirectTo: '/home' }
];
