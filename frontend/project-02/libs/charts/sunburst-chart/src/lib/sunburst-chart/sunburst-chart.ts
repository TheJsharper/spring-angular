import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { SunburstChartService } from '@services';
import { Subscription } from 'rxjs';
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

  private subscription: Subscription= new Subscription();

  ngOnInit(): void {
    // Initialize chart
    this.subscription.add(
      this.sunburstChartService.getDataFromAsset().subscribe(data => {
        const options = this.sunburstChartService.getOptions(data);
        this.chartInstance = echarts.init(document.getElementById('main') as HTMLElement);
        this.chartInstance.setOption(options);
      })
    );
  }

  ngOnDestroy(): void {
    // Cleanup chart
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
    this.subscription.unsubscribe();
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
