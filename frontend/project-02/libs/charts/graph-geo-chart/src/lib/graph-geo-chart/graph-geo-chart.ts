import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { GraphGeoChartService } from '../services/GraphGeoChartService';
import {geoChData} from '@services'
@Component({
  selector: 'lib-graph-geo-chart',
  imports: [MatButtonModule],
  templateUrl: './graph-geo-chart.html',
  styleUrls: ['./graph-geo-chart.scss'],
})
export class GraphGeoChart implements OnInit, OnDestroy {
   private router = inject(Router);

  private route = inject(ActivatedRoute);

  private chartInstance: echarts.ECharts | undefined;

  private graphChartService = inject(GraphGeoChartService);

  ngOnInit(): void {
    this.initChart();
  }

  ngOnDestroy(): void {
    this.disposeChart();
  }

  private disposeChart(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
  private initChart(): void {
    const chartDom = document.getElementById('main');
    if (chartDom) {
      this.chartInstance = echarts.init(chartDom);
      echarts.registerMap('ch', geoChData as any);
      const option = this.graphChartService.getOption();
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
}
