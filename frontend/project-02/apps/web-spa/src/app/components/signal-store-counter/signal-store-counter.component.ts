import { Component, inject } from "@angular/core";
import { MatButton } from "@angular/material/button";
import { MatLabel } from "@angular/material/form-field";
import { patchState, signalStore, withMethods, withState } from "@ngrx/signals";
export const CounterStoreSignal = signalStore(
    withState({
        count: 0,
    }),

    withMethods(state => ({
        increment() {
            patchState(state, {
                count: state.count() + 1
            });
        },
        decrement() {
            patchState(state, {
                count: state.count() - 1
            });
        },
        reset() {
            patchState(state, {
                count: 0
            });
        }
    }))
);
@Component({
    selector: "app-signal-store-counter",
    templateUrl: "./signal-store-counter.component.html",
    styleUrls: ["./signal-store-counter.component.scss"],
    imports: [MatButton, MatLabel]
})
export class SignalStoreCounterComponent {
    protected store = inject(CounterStoreSignal);
}
