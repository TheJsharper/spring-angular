import { Component, OnDestroy, OnInit } from '@angular/core';

import * as echarts from 'echarts';
import { } from 'echarts';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'lib-stats',
  imports: [ MatButtonModule ],
  templateUrl: './stats.html',
  styleUrls: ['./stats.scss'],
})
export class Stats implements OnInit, OnDestroy {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;

  ngOnInit(): void {
    this.chart = echarts.init(document.getElementById('main') as HTMLDivElement);
    this.chart.setOption({
      baseOption: {
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [820, 932, 901, 934, 1290, 1330, 1320],
          type: 'line'
        }]
      }
    });

    this.seriesRefreshInterval = setInterval(() => {

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

      if (series.length > 0) {
        const data = series[0].data as Array<number>;
        data.push(Math.floor(Math.random() * 2000));
        xAxisData.push(`Day ${xAxisData.length + 1}`);
      }
      console.log('Updating chart with new data:', series[0]);
      this.chart.setOption(option);
    }, 5000);
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
