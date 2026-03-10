import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { TreeChartService, TreemapChartService } from '@services';
import * as echarts from 'echarts';
import { Subscription } from 'rxjs';

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

    this.subscription.add(
      this.treemapChartService.getData().subscribe(data => {
        if (this.chartInstance) {
          const option = this.treemapChartService.getOptionTreemap(data);
          this.chartInstance.setOption(option);
        }
      })
    );
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
