import { Component, inject } from '@angular/core';
import { GraphLinesChartService } from '@services';

@Component({
  selector: 'lib-graph-lines-chart',
  imports: [],
  templateUrl: './graph-lines-chart.html',
  styleUrls: ['./graph-lines-chart.scss'],
})
export class GraphLinesChart {
  private graphLinesChartService = inject(GraphLinesChartService);

  constructor() {
    this.graphLinesChartService.fetchData();
  }
}
