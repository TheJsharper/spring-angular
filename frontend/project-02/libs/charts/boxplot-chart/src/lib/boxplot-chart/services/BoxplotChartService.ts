import { Injectable } from '@angular/core';

@Injectable()
export class BoxplotChartService {




    public getChartOption() {
        const categoryData = ['category0', 'category1', 'category2', 'category3', 'category4'];
        const barData = [30, 50, 70, 60, 80];
        const errorData = [
            ['category0', 10, 40],
            ['category1', 30, 60],
            ['category2', 50, 80],
            ['category3', 40, 70],
            ['category4', 60, 90]
        ];

        /**
         ** Option
         */
        const option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            title: {
                text: 'Horizontal Boxplot'
            },
            legend: {
                data: ['bar', 'error']
            },
            yAxis: {
                type: 'category',
                data: categoryData
            },
            xAxis: {},
            series: [
                {
                    type: 'bar',
                    name: 'bar',
                    data: barData,
                    itemStyle: {
                        color: '#cce0ff'
                    }
                },
                {
                    type: 'custom',
                    name: 'error',
                    itemStyle: {
                        borderWidth: 1.5
                    },
                    renderItem: function (params: any, api: any) {
                        const yValue = api.value(0);

                        const lowPoint = api.coord([api.value(1), yValue]);
                        const midPoint = api.coord([(api.value(1) + api.value(2)) / 2, yValue]);
                        const highPoint = api.coord([api.value(2), yValue]);

                        const halfHeight = api.size([1, 1])[1] * 0.1;


                        const style = api.style({
                            stroke: api.visual('color'),
                            fill: undefined
                        });
                        return {
                            type: 'group',
                            children: [
                                // Line
                                {
                                    type: 'line',
                                    transition: ['shape'],
                                    shape: {
                                        x1: lowPoint[0],
                                        y1: lowPoint[1],
                                        x2: highPoint[0],
                                        y2: highPoint[1]
                                    },
                                    style: style
                                },
                                // Low Value
                                {
                                    type: 'line',
                                    transition: ['shape'],
                                    shape: {
                                        x1: lowPoint[0],
                                        y1: lowPoint[1] - halfHeight,
                                        x2: lowPoint[0],
                                        y2: lowPoint[1] + halfHeight
                                    },
                                    style: style
                                },
                                // Mid Value
                                {
                                    type: 'line',
                                    transition: ['shape'],
                                    shape: {
                                        x1: midPoint[0],
                                        y1: midPoint[1] - halfHeight * 2,
                                        x2: midPoint[0],
                                        y2: midPoint[1] + halfHeight * 2
                                    },
                                    style: { ...style, lineWidth: style.lineWidth * 2 }
                                },
                                // High Value
                                {
                                    type: 'line',
                                    transition: ['shape'],
                                    shape: {
                                        x1: highPoint[0],
                                        y1: highPoint[1] - halfHeight,
                                        x2: highPoint[0],
                                        y2: highPoint[1] + halfHeight
                                    },
                                    style: style
                                }
                            ]
                        };
                    },
                    encode: {
                        x: [1, 2],
                        y: 0
                    },
                    data: errorData,
                    z: 100
                }
            ]
        };
        return option;
    }




}
