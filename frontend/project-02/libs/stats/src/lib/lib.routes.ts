import { Route } from '@angular/router';
import { Stats } from './stats/stats';

export const statsRoutes: Route[] = [
    {
        path: '', component: Stats,

        children: [
            {
                path: 'line-chart',
                loadComponent: () => import('@chart-line').then(m => m.LineChart)
            },
            {
                path: 'pie-chart',
                loadComponent: () => import('@pie-chart').then(m => m.PieChart)
            },
            {
                path: 'dashboard',
                loadComponent: () => import('./stats/dashboard/dashboard.component').then(m => m.DashboardComponent)
            },
            {
                path: '**',
                redirectTo: 'dashboard', pathMatch: 'full'
            }
        ]

    },
    {
        path: 'line-chart',
        loadComponent: () => import('@chart-line').then(m => m.LineChart)
    }
];
