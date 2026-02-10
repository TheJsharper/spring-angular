import { Component, inject } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatLabel } from "@angular/material/form-field";
import { CounterStoreSignal } from "./store/store-signal";

@Component({
    selector: "app-signal-store-counter",
    templateUrl: "./signal-store-counter.component.html",
    styleUrls: ["./signal-store-counter.component.scss"],
    imports: [MatButton, MatLabel]
})
export class SignalStoreCounterComponent {
    protected store = inject(CounterStoreSignal);
}
