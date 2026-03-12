import { Injectable } from "@angular/core";

@Injectable()
export class GaugeChartService {
    constructor() { }

    getSimpleGaugeOptions() {
        const option = {
            tooltip: {
                formatter: '{a} <br/>{b} : {c}%'
            },
            series: [
                {
                    name: 'Pressure',
                    type: 'gauge',
                    detail: {
                        formatter: '{value}'
                    },
                    data: [
                        {
                            value: 50,
                            name: 'SCORE'
                        }
                    ]
                }
            ]
        };
        return option;
    }
}