import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Nutrient } from "../../types/nutrient.type";
import { Observable } from "rxjs";
import * as echarts from 'echarts';

@Injectable()
export class ParallelChartService {

    private http: HttpClient = inject(HttpClient);
    private readonly indices = {
        name: 0,
        group: 1,
        id: 16
    };
    private readonly schema = [
        { name: 'name', index: 0 },
        { name: 'group', index: 1 },
        { name: 'protein', index: 2 },
        { name: 'calcium', index: 3 },
        { name: 'sodium', index: 4 },
        { name: 'fiber', index: 5 },
        { name: 'vitaminc', index: 6 },
        { name: 'potassium', index: 7 },
        { name: 'carbohydrate', index: 8 },
        { name: 'sugars', index: 9 },
        { name: 'fat', index: 10 },
        { name: 'water', index: 11 },
        { name: 'calories', index: 12 },
        { name: 'saturated', index: 13 },
        { name: 'monounsat', index: 14 },
        { name: 'polyunsat', index: 15 },
        { name: 'id', index: 16 }
    ];

    private readonly groupCategories: string[] = [];

    private readonly groupColors: string[] = [];

    getData(): Observable<Nutrient> {

        return this.http.get<Nutrient>('/assets/services/nutrients/nutrients.json');
    }

    normalizeData(data: Nutrient): Nutrient {
        const groupMap: Record<string, number> = {};

        this.groupCategories.length = 0;
        this.groupColors.length = 0;

        data.forEach((row) => {
            const groupName = String(row[this.indices.group]);
            if (!groupMap.hasOwnProperty(groupName)) {
                groupMap[groupName] = 1;
            }
        });
        data.forEach((row) => {
            row.forEach((item, index) => {
                if (
                    index !== this.indices.name &&
                    index !== this.indices.group &&
                    index !== this.indices.id
                ) {
                    // Convert null to zero, as all of them under unit "g".
                    row[index] = parseFloat(item as string) || 0;
                }
            });
        });
        for (const groupName in groupMap) {
            if (groupMap.hasOwnProperty(groupName)) {
                this.groupCategories.push(groupName);
            }
        }
        const hStep = this.groupCategories.length > 1
            ? Math.round(300 / (this.groupCategories.length - 1))
            : 0;

        for (let i = 0; i < this.groupCategories.length; i++) {
            this.groupColors.push(echarts.color.modifyHSL('#5A94DF', hStep * i));
        }

        return data;
    }
    public getOptions(data: Nutrient): echarts.EChartsOption {
        const lineStyle = {
            width: 0.5,
            opacity: 0.05
        };
        return {
            backgroundColor: '#333',
            tooltip: {
                padding: 10,
                backgroundColor: '#222',
                borderColor: '#777',
                borderWidth: 1
            },
            title: [
                {
                    text: 'Groups',
                    top: 0,
                    left: 0,
                    textStyle: {
                        color: '#fff'
                    }
                }
            ],
            visualMap: {
                show: true,
                type: 'piecewise',
                categories: this.groupCategories,
                dimension: this.indices.group,
                inRange: {
                    color: this.groupColors //['#d94e5d','#eac736','#50a3ba']
                },
                outOfRange: {
                    color: ['#ccc'] //['#d94e5d','#eac736','#50a3ba']
                },
                top: 20,
                textStyle: {
                    color: '#fff'
                },
                realtime: false
            },
            parallelAxis: [
                { dim: 16, name: this.schema[16].name, scale: true, nameLocation: 'end' },
                { dim: 2, name: this.schema[2].name, nameLocation: 'end' },
                { dim: 4, name: this.schema[4].name, nameLocation: 'end' },
                { dim: 3, name: this.schema[3].name, nameLocation: 'end' },
                { dim: 5, name: this.schema[5].name, nameLocation: 'end' },
                { dim: 6, name: this.schema[6].name, nameLocation: 'end' },
                { dim: 7, name: this.schema[7].name, nameLocation: 'end' },
                { dim: 8, name: this.schema[8].name, nameLocation: 'end' },
                { dim: 9, name: this.schema[9].name, nameLocation: 'end' },
                { dim: 10, name: this.schema[10].name, nameLocation: 'end' },
                { dim: 11, name: this.schema[11].name, nameLocation: 'end' },
                { dim: 12, name: this.schema[12].name, nameLocation: 'end' },
                { dim: 13, name: this.schema[13].name, nameLocation: 'end' },
                { dim: 14, name: this.schema[14].name, nameLocation: 'end' },
                { dim: 15, name: this.schema[15].name, nameLocation: 'end' }
            ],
            parallel: {
                left: 280,
                top: 20,
                // top: 150,
                // height: 300,
                width: 400,
                layout: 'vertical',
                parallelAxisDefault: {
                    type: 'value',
                    name: 'nutrients',
                    nameLocation: 'end',
                    nameGap: 20,
                    nameTextStyle: {
                        color: '#fff',
                        fontSize: 14
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#aaa'
                        }
                    },
                    axisTick: {
                        lineStyle: {
                            color: '#777'
                        }
                    },
                    splitLine: {
                        show: false
                    },
                    axisLabel: {
                        color: '#fff'
                    },
                    realtime: false
                }
            },
            animation: false,
            series: [
                {
                    name: 'nutrients',
                    type: 'parallel',
                    lineStyle: lineStyle,
                    inactiveOpacity: 0,
                    activeOpacity: 0.01,
                    progressive: 500,
                    smooth: true,
                    data: data
                }
            ]
        };
    }
}