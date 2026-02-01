import { createFeature, createFeatureSelector } from "@ngrx/store";
import { CounterState } from "./counter.reducers";

//export const selectCount = (state: CounterState) => state.count;

export const countFeature = createFeatureSelector<CounterState>('count');