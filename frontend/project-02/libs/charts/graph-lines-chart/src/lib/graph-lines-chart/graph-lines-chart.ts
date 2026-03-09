import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import { GraphLinesChartService, GraphLinesGeoChartService, geoUSAData } from '@services';
import * as echarts from 'echarts';
import { Subscription } from 'rxjs';

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

  private graphLinesGeoChartService = inject(GraphLinesGeoChartService);

  private subscription: Subscription = new Subscription();


  ngOnInit(): void {
    this.initChart();
  }

  private initChart(): void {
    const chartDom = document.getElementById('main');
    this.chartInstance = echarts.init(chartDom);

    if (chartDom) {
      echarts.registerMap('world', geoUSAData as any);
      this.subscription.add(
        this.graphLinesGeoChartService.getGeoData().subscribe({
          next: (geoData) => {
            echarts.registerMap('world', geoData as any);
          },
          error: (error) => {
            console.error('Error fetching geo data:', error);
          }
        }));

      this.subscription.add(
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
        }));
    }

    const option = this.graphLinesChartService.getOption();

    this.chartInstance.setOption(option);
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
    this.subscription.unsubscribe();
  }
}
