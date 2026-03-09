import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { GraphLinesChartService } from '@services';
import { from, of } from 'rxjs';
import { geoUSAData } from '@services'

@Component({
  selector: 'lib-graph-lines-chart',
  imports: [MatButtonModule],
  templateUrl: './graph-lines-chart.html',
  styleUrls: ['./graph-lines-chart.scss'],
})
export class GraphLinesChart implements OnInit, OnDestroy {

  private router = inject(Router);

  private route = inject(ActivatedRoute);

  private chartInstance: echarts.ECharts | undefined;

  private graphLinesChartService = inject(GraphLinesChartService);

  constructor() {
    this.graphLinesChartService.fetchData();
    from(of(1, 2, 3)).subscribe({
      next: (data) => {
        console.log('Data fetched in component:', data);
      },
      error: (error) => {
        console.error('Error fetching data in component:', error);
      }
    });
  }
  ngOnInit(): void {
    this.initChart();
  }

  private initChart(): void {
    const chartDom = document.getElementById('main');
    if (chartDom) {
      this.chartInstance = echarts.init(chartDom);
      echarts.registerMap('world', geoUSAData as any);

      this.graphLinesChartService.fetchData().subscribe({
        next: (data) => {
          if (this.chartInstance) {
            
            this.chartInstance.appendData({
              seriesIndex: 0,
              data
            });
          }
        },
        error: (error) => {
          console.error('Error fetching data for chart:', error);
        }
      });

      const option = this.graphLinesChartService.getOption();
      this.chartInstance.setOption(option);
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
  private disposeChart(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
  ngOnDestroy(): void {
    this.disposeChart();
  }
}
