import { Component, OnInit } from '@angular/core';
import { SimpleGaugeComponent } from './simple-gauge/simple-gauge.component';
import { SpeedGaugeComponent } from './speed-gauge/speed-gauge.component';

@Component({
  selector: 'lib-gauge-charts',
  imports: [SimpleGaugeComponent, SpeedGaugeComponent],
  templateUrl: './gauge-charts.html',
  styleUrls: ['./gauge-charts.scss'],
})
export class GaugeCharts {}
