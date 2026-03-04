import { Injectable } from '@angular/core';
import { ECBasicOption } from 'echarts/types/dist/shared';

@Injectable()
export class BoxplotChartService {




    public getChartOption(): ECBasicOption {
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
        const option: ECBasicOption = {
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

    public getOptions() {
        const options:ECBasicOption = {
            series: [
                {
                    data: [
                        {
                            x: 'Category A',
                            y: [54, 66, 69, 75, 88],
                            goals: [
                                {
                                    value: 90,
                                    strokeWidth: 10,
                                    strokeHeight: 0,
                                    strokeLineCap: 'round',
                                    strokeColor: '#F283B6',
                                },
                                {
                                    value: 93,
                                    strokeWidth: 10,
                                    strokeHeight: 0,
                                    strokeLineCap: 'round',
                                    strokeColor: '#F283B6',
                                },
                            ],
                        },
                        {
                            x: 'Category B',
                            y: [43, 65, 69, 76, 81],
                        },
                        {
                            x: 'Category C',
                            y: [31, 39, 45, 51, 59],
                        },
                        {
                            x: 'Category D',
                            y: [39, 46, 55, 65, 71],
                            goals: [
                                {
                                    value: 30,
                                    strokeWidth: 10,
                                    strokeHeight: 0,
                                    strokeLineCap: 'round',
                                    strokeColor: '#F283B6',
                                },
                                {
                                    value: 32,
                                    strokeWidth: 10,
                                    strokeHeight: 0,
                                    strokeLineCap: 'round',
                                    strokeColor: '#F283B6',
                                },
                                {
                                    value: 76,
                                    strokeWidth: 10,
                                    strokeHeight: 0,
                                    strokeLineCap: 'round',
                                    strokeColor: '#F283B6',
                                },
                            ],
                        },
                        {
                            x: 'Category E',
                            y: [41, 49, 58, 61, 67],
                        },

                    ],
                },
            ],
            chart: {
                type: 'boxPlot',
                height: 350
            },
            title: {
                text: 'Horizontal BoxPlot with Outliers',
                align: 'left'
            },
            plotOptions: {
                bar: {
                    horizontal: true,
                    barHeight: '40%'
                },
                boxPlot: {
                    colors: {
                        upper: '#B6C454',
                        lower: '#EDBFB7'
                    }
                }
            },
            stroke: {
                colors: ['#333']
            }
        };


        return options;
    }

}
