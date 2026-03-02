import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import * as echarts from 'echarts';

@Component({
  selector: 'lib-bar-chart',
  imports: [MatButtonModule],
  templateUrl: './bar-chart.html',
  styleUrls: ['./bar-chart.scss'],
})
export class BarChart implements OnInit {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);

  option = {
    xAxis: {
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {},
    series: [
      {
        type: 'bar',
        data: [23, 24, 18, 25, 27, 28, 25]
      },
      {
        type: 'bar',
        data: [26, 24, 18, 22, 23, 20, 27]
      }
    ]
  };

  constructor() { }

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

      console.log('Current xAxis data:', xAxisData);
      const series = Array.isArray(option.series)
        ? option.series
        : option.series
          ? [option.series]
          : [];

      const data = series[0].data as Array<number>;
      if (series.length > 0) {

        data.push(Math.floor(Math.random() * 2000));
        scope.dayCount++;
      }
    }, 2000);
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
