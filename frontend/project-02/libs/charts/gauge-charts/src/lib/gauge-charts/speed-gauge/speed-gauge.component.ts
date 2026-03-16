import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { SpeedGaugeChartService } from "@services";
import * as echarts from 'echarts';
import { BreakpointObserver, BreakpointState } from "@angular/cdk/layout";
import { Subscription } from "rxjs";

@Component({
    selector: 'bp-speed-gauge',
    templateUrl: './speed-gauge.component.html',
    styleUrls: ['./speed-gauge.component.scss']
})
export class SpeedGaugeComponent implements OnInit, OnDestroy {
    private chartInstance: echarts.ECharts | undefined;

    private breakpointObserver = inject(BreakpointObserver);

    private speedGaugeChartService: SpeedGaugeChartService = inject(SpeedGaugeChartService);

    private readonly subscription: Subscription = new Subscription();

    ngOnInit(): void {


        const options = this.speedGaugeChartService.getOption();

        this.chartInstance = echarts.init(document.getElementById('speed-gauge') as HTMLElement);
        this.chartInstance.resize({ height: 500, width: 700 });

                this.subscription.add(this.breakpointObserver.observe('(max-width: 600px)').subscribe(()     => 
                    this.breakpointChanged()
                ));

        this.chartInstance.setOption(options);
    }
    private breakpointChanged(): void {
        if (this.breakpointObserver.isMatched('(max-width: 600px)')) {
            this.chartInstance?.resize({ height: 300, width: 300 });
        } else {
            this.chartInstance?.resize({ height: 700, width: 600 });
        }   
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

}