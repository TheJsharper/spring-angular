import { BreakpointObserver } from "@angular/cdk/layout";
import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { GaugeChartService } from '@services';
import * as echarts from 'echarts';
import { Subscription } from "rxjs";
@Component({
  selector: 'app-simple-gauge',
  imports: [MatButtonModule],
  templateUrl: './simple-gauge.component.html',
  styleUrls: ['./simple-gauge.component.scss']
})
export class SimpleGaugeComponent implements OnInit, OnDestroy {

  private chartInstance: echarts.ECharts | undefined;

  private breakpointObserver = inject(BreakpointObserver);

  private readonly subscription = new Subscription();

  constructor(private gaugeChartService: GaugeChartService) { }

  ngOnInit(): void {
    const options = this.gaugeChartService.getSimpleGaugeOptions();
    this.chartInstance = echarts.init(document.getElementById('gauge-chart-simple') as HTMLElement);
    this.chartInstance.setOption(options);
    this.subscription.add(this.breakpointObserver.observe('(max-width: 600px)').subscribe(() =>
      this.breakpointChanged()
    ));
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

  private breakpointChanged(): void {
    if (this.breakpointObserver.isMatched('(max-width: 600px)')) {
      this.chartInstance?.resize({ height: 300, width: 300 });
    } else {
      this.chartInstance?.resize({ height: 700, width: 600 });
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }
}