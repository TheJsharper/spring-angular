import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { map, mergeMap, Observable } from "rxjs";
import { StatsDashboard } from "../models/stats-dashboard.model";
import { StatsIcons } from "../models/stats-icons.model";

@Injectable({
    providedIn: 'root'
})
export class StatsService {
    private httpClient: HttpClient = inject(HttpClient);
    constructor() { }

    getData(): Observable<(StatsDashboard[number] & Partial<StatsIcons[number]>)[]> {

        const icons = this.httpClient.get<StatsIcons>('assets/stats/pics/list-icons.json');
        
        const values = this.httpClient.get<StatsDashboard>('assets/stats/ui/en/stats-dashboard.json').pipe(
            mergeMap((dashboard: StatsDashboard) =>
                icons.pipe(
                    map((iconsData: StatsIcons) =>

                        dashboard.reduce((acc, icon) => {
                            const item = iconsData.find(item => item.name.toLowerCase().includes(icon.name.toLowerCase()));
                            if (item) {
                                acc.push({ ...item, ...icon, key: item.name });
                            } else acc.push({ ...icon, key:  icon.name });
                            return acc;
                        }, [] as (StatsDashboard[number] & Partial<StatsIcons[number] & { key: string }>)[])
                    )

                )

            )
        );
        return values;

    }
}