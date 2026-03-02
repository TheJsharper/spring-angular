import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import * as echarts from 'echarts';

@Component({
  selector: 'lib-candlestick-chart',
  imports: [MatButtonModule],
  templateUrl: './candlestick-chart.html',
  styleUrls: ['./candlestick-chart.scss'],
})
export class CandlestickChart implements OnInit, OnDestroy {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);
  option = {
    xAxis: {
      data: ['2017-10-24', '2017-10-25', '2017-10-26', '2017-10-27']
    },
    yAxis: {},
    series: [
      {
        type: 'candlestick',
        data: [
          [20, 34, 10, 38],
          [40, 35, 30, 50],
          [31, 38, 33, 44],
          [38, 15, 5, 42]
        ]
      }
    ]
  };


  ngOnInit(): void {
    this.chart = echarts.init(document.getElementById('main') as HTMLDivElement);
    this.chart.setOption(this.option);
    this.seriesRefreshInterval = setInterval(() => {
      this.updateSeries();
    }, 2000);
  }

  private updateSeries(): void {

    this.option.series[0].data.push([Math.round(Math.random() * 100), Math.round(Math.random() * 100), Math.round(Math.random() * 100), Math.round(Math.random() * 100)]);

    this.option.xAxis.data.push(new Date().toISOString());

    this.option.series[0].data.shift();

    this.option.xAxis.data.shift();

    this.chart.setOption(this.option);
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
