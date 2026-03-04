import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class GraphLinesChartService {

  private httpClient = inject(HttpClient);
  constructor() { }

  fetchData() {

    const baseUrl = 'https://echarts.apache.org/examples';
    this.httpClient.get(`${baseUrl}/data/asset/data/links-ny/links_ny_0.bin`,{responseType:'arraybuffer'}).subscribe((data) => {
      console.log('Data fetched:', data);
    });



  }

}
