import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { SunburstChartService } from '@services';
@Component({
  selector: 'lib-sunburst-chart',
  imports: [MatButtonModule],
  templateUrl: './sunburst-chart.html',
  styleUrls: ['./sunburst-chart.scss'],
})
export class SunburstChart implements OnInit, OnDestroy {
  private router = inject(Router);
  private sunburstChartService = inject(SunburstChartService);
  private route = inject(ActivatedRoute);
  private chartInstance: echarts.ECharts | undefined; 

  ngOnInit(): void {
    // Initialize chart
    const data = this.sunburstChartService.getData();
    const options = this.sunburstChartService.getOptions(data);
    this.chartInstance = echarts.init(document.getElementById('main') as HTMLElement);
    this.chartInstance.setOption(options);
  }

  ngOnDestroy(): void {
    // Cleanup chart
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }

  stopSeries(): void {
    // Stop chart series
    if (this.chartInstance) {
      this.chartInstance.clear();
    }
  }

  nav(): void {
    this.router.navigate(['/dashboard']);
  }
}
