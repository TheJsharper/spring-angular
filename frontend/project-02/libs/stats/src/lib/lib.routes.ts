import { Route } from '@angular/router';
import { BoxplotChartService } from '@boxplot-chart';
import { Stats } from './stats/stats';
import { HeatmapChartService } from '@heatmap-chart';
import { GraphChartService } from '@graph-chart';
import { FunnelChartService, GaugeChartService, GraphLinesChartService, GraphLinesGeoChartService, ParallelChartService, SankeyChartService, SunburstChartService, TreeChartService, TreemapChartService } from '@services';
import { GraphGeoChartService } from 'libs/charts/graph-geo-chart/src/lib/services/GraphGeoChartService';

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
                path: 'scatter-chart',
                loadComponent: () => import('@scatter-chart').then(m => m.ScatterChart)
            },
            {
                path: 'candlestick-chart',
                loadComponent: () => import('@candlestick-chart').then(m => m.CandlestickChart)
            },
            {
                path: 'radar-chart',
                loadComponent: () => import('@radar-chart').then(m => m.RadarChart)
            },
            {
                path: 'boxplot-chart',
                loadComponent: () => import('@boxplot-chart').then(m => m.BoxplotChart),
                providers: [BoxplotChartService]
            },
            {
                path: 'heatmap-chart',
                loadComponent: () => import('@heatmap-chart').then(m => m.HeatmapChart),
                providers: [HeatmapChartService]
            },
            {
                path: 'graph-chart',
                loadComponent: () => import('@graph-chart').then(m => m.GraphChart),
                providers: [GraphChartService]
            },
            {
                path: 'graph-lines-chart',
                loadComponent: () => import('@graph-lines-chart').then(m => m.GraphLinesChart),
                providers: [GraphLinesChartService, GraphLinesGeoChartService]
            },
            {
                path: 'graph-geo-chart',
                loadComponent: () => import('@graph-geo-chart').then(m => m.GraphGeoChart),
                providers: [GraphGeoChartService]
            },
            {
                path: 'tree-chart',
                loadComponent: () => import('@tree-chart').then(m => m.TreeChart),
                providers: [TreeChartService]
            },
            {
                path: 'treemap-chart',
                loadComponent: () => import('@treemap-chart').then(m => m.TreemapChart),
                providers: [TreemapChartService]
            },
            {
                path: 'sunburst-chart',
                loadComponent: () => import('@sunburst-chart').then(m => m.SunburstChart),
                providers: [SunburstChartService]
            },
            {
                path: 'parallel-chart',
                loadComponent: () => import('@parallel-chart').then(m => m.ParallelChart),
                providers: [ParallelChartService]
            },
            {
                path: 'sankey-chart',
                loadComponent: () => import('@sankey-chart').then(m => m.SankeyChart),
                providers: [SankeyChartService]
            },
            {
                path: 'funnel-chart',
                loadComponent: () => import('@funnel-chart').then(m => m.FunnelChart),
                providers: [FunnelChartService]
            },
            {
                path:'gauge-chart',
                loadComponent: () => import('@gauge-charts').then(m => m.GaugeCharts),
                providers: [GaugeChartService]

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
