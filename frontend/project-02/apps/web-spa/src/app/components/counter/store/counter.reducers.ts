import { createReducer, on } from "@ngrx/store";
import { decrement, increment, reset } from "./counter.actions";

export const counterFeatureKey = 'counter';

export interface CounterState {
    count: number;
}
export const initialState: CounterState = {
    count: 0,
};
export const counterReducer = createReducer(initialState,
    on(increment, state => ({ count: state.count + 1 })),
    on(decrement, state => ({ count: state.count - 1 })),
    on(reset, state => ({ count: 0 }))
)