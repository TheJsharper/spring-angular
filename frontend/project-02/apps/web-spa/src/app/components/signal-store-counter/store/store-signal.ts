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