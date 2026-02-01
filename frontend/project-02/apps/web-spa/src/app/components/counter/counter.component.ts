import { AsyncPipe } from "@angular/common";
import { Component, inject } from "@angular/core";
import { Store } from "@ngrx/store";
import { CounterState } from "./store/counter.reducers";
import { map, Observable } from "rxjs";
import { countFeature } from "./store/counter.selectors";
import { MatButtonModule } from "@angular/material/button";

@Component({
    selector: "app-counter",
    templateUrl: "./counter.component.html",
    imports: [AsyncPipe, MatButtonModule],
    styleUrls: ["./counter.component.scss"],
})
export class CounterComponent {
    count: Observable<number>;
    constructor(private store: Store<CounterState>) {
        this.count = this.store.select(countFeature).pipe(
            map(state => state.count)
        );
    }
    increment() {
        this.store.dispatch({ type: '[Counter] Increment' });
    }
    decrement() {
        this.store.dispatch({ type: '[Counter] Decrement' });
    }
    reset() {
        this.store.dispatch({ type: '[Counter] Reset' });
    }
}