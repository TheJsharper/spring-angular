import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import * as echarts from 'echarts';
import { Router, ActivatedRoute } from '@angular/router';
import { SankeyChartService } from '@services';
import { Subscription } from 'rxjs';

@Component({
  selector: 'lib-sankey-chart',
  imports: [MatButtonModule],
  templateUrl: './sankey-chart.html',
  styleUrls: ['./sankey-chart.scss'],
})
export class SankeyChart implements OnInit, OnDestroy {
  private sankeyChartService = inject(SankeyChartService);

  private router = inject(Router);

  private route = inject(ActivatedRoute);

  private subscription: Subscription = new Subscription();
  
  private chartInstance: echarts.ECharts | undefined;

  ngOnInit(): void {
    this.subscription.add(
      this.sankeyChartService.getData().subscribe(data => {
        const options = this.sankeyChartService.getOptions(data);
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
    if (this.chartInstance) {
      this.chartInstance.dispose();
    }
  }

  nav(): void {
    this.router.navigate(['../dashboard'], { relativeTo: this.route });
  }
}
