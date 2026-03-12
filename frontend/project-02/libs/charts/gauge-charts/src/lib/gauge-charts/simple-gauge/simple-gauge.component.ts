import { Component, OnDestroy, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { GaugeChartService } from '@services';
import * as echarts from 'echarts';
@Component({
  selector: 'app-simple-gauge',
  imports: [MatButtonModule],
  templateUrl: './simple-gauge.component.html',
  styleUrls: ['./simple-gauge.component.scss']
})
export class SimpleGaugeComponent implements OnInit, OnDestroy {

  private chartInstance: echarts.ECharts | undefined;

  constructor(private gaugeChartService: GaugeChartService) { }

  ngOnInit(): void {
    const options = this.gaugeChartService.getSimpleGaugeOptions();
    this.chartInstance = echarts.init(document.getElementById('gauge-chart-simple') as HTMLElement);
    this.chartInstance.setOption(options);
  }

  upScore(): void {
    if (this.chartInstance) {
      const option = this.chartInstance.getOption() as {
        series?: Array<{ data?: Array<{ value?: number }> }>;
      };
      const currentValue = Number(option.series?.[0]?.data?.[0]?.value ?? 0);
      const newValue = Math.min(currentValue + 1, 100);

      this.chartInstance.setOption({
        series: [{
          data: [{ value: newValue, name: 'SCORE' }]
        }]
      });
    }
  }

  downScore(): void {
    if (this.chartInstance) {
      const option = this.chartInstance.getOption() as {
        series?: Array<{ data?: Array<{ value?: number }> }>;
      };
      const currentValue = Number(option.series?.[0]?.data?.[0]?.value ?? 0);
      const newValue = Math.max(currentValue - 1, 0);

      this.chartInstance.setOption({
        series: [{
          data: [{ value: newValue, name: 'SCORE' }]
        }]
      });
    }
  }

  ngOnDestroy(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
}