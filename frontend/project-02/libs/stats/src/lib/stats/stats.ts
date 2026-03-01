import { Component, OnDestroy, OnInit } from '@angular/core';

import { RouterOutlet } from '@angular/router';
import * as echarts from 'echarts';

@Component({
  selector: 'lib-stats',
  imports: [  RouterOutlet ],
  templateUrl: './stats.html',
  styleUrls: ['./stats.scss'],
})
export class Stats implements OnInit, OnDestroy {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;


  ngOnInit(): void {
    /*this.chart = echarts.init(document.getElementById('main') as HTMLDivElement);
    this.chart.setOption({
      baseOption: {
        xAxis: {
          type: 'category',
          data: ['Day 1', 'Day 2', 'Day 3', 'Day 4', 'Day 5', 'Day 6', 'Day 7']
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
        xAxisData.push(`Day ${scope.dayCount }`);
       
        xAxisData.shift();
        data.shift();
      }
      console.log('Updating chart with new data:', series[0]);
      this.chart.setOption(option);
    }, 5000);*/
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
