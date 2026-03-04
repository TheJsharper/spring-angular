import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { GraphChartService } from '../services/GraphChartService';


@Component({
  selector: 'lib-graph-chart',
  imports: [MatButtonModule],
  templateUrl: './graph-chart.html',
  styleUrls: ['./graph-chart.scss'],
})
export class GraphChart implements OnInit, OnDestroy {
  private router = inject(Router);

  private route = inject(ActivatedRoute);

  private chartInstance: echarts.ECharts | undefined;

  private graphChartService = inject(GraphChartService);

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
      const option = this.graphChartService.getOptions();
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
