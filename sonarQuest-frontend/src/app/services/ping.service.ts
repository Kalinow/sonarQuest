import {Inject, Injectable, PLATFORM_ID} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {empty, fromEvent, merge, Observable} from "rxjs";
import {mapTo} from "rxjs/operators";
import {isPlatformBrowser} from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class PingService {

  private isOnline$: Observable<boolean>;

  constructor(private httpClient: HttpClient, @Inject(PLATFORM_ID) platform) {
    this.checkInternetConnectivity(platform);
  }

  private checkInternetConnectivity(platform) {
    if (isPlatformBrowser(platform)) {
      const offline$ = fromEvent(window, 'offline').pipe(mapTo(false));
      const online$ = fromEvent(window, 'online').pipe(mapTo(true));
      this.isOnline$ = merge(
        offline$, online$
      );
    } else {
      this.isOnline$ = empty();
    }
  }

  monitorIsInternetConnectionAvailable(): Observable<boolean> {
    return this.isOnline$;
  }

}
