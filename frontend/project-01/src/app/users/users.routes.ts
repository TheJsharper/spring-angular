import { Routes } from "@angular/router";
import { UserService } from "./services/users.service";

export const userRoutes: Routes = [
    {
        path: '',
        loadComponent: () => import('./users-main.component').then(c => c.UserComponent),

       providers:[UserService],     

        children: [

            {
                path: 'list',
                loadComponent: () => import('./list/user-list.component').then(c => c.UserListComponent)
            },
            {
                path: 'add',
                loadComponent: () => import('./add/user-add.component').then(c => c.UserAddComponent)
            },
            {
                path: 'update/:id',
                loadComponent: () => import('./update/user-update.component').then(c => c.UserUpdateComponent)
            },
            {
                path: 'delete/:id',
                loadComponent: () => import('./delete/user-delete.component').then(c => c.UserDeleteComponent)
            },

               {
                path: 'view/:id',
                loadComponent: () => import('./view/user-view.component').then(c => c.UserViewComponent)
            },
            {
                path: '**',
                redirectTo: 'list', pathMatch: 'full'
            }

        ]
    },
    {
        path: '',
        redirectTo: 'list', pathMatch: 'full'
    }
    ,
    {
        path: '**',
        redirectTo: 'list', pathMatch: 'full'
    }
]