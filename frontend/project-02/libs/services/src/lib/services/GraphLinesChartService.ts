import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, from, map, merge, mergeMap, throwError } from 'rxjs';

@Injectable()
export class GraphLinesChartService {

  private httpClient = inject(HttpClient);
  constructor() { }

  fetchData() {

    /*const baseUrl = 'https://echarts.apache.org/examples';
    const hearders = {
      "sec-ch-ua": "\"Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145\"",
      "sec-ch-ua-mobile": "?1",
      "sec-ch-ua-platform": "\"Android\""
    };
    this.httpClient.get(`${baseUrl}/data/asset/data/links-ny/links_ny_0.bin`,{responseType:'arraybuffer', headers: hearders}).subscribe({
      next: (data) => {
        console.log('Data fetched:', data);
      },
      error: (error) => {
        this.handleError(error);
      }
    });*/
     const chunkLen = Array.from({ length: 32 }).map((_, i) => i);
     return from(chunkLen).pipe
       (
         mergeMap((chunk) => this.httpClient.get(`assets/services/chanks/links_ny_${chunk}.bin`, { responseType: 'arraybuffer' }).pipe(
            map((rawData) => {
              const rawDataArray = new Float32Array(rawData);
              const data = new Float64Array(rawDataArray.length - 2);

              const offsetX = rawDataArray[0];
              const offsetY = rawDataArray[1];
              let off = 0;
              let addedDataCount = 0;
              for (let i = 2; i < rawDataArray.length;) {
                let count = rawDataArray[i++];
                data[off++] = count;
                for (let k = 0; k < count; k++) {
                  let x = rawDataArray[i++] + offsetX;
                  let y = rawDataArray[i++] + offsetY;
                  data[off++] = x;
                  data[off++] = y;

                  addedDataCount++;
                }
              }

              console.log(`Chunk ${chunk} fetched:`, data);
              return data;
            }),
           catchError(this.handleError) // Uncomment if you want to handle errors here
         ))
       )

   /* return this.httpClient.get(`assets/services/chanks/links_ny_0.bin`, { responseType: 'arraybuffer' })
      .pipe(
        map((rawData) => {
          const rawDataArray = new Float32Array(rawData);
          const data = new Float64Array(rawDataArray.length - 2);

          const offsetX = rawDataArray[0];
          const offsetY = rawDataArray[1];
          let off = 0;
          let addedDataCount = 0;
          for (let i = 2; i < rawDataArray.length;) {
            let count = rawDataArray[i++];
            data[off++] = count;
            for (let k = 0; k < count; k++) {
              let x = rawDataArray[i++] + offsetX;
              let y = rawDataArray[i++] + offsetY;
              data[off++] = x;
              data[off++] = y;

              addedDataCount++;
            }
          }

          console.log('Data fetched:', data);
          return data;
        }),
        catchError(this.handleError) // Uncomment if you want to handle errors here
      ).subscribe({
        next: (data) => {
          console.log('Data fetched:', data);
        },
        error: (error) => {
          this.handleError(error);
        }
      });*/



  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

  /*
fetch("https://echarts.apache.org/examples/data/asset/data/links-ny/links_ny_0.bin", {
  "headers": {
    "sec-ch-ua": "\"Not:A-Brand\";v=\"99\", \"Google Chrome\";v=\"145\", \"Chromium\";v=\"145\"",
    "sec-ch-ua-mobile": "?1",
    "sec-ch-ua-platform": "\"Android\""
  },
  "referrer": "https://echarts.apache.org/examples/en/editor.html?c=lines-ny",
  "body": null,
  "method": "GET",
  "mode": "cors",
  "credentials": "omit"
});

  */

  getOption() {
    const option = {
      progressive: 20000,
      backgroundColor: '#111',
      geo: {
        center: [-74.04327099998152, 40.86737600240287],
        zoom: 360,
        map: 'world',
        roam: true,
        silent: true,
        itemStyle: {
          color: 'transparent',
          borderColor: 'rgba(255,255,255,0.1)',
          borderWidth: 1
        }
      },
      series: [
        {
          type: 'lines',
          coordinateSystem: 'geo',
          blendMode: 'lighter',
          dimensions: ['value'],
          data: new Float64Array(),
          polyline: true,
          large: true,
          lineStyle: {
            color: 'orange',
            width: 0.5,
            opacity: 0.3
          }
        }
      ]
    };
    return option;
  }

}
