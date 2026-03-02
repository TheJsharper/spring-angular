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
                path: 'bar-chart',
                loadComponent: () => import('@bar-chart').then(m => m.BarChart)
            },
            {
                path:'scatter-chart',
                loadComponent: () => import('@scatter-chart').then(m => m.ScatterChart)
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
