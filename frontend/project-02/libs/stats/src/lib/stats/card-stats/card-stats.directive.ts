import { Directive, inject, OnInit, TemplateRef, ViewContainerRef } from "@angular/core";

@Directive({
    selector: "[libCardStats]",
    standalone: true,
})
export class CardStatsDirective implements OnInit {
    private templateRef = inject(TemplateRef);
    private viewContainerRef = inject(ViewContainerRef);

    ngOnInit(): void {
        this.viewContainerRef.createEmbeddedView(this.templateRef);

    }
}