import { AsyncPipe, NgComponentOutlet } from "@angular/common";
import { AfterContentInit, ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { ActivatedRoute, Router } from "@angular/router";
import { StatsDashboardItem } from "../../models/stats-dashboard.model";
import { StatsIconItem } from "../../models/stats-icons.model";
import { StatsService } from "../../services/stats.service";
import { CardStatsComponent } from "../card-stats/card-stats.component";
import { CardStatsDirective } from "../card-stats/card-stats.directive";
import { ActionStatsDirective } from "../card-stats/directives/action-stats.directive";

@Component({
    selector: 'lib-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    imports: [MatButtonModule, MatCardModule, CardStatsComponent, CardStatsDirective, ActionStatsDirective],
    changeDetection: ChangeDetectionStrategy.OnPush
    
})
export class DashboardComponent implements AfterContentInit {
    
    router: Router = inject(Router);

    route: ActivatedRoute = inject(ActivatedRoute);
    
    statsService: StatsService = inject(StatsService);
    
    data: (StatsDashboardItem & StatsIconItem )[] = [];

    ngAfterContentInit(): void {
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