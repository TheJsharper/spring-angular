import { Directive, inject, OnInit, TemplateRef, ViewContainerRef } from "@angular/core";

@Directive({
    selector: "[libActionStats]",
    standalone: true,
})
export class ActionStatsDirective implements OnInit {
    private templateRef = inject(TemplateRef);
    private viewContainerRef = inject(ViewContainerRef);

    ngOnInit(): void {

        this.viewContainerRef.createEmbeddedView(this.templateRef);
    }
}