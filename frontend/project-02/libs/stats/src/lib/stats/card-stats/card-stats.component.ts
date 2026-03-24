import { AfterContentInit, ChangeDetectionStrategy, Component, ContentChild, input, OnInit, TemplateRef } from "@angular/core";
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
    viewProviders: [CardStatsDirective, ActionStatsDirective],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CardStatsComponent implements OnInit, AfterContentInit {

    @ContentChild(CardStatsDirective, { read: TemplateRef })
    iconTemplate!: TemplateRef<any>;

    @ContentChild(ActionStatsDirective, { read: TemplateRef, })
    actionTemplate!: TemplateRef<any>;

    readonly dashboardItem = input.required<StatsDashboardItem & StatsIconItem>(); // = {} as StatsDashboardItem & StatsIconItem;

    ngOnInit(): void {
        console.log(this.iconTemplate);
        console.log(this.actionTemplate);
        console.log(this.dashboardItem());
    }

    ngAfterContentInit(): void {
        console.log(this.iconTemplate);
        console.log(this.actionTemplate);
        console.log(this.dashboardItem());
    }


}