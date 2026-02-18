import { provideRouter, Routes } from '@angular/router';
import { userRoutes } from './users/users.routes';

export const routes: Routes = [
    
    {
        path:'users',
        loadChildren: ()=> import('./users/users.routes').then(r => r.userRoutes),
        /*providers:[
            provideRouter(userRoutes)
        ]*/
    },
    {
        path:'',
        redirectTo:'users',
        pathMatch: 'full'
    }
];
