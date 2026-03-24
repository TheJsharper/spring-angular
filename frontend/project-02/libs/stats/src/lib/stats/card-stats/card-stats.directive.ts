import { AfterContentInit, Directive, inject, OnInit, TemplateRef, ViewContainerRef } from "@angular/core";

@Directive({
    selector: "[libCardStats]",
    standalone: true,
})
export class CardStatsDirective implements OnInit, AfterContentInit {
    public templateRef: TemplateRef<any> = inject(TemplateRef);
    public viewContainerRef: ViewContainerRef = inject(ViewContainerRef);

    ngOnInit(): void {
        this.viewContainerRef.createEmbeddedView(this.templateRef);

    }

    ngAfterContentInit(): void {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
    }
}