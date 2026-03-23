import { Component, inject, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { ActivatedRoute, Router } from "@angular/router";
import { CardStatsComponent } from "../card-stats/card-stats.component";
import { CardStatsDirective } from "../card-stats/card-stats.directive";
import { ActionStatsDirective } from "../card-stats/directives/action-stats.directive";
import { StatsService } from "../../services/stats.service";

@Component({
    selector: 'lib-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    imports: [MatButtonModule, MatCardModule, CardStatsComponent, CardStatsDirective, ActionStatsDirective]
})
export class DashboardComponent implements OnInit {
    
    router: Router = inject(Router);

    route: ActivatedRoute = inject(ActivatedRoute);
    
    statsService: StatsService = inject(StatsService);

    ngOnInit(): void {
        this.statsService.getData();
    }

    nav(goTo: string): void {
        this.router.navigate([`/stats/${goTo}`], { relativeTo: this.route });
    }
}