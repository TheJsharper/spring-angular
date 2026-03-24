import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { map, mergeMap, Observable } from "rxjs";
import { StatsDashboard, StatsDashboardItem } from "../models/stats-dashboard.model";
import { StatsIconItem, StatsIcons } from "../models/stats-icons.model";

@Injectable({
    providedIn: 'root'
})
export class StatsService {
    private httpClient: HttpClient = inject(HttpClient);
    constructor() { }

    getData(): Observable<(StatsDashboardItem & StatsIconItem)[]> {

        const icons = this.httpClient.get<StatsIcons>('assets/stats/pics/list-icons.json');
        
        const values = this.httpClient.get<StatsDashboard>('assets/stats/ui/en/stats-dashboard.json').pipe(
            mergeMap((dashboard: StatsDashboard) =>
                icons.pipe(
                    map((iconsData: StatsIcons) =>

                        dashboard.reduce((acc, icon) => {
                            const item = iconsData.find(item => item.chart.toLowerCase().includes(icon.key.toLowerCase()));
                          //  console.log('Matching item for icon:', icon, 'is', item);
                            if (item) {
                                acc.push({ ...item, ...icon});
                            }
                            return acc;
                        }, [] as (StatsDashboardItem & StatsIconItem)[])
                    )

                )

            )
        );
        return values;

    }
}