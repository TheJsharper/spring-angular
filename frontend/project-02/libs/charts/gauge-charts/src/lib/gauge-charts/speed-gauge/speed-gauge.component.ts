import { BreakpointObserver } from "@angular/cdk/layout";
import { Component, inject, OnDestroy, OnInit } from "@angular/core";
import { SpeedGaugeChartService } from "@services";
import * as echarts from 'echarts';
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
        this.subscription.add(this.breakpointObserver.observe('(max-width: 600px)').subscribe(() =>
            this.breakpointChanged()
        ));

        this.chartInstance.setOption(options);
    }
    private breakpointChanged(): void {
        if (this.breakpointObserver.isMatched('(max-width: 600px)')) {
            this.chartInstance?.resize({ height: 300, width: 300, silent: true });
            this.chartInstance?.setOption(this.speedGaugeChartService.getOptionMobile());
            echarts.registerUpdateLifecycle('afterupdate', () => {
                this.chartInstance?.resize({ height: 300, width: 300, silent: true });
            });
            console.log('mobile');
        } else {
            console.log('desktop');
            this.chartInstance?.resize({ height: 700, width: 600, silent: true });
            this.chartInstance?.setOption(this.speedGaugeChartService.getOption());
            echarts.registerUpdateLifecycle('afterupdate', () => {
                this.chartInstance?.resize({ height: 700, width: 600, silent: true });
            });
        }
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
        if (this.chartInstance) {
            this.chartInstance.dispose();
        }
    }

}