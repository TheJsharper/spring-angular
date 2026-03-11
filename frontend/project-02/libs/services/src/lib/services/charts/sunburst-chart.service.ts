import { inject, Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { SunburstData } from "../../types/coffe.types";
import { Observable } from "rxjs";
import { EChartsOption } from "echarts";

@Injectable()
export class SunburstChartService {
    private http: HttpClient = inject(HttpClient);

    getDataFromAsset(): Observable<SunburstData[]> {
        return this.http.get<SunburstData[]>('assets/services/coffee/coffee.json');
    }
    getOptions(data: SunburstData[]):EChartsOption {
        const options: EChartsOption = {
            title: {
                text: 'WORLD COFFEE RESEARCH SENSORY LEXICON',
                subtext: 'Source: https://worldcoffeeresearch.org/work/sensory-lexicon/',
                textStyle: {
                    fontSize: 14,
                    align: 'center'
                },
                subtextStyle: {
                    align: 'center'
                },
                sublink: 'https://worldcoffeeresearch.org/work/sensory-lexicon/'
            },
            series: {
                type: 'sunburst',
                data: data,
                radius: [0, '95%'],
                sort: undefined,
                emphasis: {
                    focus: 'ancestor'
                },
                levels: [
                    {},
                    {
                        r0: '15%',
                        r: '35%',
                        itemStyle: {
                            borderWidth: 2
                        },
                        label: {
                            rotate: 'tangential'
                        }
                    },
                    {
                        r0: '35%',
                        r: '70%',
                        label: {
                            align: 'right'
                        }
                    },
                    {
                        r0: '70%',
                        r: '72%',
                        label: {
                            position: 'outside',
                            padding: 3,
                            silent: false
                        },
                        itemStyle: {
                            borderWidth: 3
                        }
                    }
                ]
            }
        };
        return options;
    }

}