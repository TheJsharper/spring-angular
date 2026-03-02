import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { Router, ActivatedRoute } from '@angular/router';
import * as echarts from 'echarts';


@Component({
  selector: 'lib-radar-chart',
  imports: [MatButtonModule],
  templateUrl: './radar-chart.html',
  styleUrls: ['./radar-chart.scss'],
})
export class RadarChart implements OnInit, OnDestroy {
  chart!: echarts.EChartsType;
  seriesRefreshInterval: any;
  dayCount = 7;
  router: Router = inject(Router);
  route: ActivatedRoute = inject(ActivatedRoute);

  ngOnInit(): void {
    this.chart = echarts.init(document.getElementById('main') as HTMLDivElement);
    this.initChart();
  }
  private initChart() {
    const option = {
      radar: {
        indicator: [
          { name: 'Sales', max: 6500 },
          { name: 'Administration', max: 16000 },
          { name: 'Information Technology', max: 30000 },
          { name: 'Customer Support', max: 38000 },
          { name: 'Development', max: 52000 },
          { name: 'Marketing', max: 25000 }
        ]
      },
      series: [
        {
          type: 'radar',
          data: [
            {
              value: [4300, 10000, 28000, 35000, 50000, 19000],
              name: 'Budget Allocation'
            },
            {
              value: [5000, 14000, 28000, 31000, 42000, 21000],
              name: 'Actual Spending'
            }
          ]
        }
      ]
    };
    this.chart.setOption(option);
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
