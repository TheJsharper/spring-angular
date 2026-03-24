import { AfterContentInit, Directive, inject, OnInit, TemplateRef, ViewContainerRef } from "@angular/core";

@Directive({
    selector: "[libActionStats]",
    standalone: true,
})
export class ActionStatsDirective implements OnInit, AfterContentInit {
    public templateRef: TemplateRef<any> = inject(TemplateRef);
    public viewContainerRef: ViewContainerRef = inject(ViewContainerRef);

    ngOnInit(): void {

        this.viewContainerRef.createEmbeddedView(this.templateRef);
    }

    ngAfterContentInit(): void {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
    }
}