import { CommonModule } from "@angular/common";
import { Component, inject, OnInit } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { ActivatedRoute, Router } from "@angular/router";
import { StatsService } from "../../services/stats.service";
import { CardStatsComponent } from "../card-stats/card-stats.component";
import { CardStatsDirective } from "../card-stats/card-stats.directive";
import { ActionStatsDirective } from "../card-stats/directives/action-stats.directive";
import { StatsIconItem } from "../../models/stats-icons.model";
import { StatsDashboardItem } from "../../models/stats-dashboard.model";

@Component({
    selector: 'lib-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    imports: [MatButtonModule, MatCardModule, CardStatsComponent, CardStatsDirective, ActionStatsDirective, CommonModule]
})
export class DashboardComponent implements OnInit {
    
    router: Router = inject(Router);

    route: ActivatedRoute = inject(ActivatedRoute);
    
    statsService: StatsService = inject(StatsService);
    data: (StatsDashboardItem & StatsIconItem )[] = [];

    ngOnInit(): void {
        this.statsService.getData().subscribe((data) => {
            console.log(data);
            this.data = data;
        });
    }

    trackBy(index: number, dashboardItem: StatsDashboardItem & StatsIconItem): any {
        return dashboardItem.name; // Assuming 'name' is a unique identifier for each dashboard item
    }
    
    nav(goTo: string): void {
        this.router.navigate([`/stats/${goTo}`], { relativeTo: this.route });
    }
}