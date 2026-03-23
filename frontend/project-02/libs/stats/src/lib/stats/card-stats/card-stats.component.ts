import { Component, ContentChild, Input, input, OnInit, TemplateRef } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { CardStatsDirective } from "./card-stats.directive";
import { NgTemplateOutlet } from "@angular/common";
import { ActionStatsDirective } from "./directives/action-stats.directive";
import { StatsDashboardItem } from "../../models/stats-dashboard.model";
import { StatsIconItem } from "../../models/stats-icons.model";

@Component({
    selector: "lib-card-stats",
    imports: [MatCardModule, NgTemplateOutlet],
    templateUrl: "./card-stats.component.html",
    styleUrls: ["./card-stats.component.scss"],
})
export class CardStatsComponent implements OnInit {

    @ContentChild(CardStatsDirective, { read: TemplateRef, static: true })
    iconTemplate!: TemplateRef<any>;

    @ContentChild(ActionStatsDirective, { read: TemplateRef, static: true })
    actionTemplate!: TemplateRef<any>;

    /*title = input<string>();

    subTitle = input<string>();*/
    @Input() dashboardItem!: StatsDashboardItem & Partial<StatsIconItem>;

    ngOnInit(): void {
        console.log(this.iconTemplate);
        console.log(this.actionTemplate);
        console.log(this.dashboardItem);
    }

}