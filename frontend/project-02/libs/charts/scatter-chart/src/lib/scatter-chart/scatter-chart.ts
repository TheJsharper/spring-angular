
import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import * as echarts from 'echarts';

@Component({
  imports: [MatButtonModule],
  templateUrl: './scatter-chart.html',
  styleUrls: ['./scatter-chart.scss']
})
export class ScatterChart implements OnInit, OnDestroy {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);

  option = {
    xAxis: {},
    yAxis: {},
    series: [
      {
        symbolSize: 20,
        data: [
          [10.0, 8.04],
          [8.07, 6.95],
          [13.0, 7.58],
          [9.05, 8.81],
          [11.0, 8.33],
          [14.0, 7.66],
          [13.4, 6.81],
          [10.0, 6.33],
          [14.0, 8.96],
          [12.5, 6.82],
          [9.15, 7.2],
          [11.5, 7.2],
          [3.03, 4.23],
          [12.2, 7.83],
          [2.02, 4.47],
          [1.05, 3.33],
          [4.05, 4.96],
          [6.03, 7.24],
          [12.0, 6.26],
          [12.0, 8.84],
          [7.08, 5.82],
          [5.02, 5.68]
        ],
        type: 'scatter'
      }
    ]
  };


  ngOnInit(): void {
    this.chart = echarts.init(document.getElementById('main') as HTMLDivElement);
    this.chart.setOption(this.option);
    this.seriesRefreshInterval = setInterval(() => {
      const scope = this;

      const option = this.chart.getOption() as echarts.EChartsOption;

      const xAxis = Array.isArray(option.xAxis) ? option.xAxis[0] : option.xAxis;

      const xAxisData = xAxis && 'data' in xAxis
        ? (xAxis as { data?: unknown[] }).data ?? []
        : [];

      const series = Array.isArray(option.series)
        ? option.series
        : option.series
          ? [option.series]
          : [];

      const data = series[0].data as Array<Array<number>>;
      data.push([Math.random() * 20, Math.random() * 10]);

      this.chart.setOption({
        xAxis: { data: xAxisData },
        series: [{ data }]
      });

    }, 5000);
  }

  nav(): void {
    this.router.navigate(['/stats/dashboard'], { relativeTo: this.route });

  }

  stopSeries(): void {
    if (this.seriesRefreshInterval) {
      clearInterval(this.seriesRefreshInterval);
    }
  }

  ngOnDestroy(): void {
    if (this.seriesRefreshInterval) {
      clearInterval(this.seriesRefreshInterval);
    }
  }

}
