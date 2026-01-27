import { Injectable } from "@angular/core";
import { map, Observable, of } from "rxjs";

export interface PeriodicElement {
    id: string
    name: string;
    position: number | null;
    weight: number | null;
    symbol: string;
}
@Injectable()
export class ProductsService {
    periodictElement: Observable<Array<PeriodicElement>>;
    constructor() {
        this.periodictElement = of([
            { id: crypto.randomUUID(), position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H' },
            { id: crypto.randomUUID(), position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
            { id: crypto.randomUUID(), position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
            { id: crypto.randomUUID(), position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
            { id: crypto.randomUUID(), position: 5, name: 'Boron', weight: 10.811, symbol: 'B' },
            { id: crypto.randomUUID(), position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C' },
            { id: crypto.randomUUID(), position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N' },
            { id: crypto.randomUUID(), position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O' },
            { id: crypto.randomUUID(), position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F' },
            { id: crypto.randomUUID(), position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne' },
        ]);
    }
    getAllPeriodictElement(): Observable<Array<PeriodicElement>> {
        return this.periodictElement;
    }

    getPeriodictElementById(id: string): Observable<PeriodicElement | undefined> {

        return this.periodictElement.pipe(
            map((elements) => elements.find((el) => el.id === id))
        );
    }

    createPeriodictElement(element: Omit<PeriodicElement, 'id'>): Observable<Array<PeriodicElement>> {
        const newElement: PeriodicElement = { id: crypto.randomUUID(), ...element };

        return this.periodictElement.pipe(
            map((elements) => {
                const updatedElements = [...elements, newElement];
                this.periodictElement = of(updatedElements);
                return updatedElements;
            })
        );
    }

    deletePeriodictElement(id: string): Observable<Array<PeriodicElement>> {
        return this.periodictElement.pipe(
            map((elements) => {
                const updatedElements = elements.filter((el) => el.id !== id);
                this.periodictElement = of(updatedElements);
                return updatedElements;
            })
        );
    }

    patchPeriodictElement(id: string, updatedFields: Partial<Omit<PeriodicElement, 'id'>>): Observable<Array<PeriodicElement>> {
        return this.periodictElement.pipe(
            map((elements) => {
                const elementIndex = elements.findIndex((el) => el.id === id);
                if (elementIndex === -1) {
                    return elements;
                }
                const updatedElement = { ...elements[elementIndex], ...updatedFields };
                const updatedElements = [...elements];
                updatedElements[elementIndex] = updatedElement;
                this.periodictElement = of(updatedElements);
                return updatedElements;
            })
        );
    }


}