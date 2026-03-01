import { Component, inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
    selector: 'lib-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    imports: [MatButtonModule, MatCardModule,]
})
export class DashboardComponent {
    router: Router = inject(Router);
    route: ActivatedRoute = inject(ActivatedRoute);

    nav(goTo: string): void {
        this.router.navigate([`/stats/${goTo}`], { relativeTo: this.route });
    }
}