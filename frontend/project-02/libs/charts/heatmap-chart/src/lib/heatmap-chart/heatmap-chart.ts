import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { HeatmapChartService } from '../services/HeatmapChartService';

@Component({
  selector: 'lib-heatmap-chart',
  imports: [MatButtonModule],
  templateUrl: './heatmap-chart.html',
  styleUrl: './heatmap-chart.scss',
})
export class HeatmapChart implements OnInit, OnDestroy {
  private router = inject(Router);

  private route = inject(ActivatedRoute);

  private chartInstance: echarts.ECharts | undefined;

  private heatmapChartService = inject(HeatmapChartService);


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
      const option = this.heatmapChartService.Option;
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
