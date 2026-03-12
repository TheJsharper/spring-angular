import { HttpClient } from "@angular/common/http";
import { inject } from "@angular/core";
import { Emergy } from "../../types/emergy.types";
import { Observable } from "rxjs";

export class SankeyChartService {

    private http: HttpClient = inject(HttpClient);

    getData(): Observable<Emergy> {
        return this.http.get<Emergy>('/assets/services/emergy/emergy.json');
    }
    getOptions(data: Emergy): echarts.EChartsOption {
        return {
            title: {
                text: 'Node Align Left'
            },
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'sankey',
                    emphasis: {
                        focus: 'adjacency'
                    },
                    nodeAlign: 'left',
                    data: data.nodes,
                    links: data.links,
                    lineStyle: {
                        color: 'source',
                        curveness: 0.5
                    }
                }
            ]
        }
    }
}