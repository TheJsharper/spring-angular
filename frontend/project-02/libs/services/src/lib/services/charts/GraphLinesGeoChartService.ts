import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable()
export class GraphLinesGeoChartService {
  private httpClient = inject(HttpClient);



  getGeoData(): Observable<{type: string, features: any[]} > {
    return this.httpClient.get<{type: string, features: any[]} > ('assets/services/geo/usa.json');
  }

}
