import { Component } from '@angular/core';

import { RouterOutlet } from '@angular/router';
import { StatsService } from '../services/stats.service';

@Component({
  selector: 'lib-stats',
  imports: [RouterOutlet],
  providers: [StatsService],
  template: '<router-outlet/>',
  styleUrls: ['./stats.scss'],
})
export class Stats { }
