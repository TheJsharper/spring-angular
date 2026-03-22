import { Component, ContentChild, input, TemplateRef } from "@angular/core";
import { MatCardModule } from "@angular/material/card";
import { CardStatsDirective } from "./card-stats.directive";
import { NgTemplateOutlet } from "@angular/common";
import { ActionStatsDirective } from "./directives/action-stats.directive";

@Component({
    selector: "lib-card-stats",
    imports: [MatCardModule, NgTemplateOutlet],
    templateUrl: "./card-stats.component.html",
    styleUrls: ["./card-stats.component.scss"],
})
export class CardStatsComponent {

    @ContentChild(CardStatsDirective, { read: TemplateRef, static: true })
    iconTemplate!: TemplateRef<any>;

    @ContentChild(ActionStatsDirective, { read: TemplateRef, static: true })
    actionTemplate!: TemplateRef<any>;

    title = input<string>();

    subTitle = input<string>();
}