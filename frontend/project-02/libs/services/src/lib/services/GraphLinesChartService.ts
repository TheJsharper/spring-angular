import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, from, map, mergeMap, Observable, throwError } from 'rxjs';

@Injectable()
export class GraphLinesChartService {

  private httpClient = inject(HttpClient);


  public fetchData(): Observable<Float64Array> {
    const chunkItems = Array.from({ length: 32 }).map((_, i) => i);
    return from(chunkItems).pipe
      (
        mergeMap((chunk) => this.httpClient.get(`assets/services/chanks/links_ny_${chunk}.bin`, { responseType: 'arraybuffer' }).pipe(
          map((rawData) => this.matpToFloatint32Array(rawData)),
          catchError(this.handleError)
        ))
      )




  }
  private matpToFloatint32Array(rawData: ArrayBuffer): Float64Array {

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
    return data;

  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      console.error('An error occurred:', error.error.message);
    } else {
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => 'Something bad happened; please try again later.');
  }

  public getOption() {
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
