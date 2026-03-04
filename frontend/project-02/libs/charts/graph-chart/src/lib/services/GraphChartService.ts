import { Injectable } from '@angular/core';
import * as graph from '@services';
import {EChartsOption} from 'echarts'

@Injectable()
export class GraphChartService {

  constructor() { }

  public getOptions(): EChartsOption {

    const option: EChartsOption = {
      title: {
        text: 'Les Miserables',
        subtext: 'Default layout',
        top: 'bottom',
        left: 'right'
      },
      tooltip: {},
      legend: [
        {
          // selectedMode: 'single',
          data: graph.lesMiserableData.categories.map(function (a) {
            return a.name;
          })
        }
      ],
      series: [
        {
          name: 'Les Miserables',
          type: 'graph',
          layout: 'force',
          data: graph.lesMiserableData.nodes,
          links: graph.lesMiserableData.links,
          categories: graph.lesMiserableData.categories,
          roam: true,
          label: {
            position: 'right'
          },
          force: {
            repulsion: 100
          }
        }
      ]
    };
    return option;

  }
}
