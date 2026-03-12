import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { ParallelChartService } from '@services';
import { Subscription } from 'rxjs';

@Component({
  selector: 'lib-parallel-chart',
  imports: [MatButtonModule],
  templateUrl: './parallel-chart.html',
  styleUrls: ['./parallel-chart.scss'],
})
export class ParallelChart implements OnInit, OnDestroy {
  private parallelChartService = inject(ParallelChartService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private subscription: Subscription = new Subscription();
  private chartInstance: echarts.ECharts | undefined;

  ngOnInit(): void {
    this.subscription.add(
      this.parallelChartService.getData().subscribe(data => {
        const normalizedData = this.parallelChartService.normalizeData(data);
        const options = this.parallelChartService.getOptions(normalizedData);
        this.chartInstance = echarts.init(document.getElementById('main') as HTMLElement);
        this.chartInstance.setOption(options);
      })
    );
  }

  ngOnDestroy(): void {
      if (this.chartInstance) {
      this.chartInstance.dispose();
    }
    this.subscription.unsubscribe();
  }

  stopSeries(): void {
    // Logic to stop series
  }

  nav(): void {
    this.router.navigate(['../dashboard'], { relativeTo: this.route });
  }
}
