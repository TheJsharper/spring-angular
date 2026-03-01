import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
@Component({
  selector: 'lib-pie-chart',
  imports: [MatButtonModule],
  templateUrl: './pie-chart.html',
  styleUrl: './pie-chart.scss',
})
export class PieChart implements OnInit, OnDestroy {
  option = {
    title: {
      text: 'Referer of a Website',
      subtext: 'Fake Data',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };

  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);
  ngOnInit(): void {
    this.chart = echarts.init(document.getElementById('main') as HTMLElement);
    this.chart.setOption(this.option);
    this.seriesRefreshInterval = setInterval(() => {
      const scope = this;

      const option = this.chart.getOption() as echarts.EChartsOption;

      const series = Array.isArray(option.series)
        ? option.series
        : option.series
          ? [option.series]
          : [];

      if (series.length > 0) {
        const data = series[0].data as Array<{ value: number; name: string }>;
        data.forEach(item => {
          item.value = Math.floor(Math.random() * 2000);
        });
        scope.chart.setOption({
          series: [
            {
              data: data
            }
          ]
        });
      }
      
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
