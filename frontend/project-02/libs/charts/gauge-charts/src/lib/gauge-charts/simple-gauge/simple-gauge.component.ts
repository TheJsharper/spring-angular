import { Component, OnDestroy, OnInit } from "@angular/core";
import { FunnelChartService, GaugeChartService } from '@services';
import * as echarts from 'echarts';
@Component({
  selector: 'app-simple-gauge',
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

  ngOnDestroy(): void {
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
}