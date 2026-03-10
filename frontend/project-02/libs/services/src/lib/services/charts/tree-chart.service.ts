import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Flare } from "../../types/flare.types";
import { map, Observable } from "rxjs";
import * as echarts from 'echarts/core';
import { TreeChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import { EChartsOption } from 'echarts'

echarts.use([TreeChart, CanvasRenderer]);

@Injectable()
export class TreeChartService {
    private httpClient: HttpClient = inject(HttpClient);


    public getData(): Observable<Flare> {
        return this.httpClient.get<Flare>('assets/services/flare/flare.json');
    }

    public getOptionWithData(): Observable<EChartsOption> {

        return this.getData().pipe(map(data => {
            const option: EChartsOption = this.getOption();
            (option.series as any[])[0].data = [data];
            return option;
        }))
    }


    public getOption(): EChartsOption {
        const option: EChartsOption = {
            tooltip: {
                trigger: 'item',
                triggerOn: 'mousemove'
            },
            series: [
                {
                    type: 'tree',
                    data: [],
                    top: '1%',
                    left: '7%',
                    bottom: '1%',
                    right: '20%',
                    symbolSize: 7,
                    label: {
                        position: 'left',
                        verticalAlign: 'middle',
                        align: 'right',
                        fontSize: 9
                    },
                    leaves: {
                        label: {
                            position: 'right',
                            verticalAlign: 'middle',
                            align: 'left'
                        }
                    },
                    emphasis: {
                        focus: 'descendant'
                    },
                    expandAndCollapse: true,
                    animationDuration: 550,
                    animationDurationUpdate: 750
                }
            ]
        }
        return option;
    }
}