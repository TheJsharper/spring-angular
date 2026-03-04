import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { BoxplotChartService } from './services/BoxplotChartService';

@Component({
  selector: 'lib-boxplot-chart',
  imports: [MatButtonModule],
  templateUrl: './boxplot-chart.html',
  styleUrls: ['./boxplot-chart.scss'],
})
export class BoxplotChart implements OnInit, OnDestroy {
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private chartInstance: echarts.ECharts | undefined;
  private boxplotChartService = inject(BoxplotChartService);

  ngOnInit(): void {
    this.initChart();
  }

  ngOnDestroy(): void {
    this.disposeChart();
  }

  private initChart(): void {
    const chartDom = document.getElementById('main');
    if (chartDom) {
      this.chartInstance = echarts.init(chartDom);
      const option = this.boxplotChartService.getChartOption();
      //const option = this.boxplotChartService.getOptions();
      this.chartInstance.setOption(option);
    }
  }

  private disposeChart(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
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
