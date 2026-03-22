import { Component } from '@angular/core';

import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'lib-stats',
  imports: [RouterOutlet],
  template: '<router-outlet/>',
  styleUrls: ['./stats.scss'],
})
export class Stats { }
