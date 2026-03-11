import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { TreemapChartService } from '@services';
import * as echarts from 'echarts';
import { EChartsPackageSize } from 'libs/services/src/lib/types/echarts-package-size';
import { map, mergeMap, Subscription, timer } from 'rxjs';

@Component({
  selector: 'lib-treemap-chart',
  imports: [MatButtonModule],
  templateUrl: './treemap-chart.html',
  styleUrls: ['./treemap-chart.scss'],
})
export class TreemapChart implements OnInit, OnDestroy {
  private router: Router = inject(Router);

  private route: ActivatedRoute = inject(ActivatedRoute);

  private treemapChartService: TreemapChartService = inject(TreemapChartService);

  private chartInstance: echarts.ECharts | undefined;

  private subscription: Subscription = new Subscription();

  ngOnInit(): void {
    const chartDom = document.getElementById('main');

    this.chartInstance = echarts.init(chartDom);

    this.subscription.add(this.treemapChartService.getData().pipe(
      mergeMap((data: EChartsPackageSize) => {
        const sunburstOption = this.treemapChartService.getOptionSunburst(data);

        const treepMapOption = this.treemapChartService.getOptionTreemap(data);
        
        return timer(0, 5000).pipe(map((tic) => {
          if (this.chartInstance) {
            if (tic % 2 === 0) {
              this.chartInstance.setOption(treepMapOption);
            } else {
              this.chartInstance.setOption(sunburstOption);
            }
          }
        }));
      })
    ).subscribe());
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
  stopSeries(): void {
    if (this.chartInstance) {
      this.chartInstance.clear();
    }
  }

  nav(): void {
    this.router.navigate(['../dashboard'], { relativeTo: this.route });
  }
}
