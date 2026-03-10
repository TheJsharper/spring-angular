import { HttpClient } from '@angular/common/http';
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { EChartsPackageSize } from "../../types/echarts-package-size";

@Injectable()
export class TreemapChartService {
    constructor(private http: HttpClient) { }

    getData(): Observable<EChartsPackageSize> {
        return this.http.get<EChartsPackageSize>('assets/services/package-size/echarts-package-size.json');
    }

    getOptionTreemap(data: EChartsPackageSize) {
        const treemapOption = {
            series: [
                {
                    type: 'treemap',
                    id: 'echarts-package-size',
                    animationDurationUpdate: 1000,
                    roam: false,
                    nodeClick: undefined,
                    data: data.children,
                    universalTransition: true,
                    label: {
                        show: true
                    },
                    breadcrumb: {
                        show: false
                    }
                }
            ]
        };
        return treemapOption;
    }

    getOptionSunburst(data: EChartsPackageSize) {
        const sunburstOption = {
            series: [
                {
                    type: 'sunburst',
                    id: 'echarts-package-size',
                    radius: ['20%', '90%'],
                    animationDurationUpdate: 1000,
                    nodeClick: undefined,
                    data: data.children,
                    universalTransition: true,
                    itemStyle: {
                        borderWidth: 1,
                        borderColor: 'rgba(255,255,255,.5)'
                    },
                    label: {
                        show: false
                    }
                }
            ]
        };
        return sunburstOption;
    }
}