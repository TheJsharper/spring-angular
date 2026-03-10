import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { ActivatedRoute, Router } from '@angular/router';
import{ TreeChartService } from '@services';
import * as echarts from 'echarts';
import { Subscription } from 'rxjs';
@Component({
  selector: 'lib-tree-chart',
  imports: [MatButtonModule],
  templateUrl: './tree-chart.html',
  styleUrls: ['./tree-chart.scss'],
})
export class TreeChart implements OnInit, OnDestroy {
  private router: Router = inject(Router);

  private route: ActivatedRoute = inject(ActivatedRoute);

  private treeChartService: TreeChartService = inject(TreeChartService);

  private chartInstance: echarts.ECharts | undefined;

  private subscription: Subscription = new Subscription();

  ngOnInit(): void {
    const chartDom = document.getElementById('main');

    this.chartInstance = echarts.init(chartDom);

    this.subscription.add(
      this.treeChartService.getOptionWithData().subscribe(option => {
        if (this.chartInstance) {
          this.chartInstance.setOption(option);
        }
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
      this.chartInstance.clear();
    }
  }

  nav(): void {
    this.router.navigate(['../dashboard'], { relativeTo: this.route });
  }
}
