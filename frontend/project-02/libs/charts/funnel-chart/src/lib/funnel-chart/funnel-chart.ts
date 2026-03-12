import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { FunnelChartService } from '@services';
import * as echarts from 'echarts';
@Component({
  selector: 'lib-funnel-chart',
  imports: [MatButtonModule],
  templateUrl: './funnel-chart.html',
  styleUrl: './funnel-chart.scss',
})
export class FunnelChart implements OnInit, OnDestroy {
  private funnelChartService = inject(FunnelChartService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private chartInstance: echarts.ECharts | undefined;

  ngOnInit(): void {


    const options = this.funnelChartService.getOptions();
    this.chartInstance = echarts.init(document.getElementById('main') as HTMLElement);
    this.chartInstance.setOption(options);


  }

  ngOnDestroy(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }

  stopSeries(): void {
    // Stop the funnel chart series
    if (this.chartInstance) {
      this.chartInstance.clear();
    }
  }

  nav(): void {
    this.router.navigate(['../dashboard'], { relativeTo: this.route });
  }
}
